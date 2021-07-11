package com.example.vqhkidenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class Splash_Screen_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(5500);  //Delay of 10 seconds
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(Splash_Screen_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
                }
            }
        };
        welcomeThread.start();
    }
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(Splash_Screen_Activity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//
//        },5500);
//    }
}