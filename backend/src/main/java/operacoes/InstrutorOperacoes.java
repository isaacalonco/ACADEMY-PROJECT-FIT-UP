package operacoes;

import banco.Conexao;
import entidades.Instrutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstrutorOperacoes {

    public boolean cadastrarInstrutor(Instrutor i) {
        String sql = "INSERT INTO instrutor (nome, cpf, email, telefone, especialidade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getNome());
            ps.setString(2, i.getCpf());
            ps.setString(3, i.getEmail());
            ps.setString(4, i.getTelefone());
            ps.setString(5, i.getEspecialidade());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("-> Erro ao cadastrar instrutor: " + e.getMessage());
            return false;
        }
    }

    public List<Instrutor> listarInstrutores() {
        List<Instrutor> list = new ArrayList<>();
        String sql = "SELECT * FROM instrutor ORDER BY id_instrutor";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Instrutor i = new Instrutor(
                    rs.getInt("id_instrutor"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("email"),
                    rs.getString("telefone"),
                    rs.getString("especialidade")
                );
                list.add(i);
            }
        } catch (Exception e) {
            System.err.println("-> Erro ao listar instrutores: " + e.getMessage());
        }
        return list;
    }

    public boolean atualizarInstrutor(Instrutor i) {
        String sql = "UPDATE instrutor SET nome=?, cpf=?, email=?, telefone=?, especialidade=? WHERE id_instrutor=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, i.getNome());
            ps.setString(2, i.getCpf());
            ps.setString(3, i.getEmail());
            ps.setString(4, i.getTelefone());
            ps.setString(5, i.getEspecialidade());
            ps.setInt(6, i.getIdInstrutor());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("-> Erro ao atualizar instrutor: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarInstrutor(int id) {
        String sql = "DELETE FROM instrutor WHERE id_instrutor=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("-> Erro ao deletar instrutor: " + e.getMessage());
            return false;
        }
    }
}
