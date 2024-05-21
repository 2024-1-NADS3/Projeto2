package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        // Configura um temporizador para avançar automaticamente para a próxima tela
        new CountDownTimer(DELAY_TIME, 3000) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, Splash1.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }




}
