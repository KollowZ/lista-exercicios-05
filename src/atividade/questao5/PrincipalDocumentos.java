package atividade.questao5;

import java.util.Arrays;
import java.util.List;

public class PrincipalDocumentos {
    public static void main(String[] args) {
        List<Imprimivel> documentos = Arrays.asList(
            new RelatorioFinanceiro("Balanço mensal"),
            new Contrato("Prestação de serviços"),
            new RelatorioFinanceiro("Fluxo de caixa")
        );

        for (Imprimivel documento : documentos) {
            // A impressão usa polimorfismo: cada classe fornece sua implementação.
            documento.imprimir();

            // Somente documentos com essa capacidade podem ser enviados.
            if (documento instanceof EnviavelPorEmail) {
                EnviavelPorEmail enviavel = (EnviavelPorEmail) documento;
                enviavel.enviar("destinatario@example.com");
            }
        }
    }
}
