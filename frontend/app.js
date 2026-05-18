document.addEventListener('DOMContentLoaded', () => {

    // Cache de dados para lookup
    let cachedAlunos = [], cachedMatriculas = [], cachedPlanos = [];

    // ==================== NAVEGAÇÃO ====================
    document.querySelectorAll('.nav-links li').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const target = link.dataset.page;
            document.querySelectorAll('.nav-links li').forEach(l => l.classList.remove('active'));
            link.classList.add('active');
            document.querySelectorAll('.page').forEach(p => p.classList.remove('active-page'));
            document.getElementById('page-' + target).classList.add('active-page');
            loadPageData(target);
        });
    });

    // ==================== MODAIS ====================
    document.querySelectorAll('[data-modal]').forEach(btn => {
        btn.addEventListener('click', () => {
            const modalId = btn.dataset.modal;
            const modal = document.getElementById(modalId);
            // Reset para modo criação
            const form = modal.querySelector('form');
            if (form) form.reset();
            const hiddenId = form ? form.querySelector('input[type="hidden"]') : null;
            if (hiddenId) hiddenId.value = '';
            // Títulos
            if (modalId === 'modal-aluno') { document.getElementById('modal-aluno-title').textContent = 'Cadastrar Aluno'; document.getElementById('btn-submit-aluno').textContent = 'Cadastrar Aluno'; populatePlanoSelect(); }
            if (modalId === 'modal-instrutor') { document.getElementById('modal-instrutor-title').textContent = 'Cadastrar Instrutor'; document.getElementById('btn-submit-instrutor').textContent = 'Cadastrar Instrutor'; }
            if (modalId === 'modal-plano') { document.getElementById('modal-plano-title').textContent = 'Cadastrar Plano'; document.getElementById('btn-submit-plano').textContent = 'Cadastrar Plano'; }
            if (modalId === 'modal-pagamento') { document.getElementById('modal-pagamento-title').textContent = 'Registrar Pagamento'; document.getElementById('btn-submit-pagamento').textContent = 'Registrar Pagamento'; populateAlunoSelect('select-aluno-pagamento'); document.getElementById('pagamento-valor').value = ''; document.getElementById('pagamento-valor').setAttribute('readonly', true); document.getElementById('form-pagamento').idPagamento.value = ''; }
            modal.classList.add('active');
        });
    });

    document.querySelectorAll('.close-btn').forEach(b => b.addEventListener('click', () => b.closest('.modal-overlay').classList.remove('active')));
    document.querySelectorAll('.modal-overlay').forEach(o => o.addEventListener('click', (e) => { if (e.target === o) o.classList.remove('active'); }));

    // ==================== TOAST ====================
    function showToast(msg, type = 'success') {
        const t = document.getElementById('toast');
        t.textContent = msg; t.className = 'toast ' + type + ' show';
        setTimeout(() => t.classList.remove('show'), 3000);
    }

    // ==================== PAGAMENTO: PUXAR VALOR DO PLANO ====================
    document.getElementById('select-aluno-pagamento').addEventListener('change', async (e) => {
        const idAluno = parseInt(e.target.value);
        const valorInput = document.getElementById('pagamento-valor');
        valorInput.value = '';
        if (!idAluno) return;
        // Buscar matrícula do aluno para achar o plano e seu valor
        const [matriculas, alunos, planos] = await Promise.all([
            fetchData('/api/matriculas'), fetchData('/api/alunos'), fetchData('/api/planos')
        ]);
        const aluno = alunos.find(a => a.id === idAluno);
        const mat = matriculas.find(m => m.nomeAluno === (aluno ? aluno.nome : ''));
        if (mat) {
            const plano = planos.find(p => p.nome === mat.nomePlano);
            if (plano) { valorInput.value = plano.valor.toFixed(2); return; }
        }
        // Aluno sem plano: deixar em branco (será validado na submissão)
        valorInput.value = '';
    });

    // ==================== FORMULÁRIOS ====================

    // Aluno
    document.getElementById('form-aluno').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const id = form.id.value;
        const data = {
            nome: form.nome.value, cpf: form.cpf.value, email: form.email.value,
            telefone: form.telefone.value || 'Não informado', endereco: form.endereco.value || 'Não informado',
            dataNascimento: form.dataNascimento.value, peso: parseFloat(form.peso.value) || 0, altura: parseFloat(form.altura.value) || 0
        };
        const idPlano = form.idPlano.value;
        let ok;
        if (id) {
            data.ativo = true;
            data.idPlano = idPlano ? parseInt(idPlano) : 0;
            ok = await putData('/api/alunos/' + id, data);
            if (ok) showToast('Aluno atualizado!');
        } else {
            ok = await postData('/api/alunos', data);
            if (ok && idPlano) {
                const alunos = await fetchData('/api/alunos');
                const ultimo = alunos[alunos.length - 1];
                if (ultimo) await postData('/api/matriculas', { idAluno: ultimo.id, idPlano: parseInt(idPlano) });
            }
            if (ok) showToast('Aluno cadastrado!');
        }
        if (ok) { form.reset(); closeAllModals(); loadAlunos(); loadDashboard(); }
    });

    // Instrutor
    document.getElementById('form-instrutor').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const id = form.idInstrutor.value;
        const data = { nome: form.nome.value, cpf: form.cpf.value, email: form.email.value, telefone: form.telefone.value, especialidade: form.especialidade.value };
        let ok;
        if (id) { ok = await putData('/api/instrutores/' + id, data); if (ok) showToast('Instrutor atualizado!'); }
        else { ok = await postData('/api/instrutores', data); if (ok) showToast('Instrutor cadastrado!'); }
        if (ok) { form.reset(); closeAllModals(); loadInstrutores(); loadDashboard(); }
    });

    // Plano
    document.getElementById('form-plano').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const id = form.id.value;
        const data = { nome: form.nome.value, valor: parseFloat(form.valor.value) };
        let ok;
        if (id) { ok = await putData('/api/planos/' + id, data); if (ok) showToast('Plano atualizado!'); }
        else { ok = await postData('/api/planos', data); if (ok) showToast('Plano cadastrado!'); }
        if (ok) { form.reset(); closeAllModals(); loadPlanos(); loadDashboard(); }
    });

    // Pagamento
    document.getElementById('form-pagamento').addEventListener('submit', async (e) => {
        e.preventDefault();
        const form = e.target;
        const id = form.idPagamento.value;
        const data = { idAluno: parseInt(form.idAluno.value), valor: parseFloat(form.valor.value), status: form.status.value };
        let ok;
        if (id) { ok = await putData('/api/pagamentos/' + id, data); if (ok) showToast('Pagamento atualizado!'); }
        else { ok = await postData('/api/pagamentos', data); if (ok) showToast('Pagamento registrado!'); }
        if (ok) { form.reset(); closeAllModals(); loadPagamentos(); loadDashboard(); }
    });

    // ==================== FUNÇÕES DE EDIÇÃO ====================

    window.editAluno = async function(id) {
        const [alunos, matriculas] = await Promise.all([fetchData('/api/alunos'), fetchData('/api/matriculas')]);
        const a = alunos.find(x => x.id === id);
        if (!a) return;
        const form = document.getElementById('form-aluno');
        form.id.value = a.id; form.nome.value = a.nome; form.cpf.value = a.cpf;
        form.email.value = a.email; form.telefone.value = a.telefone; form.endereco.value = a.endereco;
        form.dataNascimento.value = a.dataNascimento; form.peso.value = a.peso; form.altura.value = a.altura;
        document.getElementById('modal-aluno-title').textContent = 'Editar Aluno';
        document.getElementById('btn-submit-aluno').textContent = 'Salvar Alterações';
        await populatePlanoSelect();
        // Pré-selecionar o plano atual do aluno
        const mat = matriculas.find(m => m.idAluno === id);
        if (mat) {
            const planos = await fetchData('/api/planos');
            const plano = planos.find(p => p.nome === mat.nomePlano);
            if (plano) document.getElementById('select-plano-aluno').value = plano.id;
        }
        document.getElementById('modal-aluno').classList.add('active');
    };

    window.editInstrutor = function(id, nome, cpf, email, telefone, especialidade) {
        const form = document.getElementById('form-instrutor');
        form.idInstrutor.value = id; form.nome.value = nome; form.cpf.value = cpf;
        form.email.value = email || ''; form.telefone.value = telefone || ''; form.especialidade.value = especialidade;
        document.getElementById('modal-instrutor-title').textContent = 'Editar Instrutor';
        document.getElementById('btn-submit-instrutor').textContent = 'Salvar Alterações';
        document.getElementById('modal-instrutor').classList.add('active');
    };

    window.editPlano = function(id, nome, valor) {
        const form = document.getElementById('form-plano');
        form.id.value = id; form.nome.value = nome; form.valor.value = valor;
        document.getElementById('modal-plano-title').textContent = 'Editar Plano';
        document.getElementById('btn-submit-plano').textContent = 'Salvar Alterações';
        document.getElementById('modal-plano').classList.add('active');
    };

    window.editPagamento = async function(id, nomeAluno) {
        const form = document.getElementById('form-pagamento');
        form.idPagamento.value = id;
        
        // Popular select de alunos e pre-selecionar o aluno correto
        await populateAlunoSelect('select-aluno-pagamento');
        const alunos = await fetchData('/api/alunos');
        const aluno = alunos.find(a => a.nome === nomeAluno);
        if (aluno) {
            form.idAluno.value = aluno.id;
            // Disparar o evento 'change' para buscar o valor do plano automaticamente
            form.idAluno.dispatchEvent(new Event('change'));
        }
        
        document.getElementById('modal-pagamento-title').textContent = 'Editar Pagamento';
        document.getElementById('btn-submit-pagamento').textContent = 'Salvar Alterações';
        document.getElementById('modal-pagamento').classList.add('active');
    };

    // ==================== FUNÇÕES DE EXCLUSÃO ====================

    window.deleteItem = async function(entity, id, label) {
        if (!confirm('Tem certeza que deseja apagar ' + label + '?')) return;
        const ok = await deleteData('/api/' + entity + '/' + id);
        if (ok) { showToast(label + ' apagado!'); loadPageData(entity); loadDashboard(); }
    };

    // ==================== CARREGAMENTO ====================

    async function loadDashboard() {
        const [alunos, instrutores, planos, pagamentos, matriculas] = await Promise.all([
            fetchData('/api/alunos'), fetchData('/api/instrutores'), fetchData('/api/planos'),
            fetchData('/api/pagamentos'), fetchData('/api/matriculas')
        ]);
        cachedAlunos = alunos; cachedMatriculas = matriculas; cachedPlanos = planos;
        document.getElementById('stat-alunos').textContent = alunos.length;
        document.getElementById('stat-instrutores').textContent = instrutores.length;
        document.getElementById('stat-planos').textContent = planos.length;
        document.getElementById('stat-pagamentos').textContent = pagamentos.length;
        const matMap = {}; matriculas.forEach(m => matMap[m.nomeAluno] = m.nomePlano);
        const tbody = document.getElementById('dashboard-alunos-table');
        tbody.innerHTML = '';
        const recentes = alunos.slice(-5).reverse();
        if (!recentes.length) { tbody.innerHTML = '<tr class="empty-row"><td colspan="5">Nenhum aluno.</td></tr>'; return; }
        recentes.forEach(a => {
            const plano = matMap[a.nome] || '<span style="color:var(--text-muted)">—</span>';
            tbody.innerHTML += `<tr><td>${a.nome}</td><td>${a.cpf}</td><td>${a.email||'-'}</td><td>${plano}</td>
                <td><span class="badge ${a.ativo?'active':'inactive'}">${a.ativo?'Ativo':'Inativo'}</span></td></tr>`;
        });
    }

    async function loadAlunos() {
        const [alunos, matriculas] = await Promise.all([fetchData('/api/alunos'), fetchData('/api/matriculas')]);
        const matMap = {}; matriculas.forEach(m => matMap[m.nomeAluno] = m.nomePlano);
        const tbody = document.getElementById('alunos-table');
        tbody.innerHTML = '';
        if (!alunos.length) { tbody.innerHTML = '<tr class="empty-row"><td colspan="9">Nenhum aluno cadastrado.</td></tr>'; return; }
        alunos.forEach(a => {
            const plano = matMap[a.nome] || '<span style="color:var(--text-muted)">—</span>';
            let imcBadge = '<span style="color:var(--text-muted)">—</span>';
            if (a.peso > 0 && a.altura > 0) {
                const imc = a.peso / (a.altura * a.altura);
                let cls = 'imc-normal';
                if (imc < 18.5) cls = 'imc-baixo'; else if (imc >= 25 && imc < 30) cls = 'imc-sobrepeso'; else if (imc >= 30) cls = 'imc-obeso';
                imcBadge = `<span class="badge ${cls}">${imc.toFixed(1)}</span>`;
            }
            tbody.innerHTML += `<tr>
                <td>${a.id}</td><td>${a.nome}</td><td>${a.cpf}</td><td>${a.email||'-'}</td><td>${a.telefone||'-'}</td>
                <td>${plano}</td><td>${imcBadge}</td>
                <td><span class="badge ${a.ativo?'active':'inactive'}">${a.ativo?'Ativo':'Inativo'}</span></td>
                <td><div class="action-btns">
                    <button class="btn-icon edit" onclick="editAluno(${a.id})" title="Editar"><i class="ph ph-pencil-simple"></i></button>
                    <button class="btn-icon delete" onclick="deleteItem('alunos',${a.id},'${a.nome}')" title="Apagar"><i class="ph ph-trash"></i></button>
                </div></td></tr>`;
        });
    }

    async function loadInstrutores() {
        const data = await fetchData('/api/instrutores');
        const tbody = document.getElementById('instrutores-table');
        tbody.innerHTML = '';
        if (!data.length) { tbody.innerHTML = '<tr class="empty-row"><td colspan="7">Nenhum instrutor.</td></tr>'; return; }
        data.forEach(i => {
            tbody.innerHTML += `<tr>
                <td>${i.idInstrutor}</td><td>${i.nome}</td><td>${i.cpf||'-'}</td><td>${i.email||'-'}</td><td>${i.telefone||'-'}</td><td>${i.especialidade}</td>
                <td><div class="action-btns">
                    <button class="btn-icon edit" onclick="editInstrutor(${i.idInstrutor},'${i.nome}','${i.cpf||''}','${i.email||''}','${i.telefone||''}','${i.especialidade}')" title="Editar"><i class="ph ph-pencil-simple"></i></button>
                    <button class="btn-icon delete" onclick="deleteItem('instrutores',${i.idInstrutor},'${i.nome}')" title="Apagar"><i class="ph ph-trash"></i></button>
                </div></td></tr>`;
        });
    }

    async function loadPlanos() {
        const data = await fetchData('/api/planos');
        const tbody = document.getElementById('planos-table');
        tbody.innerHTML = '';
        if (!data.length) { tbody.innerHTML = '<tr class="empty-row"><td colspan="4">Nenhum plano.</td></tr>'; return; }
        data.forEach(p => {
            tbody.innerHTML += `<tr><td>${p.id}</td><td>${p.nome}</td><td>R$ ${p.valor.toFixed(2)}</td>
                <td><div class="action-btns">
                    <button class="btn-icon edit" onclick="editPlano(${p.id},'${p.nome}',${p.valor})" title="Editar"><i class="ph ph-pencil-simple"></i></button>
                    <button class="btn-icon delete" onclick="deleteItem('planos',${p.id},'${p.nome}')" title="Apagar"><i class="ph ph-trash"></i></button>
                </div></td></tr>`;
        });
    }

    async function loadPagamentos() {
        const data = await fetchData('/api/pagamentos');
        const tbody = document.getElementById('pagamentos-table');
        tbody.innerHTML = '';
        if (!data.length) { tbody.innerHTML = '<tr class="empty-row"><td colspan="5">Nenhum pagamento.</td></tr>'; return; }
        data.forEach(p => {
            tbody.innerHTML += `<tr><td>${p.idPagamento}</td><td>${p.nomeAluno}</td><td>R$ ${p.valor.toFixed(2)}</td>
                <td><span class="badge ${p.status}">${p.status}</span></td>
                <td><div class="action-btns">
                    <button class="btn-icon edit" onclick="editPagamento(${p.idPagamento}, '${p.nomeAluno}')" title="Editar"><i class="ph ph-pencil-simple"></i></button>
                    <button class="btn-icon delete" onclick="deleteItem('pagamentos',${p.idPagamento},'Pagamento #${p.idPagamento}')" title="Apagar"><i class="ph ph-trash"></i></button>
                </div></td></tr>`;
        });
    }

    // ==================== SELECTS ====================

    async function populatePlanoSelect() {
        const planos = await fetchData('/api/planos');
        const s = document.getElementById('select-plano-aluno');
        s.innerHTML = '<option value="">Sem plano</option>';
        planos.forEach(p => s.innerHTML += `<option value="${p.id}">${p.nome} — R$ ${p.valor.toFixed(2)}</option>`);
    }

    async function populateAlunoSelect(selectId) {
        const alunos = await fetchData('/api/alunos');
        const s = document.getElementById(selectId);
        s.innerHTML = '<option value="">Selecione um aluno</option>';
        alunos.forEach(a => s.innerHTML += `<option value="${a.id}">${a.nome}</option>`);
    }

    // ==================== HELPERS ====================

    async function fetchData(url) {
        try { const r = await fetch(url); if (!r.ok) throw 0; return await r.json(); }
        catch { return []; }
    }
    async function postData(url, data) {
        try { const r = await fetch(url, { method: 'POST', headers: {'Content-Type':'application/json'}, body: JSON.stringify(data) }); if (r.ok) return true; showToast('Erro ao salvar.','error'); return false; }
        catch { showToast('Falha de conexão.','error'); return false; }
    }
    async function putData(url, data) {
        try { const r = await fetch(url, { method: 'PUT', headers: {'Content-Type':'application/json'}, body: JSON.stringify(data) }); if (r.ok) return true; showToast('Erro ao atualizar.','error'); return false; }
        catch { showToast('Falha de conexão.','error'); return false; }
    }
    async function deleteData(url) {
        try { const r = await fetch(url, { method: 'DELETE' }); if (r.ok) return true; showToast('Erro ao apagar.','error'); return false; }
        catch { showToast('Falha de conexão.','error'); return false; }
    }
    function closeAllModals() { document.querySelectorAll('.modal-overlay').forEach(m => m.classList.remove('active')); }
    function loadPageData(page) {
        switch(page) {
            case 'dashboard': loadDashboard(); break;
            case 'alunos': loadAlunos(); break;
            case 'instrutores': loadInstrutores(); break;
            case 'planos': loadPlanos(); break;
            case 'pagamentos': loadPagamentos(); break;
        }
    }
    loadDashboard();
});
