# FIT UP — Sistema de Gestão de Academia

Sistema completo e integrado de gestão de academia com avaliação física, desenvolvido com arquitetura modular, backend em **Java (POO)**, interface Web responsiva moderna e banco de dados relacional.

> **Portabilidade Total**: O sistema inclui banco de dados local embutido (**SQLite**). Não é necessário instalar nenhum Sistema Gerenciador de Banco de Dados (SGBD) para executar a aplicação!  
> Para a disciplina de **Banco de Dados (UCB)**, os scripts físicos equivalentes e compatíveis com **PostgreSQL / ANSI SQL** estão integralmente fornecidos na pasta [`sql/`](sql/).

---

## Como Executar o Sistema

### Pré-requisitos
- **Java 21** (ou superior) instalado e configurado no PATH do sistema.
  - [Download do Java (Oracle)](https://www.oracle.com/java/technologies/downloads/)
  - Verifique no terminal: `java --version`

---

### Método 1: Inicialização Rápida (Recomendado — 1 Clique no Windows)
1. Na raiz do projeto, dê um duplo clique no arquivo:
   ```text
   iniciar.bat
   ```
2. O script detecta automaticamente o Java, carrega o executável com todas as dependências e inicializa o servidor web e o console interativo.

---

### Método 2: Execução Manual via Terminal (JAR Executável)
Abra o Prompt de Comando ou PowerShell na raiz do projeto e execute:
```cmd
java -jar backend/target/academy-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

### Método 3: Execução via Maven
Caso prefira compilar o código-fonte diretamente:
```cmd
mvn compile exec:java -Dexec.mainClass="org.example.Main" -f backend/pom.xml
```

---

### Método 4: Execução pelo IntelliJ IDEA
1. Abra a pasta `ACADEMY-PROJECT-FIT-UP` no IntelliJ IDEA.
2. Aguarde a indexação e sincronização das dependências Maven.
3. Navegue até [`backend/src/main/java/org/example/Main.java`](backend/src/main/java/org/example/Main.java).
4. Clique com o botão direito e selecione **"Run 'Main.main()'"**.

---

## Acessando o Sistema

Ao iniciar, o FIT UP disponibiliza **duas interfaces sincronizadas**:

| Interface | Endereço / Acesso | Descrição |
|-----------|-------------------|-----------|
| **Interface Web** | `http://localhost:8080` | Painel gráfico moderno com estatísticas, gráficos, cadastro de alunos, planos, pagamentos e login administrativo. |
| **Terminal (CLI)** | Janela do Console / CMD | Menu interativo em modo texto com controle completo das operações CRUD. |

### Credenciais Administrativas da Web:
- **E-mail:** `admin@fitup.com`
- **Senha:** `Senha@123`

---

## Estrutura do Projeto

```text
ACADEMY-PROJECT-FIT-UP/
├── iniciar.bat                 # Script de inicialização rápida (1 clique no Windows)
├── README.md                   # Documentação principal do repositório
├── backend/                    # Código-fonte Java (Backend)
│   ├── pom.xml                 # Configurações Maven, dependências e plugins
│   ├── academy.db              # Banco de dados local embutido (SQLite)
│   ├── target/                 # Binários compilados (.jar com dependências)
│   └── src/main/java/
│       ├── banco/              # Conexão SQLite e criação automática de tabelas
│       ├── entidades/          # Modelos de domínio (POO: Aluno, Instrutor, Plano, Pagamento, etc.)
│       ├── operacoes/          # Regras de negócio e persistência de dados (CRUD)
│       └── org/example/        # Ponto de entrada (Main), Validador e Servidor HTTP (ApiServer)
├── frontend/                   # Interface de Usuário Web (SPA)
│   ├── index.html              # Estrutura semântica dos dashboards e formulários
│   ├── styles.css              # Design system moderno, responsivo e com tema escuro
│   └── app.js                  # Lógica de integração cliente-servidor (Fetch API assíncrona)
├── tests/                      # Suíte Centralizada de Testes Automatizados
│   ├── README.md               # Instruções detalhadas de execução dos testes
│   ├── executar_junit.bat      # Script para rodar a suíte JUnit 5
│   ├── executar_e2e.bat        # Script para rodar a suíte Playwright
│   ├── junit/                  # 11 Testes Unitários e de Negócio (Java / JUnit 5)
│   └── e2e/                    # Testes End-to-End ponta a ponta (Playwright)
├── docs/                       # Documentação Acadêmica Oficial em PDF
│   ├── README.md               # Central de documentação da Etapa 1
│   ├── FIT_UP_Relatorio_Etapa1.pdf            # Relatório Técnico Consolidado (Artefatos A1 a A5)
│   ├── FIT_UP_Documento_de_Visao_do_Sistema.pdf  # Documento de Visão e Requisitos
│   ├── FIT_UP_Dicionario_de_Dados.pdf        # Dicionário de Dados Oficial (Anexo A)
│   ├── modelo-logico.pdf                     # Modelo Lógico Relacional (3FN e BCNF)
│   └── mer-conceitual.pdf                    # Modelo Entidade-Relacionamento Conceitual
└── sql/                        # Scripts Físicos de Banco de Dados (PostgreSQL / ANSI)
    ├── 01_ddl.sql              # DDL: Criação do esquema físico e restrições nomeadas
    ├── 02_carga.sql            # DML: Carga com 50 indivíduos e 114 pagamentos
    └── 03_consultas.sql        # 15 Consultas SQL comentadas com perguntas de negócio
```

---

## Suíte de Testes Automatizados

O sistema conta com suíte automatizada dividida em dois níveis de granularidade:

### 1. Testes Unitários (JUnit 5) — 11 Casos de Teste
Localizados em [`tests/junit/`](tests/junit/), cobrem:
- **`ValidadorTest`**: Validação de formato e dígitos de CPF, expressões regulares de e-mail, bloqueio de datas de nascimento futuras, tamanho mínimo de nome, limites biométricos de altura/peso, validação de preços de planos e normalização de status de pagamento.
- **`AlunoTest`**: Precisão matemática do cálculo de IMC, faixas da OMS (Abaixo do peso, Peso normal, Sobrepeso, Obesidade) e prevenção contra divisão por zero com altura zerada.

**Como rodar:**
```cmd
tests\executar_junit.bat
# ou via Maven:
mvn test -f backend/pom.xml
```

### 2. Testes Ponta a Ponta (Playwright) — E2E
Localizados em [`tests/e2e/`](tests/e2e/), validam a interface Web integrada à API:
- `01-auth.spec.js`: Fluxo de autenticação, feedback de credenciais inválidas e persistência de sessão.
- `02-alunos.spec.js`: Cadastro, listagem e persistência de novos membros.
- `03-validation.spec.js`: Validação em tempo real dos campos de formulário na UI.
- `04-planos.spec.js`: Gestão de planos e verificação de valores.
- `05-dashboard.spec.js`: Atualização dinâmica dos cards e métricas financeiras.

**Como rodar:**
1. Inicie o sistema via `iniciar.bat` ou `java -jar ...`.
2. Em outro terminal, execute:
```cmd
tests\executar_e2e.bat
# ou:
cd tests/e2e && npm test
```
