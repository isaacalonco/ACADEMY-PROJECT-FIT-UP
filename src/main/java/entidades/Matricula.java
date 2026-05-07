package entidades;

public class Matricula {

    private int idMatricula;
    private String nomeAluno;
    private String nomePlano;
    private String dataMatricula;

    public Matricula(int idMatricula, String nomeAluno, String nomePlano, String dataMatricula) {
        this.idMatricula = idMatricula;
        this.nomeAluno = nomeAluno;
        this.nomePlano = nomePlano;
        this.dataMatricula = dataMatricula;
    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "idMatricula=" + idMatricula +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", nomePlano='" + nomePlano + '\'' +
                ", dataMatricula='" + dataMatricula + '\'' +
                '}';
    }
}
