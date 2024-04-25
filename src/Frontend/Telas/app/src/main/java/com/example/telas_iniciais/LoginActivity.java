package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private final OkHttpClient usuario = new OkHttpClient();

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

        try {
            postRequestLogin("https://7pkqm4-38883.csb.app/login", email, senha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void postRequestLogin(String postUrl, String email, String senha) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("login", email)
                .add("password", senha)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(formBody)
                .build();

        usuario.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Erro na conexão com o servidor", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show());
                } else {
                    String responseBody = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        String status = json.getString("status");

                        if (status.equals("sucesso")) {
                            runOnUiThread(() -> {
                                Toast.makeText(LoginActivity.this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        } else {
                            runOnUiThread(() -> {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Erro de Login")
                                        .setMessage("Email ou senha incorretos!!!")
                                        .setPositiveButton(android.R.string.ok, null)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Erro ao processar a resposta do servidor", Toast.LENGTH_SHORT).show());
                    }
                }
            }
        });
    }
}