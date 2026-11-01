# Atividade: interfaces e expressões lambda em Java

Resolução das cinco questões sobre interfaces, desacoplamento, múltiplas interfaces e expressões lambda. Código compatível com **Java 8 ou superior**, sem dependências externas.

## 1. Métodos com corpo em interfaces

A partir do **Java 8**, interfaces passaram a permitir métodos com corpo declarados como `default` ou `static`.

- **`default`:** fornece uma implementação padrão herdada pelas classes que implementam a interface. A classe pode sobrescrever esse comportamento. Isso permite compartilhar um comportamento e acrescentar métodos a uma interface existente sem obrigar todas as implementações a fornecer o novo método. Se duas interfaces não relacionadas fornecerem métodos default com a mesma assinatura, a classe precisa resolver o conflito sobrescrevendo o método.
- **`static`:** reúne funções auxiliares relacionadas à interface. O método pertence à própria interface e deve ser chamado por seu nome, como `Identificavel.categoria()`. Não é herdado pelas classes implementadoras e não participa da sobrescrita de métodos de instância.

Exemplo ilustrativo:

```java
interface Identificavel {
    String nome();

    default String descricao() {
        return "Nome: " + nome();
    }

    static String categoria() {
        return "Objeto identificável";
    }
}

class Produto implements Identificavel {
    @Override
    public String nome() {
        return "Caderno";
    }
}

// Em um método main:
// Identificavel produto = new Produto();
// System.out.println(produto.descricao());
// System.out.println(Identificavel.categoria());
```

Referência: [Oracle — Default Methods (Java 8)](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html).

## 2. Interfaces, desacoplamento e manutenção

Uma interface define um contrato: quais operações um objeto oferece. Uma classe que depende desse contrato não precisa conhecer os detalhes da implementação. Assim, podemos substituir uma implementação, adicionar outra ou usar uma implementação falsa em testes sem alterar o código que utiliza a interface, desde que o contrato continue sendo respeitado.

Um cenário prático é um sistema de vendas que envia notificações. Em vez de o serviço de pedidos criar diretamente um remetente de email, ele recebe uma interface `Notificador`. Isso permite trocar email por SMS sem alterar a lógica de conclusão do pedido:

```java
interface Notificador {
    void enviar(String mensagem);
}

class NotificadorEmail implements Notificador {
    @Override
    public void enviar(String mensagem) {
        System.out.println("Email: " + mensagem);
    }
}

class NotificadorSms implements Notificador {
    @Override
    public void enviar(String mensagem) {
        System.out.println("SMS: " + mensagem);
    }
}

class ServicoPedido {
    private final Notificador notificador;

    ServicoPedido(Notificador notificador) {
        this.notificador = notificador;
    }

    void concluirPedido() {
        notificador.enviar("Pedido concluído!");
    }
}

// No ponto de configuração do sistema:
// ServicoPedido servico = new ServicoPedido(new NotificadorEmail());
// servico.concluirPedido();
// Para usar SMS, forneça new NotificadorSms() ao construtor.
```

O recebimento da dependência pelo construtor é uma forma de injeção de dependência. O serviço usa o contrato, e a escolha da classe concreta fica no ponto de configuração. Esse isolamento facilita a manutenção e os testes. As implementações acima apenas simulam o envio.

## 3. Smartphone com três interfaces

Arquivos em [`src/atividade/questao3`](src/atividade/questao3):

- `AparelhoTelefonico`: `ligar()` e `atender()`.
- `NavegadorInternet`: `exibirPagina()` e `adicionarNovaAba()`.
- `ReprodutorMusical`: `tocar()` e `pausar()`.
- `Smartphone`: implementa as três interfaces com métodos públicos e `@Override`.
- `PrincipalSmartphone`: instancia o aparelho e executa os seis métodos.

As operações são simulações com mensagens no console. Aqui, `ligar()` representa realizar uma chamada telefônica. Java permite que uma classe implemente múltiplas interfaces.

## 4. Interface funcional e lambdas

Arquivos em [`src/atividade/questao4`](src/atividade/questao4).

`OperacaoMatematica` declara `double executar(double a, double b)` e usa `@FunctionalInterface`. Uma interface funcional tem um único método abstrato e pode ser implementada com uma expressão lambda.

O programa principal cria quatro implementações: soma, subtração, multiplicação e divisão. Para `a = 10.0` e `b = 2.0`, os resultados são, respectivamente, `12.0`, `8.0`, `20.0` e `5.0`.

A divisão rejeita divisor zero com `IllegalArgumentException`, tratada no exemplo principal. Essa é uma regra explícita do exemplo: a divisão de valores `double` em Java, por padrão, pode produzir infinito ou `NaN` em vez de lançar uma exceção ao dividir por zero.

## 5. Documentos imprimíveis e enviáveis por email

Arquivos em [`src/atividade/questao5`](src/atividade/questao5).

`Imprimivel` define `imprimir()` e `EnviavelPorEmail` define `enviar(String email)`. `RelatorioFinanceiro` implementa ambas, enquanto `Contrato` implementa apenas `Imprimivel`.

O programa percorre uma `List<Imprimivel>` com dois relatórios e um contrato. Todos são impressos por polimorfismo. A verificação `instanceof EnviavelPorEmail`, permitida pelo enunciado, identifica quais também podem ser enviados. O cast utiliza a sintaxe compatível com Java 8.

Não é necessário verificar a classe concreta (`RelatorioFinanceiro`): basta verificar a capacidade de envio. Assim, uma nova classe que implemente `EnviavelPorEmail` também será reconhecida. O contrato não recebe um método de envio vazio, pois não possui essa capacidade. Impressão e envio são apenas simulações no console, sem impressora ou serviço real de email.

Em um sistema maior, outra possibilidade é manter coleções separadas de `Imprimivel` e `EnviavelPorEmail`, montadas na configuração da aplicação. Cada processador usa sua interface, dispensando a verificação explícita.

## Como compilar e executar

Abra o terminal na pasta raiz deste projeto. É necessário um **JDK**, que inclui `javac`, instalado e disponível no PATH.

Com JDK 9 ou superior, compile mantendo compatibilidade com Java 8:

```sh
javac -encoding UTF-8 --release 8 -d out @fontes.txt
java -Dfile.encoding=UTF-8 -cp out atividade.Main
```

**No PowerShell**, coloque aspas nos argumentos que começam com `@` ou contêm a opção de propriedade:

```powershell
javac -encoding UTF-8 --release 8 -d out '@fontes.txt'
java '-Dfile.encoding=UTF-8' -cp out atividade.Main
```

Com o próprio JDK 8, omita `--release 8`, que ainda não existia nessa versão:

```sh
javac -encoding UTF-8 -d out @fontes.txt
java -Dfile.encoding=UTF-8 -cp out atividade.Main
```

Também é possível executar cada questão separadamente depois de compilar:

```sh
java -cp out atividade.questao3.PrincipalSmartphone
java -cp out atividade.questao4.PrincipalOperacoes
java -cp out atividade.questao5.PrincipalDocumentos
```

## Saída esperada do programa completo

```text
=== Questão 3: Smartphone ===
Realizando uma chamada...
Atendendo uma chamada...
Exibindo uma página da internet...
Adicionando uma nova aba...
Tocando uma música...
Música pausada.

=== Questão 4: Operações com lambdas ===
Soma: 12.0
Subtração: 8.0
Multiplicação: 20.0
Divisão: 5.0
Erro: Não é possível dividir por zero.

=== Questão 5: Documentos ===
Imprimindo relatório financeiro: Balanço mensal
Enviando relatório financeiro 'Balanço mensal' para destinatario@example.com
Imprimindo contrato: Prestação de serviços
Imprimindo relatório financeiro: Fluxo de caixa
Enviando relatório financeiro 'Fluxo de caixa' para destinatario@example.com
```
