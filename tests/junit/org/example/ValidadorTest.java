package org.example;

import entidades.Aluno;
import entidades.Plano;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidadorTest {

    @Test
    @DisplayName("[CT-UNIT-01] Validar CPF com 11 dígitos com AssertJ")
    void testCpfValido() {
        assertThat(Validador.isCpfValido("529.982.247-25")).isTrue();
        assertThat(Validador.isCpfValido("52998224725")).isTrue();
    }

    @Test
    @DisplayName("[CT-UNIT-02] Rejeitar CPF com tamanho incorreto com AssertJ")
    void testCpfInvalido() {
        assertThat(Validador.isCpfValido("12345")).isFalse();
        assertThat(Validador.isCpfValido("123456789012345")).isFalse();
        assertThat(Validador.isCpfValido(null)).isFalse();
        assertThat(Validador.isCpfValido("")).isFalse();
    }

    @Test
    @DisplayName("[CT-UNIT-03] Validar expressões regulares de e-mail com AssertJ")
    void testEmail() {
        assertThat(Validador.isEmailValido("aluno@academia.com")).isTrue();
        assertThat(Validador.isEmailValido("joao.silva@fitup.com.br")).isTrue();
        assertThat(Validador.isEmailValido("aluno@")).isFalse();
        assertThat(Validador.isEmailValido("aluno.com")).isFalse();
        assertThat(Validador.isEmailValido(null)).isFalse();
    }

    @Test
    @DisplayName("[CT-UNIT-04] Bloquear aluno com nascimento futuro com AssertJ")
    void testNascimentoFuturo() {
        Aluno a = new Aluno();
        a.setNome("Carlos");
        a.setCpf("11122233344");
        a.setEmail("carlos@fit.com");
        a.setDataNascimento(LocalDate.now().plusDays(1));
        assertThat(Validador.validarAluno(a)).isNotNull();
    }

    @Test
    @DisplayName("[CT-UNIT-05] Rejeitar nomes com menos de 3 caracteres com AssertJ")
    void testNomeCurto() {
        Aluno a = new Aluno();
        a.setNome("Ed");
        a.setCpf("11122233344");
        a.setEmail("ed@fit.com");
        a.setDataNascimento(LocalDate.of(2000, 1, 1));
        assertThat(Validador.validarAluno(a)).isNotNull();
    }

    @Test
    @DisplayName("[CT-UNIT-06] Validar limites de altura e peso com AssertJ")
    void testAlturaEPeso() {
        Aluno a = new Aluno();
        a.setNome("Marcos");
        a.setCpf("11122233344");
        a.setEmail("marcos@fit.com");
        a.setDataNascimento(LocalDate.of(2000, 1, 1));

        a.setAltura(-1.75);
        assertThat(Validador.validarAluno(a)).isNotNull();

        a.setAltura(3.50);
        assertThat(Validador.validarAluno(a)).isNotNull();

        a.setAltura(1.82);
        assertThat(Validador.validarAluno(a)).isNull();
    }

    @Test
    @DisplayName("[CT-UNIT-10] Bloquear planos com valor menor ou igual a zero com AssertJ")
    void testPlanoValorInvalido() {
        Plano p1 = new Plano(1, "Zero", 0.0);
        Plano p2 = new Plano(2, "Negativo", -50.0);
        assertThat(Validador.validarPlano(p1)).isNotNull();
        assertThat(Validador.validarPlano(p2)).isNotNull();
    }

    @Test
    @DisplayName("[CT-UNIT-11] Validar normalização de status de pagamento com AssertJ")
    void testNormalizarStatus() {
        assertThat(Validador.normalizarStatusPagamento("pago")).isEqualTo("Pago");
        assertThat(Validador.normalizarStatusPagamento("ATRASADO")).isEqualTo("Atrasado");
        assertThat(Validador.normalizarStatusPagamento("Cancelado")).isEqualTo("Pendente");
    }
}

