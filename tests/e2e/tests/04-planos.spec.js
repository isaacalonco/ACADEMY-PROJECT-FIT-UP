const { test, expect } = require('@playwright/test');

test('CT-E2E-04: Criação de plano e atualização no select de matrículas', async ({ page }) => {
  await page.goto('/');

  // Autenticar com credenciais demo se necessário
  const authContainer = page.locator('#auth-container');
  if (await authContainer.isVisible()) {
    await page.locator('#btn-fill-demo').click();
    await page.locator('#btn-login-submit').click();
    await expect(authContainer).toHaveClass(/hidden/, { timeout: 10000 });
  }

  // Navegar para a página de Planos
  await page.locator('.sidebar-nav-item[data-page="planos"]').click();
  await expect(page.locator('#page-planos')).toBeVisible();

  // Abrir modal de novo plano
  await page.locator('#page-planos [data-modal="modal-plano"]').click();
  const modalPlano = page.locator('#modal-plano');
  await expect(modalPlano).toHaveClass(/active/);

  // Preencher dados do plano
  const timestamp = Date.now().toString().slice(-4);
  const nomePlano = `Plano VIP ${timestamp}`;
  await page.locator('#plano-nome').fill(nomePlano);
  await page.locator('#plano-valor').fill('159.90');

  // Salvar o plano
  await page.locator('#btn-submit-plano').click();

  // Modal deve fechar e toast de sucesso deve aparecer
  await expect(modalPlano).not.toHaveClass(/active/, { timeout: 8000 });
  await expect(page.locator('#toast-container')).toContainText('sucesso', { timeout: 8000 });

  // O plano deve aparecer na tabela de planos
  await expect(page.locator('#planos-table')).toContainText(nomePlano, { timeout: 8000 });

  // Navegar para Alunos e verificar que o plano aparece no select de matrícula
  await page.locator('.sidebar-nav-item[data-page="alunos"]').click();
  await page.locator('#page-alunos [data-modal="modal-aluno"]').click();
  await expect(page.locator('#modal-aluno')).toHaveClass(/active/);

  // O select de planos deve conter a nova opção
  const selectPlano = page.locator('#select-plano-aluno');
  await expect(selectPlano).toContainText(nomePlano, { timeout: 5000 });
});

