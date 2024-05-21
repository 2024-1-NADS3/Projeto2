package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Splash3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash3);
    }

    /**
     * Método para voltar para a tela anterior (Splash2)
     */
    public void Voltar(View view) {
        Intent intent = new Intent(getApplicationContext(), Splash2.class);
        startActivity(intent);
        finish();
    }

    /**
     * Método para avançar para a próxima tela (Home, por exemplo)
     */
    public void Avancar(View view) {
        // Atualiza SharedPreferences para indicar que não é mais a primeira execução
        SharedPreferences sharedPreferences = getSharedPreferences("appPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();

        // Avança para a próxima tela (por exemplo, a tela inicial)
        startActivity(new Intent(this, Home.class));
        finish();
    }
}
