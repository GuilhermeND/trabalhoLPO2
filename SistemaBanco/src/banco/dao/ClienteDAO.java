package banco.dao;

import banco.modelo.Cliente;
import banco.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    public List<Cliente> listarTodos() {
        String sql = "SELECT id, nome, sobrenome, rg, cpf, endereco FROM clientes ORDER BY id";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
            return clientes;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes.", e);
        }
    }

    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, sobrenome, rg, cpf, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherStatement(cliente, stmt);
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir cliente.", e);
        }
    }

    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, sobrenome = ?, rg = ?, endereco = ? WHERE cpf = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente.", e);
        }
    }

    public boolean excluir(Cliente cliente) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getCpf());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente.", e);
        }
    }

    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT id, nome, sobrenome, rg, cpf, endereco FROM clientes WHERE cpf = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por CPF.", e);
        }
    }

    public List<Cliente> buscar(String termo) {
        String sql = """
                SELECT id, nome, sobrenome, rg, cpf, endereco
                FROM clientes
                WHERE LOWER(nome) LIKE LOWER(?)
                   OR LOWER(sobrenome) LIKE LOWER(?)
                   OR rg LIKE ?
                   OR cpf LIKE ?
                ORDER BY id
                """;
        String filtro = "%" + termo + "%";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, filtro);
            stmt.setString(2, filtro);
            stmt.setString(3, filtro);
            stmt.setString(4, filtro);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Cliente> clientes = new ArrayList<>();
                while (rs.next()) {
                    clientes.add(mapearCliente(rs));
                }
                return clientes;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes.", e);
        }
    }

    private void preencherStatement(Cliente cliente, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, cliente.getNome());
        stmt.setString(2, cliente.getSobrenome());
        stmt.setString(3, cliente.getRg());
        stmt.setString(4, cliente.getCpf());
        stmt.setString(5, cliente.getEndereco());
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("sobrenome"),
                rs.getString("rg"),
                rs.getString("cpf"),
                rs.getString("endereco")
        );
    }
}

