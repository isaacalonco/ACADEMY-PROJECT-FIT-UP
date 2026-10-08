package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DemonstracaoFalhaTest {

    @Test
    @DisplayName("[CT-UNIT-DEMO-FALHA] Teste propositalmente falho para demonstrar a barra vermelha no IntelliJ")
    void testDemonstracaoFalhaVisual() {
        int valorEsperadoPeloTeste = 100;
        int valorRetornadoPeloSistema = 404;

        // Esta asserção falhará intencionalmente para exibir a barra vermelha no JUnit / IntelliJ
        assertThat(valorRetornadoPeloSistema)
                .as("Demonstração: este teste falha propositalmente para gravação da ferramenta acusando erro")
                .isEqualTo(valorEsperadoPeloTeste);
    }
}
