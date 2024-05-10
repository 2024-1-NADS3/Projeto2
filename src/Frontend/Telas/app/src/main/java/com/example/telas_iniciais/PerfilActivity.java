package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {

    private static final long tempoDelayParaMudarTela = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Recuperar os dados do usuário das SharedPreferences
        SharedPreferences recuperarDados = getSharedPreferences("salvarDados", Context.MODE_PRIVATE);
        String nomeUsuario = recuperarDados.getString("nome_usuario", "");
        String emailUsuario = recuperarDados.getString("email_usuario", "");

        // Exibir os dados do usuário nos campos correspondentes
        TextView campoNome = findViewById(R.id.campo_nome);
        TextView campoEmail = findViewById(R.id.campo_email);

        campoNome.setText(nomeUsuario);
        campoEmail.setText(emailUsuario);

    }

    public void excluirConta(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Conta")
                .setMessage("Tem certeza de que deseja excluir sua conta?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Recuperar o ID do usuário
                        SharedPreferences recuperarDados = getSharedPreferences("salvarDados", Context.MODE_PRIVATE);
                        String idUsuarioConectado = recuperarDados.getString("idUsuarioConectado", null);
                        String emailUsuarioConectado = recuperarDados.getString("emailUsuarioConectado", null);

                        // Verificar se o ID do usuário e o email não são nulos
                        if (idUsuarioConectado != null && emailUsuarioConectado != null) {

                            String url = "https://4nqjkx-3000.csb.app/deletarUsuario";

                            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("Usuário excluído!")) {
                                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(PerfilActivity.this);
                                                dadosCadastro.setTitle("Conta Excluída com Sucesso!!!");
                                                dadosCadastro.setMessage("Sua conta foi excluída!");
                                                dadosCadastro.create().show();

                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }, tempoDelayParaMudarTela);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Erro na solicitação: " + error.toString(), Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> dadosUsuario = new HashMap<String, String>();
                                    dadosUsuario.put("id", idUsuarioConectado);
                                    dadosUsuario.put("email", emailUsuarioConectado);
                                    return dadosUsuario;
                                }
                            };

                            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
                        } else {

                            Toast.makeText(getApplicationContext(), "Não foi possível excluir a conta. Tente novamente mais tarde.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(R.drawable.alert_icon)
                .show();
    }

    /**
     *  Função que direciona o usuário para a tela de cadastro de eventos
     */
    public void IrTelaCadEv(View view){
        Intent IrTelaCadEv = new Intent(getApplicationContext(), CadastroEvento.class);
        startActivity(IrTelaCadEv);
    }

    /**
     *  Método para o usuário sair da conta e ir para a tela de Perfil
     */
    public void SairDaConta(View view){
        Intent SairConta = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(SairConta);
    }
}
