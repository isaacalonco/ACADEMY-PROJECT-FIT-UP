const { test, expect } = require('@playwright/test');

test('CT-E2E-04: Criação de plano e atualização no select de matrículas', async ({ page }) => {
  await page.goto('/');

  // Navegar para Planos
  await page.locator('li[data-page="planos"] a').click();
  await expect(page.locator('#page-planos')).toBeVisible();

  // Abrir modal de novo plano
  await page.locator('#page-planos .btn-primary').click();
  await expect(page.locator('#modal-plano')).toBeVisible();

  // Preencher dados do plano
  await page.locator('#form-plano input[name="nome"]').fill('Plano E2E Test');
  await page.locator('#form-plano input[name="valor"]').fill('149.90');

  // Salvar o plano
  await page.locator('#btn-submit-plano').click();

  // Modal deve fechar (usa opacity, não display:none)
  await expect(page.locator('#toast')).toContainText('cadastrad', { timeout: 7000 });
  await expect(page.locator('#modal-plano')).not.toHaveClass(/active/, { timeout: 5000 });

  // O plano deve aparecer na tabela de planos
  await expect(page.locator('#planos-table')).toContainText('Plano E2E Test');

  // Navegar para Alunos e verificar que o plano aparece no select
  await page.locator('li[data-page="alunos"] a').click();
  await page.locator('#page-alunos .btn-primary').click();
  await expect(page.locator('#modal-aluno')).toBeVisible();

  // O select de plano deve conter o plano recém criado
  const selectPlano = page.locator('#select-plano-aluno');
  await expect(selectPlano).toContainText('Plano E2E Test', { timeout: 5000 });
});
