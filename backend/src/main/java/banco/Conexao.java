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
            // Habilita as chaves estrangeiras no SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            // Lê o schema.sql que está dentro do resources (ou JAR)
            InputStream in = Conexao.class.getResourceAsStream("/schema.sql");
            if (in == null) return;
            
            String sql = new BufferedReader(new InputStreamReader(in))
                            .lines().collect(Collectors.joining("\n"));
            
            // Separa os comandos e executa um por um
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