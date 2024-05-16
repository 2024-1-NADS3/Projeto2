package com.example.telas_iniciais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Calendario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        // Lógica do calendário
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Define um ouvinte de clique para o CalendarView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Formatar a data selecionada
                String selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year);

                // Fazer uma solicitação HTTP para buscar eventos
                new FetchEventosTask().execute(selectedDate);
            }
        });

        // Lógica do componente de interface do usuário
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    Intent menuHome = new Intent(getApplicationContext(), Home.class);
                    startActivity(menuHome);
                    finish();
                    return true;

                case R.id.menuCalendar:
                    return true;

                case R.id.menuDoacao:
                    Intent menuDoacao = new Intent(getApplicationContext(), Doacao.class);
                    startActivity(menuDoacao);
                    finish();
                    return true;

                case R.id.menuConfig:
                    Intent menuConfig = new Intent(getApplicationContext(), Config.class);
                    startActivity(menuConfig);
                    finish();
                    return true;
            }
            return false;
        });
    }

    private class FetchEventosTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String selectedDate = params[0];
            String result = null;

            try {
                // Construir a URL com a data selecionada como parâmetro da consulta
                URL url = new URL("https://4nqjkx-3000.csb.app/buscarEventos?data=" + selectedDate);

                // Abrir a conexão
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Ler a resposta
                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    // Se não houver dados, retornar null
                    return null;
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Fechar os recursos
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject json = new JSONObject(result);
                    String status = json.getString("status");

                    if (status.equals("sucesso")) {
                        JSONArray eventos = json.getJSONArray("eventos");
                        // Aqui você pode processar os eventos retornados
                        // Por exemplo, exibir as informações em um Toast
                        for (int i = 0; i < eventos.length(); i++) {
                            JSONObject evento = eventos.getJSONObject(i);
                            String nomeEvento = evento.getString("evento");
                            String cidade = evento.getString("cidade");
                            String logradouro = evento.getString("logradouro");
                            String numero = evento.getString("numero");

                            String message = "Evento: " + nomeEvento + "\nCidade: " + cidade + "\nLogradouro: " + logradouro + "\nNúmero: " + numero;
                            Toast.makeText(Calendario.this, message, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Calendario.this, "Nenhum evento encontrado para esta data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Calendario.this, "Erro ao buscar eventos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
