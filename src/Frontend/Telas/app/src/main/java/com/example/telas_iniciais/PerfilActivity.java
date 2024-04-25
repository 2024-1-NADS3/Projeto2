package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }

    /**
     *  Função que direciona o usuário para a tela de cadastro de eventos
     */
    public void IrTelaCadEv(View view){
        Intent IrTelaCadEv = new Intent(getApplicationContext(), CadastroEvento.class);
        startActivity(IrTelaCadEv);

    }
}