package com.example.vqhkidenglish;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.Random;

public class Listen_Activity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    ArrayList<abc_data> listabc_data ;
    ImageView imageView_url_aw1,imageView_url_aw2,imageView_url_aw3,imageView_url_aw4;
    TextView textView_abc,textView_abc_vi;
    ImageButton imageButton_voice;
    TextToSpeech textToSpeech;
//    ProgressBar progressBar;
    Activity mActivity;
LottieAnimationView animationView;

    int checkdapan =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);
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
//                    Log.d("Data_abc_list", String.valueOf(listabc_data.size()));


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
                setvoice();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("Data_abc_list", String.valueOf(listabc_data.size()));

                setdulieu();

                if(listabc_data.size() != 0){
//                    progressBar.setVisibility(View.GONE);
                    animationView.setVisibility(View.GONE);
                    show_view();
                }
                else {
//                    progressBar.setVisibility(View.VISIBLE);
                    animationView.setVisibility(View.VISIBLE);
//                    Toast.makeText()
                    thongbao();
                    hide_view();
                }


            }
        },5500);



    }



private void  anhxa(){
   imageView_url_aw1=findViewById(R.id.imageView_url_aw1);
    imageView_url_aw2=findViewById(R.id.imageView_url_aw2);
    imageView_url_aw3=findViewById(R.id.imageView_url_aw3);
    imageView_url_aw4=findViewById(R.id.imageView_url_aw4);

    textView_abc_vi = findViewById(R.id.textView_abc_vi);
     textView_abc = findViewById(R.id.textView_abc);
    imageButton_voice = findViewById(R.id.imageButton_voice);

    animationView = findViewById(R.id.animationView);
//    progressBar = findViewById(R.id.progressBar);

    imageView_url_aw1.setOnClickListener(this);
    imageView_url_aw2.setOnClickListener(this);
    imageView_url_aw3.setOnClickListener(this);
    imageView_url_aw4.setOnClickListener(this);

}


    private void setdulieu(){
        int x = listabc_data.size();

        Random r = new Random();
        int random =  r.nextInt(x);

        Random aws = new Random();
        int randomaws = aws.nextInt(4);

        randomAwsImage(randomaws, random);


//        next(x);
//        prev(x);
    }
    //random image aws
    private void randomAwsImage(int x,int aws) {
        switch (x){
            case 0:
                new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw1)).execute(listabc_data.get(aws).getUrl());
                initaws(aws,0);
                checkdapan=1;
                break;
            case 1:
                new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw2)).execute(listabc_data.get(aws).getUrl());
                initaws(aws,1);
                checkdapan=2;
                break;
            case 2:
                new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw3)).execute(listabc_data.get(aws).getUrl());
                initaws(aws,2);
                checkdapan=3;
                break;
            case 3:
                new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw4)).execute(listabc_data.get(aws).getUrl());
                initaws(aws,3);
                checkdapan=4;
                break;
        }
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
//    private void next(int x){
//        button_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
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
        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_abc)).execute(listabc_data.get(i).getUrl());
        textView_abc.setText(listabc_data.get(i).getName());
        textView_abc_vi.setText(listabc_data.get(i).getVi());
        textToSpeech.setSpeechRate((float) 0.2);
        textToSpeech.speak(listabc_data.get(i).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
        playvoice(listabc_data.get(i).getName());

    }

    //khởi tạo câu hỏi
    private void initaws(int aws, int x){
        textView_abc.setText(listabc_data.get(aws).getName());
        textView_abc_vi.setText(listabc_data.get(aws).getVi());
        textToSpeech.setSpeechRate(0.5f);
        textToSpeech.speak(listabc_data.get(aws).getName(),TextToSpeech.QUEUE_FLUSH,null,null);
        playvoice(listabc_data.get(aws).getName());
        flaseaws(aws,x);
    }
    //khởi tạo random câu hỏi sai
    private void flaseaws(int aws,int x){
        int checkaws = 0;
        int l = listabc_data.size();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        while (checkaws < 3) {
            Random r = new Random();
            int random =  r.nextInt(l);

            if (!numbers.contains(random) && random != aws) {
                numbers.add(random);
                checkaws++;
            }
        }
            switch (x){
                case 0:
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw2)).execute(listabc_data.get(numbers.get(0)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw3)).execute(listabc_data.get(numbers.get(1)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw4)).execute(listabc_data.get(numbers.get(2)).getUrl());

                    break;
                case 1:
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw1)).execute(listabc_data.get(numbers.get(0)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw3)).execute(listabc_data.get(numbers.get(1)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw4)).execute(listabc_data.get(numbers.get(2)).getUrl());

                    break;
                case 2:
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw1)).execute(listabc_data.get(numbers.get(0)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw2)).execute(listabc_data.get(numbers.get(1)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw4)).execute(listabc_data.get(numbers.get(2)).getUrl());

                    break;
                case 3:
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw2)).execute(listabc_data.get(numbers.get(0)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw3)).execute(listabc_data.get(numbers.get(1)).getUrl());
                    new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_url_aw1)).execute(listabc_data.get(numbers.get(2)).getUrl());

                    break;
            }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageView_url_aw1:
//                Toast.makeText(Listen_Activity.this,"test",Toast.LENGTH_LONG).show();
                if(checkdapan==1){
                    imageView_url_aw1.setBackgroundResource(R.color.truecloler);
                    new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setdulieu();
                        imageView_url_aw1.setBackgroundResource(R.color.white);
                    }
                    },1000);

                }
                else {
                    imageView_url_aw1.setBackgroundResource(R.color.flasecolor);
                     new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageView_url_aw1.setBackgroundResource(R.color.white);
                    }
                    },1000);
                }
                break;
            case R.id.imageView_url_aw2:
//                Toast.makeText(Listen_Activity.this,"test",Toast.LENGTH_LONG).show();
                if(checkdapan==2){

                    imageView_url_aw2.setBackgroundResource(R.color.truecloler);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setdulieu();
                            imageView_url_aw2.setBackgroundResource(R.color.white);
                        }
                    },1000);

                }
                else {
                    imageView_url_aw2.setBackgroundResource(R.color.flasecolor);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView_url_aw2.setBackgroundResource(R.color.white);
                        }
                    },1000);
                }
                break;
            case R.id.imageView_url_aw3:
//                Toast.makeText(Listen_Activity.this,"test",Toast.LENGTH_LONG).show();
                if(checkdapan==3){
                    imageView_url_aw3.setBackgroundResource(R.color.truecloler);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setdulieu();
                            imageView_url_aw3.setBackgroundResource(R.color.white);
                        }
                    },1000);

                }
                else {
                    imageView_url_aw3.setBackgroundResource(R.color.flasecolor);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView_url_aw3.setBackgroundResource(R.color.white);
                        }
                    },1000);
                }
                break;
            case R.id.imageView_url_aw4:
//                Toast.makeText(Listen_Activity.this,"test",Toast.LENGTH_LONG).show();
                if(checkdapan==4){
                    imageView_url_aw4.setBackgroundResource(R.color.truecloler);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setdulieu();
                            imageView_url_aw4.setBackgroundResource(R.color.white);
                        }
                    },1000);

                }
                else {
                    imageView_url_aw4.setBackgroundResource(R.color.flasecolor);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView_url_aw4.setBackgroundResource(R.color.white);
                        }
                    },1000);
                }
                break;
        }
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

    // thông báo
    private void thongbao(){
        //Tạo đối tượng
        mActivity = Listen_Activity.this;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Xác nhận");
        b.setMessage("Tốc độ mạng không ổn định, bạn có muốn tải lại dữ liệu?");
// Nút Ok
        b.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mActivity.recreate();
//                finish();
            }
        });
//Nút Cancel
        b.setNegativeButton("Không đồng ý", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();
//Hiển thị
        al.show();
    }

    //show
    private void hide_view(){
        imageView_url_aw1.setVisibility(View.GONE);
        imageView_url_aw2.setVisibility(View.GONE);
        imageView_url_aw3.setVisibility(View.GONE);
        imageView_url_aw4.setVisibility(View.GONE);

        textView_abc_vi.setVisibility(View.GONE);
        textView_abc.setVisibility(View.GONE);
        imageButton_voice.setVisibility(View.GONE);
    }
    private void show_view(){
        imageView_url_aw1.setVisibility(View.VISIBLE);
        imageView_url_aw2.setVisibility(View.VISIBLE);
        imageView_url_aw3.setVisibility(View.VISIBLE);
        imageView_url_aw4.setVisibility(View.VISIBLE);

        textView_abc_vi.setVisibility(View.VISIBLE);
        textView_abc.setVisibility(View.VISIBLE);
        imageButton_voice.setVisibility(View.VISIBLE);
    }
}