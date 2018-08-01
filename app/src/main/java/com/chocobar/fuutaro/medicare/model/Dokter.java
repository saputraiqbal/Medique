package com.chocobar.fuutaro.medicare.model;

/**
 * Created by USER on 7/10/2018.
 */

public class Dokter {

    private String nama;
    private String noTelp;
    private String alamat;
    private String kota;
    private String provinsi;
    private String spesialis;
    private String img;
    private String idDokter;

    public Dokter(){}

    public Dokter(String nama, String noTelp, String alamat, String kota, String provinsi, String spesialis, String img, String idDokter){
        this.nama = nama;
        this.noTelp = noTelp;
        this.alamat = alamat;
        this.kota = kota;
        this.provinsi = provinsi;
        this.spesialis = spesialis;
        this.img = img;
        this.idDokter = idDokter;
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
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

    public String getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
