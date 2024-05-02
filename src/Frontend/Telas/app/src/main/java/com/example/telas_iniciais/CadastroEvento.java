package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastroEvento extends AppCompatActivity {

    private static final long tempoDelayParaMudarTela = 3000;

    private RequestQueue filaRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        filaRequest = Volley.newRequestQueue(this);
    }

    /** função que faz voltar para a tela de perfil quando clicar no botão */
    public void VoltarPerfil(View view){
        Intent VoltarPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(VoltarPerfil);
    }

    public void botaoCadastroEvento(View view) {
        EditText inputEvento, inputData, inputCidade, inputLogradouro, inputNumero;
        String evento, data, cidade, logradouro, numero;

        inputEvento = findViewById(R.id.editTextTextPersonName);
        inputData = findViewById(R.id.editTextTextPersonName2);
        inputCidade = findViewById(R.id.editTextTextPersonName3);
        inputLogradouro = findViewById(R.id.editTextTextPersonName4);
        inputNumero = findViewById(R.id.editTextTextPersonName5);

        evento = inputEvento.getText().toString();
        data = inputData.getText().toString();
        cidade = inputCidade.getText().toString();
        logradouro = inputLogradouro.getText().toString();
        numero = inputNumero.getText().toString();

        /*
         * Se o usuário deixar algum campo vazio exibe um AlertDialog de Erro
         */
        if (evento.isEmpty() || data.isEmpty() || cidade.isEmpty() || logradouro.isEmpty() || numero.isEmpty()) {
            AlertDialog.Builder camposVazios = new AlertDialog.Builder(CadastroEvento.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton(android.R.string.ok, null);
            camposVazios.setIcon(R.drawable.alert_icon);
            camposVazios.create().show();
            return;
        }

        Evento eventoFecap = new Evento(evento, data, cidade, logradouro, numero);

        String id_user = getIntent().getStringExtra("id_user");

        eventoFecap.setId_user(id_user);

        realizarCadastroEvento("https://twm93x-3000.csb.app/cadastroEvento", eventoFecap);
    }

    void realizarCadastroEvento(String postUrl, final Evento evento) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("evento", evento.getEvento());
            jsonBody.put("data", evento.getData());
            jsonBody.put("cidade", evento.getCidade());
            jsonBody.put("logradouro", evento.getLogradouro());
            jsonBody.put("numero", evento.getNumero());
            jsonBody.put("id_user", evento.getId_user());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtém a mensagem da resposta
                            String message = response.getString("message");

                            if (message.equals("Evento já cadastrado!")) {
                                Toast.makeText(CadastroEvento.this, "Evento já cadastrado!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroEvento.this);
                                dadosCadastro.setTitle("Erro!!!");
                                dadosCadastro.setMessage("Evento já cadastrado!");
                                dadosCadastro.setIcon(android.R.drawable.ic_dialog_alert);
                                dadosCadastro.setNegativeButton("Tentar Novamente", null);
                                dadosCadastro.create().show();
                            } else {
                                // Registro bem-sucedido
                                Toast.makeText(CadastroEvento.this, "Cadastro do evento bem-sucedido!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroEvento.this);
                                dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
                                dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.create().show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(CadastroEvento.this, PerfilActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, tempoDelayParaMudarTela);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Falha no registro
                        Toast.makeText(CadastroEvento.this, "Erro ao cadastrar o evento", Toast.LENGTH_SHORT).show();
                    }
                });

        filaRequest.add(jsonObjectRequest);
    }
}
