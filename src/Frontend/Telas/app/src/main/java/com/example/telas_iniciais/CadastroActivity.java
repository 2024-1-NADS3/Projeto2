package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import okhttp3.*;
import android.widget.Toast;
import java.io.IOException;


public class CadastroActivity extends AppCompatActivity {

    private static final long DELAY_TIME_MS = 3000;
    private final OkHttpClient usuario = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(voltarTelaLogin);
    }

    public void BotaoCadastro(View view) {
        EditText inputNomeCadastro, inputEmailCadastro, inputSenhaCadastro;
        String nome, email, senha;

        inputNomeCadastro = findViewById(R.id.inputNomeCadastro);
        inputEmailCadastro = findViewById(R.id.inputEmailCadastro);
        inputSenhaCadastro = findViewById(R.id.inputSenhaCadastro);

        nome = inputNomeCadastro.getText().toString();
        email = inputEmailCadastro.getText().toString();
        senha = inputSenhaCadastro.getText().toString();

        try {
            postRequestCadastro("https://7pkqm4-38883.csb.app/cadastro", nome, email, senha);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void postRequestCadastro(String postUrl, String nome, String email, String password) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("nome", nome)
                .add("email", email)
                .add("senha", password)
                .build();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(formBody)
                .build();

        usuario.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(CadastroActivity.this, "Erro na conexão com o servidor", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(CadastroActivity.this, "Falha no cadastro", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(CadastroActivity.this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
                        dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
                        dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
                        dadosCadastro.create().show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, DELAY_TIME_MS);
                    });
                }
            }
        });
    }
}