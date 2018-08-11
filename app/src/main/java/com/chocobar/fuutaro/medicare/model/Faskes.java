package com.chocobar.fuutaro.medicare.model;

public class Faskes {
    private String id;
    private String namaFaskes;
    private String alamat;
    private String kota;
    private String provinsi;
    private String imgFaskes;

    public Faskes() {
    }

    public Faskes(String id, String namaFaskes, String alamat, String kota, String provinsi, String imgFaskes) {
        this.id = id;
        this.namaFaskes = namaFaskes;
        this.alamat = alamat;
        this.kota = kota;
        this.provinsi = provinsi;
        this.imgFaskes = imgFaskes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaFaskes() {
        return namaFaskes;
    }

    public void setNamaFaskes(String namaFaskes) {
        this.namaFaskes = namaFaskes;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getImgFaskes() {
        return imgFaskes;
    }

    public void setImgFaskes(String imgFaskes) {
        this.imgFaskes = imgFaskes;
    }
}
