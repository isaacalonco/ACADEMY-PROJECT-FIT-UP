@echo off
chcp 65001 >nul
cls
echo ========================================================
echo    FIT UP - Abrindo Relatorio Interativo com Videos
echo ========================================================
echo.
cd /d "%~dp0\e2e"
call cmd.exe /c "npx playwright show-report"
