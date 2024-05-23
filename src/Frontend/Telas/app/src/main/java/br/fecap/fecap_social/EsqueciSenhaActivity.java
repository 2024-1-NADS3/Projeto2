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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.telas_iniciais.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private static final long tempoDelayMudarTela = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);
    }

    /**
     * volta para e tela "login"
     */
    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(voltarTelaLogin);
    }

    /**
     * Envia o email para o usuário
     */
    public void enviarEsqueceu(View view) {
        EditText inputEmailEsqueceu;
        String email;

        inputEmailEsqueceu = findViewById(R.id.inputEmailEsqueceu);
        email = inputEmailEsqueceu.getText().toString();

        ClasseUsuario usuario = new ClasseUsuario(email);

        String url = "https://4nqjkx-3000.csb.app/esqueceu-senha";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if (status.equals("sucesso")) {

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(EsqueciSenhaActivity.this);
                                dadosCadastro.setTitle("Email enviado com sucesso!!!");
                                dadosCadastro.setMessage("Em breve enviaremos um email com o Token!");
                                dadosCadastro.setIcon(R.drawable.icon_check);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(EsqueciSenhaActivity.this, RedefinirSenhaActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, tempoDelayMudarTela);
                            } else {

                                AlertDialog.Builder dadosCadastro = new AlertDialog.Builder(EsqueciSenhaActivity.this);
                                dadosCadastro.setTitle("Erro");
                                dadosCadastro.setMessage(message);
                                dadosCadastro.setIcon(R.drawable.alert_icon);
                                dadosCadastro.setPositiveButton("OK", null);
                                AlertDialog dialog = dadosCadastro.create();
                                dialog.show();

                                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                                // Alterar a cor do texto do botão
                                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                positiveButton.setTextColor(Color.WHITE);
                                positiveButton.setBackgroundColor(Color.parseColor("#FCBA51"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EsqueciSenhaActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EsqueciSenhaActivity.this, "Ocorreu um erro. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> dadosDoUsuario = new HashMap<String, String>();
                dadosDoUsuario.put("email", usuario.getEmail());
                return dadosDoUsuario;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }
}
