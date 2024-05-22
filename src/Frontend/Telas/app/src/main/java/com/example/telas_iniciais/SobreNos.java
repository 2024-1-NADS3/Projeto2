package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SobreNos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nos);
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



    /**
     * método que faz ir para a tela de presidente
     */
    public void ClicaBtn(View view){
        Intent ir = new Intent(getApplicationContext(), SobreNosPresidente.class);
        startActivity(ir);
    }


    /**
     * método que faz ir para a tela de time de comunicação
     */
    public void ClicaBtn1(View view){
        Intent ir = new Intent(getApplicationContext(), SobreNosComunicacao.class);
        startActivity(ir);
    }

    /**
     * método que faz ir para a tela de time de projeto
     */
    public void ClicaBtn2(View view){
        Intent ir = new Intent(getApplicationContext(), SobreNosProjeto.class);
        startActivity(ir);
    }

    /**
     * método que faz ir para a tela de time de financeiro
     */
    public void ClicaBtn3(View view){
        Intent ir = new Intent(getApplicationContext(), SobreNosFinanceiro.class);
        startActivity(ir);
    }

    /**
     * Volta para e tela "home"
     */
    public void ClicaBtn4(View view){
        Intent voltar = new Intent(getApplicationContext(), Home.class);
        startActivity(voltar);
    }
}