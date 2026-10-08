package entidades;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class AlunoTest {

    @Test
    @DisplayName("[CT-UNIT-07] Validar precisão da fórmula de IMC com AssertJ")
    void testCalculoImc() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");
        a.setPeso(80.0);
        a.setAltura(2.0);
        assertThat(a.getImc()).isCloseTo(20.0, within(0.001));
    }

    @Test
    @DisplayName("[CT-UNIT-08] Validar 4 faixas de classificação nutricional com AssertJ")
    void testClassificacaoImc() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");

        a.setPeso(50.0); a.setAltura(1.75); // < 18.5
        assertThat(a.getClassificacaoImc()).isEqualTo("Abaixo do peso");

        a.setPeso(70.0); a.setAltura(1.75); // 18.5 - 24.9
        assertThat(a.getClassificacaoImc()).isEqualTo("Peso normal");

        a.setPeso(85.0); a.setAltura(1.75); // 25.0 - 29.9
        assertThat(a.getClassificacaoImc()).isEqualTo("Sobrepeso");

        a.setPeso(100.0); a.setAltura(1.75); // >= 30.0
        assertThat(a.getClassificacaoImc()).isEqualTo("Obesidade");
    }

    @Test
    @DisplayName("[CT-UNIT-09] Tratar altura zerada sem divisão por zero com AssertJ")
    void testAlturaZero() {
        Aluno a = new Aluno();
        a.setNome("Teste");
        a.setCpf("11122233344");
        a.setEmail("teste@fit.com");
        a.setPeso(70.0);
        a.setAltura(0.0);
        assertThat(a.getImc()).isEqualTo(0.0);
        assertThat(a.getClassificacaoImc()).isEqualTo("Não calculado");
    }
}

