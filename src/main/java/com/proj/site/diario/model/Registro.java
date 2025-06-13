package com.proj.site.diario.model;

public class Registro {
    private int id;
    private String titulo;
    private String data;
    private String conteudo;

    public Registro(int id, String titulo, String data, String conteudo) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + "\n" +
                "Data: " + data + "\n" +
                "Conteúdo: " + conteudo + "\n";
    }
}
