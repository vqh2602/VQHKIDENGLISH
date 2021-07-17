package com.example.vqhkidenglish.model;

public class Vip {
    String day,gia;
    int imageUrl,imageicon;

    public Vip(String day, String gia, int imageUrl, int imageicon) {
        this.day = day;
        this.gia = gia;
        this.imageUrl = imageUrl;
        this.imageicon = imageicon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }


    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageicon() {
        return imageicon;
    }

    public void setImageicon(int imageicon) {
        this.imageicon = imageicon;
    }
}
