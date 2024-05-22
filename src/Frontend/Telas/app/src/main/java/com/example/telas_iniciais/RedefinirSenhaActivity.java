package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity responsável pela tela Redefinir Senha
 */
public class RedefinirSenhaActivity extends AppCompatActivity {

    private static final long tempoDelayMudarTela = 3000;
    private Cripto cripto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        cripto = new Cripto(3);
    }

    /**
     * Método quando clicar no botão (ícone) para voltar para a tela de Esqueci a senha
     */
    public void voltarTelaEsqueceuSenha(View view) {
        Intent voltarTelaEsqueceuSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(voltarTelaEsqueceuSenha);
    }

    /**
     * Método chamado quando o botão "Enviar" é clicado para enviar o email.
     */
    public void botaoRedefinirSenha(View view) {
        EditText inputToken, inputNovaSenha, inputNovaSenhaConfirmar;
        String token, novaSenha, novaSenhaConfirmar;

        inputToken = findViewById(R.id.inputToken);
        inputNovaSenha = findViewById(R.id.inputNovaSenha);
        inputNovaSenhaConfirmar = findViewById(R.id.inputNovaSenhaConfirmar);

        token = inputToken.getText().toString();
        novaSenha = inputNovaSenha.getText().toString();
        novaSenhaConfirmar = inputNovaSenhaConfirmar.getText().toString();

        /**
         * Se o usuário deixar algum campo vazio exibe um AlertDialog de Erro
         */
        if (token.isEmpty() || novaSenha.isEmpty() || novaSenhaConfirmar.isEmpty()) {
            androidx.appcompat.app.AlertDialog.Builder camposVazios = new androidx.appcompat.app.AlertDialog.Builder(RedefinirSenhaActivity.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton("OK", null);
            camposVazios.setIcon(R.drawable.alert_icon);
            androidx.appcompat.app.AlertDialog dialog = camposVazios.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

            // Alterar a cor do texto do botão
            Button positiveButton = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
            return;
        }

        /**
         * Se o usuário inserir senhas diferentes nos campos de senha, exibe um AlertDialog de Erro
         */
        if (!novaSenha.equals(novaSenhaConfirmar)) {
            androidx.appcompat.app.AlertDialog.Builder senhaConfirme = new androidx.appcompat.app.AlertDialog.Builder(RedefinirSenhaActivity.this);
            senhaConfirme.setTitle("Erro");
            senhaConfirme.setMessage("As senhas inseridas não coincidem");
            senhaConfirme.setPositiveButton("OK", null);
            senhaConfirme.setIcon(R.drawable.alert_icon);
            androidx.appcompat.app.AlertDialog dialog = senhaConfirme.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

            // Alterar a cor do texto do botão
            Button positiveButton = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
            return;
        }


        String novaSenhaCriptografada = cripto.encrypt(novaSenha);
        String novaSenhaConfirmarCriptografada = cripto.encrypt(novaSenhaConfirmar);

        ClasseRedefinirSenha usuarioCriptografado = new ClasseRedefinirSenha(token, novaSenhaCriptografada, novaSenhaConfirmarCriptografada);

        String url = "https://4nqjkx-3000.csb.app/redefinir-senha";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if ("sucesso".equals(status)) {
                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(RedefinirSenhaActivity.this);
                                dadosCadastro.setTitle("Senha redefinida com sucesso!!!");
                                dadosCadastro.setMessage(message);
                                dadosCadastro.setIcon(R.drawable.icon_check);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(RedefinirSenhaActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, tempoDelayMudarTela);
                            } else {
                                AlertDialog.Builder erroCadastro = new AlertDialog.Builder(RedefinirSenhaActivity.this);
                                erroCadastro.setTitle("Erro");
                                erroCadastro.setMessage(message);
                                erroCadastro.setIcon(R.drawable.alert_icon);
                                AlertDialog dialog = erroCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);
                                Button positiveButton = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE);
                                positiveButton.setTextColor(Color.WHITE);
                                positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RedefinirSenhaActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RedefinirSenhaActivity.this, "Ocorreu um erro. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<>();
                dadosDoUsuario.put("token", usuarioCriptografado.getToken());
                dadosDoUsuario.put("novaSenha", usuarioCriptografado.getNovaSenha());
                dadosDoUsuario.put("novaSenhaConfirmar", usuarioCriptografado.getNovaSenhaConfirmar());
                return dadosDoUsuario;
            }
        };

        Volley.newRequestQueue(RedefinirSenhaActivity.this).add(postRequest);
    }
}
