package model;

import enums.NivelAcesso;
import model.interfaces.Cadastravel;

public class Funcionario implements Cadastravel {

    private int        id;
    private String     nome;
    private String     login;
    private String     senha;
    private NivelAcesso nivelAcesso;
    private boolean    ativo;

    public Funcionario() {}

    public Funcionario(String nome, String login, String senha, NivelAcesso nivelAcesso) {
        this.nome        = nome;
        this.login       = login;
        this.senha       = senha;
        this.nivelAcesso = nivelAcesso;
        this.ativo       = true;
    }

    @Override
    public int getId()       { return id; }
    public void setId(int id){ this.id = id; }

    @Override
    public String getNome()          { return nome; }
    public void   setNome(String n)  { this.nome = n; }

    public String getLogin()           { return login; }
    public void   setLogin(String l)   { this.login = l; }

    public String getSenha()           { return senha; }
    public void   setSenha(String s)   { this.senha = s; }

    public NivelAcesso getNivelAcesso()              { return nivelAcesso; }
    public void        setNivelAcesso(NivelAcesso n) { this.nivelAcesso = n; }

    @Override
    public boolean isAtivo()           { return ativo; }
    public void    setAtivo(boolean a) { this.ativo = a; }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " | Login: " + login + " | Nível: " + nivelAcesso;
    }
}
