# 🏋️ Academy System — Sistema de Gestão de Academia

Sistema completo de gestão de academia desenvolvido em Java com POO e interface Web.

**Versão 100% Portátil**: O sistema utiliza um banco de dados embutido (SQLite). **Não é necessário instalar nenhum banco de dados para rodar este projeto!**

---

## 📋 Pré-requisitos

Para rodar o projeto em qualquer computador, você só precisa do:

1. **Java 21** (ou superior) instalado.
   - [Download do Java (Oracle)](https://www.oracle.com/java/technologies/downloads/)

> ⚠️ Após instalar, abra o terminal (Prompt de Comando) e verifique se o Java está configurado corretamente:
> ```cmd
> java --version
> ```

---

## 🚀 Como Executar na Apresentação

O sistema já está pronto para rodar. Basta utilizar o arquivo `.jar` executável.

1. Navegue até a pasta `backend/target/`.
2. Dê um **duplo clique** no arquivo `academy-1.0-SNAPSHOT-jar-with-dependencies.jar` **OU** abra o terminal nessa pasta e digite:
   ```cmd
   java -jar academy-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

*(O banco de dados `academy.db` será criado automaticamente na pasta de onde você executou o arquivo, na primeira vez que o sistema rodar!)*

---

## 🌐 Acessando o Sistema

Após executar o arquivo, o sistema estará disponível em **duas interfaces**:

| Interface | Como acessar |
|-----------|--------------|
| **Interface Web** | Abra o navegador e acesse: `http://localhost:8080` |
| **Terminal (CLI)** | O menu aparece no console de onde o programa foi executado |

---

## 💻 Para Desenvolvedores (Rodando pelo IntelliJ)

Se você quiser abrir o código e rodar pelo seu ambiente de desenvolvimento:

1. Abra a pasta principal do projeto no IntelliJ IDEA.
2. Aguarde o Maven sincronizar as dependências.
3. Clique com o botão direito no arquivo `backend/src/main/java/org/example/Main.java`.
4. Selecione **"Run 'Main.main()'"**.

---

## 📁 Estrutura do Projeto

```text
ACADEMY-POO-PROJECT/
├── backend/                    # Código Java
│   ├── pom.xml                 
│   ├── target/                 # Arquivos compilados (.jar)
│   └── src/main/java/
│       ├── banco/              # Conexão (SQLite + Script de inicialização)
│       ├── entidades/          # Classes de domínio (POO: Herança, Polimorfismo)
│       ├── operacoes/          # Classes de negócio (CRUD)
│       └── org/example/        # Main + Servidor Web (ApiServer)
├── frontend/                   # Interface Web (HTML/CSS/JS)
│   ├── index.html
│   ├── styles.css
│   └── app.js
```
