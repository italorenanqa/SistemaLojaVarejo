package controller;

import model.Funcionario;
import service.FuncionarioService;
import java.util.List;

public class FuncionarioController {
    private FuncionarioService service = new FuncionarioService();

    public void cadastrar(Funcionario f)         { service.cadastrar(f); }
    public List<Funcionario> listar()            { return service.listar(); }
    public Funcionario buscarPorId(int id)       { return service.buscarPorId(id); }
    public Funcionario autenticar(String l, String s) { return service.autenticar(l, s); }
    public void atualizar(Funcionario f)         { service.atualizar(f); }
    public void deletar(int id)                  { service.deletar(id); }
}
