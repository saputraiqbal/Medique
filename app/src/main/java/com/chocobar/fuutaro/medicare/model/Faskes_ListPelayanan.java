package com.chocobar.fuutaro.medicare.model;

public class Faskes_ListPelayanan {
    private String idPelayanan;
    private String jenis;

    public Faskes_ListPelayanan() {
    }

    public Faskes_ListPelayanan(String idPelayanan, String jenis) {
        this.idPelayanan = idPelayanan;
        this.jenis = jenis;
    }

    public String getIdPelayanan() {
        return idPelayanan;
    }

    public void setIdPelayanan(String idPelayanan) {
        this.idPelayanan = idPelayanan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
