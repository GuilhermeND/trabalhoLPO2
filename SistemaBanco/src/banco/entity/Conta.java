package banco.entity;

// Classe abstrata que concentra os dados e comportamentos comuns de qualquer conta bancaria.
public abstract class Conta implements ContaI {
    // Sequencia simples usada para gerar numeros de conta quando o objeto e criado em memoria.
    private static int PROXIMO_NUMERO = 1000;

    private Cliente dono;
    private int numero;
    protected double saldo;

    // Construtor base usado pelas subclasses para definir dono, numero e saldo inicial.
    public Conta(Cliente dono, double depositoInicial) {
        this.dono = dono;
        this.numero = PROXIMO_NUMERO++;
        this.saldo = depositoInicial;
    }

    // Retorna o cliente dono da conta.
    public Cliente getDono() {
        return dono;
    }

    // Retorna o numero identificador da conta.
    public int getNumero() {
        return numero;
    }

    // Retorna o saldo atual da conta.
    public double getSaldo() {
        return saldo;
    }

    // Atualiza o numero da conta, usado principalmente apos carregar ou inserir no banco.
    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Atualiza o saldo da conta, usado ao reconstruir objetos vindos do banco.
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Deposita valores positivos na conta e rejeita valores invalidos.
    @Override
    public boolean deposita(double valor) {
        if (valor <= 0) {
            return false;
        }
        this.saldo += valor;
        return true;
    }

    // Valida a regra basica de saque: o valor precisa ser positivo.
    // As subclasses completam a regra conforme o tipo de conta.
    @Override
    public boolean saca(double valor) {
        return valor > 0;
    }

    // Obriga cada tipo de conta a implementar sua propria forma de remuneracao.
    @Override
    public abstract void remunera();
}
