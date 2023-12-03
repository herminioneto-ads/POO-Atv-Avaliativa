import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.ContaJaExisteException;
import exceptions.ContaException;
import exceptions.ContaNaoEncontradaException;
import exceptions.ContaPoupancaInvalidaException;
import exceptions.ValorInvalidoException;

class Banco {
  List<Conta> contas = new ArrayList<>();
  int proximoId = contas.size();

  Conta criarConta(String nome) {
    int quantidadeContas = proximoId;
    proximoId++;
    double saldoInicial = 0;
    Conta novaConta;
    try {
      novaConta = new Conta(quantidadeContas, nome, saldoInicial);
      return novaConta;

    } catch (ValorInvalidoException e) {
      e.printStackTrace();
    }
    return null;

  }

  ContaPoupanca criarContaPoupanca(String nome, double taxa) {
    int quantidadeContas = proximoId;
    proximoId++;
    double saldoInicial = 0;
    ContaPoupanca novaConta;
    try {
      novaConta = new ContaPoupanca(quantidadeContas, nome, taxa, saldoInicial);
      return novaConta;

    } catch (ValorInvalidoException e) {
      e.printStackTrace();
    }
    return null;

  }
  
  void adicionarConta(Conta conta) throws ContaJaExisteException {

    try{
      Conta existente = encontrarContaPorId(conta.getIdentificador());
      throw new ContaJaExisteException("ERRO: Conta já existe");

    } catch (ContaNaoEncontradaException e) {
      contas.add(conta);

    } catch (ContaException e){
      throw e;
    }
  }

  Conta encontrarContaPorId(int id) throws ContaNaoEncontradaException {
    Optional<Conta> encontrada = contas.stream().filter(elemento -> id == elemento.identificador).findFirst();

    if (encontrada.isEmpty()) {
      throw new ContaNaoEncontradaException("ERRO: Conta não encontrada");
    }
    return encontrada.get();

  }

  void depositar(int numeroConta, double quantia) throws ContaNaoEncontradaException, ValorInvalidoException {
    Conta contaDeposito = encontrarContaPorId(numeroConta);
    contaDeposito.depositar(quantia);
  }

  void sacar(int numeroConta, double quantia) throws ContaException, ValorInvalidoException {
    Conta encontrada = encontrarContaPorId(numeroConta);
    encontrada.sacar(quantia);
  }

  void transferir(int numeroContaDebito, int numeroContaCredito, int quantia) throws ContaException, ValorInvalidoException {
    Conta contaDebito = encontrarContaPorId(numeroContaDebito);
    Conta contaCredito = encontrarContaPorId(numeroContaCredito);
    contaDebito.transferir(contaCredito, quantia);
  }

  void passarMes(int idConta) throws ContaNaoEncontradaException, ContaPoupancaInvalidaException {
    Conta contaEncontrada = encontrarContaPorId(idConta);
    if (!(contaEncontrada instanceof ContaPoupanca)) {
      throw new ContaPoupancaInvalidaException("ERRO: Conta não é poupança");
    }

  }

  public int getQuantidadeContas(){
    return contas.size();
  }

  public static void main(String[] args) throws ValorInvalidoException, ContaException{
    Banco banco = new Banco();
    banco.adicionarConta(new Conta(1, "Herminio", 10));
    banco.adicionarConta(new Conta(2, "João", 10));

    banco.transferir(1, 2, 100);
  }
}
