@echo off
chcp 65001 >nul
title FIT UP - Sistema de Gestão de Academia

echo =========================================================
echo       FIT UP — Sistema de Gestão de Academia
echo =========================================================
echo.

:: 1. Verificar instalação do Java
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Java não foi encontrado no PATH do sistema.
    echo Por favor, instale o Java 21+ ou configure a variável JAVA_HOME.
    echo Download: https://www.oracle.com/java/technologies/downloads/
    echo.
    pause
    exit /b 1
)

:: 2. Executar o JAR montado com todas as dependências
if exist "%~dp0backend\target\academy-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo [INFO] Iniciando FIT UP via JAR executável...
    echo [INFO] Interface Web: http://localhost:8080
    echo [INFO] Interface CLI: Digite as opções no console abaixo
    echo.
    cd /d "%~dp0backend"
    java -jar target\academy-1.0-SNAPSHOT-jar-with-dependencies.jar
    pause
    exit /b 0
)

:: 3. Alternativa via Maven caso o JAR não esteja compilado
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 (
    echo [INFO] Compilando e iniciando via Maven...
    cd /d "%~dp0backend"
    mvn compile exec:java -Dexec.mainClass="org.example.Main"
    pause
    exit /b 0
)

echo [ERRO] Arquivo executável JAR não encontrado em backend\target.
echo Execute 'mvn package' dentro da pasta backend para gerar o JAR.
echo.
pause
