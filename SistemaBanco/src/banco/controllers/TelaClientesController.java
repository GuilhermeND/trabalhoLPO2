package banco.controllers;

import banco.entity.Cliente;
import banco.models.ModelClientes;
import banco.models.ModelContas;

import java.util.List;

public class TelaClientesController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    public TelaClientesController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    public List<Cliente> listarTodos() {
        return modelClientes.listarTodos();
    }

    public void adicionar(Cliente cliente) {
        modelClientes.adicionar(cliente);
    }

    public void atualizar(Cliente cliente) {
        modelClientes.atualizar(cliente);
    }

    public boolean excluirClienteEContas(Cliente cliente) {
        modelContas.excluirContasDoCliente(cliente);
        return modelClientes.excluir(cliente);
    }

    public Cliente buscarPorCpf(String cpf) {
        return modelClientes.buscarPorCpf(cpf);
    }

    public List<Cliente> buscar(String termo) {
        return modelClientes.buscar(termo);
    }

    public List<Cliente> ordenar(String campo, List<Cliente> clientes) {
        return modelClientes.ordenar(campo, clientes);
    }
}

