package banco.controllers;

import banco.entity.Cliente;
import banco.entity.Conta;
import banco.models.ModelClientes;
import banco.models.ModelContas;

// Controller que liga a tela de operacoes aos models de clientes e contas.
public class TelaOperacoesController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    // Recebe os models necessarios para buscar conta e executar operacoes.
    public TelaOperacoesController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    // Localiza o cliente usado como base para a busca da conta.
    public Cliente buscarClientePorCpf(String cpf) {
        return modelClientes.buscarPorCpf(cpf);
    }

    // Localiza a conta vinculada ao CPF informado.
    public Conta buscarContaPorCpfCliente(String cpf) {
        return modelContas.buscarContaPorCpfCliente(cpf);
    }

    // Solicita ao model a execucao do saque.
    public boolean sacar(Conta conta, double valor) {
        return modelContas.sacar(conta, valor);
    }

    // Solicita ao model a execucao do deposito.
    public boolean depositar(Conta conta, double valor) {
        return modelContas.depositar(conta, valor);
    }

    // Solicita ao model a aplicacao da remuneracao da conta.
    public void remunerar(Conta conta) {
        modelContas.remunerar(conta);
    }
}
