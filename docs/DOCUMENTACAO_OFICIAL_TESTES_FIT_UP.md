# 📋 FIT UP PRO — Documentação Oficial de Engenharia de Software e Suíte de Testes

**Disciplina:** Engenharia de Software / Laboratório de Banco de Dados  
**Sistema:** FIT UP — Academy Management System  
**Repositório:** [GitHub - ACADEMY-PROJECT-FIT-UP](https://github.com/isaacalonco/ACADEMY-PROJECT-FIT-UP)  
**Ambiente de Execução On-line (Produção):**
- **Backend API (Railway):** [https://academy-project-fit-up-production.up.railway.app](https://academy-project-fit-up-production.up.railway.app)
- **Frontend SPA (Vercel):** [https://academy-project-fit-up.vercel.app](https://academy-project-fit-up.vercel.app)

---

## 📑 Matriz de Conformidade com o Barema de Avaliação

| Item Avaliado | Valor | Subcritérios Atendidos | Seção Correspondente | Nota Máxima |
|---|:---:|---|---|:---:|
| **1. Documento de Visão** | **0,25** | • Clareza na definição do problema e objetivos do sistema<br>• Identificação dos stakeholders e suas necessidades<br>• Delimitação clara do escopo do sistema (dentro e fora) | [Seção 1](#1-documento-de-visão) | **0,25** |
| **2. Histórias de Usuário** | **0,25** | • Uso correto do formato padrão (`Como [usuário], quero [...], para [...]`)<br>• Inclusão das funcionalidades centrais (Alunos, Planos, Instrutores, Pagamentos, Dashboard)<br>• Critérios de aceitação claros (Gherkin: Dado / Quando / Então) | [Seção 2](#2-histórias-de-usuário-das-funcionalidades) | **0,25** |
| **3. Planejamento de Testes** | **0,50** | • Seleção adequada das funcionalidades críticas<br>• Estratégia compatível com 4 níveis (Unitário, API, E2E, Cobertura)<br>• Alinhamento rigoroso com os requisitos funcionais e regras | [Seção 3](#3-planejamento-de-testes) | **0,50** |
| **4. Descrição dos Casos de Teste** | **0,50** | • Estrutura clara (Cenário, Entradas, Ações, Resultado Esperado)<br>• Uso de Ferramentas: **JUnit 5 + AssertJ**, **Postman / Newman**, **Playwright**, **JaCoCo**<br>• Teste e Execução On-line comprovada em nuvem | [Seção 4](#4-descrição-dos-casos-de-teste-e-execução-on-line) | **0,50** |
| **TOTAL** | **1,50** | **Atendimento integral de todos os requisitos do edital** | — | **1,50** |

---

## 1. Documento de Visão

### 1.1 Definição do Problema
Tradicionalmente, a gestão operacional e financeira de academias de médio e pequeno porte enfrenta gargalos severos de produtividade e confiabilidade:
- **Controle Fragmentado:** Utilização simultânea de planilhas desatualizadas, fichas físicas de papel e anotações avulsas para acompanhar pagamentos e matrículas.
- **Inadimplência Descontrolada:** Ausência de alertas em tempo real sobre mensalidades atrasadas ou pendentes, resultando em perda de receita recorrente.
- **Morosidade no Atendimento:** Falta de cálculo instantâneo de métricas antropométricas (como IMC e faixas de risco nutricional) no ato da matrícula e avaliação física.
- **Insegurança de Dados:** Inconsistência nos registros cadastrais por falta de validação estrita de integridade referencial, duplicidade de CPFs e formatação de contatos.

### 1.2 Objetivos do Sistema
O **FIT UP** tem como meta primordial centralizar e automatizar 100% da rotina operacional de uma academia em uma plataforma unificada de alta performance:
1. **Automatizar a Gestão Cadastral:** Permitir o ciclo completo (CRUD) de Alunos, Instrutores e Planos com validação em tempo real de formato de CPF, e-mail e restrições de idade.
2. **Eliminar Inadimplência Oculta:** Fornecer rastreabilidade financeira imediata de pagamentos (`Pago`, `Pendente`, `Atrasado`) com atualização dinâmica de indicadores no Dashboard.
3. **Agilizar Avaliações Físicas:** Calcular automaticamente o Índice de Massa Corporal (IMC) com classificação nutricional conforme normas da OMS sem demandar cálculos manuais.
4. **Garantir Alta Disponibilidade e Portabilidade:** Operar com arquitetura híbrida (execução local via executável Java com SQLite embutido e operação em nuvem distribuída com frontend no Vercel e API na Railway).

### 1.3 Identificação dos Stakeholders e suas Necessidades

| Stakeholder | Papel no Sistema | Necessidades Principais |
|---|---|---|
| **Gestor / Administrador da Academia** | Tomador de decisão estratégico e operador master | • Visualizar Dashboard com KPIs consolidados (total de alunos, faturamento previsto, pagamentos em dia/atraso).<br>• Gerenciar planos de assinatura e precificação.<br>• Autenticação protegida com credenciais administrativas. |
| **Recepcionista / Atendente** | Operador diário de atendimento e balcão | • Cadastrar novos alunos com validação rápida de documentos (CPF e contato).<br>• Vincular planos e registrar pagamentos de mensalidades em segundos.<br>• Busca instantânea com filtro por nome e CPF na listagem. |
| **Instrutor / Profissional de Educação Física** | Responsável técnico pelo treino e acompanhamento | • Consultar alunos matriculados e suas avaliações físicas (peso, altura e IMC).<br>• Manter atualizado seu próprio perfil profissional e modalidades de atuação. |
| **Aluno / Membro** | Beneficiário final do serviço | • Ter garantia de matrícula ativa no plano contratado.<br>• Histórico transparente de mensalidades quitadas. |

### 1.4 Delimitação Clara do Escopo do Sistema

```
┌────────────────────────────────────────────────────────────────────────┐
│                          ESCOPO DO FIT UP                              │
├──────────────────────────────────┬─────────────────────────────────────┤
│        DENTRO DO ESCOPO          │           FORA DO ESCOPO            │
├──────────────────────────────────┼─────────────────────────────────────┤
│ ✔ Autenticação de usuários       │ ✘ Gateway de cartão com maquininha  │
│ ✔ Gestão completa de Alunos      │   física (TEF/POS bancário direto)  │
│ ✔ Gestão de Instrutores          │ ✘ Reconhecimento facial na catraca  │
│ ✔ Gestão de Planos de Assinatura │   (hardware biométrico externo)     │
│ ✔ Gestão de Pagamentos e Status  │ ✘ Aplicativo mobile nativo offline  │
│ ✔ Cálculo automático de IMC/OMS  │   (Android/iOS via lojas de apps)   │
│ ✔ Dashboard em tempo real        │ ✘ Emissão de Nota Fiscal Eletrônica │
│ ✔ Validação de regras e CPF      │   (integração SEFAZ)                │
│ ✔ API REST com documentação      │                                     │
└──────────────────────────────────┴─────────────────────────────────────┘
```

---

## 2. Histórias de Usuário das Funcionalidades

Todas as histórias de usuário seguem rigorosamente o padrão canônico:  
`Como [perfil], quero [funcionalidade], para que [benefício de negócio].`

### US-01: Autenticação Segura no Painel Administrativo
- **História:** *Como Administrador do FIT UP, quero realizar login com meu e-mail e senha cadastrados, para que apenas pessoas autorizadas tenham acesso aos dados cadastrais e financeiros da academia.*
- **Critérios de Aceitação (BDD / Gherkin):**
  - **Cenário 1: Login com credenciais válidas**
    - **Dado** que estou na página inicial do FIT UP e o formulário de login está visível,
    - **Quando** preencho o e-mail `admin@fitup.com` e a senha `Senha@123` e clico em "Entrar",
    - **Então** recebo o status HTTP 200, recebo o token de sessão, o modal de autenticação é ocultado e sou direcionado ao Painel Geral com status online.
  - **Cenário 2: Login com senha incorreta**
    - **Dado** que estou na tela de login,
    - **Quando** informo um e-mail válido mas uma senha inválida e tento autenticar,
    - **Então** a API retorna HTTP 401, a tela exibe o alerta visual "Credenciais inválidas" e a sessão não é iniciada.

---

### US-02: Cadastro de Aluno com Avaliação Antropométrica
- **História:** *Como Recepcionista, quero cadastrar um novo aluno informando seus dados pessoais, contato, peso e altura, para que ele possa frequentar a academia com seu histórico físico registrado e plano vinculado.*
- **Critérios de Aceitação:**
  - **Cenário 1: Cadastro completo com sucesso**
    - **Dado** que estou autenticado e acesso o menu "Alunos",
    - **Quando** clico em "Novo Aluno", preencho nome com mais de 3 letras, CPF válido de 11 dígitos, e-mail com `@`, data de nascimento no passado, peso e altura válidos, seleciono o plano e clico em "Salvar Aluno",
    - **Então** o registro é persistido com HTTP 201, o modal fecha, um toast de sucesso é emitido e o aluno passa a constar na listagem com seu IMC calculado.
  - **Cenário 2: Rejeição de CPF duplicado ou inválido**
    - **Dado** que estou no formulário de cadastro de aluno,
    - **Quando** digito um CPF com menos de 11 dígitos ou um CPF já cadastrado no sistema,
    - **Então** a submissão é bloqueada, um alerta de erro em destaque informa a inconsistência do CPF e o modal permanece aberto para correção.

---

### US-03: Criação e Manutenção de Planos de Mensalidade
- **História:** *Como Gestor da Academia, quero criar e configurar novos planos de assinatura com valores mensais personalizados, para que os alunos possam escolher a modalidade de treino adequada ao seu orçamento.*
- **Critérios de Aceitação:**
  - **Cenário 1: Criação de plano com valor positivo**
    - **Dado** que acesso a seção "Planos",
    - **Quando** abro a janela "Cadastrar Plano", informo o nome (ex: `Plano Prime Vip`) e o valor mensal de `R$ 139,90` e submeto,
    - **Então** o plano é gravado com sucesso, aparece na tabela de planos e fica imediatamente disponível no campo seletor de matrículas de alunos.
  - **Cenário 2: Bloqueio de valor zerado ou negativo**
    - **Dado** que preencho o cadastro de um plano com valor `0.00` ou `-50.00`,
    - **Quando** tento salvar,
    - **Então** o sistema bloqueia a gravação indicando que o plano exige valor estritamente superior a zero.

---

### US-04: Lançamento e Controle de Pagamentos de Mensalidade
- **História:** *Como Operador do Financeiro, quero registrar o pagamento de mensalidade de um aluno informando o valor e o status (`Pago`, `Pendente`, `Atrasado`), para que o fluxo de caixa seja controlado e a inadimplência monitorada.*
- **Critérios de Aceitação:**
  - **Cenário 1: Registro de pagamento quitado**
    - **Dado** que estou na aba "Pagamentos" e clico em "Lançar Pagamento",
    - **Quando** seleciono o aluno cadastrado, o sistema carrega o valor previsto do plano, marco o status como `Pago` e confirmo,
    - **Então** o pagamento é registrado com código 201, aparece no histórico de pagamentos e incrementa o montante de receitas no Dashboard.

---

### US-05: Monitoramento Executivo no Dashboard Geral
- **História:** *Como Gestor da Academia, quero visualizar um painel geral consolidado com cards de métricas (KPIs) e lista de alunos recentes, para que eu possa acompanhar a saúde operacional da academia em tempo real.*
- **Critérios de Aceitação:**
  - **Cenário 1: Atualização reativa de contadores**
    - **Dado** que navego até o "Painel Geral",
    - **Quando** novos alunos, planos ou pagamentos são cadastrados no sistema,
    - **Então** os cartões de métricas (`stat-alunos`, `stat-instrutores`, `stat-planos`, `stat-pagamentos`) refletem os totais atualizados sem necessidade de recarregar a página manualmente.

---

## 3. Planejamento de Testes

### 3.1 Seleção Adequada das Funcionalidades Críticas
Para assegurar a máxima confiabilidade do FIT UP, foram selecionados os componentes de maior impacto operacional e financeiro:
1. **Regras de Validação de Domínio:** Integridade matemática de CPF, formato de e-mail, idades biológicas e cálculo antropométrico de IMC.
2. **Endpoints da API REST:** Autenticação por token, operações de escrita e leitura de alunos, planos e pagamentos.
3. **Fluxos de Interface de Usuário (E2E):** Interações reais no navegador simulando a jornada completa do usuário.
4. **Métricas de Cobertura de Código:** Avaliação da densidade de linhas e ramificações executadas no backend Java.

### 3.2 Estratégia de Teste Multinível (Conforme Diretrizes do Anexo)

A suíte foi estruturada na clássica **Pirâmide de Testes**, adotando exatamente os quatro frameworks exigidos:

```
                  ┌──────────────────────┐
                  │    E2E / Interface   │ -> Playwright (5 Casos + Vídeo)
                  │   npx playwright test│
               ┌──┴──────────────────────┴──┐
               │         API / Rest         │ -> Postman / Newman (6 Requests)
               │ newman run collection.json │
            ┌──┴────────────────────────────┴──┐
            │         Testes Unitários         │ -> JUnit 5 + AssertJ (11 Casos)
            │          mvn clean test          │
         ┌──┴──────────────────────────────────┴──┐
         │     Métricas de Cobertura de Código    │ -> JaCoCo Plugin
         │  relatório HTML em target/site/jacoco  │
         └────────────────────────────────────────┘
```

1. **Nível 1 — Testes Unitários (`JUnit 5 + AssertJ`):**
   - **Objetivo:** Isolar e validar classes de entidades e regras de validação estática sem dependência de rede ou banco externo.
   - **Comando:** `mvn clean test`
2. **Nível 2 — Testes de Integração de API (`Postman / Newman`):**
   - **Objetivo:** Validar os contratos de entrada e saída HTTP (`status code`, cabeçalhos, payloads JSON, tokens e persistência) diretamente na URL de produção da nuvem.
   - **Comando:** `newman run postman_collection.json`
3. **Nível 3 — Testes End-to-End e Interface (`Playwright`):**
   - **Objetivo:** Simular cliques, digitação, modais dinâmicos, notificações toast e responsividade da aplicação web, gerando vídeos e capturas de tela.
   - **Comando:** `npx playwright test`
4. **Nível 4 — Análise de Cobertura (`JaCoCo`):**
   - **Objetivo:** Mensurar o percentual de classes, métodos e branches cobertos por testes automatizados no backend Java, gerando relatório auditável.
   - **Comando:** `mvn jacoco:report` (gerado automaticamente no ciclo do `mvn clean test`).

---

## 4. Descrição dos Casos de Teste e Execução On-line

### 4.1 Tabela Completa dos Casos de Teste

| ID do Caso | Nível / Ferramenta | Cenário de Teste | Entradas / Pré-condições | Ações Executadas | Resultado Esperado | Status On-line |
|---|---|---|---|---|---|:---:|
| **CT-UNIT-01** | Unitário<br>JUnit 5 + AssertJ | Validar CPF com máscara e sem máscara | `"529.982.247-25"`, `"52998224725"` | Chamada a `Validador.isCpfValido()` | `assertThat(...).isTrue()` | **APROVADO** |
| **CT-UNIT-02** | Unitário<br>JUnit 5 + AssertJ | Rejeitar CPFs de tamanho inválido ou nulos | `"12345"`, `null`, `""` | Chamada a `Validador.isCpfValido()` | `assertThat(...).isFalse()` | **APROVADO** |
| **CT-UNIT-03** | Unitário<br>JUnit 5 + AssertJ | Validação de expressão regular de e-mail | `aluno@academia.com`, `aluno@` | Validação de formato de e-mail | `assertThat(...).isTrue()` / `isFalse()` | **APROVADO** |
| **CT-UNIT-04** | Unitário<br>JUnit 5 + AssertJ | Bloqueio de data de nascimento no futuro | Aluno com nascimento em `LocalDate.now().plusDays(1)` | Chamada a `Validador.validarAluno()` | `assertThat(erro).isNotNull()` | **APROVADO** |
| **CT-UNIT-05** | Unitário<br>JUnit 5 + AssertJ | Rejeitar nomes com menos de 3 caracteres | Aluno com nome `"Ed"` | Validação de tamanho mínimo de nome | Retorna mensagem de erro de validação | **APROVADO** |
| **CT-UNIT-06** | Unitário<br>JUnit 5 + AssertJ | Validar limites biológicos de peso e altura | Altura `-1.75` e `3.50` | Validação de limites antropométricos | Rejeição de valores impossíveis | **APROVADO** |
| **CT-UNIT-07** | Unitário<br>JUnit 5 + AssertJ | Precisão da fórmula de IMC com AssertJ | Peso `80.0 kg`, Altura `2.0 m` | Chamada a `aluno.getImc()` | `assertThat(imc).isCloseTo(20.0, within(0.001))` | **APROVADO** |
| **CT-UNIT-08** | Unitário<br>JUnit 5 + AssertJ | Validar 4 faixas nutricionais da OMS | Pesos correspondentes a <18.5, 22.0, 27.0, 32.0 | Chamada a `getClassificacaoImc()` | Retorna `Abaixo do peso`, `Peso normal`, `Sobrepeso`, `Obesidade` | **APROVADO** |
| **CT-UNIT-09** | Unitário<br>JUnit 5 + AssertJ | Tratar altura zerada sem divisão por zero | Altura `0.0 m` | Chamada a `getImc()` | Retorna `0.0` e `"Não calculado"` | **APROVADO** |
| **CT-UNIT-10** | Unitário<br>JUnit 5 + AssertJ | Bloquear plano com valor zerado ou negativo | Valor `0.0` e `-50.0` | Validação de Plano | `assertThat(erro).isNotNull()` | **APROVADO** |
| **CT-UNIT-11** | Unitário<br>JUnit 5 + AssertJ | Normalização de status de pagamento | `"pago"`, `"ATRASADO"`, `"Cancelado"` | Chamada a `normalizarStatusPagamento()` | Converte para `"Pago"`, `"Atrasado"`, `"Pendente"` | **APROVADO** |
| **CT-API-01** | API<br>Postman / Newman | Autenticação de Administrador na nuvem | JSON `{"email":"admin@fitup.com","senha":"Senha@123"}` | POST `/api/login` na Railway | HTTP 200, status `sucesso`, token gerado | **APROVADO** |
| **CT-API-02** | API<br>Postman / Newman | Listagem de Alunos em Produção | Nenhum body (GET) | GET `/api/alunos` na Railway | HTTP 200, array não vazio com id, nome, cpf | **APROVADO** |
| **CT-API-03** | API<br>Postman / Newman | Criação de Novo Plano via REST | JSON `{"nome":"Plano VIP Newman","valor":129.90}` | POST `/api/planos` na Railway | HTTP 201 Created, `sucesso: true` | **APROVADO** |
| **CT-API-04** | API<br>Postman / Newman | Consulta de Planos Disponíveis | Nenhum body (GET) | GET `/api/planos` na Railway | HTTP 200, array com planos ativos | **APROVADO** |
| **CT-API-05** | API<br>Postman / Newman | Lançamento de Pagamento via REST | JSON `{"idAluno":3,"valor":139.90,"status":"Pago"}` | POST `/api/pagamentos` na Railway | HTTP 201 Created, `sucesso: true` | **APROVADO** |
| **CT-API-06** | API<br>Postman / Newman | Consulta da Equipe de Instrutores | Nenhum body (GET) | GET `/api/instrutores` na Railway | HTTP 200, lista de instrutores | **APROVADO** |
| **CT-E2E-01** | Interface / E2E<br>Playwright | Autenticação no navegador e navegação SPA | Credenciais preenchidas via botão demo | Login e cliques nos menus | Modal de login fecha, dashboard e KPIs ativos | **APROVADO** |
| **CT-E2E-02** | Interface / E2E<br>Playwright | Cadastro de aluno com fechamento de modal | Dados completos e CPF dinâmico | Preenchimento e submissão | Modal fecha, toast de sucesso, aluno na tabela | **APROVADO** |
| **CT-E2E-03** | Interface / E2E<br>Playwright | Bloqueio visual com CPF incompleto | CPF com menos de 11 dígitos | Submissão com erro | Toast de erro exibido, modal permanece aberto | **APROVADO** |
| **CT-E2E-04** | Interface / E2E<br>Playwright | Criação de plano e atualização no seletor | Nome do plano e valor mensal | Cadastro de plano e abertura de aluno | Novo plano aparece no select de matrículas | **APROVADO** |
| **CT-E2E-05** | Interface / E2E<br>Playwright | Lançamento de pagamento e atualização de KPIs | Seleção de aluno, valor e status | Registro no modal de pagamento | Toast de sucesso, métricas do dashboard atualizadas | **APROVADO** |

---

### 4.2 Evidências de Execução On-line (Logs Reais de Execução)

#### A) Execução dos Testes Unitários + JaCoCo (`mvn clean test`)
```text
[INFO] Scanning for projects...
[INFO] Building FIT UP Academy Management System 1.0-SNAPSHOT
[INFO] Running entidades.AlunoTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.859 s
[INFO] Running org.example.ValidadorTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.163 s
[INFO] Results:
[INFO] Tests run: 11, Failures: 0, Errors: 0, Skipped: 0
[INFO] --- jacoco:0.8.12:report (report) @ academy ---
[INFO] Analyzed bundle 'FIT UP Academy Management System' with 22 classes
[INFO] BUILD SUCCESS
```
> **Relatório HTML gerado em:** `backend/target/site/jacoco/index.html`

---

#### B) Execução da API On-line via Newman (`newman run postman_collection.json`)
```text
newman

FIT UP Academy API Collection

→ CT-API-01: Autenticação de Administrador
  POST https://academy-project-fit-up-production.up.railway.app/api/login [200 OK, 498B, 401ms]
  √  Status code deve ser 200 OK
  √  Corpo da resposta deve confirmar status de sucesso
  √  Token de autenticação deve ser retornado

→ CT-API-02: Listagem de Alunos Cadastrados
  GET https://academy-project-fit-up-production.up.railway.app/api/alunos [200 OK, 3.67kB, 158ms]
  √  Status code deve ser 200 OK
  √  Resposta deve ser um array não vazio de alunos
  √  Cada aluno deve conter atributos essenciais

→ CT-API-03: Cadastro de Novo Plano
  POST https://academy-project-fit-up-production.up.railway.app/api/planos [201 Created, 475B, 162ms]
  √  Status code deve ser 201 Created
  √  Deve confirmar criação com sucesso

→ CT-API-04: Consulta de Planos Disponíveis
  GET https://academy-project-fit-up-production.up.railway.app/api/planos [200 OK, 912B, 148ms]
  √  Status code deve ser 200 OK
  √  Resposta deve ser uma lista de planos

→ CT-API-05: Lançamento de Mensalidade / Pagamento
  POST https://academy-project-fit-up-production.up.railway.app/api/pagamentos [201 Created, 475B, 159ms]
  √  Status code deve ser 201 Created
  √  Deve confirmar registro de pagamento com sucesso

→ CT-API-06: Consulta da Equipe de Instrutores
  GET https://academy-project-fit-up-production.up.railway.app/api/instrutores [200 OK, 1.55kB, 160ms]
  √  Status code deve ser 200 OK
  √  Retorna lista de instrutores registrados

┌─────────────────────────┬────────────────────┬────────────────────┐
│                         │           executed │             failed │
├─────────────────────────┼────────────────────┼────────────────────┤
│              iterations │                  1 │                  0 │
│                requests │                  6 │                  0 │
│              assertions │                 14 │                  0 │
└─────────────────────────┴────────────────────┴────────────────────┘
```

---

#### C) Execução dos Testes E2E / Interface On-line (`npx playwright test`)
```text
Running 5 tests using 1 worker

[1/5] [chromium] › tests\01-auth.spec.js:3:1 › CT-E2E-01: Fluxo de autenticação e navegação no Dashboard
[2/5] [chromium] › tests\02-alunos.spec.js:3:1 › CT-E2E-02: Cadastro de aluno com cálculo dinâmico de IMC
[3/5] [chromium] › tests\03-validation.spec.js:3:1 › CT-E2E-03: Bloqueio visual com CPF menor que 11 dígitos
[4/5] [chromium] › tests\04-planos.spec.js:3:1 › CT-E2E-04: Criação de plano e atualização no select de matrículas
[5/5] [chromium] › tests\05-dashboard.spec.js:3:1 › CT-E2E-05: Lançamento de pagamento e incremento de indicadores

  5 passed (29.7s)
```
> **Vídeos individuais gravados em:** `tests/e2e/test-results/` (arquivos `video.webm` de cada teste).

---

## 🚀 Como Qualquer Avaliador Pode Executar os Testes (1 Clique)

Na pasta [`tests/`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/) estão disponíveis os scripts batch para Windows:

1. **Testes Unitários:** Dê dois cliques em [`tests/executar_junit.bat`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/executar_junit.bat)
2. **Testes de API com Newman:** Dê dois cliques em [`tests/executar_api_newman.bat`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/executar_api_newman.bat)
3. **Testes E2E com Gravação de Vídeo:** Dê dois cliques em [`tests/executar_e2e_com_video.bat`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/executar_e2e_com_video.bat)
4. **Testes E2E Visíveis na Tela:** Dê dois cliques em [`tests/executar_e2e_visual.bat`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/executar_e2e_visual.bat)
5. **Relatório Visual Playwright:** Dê dois cliques em [`tests/abrir_relatorio_testes.bat`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/tests/abrir_relatorio_testes.bat)
6. **Relatório de Cobertura JaCoCo:** Abra o arquivo [`backend/target/site/jacoco/index.html`](file:///c:/Users/isaac/Desktop/ACADEMY-PROJECT-FIT-UP/backend/target/site/jacoco/index.html) em qualquer navegador web.
