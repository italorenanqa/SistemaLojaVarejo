package controller;

import model.Cliente;
import service.ClienteService;
import java.util.List;

public class ClienteController {
    private ClienteService service = new ClienteService();

    public void cadastrar(Cliente c)       { service.cadastrar(c); }
    public List<Cliente> listar()          { return service.listar(); }
    public Cliente buscarPorId(int id)     { return service.buscarPorId(id); }
    public void atualizar(Cliente c)       { service.atualizar(c); }
    public void deletar(int id)            { service.deletar(id); }
}
