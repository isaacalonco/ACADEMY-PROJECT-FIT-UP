package entidades;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlunoTest {

    @Test
    @DisplayName("[CT-UNIT-07] Validar precisão da fórmula de IMC")
    void testCalculoImc() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");
        a.setPeso(80.0);
        a.setAltura(2.0);
        assertEquals(20.0, a.getImc(), 0.001);
    }

    @Test
    @DisplayName("[CT-UNIT-08] Validar 4 faixas de classificação nutricional")
    void testClassificacaoImc() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");

        a.setPeso(50.0); a.setAltura(1.75); // < 18.5
        assertEquals("Abaixo do peso", a.getClassificacaoImc());

        a.setPeso(70.0); a.setAltura(1.75); // 18.5 - 24.9
        assertEquals("Peso normal", a.getClassificacaoImc());

        a.setPeso(85.0); a.setAltura(1.75); // 25.0 - 29.9
        assertEquals("Sobrepeso", a.getClassificacaoImc());

        a.setPeso(100.0); a.setAltura(1.75); // >= 30.0
        assertEquals("Obesidade", a.getClassificacaoImc());
    }

    @Test
    @DisplayName("[CT-UNIT-09] Tratar altura zerada sem divisão por zero")
    void testAlturaZero() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");
        a.setPeso(70.0);
        a.setAltura(0.0);
        assertEquals(0.0, a.getImc());
        assertEquals("Não calculado", a.getClassificacaoImc());
    }
}
