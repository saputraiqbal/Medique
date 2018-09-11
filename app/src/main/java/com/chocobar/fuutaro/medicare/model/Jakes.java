package com.chocobar.fuutaro.medicare.model;

public class Jakes {
    private int idJakes;
    private String namaJakes;

    public Jakes(){}

    public Jakes(int idJakes, String namaJakes){
        this.idJakes = idJakes;
        this.namaJakes = namaJakes;
    }

    public int getIdJakes() {
        return idJakes;
    }

    public void setIdJakes(int idJakes) {
        this.idJakes = idJakes;
    }

    public String getNamaJakes() {
        return namaJakes;
    }

    public void setNamaJakes(String namaJakes) {
        this.namaJakes = namaJakes;
    }

}
