package com.chocobar.fuutaro.medicare.model;

public class Faskes_ListJamKes {
    private String idJamKes;
    private String jenis;

    public Faskes_ListJamKes() {
    }

    public Faskes_ListJamKes(String idJamKes, String jenis) {
        this.idJamKes = idJamKes;
        this.jenis = jenis;
    }

    public String getIdJamKes() {
        return idJamKes;
    }

    public void setIdJamKes(String idJamKes) {
        this.idJamKes = idJamKes;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
