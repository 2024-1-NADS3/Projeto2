package com.example.telas_iniciais;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLNonTransientConnectionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CadastroActivity extends AppCompatActivity {

    connection connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;


    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        connectionClass = new connection();

        editTextNome = findViewById(R.id.inputNomeCadastro);
        editTextEmail = findViewById(R.id.inputEmailCadastro);
        editTextSenha = findViewById(R.id.inputSenhaCadastro);

        Button buttonCadastrar = findViewById(R.id.btnCadastrar);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    public void voltarTelaLogin(View view) {
        Intent voltarTelaLogin = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(voltarTelaLogin);
    }

    public static class MySQLConnection {
        private static final String URL = "dbc:mysql://fecapsocialbd.mysql.database.azure.com:3306/fecapsocialdb";
        private static final String USER = "fecapsocial@fecapsocialbd";
        private static final String PASSWORD = "Hamburgada@";

        public static Connection getConnection() throws SQLException {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new SQLException("MySQL JDBC driver não encontrado.", e);
            }
        }
    }

    public class Cadastro {
        private int id;
        private String nome;
        private String email;
        private String senha;

        public Cadastro(String nome, String email, String senha) {
            this.nome = nome;
            this.email = email;
            this.senha = senha;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
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

    private void cadastrarUsuario() {
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();

        if (!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty()) {
            Cadastro cadastro = new Cadastro(nome, email, senha);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    con = connectionClass.CONN();
                    if (con != null) {
                        PreparedStatement statement = con.prepareStatement("INSERT INTO cadastro (nome, email, senha) VALUES (?, ?, ?)");
                        statement.setString(1, cadastro.getNome());
                        statement.setString(2, cadastro.getEmail());
                        statement.setString(3, cadastro.getSenha());
                        statement.executeUpdate();
                        statement.close();
                        str = "Usuário cadastrado com sucesso!";
                    } else {
                        str = "Erro na conexão";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    str = "Erro ao cadastrar usuário: " + e.getMessage();
                }

                runOnUiThread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, str, Toast.LENGTH_LONG).show();
                });
            });
        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }





}

