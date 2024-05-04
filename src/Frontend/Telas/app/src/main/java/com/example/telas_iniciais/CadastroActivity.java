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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private static final long tempoDelayParaMudarTela = 3000;
    private RequestQueue filaRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        filaRequest = Volley.newRequestQueue(this);
    }

    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(voltarTelaLogin);
    }

    public void BotaoCadastro(View view) {
        EditText inputNomeCadastro, inputEmailCadastro, inputSenhaCadastro, inputSenhaConfirme;
        String nome, email, senha, confirmeSenha;

        inputNomeCadastro = findViewById(R.id.inputNomeCadastro);
        inputEmailCadastro = findViewById(R.id.inputEmailCadastro);
        inputSenhaCadastro = findViewById(R.id.inputSenhaCadastro);
        inputSenhaConfirme = findViewById(R.id.inputSenhaConfirme);

        nome = inputNomeCadastro.getText().toString();
        email = inputEmailCadastro.getText().toString();
        senha = inputSenhaCadastro.getText().toString();
        confirmeSenha = inputSenhaConfirme.getText().toString();

        /*
         * Se o usuário deixar algum campo vazio exibe um AlertDialog de Erro
         */
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmeSenha.isEmpty()) {
            AlertDialog.Builder camposVazios = new AlertDialog.Builder(CadastroActivity.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton(android.R.string.ok, null);
            camposVazios.setIcon(R.drawable.alert_icon);
            camposVazios.create().show();
            return;
        }

        /*
         * Se o usuário inserir senhas diferentes nos campos de senha, exibe um AlertDialog de Erro
         */
        if (!senha.equals(confirmeSenha)) {
            AlertDialog.Builder senhaConfirme = new AlertDialog.Builder(CadastroActivity.this);
            senhaConfirme.setTitle("Erro");
            senhaConfirme.setMessage("As senhas inseridas não coincidem");
            senhaConfirme.setPositiveButton(android.R.string.ok, null);
            senhaConfirme.setIcon(R.drawable.alert_icon);
            senhaConfirme.create().show();
            return;
        }


        Usuario usuario = new Usuario(nome, email, senha);

        realizarCadastro("https://twm93x-3000.csb.app/cadastro", usuario);
    }

    void realizarCadastro(String postUrl, final Usuario usuario) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Converte a resposta em um objeto JSON
                            JSONObject jsonResponse = new JSONObject(response);

                            // Obtém a mensagem da resposta
                            String message = jsonResponse.getString("message");

                            if(message.equals("Email já cadastrado!")){
                                Toast.makeText(CadastroActivity.this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
                                dadosCadastro.setTitle("Erro!!!");
                                dadosCadastro.setMessage("Email já cadastrado!");
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.setNegativeButton("Tentar Novamente", null);
                                dadosCadastro.create().show();
                            } else{
                                // Registro bem-sucedido
                                Toast.makeText(CadastroActivity.this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
                                dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
                                dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.create().show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
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
                        Toast.makeText(CadastroActivity.this, "Erro ao se cadastrar", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<String, String>();
                dadosDoUsuario.put("nome", usuario.getNome());
                dadosDoUsuario.put("email", usuario.getEmail());
                dadosDoUsuario.put("senha", usuario.getSenha());
                return dadosDoUsuario;
            }
        };

        filaRequest.add(stringRequest);
    }
}
