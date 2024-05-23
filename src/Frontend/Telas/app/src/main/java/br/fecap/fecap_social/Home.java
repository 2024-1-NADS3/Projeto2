package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.telas_iniciais.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageSlider = findViewById(R.id.image_slider);

        // Lista de imagem
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image13, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image12, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Bloco de código de componente de interface do usuário que lida com o menu de navegação (Menu principal)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
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
     * Método que faz ir para a tela de Ações
     */
    public void botaoSaberMaisAcaoGeral(View view) {
        Intent mudarTelaBotaoAcao = new Intent(getApplicationContext(), NossasAcoesGeral.class);
        startActivity(mudarTelaBotaoAcao);
    }

    /**
     * Método que faz ir para a tela sobre a FECAP Social
     */
    public void BtnIr(View view) {
        Intent mudarTelaSobre = new Intent(getApplicationContext(), SobreNos.class);
        startActivity(mudarTelaSobre);
    }

    public void Instagram(View view){
        String url = "https://www.instagram.com/fecapsocial?igsh=aXVnNDF5OG9oY255";
        Intent mudarTelaInstagram = new Intent(Intent.ACTION_VIEW);
        mudarTelaInstagram.setData(Uri.parse(url));
        mudarTelaInstagram.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mudarTelaInstagram);
    }
}
