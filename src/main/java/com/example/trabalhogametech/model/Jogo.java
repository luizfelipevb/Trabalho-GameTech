package com.example.trabalhogametech.model;

public class Jogo {

    // ID do jogo
    private int id;

    // Nome do jogo
    private String nome;

    // Gênero do jogo
    private String genero;

    // Plataforma do jogo
    private String plataforma;

    // Ano de lançamento
    private int ano;

    // Nota do jogo
    private double avaliacao;

    // Status do jogo
    private String status;


    // Construtor vazio
    public Jogo() {
    }


    // Construtor completo
    public Jogo(int id, String nome, String genero, String plataforma,
                int ano, double avaliacao, String status) {

        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.plataforma = plataforma;
        this.ano = ano;
        this.avaliacao = avaliacao;
        this.status = status;
    }


    // Getter do ID
    public int getId() {
        return id;
    }

    // Setter do ID
    public void setId(int id) {
        this.id = id;
    }


    // Getter do nome
    public String getNome() {
        return nome;
    }

    // Setter do nome
    public void setNome(String nome) {
        this.nome = nome;
    }


    // Getter do gênero
    public String getGenero() {
        return genero;
    }

    // Setter do gênero
    public void setGenero(String genero) {
        this.genero = genero;
    }


    // Getter da plataforma
    public String getPlataforma() {
        return plataforma;
    }

    // Setter da plataforma
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }


    // Getter do ano
    public int getAno() {
        return ano;
    }

    // Setter do ano
    public void setAno(int ano) {
        this.ano = ano;
    }


    // Getter da avaliação
    public double getAvaliacao() {
        return avaliacao;
    }

    // Setter da avaliação
    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }


    // Getter do status
    public String getStatus() {
        return status;
    }

    // Setter do status
    public void setStatus(String status) {
        this.status = status;
    }
}