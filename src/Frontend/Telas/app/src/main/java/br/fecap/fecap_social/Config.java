package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telas_iniciais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

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
                    return true;
            }
            return false;
        });
    }

    /**
     * Avança para e tela "sobre"
     */
    public void avancaSobre(View view) {
        Intent avancar = new Intent(getApplicationContext(), sobreApp.class);
        startActivity(avancar);
    }

    /**
     * Avança para e tela "termos"
     */
    public void avancaTermos(View view) {
        Intent avancar = new Intent(getApplicationContext(), termos.class);
        startActivity(avancar);
    }

    /**
     * Avança para e tela "suporte"
     */
    public void avancaSuporte(View view) {
        Intent avancar = new Intent(getApplicationContext(), suporte.class);
        startActivity(avancar);
    }

    /**
     * Avança para e tela "senha login" do acesso interno
     */
    public void avancaAcessoInterno(View view) {
        Intent avancar = new Intent(getApplicationContext(), SenhaLogin.class);
        startActivity(avancar);
    }


}