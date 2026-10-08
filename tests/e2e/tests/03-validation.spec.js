const { test, expect } = require('@playwright/test');

test('CT-E2E-03: Bloqueio visual com CPF menor que 11 dígitos', async ({ page }) => {
  await page.goto('/');

  // Autenticar com credenciais demo se necessário
  const authContainer = page.locator('#auth-container');
  if (await authContainer.isVisible()) {
    await page.locator('#btn-fill-demo').click();
    await page.locator('#btn-login-submit').click();
    await expect(authContainer).toHaveClass(/hidden/, { timeout: 10000 });
  }

  // Navegar para a página de Alunos
  await page.locator('.sidebar-nav-item[data-page="alunos"]').click();
  await expect(page.locator('#page-alunos')).toBeVisible();

  // Abrir modal de novo aluno
  await page.locator('#page-alunos [data-modal="modal-aluno"]').click();
  const modalAluno = page.locator('#modal-aluno');
  await expect(modalAluno).toHaveClass(/active/);

  // Preencher campos válidos exceto CPF (incompleto)
  await page.locator('#aluno-nome').fill('Teste Bloqueio CPF');
  await page.locator('#aluno-cpf').fill('123.456');
  await page.locator('#aluno-email').fill('teste.bloqueio@fitup.com');
  await page.locator('#aluno-nascimento').fill('1990-01-01');

  // Submeter com CPF inválido
  await page.locator('#btn-submit-aluno').click();

  // Toast de erro deve aparecer indicando problema de validação no CPF
  const toastContainer = page.locator('#toast-container');
  await expect(toastContainer).toBeVisible({ timeout: 4000 });
  await expect(toastContainer).toContainText('CPF');

  // Modal deve continuar aberto
  await expect(modalAluno).toHaveClass(/active/);
});

