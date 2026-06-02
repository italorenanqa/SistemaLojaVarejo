package service;

import model.Cliente;
import repository.ClienteRepository;

import java.util.List;

public class ClienteService {

    private ClienteRepository repository = new ClienteRepository();

    public void cadastrar(Cliente c) {
        if (c.getNome() == null || c.getNome().isBlank()) {
            System.out.println("Nome não pode ser vazio."); return;
        }
        if (c.getCpf() == null || c.getCpf().isBlank()) {
            System.out.println("CPF não pode ser vazio."); return;
        }
        repository.cadastrar(c);
    }

    public List<Cliente> listar() {
        return repository.listar();
    }

    public Cliente buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public void atualizar(Cliente c) {
        if (repository.buscarPorId(c.getId()) == null) {
            System.out.println("Cliente não encontrado."); return;
        }
        repository.atualizar(c);
    }

    public void deletar(int id) {
        if (repository.buscarPorId(id) == null) {
            System.out.println("Cliente não encontrado."); return;
        }
        repository.deletar(id);
    }
}
