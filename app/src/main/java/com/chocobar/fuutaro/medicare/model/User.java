package com.chocobar.fuutaro.medicare.model;

public class User {
    private String nama;
    private String NoKTP;
    private String tempatLahir;
    private String tanggalLahir;
    private String tgl_Lahir;
    private String bln_Lahir;
    private String thn_Lahir;
    private String alamat;
    private String telp;
    private int idJamkes;
    private String jamkes;
    private String noJamkes;
    private String txtAvatar;

    public User() {
    }

    public User(String nama, String noKTP, String tempatLahir, String tanggalLahir, String tgl_Lahir, String bln_Lahir, String thn_Lahir, String alamat, String telp, int idJamkes, String jamkes, String noJamkes, String txtAvatar) {
        this.nama = nama;
        NoKTP = noKTP;
        this.tempatLahir = tempatLahir;
        this.tanggalLahir = tanggalLahir;
        this.tgl_Lahir = tgl_Lahir;
        this.bln_Lahir = bln_Lahir;
        this.thn_Lahir = thn_Lahir;
        this.alamat = alamat;
        this.telp = telp;
        this.idJamkes = idJamkes;
        this.jamkes = jamkes;
        this.noJamkes = noJamkes;
        this.txtAvatar = txtAvatar;
    }

    public String getTgl_Lahir() {
        return tgl_Lahir;
    }

    public void setTgl_Lahir(String tgl_Lahir) {
        this.tgl_Lahir = tgl_Lahir;
    }

    public String getBln_Lahir() {
        return bln_Lahir;
    }

    public void setBln_Lahir(String bln_Lahir) {
        this.bln_Lahir = bln_Lahir;
    }

    public String getThn_Lahir() {
        return thn_Lahir;
    }

    public void setThn_Lahir(String thn_Lahir) {
        this.thn_Lahir = thn_Lahir;
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
