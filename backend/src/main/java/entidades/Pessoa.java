package entidades;

/**
 * Classe abstrata que representa uma Pessoa genérica no sistema da academia.
 */
public abstract class Pessoa {

    protected String nome;
    protected String cpf;
    protected String email;
    protected String telefone;

    // Construtor vazio (necessário para o Gson)
    public Pessoa() {}

    // Construtor com parâmetros
    public Pessoa(String nome, String cpf, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
    }

    // Método abstrato de descrição
    public abstract String getDescricao();

    // Método abstrato — retorna o tipo/papel da pessoa no sistema
    public abstract String getTipo();

    // Getters e Setters comuns
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return getTipo() + ": " + nome + " | CPF: " + cpf;
    }
}
