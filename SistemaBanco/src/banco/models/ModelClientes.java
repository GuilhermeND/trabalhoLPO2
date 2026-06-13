package banco.models;

import banco.dao.ClienteDAO;
import banco.entity.Cliente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GerenciadorClientes {
    private final ClienteDAO clienteDAO;
    private final GerenciadorContas gerenciadorContas;

    public GerenciadorClientes(GerenciadorContas gerenciadorContas) {
        this.clienteDAO = new ClienteDAO();
        this.gerenciadorContas = gerenciadorContas;
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public void adicionar(Cliente cliente) {
        clienteDAO.inserir(cliente);
    }

    public void atualizar(Cliente cliente) {
        clienteDAO.atualizar(cliente);
    }

    public boolean excluir(Cliente cliente) {
        return clienteDAO.excluir(cliente);
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteDAO.buscarPorCpf(cpf);
    }

    public List<Cliente> buscar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }
        return clienteDAO.buscar(termo.trim());
    }

    public List<Cliente> ordenar(String campo, List<Cliente> lista) {
        List<Cliente> listaOrdenada = new ArrayList<>(lista);

        if (campo.equalsIgnoreCase("nome")) {
            Collections.sort(listaOrdenada);
        } else if (campo.equalsIgnoreCase("sobrenome")) {
            Collections.sort(listaOrdenada, Comparator.comparing(Cliente::getSobrenome));
        } else if (campo.equalsIgnoreCase("salário") || campo.equalsIgnoreCase("salario")) {
            Collections.sort(listaOrdenada, (c1, c2) -> {
                double saldo1 = gerenciadorContas.buscarContaPorCpfCliente(c1.getCpf()) != null
                        ? gerenciadorContas.buscarContaPorCpfCliente(c1.getCpf()).getSaldo()
                        : 0.0;
                double saldo2 = gerenciadorContas.buscarContaPorCpfCliente(c2.getCpf()) != null
                        ? gerenciadorContas.buscarContaPorCpfCliente(c2.getCpf()).getSaldo()
                        : 0.0;
                return Double.compare(saldo2, saldo1);
            });
        }
        return listaOrdenada;
    }
}

