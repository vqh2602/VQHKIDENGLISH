package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vqhkidenglish.data.abc_data;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Phatam_Activity extends AppCompatActivity {
    //       ca-app-pub-5964552069889646/1781850064
    private static  String AD_UNIT_ID ="";

    private DatabaseReference mDatabase;
    ArrayList<abc_data> listabc_data ;
    ImageView imageView_url_abc;
    TextView textView_abc,textView_abc_vi;
    Button button_next,button_prev;
    ImageButton imageButton_voice;
    TextToSpeech textToSpeech;
//    ProgressBar progressBar;
    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    Activity mActivity;
    LottieAnimationView animationView;
    private InterstitialAd mInterstitialAd;
MediaPlayer mediaPlayer;

    int check =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phatam);
        anhxa();
        AD_UNIT_ID = getString(R.string.Trunggian);
        loadad();

        Intent intent = getIntent();

        //        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listabc_data = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference(intent.getStringExtra("key"));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    abc_data abcData = postSnapshot.getValue(abc_data.class);
//                    Log.d("Data_abc_string", abcData.getUrl());
                    listabc_data.add(abcData);
                    Log.d("Data_abc_list", String.valueOf(listabc_data.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("Data", "loadPost:onCancelled", error.toException());
            }
        });

//        db.collection("ABC")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
////                                Log.d("Data_abc", document.getId() + " => " + document.getData());
////                                listabc_data.add(document.getData().values())
////                                xulichuoi(document.getData().values().toString());
//
//                            }
//                        } else {
//                            Log.w("Data_abc", "Error getting documents.", task.getException());
//                        }
//                    }
//                });

                hide_view();
//                setvoice();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("Data_abc_list", String.valueOf(listabc_data.size()));


                progressBar.setMax(listabc_data.size()-1);
                if(listabc_data.size() != 0){
//                    progressBar.setVisibility(View.GONE);
                    animationView.setVisibility(View.GONE);
                    show_view();
                    setdulieu();
                    showad();
                }
                else {
//                    progressBar.setVisibility(View.VISIBLE);
                    animationView.setVisibility(View.VISIBLE);
                    thongbao();
                    hide_view();
                }

            }
        },5500);



    }



private void  anhxa(){
   imageView_url_abc=findViewById(R.id.imageView_url_abc);
    textView_abc_vi = findViewById(R.id.textView_abc_vi);
     textView_abc = findViewById(R.id.textView_abc);
   button_next = findViewById(R.id.button_next);
   button_prev = findViewById(R.id.button_prev);
    imageButton_voice = findViewById(R.id.imageButton_voice);
    progressBar = findViewById(R.id.progressBar);
    animationView = findViewById(R.id.animationView);
}


    private void setdulieu(){
        int x = listabc_data.size();
        progressBar.setProgress(check);

        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(0).getUrl());
        textView_abc.setText(listabc_data.get(0).getName());
        textView_abc_vi.setText(listabc_data.get(0).getVi());
//        textToSpeech.setSpeechRate(0.5f);
//        textToSpeech.speak(listabc_data.get(0).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
        playvoice(listabc_data.get(0).getVoice());

        next(x);
        prev(x);
    }

//voice
    private void playvoice(String s){

        String audioUrl = "https://docs.google.com/uc?export=open&id=" + s;
        // initializing media player
        mediaPlayer = new MediaPlayer();
        Resources res = getResources();
        int sound = res.getIdentifier(s, "raw", getPackageName());

        //Play music
        mediaPlayer = MediaPlayer.create(getApplicationContext(), sound);

        mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(0.7f));
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
//
//        // below line is use to set the audio
//        // stream type for our media player.
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//        // below line is use to set our
//        // url to our media player.
//        try {
//            mediaPlayer.setDataSource(audioUrl);
//            // below line is use to prepare
//            // and start our media player.
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        imageButton_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                textToSpeech.setSpeechRate(0.5f);
//                textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);


//                // initializing media player
//                mediaPlayer = new MediaPlayer();
//
//                // below line is use to set the audio
//                // stream type for our media player.
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                // below line is use to set our
//                // url to our media player.
//                try {
//                    mediaPlayer.setDataSource(audioUrl);
//                    // below line is use to prepare
//                    // and start our media player.
//                    mediaPlayer.prepare();
//                    mediaPlayer.start();
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
    }
    // next
    private void next(int x){
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(check < x-1){
                    check++;
                    setview(check);


                }
                else {
                    check = 0;
                    setview(check);
                }

            }
        });
    }
    private void prev(int x){
        button_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check > 0 && check < x){
                    check--;
                    setview(check);
                }
                else {
                    check = x-1;
                    setview(check);
                }

            }
        });
    }
    //set view
    private void setview(int i){
        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(i).getUrl());
        textView_abc.setText(listabc_data.get(i).getName());
        textView_abc_vi.setText(listabc_data.get(i).getVi());
//        textToSpeech.setSpeechRate((float) 0.2);
//        textToSpeech.speak(listabc_data.get(i).getName(),TextToSpeech.QUEUE_FLUSH,null,null);

        playvoice(listabc_data.get(i).getVoice());
        progressBar.setProgress(check);

    }
    // get url image
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

//    //set voice
//    private void setvoice(){
//        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int i) {
//                if(i == TextToSpeech.SUCCESS){
//                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
//                }
//            }
//        });
//
//    }

    // th??ng b??o
    private void thongbao(){
        //T???o ?????i t?????ng
        mActivity = Phatam_Activity.this;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thi???t l???p ti??u ?????
        b.setTitle("Kh??ng th??? t???i d??? li???u");
        b.setMessage("T???c ????? m???ng kh??ng ???n ?????nh, b???n c?? mu???n t???i l???i d??? li???u?");
// N??t Ok
        b.setCancelable(false);
        b.setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActivity.recreate();
//                finish();
            }
        });
//N??t Cancel
        b.setNegativeButton("Kh??ng ?????ng ??", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                dialog.cancel();
                finish();
            }
        });
//T???o dialog
        AlertDialog al = b.create();
//Hi???n th???
//Hi???n th???
        if(!isFinishing())
        {
            al.show();
        }
    }
    //show
    private void hide_view(){
        imageView_url_abc.setVisibility(View.GONE);
        textView_abc_vi.setVisibility(View.GONE);
        textView_abc.setVisibility(View.GONE);
        button_next.setVisibility(View.GONE);
        button_prev.setVisibility(View.GONE);
        imageButton_voice.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

    }
    private void show_view(){
        imageView_url_abc.setVisibility(View.VISIBLE);
        textView_abc_vi.setVisibility(View.VISIBLE);
        textView_abc.setVisibility(View.VISIBLE);
        button_next.setVisibility(View.VISIBLE);
        button_prev.setVisibility(View.VISIBLE);
        imageButton_voice.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);

    }

    //load qu???ng c??o
    private void loadad(){
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

//        ca-app-pub-5964552069889646/1538183771
        InterstitialAd.load(this,AD_UNIT_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG11", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG11", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG11", "The ad was shown.");
                            }
                        });
                        Log.i("ad11", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("ad11", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });




    }
    // hi???n ad
    private void showad(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Phatam_Activity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }
}