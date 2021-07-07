package com.example.vqhkidenglish.model;

public class Phatam {

    String name,vi;
    int imageUrl;

    public Phatam(String name, String vi, int imageUrl) {
        this.name = name;
        this.vi = vi;
        this.imageUrl = imageUrl;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
