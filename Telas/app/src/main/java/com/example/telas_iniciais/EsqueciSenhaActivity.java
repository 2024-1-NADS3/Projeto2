package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private static final long DELAY_TIME_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

    }

    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(voltarTelaLogin);
    }

    public void enviarEsqueceu(View view) {
        TextView inputEmailEsqueceu;
        String emailEsqueceu;

        inputEmailEsqueceu = findViewById(R.id.inputEmailEsqueceu);

        emailEsqueceu = inputEmailEsqueceu.getText().toString();

        Usuario usuario = new Usuario(emailEsqueceu);

        AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(EsqueciSenhaActivity.this);
        dadosCadastro.setTitle("Dados enviados com sucesso!!!");
        dadosCadastro.setMessage("Em breve enviaremos um email!");
        dadosCadastro.create().show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(EsqueciSenhaActivity.this, MainActivity.class);
                startActivity(intent);


                finish();
            }
        }, DELAY_TIME_MS);

    }
}