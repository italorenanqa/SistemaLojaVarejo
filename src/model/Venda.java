package model;

import enums.StatusVenda;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private int           id;
    private LocalDateTime dataVenda;
    private double        total;
    private StatusVenda   status;
    private int           idCliente;
    private String        nomeCliente;
    private int           idFuncionario;
    private String        nomeFuncionario;
    private int           idCaixa;
    private List<ItemVenda> itens;

    public Venda() {
        this.itens     = new ArrayList<>();
        this.dataVenda = LocalDateTime.now();
        this.status    = StatusVenda.ABERTA;
        this.total     = 0.0;
    }

    public Venda(int idFuncionario, int idCaixa) {
        this();
        this.idFuncionario = idFuncionario;
        this.idCaixa       = idCaixa;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.total = itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }

    public int getId()        { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDataVenda()              { return dataVenda; }
    public void          setDataVenda(LocalDateTime d){ this.dataVenda = d; }

    public double getTotal()          { return total; }
    public void   setTotal(double t)  { this.total = t; }

    public StatusVenda getStatus()             { return status; }
    public void        setStatus(StatusVenda s){ this.status = s; }

    public int  getIdCliente()       { return idCliente; }
    public void setIdCliente(int i)  { this.idCliente = i; }

    public String getNomeCliente()         { return nomeCliente; }
    public void   setNomeCliente(String n) { this.nomeCliente = n; }

    public int  getIdFuncionario()       { return idFuncionario; }
    public void setIdFuncionario(int i)  { this.idFuncionario = i; }

    public String getNomeFuncionario()         { return nomeFuncionario; }
    public void   setNomeFuncionario(String n) { this.nomeFuncionario = n; }

    public int  getIdCaixa()       { return idCaixa; }
    public void setIdCaixa(int i)  { this.idCaixa = i; }

    public List<ItemVenda> getItens()              { return itens; }
    public void            setItens(List<ItemVenda> i){ this.itens = i; }

    @Override
    public String toString() {
        return "[" + id + "] Venda | " + dataVenda
                + " | Total: R$" + String.format("%.2f", total)
                + " | Status: " + status
                + " | Cliente: " + (nomeCliente != null ? nomeCliente : "Não informado");
    }
}
