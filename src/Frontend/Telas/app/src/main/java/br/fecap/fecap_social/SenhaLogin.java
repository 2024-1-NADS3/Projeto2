package br.fecap.fecap_social;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.telas_iniciais.R;

public class SenhaLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha_login);
    }




    /**
     * Avança para e tela "configurações"
     */
    public void VoltaConfig(View view) {
        Intent volta = new Intent(getApplicationContext(), Config.class);
        startActivity(volta);
    }

    /**
     * confere a senha de acesso e direciona para a tela de login se correto
     */
    public void IrLogin(View view) {
        EditText inputTokenLogin;
        String token = "";

        // Encontre o EditText
        inputTokenLogin = findViewById(R.id.inputTokenLogin);

        // Use um loop para repetir o processo até que o token seja correto
        while (!token.equals("FecapSocial1SKL")) {
            // Leia o token do EditText
            token = inputTokenLogin.getText().toString();

            // Verifique se o token é correto
            if (token.equals("FecapSocial1SKL")) {
                // Se for correto, inicie a atividade de login e saia do loop
                Intent Irlogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(Irlogin);
                break;
            } else {
                // Se for incorreto, exiba uma mensagem de erro
                AlertDialog.Builder dadosLogin = new AlertDialog.Builder(SenhaLogin.this);
                dadosLogin.setTitle("Erro ao tentar ir para tela de Login");
                dadosLogin.setMessage("Token incorreto!!!");
                dadosLogin.setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Limpar o EditText quando o usuário clicar em "Tentar novamente"
                        inputTokenLogin.setText("");
                    }
                });
                dadosLogin.setIcon(R.drawable.alert_icon);
                AlertDialog dialog = dadosLogin.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.border_alert_dialog);

                // Sair do loop para evitar um loop infinito quando o token é incorreto
                break;
            }
        }
    }


}