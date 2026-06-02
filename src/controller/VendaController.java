package controller;

import model.ItemVenda;
import model.Venda;
import service.VendaService;
import java.util.List;

public class VendaController {
    private VendaService service = new VendaService();

    public Venda iniciarVenda(int idFuncionario, int idCaixa)                              { return service.iniciarVenda(idFuncionario, idCaixa); }
    public boolean adicionarItem(Venda v, int idProduto, String nome, double preco, int qtd){ return service.adicionarItem(v, idProduto, nome, preco, qtd); }
    public void finalizarVenda(Venda v)                                                     { service.finalizarVenda(v); }
    public void cancelarVenda(Venda v)                                                      { service.cancelarVenda(v); }
    public List<Venda> listar()                                                             { return service.listar(); }
    public List<Venda> listarPorData(String data)                                           { return service.listarPorData(data); }
    public List<ItemVenda> listarItens(int idVenda)                                         { return service.listarItensDaVenda(idVenda); }
    public void imprimirCupom(Venda v)                                                      { service.imprimirCupom(v); }
}
