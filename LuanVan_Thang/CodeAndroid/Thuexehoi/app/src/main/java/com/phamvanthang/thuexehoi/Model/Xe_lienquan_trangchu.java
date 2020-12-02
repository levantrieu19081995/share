package com.phamvanthang.thuexehoi.Model;

public class Xe_lienquan_trangchu {
    String img;
    String tenxe;
    String giaxe;
    String diachi;
    String ma_xe;
    String ma_sx;


    public Xe_lienquan_trangchu(String img, String tenxe, String giaxe, String diachi, String ma_xe, String ma_sx) {
        this.img = img;
        this.tenxe = tenxe;
        this.giaxe = giaxe;
        this.diachi = diachi;
        this.ma_xe = ma_xe;
        this.ma_sx = ma_sx;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTenxe() {
        return tenxe;
    }

    public void setTenxe(String tenxe) {
        this.tenxe = tenxe;
    }

    public String getGiaxe() {
        return giaxe;
    }

    public void setGiaxe(String giaxe) {
        this.giaxe = giaxe;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getMa_xe() {
        return ma_xe;
    }

    public void setMa_xe(String ma_xe) {
        this.ma_xe = ma_xe;
    }

    public String getMa_sx() {
        return ma_sx;
    }

    public void setMa_sx(String ma_sx) {
        this.ma_sx = ma_sx;
    }
}
