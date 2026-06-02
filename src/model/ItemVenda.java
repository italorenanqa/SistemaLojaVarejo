package model;

public class ItemVenda {

    private int     id;
    private int     quantidade;
    private double  precoUnitario;
    private double  subtotal;
    private int     idVenda;
    private int     idProduto;
    private String  nomeProduto;

    public ItemVenda() {}

    public ItemVenda(int idProduto, String nomeProduto, int quantidade, double precoUnitario) {
        this.idProduto     = idProduto;
        this.nomeProduto   = nomeProduto;
        this.quantidade    = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal      = quantidade * precoUnitario;
    }

    public int getId()        { return id; }
    public void setId(int id) { this.id = id; }

    public int  getQuantidade()       { return quantidade; }
    public void setQuantidade(int q)  {
        this.quantidade = q;
        this.subtotal   = q * this.precoUnitario;
    }

    public double getPrecoUnitario()          { return precoUnitario; }
    public void   setPrecoUnitario(double p)  {
        this.precoUnitario = p;
        this.subtotal      = this.quantidade * p;
    }

    public double getSubtotal()          { return subtotal; }
    public void   setSubtotal(double s)  { this.subtotal = s; }

    public int  getIdVenda()       { return idVenda; }
    public void setIdVenda(int i)  { this.idVenda = i; }

    public int  getIdProduto()       { return idProduto; }
    public void setIdProduto(int i)  { this.idProduto = i; }

    public String getNomeProduto()         { return nomeProduto; }
    public void   setNomeProduto(String n) { this.nomeProduto = n; }

    @Override
    public String toString() {
        return nomeProduto + " | Qtd: " + quantidade
                + " | Unit: R$" + String.format("%.2f", precoUnitario)
                + " | Subtotal: R$" + String.format("%.2f", subtotal);
    }
}
