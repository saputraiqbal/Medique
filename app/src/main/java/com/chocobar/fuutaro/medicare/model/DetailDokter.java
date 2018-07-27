package com.chocobar.fuutaro.medicare.model;

public class DetailDokter {
    private String nama;
    private String profileDetail;
    private String imgBase64;

    public DetailDokter() {
    }

    public DetailDokter(String nama, String profileDetail, String imgBase64) {
        this.nama = nama;
        this.profileDetail = profileDetail;
        this.imgBase64 = imgBase64;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getProfileDetail() {
        return profileDetail;
    }

    public void setProfileDetail(String profileDetail) {
        this.profileDetail = profileDetail;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }
}
