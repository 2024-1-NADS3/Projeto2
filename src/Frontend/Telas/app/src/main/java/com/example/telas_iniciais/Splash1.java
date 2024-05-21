package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Splash1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

    }



    /**
     * método para avançar para a próxima tela
     */
    public void Avancar(View view){
        Intent Avancar = new Intent(getApplicationContext(), Splash2.class);
        startActivity(Avancar);
    }
}