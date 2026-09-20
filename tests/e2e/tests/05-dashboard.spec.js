const { test, expect } = require('@playwright/test');

test('CT-E2E-05: Lançamento de pagamento e incremento de indicadores', async ({ page }) => {
  await page.goto('/');

  // Primeiro: criar um plano para poder registrar pagamento
  await page.locator('li[data-page="planos"] a').click();
  await page.locator('#page-planos .btn-primary').click();
  await page.locator('#form-plano input[name="nome"]').fill('Plano Dashboard Test');
  await page.locator('#form-plano input[name="valor"]').fill('99.90');
  await page.locator('#btn-submit-plano').click();
  await expect(page.locator('#modal-plano')).not.toBeVisible({ timeout: 5000 });

  // Criar um aluno vinculado ao plano
  await page.locator('li[data-page="alunos"] a').click();
  await page.locator('#page-alunos .btn-primary').click();
  await expect(page.locator('#modal-aluno')).toBeVisible();
  await page.locator('#form-aluno input[name="nome"]').fill('Carlos Dashboard E2E');
  await page.locator('#form-aluno input[name="cpf"]').fill('987.654.321-00');
  await page.locator('#form-aluno input[name="email"]').fill('carlos.dashboard@fitup.com');
  await page.locator('#form-aluno input[name="dataNascimento"]').fill('1988-03-20');
  await page.locator('#form-aluno input[name="peso"]').fill('80');
  await page.locator('#form-aluno input[name="altura"]').fill('1.80');
  // Selecionar o plano criado
  const planoOptValue = await page.locator('#select-plano-aluno option', { hasText: 'Plano Dashboard Test' }).getAttribute('value');
  await page.locator('#select-plano-aluno').selectOption(planoOptValue);
  await page.locator('#btn-submit-aluno').click();
  await expect(page.locator('#modal-aluno')).not.toBeVisible({ timeout: 5000 });

  // Verificar stat-alunos no Dashboard incrementado
  await page.locator('li[data-page="dashboard"] a').click();
  const statAlunos = page.locator('#stat-alunos');
  await expect(statAlunos).not.toHaveText('0');

  // Navegar para Pagamentos e registrar um pagamento
  await page.locator('li[data-page="pagamentos"] a').click();
  await page.locator('#page-pagamentos .btn-primary').click();
  await expect(page.locator('#modal-pagamento')).toBeVisible();

  // Selecionar o aluno no select
  const selectAluno = page.locator('#select-aluno-pagamento');
  await selectAluno.selectOption({ label: 'Carlos Dashboard E2E' });

  // Aguardar o valor ser preenchido automaticamente pelo plano
  await expect(page.locator('#pagamento-valor')).not.toHaveValue('', { timeout: 3000 });

  // Submeter
  await page.locator('#btn-submit-pagamento').click();
  await expect(page.locator('#modal-pagamento')).not.toBeVisible({ timeout: 5000 });

  // Dashboard deve refletir o pagamento nas Receitas
  await page.locator('li[data-page="dashboard"] a').click();
  const statPagamentos = page.locator('#stat-pagamentos');
  await expect(statPagamentos).not.toHaveText('0');
});
