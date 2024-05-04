package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

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

        // Obter o idUsuarioLogado do SharedPreferences ou de onde você o armazenou
        String idUsuarioLogado = obterIdUsuarioLogado();

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

        realizarCadastroEvento("https://twm93x-3000.csb.app/cadastroEvento", eventoFecap, idUsuarioLogado);
    }

    void realizarCadastroEvento(String postUrl, final Evento evento, String idUsuarioLogado) {
        Map<String, String> dadosEvento = new HashMap<>();
        dadosEvento.put("evento", evento.getEvento());
        dadosEvento.put("data", evento.getData());
        dadosEvento.put("cidade", evento.getCidade());
        dadosEvento.put("logradouro", evento.getLogradouro());
        dadosEvento.put("numero", evento.getNumero());
        dadosEvento.put("idUsuarioLogado", idUsuarioLogado);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, new JSONObject(dadosEvento),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Aqui, 'response' é a resposta do servidor como um objeto JSON
                        // Você pode processá-la conforme necessário
                        try {
                            if (response.getString("status").equals("success")) {
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
                            } else {
                                // Exibir mensagem de erro
                                String mensagemErro = response.getString("message");
                                Toast.makeText(CadastroEvento.this, mensagemErro, Toast.LENGTH_SHORT).show();
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
                        // Imprime a mensagem de erro
                        Log.e("VolleyError", error.toString());

                        // Imprime a pilha de chamadas
                        error.printStackTrace();
                        // Falha no registro
                        Toast.makeText(CadastroEvento.this, "Erro ao cadastrar o evento", Toast.LENGTH_SHORT).show();
                    }
                });

        filaRequest.add(jsonObjectRequest);
    }

    public String obterIdUsuarioLogado() {
        SharedPreferences sharedPreferences = getSharedPreferences("PreferenciaUsuario", MODE_PRIVATE);
        return sharedPreferences.getString("id_user", "");
    }
}
