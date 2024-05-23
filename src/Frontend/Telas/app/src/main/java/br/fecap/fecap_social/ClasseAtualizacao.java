package br.fecap.fecap_social;

import java.io.Serializable;

/**
 * Classe que representa a atualização do usuário.
 */
public class ClasseAtualizacao implements Serializable {

    /**
     * Atributos
     */
    private String nome;
    private String email;

    /**
     * Obtém o nome do usuário.
     * @return o nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define um novo nome do usuário.
     */
    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    /**
     * Obtém o email do usuário.
     * @return o email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define um novo email do usuário.
     */
    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    /**
     * Construtor para criar um objeto com nome e email. (tela de atualização de cadastro)
     */
    public ClasseAtualizacao(String novoNome, String novoEmail) {
        this.nome = novoNome;
        this.email = novoEmail;
    }
}
