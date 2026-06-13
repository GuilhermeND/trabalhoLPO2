package banco.dao;

import banco.entity.Cliente;
import banco.entity.Conta;
import banco.entity.ContaCorrente;
import banco.entity.ContaInvestimento;
import banco.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO implements Dao<Conta> {
    @Override
    public List<Conta> listarTodos() {
        String sql = "SELECT c.numero, c.tipo, c.saldo, c.limite, c.montante_minimo, c.deposito_minimo, "
                + "cl.id AS cliente_id, cl.nome, cl.sobrenome, cl.rg, cl.cpf, cl.endereco "
                + "FROM contas c "
                + "JOIN clientes cl ON cl.id = c.cliente_id "
                + "ORDER BY c.numero";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<Conta> contas = new ArrayList<>();
            while (rs.next()) {
                contas.add(mapearConta(rs));
            }
            return contas;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar contas.", e);
        }
    }

    public List<Conta> listarTodas() {
        return listarTodos();
    }

    @Override
    public void inserir(Conta conta) {
        String sql = "INSERT INTO contas "
                + "(numero, cliente_id, tipo, saldo, limite, montante_minimo, deposito_minimo) "
                + "VALUES (nextval('numero_conta_seq'), ?, ?, ?, ?, ?, ?) "
                + "RETURNING numero";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (conta.getDono().getId() == null) {
                throw new IllegalArgumentException("Cliente precisa existir no banco antes de criar conta.");
            }
            stmt.setLong(1, conta.getDono().getId());
            stmt.setString(2, getTipo(conta));
            stmt.setDouble(3, conta.getSaldo());
            preencherCamposEspecificos(conta, stmt);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    conta.setNumero(rs.getInt("numero"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir conta.", e);
        }
    }

    public Conta buscarContaPorCpfCliente(String cpf) {
        String sql = "SELECT c.numero, c.tipo, c.saldo, c.limite, c.montante_minimo, c.deposito_minimo, "
                + "cl.id AS cliente_id, cl.nome, cl.sobrenome, cl.rg, cl.cpf, cl.endereco "
                + "FROM contas c "
                + "JOIN clientes cl ON cl.id = c.cliente_id "
                + "WHERE cl.cpf = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearConta(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta por CPF.", e);
        }
    }

    public void atualizarSaldo(Conta conta) {
        String sql = "UPDATE contas SET saldo = ? WHERE numero = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, conta.getSaldo());
            stmt.setInt(2, conta.getNumero());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo da conta.", e);
        }
    }

    public void excluirContasDoCliente(Cliente cliente) {
        String sql = "DELETE FROM contas WHERE cliente_id = (SELECT id FROM clientes WHERE cpf = ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir contas do cliente.", e);
        }
    }

    private void preencherCamposEspecificos(Conta conta, PreparedStatement stmt) throws SQLException {
        if (conta instanceof ContaCorrente) {
            ContaCorrente contaCorrente = (ContaCorrente) conta;
            stmt.setDouble(4, contaCorrente.getLimite());
            stmt.setNull(5, java.sql.Types.NUMERIC);
            stmt.setNull(6, java.sql.Types.NUMERIC);
        } else if (conta instanceof ContaInvestimento) {
            ContaInvestimento contaInvestimento = (ContaInvestimento) conta;
            stmt.setNull(4, java.sql.Types.NUMERIC);
            stmt.setDouble(5, contaInvestimento.getMontanteMinimo());
            stmt.setDouble(6, contaInvestimento.getDepositoMinimo());
        } else {
            throw new IllegalArgumentException("Tipo de conta nao suportado.");
        }
    }

    private String getTipo(Conta conta) {
        if (conta instanceof ContaCorrente) {
            return "CORRENTE";
        }
        if (conta instanceof ContaInvestimento) {
            return "INVESTIMENTO";
        }
        throw new IllegalArgumentException("Tipo de conta nao suportado.");
    }

    private Conta mapearConta(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getLong("cliente_id"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("rg"),
                rs.getString("cpf"),
                rs.getString("endereco")
        );
        String tipo = rs.getString("tipo");
        Conta conta;
        if ("CORRENTE".equals(tipo)) {
            conta = new ContaCorrente(cliente, rs.getDouble("saldo"), rs.getDouble("limite"));
        } else if ("INVESTIMENTO".equals(tipo)) {
            conta = new ContaInvestimento(
                    cliente,
                    rs.getDouble("saldo"),
                    rs.getDouble("montante_minimo"),
                    rs.getDouble("deposito_minimo"),
                    true
            );
        } else {
            throw new SQLException("Tipo de conta desconhecido: " + tipo);
        }
        conta.setNumero(rs.getInt("numero"));
        conta.setSaldo(rs.getDouble("saldo"));
        return conta;
    }
}
