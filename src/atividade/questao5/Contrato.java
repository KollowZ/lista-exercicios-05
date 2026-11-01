package atividade.questao5;

public class Contrato implements Imprimivel {
    private final String descricao;

    public Contrato(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public void imprimir() {
        System.out.println("Imprimindo contrato: " + descricao);
    }
}
