package banco.entity;

// Representa uma conta corrente, que permite saque usando saldo e limite.
public class ContaCorrente extends Conta {
    private double limite;

    // Cria uma conta corrente com dono, deposito inicial e limite disponivel.
    public ContaCorrente(Cliente dono, double depositoInicial, double limite) {
        super(dono, depositoInicial);
        this.limite = limite;
    }

    // Retorna o limite de credito da conta corrente.
    public double getLimite() {
        return limite;
    }

    // Realiza saque respeitando o limite permitido para saldo negativo.
    @Override
    public boolean saca(double valor) {
        if (!super.saca(valor)) {
            return false;
        }

        double novoSaldo = this.saldo - valor;
        if (novoSaldo < -this.limite) {
            return false;
        }

        this.saldo = novoSaldo;
        return true;
    }

    // Aplica rendimento de 1% ao saldo da conta corrente.
    @Override
    public void remunera() {
        this.saldo *= 1.01;
    }

    // Monta um texto resumido com os dados principais da conta corrente.
    @Override
    public String toString() {
        return "Conta Corrente Nº " + getNumero()
                + " (Dono: " + getDono().getNome()
                + ", Saldo: R$ " + String.format("%.2f", saldo)
                + ", Limite: R$ " + String.format("%.2f", limite) + ")";
    }
}
