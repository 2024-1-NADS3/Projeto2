package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class CadastroActivity extends AppCompatActivity {

    private static final long DELAY_TIME_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
    }

    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(voltarTelaLogin);
    }

    public void BotaoCadastro(View view){
        TextView inputNomeCadastro, inputEmailCadastro, inputSenhaCadastro;
        String nome, email, senha;

        inputNomeCadastro = findViewById(R.id.inputNomeCadastro);
        inputEmailCadastro = findViewById(R.id.inputEmailCadastro);
        inputSenhaCadastro = findViewById(R.id.inputSenhaCadastro);

        nome = inputNomeCadastro.getText().toString();
        email = inputEmailCadastro.getText().toString();
        senha = inputSenhaCadastro.getText().toString();

        Usuario usuario = new Usuario(nome, email, senha);

        AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
        dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
        dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
        dadosCadastro.create().show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }, DELAY_TIME_MS);


    }
}