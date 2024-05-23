package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.telas_iniciais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class suporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suporte);

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
     * método para voltar para a tela anterior
     */
    public void ClicaTexto(View view){
        Intent voltar = new Intent(getApplicationContext(), Config.class);
        startActivity(voltar);
    }

    /**
     * Método para ir para a página do instagram
     */
    public void Instagram(View view){
        String url = "https://www.instagram.com/fecapsocial?igsh=aXVnNDF5OG9oY255";
        Intent mudarTelaInstagram = new Intent(Intent.ACTION_VIEW);
        mudarTelaInstagram.setData(Uri.parse(url));
        mudarTelaInstagram.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mudarTelaInstagram);
    }

    /**
     * Método para ir para a página do linkedin
     */
    public void Linkedin(View view){
        String url = "https://www.linkedin.com/company/fecap-social/";
        Intent mudarTelaLinkedin = new Intent(Intent.ACTION_VIEW);
        mudarTelaLinkedin.setData(Uri.parse(url));
        mudarTelaLinkedin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mudarTelaLinkedin);
    }

}