import exceptions.ValorInvalidoException;

public class ContaPoupanca extends Conta {
    double taxa;
    
    public ContaPoupanca(int id, String nome, double taxa){
        super(id, nome);
        this.taxa = taxa;
    }
    
    public double getTaxa(){
        return taxa;
    }
    
    public ContaPoupanca(int id, String nome, double taxa, double saldo) throws ValorInvalidoException {
        super(id, nome, saldo);
        this.taxa = taxa;
    }

    void passarMes() throws ValorInvalidoException {
        depositar(getSaldo() * taxa);
    }
}
