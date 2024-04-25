package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CadastroEvento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
    }

    /**
     * função que faz voltar para a tela de perfil quando clicar no botão
     */
    public void VoltarPerfil(View view){
        Intent VoltarPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(VoltarPerfil);

    }
}