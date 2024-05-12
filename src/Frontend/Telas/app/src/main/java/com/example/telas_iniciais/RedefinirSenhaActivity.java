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

    private static final long tempoDelayMudarTela = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

    }

    public void voltarTelaEsqueceuSenha(View view) {
        Intent voltarTelaEsqueceuSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(voltarTelaEsqueceuSenha);
    }

    public void botaoRedefinirSenha(View view) {
        EditText inputToken, inputNovaSenha, inputNovaSenhaConfirmar;
        String token, novaSenha, novaSenhaConfirmar;

        inputToken = findViewById(R.id.inputToken);
        inputNovaSenha = findViewById(R.id.inputNovaSenha);
        inputNovaSenhaConfirmar = findViewById(R.id.inputNovaSenhaConfirmar);

        token = inputToken.getText().toString();
        novaSenha = inputNovaSenha.getText().toString();
        novaSenhaConfirmar = inputNovaSenhaConfirmar.getText().toString();

        ClasseRedefinirSenha dadosUsuario = new ClasseRedefinirSenha(token, novaSenha, novaSenhaConfirmar);

        dadosUsuario.setToken(token);
        dadosUsuario.setNovaSenha(novaSenha);
        dadosUsuario.setNovaSenhaConfirmar(novaSenhaConfirmar);

        /*
         * Se o usuário deixar algum campo vazio exibe um AlertDialog de Erro
         */
        if (token.isEmpty() || novaSenha.isEmpty() || novaSenhaConfirmar.isEmpty()) {
            androidx.appcompat.app.AlertDialog.Builder camposVazios = new androidx.appcompat.app.AlertDialog.Builder(RedefinirSenhaActivity.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton(android.R.string.ok, null);
            camposVazios.setIcon(R.drawable.alert_icon);
            camposVazios.create().show();
            return;
        }

        /*
         * Se o usuário inserir senhas diferentes nos campos de senha, exibe um AlertDialog de Erro
         */
        if (!novaSenha.equals(novaSenhaConfirmar)) {
            androidx.appcompat.app.AlertDialog.Builder senhaConfirme = new androidx.appcompat.app.AlertDialog.Builder(RedefinirSenhaActivity.this);
            senhaConfirme.setTitle("Erro");
            senhaConfirme.setMessage("As senhas inseridas não coincidem");
            senhaConfirme.setPositiveButton(android.R.string.ok, null);
            senhaConfirme.setIcon(R.drawable.alert_icon);
            senhaConfirme.create().show();
            return;
        }

        String url = "https://4nqjkx-3000.csb.app/redefinir-senha";

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
                dadosDoUsuario.put("token", dadosUsuario.getToken());
                dadosDoUsuario.put("novaSenha", dadosUsuario.getNovaSenha());
                dadosDoUsuario.put("novaSenhaConfirmar", dadosUsuario.getNovaSenhaConfirmar());
                return dadosDoUsuario;
            }
        };

        Volley.newRequestQueue(RedefinirSenhaActivity.this).add(postRequest);
    }
}