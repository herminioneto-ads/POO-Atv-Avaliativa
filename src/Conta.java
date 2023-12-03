import exceptions.SaldoInsuficienteException;
import exceptions.ValorInvalidoException;

public class Conta {
    public int identificador;
    private String nome;
    private double saldo;

    public Conta(Integer identificador, String nome, double saldo) throws ValorInvalidoException {
        this.identificador = identificador;
        this.nome = nome;
        if (saldo < 0)
            throw new ValorInvalidoException("Saldo deve ser positivo!");
        this.saldo = saldo;
    }

    public Conta(Integer identificador, String nome) {
        this.identificador = identificador;
        this.nome = nome;
        this.saldo = 0;
    }

    private void validarValor(double valor) throws ValorInvalidoException {
        if (valor < 0) {
            throw new ValorInvalidoException("ERRO: Valor inválido");
        }
    }

    private boolean podeSacar(double quantia) {
        double resultado = this.saldo - quantia;
        return resultado >= 0;
    }

    public int getIdentificador() {
        return this.identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return this.saldo;
    }

    public void sacar(double quantia) throws SaldoInsuficienteException, ValorInvalidoException {
        validarValor(quantia);
        if (!podeSacar(quantia)) {
            throw new SaldoInsuficienteException("ERRO: Saldo Insuficiente");
        }
        this.saldo -= quantia;
    }

    public void depositar(double quantia) throws ValorInvalidoException {
        validarValor(quantia);
        this.saldo += quantia;
    }

    public void transferir(Conta contaDestino, double quantia) throws SaldoInsuficienteException, ValorInvalidoException {
        this.sacar(quantia);
        contaDestino.depositar(quantia);
    }

    @Override
    public String toString() {
        return "Número: " + getIdentificador() + " | " + "Titular: " + getNome(); 
    }

    public static void main(String[] args) throws ValorInvalidoException, SaldoInsuficienteException{
        Conta conta1 = new Conta(1, "Herminio", 1);
        Conta conta2 = new Conta(2, "João", 10);
        conta1.transferir(conta2, 100);

    }
}

