package banco.controllers;

import banco.entity.Cliente;
import banco.models.ModelClientes;
import banco.models.ModelContas;

import java.util.List;

// Controller que atende as acoes da tela de manutencao de clientes.
public class TelaClientesController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    // Recebe os models usados para consultar clientes e contas relacionadas.
    public TelaClientesController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    // Lista todos os clientes cadastrados.
    public List<Cliente> listarTodos() {
        return modelClientes.listarTodos();
    }

    // Cadastra um novo cliente.
    public void adicionar(Cliente cliente) {
        modelClientes.adicionar(cliente);
    }

    // Atualiza os dados de um cliente existente.
    public void atualizar(Cliente cliente) {
        modelClientes.atualizar(cliente);
    }

    // Exclui as contas vinculadas antes de remover o cliente.
    public boolean excluirClienteEContas(Cliente cliente) {
        modelContas.excluirContasDoCliente(cliente);
        return modelClientes.excluir(cliente);
    }

    // Busca um cliente pelo CPF sem mascara.
    public Cliente buscarPorCpf(String cpf) {
        return modelClientes.buscarPorCpf(cpf);
    }

    // Busca clientes pelo termo informado na tela.
    public List<Cliente> buscar(String termo) {
        return modelClientes.buscar(termo);
    }

    // Ordena a lista de clientes de acordo com o campo escolhido.
    public List<Cliente> ordenar(String campo, List<Cliente> clientes) {
        return modelClientes.ordenar(campo, clientes);
    }
}
