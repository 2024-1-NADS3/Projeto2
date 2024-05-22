package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SenhaloginActivity extends AppCompatActivity {

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senhalogin);
    }

    public void voltarConfig(View view) {
        Intent voltar = new Intent(getApplicationContext(), Config.class);
        startActivity(voltar);
    }


    public void IrLogin(View view) {
        EditText inputTokenLogin;
        String token;

        inputTokenLogin = findViewById(R.id.inputTokenLogin);

        token = inputTokenLogin.getText().toString();

        if (token.equals("FecapSocial1SKL")) {
            Intent Irlogin = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(Irlogin);
        } else {
            AlertDialog.Builder dadosLogin = new AlertDialog.Builder(SenhaloginActivity.this);

            dadosLogin.setTitle("Erro ao tentar ir para tela de Login");
            dadosLogin.setMessage("Token incorreto!!!");
            dadosLogin.setPositiveButton("Tentar novamente", null);
            dadosLogin.setIcon(R.drawable.alert_icon);
            AlertDialog dialog = dadosLogin.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);
        }
    }



}