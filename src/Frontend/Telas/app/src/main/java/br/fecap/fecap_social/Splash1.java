package br.fecap.fecap_social;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.telas_iniciais.R;

public class Splash1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);
    }

    /**
     * método para avançar para a próxima tela
     */
    public void Avancar(View view) {
        Intent Avancar = new Intent(getApplicationContext(), Splash2.class);
        startActivity(Avancar);
        finish();
    }
}
