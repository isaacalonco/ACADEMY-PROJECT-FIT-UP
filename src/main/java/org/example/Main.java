import entidades.Aluno;
import operacoes.AlunoOperacoes;

public class Main {

    public static void main(String[] args) {

        Aluno novoAluno = new Aluno(

                "Isaac",
                "12345678900",
                "61999999999",
                70,
                1.75
        );

        AlunoOperacoes operacoes =
                new AlunoOperacoes();

        operacoes.cadastrarAluno(novoAluno);

        operacoes.listarAlunos();
    }
}