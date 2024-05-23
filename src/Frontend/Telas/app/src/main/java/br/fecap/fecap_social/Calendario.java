package br.fecap.fecap_social;

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

import com.example.telas_iniciais.R;
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

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                showLoadingModal();
                new FetchEventosTask().execute(selectedDate);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuHome:
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                    return true;
                case R.id.menuCalendar:
                    return true;
                case R.id.menuDoacao:
                    startActivity(new Intent(getApplicationContext(), Doacao.class));
                    finish();
                    return true;
                case R.id.menuConfig:
                    startActivity(new Intent(getApplicationContext(), Config.class));
                    finish();
                    return true;
            }
            return false;
        });

        // Obtém o número de dias no mês atual e busca os eventos
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        int maxDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        showLoadingModal();
        for (int i = 1; i <= maxDayOfMonth; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            String selectedDate = sdf.format(calendar.getTime());
            new FetchEventosTask().execute(selectedDate);
        }
    }

    /** Exibe o diálogo de carregamento */
    private void showLoadingModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.modal_loading, null);
        builder.setView(dialogView);
        loadingDialog = builder.create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    /** Esconde o diálogo de carregamento */
    private void hideLoadingModal() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /** Classe AsyncTask para buscar eventos em segundo plano */
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
            hideLoadingModal();
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Calendario.this, "Erro ao buscar eventos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
