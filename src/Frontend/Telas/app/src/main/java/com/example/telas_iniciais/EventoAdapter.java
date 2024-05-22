package com.example.telas_iniciais;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private List<Evento> eventos;

    public EventoAdapter(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);
        holder.textNomeEvento.setText(evento.getEvento());
        holder.dataEvento.setText("Data: " + evento.getData());
        holder.textCidade.setText("Cidade: " + evento.getCidade());
        holder.textLogradouro.setText("Logradouro: " + evento.getLogradouro());
        holder.textNumero.setText("Número: " + evento.getNumero());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
        notifyDataSetChanged();
    }

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
