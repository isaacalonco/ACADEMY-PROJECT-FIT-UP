package entidades;

import java.time.LocalDate;
import java.time.Period;

public class aluno {

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String endereco;
    private LocalDate dataNascimento;
    private double peso;
    private double altura;
    private LocalDate dataCadastro;
    private boolean ativo;

    public Aluno(String nome, String cpf, String email,
                 String telefone, String endereco,
                 LocalDate dataNascimento,
                 double peso, double altura) {

        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
        this.altura = altura;
        this.dataCadastro = LocalDate.now();
        this.ativo = true;
    }


    public int getIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public double getImc() {
        return peso / (altura * altura);
    }

    public String getClassificacaoImc() {

        double imc = getImc();

        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else {
            return "Obesidade";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Aluno: " + nome + " | CPF: " + cpf;
    }
}
