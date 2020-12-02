package com.phamvanthang.thuexehoi.Model;

public class Xe_dathue {
    String img;
    String tenxe;
    String giaxe;


    public Xe_dathue(String img, String tenxe, String giaxe) {
        this.img = img;
        this.tenxe = tenxe;
        this.giaxe = giaxe;
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
}
