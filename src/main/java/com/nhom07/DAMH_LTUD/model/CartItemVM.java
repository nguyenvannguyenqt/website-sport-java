package com.nhom07.DAMH_LTUD.model;

public class CartItemVM {
    private Long maSp;
    private String tenSp;
    private Double donGia;
    private String hinh;
    private Integer soLuong;

    public Double getTongTien() {
        return soLuong * donGia;
    }

    public CartItemVM() {
    }

    public CartItemVM(Long maSp, String tenSp, Double donGia, String hinh, Integer soLuong) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.donGia = donGia;
        this.hinh = hinh;
        this.soLuong = soLuong;
    }


    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Long getMaSp() {
        return maSp;
    }

    public void setMaSp(Long maSp) {
        this.maSp = maSp;
    }
}
