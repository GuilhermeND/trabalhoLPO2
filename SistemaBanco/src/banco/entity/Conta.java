package banco.entity;

public abstract class Conta implements ContaI {
    private static int PROXIMO_NUMERO = 1000;

    private Cliente dono;
    private int numero;
    protected double saldo;

    public Conta(Cliente dono, double depositoInicial) {
        this.dono = dono;
        this.numero = PROXIMO_NUMERO++;
        this.saldo = depositoInicial;
    }

    public Cliente getDono() {
        return dono;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.saldo += valor;
        return true;
    }

    @Override
    public boolean saca(double valor) {
        return valor > 0;
    }

    @Override
    public abstract void remunera();
}

