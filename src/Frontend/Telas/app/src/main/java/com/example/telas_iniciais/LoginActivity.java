package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;

    String name, str;

    connection connectionClass;
    Connection con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.inputEmailLogin);
        editTextSenha = findViewById(R.id.inputSenhaLogin);
        connectionClass = new connection();

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerLogin();
            }
        });
    }

    public void mudarTelaCadastro(View view) {
        Intent mudarTelaCadastro = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(mudarTelaCadastro);
    }

    public void mudarTelaEsqueciSenha(View view) {
        Intent mudarTelaEsqueciSenha = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(mudarTelaEsqueciSenha);
    }

    public class Login {
        private int id;
        private String email;
        private String senha;

        public Login(String email, String senha) {
            this.email = email;
            this.senha = senha;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }
    }

    private void fazerLogin() {
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        if (!email.isEmpty() && !senha.isEmpty()) {
            Login login = new Login(email, senha);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    con = connectionClass.CONN();
                    if (con != null) {
                        PreparedStatement statement = con.prepareStatement("SELECT * FROM cadastro WHERE email=? AND senha=?");
                        statement.setString(1, login.getEmail());
                        statement.setString(2, login.getSenha());
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            // Login bem-sucedido
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Login bem-sucedido!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
                                startActivity(intent);
                                finish();
                            });
                        } else {
                            // Login falhou - email ou senha incorretos
                            runOnUiThread(() -> {
                                Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show();
                            });
                        }

                        resultSet.close();
                        statement.close();
                    } else {
                        // Erro na conexão com o banco de dados
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Erro na conexão com o banco de dados", Toast.LENGTH_SHORT).show();
                        });
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Erro durante o login: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            });
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }

}