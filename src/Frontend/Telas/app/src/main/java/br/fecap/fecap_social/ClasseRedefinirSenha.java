package br.fecap.fecap_social;

import java.io.Serializable;

/**
 * Classe para lidar com os dados da tela Redefinir Senha.
 */
public class ClasseRedefinirSenha implements Serializable {
    /**
     * Atributos
     */
    protected String token;
    protected String novaSenha;
    protected String novaSenhaConfirmar;

    /**
     * Obtém o token
     * @return o token
     */
    public String getToken() {
        return token;
    }

    /**
     * Define o token do usuário.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Obtém a nova senha do usuário.
     * @return a nova senha do usuário.
     */
    public String getNovaSenha() {
        return novaSenha;
    }

    /**
     * Define a nova senha do usuário.
     */
    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    /**
     * Obtém a nova senha (confirmar) do usuário.
     * @return a nova senha (confirmar) do usuário.
     */
    public String getNovaSenhaConfirmar() {
        return novaSenhaConfirmar;
    }

    /**
     * Define a nova senha de confirmação do usuário.
     */
    public void setNovaSenhaConfirmar(String novaSenhaConfirmar) {
        this.novaSenhaConfirmar = novaSenhaConfirmar;
    }


    /**
     * Construtor para criar um objeto com token, novaSenha e novaSenhaConfirmar. (tela de Redefinição de senha)
     */
    public ClasseRedefinirSenha(String token, String novaSenha, String novaSenhaConfirmar) {
        this.token = token;
        this.novaSenha = novaSenha;
        this.novaSenhaConfirmar = novaSenhaConfirmar;
    }
}
