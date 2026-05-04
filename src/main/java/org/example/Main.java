import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/academy";
        String user = "postgres";
        String password = "isaacalonco";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}