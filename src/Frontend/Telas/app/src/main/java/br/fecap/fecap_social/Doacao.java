package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.telas_iniciais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Doacao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao);

        /**
         * Bloco de código de componente de interface do usuário que lida com o menu de navegação (Menu principal) e
         * controla a navegação entre diferentes telas de acordo com os itens selecionados
         */
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
}