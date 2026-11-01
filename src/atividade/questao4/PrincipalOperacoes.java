package atividade.questao4;

public class PrincipalOperacoes {
    public static void main(String[] args) {
        OperacaoMatematica soma = (a, b) -> a + b;
        OperacaoMatematica subtracao = (a, b) -> a - b;
        OperacaoMatematica multiplicacao = (a, b) -> a * b;
        OperacaoMatematica divisao = (a, b) -> {
            if (b == 0.0) {
                throw new IllegalArgumentException("Não é possível dividir por zero.");
            }
            return a / b;
        };

        double a = 10.0;
        double b = 2.0;
        System.out.println("Soma: " + soma.executar(a, b));
        System.out.println("Subtração: " + subtracao.executar(a, b));
        System.out.println("Multiplicação: " + multiplicacao.executar(a, b));
        System.out.println("Divisão: " + divisao.executar(a, b));

        try {
            divisao.executar(a, 0.0);
        } catch (IllegalArgumentException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }
}
