package br.fecap.fecap_social;

import org.junit.Test;

import static org.junit.Assert.*;
public class RedefinirSenhaTest {

    @Test
    /**
     * método que o construtor
     */
    public void testConstrutor() {
        // Testa o construtor com todos os atributos
        String token = "123456";
        String novaSenha = "novaSenha123";
        String novaSenhaConfirmar = "novaSenha123";

        ClasseRedefinirSenha redefinirSenha = new ClasseRedefinirSenha(token, novaSenha, novaSenhaConfirmar);

        // Verifica se os valores foram atribuídos corretamente
        assertEquals(token, redefinirSenha.getToken());
        assertEquals(novaSenha, redefinirSenha.getNovaSenha());
        assertEquals(novaSenhaConfirmar, redefinirSenha.getNovaSenhaConfirmar());
    }

    @Test
    /**
     * método que testa os getts e setts
     */
    public void testSettersEGetters() {
        // Cria um objeto ClasseRedefinirSenha vazio
        ClasseRedefinirSenha redefinirSenha = new ClasseRedefinirSenha("", "", "");

        // Define os valores dos atributos usando os setters
        String token = "654321";
        String novaSenha = "senhaNova321";
        String novaSenhaConfirmar = "senhaNova321";

        redefinirSenha.setToken(token);
        redefinirSenha.setNovaSenha(novaSenha);
        redefinirSenha.setNovaSenhaConfirmar(novaSenhaConfirmar);

        // Verifica se os getters retornam os valores corretos
        assertEquals(token, redefinirSenha.getToken());
        assertEquals(novaSenha, redefinirSenha.getNovaSenha());
        assertEquals(novaSenhaConfirmar, redefinirSenha.getNovaSenhaConfirmar());
    }
}
