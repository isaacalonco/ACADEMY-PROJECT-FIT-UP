package org.example;

import entidades.Aluno;
import entidades.Plano;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidadorTest {

    @Test
    @DisplayName("[CT-UNIT-01] Validar CPF com 11 dígitos")
    void testCpfValido() {
        assertTrue(Validador.isCpfValido("529.982.247-25"));
        assertTrue(Validador.isCpfValido("52998224725"));
    }

    @Test
    @DisplayName("[CT-UNIT-02] Rejeitar CPF com tamanho incorreto")
    void testCpfInvalido() {
        assertFalse(Validador.isCpfValido("12345"));
        assertFalse(Validador.isCpfValido("123456789012345"));
        assertFalse(Validador.isCpfValido(null));
        assertFalse(Validador.isCpfValido(""));
    }

    @Test
    @DisplayName("[CT-UNIT-03] Validar expressões regulares de e-mail")
    void testEmail() {
        assertTrue(Validador.isEmailValido("aluno@academia.com"));
        assertTrue(Validador.isEmailValido("joao.silva@fitup.com.br"));
        assertFalse(Validador.isEmailValido("aluno@"));
        assertFalse(Validador.isEmailValido("aluno.com"));
        assertFalse(Validador.isEmailValido(null));
    }

    @Test
    @DisplayName("[CT-UNIT-04] Bloquear aluno com nascimento futuro")
    void testNascimentoFuturo() {
        Aluno a = new Aluno();
        a.setNome("Carlos");
        a.setCpf("11122233344");
        a.setEmail("carlos@fit.com");
        a.setDataNascimento(LocalDate.now().plusDays(1));
        assertNotNull(Validador.validarAluno(a));
    }

    @Test
    @DisplayName("[CT-UNIT-05] Rejeitar nomes com menos de 3 caracteres")
    void testNomeCurto() {
        Aluno a = new Aluno();
        a.setNome("Ed");
        a.setCpf("11122233344");
        a.setEmail("ed@fit.com");
        a.setDataNascimento(LocalDate.of(2000, 1, 1));
        assertNotNull(Validador.validarAluno(a));
    }

    @Test
    @DisplayName("[CT-UNIT-06] Validar limites de altura e peso")
    void testAlturaEPeso() {
        Aluno a = new Aluno();
        a.setNome("Marcos");
        a.setCpf("11122233344");
        a.setEmail("marcos@fit.com");
        a.setDataNascimento(LocalDate.of(2000, 1, 1));

        a.setAltura(-1.75);
        assertNotNull(Validador.validarAluno(a));

        a.setAltura(3.50);
        assertNotNull(Validador.validarAluno(a));

        a.setAltura(1.82);
        assertNull(Validador.validarAluno(a));
    }

    @Test
    @DisplayName("[CT-UNIT-10] Bloquear planos com valor menor ou igual a zero")
    void testPlanoValorInvalido() {
        Plano p1 = new Plano(1, "Zero", 0.0);
        Plano p2 = new Plano(2, "Negativo", -50.0);
        assertNotNull(Validador.validarPlano(p1));
        assertNotNull(Validador.validarPlano(p2));
    }

    @Test
    @DisplayName("[CT-UNIT-11] Validar normalização de status de pagamento")
    void testNormalizarStatus() {
        assertEquals("Pago", Validador.normalizarStatusPagamento("pago"));
        assertEquals("Atrasado", Validador.normalizarStatusPagamento("ATRASADO"));
        assertEquals("Pendente", Validador.normalizarStatusPagamento("Cancelado"));
    }
}
