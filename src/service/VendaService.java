package service;

import enums.StatusVenda;
import model.ItemVenda;
import model.Venda;
import repository.VendaRepository;

import java.util.List;

public class VendaService {

    private VendaRepository  vendaRepository  = new VendaRepository();
    private ProdutoService   produtoService   = new ProdutoService();

    public Venda iniciarVenda(int idFuncionario, int idCaixa) {
        return new Venda(idFuncionario, idCaixa);
    }

    public boolean adicionarItem(Venda venda, int idProduto, String nomeProduto, double precoUnitario, int quantidade) {
        if (!produtoService.temEstoqueSuficiente(idProduto, quantidade)) {
            System.out.println("Estoque insuficiente para: " + nomeProduto);
            return false;
        }
        ItemVenda item = new ItemVenda(idProduto, nomeProduto, quantidade, precoUnitario);
        venda.adicionarItem(item);
        return true;
    }

    public void finalizarVenda(Venda venda) {
        if (venda.getItens().isEmpty()) {
            System.out.println("Venda sem itens!"); return;
        }
        venda.setStatus(StatusVenda.FINALIZADA);
        vendaRepository.cadastrar(venda);
        for (ItemVenda item : venda.getItens()) {
            produtoService.reduzirEstoque(item.getIdProduto(), item.getQuantidade());
        }
        imprimirCupom(venda);
    }

    public void cancelarVenda(Venda venda) {
        venda.setStatus(StatusVenda.CANCELADA);
        if (venda.getId() > 0) vendaRepository.atualizarStatus(venda.getId(), StatusVenda.CANCELADA);
        System.out.println("Venda cancelada.");
    }

    public List<Venda> listar() {
        return vendaRepository.listar();
    }

    public List<Venda> listarPorData(String data) {
        return vendaRepository.listarPorData(data);
    }

    public List<ItemVenda> listarItensDaVenda(int idVenda) {
        return vendaRepository.listarItensDaVenda(idVenda);
    }

    public void imprimirCupom(Venda venda) {
        System.out.println("\n========== CUPOM FISCAL ==========");
        System.out.println("Venda ID : " + venda.getId());
        System.out.println("Data     : " + venda.getDataVenda());
        System.out.println("Cliente  : " + (venda.getNomeCliente() != null ? venda.getNomeCliente() : "Consumidor final"));
        System.out.println("----------------------------------");
        for (ItemVenda item : venda.getItens()) {
            System.out.printf("%-20s %3dx R$%6.2f = R$%7.2f%n",
                    item.getNomeProduto(), item.getQuantidade(),
                    item.getPrecoUnitario(), item.getSubtotal());
        }
        System.out.println("----------------------------------");
        System.out.printf("TOTAL: R$ %.2f%n", venda.getTotal());
        System.out.println("==================================\n");
    }
}
