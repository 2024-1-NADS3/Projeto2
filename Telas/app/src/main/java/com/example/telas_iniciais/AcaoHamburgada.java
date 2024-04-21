package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AcaoHamburgada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acao_hamburgada);
    }

    public void voltarTelaAcaoGeral1(View view) {
        Intent voltarTelaAcaoGeral = new Intent(getApplicationContext(), NossasAcoesGeral.class);
        startActivity(voltarTelaAcaoGeral);
    }
}