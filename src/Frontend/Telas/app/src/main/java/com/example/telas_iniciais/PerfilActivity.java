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

/**
 * Activity responsável pela tela de Perfil do usuário
 */
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

    /**
     * Exclui a conta do usuário após confirmar com um AlertDialog.
     * Chamado quando o botão de exclusão de conta é clicado.
     */
    public void excluirConta(View view) {
        confirmarExclusaoConta();
    }

    /**
     * Exibe um AlertDialog para ver se o usuário tem certeza que quer excluir a conta.
     */
    private void confirmarExclusaoConta() {
        new AlertDialog.Builder(this)
                .setTitle("Excluir Conta")
                .setMessage("Tem certeza de que deseja excluir sua conta?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences recuperarDados = getSharedPreferences("salvarDados", Context.MODE_PRIVATE);
                        String idUsuario = recuperarDados.getString("id_usuario", "");
                        executarExclusaoConta(idUsuario);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(R.drawable.alert_icon)
                .show();
    }

    /**
     * Executa a exclusão da conta do usuário.
     * Recupera o ID do usuário, realiza a requisição DELETE para o servidor
     * e exibe mensagens de sucesso ou erro.
     */
    private void executarExclusaoConta(String idUsuario) {
        String url = "https://4nqjkx-3000.csb.app/deletarUsuario?id_user=" + idUsuario;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.contains("Usuário excluído com sucesso!")) {
                            exibirSucessoExclusaoConta();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        exibirErroExclusaoConta(error.toString());
                    }
                });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    /**
     * Exibe um AlertDialog informando que a conta foi excluída com sucesso
     */
    private void exibirSucessoExclusaoConta() {
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

    /**
     * Exibe uma mensagem de erro caso ocorra um problema durante a exclusão da conta.
     */
    private void exibirErroExclusaoConta(String mensagemErro) {
        Toast.makeText(getApplicationContext(), "Erro na solicitação: " + mensagemErro, Toast.LENGTH_LONG).show();
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

    /**
     *  Método para o ir para a tela de atualização de cadastro do usuario
     */
    public void IrParaTelaAtualizacaoUsuario(View view){
        Intent mudarTelaAtualizacao = new Intent(getApplicationContext(), AtualizarCadastroUsuario.class);
        startActivity(mudarTelaAtualizacao);
    }
}
