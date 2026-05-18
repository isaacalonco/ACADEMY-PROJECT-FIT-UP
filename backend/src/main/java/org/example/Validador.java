package org.example;

import entidades.Aluno;
import entidades.Instrutor;
import entidades.Plano;
import entidades.Pagamento;
import java.time.LocalDate;

public class Validador {

    public static boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        String limpo = cpf.replaceAll("\\D", "");
        return limpo.length() == 11;
    }

    public static boolean isEmailValido(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    public static String validarAluno(Aluno a) {
        if (a == null) return "Dados do aluno inválidos.";
        if (a.getNome() == null || a.getNome().trim().length() < 3) {
            return "O nome do aluno deve conter pelo menos 3 caracteres.";
        }
        if (!isCpfValido(a.getCpf())) {
            return "CPF inválido. Certifique-se de que possui 11 dígitos.";
        }
        if (!isEmailValido(a.getEmail())) {
            return "E-mail com formato inválido.";
        }
        if (a.getDataNascimento() == null || a.getDataNascimento().isAfter(LocalDate.now())) {
            return "Data de nascimento inválida (não pode ser no futuro).";
        }
        if (a.getPeso() < 0) {
            return "O peso não pode ser negativo.";
        }
        if (a.getAltura() < 0 || a.getAltura() > 3.0) {
            return "A altura deve ser um valor positivo e razoável (até 3 metros).";
        }
        return null;
    }

    public static String validarInstrutor(Instrutor i) {
        if (i == null) return "Dados do instrutor inválidos.";
        if (i.getNome() == null || i.getNome().trim().length() < 3) {
            return "O nome do instrutor deve conter pelo menos 3 caracteres.";
        }
        if (!isCpfValido(i.getCpf())) {
            return "CPF inválido. Certifique-se de que possui 11 dígitos.";
        }
        if (i.getEmail() != null && !i.getEmail().trim().isEmpty() && !isEmailValido(i.getEmail())) {
            return "E-mail com formato inválido.";
        }
        if (i.getEspecialidade() == null || i.getEspecialidade().trim().isEmpty()) {
            return "A especialidade do instrutor é obrigatória.";
        }
        return null;
    }

    public static String validarPlano(Plano p) {
        if (p == null) return "Dados do plano inválidos.";
        if (p.getNome() == null || p.getNome().trim().isEmpty()) {
            return "O nome do plano é obrigatório.";
        }
        if (p.getValor() <= 0) {
            return "O valor do plano deve ser maior que zero.";
        }
        return null;
    }

    public static String validarPagamento(Pagamento pg) {
        if (pg == null) return "Dados do pagamento inválidos.";
        if (pg.getIdAluno() <= 0) {
            return "Um aluno válido deve ser selecionado para o pagamento.";
        }
        if (pg.getValor() < 0) {
            return "O valor do pagamento não pode ser negativo.";
        }
        if (pg.getStatus() == null) {
            return "O status do pagamento deve ser 'Pago', 'Pendente' ou 'Atrasado'.";
        }
        String status = pg.getStatus().trim();
        if (status.equalsIgnoreCase("Pago")) {
            pg.setStatus("Pago");
        } else if (status.equalsIgnoreCase("Pendente")) {
            pg.setStatus("Pendente");
        } else if (status.equalsIgnoreCase("Atrasado")) {
            pg.setStatus("Atrasado");
        } else {
            return "O status do pagamento deve ser 'Pago', 'Pendente' ou 'Atrasado'.";
        }
        return null;
    }
}
