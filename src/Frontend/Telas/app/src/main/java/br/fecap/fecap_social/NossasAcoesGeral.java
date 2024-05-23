package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telas_iniciais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NossasAcoesGeral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nossas_acoes_geral);

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
     * Método para mudar para a tela AcaoHamburgada
     */
    public void mudarTelaAcaoHamb(View view) {
        Intent mudarTelaAcaoHamb = new Intent(getApplicationContext(), AcaoHamburgada.class);
        startActivity(mudarTelaAcaoHamb);
    }

    /**
     * Método para mudar para a tela AcaoTroteSolidario
     */
    public void mudarTelaTroteSolidario(View view) {
        Intent mudarTelaTroteSolidario = new Intent(getApplicationContext(), AcaoTroteSolidario.class);
        startActivity(mudarTelaTroteSolidario);
    }

    /**
     * Método para mudar para a tela AcaoPascoaSolidaria
     */
    public void mudarTelaPascoaSolidaria(View view) {
        Intent mudarTelaPascoaSolidaria = new Intent(getApplicationContext(), AcaoPascoaSolidaria.class);
        startActivity(mudarTelaPascoaSolidaria);
    }

    /**
     * Método para voltar para a tela Home
     */
    public void voltarTelaHome(View view) {
        Intent voltarTelaHome = new Intent(getApplicationContext(), Home.class);
        startActivity(voltarTelaHome);
    }
}

