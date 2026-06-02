package repository;

import enums.StatusVenda;
import model.ItemVenda;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaRepository {

    public void cadastrar(Venda v) {
        String sql = "INSERT INTO venda (data_venda, total, status, id_cliente, id_funcionario, id_caixa) VALUES (NOW(), ?, ?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, v.getTotal());
            ps.setString(2, v.getStatus().name());
            if (v.getIdCliente() > 0) ps.setInt(3, v.getIdCliente());
            else ps.setNull(3, Types.INTEGER);
            ps.setInt(4, v.getIdFuncionario());
            ps.setInt(5, v.getIdCaixa());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) v.setId(rs.getInt(1));

            for (ItemVenda item : v.getItens()) {
                item.setIdVenda(v.getId());
                salvarItem(item, con);
            }
            System.out.println("Venda registrada com sucesso! ID: " + v.getId());
        } catch (SQLException e) {
            System.out.println("Erro ao registrar venda: " + e.getMessage());
        }
    }

    private void salvarItem(ItemVenda item, Connection con) throws SQLException {
        String sql = "INSERT INTO item_venda (quantidade, preco_unitario, subtotal, id_venda, id_produto) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, item.getQuantidade());
            ps.setDouble(2, item.getPrecoUnitario());
            ps.setDouble(3, item.getSubtotal());
            ps.setInt(4, item.getIdVenda());
            ps.setInt(5, item.getIdProduto());
            ps.executeUpdate();
        }
    }

    public void atualizarStatus(int idVenda, StatusVenda status) {
        String sql = "UPDATE venda SET status = ? WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, idVenda);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status da venda: " + e.getMessage());
        }
    }

    public List<Venda> listar() {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nome as nome_cliente, f.nome as nome_funcionario " +
                     "FROM venda v " +
                     "LEFT JOIN cliente c ON v.id_cliente = c.id " +
                     "JOIN funcionario f ON v.id_funcionario = f.id " +
                     "ORDER BY v.data_venda DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }
        return lista;
    }

    public List<Venda> listarPorData(String data) {
        List<Venda> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nome as nome_cliente, f.nome as nome_funcionario " +
                     "FROM venda v " +
                     "LEFT JOIN cliente c ON v.id_cliente = c.id " +
                     "JOIN funcionario f ON v.id_funcionario = f.id " +
                     "WHERE DATE(v.data_venda) = ? ORDER BY v.data_venda DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, data);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas por data: " + e.getMessage());
        }
        return lista;
    }

    public List<ItemVenda> listarItensDaVenda(int idVenda) {
        List<ItemVenda> lista = new ArrayList<>();
        String sql = "SELECT iv.*, p.nome as nome_produto FROM item_venda iv JOIN produto p ON iv.id_produto = p.id WHERE iv.id_venda = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idVenda);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                item.setId(rs.getInt("id"));
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getDouble("preco_unitario"));
                item.setSubtotal(rs.getDouble("subtotal"));
                item.setIdVenda(rs.getInt("id_venda"));
                item.setIdProduto(rs.getInt("id_produto"));
                item.setNomeProduto(rs.getString("nome_produto"));
                lista.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar itens da venda: " + e.getMessage());
        }
        return lista;
    }

    private Venda mapear(ResultSet rs) throws SQLException {
        Venda v = new Venda();
        v.setId(rs.getInt("id"));
        v.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
        v.setTotal(rs.getDouble("total"));
        v.setStatus(StatusVenda.valueOf(rs.getString("status")));
        v.setIdFuncionario(rs.getInt("id_funcionario"));
        v.setNomeFuncionario(rs.getString("nome_funcionario"));
        v.setNomeCliente(rs.getString("nome_cliente"));
        int idCliente = rs.getInt("id_cliente");
        if (!rs.wasNull()) v.setIdCliente(idCliente);
        return v;
    }
}
