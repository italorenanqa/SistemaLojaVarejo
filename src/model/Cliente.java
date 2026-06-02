package model;

import model.interfaces.Cadastravel;

public class Cliente implements Cadastravel {

    private int     id;
    private String  nome;
    private String  cpf;
    private String  telefone;
    private String  email;
    private boolean ativo;

    public Cliente() {}

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome     = nome;
        this.cpf      = cpf;
        this.telefone = telefone;
        this.email    = email;
        this.ativo    = true;
    }

    @Override
    public int getId()        { return id; }
    public void setId(int id) { this.id = id; }

    @Override
    public String getNome()         { return nome; }
    public void   setNome(String n) { this.nome = n; }

    public String getCpf()          { return cpf; }
    public void   setCpf(String c)  { this.cpf = c; }

    public String getTelefone()          { return telefone; }
    public void   setTelefone(String t)  { this.telefone = t; }

    public String getEmail()         { return email; }
    public void   setEmail(String e) { this.email = e; }

    @Override
    public boolean isAtivo()           { return ativo; }
    public void    setAtivo(boolean a) { this.ativo = a; }

    @Override
    public String toString() {
        return "[" + id + "] " + nome + " | CPF: " + cpf + " | Tel: " + telefone;
    }
}
