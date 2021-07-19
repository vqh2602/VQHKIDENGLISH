package com.example.vqhkidenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vqhkidenglish.exten.Windy_Activity;

public class About_Activity extends AppCompatActivity implements View.OnClickListener {

    TextView textView_phienban, textView_trogiup, textView_chinhsach, textView_web, textView_dieukhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textView_phienban = findViewById(R.id.textView_phienban);
        textView_trogiup = findViewById(R.id.textView_trogiup);
        textView_chinhsach = findViewById(R.id.textView_chinhsach);
        textView_web = findViewById(R.id.textView_web);
        textView_dieukhoan = findViewById(R.id.textView_dieukhoan);

        // full màn hình
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        textView_phienban.setText(getResources().getString(R.string.phienban) + versionName);
//    Log.d("LOG11",getResources().getString(R.string.phienban) + versionName);

        textView_trogiup.setOnClickListener(this);
        textView_chinhsach.setOnClickListener(this);
        textView_web.setOnClickListener(this);
        textView_dieukhoan.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textView_trogiup:
//
//                Toast.makeText(HomeActivity.this, "facebook", Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse("https://www.facebook.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
//                GoToURL("https://www.facebook.com/");
                break;

            case R.id.textView_chinhsach:
                Intent intent1 = new Intent(About_Activity.this, Windy_Activity.class);
                intent1.putExtra("idexten", "https://chinhsachbaomatvqhapp.blogspot.com/");
                startActivity(intent1);
                break;

            case R.id.textView_dieukhoan:
                Intent intent2 = new Intent(About_Activity.this, Windy_Activity.class);
                intent2.putExtra("idexten", "https://dieukhoanvqhapps.blogspot.com/");
                startActivity(intent2);
                break;

            case R.id.textView_web:
                Uri uri2 = Uri.parse("https://vqhapps.blogspot.com/");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent3);
        }
    }
}
