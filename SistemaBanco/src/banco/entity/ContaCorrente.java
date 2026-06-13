package banco.entity;

public class ContaCorrente extends Conta {
    private double limite;

    public ContaCorrente(Cliente dono, double depositoInicial, double limite) {
        super(dono, depositoInicial);
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }

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

    @Override
    public void remunera() {
        this.saldo *= 1.01;
    }

    @Override
    public String toString() {
        return "Conta Corrente Nº " + getNumero()
                + " (Dono: " + getDono().getNome()
                + ", Saldo: R$ " + String.format("%.2f", saldo)
                + ", Limite: R$ " + String.format("%.2f", limite) + ")";
    }
}

