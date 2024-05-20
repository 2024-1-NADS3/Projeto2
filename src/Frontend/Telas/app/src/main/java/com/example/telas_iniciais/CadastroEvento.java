package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity responsável pelo Cadastro de eventos da Fecap Social
 */
public class CadastroEvento extends AppCompatActivity {

    private static final long tempoDelayParaMudarTela = 3000;

    private RequestQueue filaRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        filaRequest = Volley.newRequestQueue(this);
    }

    /**
     * Método quando clicar no botão (ícone) para voltar para a tela de Perfil
     */
    public void VoltarPerfil(View view){
        Intent VoltarPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(VoltarPerfil);
    }

    /**
     * Método chamado quando o botão "Cadastrar" é clicado para cadastrar um evento.
     */
    public void botaoCadastroEvento(View view) {
        EditText inputEvento, inputData, inputCidade, inputLogradouro, inputNumero;
        String evento, data, cidade, logradouro, numero;

        inputEvento = findViewById(R.id.evento);
        inputData = findViewById(R.id.data);
        inputCidade = findViewById(R.id.cidade);
        inputLogradouro = findViewById(R.id.logradouro);
        inputNumero = findViewById(R.id.numero);

        evento = inputEvento.getText().toString();
        data = inputData.getText().toString();
        cidade = inputCidade.getText().toString();
        logradouro = inputLogradouro.getText().toString();
        numero = inputNumero.getText().toString();

        /**
         * Se o usuário deixar algum campo vazio exibe um AlertDialog de Erro
         */
        if (evento.isEmpty() || data.isEmpty() || cidade.isEmpty() || logradouro.isEmpty() || numero.isEmpty()) {
            AlertDialog.Builder camposVazios = new AlertDialog.Builder(CadastroEvento.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton("OK", null);
            camposVazios.setIcon(R.drawable.alert_icon);
            AlertDialog dialog = camposVazios.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

            // Alterar a cor do texto do botão
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
            return;
        }

        Evento eventoFecap = new Evento(evento, data, cidade, logradouro, numero);

        realizarCadastroEvento("https://4nqjkx-3000.csb.app/cadastroEvento", eventoFecap);
    }

    /**
     * Método para realizar o cadastro do evento no servidor.
     */
    void realizarCadastroEvento(String postUrl, final Evento evento) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getString("status").equals("sucesso")) {

                                // Registro bem-sucedido
                                Toast.makeText(CadastroEvento.this, "Cadastro do evento bem-sucedido!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroEvento.this);
                                dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
                                dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
                                dadosCadastro.setIcon(R.drawable.icon_check);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(CadastroEvento.this, PerfilActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, tempoDelayParaMudarTela);
                            } else {
                                // Exibir mensagem de erro
                                String mensagemErro = jsonResponse.getString("message");
                                Toast.makeText(CadastroEvento.this, mensagemErro, Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroEvento.this);
                                dadosCadastro.setTitle("Erro!!!");
                                dadosCadastro.setMessage("Evento já cadastrado!");
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.setNegativeButton("Tentar Novamente", null);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                                // Alterar a cor do texto do botão
                                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(Color.WHITE);
                                negativeButton.setBackgroundColor(Color.parseColor("#FCBA51"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CadastroEvento.this, "Erro ao processar resposta do servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CadastroEvento.this, "Erro ao cadastrar o evento", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosEvento = new HashMap<>();
                dadosEvento.put("evento", evento.getEvento());
                dadosEvento.put("data", evento.getData());
                dadosEvento.put("cidade", evento.getCidade());
                dadosEvento.put("logradouro", evento.getLogradouro());
                dadosEvento.put("numero", evento.getNumero());
                return dadosEvento;
            }
        };

        filaRequest.add(stringRequest);
    }
}
