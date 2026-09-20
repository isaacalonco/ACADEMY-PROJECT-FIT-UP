# Suíte Centralizada de Testes — FIT UP

Esta pasta centraliza todos os testes automatizados do sistema **FIT UP**, organizados por nível e tecnologia de teste.

---

## Estrutura de Diretórios

```text
tests/
├── junit/                       # Testes Unitários e de Integração (Java / JUnit 5)
│   ├── entidades/
│   │   └── AlunoTest.java       # Testes de regras de negócio da entidade Aluno
│   └── org/example/
│       └── ValidadorTest.java   # Testes dos validadores (CPF, Email, Telefone, etc.)
│
├── e2e/                         # Testes Ponta a Ponta / End-to-End (Playwright)
│   ├── tests/
│   │   ├── 01-auth.spec.js      # Testes de autenticação e controle de acesso
│   │   ├── 02-alunos.spec.js    # Testes de cadastro e listagem de alunos
│   │   ├── 03-validation.spec.js# Testes de validação de formulários na UI
│   │   ├── 04-planos.spec.js    # Testes de planos de matrícula
│   │   └── 05-dashboard.spec.js # Testes dos cards, gráficos e estatísticas
│   ├── playwright.config.js     # Configuração do Playwright
│   └── package.json             # Dependências e scripts de execução
│
├── executar_junit.bat           # Atalho para rodar testes JUnit
└── executar_e2e.bat             # Atalho para rodar testes E2E (Playwright)
```

---

## Como Executar os Testes

### 1. Testes Unitários (JUnit 5)

Os testes JUnit utilizam o Maven configurado no backend.

#### Via Terminal / Linha de Comando:
```bash
# A partir da raiz do projeto:
mvn test -f backend/pom.xml

# Ou navegando até a pasta backend:
cd backend
mvn test
```

#### Via Script Rápido (Windows):
Dê um duplo clique ou execute no terminal:
```cmd
tests\executar_junit.bat
```

---

### 2. Testes End-to-End (Playwright)

Os testes E2E validam os fluxos da interface Web integrada à API.

#### Pré-requisitos:
1. Certifique-se de que o backend está em execução:
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main" -f backend/pom.xml
   ```
   *(Acesse http://localhost:8080 para confirmar)*

#### Executando os testes:
```bash
cd tests/e2e
npm test
```

Para abrir o relatório interativo ou interface visual do Playwright:
```bash
npm run test:ui
```
