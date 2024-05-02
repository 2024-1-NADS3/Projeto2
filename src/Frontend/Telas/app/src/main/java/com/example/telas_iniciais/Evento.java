package com.example.telas_iniciais;

import java.io.Serializable;

public class Evento implements Serializable {

    private String evento;

    private String data;

    private String cidade;

    private String logradouro;

    private String Numero;

    private String id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public Evento(String evento, String data, String cidade, String logradouro, String numero) {
        this.evento = evento;
        this.data = data;
        this.cidade = cidade;
        this.logradouro = logradouro;
        Numero = numero;
    }
}
