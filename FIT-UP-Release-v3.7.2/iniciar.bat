@echo off
cd /d "%~dp0"
title Sistema FIT UP - Iniciando
cls

echo ===================================================
echo             INICIANDO SISTEMA FIT UP
echo ===================================================
echo.

set "JAR=academy-1.0-SNAPSHOT-jar-with-dependencies.jar"

echo Pasta atual:
echo %cd%
echo.

if not exist "%JAR%" (
    echo [ERRO] O arquivo JAR nao foi encontrado nesta pasta!
    echo.
    echo Arquivo esperado:
    echo %JAR%
    echo.
    echo Coloque o arquivo .jar na mesma pasta deste iniciar.bat
    echo.
    pause
    exit /b
)

echo Verificando Java...
echo.

where java >nul 2>nul

if %errorlevel% equ 0 (
    echo [OK] Java encontrado no sistema.
    echo.
    echo Iniciando servidor...
    echo.
    echo Depois acesse no navegador:
    echo http://localhost:8080
    echo.
    java -jar "%JAR%"
    goto fim
)

echo [AVISO] Java nao encontrado pelo comando "java".
echo Tentando usar Java do IntelliJ...
echo.

if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.3\jbr\bin\java.exe" (
    echo [OK] Java do IntelliJ encontrado.
    echo.
    "C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.3\jbr\bin\java.exe" -jar "%JAR%"
    goto fim
)

if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\jbr\bin\java.exe" (
    echo [OK] Java do IntelliJ encontrado.
    echo.
    "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1.2\jbr\bin\java.exe" -jar "%JAR%"
    goto fim
)

if exist "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\java.exe" (
    echo [OK] Java do IntelliJ encontrado.
    echo.
    "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr\bin\java.exe" -jar "%JAR%"
    goto fim
)

echo [ERRO] Java nao foi encontrado.
echo.
echo Instale o Java 21 ou superior.
echo Depois tente abrir este iniciar.bat novamente.
echo.

:fim
echo.
echo ===================================================
echo Processo finalizado.
echo ===================================================
pause