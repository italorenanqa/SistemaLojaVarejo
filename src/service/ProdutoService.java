package service;

import model.Produto;
import repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {

    private ProdutoRepository repository = new ProdutoRepository();

    public void cadastrar(Produto p) {
        if (p.getNome() == null || p.getNome().isBlank()) {
            System.out.println("Nome não pode ser vazio."); return;
        }
        if (p.getPrecoVenda() <= 0) {
            System.out.println("Preço de venda inválido."); return;
        }
        repository.cadastrar(p);
    }

    public List<Produto> listar() {
        return repository.listar();
    }

    public List<Produto> listarEstoqueBaixo() {
        return repository.listarEstoqueBaixo();
    }

    public Produto buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Produto p) {
        if (repository.buscarPorId(p.getId()) == null) {
            System.out.println("Produto não encontrado."); return;
        }
        repository.atualizar(p);
    }

    public void deletar(int id) {
        if (repository.buscarPorId(id) == null) {
            System.out.println("Produto não encontrado."); return;
        }
        repository.deletar(id);
    }

    public boolean temEstoqueSuficiente(int idProduto, int quantidade) {
        Produto p = repository.buscarPorId(idProduto);
        return p != null && p.getQuantidadeEstoque() >= quantidade;
    }

    public void reduzirEstoque(int idProduto, int quantidade) {
        Produto p = repository.buscarPorId(idProduto);
        if (p != null) {
            repository.atualizarEstoque(idProduto, p.getQuantidadeEstoque() - quantidade);
        }
    }
}
