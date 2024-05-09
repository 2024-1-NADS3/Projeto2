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


    /**
     * método para voltar para a tela anterior
     */
    public void Voltar(View view){
        Intent Voltar= new Intent(getApplicationContext(), Splash1.class);
        startActivity(Voltar);
    }


    /**
     * método para avançar para a próxima tela
     */
    public void Avancar(View view){
        Intent Avancar= new Intent(getApplicationContext(), Splash3.class);
        startActivity(Avancar);
    }


}