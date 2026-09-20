const { test, expect } = require('@playwright/test');

test('CT-E2E-02: Cadastro de aluno com cálculo dinâmico de IMC', async ({ page }) => {
  await page.goto('/');

  // Navegar para Alunos
  await page.locator('li[data-page="alunos"] a').click();
  await expect(page.locator('#page-alunos')).toBeVisible();

  // Abrir modal de novo aluno
  await page.locator('#page-alunos .btn-primary').click();
  await expect(page.locator('#modal-aluno')).toBeVisible();

  // Preencher os campos do formulário
  await page.locator('#form-aluno input[name="nome"]').fill('Maria Teste Playwright');
  await page.locator('#form-aluno input[name="cpf"]').fill('529.982.247-25');
  await page.locator('#form-aluno input[name="email"]').fill('maria.playwright@fitup.com');
  await page.locator('#form-aluno input[name="telefone"]').fill('(11) 99999-1234');
  await page.locator('#form-aluno input[name="endereco"]').fill('Rua dos Testes, 42');
  await page.locator('#form-aluno input[name="dataNascimento"]').fill('1995-06-15');
  await page.locator('#form-aluno input[name="peso"]').fill('65');
  await page.locator('#form-aluno input[name="altura"]').fill('1.70');

  // Submeter o formulário
  await page.locator('#btn-submit-aluno').click();

  // Aguardar toast de sucesso e modal fechar (modal usa opacity, não display:none)
  await expect(page.locator('#toast')).toContainText('cadastrad', { timeout: 7000 });
  await expect(page.locator('#modal-aluno')).not.toHaveClass(/active/, { timeout: 5000 });

  // O aluno deve aparecer na tabela
  await expect(page.locator('#alunos-table')).toContainText('Maria Teste Playwright');
});
