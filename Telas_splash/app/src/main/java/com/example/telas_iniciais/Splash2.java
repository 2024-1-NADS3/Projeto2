package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Splash2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
    }

    public void Voltar(View view){
        Intent Voltar= new Intent(getApplicationContext(), Splash1.class);
        startActivity(Voltar);
    }

    public void Avancar(View view){
        Intent Avancar= new Intent(getApplicationContext(), Splash3.class);
        startActivity(Avancar);
    }


}