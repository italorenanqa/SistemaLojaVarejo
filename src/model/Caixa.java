package model;

import enums.StatusCaixa;
import java.time.LocalDateTime;

public class Caixa {

    private int           id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private double        saldoInicial;
    private double        saldoFinal;
    private StatusCaixa   status;
    private int           idFuncionario;
    private String        nomeFuncionario;

    public Caixa() {}

    public Caixa(double saldoInicial, int idFuncionario) {
        this.saldoInicial  = saldoInicial;
        this.idFuncionario = idFuncionario;
        this.dataAbertura  = LocalDateTime.now();
        this.status        = StatusCaixa.ABERTO;
    }

    public int getId()        { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDataAbertura()              { return dataAbertura; }
    public void          setDataAbertura(LocalDateTime d){ this.dataAbertura = d; }

    public LocalDateTime getDataFechamento()               { return dataFechamento; }
    public void          setDataFechamento(LocalDateTime d) { this.dataFechamento = d; }

    public double getSaldoInicial()          { return saldoInicial; }
    public void   setSaldoInicial(double s)  { this.saldoInicial = s; }

    public double getSaldoFinal()          { return saldoFinal; }
    public void   setSaldoFinal(double s)  { this.saldoFinal = s; }

    public StatusCaixa getStatus()             { return status; }
    public void        setStatus(StatusCaixa s){ this.status = s; }

    public int  getIdFuncionario()       { return idFuncionario; }
    public void setIdFuncionario(int i)  { this.idFuncionario = i; }

    public String getNomeFuncionario()           { return nomeFuncionario; }
    public void   setNomeFuncionario(String n)   { this.nomeFuncionario = n; }

    @Override
    public String toString() {
        return "[" + id + "] Caixa | Status: " + status
                + " | Saldo inicial: R$" + String.format("%.2f", saldoInicial)
                + " | Abertura: " + dataAbertura;
    }
}
