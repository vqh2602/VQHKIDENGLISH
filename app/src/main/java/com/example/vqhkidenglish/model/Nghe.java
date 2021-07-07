package com.example.vqhkidenglish.model;

public class Nghe {
    String name,vi;
    int imageUrl;

    public Nghe(String name, String vi, int imageUrl) {
        this.name = name;
        this.vi = vi;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
