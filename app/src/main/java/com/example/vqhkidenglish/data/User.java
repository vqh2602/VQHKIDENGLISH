package com.example.vqhkidenglish.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public int xu;
    public String id;
    public String date;

    public User(String username, String email, int xu, String id, String date) {
        this.username = username;
        this.email = email;
        this.xu = xu;
        this.id = id;
        this.date = date;
    }

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


}
