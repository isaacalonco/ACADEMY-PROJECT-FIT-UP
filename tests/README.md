# Central de Testes Automatizados — FIT UP Academy

Este repositório possui uma suíte completa de testes automatizados cobrindo **Testes Unitários (Backend)**, **Testes de Integração e Interface (E2E)** e **Gravações em Vídeo de Execução**.

---

## 📋 Resumo da Cobertura de Testes

| Categoria | Tecnologia | Quantidade | O que valida | Localização |
|---|---|---|---|---|
| **Unitários** | JUnit 5 + Maven | 11 casos de teste | Regras de validação (CPF, E-mail, datas, altura, peso, limites), cálculo de IMC e regras de negócio | [`tests/junit/`](junit/) |
| **Interface / E2E** | Playwright (Chromium) | 5 casos de teste | Fluxo de autenticação, cadastro de alunos, bloqueios visuais, gestão de planos e financeiro no Dashboard | [`tests/e2e/tests/`](e2e/tests/) |
| **Gravação em Vídeo** | Playwright Video (`.webm`) | 5 vídeos automáticos | Gravação em tempo real do navegador executando cada teste da interface | [`tests/e2e/test-results/`](e2e/test-results/) |

---

## 🎬 Como Gravar os Testes para Entrega

### Opção 1: Vídeos Gerados Automaticamente pelo Sistema (Mais Prático)
O Playwright foi configurado com `video: 'on'` e grava um arquivo de vídeo individual para cada teste:
1. Dê um duplo clique no arquivo:
   ```cmd
   tests\executar_e2e_com_video.bat
   ```
2. Após a execução, os vídeos em formato `.webm` estarão salvos dentro das pastas em:
   ```text
   tests/e2e/test-results/
   ├── 01-auth-.../video.webm
   ├── 02-alunos-.../video.webm
   ├── 03-validation-.../video.webm
   ├── 04-planos-.../video.webm
   └── 05-dashboard-.../video.webm
   ```
3. Para visualizar o relatório interativo com os vídeos e capturas de tela no navegador:
   ```cmd
   tests\abrir_relatorio_testes.bat
   ```

---

### Opção 2: Gravação da Tela ao Vivo (Apresentação / Demonstração)
Se o seu professor exigir que você grave a sua tela demonstrando a execução:

1. **Testes Unitários:**
   - Inicie o gravador de tela do Windows com o atalho: **`Windows + Alt + R`** (ou use OBS Studio / Ferramenta de Captura do Windows).
   - Dê dois cliques em:
     ```cmd
     tests\executar_junit.bat
     ```
   - O terminal exibirá a compilação do Maven e a aprovação de todos os 11 testes unitários (`BUILD SUCCESS`).
   - Pressione **`Windows + Alt + R`** para finalizar a gravação.

2. **Testes de Interface / E2E com Navegador Aberto:**
   - Inicie o gravador de tela (**`Windows + Alt + R`**).
   - Dê dois cliques em:
     ```cmd
     tests\executar_e2e_visual.bat
     ```
   - Uma janela real do navegador Google Chrome/Chromium abrirá na sua tela e executará todas as ações automaticamente (login, cliques, digitação, modais e validações).
   - Finalize a gravação.

---

## 🛠️ Comandos Manuais via Terminal

Caso prefira rodar pelo Prompt de Comando ou PowerShell:

```bash
# Rodar testes unitários (JUnit)
mvn test -f backend/pom.xml

# Rodar testes E2E com gravação de vídeo
cd tests/e2e
npx playwright test

# Rodar testes E2E com navegador visível na tela
cd tests/e2e
npx playwright test --headed

# Abrir relatório visual dos testes
cd tests/e2e
npx playwright show-report
```
