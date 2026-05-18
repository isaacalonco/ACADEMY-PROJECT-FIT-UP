# ACADEMY SYSTEM — Sistema de Gestão de Academia

**Trabalho Prático — Programação Orientada a Objetos (POO)**

---

## 2. Introdução

O presente trabalho consiste no desenvolvimento de um sistema completo de gerenciamento de academia, denominado **Academy System**, utilizando a linguagem de programação Java e os princípios fundamentais da Programação Orientada a Objetos (POO).

O sistema foi criado para resolver um problema real e comum em academias de pequeno e médio porte: a dificuldade de gerenciar alunos, instrutores, planos e pagamentos de forma organizada, centralizada e eficiente. Muitas academias ainda utilizam planilhas manuais ou cadernos para controlar essas informações, o que resulta em erros, perda de dados e falta de agilidade.

O Academy System oferece duas interfaces de uso: uma **interface de linha de comando (CLI)** para operação direta no terminal, e uma **interface web gráfica** acessível pelo navegador, ambas conectadas ao mesmo banco de dados embutido (SQLite). Isso permite que o sistema seja portátil e não dependa de instalações complexas.

---

## 3. Objetivo do Projeto

### 3.1. Objetivo Geral

Desenvolver um sistema funcional de gestão de academia utilizando Java e os quatro pilares da Programação Orientada a Objetos, contemplando operações completas de CRUD (Create, Read, Update, Delete) em múltiplos módulos.

### 3.2. Objetivos Específicos

- Implementar no mínimo 4 módulos (Alunos, Instrutores, Planos e Pagamentos), cada um com CRUD completo.
- Aplicar corretamente os conceitos de **Abstração**, **Herança**, **Polimorfismo** e **Encapsulamento**.
- Criar uma interface de linha de comando (CLI) com menus interativos e navegação intuitiva.
- Desenvolver uma interface web moderna como complemento visual do sistema.
- Utilizar banco de dados embutido SQLite para persistência dos dados de forma portátil.
- Garantir a integridade dos dados por meio de relacionamentos entre tabelas (chaves estrangeiras).

---

## 4. Justificativa

A gestão eficiente de uma academia envolve o controle simultâneo de diversas informações: dados pessoais dos alunos, especialidades dos instrutores, tipos e valores de planos, e o acompanhamento financeiro dos pagamentos.

Um sistema digital e integrado elimina retrabalho, reduz erros humanos e permite consultas rápidas. Além disso, o projeto serve como um excelente exercício prático para consolidar os conceitos de POO estudados em sala de aula, pois exige a modelagem de entidades do mundo real, o uso de herança entre classes, a implementação de polimorfismo e o encapsulamento adequado dos dados.

---

## 5. Fundamentação Teórica

### 5.1. Programação Orientada a Objetos (POO)

A POO é um paradigma de programação que organiza o software em torno de **objetos** — entidades que combinam dados (atributos) e comportamentos (métodos). Os quatro pilares fundamentais são:

**Abstração:** Consiste em representar apenas as características essenciais de uma entidade do mundo real. No projeto, a classe `Pessoa` é uma abstração que captura os atributos comuns a qualquer pessoa no sistema.

**Encapsulamento:** É o princípio de proteger os dados internos de um objeto, permitindo acesso apenas através de métodos controlados (getters e setters). 

**Herança:** Permite que uma classe (filha) herde atributos e métodos de outra classe (mãe), promovendo reutilização de código. No projeto, `Aluno` e `Instrutor` herdam da classe abstrata `Pessoa`.

**Polimorfismo:** Permite que um mesmo método tenha comportamentos diferentes dependendo da classe que o implementa. No projeto, o método abstrato `getDescricao()` é implementado de forma diferente em `Aluno` e em `Instrutor`.

### 5.2. Arquitetura em Camadas

O sistema segue uma arquitetura em camadas que separa responsabilidades:
- **Camada de Entidades:** Classes que representam os objetos do domínio.
- **Camada de Operações:** Classes que contêm a lógica de negócio e as queries SQL.
- **Camada de Banco:** Classe responsável pela conexão com o banco de dados.
- **Camada de Apresentação:** A interface CLI e a interface Web.

---

## 6. Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|---|---|---|
| **Java** | 21 (LTS) | Linguagem principal do backend |
| **SQLite** | 3.x | Banco de dados embutido |
| **JDBC** | sqlite-jdbc 3.45 | Conexão Java ↔ SQLite |
| **Gson** | 2.10.1 | Serialização/deserialização JSON |
| **Maven** | 3.x | Gerenciamento de dependências e build |
| **HttpServer** | JDK nativo | Servidor HTTP embutido (sem framework externo) |
| **HTML5 / CSS3 / JavaScript** | — | Interface web do frontend |
| **IntelliJ IDEA** | — | IDE de desenvolvimento |

---

## 7. Desenvolvimento do Projeto

O desenvolvimento seguiu as seguintes etapas:
1. **Planejamento:** Definição das entidades e modelo de dados.
2. **Modelagem:** Implementação das classes Java com herança e polimorfismo.
3. **Persistência:** Integração com SQLite e queries automáticas.
4. **Camada de Operações:** Implementação dos métodos CRUD (Create, Read, Update, Delete).
5. **Interface CLI:** Menu interativo `Main.java`.
6. **API REST:** Servidor HTTP nativo.
7. **Interface Web:** Criação do painel web.

---

## 8. Estrutura do Código

```
ACADEMY-POO-PROJECT/
├── backend/
│   ├── pom.xml                          # Dependências Maven
│   └── src/main/java/
│       ├── banco/
│       │   └── Conexao.java             # Gerencia a conexão com SQLite
│       ├── entidades/
│       │   ├── Pessoa.java              # Classe abstrata base
│       │   ├── Aluno.java               # Subclasse de Pessoa
│       │   ├── Instrutor.java           # Subclasse de Pessoa
│       │   ├── Plano.java               # Entidade independente
│       │   ├── Pagamento.java           # Entidade independente
│       │   └── Matricula.java           # Relacionamento Aluno ↔ Plano
│       ├── operacoes/
│       │   ├── AlunoOperacoes.java      # CRUD do Aluno
│       │   ├── InstrutorOperacoes.java  # CRUD do Instrutor
│       │   ├── PlanoOperacoes.java      # CRUD do Plano
│       │   └── PagamentoOperacoes.java  # CRUD do Pagamento
│       └── org/example/
│           ├── Main.java                # Interface CLI
│           └── ApiServer.java           # Servidor REST
├── frontend/
│   ├── index.html                       # Página web principal
│   ├── styles.css                       # CSS do painel
│   └── app.js                           # JavaScript (Fetch API)
```

---

## 9. Código-Fonte Comentado

### 9.1. Conexão com SQLite (`Conexao.java`)

```java
package banco;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
    // String de conexão aponta para um arquivo local "academy.db"
    private static final String URL = "jdbc:sqlite:academy.db";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception erro) {
            throw new RuntimeException("Erro ao conectar: " + erro.getMessage());
        }
    }
}
```

### 9.2. Classe Abstrata (`Pessoa.java`)

```java
public abstract class Pessoa {
    protected String nome;
    protected String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    // Implementação abstrata
    public abstract String getDescricao();
}
```

### 9.3. Classe Filha (`Aluno.java`)

```java
public class Aluno extends Pessoa {
    private double peso;
    private double altura;

    public Aluno(String nome, String cpf, double peso, double altura) {
        super(nome, cpf);
        this.peso = peso;
        this.altura = altura;
    }

    @Override
    public String getDescricao() {
        return "Aluno: " + nome + " | IMC: " + (peso / (altura * altura));
    }
}
```

---

## 10. Funcionamento e Testes

O sistema permite criar, ler, atualizar e deletar informações de todas as entidades. Como utilizamos o **SQLite**, todos os dados são salvos no arquivo local `academy.db`.

| Operação | Entrada | Processo | Saída |
|---|---|---|---|
| Cadastrar Aluno | Nome, CPF, Email | INSERT no SQLite | "Aluno cadastrado!" |
| Editar Plano | Novo valor | UPDATE no SQLite | "Plano atualizado!" |
| Apagar Pagamento | ID do pagamento | DELETE no SQLite | "Pagamento apagado!" |

---

## 11. Conclusão

O projeto foi concluído com sucesso e atinge todos os requisitos do Trabalho Prático. A mudança final de PostgreSQL para **SQLite** tornou o software 100% portátil, podendo ser executado via arquivo `.jar` em qualquer computador sem necessidade de instalações adicionais, garantindo uma demonstração fluida e segura.
