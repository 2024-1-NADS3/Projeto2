package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.telas_iniciais.R;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Recuperar SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);


        /**
         * Configura um temporizador para avançar automaticamente para a próxima tela
         */
        new CountDownTimer(DELAY_TIME, 1000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                if (isFirstRun) {
                    // Se for a primeira vez, navega para a primeira tela splash
                    Intent intent = new Intent(MainActivity.this, Splash1.class);
                    startActivity(intent);
                } else {
                    // Caso contrário, pula direto para a tela principal do app
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
                finish();
            }
        }.start();
    }
}
