package service;

import model.Funcionario;
import repository.FuncionarioRepository;

import java.util.List;

public class FuncionarioService {

    private FuncionarioRepository repository = new FuncionarioRepository();

    public void cadastrar(Funcionario f) {
        if (f.getNome() == null || f.getNome().isBlank()) {
            System.out.println("Nome não pode ser vazio."); return;
        }
        if (f.getLogin() == null || f.getLogin().isBlank()) {
            System.out.println("Login não pode ser vazio."); return;
        }
        if (repository.buscarPorLogin(f.getLogin()) != null) {
            System.out.println("Login já em uso."); return;
        }
        repository.cadastrar(f);
    }

    public List<Funcionario> listar() {
        return repository.listar();
    }

    public Funcionario buscarPorId(int id) {
        return repository.buscarPorId(id);
    }

    public Funcionario autenticar(String login, String senha) {
        Funcionario f = repository.buscarPorLogin(login);
        if (f != null && f.getSenha().equals(senha)) return f;
        return null;
    }

    public void atualizar(Funcionario f) {
        if (repository.buscarPorId(f.getId()) == null) {
            System.out.println("Funcionário não encontrado."); return;
        }
        repository.atualizar(f);
    }

    public void deletar(int id) {
        if (repository.buscarPorId(id) == null) {
            System.out.println("Funcionário não encontrado."); return;
        }
        repository.deletar(id);
    }
}
