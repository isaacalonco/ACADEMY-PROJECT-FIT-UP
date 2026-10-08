@echo off
chcp 65001 >nul
cls
echo ========================================================
echo   FIT UP - Testes com Navegador Visivel na Tela
echo ========================================================
echo.
echo DICA DE GRAVACAO:
echo Você pode usar a Barra de Jogos do Windows (Win + Alt + R)
echo ou OBS Studio para gravar a tela enquanto o teste roda!
echo.
pause
cd /d "%~dp0\e2e"
set HEADED=1
call cmd.exe /c "npx playwright test --headed"
echo.
pause
