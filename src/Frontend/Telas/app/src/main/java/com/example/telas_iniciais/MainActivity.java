package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Método que cria um delay e passa para a próxima tela automaticamente
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Splash1.class);
                startActivity(intent);
                finish();
            }
        }, DELAY_TIME);
        new CountDownTimer(DELAY_TIME, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, Splash1.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }
    }


