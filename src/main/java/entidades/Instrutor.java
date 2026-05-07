package entidades;

public class Instrutor {

    private int idInstrutor;
    private String nome;
    private String especialidade;

    public Instrutor(int idInstrutor, String nome, String especialidade) {
        this.idInstrutor = idInstrutor;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public int getIdInstrutor() {
        return idInstrutor;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Instrutor{" +
                "idInstrutor=" + idInstrutor +
                ", nome='" + nome + '\'' +
                ", especialidade='" + especialidade + '\'' +
                '}';
    }
}
