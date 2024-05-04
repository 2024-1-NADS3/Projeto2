package com.example.telas_iniciais;

import java.io.Serializable;

/**
 * Classe que representa um usuário.
 */
public class Usuario implements Serializable {

    protected String nome;
    protected String email;
    protected String senha;

    /**
     * Obtém o nome do usuário.
     * @return o nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o email do usuário.
     * @return o email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a senha do usuário.
     * @return a senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Construtor para criar um objeto Usuario com nome, email e senha. (tela de cadastro)
     */
    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    /**
     * Construtor para criar um objeto Usuario com email e senha. (tela de login)
     */
    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    /**
     * Construtor para criar um objeto Usuario apenas com o email (tela de esqueceu a senha)
     */
    public Usuario(String email) {
        this.email = email;
    }
}