# FIT UP — Sistema de Gestão de Academia

Sistema completo de gestão de academia desenvolvido em Java com POO e interface Web.

**Versão 100% Portátil**: O sistema utiliza um banco de dados embutido (SQLite). **Não é necessário instalar nenhum banco de dados para rodar este projeto!**

---

## Pré-requisitos

Para rodar o projeto em qualquer computador, você só precisa do:

1. **Java 21** (ou superior) instalado.
   - [Download do Java (Oracle)](https://www.oracle.com/java/technologies/downloads/)

> Após instalar, abra o terminal (Prompt de Comando) e verifique se o Java está configurado corretamente:
> ```cmd
> java --version
> ```

---

## Como Executar na Apresentação

O sistema está pronto para rodar de forma simples e rápida utilizando a pasta de **Release**.

### Método Recomendado (Via Script de Inicialização):
1. Navegue até a pasta `FIT-UP-Release-v3.7.2`.
2. Dê um **duplo clique** no arquivo `iniciar.bat`.
   - *Este script verifica se você possui o Java instalado, tenta localizar automaticamente o Java do IntelliJ caso necessário, e inicia o sistema de forma automatizada.*

### Método Manual (Via Terminal):
1. Abra o terminal na pasta `FIT-UP-Release-v3.7.2`.
2. Execute o comando:
   ```cmd
   java -jar academy-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

*(O banco de dados `academy.db` já está incluído e configurado na pasta de release).*

---

## Acessando o Sistema

Após executar o arquivo, o sistema estará disponível em **duas interfaces**:

| Interface | Como acessar |
|-----------|--------------|
| **Interface Web** | Abra o navegador e acesse: `http://localhost:8080` |
| **Terminal (CLI)** | O menu aparece no console de onde o programa foi executado |

---

## Para Desenvolvedores (Rodando pelo IntelliJ)

Se você quiser abrir o código e rodar pelo seu ambiente de desenvolvimento:

1. Abra a pasta principal do projeto no IntelliJ IDEA.
2. Aguarde o Maven sincronizar as dependências.
3. Clique com o botão direito no arquivo `backend/src/main/java/org/example/Main.java`.
4. Selecione **"Run 'Main.main()'"**.

---

## Estrutura do Projeto

```text
ACADEMY-POO-PROJECT/
├── backend/                    # Código Java (Backend)
│   ├── pom.xml                 
│   ├── target/                 # Arquivos compilados (.jar)
│   └── src/main/java/
│       ├── banco/              # Conexão (SQLite + Script de inicialização)
│       ├── entidades/          # Classes de domínio (POO: Herança, Polimorfismo)
│       ├── operacoes/          # Classes de negócio (CRUD)
│       └── org/example/        # Main + Servidor Web (ApiServer)
├── frontend/                   # Código HTML/CSS/JS (Frontend)
│   ├── index.html
│   ├── styles.css
│   └── app.js
```
