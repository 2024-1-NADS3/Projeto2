package br.fecap.fecap_social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_iniciais.R;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventos;

    public EventoAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    /** Cria novas visualizações (chamado pelo layout manager) */
    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    /** Substitui o conteúdo de uma visualização (chamado pelo layout manager) */
    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.textNomeEvento.setText(evento.getEvento());
        holder.dataEvento.setText("Data: " + evento.getData());
        holder.textCidade.setText("Cidade: " + evento.getCidade());
        holder.textLogradouro.setText("Logradouro: " + evento.getLogradouro());
        holder.textNumero.setText("Número: " + evento.getNumero());
    }

    /** Retorna o tamanho da sua lista (chamado pelo layout manager) */
    @Override
    public int getItemCount() {
        return eventos.size();
    }

    /** Atualiza a lista de eventos com novos dados */
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
        notifyDataSetChanged();
    }

    /** ViewHolder que representa cada item na RecyclerView */
    static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView textNomeEvento, dataEvento, textCidade, textLogradouro, textNumero;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNomeEvento = itemView.findViewById(R.id.textNomeEvento);
            dataEvento = itemView.findViewById(R.id.textDataEvento);
            textCidade = itemView.findViewById(R.id.textCidade);
            textLogradouro = itemView.findViewById(R.id.textLogradouro);
            textNumero = itemView.findViewById(R.id.textNumero);
        }
    }
}
