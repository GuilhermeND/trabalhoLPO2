package banco.controllers;

import banco.entity.Cliente;
import banco.entity.Conta;
import banco.models.ModelClientes;
import banco.models.ModelContas;

public class TelaOperacoesController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    public TelaOperacoesController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        return modelClientes.buscarPorCpf(cpf);
    }

    public Conta buscarContaPorCpfCliente(String cpf) {
        return modelContas.buscarContaPorCpfCliente(cpf);
    }

    public boolean sacar(Conta conta, double valor) {
        return modelContas.sacar(conta, valor);
    }

    public boolean depositar(Conta conta, double valor) {
        return modelContas.depositar(conta, valor);
    }

    public void remunerar(Conta conta) {
        modelContas.remunerar(conta);
    }
}

