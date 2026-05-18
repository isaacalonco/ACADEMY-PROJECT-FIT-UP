package org.example;

import entidades.Aluno;
import entidades.Instrutor;
import entidades.Plano;
import entidades.Pagamento;
import entidades.Matricula;
import operacoes.AlunoOperacoes;
import operacoes.InstrutorOperacoes;
import operacoes.PlanoOperacoes;
import operacoes.PagamentoOperacoes;
import operacoes.MatriculaOperacoes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static AlunoOperacoes alunoOps = new AlunoOperacoes();
    static InstrutorOperacoes instrutorOps = new InstrutorOperacoes();
    static PlanoOperacoes planoOps = new PlanoOperacoes();
    static PagamentoOperacoes pagamentoOps = new PagamentoOperacoes();
    static MatriculaOperacoes matriculaOps = new MatriculaOperacoes();

    public static void main(String[] args) {
        // Inicializa o banco de dados (SQLite)
        banco.Conexao.inicializarBanco();
        
        // Inicia o Servidor Web em paralelo
        ApiServer.start();

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║        SISTEMA ACADEMY         ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ 1 - Alunos                     ║");
            System.out.println("║ 2 - Instrutores                ║");
            System.out.println("║ 3 - Planos                     ║");
            System.out.println("║ 4 - Pagamentos                 ║");
            System.out.println("║ 0 - Sair                       ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Escolha: ");
            opcao = lerInt();

            switch (opcao) {
                case 1:
                    menuAlunos();
                    break;
                case 2:
                    menuInstrutores();
                    break;
                case 3:
                    menuPlanos();
                    break;
                case 4:
                    menuPagamentos();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
        System.exit(0);
    }

    // ==================== ALUNOS ====================
    static void menuAlunos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ALUNOS ---");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Apagar Aluno");
            System.out.println("4 - Vincular/Trocar Plano");
            System.out.println("5 - Editar Aluno");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt();

            switch (op) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String tel = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String end = scanner.nextLine();
                    System.out.print("Data Nascimento (DD/MM/AAAA): ");
                    String dataStr = scanner.nextLine();
                    LocalDate dataNasc = parseDate(dataStr);
                    System.out.print("Peso (kg): ");
                    double peso = lerDouble();
                    System.out.print("Altura (m): ");
                    double altura = lerDouble();

                    Aluno novoAluno = new Aluno(nome, cpf, email, tel, end, dataNasc, peso, altura);
                    if (alunoOps.cadastrarAluno(novoAluno))
                        System.out.println("-> Aluno cadastrado!");
                    break;

                case 2:
                    List<Aluno> alunos = alunoOps.listarAlunos();
                    List<MatriculaOperacoes.MatriculaView> mats = matriculaOps.listarMatriculas();
                    System.out.println("\n--- Lista de Alunos ---");
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno.");
                        break;
                    }
                    for (Aluno a : alunos) {
                        String planoNome = "Sem plano";
                        for (MatriculaOperacoes.MatriculaView m : mats) {
                            if (m.idAluno == a.getId()) {
                                planoNome = m.nomePlano;
                                break;
                            }
                        }
                        System.out.printf("ID: %d | %s | CPF: %s | Email: %s | Plano: %s%n",
                                a.getId(), a.getNome(), a.getCpf(), a.getEmail(), planoNome);
                    }
                    break;

                case 3:
                    System.out.print("ID do aluno a apagar: ");
                    int idDel = lerInt();
                    if (alunoOps.deletarAluno(idDel))
                        System.out.println("-> Aluno apagado!");
                    else
                        System.out.println("-> Aluno não encontrado.");
                    break;

                case 4:
                    // Mostrar planos disponíveis
                    List<Plano> planosDisp = planoOps.listarPlanos();
                    if (planosDisp.isEmpty()) {
                        System.out.println("Nenhum plano cadastrado! Cadastre um primeiro.");
                        break;
                    }
                    System.out.println("\nPlanos disponíveis:");
                    for (Plano p : planosDisp) {
                        System.out.printf("  ID: %d | %s | R$ %.2f%n", p.getId(), p.getNome(), p.getValor());
                    }
                    System.out.print("ID do Aluno: ");
                    int idAluno = lerInt();
                    System.out.print("ID do Plano: ");
                    int idPlano = lerInt();
                    if (matriculaOps.atualizarPlanoDoAluno(idAluno, idPlano)) {
                        System.out.println("-> Plano vinculado/atualizado com sucesso!");
                    }
                    break;

                case 5:
                    System.out.print("ID do Aluno a editar: ");
                    int idEditAluno = lerInt();
                    System.out.print("Novo Nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo CPF: ");
                    String novoCpf = scanner.nextLine();
                    System.out.print("Novo Email: ");
                    String novoEmail = scanner.nextLine();
                    System.out.print("Novo Telefone: ");
                    String novoTel = scanner.nextLine();
                    System.out.print("Novo Endereço: ");
                    String novoEnd = scanner.nextLine();
                    System.out.print("Nova Data Nascimento (DD/MM/AAAA): ");
                    String novaDataStr = scanner.nextLine();
                    LocalDate novaDataNasc = parseDate(novaDataStr);
                    System.out.print("Novo Peso (kg): ");
                    double novoPeso = lerDouble();
                    System.out.print("Nova Altura (m): ");
                    double novaAltura = lerDouble();

                    Aluno alunoEditado = new Aluno(novoNome, novoCpf, novoEmail, novoTel, novoEnd, novaDataNasc, novoPeso, novaAltura);
                    alunoEditado.setId(idEditAluno);
                    if (alunoOps.atualizarAluno(alunoEditado))
                        System.out.println("-> Aluno atualizado com sucesso!");
                    else
                        System.out.println("-> Erro ou Aluno não encontrado.");
                    break;
            }
        }
    }

    // ==================== INSTRUTORES ====================
    static void menuInstrutores() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- INSTRUTORES ---");
            System.out.println("1 - Cadastrar Instrutor");
            System.out.println("2 - Listar Instrutores");
            System.out.println("3 - Apagar Instrutor");
            System.out.println("4 - Editar Instrutor");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt();

            switch (op) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String tel = scanner.nextLine();
                    System.out.print("Especialidade: ");
                    String esp = scanner.nextLine();

                    Instrutor novoInst = new Instrutor(0, nome, cpf, email, tel, esp);
                    if (instrutorOps.cadastrarInstrutor(novoInst))
                        System.out.println("-> Instrutor cadastrado!");
                    break;

                case 2:
                    List<Instrutor> instrutores = instrutorOps.listarInstrutores();
                    System.out.println("\n--- Lista de Instrutores ---");
                    if (instrutores.isEmpty()) {
                        System.out.println("Nenhum instrutor.");
                        break;
                    }
                    for (Instrutor i : instrutores) {
                        System.out.printf("ID: %d | %s | CPF: %s | Email: %s | Tel: %s | Esp: %s%n",
                                i.getIdInstrutor(), i.getNome(), i.getCpf(), i.getEmail(), i.getTelefone(),
                                i.getEspecialidade());
                    }
                    break;

                case 3:
                    System.out.print("ID do instrutor a apagar: ");
                    int idDel = lerInt();
                    if (instrutorOps.deletarInstrutor(idDel))
                        System.out.println("-> Instrutor apagado!");
                    else
                        System.out.println("-> Instrutor não encontrado.");
                    break;

                case 4:
                    System.out.print("ID do Instrutor a editar: ");
                    int idEditInst = lerInt();
                    System.out.print("Novo Nome: ");
                    String nNome = scanner.nextLine();
                    System.out.print("Novo CPF: ");
                    String nCpf = scanner.nextLine();
                    System.out.print("Novo Email: ");
                    String nEmail = scanner.nextLine();
                    System.out.print("Novo Telefone: ");
                    String nTel = scanner.nextLine();
                    System.out.print("Nova Especialidade: ");
                    String nEsp = scanner.nextLine();

                    Instrutor instrutorEditado = new Instrutor(idEditInst, nNome, nCpf, nEmail, nTel, nEsp);
                    if (instrutorOps.atualizarInstrutor(instrutorEditado))
                        System.out.println("-> Instrutor atualizado com sucesso!");
                    else
                        System.out.println("-> Erro ou Instrutor não encontrado.");
                    break;
            }
        }
    }

    // ==================== PLANOS ====================
    static void menuPlanos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PLANOS ---");
            System.out.println("1 - Cadastrar Plano");
            System.out.println("2 - Listar Planos");
            System.out.println("3 - Apagar Plano");
            System.out.println("4 - Editar Plano");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt();

            switch (op) {
                case 1:
                    System.out.print("Nome do Plano: ");
                    String nome = scanner.nextLine();
                    System.out.print("Valor Mensal (R$): ");
                    double valor = lerDouble();

                    Plano novoPlano = new Plano(0, nome, valor);
                    if (planoOps.cadastrarPlano(novoPlano))
                        System.out.println("-> Plano cadastrado!");
                    break;

                case 2:
                    List<Plano> planos = planoOps.listarPlanos();
                    System.out.println("\n--- Lista de Planos ---");
                    if (planos.isEmpty()) {
                        System.out.println("Nenhum plano.");
                        break;
                    }
                    for (Plano p : planos) {
                        System.out.printf("ID: %d | %s | R$ %.2f%n", p.getId(), p.getNome(), p.getValor());
                    }
                    break;

                case 3:
                    System.out.print("ID do plano a apagar: ");
                    int idDel = lerInt();
                    if (planoOps.deletarPlano(idDel))
                        System.out.println("-> Plano apagado!");
                    else
                        System.out.println("-> Plano não encontrado.");
                    break;

                case 4:
                    System.out.print("ID do Plano a editar: ");
                    int idEditPlano = lerInt();
                    System.out.print("Novo Nome do Plano: ");
                    String nNomePlano = scanner.nextLine();
                    System.out.print("Novo Valor Mensal (R$): ");
                    double nValor = lerDouble();

                    Plano planoEditado = new Plano(idEditPlano, nNomePlano, nValor);
                    if (planoOps.atualizarPlano(planoEditado))
                        System.out.println("-> Plano atualizado com sucesso!");
                    else
                        System.out.println("-> Erro ou Plano não encontrado.");
                    break;
            }
        }
    }

    // ==================== PAGAMENTOS ====================
    static void menuPagamentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Registrar Pagamento");
            System.out.println("2 - Listar Pagamentos");
            System.out.println("3 - Apagar Pagamento");
            System.out.println("4 - Atualizar Status de Pagamento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt();

            switch (op) {
                case 1:
                    System.out.print("ID do Aluno: ");
                    int idAluno = lerInt();

                    // Buscar valor do plano do aluno automaticamente
                    double valorPag = 0;
                    List<MatriculaOperacoes.MatriculaView> matsAluno = matriculaOps.listarMatriculas();
                    String nomePlanoAluno = null;
                    for (MatriculaOperacoes.MatriculaView mv : matsAluno) {
                        if (mv.idAluno == idAluno) { nomePlanoAluno = mv.nomePlano; break; }
                    }
                    if (nomePlanoAluno != null) {
                        List<Plano> planosDisp2 = planoOps.listarPlanos();
                        for (Plano pl : planosDisp2) {
                            if (pl.getNome().equals(nomePlanoAluno)) { valorPag = pl.getValor(); break; }
                        }
                        System.out.printf("-> Plano detectado: %s | Valor: R$ %.2f%n", nomePlanoAluno, valorPag);
                    } else {
                        System.out.println("-> Aluno sem plano vinculado. Digite o valor manualmente:");
                        System.out.print("Valor (R$): ");
                        valorPag = lerDouble();
                    }

                    System.out.print("Status (Pago/Pendente/Atrasado): ");
                    String status = scanner.nextLine();

                    Pagamento novoPag = new Pagamento(0, idAluno, valorPag, status);
                    if (pagamentoOps.cadastrarPagamento(novoPag))
                        System.out.println("-> Pagamento registrado!");
                    break;

                case 2:
                    List<PagamentoOperacoes.PagamentoView> pags = pagamentoOps.listarPagamentos();
                    System.out.println("\n--- Lista de Pagamentos ---");
                    if (pags.isEmpty()) {
                        System.out.println("Nenhum pagamento.");
                        break;
                    }
                    for (PagamentoOperacoes.PagamentoView p : pags) {
                        System.out.printf("ID: %d | Aluno: %s | R$ %.2f | Status: %s%n",
                                p.idPagamento, p.nomeAluno, p.valor, p.status);
                    }
                    break;

                case 3:
                    System.out.print("ID do pagamento a apagar: ");
                    int idDel = lerInt();
                    if (pagamentoOps.deletarPagamento(idDel))
                        System.out.println("-> Pagamento apagado!");
                    else
                        System.out.println("-> Pagamento não encontrado.");
                    break;

                case 4:
                    System.out.print("ID do Pagamento a atualizar: ");
                    int idEditPag = lerInt();
                    System.out.print("Novo Status (Pago/Pendente/Atrasado): ");
                    String novoStatus = scanner.nextLine();

                    if (pagamentoOps.atualizarStatusPagamento(idEditPag, novoStatus))
                        System.out.println("-> Status atualizado com sucesso!");
                    else
                        System.out.println("-> Erro ou Pagamento não encontrado.");
                    break;
            }
        }
    }

    // ==================== HELPERS ====================
    static int lerInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().replace(",", "."));
        } catch (Exception e) {
            return 0;
        }
    }

    static LocalDate parseDate(String str) {
        try {
            return LocalDate.parse(str, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            System.out.println("Data inválida, usando data atual.");
            return LocalDate.now();
        }
    }
}