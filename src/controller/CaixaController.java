package controller;

import model.Caixa;
import service.CaixaService;
import java.util.List;

public class CaixaController {
    private CaixaService service = new CaixaService();

    public Caixa abrir(double saldoInicial, int idFuncionario) { return service.abrir(saldoInicial, idFuncionario); }
    public void fechar(double saldoFinal)                       { service.fechar(saldoFinal); }
    public Caixa buscarCaixaAberto()                            { return service.buscarCaixaAberto(); }
    public List<Caixa> listar()                                 { return service.listar(); }
}
