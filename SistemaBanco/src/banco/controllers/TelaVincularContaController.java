package banco.controllers;

import banco.entity.Cliente;
import banco.entity.Conta;
import banco.models.ModelClientes;
import banco.models.ModelContas;

import java.util.List;

public class TelaVincularContaController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    public TelaVincularContaController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    public List<Cliente> listarClientes() {
        return modelClientes.listarTodos();
    }

    public Conta buscarContaPorCpfCliente(String cpf) {
        return modelContas.buscarContaPorCpfCliente(cpf);
    }

    public void vincularConta(Conta conta) {
        modelContas.adicionar(conta);
    }
}

