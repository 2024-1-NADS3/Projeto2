package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.content.Intent;


public class AcaoTroteSolidario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acao_trote_solidario);

        HorizontalScrollView scrollView = findViewById(R.id.carrosselImagens);
        ImageView setaEsquerda = findViewById(R.id.setaEsquerdaCarrossel);
        ImageView setaDireita = findViewById(R.id.setaDireitaCarrossel);

        // No início do carrossel a seta esquerda fica invisível
        setaEsquerda.setVisibility(View.INVISIBLE);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            /*
             * Método chamado quando a posição de rolagem do scroll muda.
             */
            @Override
            public void onScrollChange(View viewAlterado, int novoScrollX, int novoScrollY, int anteriorScrollX, int anteriorScrollY) {
                // Quando o usuário movimenta o carrossel para a direita, a seta esquerda fica visível
                if (novoScrollX > 0) {
                    setaEsquerda.setVisibility(View.VISIBLE);
                } else {
                    setaEsquerda.setVisibility(View.INVISIBLE);
                }

                // Verifica se a última imagem do carrossel está sendo exibida
                View view = findViewById(R.id.carrosselTrote5);
                if (scrollView.isShown()) {
                    Rect scrollBounds = new Rect();
                    scrollView.getHitRect(scrollBounds);
                    if (view.getLocalVisibleRect(scrollBounds)) {
                        // A seta direita fica invisível quando a última imagem do carrossel é exibida
                        setaDireita.setVisibility(View.INVISIBLE);
                    } else {
                        setaDireita.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    /*
     * Método para voltar para a tela Nossas Ações (geral)
     */
    public void voltarTelaAcaoGeral1(View view) {
        Intent voltarTelaAcaoGeral = new Intent(getApplicationContext(), NossasAcoesGeral.class);
        startActivity(voltarTelaAcaoGeral);
    }
}