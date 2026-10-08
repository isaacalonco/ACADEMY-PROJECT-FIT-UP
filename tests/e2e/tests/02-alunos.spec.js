const { test, expect } = require('@playwright/test');

test('CT-E2E-02: Cadastro de aluno com cálculo dinâmico de IMC', async ({ page }) => {
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

  // Preencher formulário completo com dados únicos
  const timestamp = Date.now().toString().slice(-4);
  const randomCpfDigits = Math.floor(100000000 + Math.random() * 900000000).toString();
  const cpfUnico = `${randomCpfDigits.slice(0,3)}.${randomCpfDigits.slice(3,6)}.${randomCpfDigits.slice(6,9)}-${randomCpfDigits.slice(0,2)}`;
  const nomeAluno = `Aluno Playwright ${timestamp}`;
  await page.locator('#aluno-nome').fill(nomeAluno);
  await page.locator('#aluno-cpf').fill(cpfUnico);
  await page.locator('#aluno-email').fill(`playwright.${timestamp}@fitup.com`);
  await page.locator('#aluno-telefone').fill('(61) 98888-7777');
  await page.locator('#aluno-endereco').fill('Brasília, DF');
  await page.locator('#aluno-nascimento').fill('1996-05-20');
  await page.locator('#aluno-peso').fill('78.5');
  await page.locator('#aluno-altura').fill('1.78');

  // Submeter formulário
  await page.locator('#btn-submit-aluno').click();

  // Modal deve fechar (remover classe active) e exibir notificação toast
  await expect(modalAluno).not.toHaveClass(/active/, { timeout: 8000 });
  await expect(page.locator('#toast-container')).toContainText('sucesso', { timeout: 8000 });

  // O aluno cadastrado deve estar presente na listagem
  await expect(page.locator('#alunos-table')).toContainText(nomeAluno, { timeout: 8000 });
});

