package com.chocobar.fuutaro.medicare.model;

public class User {
    private String nama;
    private String NoKTP;
    private String tempatLahir;
    private String tanggalLahir;
    private String tglLahir;
    private String blnLahir;
    private String thnLahir;
    private String alamat;
    private String telp;
    private int idJamkes;
    private String jamkes;
    private String noJamkes;
    private String txtAvatar;

    public User() {
    }

    public User(String nama, String noKTP, String tempatLahir, String tanggalLahir, String tglLahir, String blnLahir, String thnLahir, String alamat, String telp, int idJamkes, String jamkes, String noJamkes, String txtAvatar) {
        this.nama = nama;
        NoKTP = noKTP;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.tglLahir = tglLahir;
        this.blnLahir = blnLahir;
        this.thnLahir = thnLahir;
        this.alamat = alamat;
        this.telp = telp;
        this.idJamkes = idJamkes;
        this.jamkes = jamkes;
        this.noJamkes = noJamkes;
        this.txtAvatar = txtAvatar;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getBlnLahir() {
        return blnLahir;
    }

    public void setBlnLahir(String blnLahir) {
        this.blnLahir = blnLahir;
    }

    public String getThnLahir() {
        return thnLahir;
    }

    public void setThnLahir(String thnLahir) {
        this.thnLahir = thnLahir;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKTP() {
        return NoKTP;
    }

    public void setNoKTP(String noKTP) {
        NoKTP = noKTP;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public int getIdJamkes() {
        return idJamkes;
    }

    public void setIdJamkes(int idJamkes) {
        this.idJamkes = idJamkes;
    }

    public String getJamkes() {
        return jamkes;
    }

    public void setJamkes(String jamkes) {
        this.jamkes = jamkes;
    }

    public String getNoJamkes() {
        return noJamkes;
    }

    public void setNoJamkes(String noJamkes) {
        this.noJamkes = noJamkes;
    }

    public String getTxtAvatar() {
        return txtAvatar;
    }

    public void setTxtAvatar(String txtAvatar) {
        this.txtAvatar = txtAvatar;
    }
}
