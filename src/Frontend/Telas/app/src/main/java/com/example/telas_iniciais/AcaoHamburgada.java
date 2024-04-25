package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AcaoHamburgada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acao_hamburgada);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    Intent menuHome = new Intent(getApplicationContext(), Home.class);
                    startActivity(menuHome);
                    finish();
                    return true;
                case R.id.menuCalendar:
                    Intent menuCalendar = new Intent(getApplicationContext(), Calendario.class);
                    startActivity(menuCalendar);
                    finish();
                    return true;
                case R.id.menuDoacao:
                    Intent menuDoacao = new Intent(getApplicationContext(), Doacao.class);
                    startActivity(menuDoacao);
                    finish();
                    return true;
                case R.id.menuConfig:
                    Intent menuConfig = new Intent(getApplicationContext(), Config.class);
                    startActivity(menuConfig);
                    finish();
                    return true;
            }
            return false;
        });
    }

    public void voltarTelaAcaoGeral1(View view) {
        Intent voltarTelaAcaoGeral = new Intent(getApplicationContext(), NossasAcoesGeral.class);
        startActivity(voltarTelaAcaoGeral);
    }
}