package controller;

import model.Produto;
import service.ProdutoService;
import java.util.List;

public class ProdutoController {
    private ProdutoService service = new ProdutoService();

    public void cadastrar(Produto p)            { service.cadastrar(p); }
    public List<Produto> listar()               { return service.listar(); }
    public List<Produto> listarEstoqueBaixo()   { return service.listarEstoqueBaixo(); }
    public Produto buscarPorId(int id)          { return service.buscarPorId(id); }
    public void atualizar(Produto p)            { service.atualizar(p); }
    public void deletar(int id)                 { service.deletar(id); }
}
