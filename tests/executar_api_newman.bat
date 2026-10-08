@echo off
chcp 65001 >nul
cls
echo ========================================================
echo    FIT UP - Testes de API Automatizados (Postman / Newman)
echo ========================================================
echo.
cd /d "%~dp0"
echo [1/2] Executando postman_collection.json via Newman na nuvem (Railway)...
echo.
call cmd.exe /c "npx newman run postman_collection.json"
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================
    echo  [SUCESSO] Todos os 6 testes de API / 14 assercoes passaram!
    echo ========================================================
) else (
    echo.
    echo ========================================================
    echo  [FALHA] Verifique as falhas da API acima.
    echo ========================================================
)
echo.
pause
