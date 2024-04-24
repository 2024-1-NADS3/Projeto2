package com.example.telas_iniciais;

import java.io.Serializable;

public class Usuario implements Serializable {

    protected String nome;

    protected String email;

    protected String senha;

    protected String emailEsqueceu;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmailEsqueceu() {
        return emailEsqueceu;
    }

    public void setEmailEsqueceu(String emailEsqueceu) {
        this.emailEsqueceu = emailEsqueceu;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String emailEsqueceu) {
        this.emailEsqueceu = emailEsqueceu;
    }

}
