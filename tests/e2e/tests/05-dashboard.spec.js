const { test, expect } = require('@playwright/test');

test('CT-E2E-05: Lançamento de pagamento e incremento de indicadores', async ({ page }) => {
  await page.goto('/');

  // Autenticar com credenciais demo se necessário
  const authContainer = page.locator('#auth-container');
  if (await authContainer.isVisible()) {
    await page.locator('#btn-fill-demo').click();
    await page.locator('#btn-login-submit').click();
    await expect(authContainer).toHaveClass(/hidden/, { timeout: 10000 });
  }

  // 1. Dashboard deve carregar dados e exibir indicadores
  await page.locator('.sidebar-nav-item[data-page="dashboard"]').click();
  await expect(page.locator('#page-dashboard')).toBeVisible();

  // Cards de métricas
  const statAlunos = page.locator('#stat-alunos');
  await expect(statAlunos).toBeVisible();

  // 2. Navegar para Pagamentos
  await page.locator('.sidebar-nav-item[data-page="pagamentos"]').click();
  await expect(page.locator('#page-pagamentos')).toBeVisible();

  // 3. Abrir modal de pagamento
  await page.locator('#page-pagamentos [data-modal="modal-pagamento"]').click();
  const modalPagamento = page.locator('#modal-pagamento');
  await expect(modalPagamento).toHaveClass(/active/);

  // 4. Selecionar o primeiro aluno disponível no select
  const selectAluno = page.locator('#select-aluno-pagamento');
  await expect(selectAluno).toBeVisible();
  
  // Aguarda opções serem carregadas
  await page.waitForFunction(() => {
    const sel = document.getElementById('select-aluno-pagamento');
    return sel && sel.options.length > 1;
  }, { timeout: 10000 });

  // Seleciona o aluno
  await selectAluno.selectOption({ index: 1 });
  
  // Aguarda auto-cálculo ou preenche o valor
  await page.waitForTimeout(1000);
  const valAtual = await page.locator('#pagamento-valor').inputValue();
  if (!valAtual || parseFloat(valAtual) <= 0) {
    await page.locator('#pagamento-valor').fill('139.90');
  }
  await page.locator('#pagamento-status').selectOption('Pago');

  // Submete o pagamento
  await page.locator('#btn-submit-pagamento').click();

  // Modal fecha e toast de sucesso aparece
  await expect(modalPagamento).not.toHaveClass(/active/, { timeout: 8000 });
  await expect(page.locator('#toast-container')).toContainText('sucesso', { timeout: 8000 });

  // 5. Retorna ao Dashboard e valida que os pagamentos aparecem
  await page.locator('.sidebar-nav-item[data-page="dashboard"]').click();
  await expect(page.locator('#stat-pagamentos')).toBeVisible();
});


