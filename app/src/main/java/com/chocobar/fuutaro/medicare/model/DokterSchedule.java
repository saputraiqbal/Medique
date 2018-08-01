package com.chocobar.fuutaro.medicare.model;

public class DokterSchedule {
    private String partner;
    private String jenisPelayanan;
    private String startAt;
    private String endAt;
    private int idPartner;
    private int idJenisPelayanan;
    private int idJadwal;
    private int kuota;
    private int jumlahAntrian;

    public DokterSchedule() {
    }

    public DokterSchedule(String partner, String jenisPelayanan, String startAt, String endAt, int idPartner, int idJenisPelayanan, int idJadwal, int kuota, int jumlahAntrian) {
        this.partner = partner;
        this.jenisPelayanan = jenisPelayanan;
        this.startAt = startAt;
        this.endAt = endAt;
        this.idPartner = idPartner;
        this.idJenisPelayanan = idJenisPelayanan;
        this.idJadwal = idJadwal;
        this.kuota = kuota;
        this.jumlahAntrian = jumlahAntrian;
    }

    public int getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(int idPartner) {
        this.idPartner = idPartner;
    }

    public int getIdJenisPelayanan() {
        return idJenisPelayanan;
    }

    public void setIdJenisPelayanan(int idJenisPelayanan) {
        this.idJenisPelayanan = idJenisPelayanan;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getJenisPelayanan() {
        return jenisPelayanan;
    }

    public void setJenisPelayanan(String jenisPelayanan) {
        this.jenisPelayanan = jenisPelayanan;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public int getKuota() {
        return kuota;
    }

    public void setKuota(int kuota) {
        this.kuota = kuota;
    }

    public int getJumlahAntrian() {
        return jumlahAntrian;
    }

    public void setJumlahAntrian(int jumlahAntrian) {
        this.jumlahAntrian = jumlahAntrian;
    }
}
