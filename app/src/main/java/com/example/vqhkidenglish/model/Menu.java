package com.example.vqhkidenglish.model;

public class Menu {
    String name,vi;
    int imageUrl,imageicon;

    public Menu(String name, String vi, int imageUrl, int imageicon) {
        this.name = name;
        this.vi = vi;
        this.imageUrl = imageUrl;
        this.imageicon = imageicon;
    }

    public int getImageicon() {
        return imageicon;
    }

    public void setImageicon(int imageicon) {
        this.imageicon = imageicon;
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
