package operacoes;

import banco.Conexao;
import entidades.Aluno;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoOperacoes {

    public boolean cadastrarAluno(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, cpf, email, telefone, endereco, data_nascimento, peso, altura, data_cadastro, ativo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getCpf());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getTelefone());
            ps.setString(5, aluno.getEndereco());
            ps.setDate(6, Date.valueOf(aluno.getDataNascimento()));
            ps.setDouble(7, aluno.getPeso());
            ps.setDouble(8, aluno.getAltura());
            ps.setDate(9, Date.valueOf(aluno.getDataCadastro() != null ? aluno.getDataCadastro() : LocalDate.now()));
            ps.setBoolean(10, aluno.isAtivo());
            return ps.executeUpdate() > 0;
        } catch (java.sql.SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
                System.err.println("-> CPF já cadastrado no sistema!");
            } else {
                System.err.println("-> Erro ao cadastrar aluno: " + e.getMessage());
            }
            return false;
        } catch (Exception e) {
            System.err.println("-> Erro ao cadastrar aluno: " + e.getMessage());
            return false;
        }
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY id";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setEmail(rs.getString("email"));
                a.setTelefone(rs.getString("telefone"));
                a.setEndereco(rs.getString("endereco"));
                a.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                a.setPeso(rs.getDouble("peso"));
                a.setAltura(rs.getDouble("altura"));
                a.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
                a.setAtivo(rs.getBoolean("ativo"));
                alunos.add(a);
            }
        } catch (Exception e) {
            System.err.println("-> Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public boolean atualizarAluno(Aluno aluno) {
        String sql = "UPDATE aluno SET nome=?, cpf=?, email=?, telefone=?, endereco=?, data_nascimento=?, peso=?, altura=?, ativo=? WHERE id=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getCpf());
            ps.setString(3, aluno.getEmail());
            ps.setString(4, aluno.getTelefone());
            ps.setString(5, aluno.getEndereco());
            ps.setDate(6, Date.valueOf(aluno.getDataNascimento()));
            ps.setDouble(7, aluno.getPeso());
            ps.setDouble(8, aluno.getAltura());
            ps.setBoolean(9, aluno.isAtivo());
            ps.setInt(10, aluno.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("-> Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    public boolean deletarAluno(int id) {
        String sql = "DELETE FROM aluno WHERE id=?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("-> Erro ao deletar aluno: " + e.getMessage());
            return false;
        }
    }
}
