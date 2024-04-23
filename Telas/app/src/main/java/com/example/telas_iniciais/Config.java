package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

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
                    return true;
            }
            return false;
        });
    }

    public void avancaSobre(View view) {
        Intent avancar = new Intent(getApplicationContext(), sobreApp.class);
        startActivity(avancar);
    }

    public void avancaTermos(View view) {
        Intent avancar = new Intent(getApplicationContext(), termos.class);
        startActivity(avancar);
    }

    public void avancaSuporte(View view) {
        Intent avancar = new Intent(getApplicationContext(), suporte.class);
        startActivity(avancar);
    }


}