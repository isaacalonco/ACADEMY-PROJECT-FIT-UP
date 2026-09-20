@echo off
echo ===================================================
echo   FIT UP - Executando Testes E2E (Playwright)
echo ===================================================
echo IMPORTANTE: O servidor backend deve estar ativo em http://localhost:8080
echo.
cd /d "%~dp0\e2e"
call npx playwright test
pause
