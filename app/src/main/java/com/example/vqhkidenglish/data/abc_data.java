package com.example.vqhkidenglish.data;

public class abc_data {
    String name;
    String url;
    String vi;




    public abc_data(String name, String url, String vi) {
        this.name = name;
        this.url = url;
        this.vi = vi;
    }

    public abc_data() {
        name="";
        url="";
        vi="";
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

    public void setUrl(String url) {
        this.url = url;
    }
    public String getName() {
        return name;
    }


    public String getUrl() {
        return url;
    }
}
