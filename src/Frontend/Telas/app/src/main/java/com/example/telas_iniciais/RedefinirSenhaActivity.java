package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private EditText inputNovaSenha;
    private EditText inputToken;
    private static final long tempoDelayMudarTela = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        inputNovaSenha = findViewById(R.id.inputNovaSenha);
        inputToken = findViewById(R.id.inputToken);
    }

    public void voltarTelaEsqueceuSenha(View view) {
        Intent voltarTelaEsqueceuSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(voltarTelaEsqueceuSenha);
    }

    public void botaoRedefinirSenha(View view) {
        String novaSenha = inputNovaSenha.getText().toString();
        String token = inputToken.getText().toString();

        String url = "https://twm93x-3000.csb.app/redefinir-senha";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(RedefinirSenhaActivity.this);
                        dadosCadastro.setTitle("Senha redefinida com sucesso!!!");
                        dadosCadastro.setMessage("Sua senha foi redefinida. Você pode agora fazer login com sua nova senha.");
                        dadosCadastro.create().show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RedefinirSenhaActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, tempoDelayMudarTela);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RedefinirSenhaActivity.this, "Ocorreu um erro. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();
                        Log.e("VolleyError", "Erro: " + error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<String, String>();
                dadosDoUsuario.put("token", token);
                dadosDoUsuario.put("novaSenha", novaSenha);
                return dadosDoUsuario;
            }
        };

        Volley.newRequestQueue(RedefinirSenhaActivity.this).add(postRequest);
    }
}