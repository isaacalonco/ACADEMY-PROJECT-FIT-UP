package entidades;

public class Pagamento {

    private int idPagamento;
    private String nomeAluno;
    private double valor;
    private String status;

    public Pagamento(int idPagamento, String nomeAluno, double valor, String status) {
        this.idPagamento = idPagamento;
        this.nomeAluno = nomeAluno;
        this.valor = valor;
        this.status = status;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public double getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "idPagamento=" + idPagamento +
                ", nomeAluno='" + nomeAluno + '\'' +
                ", valor=" + valor +
                ", status='" + status + '\'' +
                '}';
    }
}
