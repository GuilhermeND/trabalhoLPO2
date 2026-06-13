package banco.entity;

public class ContaInvestimento extends Conta {
    private double montanteMinimo;
    private double depositoMinimo;

    public ContaInvestimento(Cliente dono, double depositoInicial, double montanteMinimo, double depositoMinimo) {
        super(dono, 0);
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;

        if (depositoInicial > 0) {
            deposita(depositoInicial);
        }
    }

    public ContaInvestimento(Cliente dono, double saldo, double montanteMinimo, double depositoMinimo, boolean carregarDoBanco) {
        super(dono, saldo);
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;
    }

    public double getMontanteMinimo() {
        return montanteMinimo;
    }

    public double getDepositoMinimo() {
        return depositoMinimo;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor < this.depositoMinimo) {
            return false;
        }
        return super.deposita(valor);
    }

    @Override
    public boolean saca(double valor) {
        if (!super.saca(valor)) {
            return false;
        }

        if (this.saldo - valor < this.montanteMinimo) {
            return false;
        }

        this.saldo -= valor;
        return true;
    }

    @Override
    public void remunera() {
        this.saldo *= 1.02;
    }

    @Override
    public String toString() {
        return "Conta Investimento Nº " + getNumero()
                + " (Dono: " + getDono().getNome()
                + ", Saldo: R$ " + String.format("%.2f", saldo)
                + ", Mínimo: R$ " + String.format("%.2f", montanteMinimo) + ")";
    }
}

