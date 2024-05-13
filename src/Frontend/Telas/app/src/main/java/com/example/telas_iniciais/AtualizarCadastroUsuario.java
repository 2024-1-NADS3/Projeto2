package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity responsável pela atualização dos dados do usuário
 */
public class AtualizarCadastroUsuario extends AppCompatActivity {

    /**
     * SharedPreferences para armazenar e recuperar os dados do usuário
     */
    private SharedPreferences recuperarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_cadastro_usuario);

        recuperarDados = getSharedPreferences("salvarDados", Context.MODE_PRIVATE);

        atualizarDadosUsuario();
    }

    /**
     * Atualiza a interface do usuário com os novos dados sempre que a atividade é reiniciada
     */
    @Override
    protected void onResume() {
        super.onResume();

        atualizarDadosUsuario();
    }

    /**
     * Atualiza a interface do usuário com os dados do usuário
     */
    private void atualizarDadosUsuario() {
        // Recuperar os dados do usuário das SharedPreferences
        String nomeUsuario = recuperarDados.getString("nome_usuario", "");
        String emailUsuario = recuperarDados.getString("email_usuario", "");

        ClasseAtualizacao usuario = new ClasseAtualizacao(nomeUsuario, emailUsuario);

        EditText campoNome = findViewById(R.id.campoNome);
        EditText campoEmail = findViewById(R.id.campoEmail);

        campoNome.setText(usuario.getNome());
        campoEmail.setText(usuario.getEmail());
    }

    /**
     * Atualiza os dados do usuário no servidor quando o botão é clicado
     * e esse método recupera os novos valores inseridos pelo usuário
     */
    public void atualizarUsuario(View view){
        // Recuperar os dados do usuário das SharedPreferences
        String idUsuario = recuperarDados.getString("id_usuario", "");

        EditText campoNome = findViewById(R.id.campoNome);
        EditText campoEmail = findViewById(R.id.campoEmail);
        String novoNome = campoNome.getText().toString();
        String novoEmail = campoEmail.getText().toString();

        ClasseAtualizacao usuario = new ClasseAtualizacao(novoNome, novoEmail);

        usuario.setNome(novoNome);
        usuario.setEmail(novoEmail);

        // Envia uma requisição PUT para o servidor
        enviarRequisicaoAtualizacaoUsuario(idUsuario, usuario.getNome(), usuario.getEmail());
    }

    /**
     * Envia uma requisição PUT para o servidor para atualizar os dados do usuário
     */
    private void enviarRequisicaoAtualizacaoUsuario(String idUsuario, final String novoNome, final String novoEmail) {
        String url = "https://4nqjkx-3000.csb.app/atualizarUsuario";

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Usuário atualizado!")) {
                            // Atualiza as SharedPreferences com os novos dados
                            atualizarSharedPreferences(novoNome, novoEmail);

                            exibirSucessoAtualizacaoUsuario();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        exibirErroAtualizacaoUsuario(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadoUsuario = new HashMap<>();
                dadoUsuario.put("id_user", idUsuario);
                dadoUsuario.put("nome", novoNome);
                dadoUsuario.put("email", novoEmail);
                return dadoUsuario;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    /**
     * Método para atualizar as SharedPreferences
     */
    private void atualizarSharedPreferences(String novoNome, String novoEmail) {
        SharedPreferences.Editor dadosUsuario = recuperarDados.edit();
        dadosUsuario.putString("nome_usuario", novoNome);
        dadosUsuario.putString("email_usuario", novoEmail);
        dadosUsuario.apply();
    }

    /**
     * Exibe uma mensagem de sucesso quando a atualização do usuário é bem-sucedida
     */
    private void exibirSucessoAtualizacaoUsuario() {
        Toast.makeText(getApplicationContext(), "Usuário atualizado com sucesso!", Toast.LENGTH_LONG).show();

        AlertDialog.Builder dadosAtualizados = new AlertDialog.Builder(AtualizarCadastroUsuario.this);
        dadosAtualizados.setTitle("Atualização Concluída com Sucesso!!!");
        dadosAtualizados.setMessage("Obrigado pela atualização!");
        AlertDialog dialog = dadosAtualizados.create();
        dialog.show();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);
    }

    /**
     * Exibe uma mensagem de erro quando ocorre um erro na atualização dos dados
     */
    private void exibirErroAtualizacaoUsuario(String mensagemErro) {
        Toast.makeText(getApplicationContext(), "Erro na solicitação: " + mensagemErro, Toast.LENGTH_LONG).show();
    }

    /**
     * Método para voltar para a tela de perfil quando o botão (ícone) "<" é clicado.
     */
    public void voltarTelaPerfil(View view) {
        Intent voltarTelaPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(voltarTelaPerfil);
    }
}
