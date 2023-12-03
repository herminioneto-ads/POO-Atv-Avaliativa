import java.util.Stack;

import exceptions.ContaJaExisteException;
import exceptions.ContaNaoEncontradaException;
import exceptions.SaldoInsuficienteException;
import exceptions.ValorInvalidoException;
import utils.IOUtils;

public class App {
    Stack<Runnable> pilhaVisualizacao = new Stack<>();
    Banco banco = new Banco();
    Conta contaLogada;
    String menuAtual;
    int escolha = 0;

    private Conta entrar() throws ContaNaoEncontradaException {
        int idConta = IOUtils.getInt("Qual é o número da sua conta? \n> ");
        Conta contaEncontrada = banco.encontrarContaPorId(idConta);
        return contaEncontrada;
    }

    private void criarConta(int tipo) {
        String nome = IOUtils.getText("Digite o seu nome: ");

        Conta contaCriada;
        switch (tipo) {
            case 1: // Conta Normal
                contaCriada = banco.criarConta(nome);
                break;
            case 2: // Conta Poupança
                try {
                    double taxa = IOUtils.getPercent("Digite a taxa (0 - 100)% : ");
                    contaCriada = banco.criarContaPoupanca(nome, taxa);
                } catch (ValorInvalidoException e) {
                    IOUtils.show("O valor da taxa deve estar entre 0 e 100 !");
                    return;
                }
                break;

            default:
                return;
        }
        try {
            banco.adicionarConta(contaCriada);
            IOUtils.show("Conta " + contaCriada.getIdentificador() + " criada com sucesso");
        } catch (ContaJaExisteException e) {
            IOUtils.show(e.getMessage());
        }
    }

    private void depositar() {
        double valor = IOUtils.getDouble("Digite o valor a ser depositado (R$): ");
        try {
            contaLogada.depositar(valor);
            IOUtils.show("Depósito realizado com sucesso!");
        } catch (ValorInvalidoException e) {
            IOUtils.show("Valor de depósito inválido!");
        }
    }

    private void sacar() {
        double valor = IOUtils.getDouble("Digite o valor a ser sacado (R$): ");
        try {
            contaLogada.sacar(valor);
            IOUtils.show("Saque realizado com sucesso!");
        } catch (ValorInvalidoException e) {
            IOUtils.show("Valor de saque inválido!");
        } catch (SaldoInsuficienteException e) {
            IOUtils.show("Saldo insuficiente para realizar o saque!");
        }
    }

    private void transferir() {
        int idDestino = IOUtils.getInt("Digite o número da conta para onde você quer transferir: ");
        Conta destino;
        try {
            destino = banco.encontrarContaPorId(idDestino);
        } catch (ContaNaoEncontradaException e) {
            IOUtils.show(e.getMessage());
            return;
        }
        double valor = IOUtils.getDouble("Digite o valor a ser transferido (R$): ");
        try {
            contaLogada.transferir(destino, valor);
            IOUtils.show("Transferência para " + destino.getNome() + " realizada com sucesso!");
        } catch (ValorInvalidoException e) {
            IOUtils.show("Valor de transferência inválido!");
        } catch (SaldoInsuficienteException e) {
            IOUtils.show("Saldo insuficiente para realizar a transferência!");
        }
    }

    private void menuInicial() {
        String contasExibir = banco.getQuantidadeContas() > 0 ? "Contas Disponíveis: " + banco.getQuantidadeContas() + '\n' : "";
        IOUtils.clearScreen();
        IOUtils.show("-> Banco <- \n" + contasExibir + "_".repeat(40));
        escolha = IOUtils.getInt("1 - Menu Conta\n2 - Abrir Conta no Banco\n0 - Sair do APP \n> ");
        switch (escolha) {
            case 1:
                try {
                    contaLogada = entrar();
                    pilhaVisualizacao.add(this::menuPrincipal);
                } catch (ContaNaoEncontradaException e) {
                    IOUtils.show("Não foi possível fazer login...");
                    IOUtils.show(e.getMessage());
                }
                break;

            case 2:
                pilhaVisualizacao.push(this::menuCriarConta);
                break;

            case 0:
                pilhaVisualizacao.pop();
                break;

            default:
                IOUtils.show("Opção inválida!");
                break;
        }
    }

    private void menuPrincipal() {
        IOUtils.clearScreen();
        String tipoConta = contaLogada instanceof ContaPoupanca ?
                "Conta Poupança" : "Conta Corrente";
        String mensagemFinal = contaLogada instanceof ContaPoupanca ?
                String.format(" > Rendendo %.2f %s por mês", ((ContaPoupanca) contaLogada).getTaxa(), "%") : "";

        IOUtils.show(String.format("+++ MENU CONTA (%s) +++\n>Número da conta: %d\n>Titular: %s\n>>TOTAL: R$%.2f<<\n%s",
                tipoConta, contaLogada.getIdentificador(), contaLogada.getNome(), contaLogada.getSaldo(), mensagemFinal));
        escolha = IOUtils.getInt("1 - Depositar\n2 - Sacar\n3 - Transferir\n0 - Deslogar \n> ");
        switch (escolha) {
            case 1:
                depositar();
                break;
            case 2:
                sacar();
                break;
            case 3:
                transferir();
                break;
            case 0:
                pilhaVisualizacao.pop();
                break;

            default:
                IOUtils.show("Opção Inválida!");
                break;
        }
    }

    private void menuCriarConta() {
        IOUtils.clearScreen();
        escolha = IOUtils.getInt("1 - Criar conta corrente\n2 - Criar conta poupança\n0 - Cancelar \n> ");
        switch (escolha) {
            case 1:
                criarConta(1); // Criando uma conta normal
                pilhaVisualizacao.pop();
                break;

            case 2:
                criarConta(2); // Criando uma conta poupança
                pilhaVisualizacao.pop();
                break;

            case 0:
                pilhaVisualizacao.pop();
                break;

            default:
                IOUtils.show("Opção Inválida!");
                break;
        }
    }

    public void executar() {
        pilhaVisualizacao.push(this::menuInicial);

        do {
            try {
                Runnable topoPilha = pilhaVisualizacao.peek();
                topoPilha.run();
            } catch (NumberFormatException e) {
                IOUtils.show("Digite um valor válido...");
            } catch (Exception e) {
                IOUtils.show("Ocorreu um erro...");
                IOUtils.show("ERRO! " + e.getMessage());
            } finally {
                IOUtils.enterToContinue();
            }
        } while (!pilhaVisualizacao.empty());

        IOUtils.closeScanner();
    }
}

class Auxiliar {
    public static void main(String[] args) {
        App novoAplicativo = new App();
        novoAplicativo.executar();
    }
}
