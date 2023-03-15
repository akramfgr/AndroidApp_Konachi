package com.example.konachprojrct.beans;


import java.util.Date;

public class Credit {
    private int id;
    private int idClient;
    private int idProduit;
    private int quantite;
    private String date;
    private String etat;
    private Double totale;

    public Credit(int idClient, int idProduit, int quantite, String date, String etat, Double totale) {
        this.idClient = idClient;
        this.idProduit = idProduit;
        this.quantite = quantite;
        this.date = date;
        this.etat = etat;
        this.totale = totale;
    }

    public Credit() {

    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getDate() {
        return date;
    }

    public String getEtat() {
        return etat;
    }

    public Double getTotale() {
        return totale;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setTotale(Double totale) {
        this.totale = totale;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", idClient=" + idClient +
                ", idProduit=" + idProduit +
                ", quantite=" + quantite +
                ", date=" + date +
                ", etat='" + etat + '\'' +
                ", totale=" + totale +
                '}';
    }
}

