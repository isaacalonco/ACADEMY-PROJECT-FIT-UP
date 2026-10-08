@echo off
chcp 65001 >nul
cls
echo ========================================================
echo    FIT UP - Execução de Testes Unitários (JUnit 5)
echo ========================================================
echo.
cd /d "%~dp0\..\backend"
echo [1/2] Compilando e executando suíte JUnit 5 via Maven...
echo.
call mvn test
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================================
    echo  [SUCESSO] Todos os 11 testes unitarios passaram!
    echo ========================================================
) else (
    echo.
    echo ========================================================
    echo  [FALHA] Ocorreu erro em um ou mais testes unitarios.
    echo ========================================================
)
echo.
pause
