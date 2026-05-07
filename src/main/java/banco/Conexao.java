package banco;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/academy";

    private static final String USUARIO =
            "postgres";

    private static final String SENHA =
            "isaacalonco";

    public static Connection conectar() {

        try {

            Connection conexao =
                    DriverManager.getConnection(
                            URL,
                            USUARIO,
                            SENHA
                    );

            System.out.println("Banco conectado com sucesso!");

            return conexao;

        } catch (Exception erro) {

            throw new RuntimeException(
                    "Erro ao conectar: "
                            + erro.getMessage()
            );
        }
    }
}