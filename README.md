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
ACADEMY-PROJECT-FIT-UP/
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
└── sql/                        # Scripts Físicos de Banco de Dados (PostgreSQL / ANSI)
    ├── 01_ddl.sql              # DDL: Criação do esquema com restrições nomeadas
    ├── 02_carga.sql            # DML: Carga com >50 pessoas e >110 pagamentos
    └── 03_consultas.sql        # 15 Consultas comentadas (Básicas, Junções e Avançadas)
```

---

## 📚 1. Trabalho Final de Teste de Software

Todo o material acadêmico exigido pelo roteiro de testes está disponível na pasta [`docs/`](docs/README.md) nos formatos **Markdown (`.md`)** e **Microsoft Word (`.docx`)**:

1. **[Documento de Visão do Sistema](docs/01-documento-de-visao.md)** ([Word .docx](docs/01-documento-de-visao.docx))
2. **[Requisitos Ágeis & Cenários BDD](docs/02-requisitos-e-bdd.md)** ([Word .docx](docs/02-requisitos-e-bdd.docx))
3. **[Plano de Testes de Software](docs/03-plano-de-testes.md)** ([Word .docx](docs/03-plano-de-testes.docx))
4. **[Catálogo de Casos de Teste (26 Casos)](docs/04-casos-de-teste.md)** ([Word .docx](docs/04-casos-de-teste.docx))
5. **[Evidências de Execução e Defeitos](docs/05-evidencias-e-defeitos.md)** ([Word .docx](docs/05-evidencias-e-defeitos.docx))
6. 🏆 **[Relatório Final Consolidado para Entrega](docs/RELATORIO_FINAL_TESTE_SOFTWARE.md)** ([Word .docx](docs/RELATORIO_FINAL_TESTE_SOFTWARE.docx))

---

## 🗄️ 2. Projeto Final de Laboratório de Banco de Dados (UCB)

Desenvolvido para a disciplina **Laboratório de Banco de Dados (GPE17M40083)** da **Universidade Católica de Brasília (UCB)** sob orientação do **Prof. Samuel Novais Moura Júnior**.

Tema oficial homologado: **Academia com Avaliação Física** (Planos, contratos, treinos e série histórica de medidas antropométricas).

A documentação completa está na pasta [`docs_banco_de_dados/`](docs_banco_de_dados/README.md) em **Markdown (`.md`)** e **Word (`.docx`)**:

1. **[Escopo e 20 Regras de Negócio (RN01 a RN20)](docs_banco_de_dados/01_escopo_e_regras_de_negocio.md)** ([Word .docx](docs_banco_de_dados/01_escopo_e_regras_de_negocio.docx)) — *Artefato A1*
2. **[Modelo Entidade-Relacionamento Conceitual (MER)](docs_banco_de_dados/02_modelo_conceitual_mer.md)** ([Word .docx](docs_banco_de_dados/02_modelo_conceitual_mer.docx)) — *Artefato A2*
3. **[Dicionário de Dados Conceitual Oficial](docs_banco_de_dados/03_dicionario_de_dados.md)** ([Word .docx](docs_banco_de_dados/03_dicionario_de_dados.docx)) — *Artefato A3 (Anexo A)*
4. **[Modelo Lógico Relacional e Normalização (3FN e BCNF)](docs_banco_de_dados/04_modelo_logico_e_normalizacao.md)** ([Word .docx](docs_banco_de_dados/04_modelo_logico_e_normalizacao.docx)) — *Artefatos A4 e A5*
5. **[Segurança, Transações, Otimização EXPLAIN e Backup](docs_banco_de_dados/05_seguranca_transacoes_e_desempenho.md)** ([Word .docx](docs_banco_de_dados/05_seguranca_transacoes_e_desempenho.docx)) — *Artefatos B1 a B7*
6. 🏆 **[Relatório Final Consolidado de Banco de Dados](docs_banco_de_dados/RELATORIO_FINAL_BANCO_DE_DADOS.md)** ([Word .docx](docs_banco_de_dados/RELATORIO_FINAL_BANCO_DE_DADOS.docx)) — *Relatório Mestre Integrado*

### Scripts Físicos Executáveis:
- 📜 **[`sql/01_ddl.sql`](sql/01_ddl.sql)**: Criação do esquema físico, chaves, especialização e restrições nomeadas (`pk_`, `uq_`, `fk_`, `ck_`, `idx_`).
- 📜 **[`sql/02_carga.sql`](sql/02_carga.sql)**: Carga de dados realistas com casos de contorno, mais de 50 pessoas e mais de 110 pagamentos.
- 📜 **[`sql/03_consultas.sql`](sql/03_consultas.sql)**: 15 Consultas comentadas (5 Básicas, 5 Junções/Agregação, 5 Avançadas com subconsultas correlacionadas e `EXISTS`).

