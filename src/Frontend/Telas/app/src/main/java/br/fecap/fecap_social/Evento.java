package br.fecap.fecap_social;

import java.io.Serializable;

/**
 * Classe que representa um Evento
 */
public class Evento implements Serializable {

    private String evento;
    private String data;
    private String cidade;
    private String logradouro;
    private String Numero;

    /**
     * Obtém o nome do evento.
     * @return o nome do evento.
     */
    public String getEvento() {
        return evento;
    }

    /**
     * Define o nome do evento.
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * Obtém a data do evento.
     * @return a data do evento.
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data do evento.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obtém a cidade do evento.
     * @return a cidade do evento.
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Define a cidade do evento.
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Obtém o logradouro do evento.
     * @return o logradouro do evento.
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Define o logradouro do evento.
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * Obtém o número do local do evento.
     * @return o número do local do evento.
     */
    public String getNumero() {
        return Numero;
    }

    /**
     * Define o número do local do evento.
     */
    public void setNumero(String numero) {
        Numero = numero;
    }

    /**
     * Construtor para criar um objeto Evento com os detalhes do evento
     */
    public Evento(String evento, String data, String cidade, String logradouro, String numero) {
        this.evento = evento;
        this.data = data;
        this.cidade = cidade;
        this.logradouro = logradouro;
        Numero = numero;
    }
}
