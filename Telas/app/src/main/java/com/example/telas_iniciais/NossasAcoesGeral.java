package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NossasAcoesGeral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nossas_acoes_geral);

        mudarTelaAcaoHamb(null);
    }


    public void mudarTelaAcaoHamb(View view) {
        Intent mudarTelaAcaoHamb = new Intent(getApplicationContext(), AcaoHamburgada.class);
        startActivity(mudarTelaAcaoHamb);
    }

}