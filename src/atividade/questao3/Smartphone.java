package atividade.questao3;

public class Smartphone implements AparelhoTelefonico, NavegadorInternet, ReprodutorMusical {
    @Override
    public void ligar() {
        System.out.println("Realizando uma chamada...");
    }

    @Override
    public void atender() {
        System.out.println("Atendendo uma chamada...");
    }

    @Override
    public void exibirPagina() {
        System.out.println("Exibindo uma página da internet...");
    }

    @Override
    public void adicionarNovaAba() {
        System.out.println("Adicionando uma nova aba...");
    }

    @Override
    public void tocar() {
        System.out.println("Tocando uma música...");
    }

    @Override
    public void pausar() {
        System.out.println("Música pausada.");
    }
}
