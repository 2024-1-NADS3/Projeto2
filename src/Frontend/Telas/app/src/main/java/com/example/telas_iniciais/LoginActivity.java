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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void mudarTelaCadastro(View view) {
        Intent mudarTelaCadastro = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(mudarTelaCadastro);
    }

    public void mudarTelaEsqueciSenha(View view) {
        Intent mudarTelaEsqueciSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(mudarTelaEsqueciSenha);
    }

    public void botaoEntrarLogin(View view) {
        EditText inputEmailLogin, inputSenhaLogin;
        String email, senha;

        inputEmailLogin = findViewById(R.id.inputEmailLogin);
        inputSenhaLogin = findViewById(R.id.inputSenhaLogin);

        email = inputEmailLogin.getText().toString();
        senha = inputSenhaLogin.getText().toString();

        Usuario usuario = new Usuario(email, senha);
        realizarLogin("https://twm93x-3000.csb.app/login", usuario);
    }

    void realizarLogin(String postUrl, final Usuario usuario) {
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

                                // Extrair o ID do usuário retornado pelo servidor
                                String idUsuario = json.getString("id_usuario");

                                // Salvar o ID do usuário nas SharedPreferences
                                salvarIdUsuarioPreferencias(idUsuario);

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

    // Método para salvar o ID do usuário nas SharedPreferences
    private void salvarIdUsuarioPreferencias(String idUsuario) {
        SharedPreferences preferencias = getSharedPreferences("MinhasPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        editorPreferencias.putString("id_usuario", idUsuario);
        editorPreferencias.apply();
    }
}
