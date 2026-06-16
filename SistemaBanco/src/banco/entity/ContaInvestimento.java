package banco.entity;

// Representa uma conta investimento, com deposito minimo e saldo minimo obrigatorio.
public class ContaInvestimento extends Conta {
    private double montanteMinimo;
    private double depositoMinimo;

    // Cria uma conta investimento nova e tenta aplicar o deposito inicial pelas regras da conta.
    public ContaInvestimento(Cliente dono, double depositoInicial, double montanteMinimo, double depositoMinimo) {
        super(dono, 0);
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;

        if (depositoInicial > 0) {
            deposita(depositoInicial);
        }
    }

    // Construtor usado ao carregar a conta do banco, preservando o saldo ja existente.
    public ContaInvestimento(Cliente dono, double saldo, double montanteMinimo, double depositoMinimo, boolean carregarDoBanco) {
        super(dono, saldo);
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;
    }

    // Retorna o saldo minimo que deve permanecer na conta.
    public double getMontanteMinimo() {
        return montanteMinimo;
    }

    // Retorna o valor minimo permitido para cada deposito.
    public double getDepositoMinimo() {
        return depositoMinimo;
    }

    // Deposita somente quando o valor atinge o deposito minimo configurado.
    @Override
    public boolean deposita(double valor) {
        if (valor < this.depositoMinimo) {
            return false;
        }
        return super.deposita(valor);
    }

    // Realiza saque somente se o saldo final continuar acima do montante minimo.
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

    // Aplica rendimento de 2% ao saldo da conta investimento.
    @Override
    public void remunera() {
        this.saldo *= 1.02;
    }

    // Monta um texto resumido com os dados principais da conta investimento.
    @Override
    public String toString() {
        return "Conta Investimento Nº " + getNumero()
                + " (Dono: " + getDono().getNome()
                + ", Saldo: R$ " + String.format("%.2f", saldo)
                + ", Mínimo: R$ " + String.format("%.2f", montanteMinimo) + ")";
    }
}
