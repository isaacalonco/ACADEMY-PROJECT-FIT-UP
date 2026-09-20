@echo off
echo ===================================================
echo   FIT UP - Executando Testes Unitarios (JUnit 5)
echo ===================================================
cd /d "%~dp0\..\backend"
mvn test
pause
