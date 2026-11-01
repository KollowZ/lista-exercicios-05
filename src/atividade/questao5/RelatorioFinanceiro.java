package atividade.questao5;

public class RelatorioFinanceiro implements Imprimivel, EnviavelPorEmail {
    private final String titulo;

    public RelatorioFinanceiro(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void imprimir() {
        System.out.println("Imprimindo relatório financeiro: " + titulo);
    }

    @Override
    public void enviar(String email) {
        System.out.println("Enviando relatório financeiro '" + titulo + "' para " + email);
    }
}
