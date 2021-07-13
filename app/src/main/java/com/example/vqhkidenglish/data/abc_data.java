package com.example.vqhkidenglish.data;

public class abc_data {
    String name;
    String url;
    String vi;
    String voice;




    public abc_data(String name, String url, String vi, String voice) {
        this.name = name;
        this.url = url;
        this.vi = vi;
        this.voice = voice;
    }

    public abc_data() {
        name="";
        url="";
        vi="";
        voice ="";
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getVi() {
        return vi;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
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
