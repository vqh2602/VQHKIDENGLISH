package com.example.vqhkidenglish.exten;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.vqhkidenglish.R;
public class Windy_Activity extends AppCompatActivity {
    WebView webview_map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windy);


        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading Data...");
//        progressDialog.setCancelable(false);

        webview_map = (WebView) findViewById(R.id.webview_map);

        Intent intent = getIntent();
        String idexten = intent.getStringExtra("idexten");
        //        WebSettings webSettings = webview_map.getSettings();

        webview_map.requestFocus();
        webview_map.getSettings().setLightTouchEnabled(true);
        webview_map.getSettings().setJavaScriptEnabled(true);
        webview_map.getSettings().setGeolocationEnabled(true);
        webview_map.setSoundEffectsEnabled(true);
        webview_map.getSettings().setAppCacheEnabled(true);
        webview_map.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview_map.loadUrl(idexten);

        webview_map.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.setMessage("Loading Data: "+progress +" %");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();

                }

            }
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });

        webview_map.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webview_map.destroy();
    }

}