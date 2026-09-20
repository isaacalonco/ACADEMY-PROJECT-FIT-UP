const { test, expect } = require('@playwright/test');

test('CT-E2E-03: Bloqueio visual com CPF menor que 11 dígitos', async ({ page }) => {
  await page.goto('/');

  // Navegar para Alunos
  await page.locator('li[data-page="alunos"] a').click();

  // Abrir modal de novo aluno
  await page.locator('#page-alunos .btn-primary').click();
  await expect(page.locator('#modal-aluno')).toBeVisible();

  // Preencher campos válidos exceto CPF (menos de 11 dígitos)
  await page.locator('#form-aluno input[name="nome"]').fill('Teste Bloqueio CPF');
  await page.locator('#form-aluno input[name="cpf"]').fill('123.456');
  await page.locator('#form-aluno input[name="email"]').fill('teste@fitup.com');
  await page.locator('#form-aluno input[name="dataNascimento"]').fill('1990-01-01');

  // Submeter com CPF inválido
  await page.locator('#btn-submit-aluno').click();

  // Toast de erro deve aparecer com mensagem sobre CPF
  const toast = page.locator('#toast');
  await expect(toast).toBeVisible({ timeout: 3000 });
  await expect(toast).toContainText('CPF');

  // Modal deve continuar aberto (não fechou)
  await expect(page.locator('#modal-aluno')).toBeVisible();
});
