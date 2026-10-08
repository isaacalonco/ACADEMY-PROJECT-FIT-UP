@echo off
chcp 65001 >nul
cls
echo ========================================================
echo    FIT UP - Testes E2E / Interface (Gravando Videos)
echo ========================================================
echo.
cd /d "%~dp0\e2e"
echo [1/2] Executando testes Playwright e gerando gravacoes em video...
echo.
call cmd.exe /c "npx playwright test"
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================
    echo  [SUCESSO] Todos os testes E2E e de Interface passaram!
    echo ========================================================
    echo.
    echo  Os videos foram gravados com sucesso na pasta:
    echo  tests\e2e\test-results\
    echo.
    echo  Para abrir o relatorio interativo com os videos, execute:
    echo  tests\abrir_relatorio_testes.bat
    echo ========================================================
) else (
    echo.
    echo ========================================================
    echo  [FALHA] Verifique as falhas no terminal.
    echo ========================================================
)
echo.
pause
