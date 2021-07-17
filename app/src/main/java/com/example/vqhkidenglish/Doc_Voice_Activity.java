package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vqhkidenglish.data.User;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Doc_Voice_Activity extends AppCompatActivity {
    //        ca-app-pub-5964552069889646/1781850064
    private static String AD_UNIT_ID = "";

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabasecheck;
    ArrayList<abc_data> listabc_data ;
    ImageView imageView_url_abc;
    TextView textView_abc,textView_abc_vi;
    Button button_next,button_prev;
    ImageButton imageButton_voice;
    TextToSpeech textToSpeech;
    //    ProgressBar progressBar;
    private int CurrentProgress = 0;
    private ProgressBar progressBar;

    Switch switch_hidetext;
    Activity mActivity;
    LottieAnimationView animationView;
    private InterstitialAd mInterstitialAd;
    MediaPlayer mediaPlayer;
RatingBar ratingBar;
    SpeechRecognizer speechRecognizer;
    LottieAnimationView animationViewvoice;

    String check ="";
    int sttcauhoi=-1;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_voice);
        AD_UNIT_ID = getString(R.string.Trunggian);
        //check dang nhap
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        anhxa();
        loadad();

        Intent intent = getIntent();

        //        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listabc_data = new ArrayList<>();
//        intent.getStringExtra("key")
        mDatabase = FirebaseDatabase.getInstance().getReference(intent.getStringExtra("key"));
        mDatabasecheck = FirebaseDatabase.getInstance().getReference("users");

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

        mDatabasecheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
//                    Log.d("Data_abc_string", abcData.getUrl());
                    if(currentUser.getUid().equals(user.id)){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                                    Calendar c = Calendar.getInstance();
//                                    c.setTime(sdf.parse(dt));
//                                    c.add(Calendar.DATE, 1);  // number of days to add
//                                    dt = sdf.format(c.getTime());
                        DateFormat dform = new SimpleDateFormat("dd/MM/yyyy");
                        Date obj = new Date();
                        String d1 = dform.format(obj);

                        try {
                            Date date1 = sdf.parse(d1);
                            Date date2 = sdf.parse(user.date);
                            if(date2.compareTo(date1) < 0){
                            thongbao2();
                            }
                            else {

                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                        }
                }


            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("Data", "loadPost:onCancelled", error.toException());
            }
        });

        hide_view();
//                setvoice();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("Data_abc_list", String.valueOf(listabc_data.size()));


                progressBar.setMax(listabc_data.size()-1);
                if(listabc_data.size() != 0){
                    Collections.shuffle(listabc_data);
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

        switch_hidetext.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        if(switch_hidetext.isChecked() ) {
                            imageButton_voice.setVisibility(View.VISIBLE);

                        }
                        else {
                            imageButton_voice.setVisibility(View.GONE);

                        }
                    }
                });
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
        animationViewvoice = findViewById(R.id.animationViewvoice);
        switch_hidetext = findViewById(R.id.switch_hidetext);
        ratingBar = findViewById(R.id.ratingBar);
    }


    private void setdulieu(){
        int x = listabc_data.size();
//        progressBar.setProgress(check);

//        new Doc_Voice_Activity.DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(0).getUrl());
//        textView_abc.setText(listabc_data.get(0).getName());
//        textView_abc_vi.setText(listabc_data.get(0).getVi());
////        textToSpeech.setSpeechRate(0.5f);
////        textToSpeech.speak(listabc_data.get(0).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
//        playvoice(listabc_data.get(0).getVoice());
        if(sttcauhoi <x-1){
            sttcauhoi++;
            CurrentProgress++;
        }
        else {
            sttcauhoi =0;
            CurrentProgress=0;
        }
        progressBar.setProgress(sttcauhoi);
        setview(sttcauhoi);
//        next(x);
//        prev(x);
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

//        mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(0.7f));
//        mediaPlayer.seekTo(0);
//        mediaPlayer.start();
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
//    private void next(int x){
//        button_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(check < x-1){
//                    check++;
//                    setview(check);
//
//
//                }
//                else {
//                    check = 0;
//                    setview(check);
//                }
//
//            }
//        });
//    }
//    private void prev(int x){
//        button_prev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(check > 0 && check < x){
//                    check--;
//                    setview(check);
//                }
//                else {
//                    check = x-1;
//                    setview(check);
//                }
//
//            }
//        });
//    }
    //set view
    private void setview(int i){
        new Doc_Voice_Activity.DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(i).getUrl());
        textView_abc.setText(listabc_data.get(i).getName());
        textView_abc_vi.setText(listabc_data.get(i).getVi());
//        textToSpeech.setSpeechRate((float) 0.2);
//        textToSpeech.speak(listabc_data.get(i).getName(),TextToSpeech.QUEUE_FLUSH,null,null);

        playvoice(listabc_data.get(i).getVoice());
        progressBar.setProgress(sttcauhoi);
        check = listabc_data.get(i).getName();

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



    // thông báo
    private void thongbao(){
        //Tạo đối tượng
        mActivity = Doc_Voice_Activity.this;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Không thể tải dữ liệu");
        b.setMessage("Tốc độ mạng không ổn định, bạn có muốn tải lại dữ liệu?");
// Nút Ok
        b.setCancelable(false);
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActivity.recreate();
//                finish();
            }
        });
//Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                dialog.cancel();
                finish();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();
//Hiển thị
        //Hiển thị
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
        imageButton_voice.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        animationViewvoice.setVisibility(View.GONE);
        switch_hidetext.setVisibility(View.GONE);
        ratingBar.setVisibility(View.GONE);

    }
    private void show_view(){
        imageView_url_abc.setVisibility(View.VISIBLE);
        textView_abc_vi.setVisibility(View.VISIBLE);
        textView_abc.setVisibility(View.VISIBLE);
//        imageButton_voice.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        animationViewvoice.setVisibility(View.VISIBLE);
        switch_hidetext.setVisibility(View.VISIBLE);
        ratingBar.setVisibility(View.VISIBLE);

    }

    //load quảng cáo
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
    // hiện ad
    private void showad(){
        if (mInterstitialAd != null) {
            mInterstitialAd.show(Doc_Voice_Activity.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
    }



    public void getSpeech(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent,1);
        }else {
            Toast.makeText(this, "Your Device Not Supported For speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                    tvResult.setText(result.get(0));
                }
                break;
        }
    }

    public void ghiam(View view){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(this);
        final Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});
//       intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        String languagePref = Locale.ENGLISH.toString();//or, whatever iso code...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);
        speechRecognizer.startListening(intent);

        animationViewvoice.playAnimation();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                speechRecognizer.stopListening();
//                animationViewvoice.pauseAnimation();
//                animationViewvoice.setFrame(113);
//            }
//        },5000);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {
                Log.d("voice1", String.valueOf(i));
                if(i ==7){
                    Toast.makeText(Doc_Voice_Activity.this,"Phát âm chưa chuẩn,phát âm lại",Toast.LENGTH_SHORT).show();
                    StyleableToast.Builder st = new StyleableToast.Builder(Doc_Voice_Activity.this)
                            .text("Phát âm chưa chuẩn,phát âm lại")
                            .backgroundColor(Color.parseColor("#f04049"))
                            .textColor(Color.parseColor("#ffffff"))
                            .iconStart(R.drawable.sweet_banana_480px)
                            .build();
                    st.show();
                    speechRecognizer.stopListening();
                                    animationViewvoice.pauseAnimation();
                animationViewvoice.setFrame(113);
                }


            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
//                tvResult.setText(data.get(0));
//                if(data.size())
                Float[] myNum = {0.5f,1.0f, 1.5f, 2.0f,2.5f,3.0f,3.5f,4.0f,4.5f,5.0f};

                animationViewvoice.pauseAnimation();
                animationViewvoice.setFrame(113);
                speechRecognizer.stopListening();

                Log.d("voice1",data.get(0) + "| "+check);
                if(data.get(0).equalsIgnoreCase(check)){
                    Random r = new Random();
                    int rd = r.nextInt(5) + 5;
                    ratingBar.setRating(myNum[rd]);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setdulieu();
                            ratingBar.setRating(0);
                        }
                    },2500);

                }
                else {
                    Random r = new Random();
                    int rd = r.nextInt(5);
                    ratingBar.setRating(myNum[rd]);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setdulieu();
                            ratingBar.setRating(0);
                        }
                    },2500);
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0] ==PackageManager.PERMISSION_GRANTED){

            }
            else {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    // thông báo
    private void thongbao2(){
        //Tạo đối tượng
        mActivity = Doc_Voice_Activity.this;

        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Bạn chưa đăng kí dịch vụ");
        b.setMessage("Đăng kí dịch vụ miễn phí \n Đăng kí tại trang tài khoản");
// Nút Ok
        b.setCancelable(false);
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActivity.recreate();
                Intent intent = new Intent(Doc_Voice_Activity.this, Menu_Activity.class);
//        String xu1 = String.valueOf(xu);
//        intent.putExtra("userxu",xu1);
                startActivity(intent);
                overridePendingTransition(R.anim.in_left,R.anim.out_left);
                dialog.dismiss();
                finish();
            }
        });
//Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                finish();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();
//Hiển thị
        if(!isFinishing())
        {
            al.show();
        }

    }

}