package com.example.telas_iniciais;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UsuarioTest {

    @Test
    public void testConstrutorCompleto() {
        // Criar um objeto ClasseUsuario com todos os atributos
        ClasseUsuario usuario = new ClasseUsuario("Nome Teste", "email@teste.com", "senha123");

        // Verificar se o objeto não é nulo
        assertNotNull(usuario);

        // Verificar se os atributos foram definidos corretamente
        assertEquals("Nome Teste", usuario.getNome());
        assertEquals("email@teste.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
    }

    @Test
    public void testSettersEGetters() {
        // Criar um objeto ClasseUsuario vazio
        ClasseUsuario usuario = new ClasseUsuario("email@teste.com");

        // Definir os valores dos atributos usando os setters
        usuario.setNome("Novo Nome");
        usuario.setSenha("novaSenha123");

        // Verificar se os getters retornam os valores corretos
        assertEquals("Novo Nome", usuario.getNome());
        assertEquals("email@teste.com", usuario.getEmail());
        assertEquals("novaSenha123", usuario.getSenha());
    }
}


