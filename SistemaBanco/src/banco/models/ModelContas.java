package banco.models;

import banco.dao.ContaDAO;
import banco.entity.Cliente;
import banco.entity.Conta;

import java.util.List;

public class ModelContas {
    private final ContaDAO contaDAO;

    public ModelContas() {
        this.contaDAO = new ContaDAO();
    }

    public void inicializarContasDeTeste(ModelClientes modelClientes) {
        // Os dados agora ficam no PostgreSQL. Insira dados de teste pelo script SQL ou pela tela.
    }

    public List<Conta> listarTodas() {
        return contaDAO.listarTodas();
    }

    public void adicionar(Conta conta) {
        contaDAO.inserir(conta);
    }

    public void excluirContasDoCliente(Cliente cliente) {
        contaDAO.excluirContasDoCliente(cliente);
    }

    public Conta buscarContaPorCpfCliente(String cpf) {
        return contaDAO.buscarContaPorCpfCliente(cpf);
    }

    public boolean sacar(Conta conta, double valor) {
        boolean sucesso = conta.saca(valor);
        if (sucesso) {
            contaDAO.atualizarSaldo(conta);
        }
        return sucesso;
    }

    public boolean depositar(Conta conta, double valor) {
        boolean sucesso = conta.deposita(valor);
        if (sucesso) {
            contaDAO.atualizarSaldo(conta);
        }
        return sucesso;
    }

    public void remunerar(Conta conta) {
        conta.remunera();
        contaDAO.atualizarSaldo(conta);
    }
}

