package banco;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class Conexao {

    private static final String URL = "jdbc:sqlite:academy.db";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL);
        } catch (Exception erro) {
            throw new RuntimeException("Erro ao conectar: " + erro.getMessage());
        }
    }

    public static void inicializarBanco() {
        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            InputStream in = Conexao.class.getResourceAsStream("/schema.sql");
            if (in == null) return;
            
            String sql = new BufferedReader(new InputStreamReader(in, java.nio.charset.StandardCharsets.UTF_8))
                            .lines().collect(Collectors.joining("\n"));
            
            String[] comandos = sql.split(";");
            for (String comando : comandos) {
                if (!comando.trim().isEmpty()) {
                    stmt.execute(comando);
                }
            }
        } catch (Exception e) {
            System.err.println("-> Erro ao inicializar banco: " + e.getMessage());
        }
    }
}