package repository;

import enums.StatusCaixa;
import model.Caixa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaixaRepository {

    public void abrir(Caixa c) {
        String sql = "INSERT INTO caixa (data_abertura, saldo_inicial, status, id_funcionario) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(c.getDataAbertura()));
            ps.setDouble(2, c.getSaldoInicial());
            ps.setString(3, c.getStatus().name());
            ps.setInt(4, c.getIdFuncionario());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) c.setId(rs.getInt(1));
            System.out.println("Caixa aberto com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao abrir caixa: " + e.getMessage());
        }
    }

    public void fechar(int idCaixa, double saldoFinal) {
        String sql = "UPDATE caixa SET data_fechamento=NOW(), saldo_final=?, status='FECHADO' WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, saldoFinal);
            ps.setInt(2, idCaixa);
            ps.executeUpdate();
            System.out.println("Caixa fechado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao fechar caixa: " + e.getMessage());
        }
    }

    public Caixa buscarCaixaAberto() {
        String sql = "SELECT c.*, f.nome as nome_funcionario FROM caixa c JOIN funcionario f ON c.id_funcionario = f.id WHERE c.status = 'ABERTO' LIMIT 1";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.out.println("Erro ao buscar caixa aberto: " + e.getMessage());
        }
        return null;
    }

    public List<Caixa> listar() {
        List<Caixa> lista = new ArrayList<>();
        String sql = "SELECT c.*, f.nome as nome_funcionario FROM caixa c JOIN funcionario f ON c.id_funcionario = f.id ORDER BY c.data_abertura DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar caixas: " + e.getMessage());
        }
        return lista;
    }

    private Caixa mapear(ResultSet rs) throws SQLException {
        Caixa c = new Caixa();
        c.setId(rs.getInt("id"));
        c.setDataAbertura(rs.getTimestamp("data_abertura").toLocalDateTime());
        Timestamp fechamento = rs.getTimestamp("data_fechamento");
        if (fechamento != null) c.setDataFechamento(fechamento.toLocalDateTime());
        c.setSaldoInicial(rs.getDouble("saldo_inicial"));
        c.setSaldoFinal(rs.getDouble("saldo_final"));
        c.setStatus(StatusCaixa.valueOf(rs.getString("status")));
        c.setIdFuncionario(rs.getInt("id_funcionario"));
        c.setNomeFuncionario(rs.getString("nome_funcionario"));
        return c;
    }
}
