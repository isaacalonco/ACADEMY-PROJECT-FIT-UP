const { test, expect } = require('@playwright/test');

test('CT-E2E-01: Fluxo de autenticação e redirecionamento para Dashboard', async ({ page }) => {
  await page.goto('/');

  // A página deve carregar e exibir a sidebar com o logo FIT UP
  await expect(page.locator('.logo h1')).toContainText('FIT UP');

  // O Dashboard deve estar ativo por padrão
  await expect(page.locator('#page-dashboard')).toBeVisible();

  // Os cards de estatísticas devem estar presentes
  await expect(page.locator('#stat-alunos')).toBeVisible();
  await expect(page.locator('#stat-instrutores')).toBeVisible();
  await expect(page.locator('#stat-planos')).toBeVisible();
  await expect(page.locator('#stat-pagamentos')).toBeVisible();

  // Navegação: clicar em Alunos deve exibir a seção correta
  await page.locator('li[data-page="alunos"] a').click();
  await expect(page.locator('#page-alunos')).toBeVisible();
  await expect(page.locator('#page-dashboard')).not.toBeVisible();

  // Voltar ao Dashboard
  await page.locator('li[data-page="dashboard"] a').click();
  await expect(page.locator('#page-dashboard')).toBeVisible();
});
