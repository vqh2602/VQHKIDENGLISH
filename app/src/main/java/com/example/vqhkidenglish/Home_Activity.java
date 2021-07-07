package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vqhkidenglish.data.abc_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Home_Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<abc_data> listabc_data ;
    ImageView imageView_url_abc;
    TextView textView_abc,textView_abc_vi;
    Button button_next,button_prev;
    ImageButton imageButton_voice;
    TextToSpeech textToSpeech;

    int check =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anhxa();

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

                setvoice();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("Data_abc_list", String.valueOf(listabc_data.size()));

                setdulieu();

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
}


    private void setdulieu(){
        int x = listabc_data.size();
        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(0).getUrl());
        textView_abc.setText(listabc_data.get(0).getName());
        textView_abc_vi.setText(listabc_data.get(0).getVi());
        textToSpeech.setSpeechRate(0.5f);
        textToSpeech.speak(listabc_data.get(0).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
        playvoice(listabc_data.get(0).getName());

        next(x);
        prev(x);
    }

//voice
    private void playvoice(String s){
        imageButton_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.setSpeechRate(0.5f);
                textToSpeech.speak(s,TextToSpeech.QUEUE_FLUSH,null,null);
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
        textToSpeech.setSpeechRate((float) 0.2);
        textToSpeech.speak(listabc_data.get(i).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
        playvoice(listabc_data.get(i).getName());

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

    //set voice
    private void setvoice(){
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

    }
}