package com.chocobar.fuutaro.medicare.model;

public class Klinik {

    private int idKlinik;
    private String namaKlinik;

    public Klinik(){}

    public Klinik(int idKlinik, String namaKlinik){
        this.idKlinik = idKlinik;
        this.namaKlinik = namaKlinik;
    }

    public int getIdKlinik() {
        return idKlinik;
    }

    public void setIdKlinik(int idKlinik) {
        this.idKlinik = idKlinik;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

}
