package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity responsável pela tela de login do aplicativo Fecap Social.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Método para quando clicar no botão de voltar a tela "<", ir para a tela Configuração.
     */
    public void voltarTelaConfig(View view) {
        Intent mudarTelaConfig = new Intent(getApplicationContext(), Config.class);
        startActivity(mudarTelaConfig);
    }

    /**
     * Método para quando clicar no botão "Realizar Cadastro", ir para a tela de CadastroActivity (Cadastro do Usuário)
     */
    public void mudarTelaCadastro(View view) {
        Intent mudarTelaCadastro = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(mudarTelaCadastro);
    }

    /**
     * Método para quando clicar no botão "Esqueceu a senha?", ir para a tela EsqueciSenhaActivity
     */
    public void mudarTelaEsqueciSenha(View view) {
        Intent mudarTelaEsqueciSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(mudarTelaEsqueciSenha);
    }

    /**
     * Método para quando clicar no botão "Entrar", realizar o login do usuário
     */
    public void botaoEntrarLogin(View view) {
        EditText inputEmailLogin, inputSenhaLogin;
        String email, senha;

        inputEmailLogin = findViewById(R.id.inputEmailLogin);
        inputSenhaLogin = findViewById(R.id.inputSenhaLogin);

        email = inputEmailLogin.getText().toString();
        senha = inputSenhaLogin.getText().toString();

        ClasseUsuario usuario = new ClasseUsuario(email, senha);
        realizarLogin("https://4nqjkx-3000.csb.app/login", usuario);
    }

    /**
     * Método para realizar a requisição de login ao servidor
     */
    void realizarLogin(String postUrl, final ClasseUsuario usuario) {
        RequestQueue filaRequest = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String status = json.getString("status");

                            if (status.equals("sucesso")) {
                                Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                                // Recupera o nome do usuário retornado pelo servidor
                                String idUsuario = json.getString("id_usuario");
                                String emailUsuario = json.getString("email_usuario");
                                String nomeUsuario = json.getString("nome_usuario");

                                salvarDadosUsuarioPreferencias(idUsuario, emailUsuario, nomeUsuario);

                                Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder dadosLogin = new AlertDialog.Builder(LoginActivity.this);
                                dadosLogin.setTitle("Erro de Login");
                                dadosLogin.setMessage("Email ou senha incorretos!!!");
                                dadosLogin.setPositiveButton(android.R.string.ok, null);
                                dadosLogin.setIcon(R.drawable.alert_icon);
                                dadosLogin.create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Erro ao processar a resposta do servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Erro na conexão com o servidor", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<String, String>();
                dadosDoUsuario.put("login", usuario.getEmail());
                dadosDoUsuario.put("password", usuario.getSenha());

                return dadosDoUsuario;
            }
        };

        filaRequest.add(stringRequest);
    }

    /**
     * Método para salvar o ID e o email do usuário para ser usado na tela de Perfil
     */

    private void salvarDadosUsuarioPreferencias(String idUsuario, String emailUsuario, String nomeUsuario) {
        SharedPreferences preferencias = getSharedPreferences("salvarDados", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        editorPreferencias.putString("id_usuario", idUsuario);
        editorPreferencias.putString("email_usuario", emailUsuario);
        editorPreferencias.putString("nome_usuario", nomeUsuario);
        editorPreferencias.apply();
    }
}
