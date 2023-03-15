package com.example.konachprojrct.beans;

public class Produit {
    private int id;
    private String libelle;
    private Double prix;
    private int idCategory;
    public Produit(){}
    public Produit(String libelle, Double prix, int idCategory) {
        this.libelle = libelle;
        this.prix = prix;
        this.idCategory = idCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public int getIdCategory() {
        return idCategory;
    }
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", prix=" + prix +
                ", idCategory=" + idCategory +
                '}';
    }


}
