package com.chocobar.fuutaro.medicare.model;

public class DetailFaskes {
    private String namaFaskes;
    private String profileDetail;
    private String imgBase64;

    public DetailFaskes() {
    }

    public DetailFaskes(String namaFaskes, String profileDetail, String imgBase64) {
        this.namaFaskes = namaFaskes;
        this.profileDetail = profileDetail;
        this.imgBase64 = imgBase64;
    }

    public String getNamaFaskes() {
        return namaFaskes;
    }

    public void setNamaFaskes(String namaFaskes) {
        this.namaFaskes = namaFaskes;
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
