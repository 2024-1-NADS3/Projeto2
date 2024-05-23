package br.fecap.fecap_social;

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
import com.example.telas_iniciais.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {

    private static final long tempoDelayParaMudarTela = 3000;
    private RequestQueue filaRequest;
    private Cripto cripto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        filaRequest = Volley.newRequestQueue(this);
        cripto = new Cripto(3);
    }

    /**
     * método criado para voltar a tela de login ao clicar no botão
     */
    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(voltarTelaLogin);
    }

    /**
     * Método chamado quando o botão "Cadastrar" é clicado para cadastrar um usuário.
     */
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


        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmeSenha.isEmpty()) {
            AlertDialog.Builder camposVazios = new AlertDialog.Builder(CadastroActivity.this);
            camposVazios.setTitle("Erro");
            camposVazios.setMessage("Todos os campos devem ser preenchidos");
            camposVazios.setPositiveButton("OK", null);
            camposVazios.setIcon(R.drawable.alert_icon);
            AlertDialog dialog = camposVazios.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
            return;
        }

        if (!senha.equals(confirmeSenha)) {
            AlertDialog.Builder senhaConfirme = new AlertDialog.Builder(CadastroActivity.this);
            senhaConfirme.setTitle("Erro");
            senhaConfirme.setMessage("As senhas inseridas não coincidem");
            senhaConfirme.setPositiveButton("OK", null);
            senhaConfirme.setIcon(R.drawable.alert_icon);
            AlertDialog dialog = senhaConfirme.create();
            dialog.show();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.WHITE);
            positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
            return;
        }

        // Criptografa os campos antes de criar o objeto usuário
        String nomeCriptografado = cripto.encrypt(nome);
        String emailCriptografado = cripto.encrypt(email);
        String senhaCriptografada = cripto.encrypt(senha);

        ClasseUsuario usuario = new ClasseUsuario(nomeCriptografado, emailCriptografado, senhaCriptografada);

        realizarCadastro("https://4nqjkx-3000.csb.app/cadastro", usuario);
    }


    /**
     * Método para realizar o cadastro do usuário no servidor.
     */
    void realizarCadastro(String postUrl, final ClasseUsuario usuario) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, postUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message = jsonResponse.getString("message");

                            if (message.equals("Email já cadastrado!")) {
                                Toast.makeText(CadastroActivity.this, "E-mail já cadastrado!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
                                dadosCadastro.setTitle("Erro!!!");
                                dadosCadastro.setMessage("Email já cadastrado!");
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.setNegativeButton("Tentar Novamente", null);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                                Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(Color.WHITE);
                                negativeButton.setBackgroundColor(Color.parseColor("#FCBA51"));
                            } else {
                                Toast.makeText(CadastroActivity.this, "Cadastro bem-sucedido!", Toast.LENGTH_SHORT).show();

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(CadastroActivity.this);
                                dadosCadastro.setTitle("Cadastro Concluído com Sucesso!!!");
                                dadosCadastro.setMessage("Obrigado pelo seu cadastro!");
                                dadosCadastro.setIcon(R.drawable.icon_check);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

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
                        Toast.makeText(CadastroActivity.this, "Erro ao se cadastrar", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<>();
                dadosDoUsuario.put("nome", usuario.getNome());
                dadosDoUsuario.put("email", usuario.getEmail());
                dadosDoUsuario.put("senha", usuario.getSenha());
                return dadosDoUsuario;
            }
        };

        filaRequest.add(stringRequest);
    }
}
