package com.chocobar.fuutaro.medicare.model;

public class Spesialis {
    private int idSpesialis;
    private String spesialis;

    public Spesialis() {
    }

    public Spesialis(int idSpesialis, String spesialis) {
        this.idSpesialis = idSpesialis;
        this.spesialis = spesialis;
    }

    public int getIdSpesialis() {
        return idSpesialis;
    }

    public void setIdSpesialis(int idSpesialis) {
        this.idSpesialis = idSpesialis;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }
}
