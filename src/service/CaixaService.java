package service;

import model.Caixa;
import repository.CaixaRepository;

import java.util.List;

public class CaixaService {

    private CaixaRepository repository = new CaixaRepository();

    public Caixa abrir(double saldoInicial, int idFuncionario) {
        if (repository.buscarCaixaAberto() != null) {
            System.out.println("Já existe um caixa aberto!"); return null;
        }
        Caixa c = new Caixa(saldoInicial, idFuncionario);
        repository.abrir(c);
        return c;
    }

    public void fechar(double saldoFinal) {
        Caixa aberto = repository.buscarCaixaAberto();
        if (aberto == null) {
            System.out.println("Nenhum caixa aberto."); return;
        }
        repository.fechar(aberto.getId(), saldoFinal);
    }

    public Caixa buscarCaixaAberto() {
        return repository.buscarCaixaAberto();
    }

    public List<Caixa> listar() {
        return repository.listar();
    }
}
