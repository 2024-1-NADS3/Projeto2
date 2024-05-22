package com.example.telas_iniciais;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Calendario extends AppCompatActivity {

    private RecyclerView recyclerViewEventos;
    private EventoAdapter eventoAdapter;
    private List<Evento> eventosList;
    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        eventosList = new ArrayList<>();
        recyclerViewEventos = findViewById(R.id.recyclerViewEventos);
        recyclerViewEventos.setLayoutManager(new LinearLayoutManager(this));
        eventoAdapter = new EventoAdapter(eventosList);
        recyclerViewEventos.setAdapter(eventoAdapter);

        // Mostra o modal de carregamento enquanto busca os eventos
        showLoadingModal();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                new FetchEventosTask().execute(selectedDate);
            }
        });

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

        // Obtém o número de dias no mês atual
        Calendar calendar = Calendar.getInstance();
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Itera sobre cada dia do mês atual e busca os eventos
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        for (int i = 1; i <= maxDayOfMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            String selectedDate = sdf.format(calendar.getTime());
            new FetchEventosTask().execute(selectedDate);
        }

        // Esconde o modal de carregamento após finalizar a busca de eventos
        hideLoadingModal();
    }

    /**
     * modal do loading
     */
    private void showLoadingModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.modal_loading, null);
        builder.setView(dialogView);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false); // Impede que o usuário cancele
        loadingDialog.show();
    }


    /**
     * esconde modal do loading
     */
    private void hideLoadingModal() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private class FetchEventosTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String selectedDate = params[0];
            String result = null;

            try {
                URL url = new URL("https://4nqjkx-3000.csb.app/buscarEventos?data=" + selectedDate);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
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
                        for (int i = 0; i < eventos.length(); i++) {
                            JSONObject eventoJson = eventos.getJSONObject(i);
                            Evento evento = new Evento(
                                    eventoJson.getString("evento"),
                                    eventoJson.getString("data"),
                                    eventoJson.getString("cidade"),
                                    eventoJson.getString("logradouro"),
                                    eventoJson.getString("numero")
                            );
                            eventosList.add(evento);
                        }
                        eventoAdapter.notifyDataSetChanged();
                    }
                    // Não precisa exibir aviso se não houver eventos para esta data
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Calendario.this, "Erro ao buscar eventos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
