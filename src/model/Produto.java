package model;

import model.interfaces.Cadastravel;

public class Produto implements Cadastravel {

    private int     id;
    private String  nome;
    private String  descricao;
    private double  precoCusto;
    private double  precoVenda;
    private int     quantidadeEstoque;
    private int     estoqueMinimo;
    private boolean ativo;

    public Produto() {}

    public Produto(String nome, String descricao, double precoCusto, double precoVenda, int quantidadeEstoque, int estoqueMinimo) {
        this.nome              = nome;
        this.descricao         = descricao;
        this.precoCusto        = precoCusto;
        this.precoVenda        = precoVenda;
        this.quantidadeEstoque = quantidadeEstoque;
        this.estoqueMinimo     = estoqueMinimo;
        this.ativo             = true;
    }

    @Override
    public int getId()        { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String getNome()         { return nome; }
    public void   setNome(String n) { this.nome = n; }

    public String getDescricao()         { return descricao; }
    public void   setDescricao(String d) { this.descricao = d; }

    public double getPrecoCusto()          { return precoCusto; }
    public void   setPrecoCusto(double p)  { this.precoCusto = p; }

    public double getPrecoVenda()          { return precoVenda; }
    public void   setPrecoVenda(double p)  { this.precoVenda = p; }

    public int  getQuantidadeEstoque()       { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int q)  { this.quantidadeEstoque = q; }

    public int  getEstoqueMinimo()       { return estoqueMinimo; }
    public void setEstoqueMinimo(int e)  { this.estoqueMinimo = e; }

    @Override
    public boolean isAtivo()           { return ativo; }
    public void    setAtivo(boolean a) { this.ativo = a; }

    public boolean estoqueBaixo() {
        return quantidadeEstoque <= estoqueMinimo;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " | Preço: R$" + String.format("%.2f", precoVenda)
                + " | Estoque: " + quantidadeEstoque
                + (estoqueBaixo() ? " ⚠ ESTOQUE BAIXO" : "");
    }
}
