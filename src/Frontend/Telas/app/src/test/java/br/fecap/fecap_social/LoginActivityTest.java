package br.fecap.fecap_social;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;


import java.util.Map;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class LoginActivityTest {

    private LoginActivity loginActivity;




    @Test
    public void testGetParamsMap() {
        ClasseUsuario usuario = new ClasseUsuario("teste@teste.com", "senha123");

        Map<String, String> paramsMap = loginActivity.getParamsMap(usuario);

        assertEquals(2, paramsMap.size());
        assertEquals("teste@teste.com", paramsMap.get("login"));
        assertEquals("senha123", paramsMap.get("password"));
    }
}
