package com.chocobar.fuutaro.medicare.model;

public class LoketPelayanan {
    private int idLoket;
    private String loket;

    public LoketPelayanan() {
    }

    public LoketPelayanan(int idLoket, String loket) {
        this.idLoket = idLoket;
        this.loket = loket;
    }

    public int getIdLoket() {
        return idLoket;
    }

    public void setIdLoket(int idLoket) {
        this.idLoket = idLoket;
    }

    public String getLoket() {
        return loket;
    }

    public void setLoket(String loket) {
        this.loket = loket;
    }
}
