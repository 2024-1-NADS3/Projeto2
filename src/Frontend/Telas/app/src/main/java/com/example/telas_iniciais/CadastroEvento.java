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
public class CadastroEvento extends AppCompatActivity {

    connection connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    /** declaracao de variáveis */

    private EditText editTextNomeEvento;
    private EditText editTextDataEvento;
    private EditText editTextLogradouro;
    private EditText editTextNumLogradouro;
    private EditText editTextCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        
        /** instancia a classe connection, onde estão todas as credenciais do bd */
        connectionClass = new connection();

        editTextNomeEvento = findViewById(R.id.editTextTextPersonName);
        editTextDataEvento = findViewById(R.id.editTextTextPersonName2);
        editTextLogradouro = findViewById(R.id.editTextTextPersonName4);
        editTextNumLogradouro = findViewById(R.id.editTextTextPersonName5);
        editTextCidade = findViewById(R.id.editTextTextPersonName3);

        Button buttonCadastrar = findViewById(R.id.btnCadastrarEv);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarEvento();
            }
        });
    }

    /** função que faz voltar para a tela de perfil quando clicar no botão */
    public void VoltarPerfil(View view){
        Intent VoltarPerfil = new Intent(getApplicationContext(), PerfilActivity.class);
        startActivity(VoltarPerfil);

    }
    /** gets e sets de cada variável */
    public class CadastroEventoGetsSets {
        private String nomeEvento;
        private String dataEvento;
        private String cidade;
        private String logradouro;
        private String logradouroNum;


        public CadastroEventoGetsSets(String nomeEvento, String dataEvento, String cidade ,String logradouro, String logradouroNum) {
            this.nomeEvento = nomeEvento;
            this.dataEvento = dataEvento;
            this.cidade = cidade;
            this.logradouro = logradouro;
            this.logradouroNum = logradouroNum;

        }

        public String getnomeEvento() {
            return nomeEvento;
        }

        public void setnomeEvento(String nomeEvento) {
            this.nomeEvento = nomeEvento;
        }


        public String getdataEvento() {
            return dataEvento;
        }

        public void setdataEvento(String dataEvento) {
            this.dataEvento = dataEvento;
        }


        public String getcidade() {
            return cidade;
        }

        public void setcidade(String cidade) {
            this.cidade = cidade;
        }


        public String getlogradouro() {
            return logradouro;
        }

        public void setlogradouro(String logradouro) {
            this.logradouro = logradouro;
        }


        public String getlogradouroNum() {
            return logradouroNum;
        }

        public void setlogradouroNum(String logradouroNum) {
            this.logradouroNum = logradouroNum;
        }

        }

    /** Função para cadastro do evento */
    private void cadastrarEvento() {
        /** Pega o input do usuário, transforma em texto e remove espaços em branco */
        String nomeEvento = editTextNomeEvento.getText().toString().trim();
        String dataEvento = editTextDataEvento.getText().toString().trim();
        String cidade = editTextLogradouro.getText().toString().trim();
        String logradouro = editTextNumLogradouro.getText().toString().trim();
        String logradouroNum = editTextCidade.getText().toString().trim();


        if (!nomeEvento.isEmpty() && !dataEvento.isEmpty() && !cidade.isEmpty() && !logradouro.isEmpty() && !logradouroNum.isEmpty()) {
            CadastroEventoGetsSets CadastroEventoGetsSets = new CadastroEventoGetsSets(nomeEvento, dataEvento, cidade, logradouro, logradouroNum);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    con = connectionClass.CONN();
                    if (con != null) {
                        PreparedStatement statement = con.prepareStatement("INSERT INTO eventos (nome_evento, data_evento, logradouro, cidade, numero_logradouro) VALUES (?, ?, ?, ?, ?)");
                        statement.setString(1, CadastroEventoGetsSets.getnomeEvento());
                        statement.setString(2, CadastroEventoGetsSets.getdataEvento());
                        statement.setString(3, CadastroEventoGetsSets.getlogradouro());
                        statement.setString(4, CadastroEventoGetsSets.getcidade());
                        statement.setString(5, CadastroEventoGetsSets.getlogradouroNum());
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