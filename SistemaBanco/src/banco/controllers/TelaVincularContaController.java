package banco.controllers;

import banco.entity.Cliente;
import banco.entity.Conta;
import banco.models.ModelClientes;
import banco.models.ModelContas;

import java.util.List;

// Controller que atende a tela de criacao e vinculo de contas a clientes.
public class TelaVincularContaController {
    private final ModelClientes modelClientes;
    private final ModelContas modelContas;

    // Recebe os models necessarios para listar clientes e gravar contas.
    public TelaVincularContaController(ModelClientes modelClientes, ModelContas modelContas) {
        this.modelClientes = modelClientes;
        this.modelContas = modelContas;
    }

    // Lista os clientes disponiveis para selecao na tela.
    public List<Cliente> listarClientes() {
        return modelClientes.listarTodos();
    }

    // Verifica se o cliente ja possui conta vinculada.
    public Conta buscarContaPorCpfCliente(String cpf) {
        return modelContas.buscarContaPorCpfCliente(cpf);
    }

    // Grava a nova conta vinculada ao cliente escolhido.
    public void vincularConta(Conta conta) {
        modelContas.adicionar(conta);
    }
}
