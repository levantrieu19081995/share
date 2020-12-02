package com.phamvanthang.thuexehoi.Model;

public class Xe_khuyenmai {
    String img;
    String loaixe;
    String giaxe;
    String phantram;
    String ma_xe;
    String ma_sx;

    public Xe_khuyenmai(String img, String loaixe, String giaxe, String phantram, String ma_xe, String ma_sx) {
        this.img = img;
        this.loaixe = loaixe;
        this.giaxe = giaxe;
        this.phantram = phantram;
        this.ma_xe = ma_xe;
        this.ma_sx = ma_sx;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLoaixe() {
        return loaixe;
    }

    public void setLoaixe(String loaixe) {
        this.loaixe = loaixe;
    }

    public String getGiaxe() {
        return giaxe;
    }

    public void setGiaxe(String giaxe) {
        this.giaxe = giaxe;
    }

    public String getPhantram() {
        return phantram;
    }

    public void setPhantram(String phantram) {
        this.phantram = phantram;
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
