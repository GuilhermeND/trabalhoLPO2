package banco.dao;

import banco.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbInitializer {
    private DbInitializer() {
    }

    public static void inicializarDadosPadrao() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            inserirClienteEContaCorrenteSeNaoExistir(
                    conn,
                    "Amanda",
                    "Cristine",
                    "1234567",
                    "11111111111",
                    "Rua A",
                    1000.00,
                    500.00
            );

            inserirClienteEContaInvestimentoSeNaoExistir(
                    conn,
                    "Eduardo",
                    "Almeida",
                    "7654321",
                    "22222222222",
                    "Rua B",
                    5000.00,
                    1000.00,
                    100.00
            );

            inserirClienteEContaCorrenteSeNaoExistir(
                    conn,
                    "Guilherme",
                    "Gemniczak",
                    "9876543",
                    "33333333333",
                    "Rua C",
                    750.00,
                    300.00
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar dados padrao do banco.", e);
        }
    }

    private static void inserirClienteEContaCorrenteSeNaoExistir(
            Connection conn,
            String nome,
            String sobrenome,
            String rg,
            String cpf,
            String endereco,
            double saldo,
            double limite
    ) throws SQLException {
        long clienteId = buscarOuInserirCliente(conn, nome, sobrenome, rg, cpf, endereco);
        if (!contaExisteParaCliente(conn, clienteId)) {
            inserirContaCorrente(conn, clienteId, saldo, limite);
        }
    }

    private static void inserirClienteEContaInvestimentoSeNaoExistir(
            Connection conn,
            String nome,
            String sobrenome,
            String rg,
            String cpf,
            String endereco,
            double saldo,
            double montanteMinimo,
            double depositoMinimo
    ) throws SQLException {
        long clienteId = buscarOuInserirCliente(conn, nome, sobrenome, rg, cpf, endereco);
        if (!contaExisteParaCliente(conn, clienteId)) {
            inserirContaInvestimento(conn, clienteId, saldo, montanteMinimo, depositoMinimo);
        }
    }

    private static long buscarOuInserirCliente(
            Connection conn,
            String nome,
            String sobrenome,
            String rg,
            String cpf,
            String endereco
    ) throws SQLException {
        Long idExistente = buscarClienteIdPorCpf(conn, cpf);
        if (idExistente != null) {
            return idExistente;
        }

        String sql = "INSERT INTO clientes (nome, sobrenome, rg, cpf, endereco) "
                + "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setString(3, rg);
            stmt.setString(4, cpf);
            stmt.setString(5, endereco);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }
        }

        throw new SQLException("Nao foi possivel inserir cliente padrao: " + cpf);
    }

    private static Long buscarClienteIdPorCpf(Connection conn, String cpf) throws SQLException {
        String sql = "SELECT id FROM clientes WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                }
            }
        }
        return null;
    }

    private static boolean contaExisteParaCliente(Connection conn, long clienteId) throws SQLException {
        String sql = "SELECT 1 FROM contas WHERE cliente_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void inserirContaCorrente(Connection conn, long clienteId, double saldo, double limite) throws SQLException {
        String sql = "INSERT INTO contas "
                + "(numero, cliente_id, tipo, saldo, limite, montante_minimo, deposito_minimo) "
                + "VALUES (nextval('numero_conta_seq'), ?, 'CORRENTE', ?, ?, NULL, NULL)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clienteId);
            stmt.setDouble(2, saldo);
            stmt.setDouble(3, limite);
            stmt.executeUpdate();
        }
    }

    private static void inserirContaInvestimento(
            Connection conn,
            long clienteId,
            double saldo,
            double montanteMinimo,
            double depositoMinimo
    ) throws SQLException {
        String sql = "INSERT INTO contas "
                + "(numero, cliente_id, tipo, saldo, limite, montante_minimo, deposito_minimo) "
                + "VALUES (nextval('numero_conta_seq'), ?, 'INVESTIMENTO', ?, NULL, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clienteId);
            stmt.setDouble(2, saldo);
            stmt.setDouble(3, montanteMinimo);
            stmt.setDouble(4, depositoMinimo);
            stmt.executeUpdate();
        }
    }
}

