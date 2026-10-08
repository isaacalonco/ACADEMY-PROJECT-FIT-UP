const { test, expect } = require('@playwright/test');

test('CT-E2E-01: Fluxo de autenticação e navegação no Dashboard', async ({ page }) => {
  await page.goto('/');

  // 1. Tela de login inicial deve estar visível
  const authContainer = page.locator('#auth-container');
  await expect(authContainer).toBeVisible();

  // 2. Preencher com acesso demo e submeter
  await page.locator('#btn-fill-demo').click();
  await page.locator('#btn-login-submit').click();

  // 3. Após autenticação, tela de login deve ser ocultada
  await expect(authContainer).toHaveClass(/hidden/, { timeout: 10000 });

  // 4. Sidebar deve exibir o logo FIT UP e status online
  await expect(page.locator('.sidebar-title')).toContainText('FIT UP');
  await expect(page.locator('.sidebar-profile')).toBeVisible();

  // 5. O Dashboard deve estar ativo com os cards de KPIs
  await expect(page.locator('#page-dashboard')).toBeVisible();
  await expect(page.locator('#stat-alunos')).toBeVisible();
  await expect(page.locator('#stat-instrutores')).toBeVisible();
  await expect(page.locator('#stat-planos')).toBeVisible();
  await expect(page.locator('#stat-pagamentos')).toBeVisible();

  // 6. Navegação entre páginas: Alunos
  await page.locator('.sidebar-nav-item[data-page="alunos"]').click();
  await expect(page.locator('#page-alunos')).toBeVisible();
  await expect(page.locator('#page-dashboard')).not.toBeVisible();

  // 7. Voltar ao Dashboard
  await page.locator('.sidebar-nav-item[data-page="dashboard"]').click();
  await expect(page.locator('#page-dashboard')).toBeVisible();
});

