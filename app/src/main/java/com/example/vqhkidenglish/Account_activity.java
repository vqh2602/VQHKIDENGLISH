package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vqhkidenglish.adapter.Menu_Adapter;
import com.example.vqhkidenglish.adapter.Vip_Adapter;
import com.example.vqhkidenglish.data.User;
import com.example.vqhkidenglish.exten.Windy_Activity;
import com.example.vqhkidenglish.model.Menu;
import com.example.vqhkidenglish.model.Vip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.vqhkidenglish.R.drawable.ac1;
import static com.example.vqhkidenglish.R.drawable.ac2;
import static com.example.vqhkidenglish.R.drawable.ac3;
import static com.example.vqhkidenglish.R.drawable.ane2048_g;
import static com.example.vqhkidenglish.R.drawable.box_480px;
import static com.example.vqhkidenglish.R.drawable.building_construction_256px;
import static com.example.vqhkidenglish.R.drawable.cv1;
import static com.example.vqhkidenglish.R.drawable.cv2;
import static com.example.vqhkidenglish.R.drawable.cv3;
import static com.example.vqhkidenglish.R.drawable.cv4;
import static com.example.vqhkidenglish.R.drawable.cv5;
import static com.example.vqhkidenglish.R.drawable.face_blowing_a_kiss_50px;
import static com.example.vqhkidenglish.R.drawable.info_480px;
import static com.example.vqhkidenglish.R.drawable.kissing_face_with_closed_eyes_50px;
import static com.example.vqhkidenglish.R.drawable.smiling_face_with_hearts_50px;
import static com.example.vqhkidenglish.R.drawable.sun_500px;
import static com.example.vqhkidenglish.R.drawable.sweet_banana_480px;
import static com.example.vqhkidenglish.R.drawable.tic_tac_toe_480px;

public class Account_activity extends AppCompatActivity {

    RecyclerView recently_menu;

    Vip_Adapter vip_adapter;
    List<Vip> vipList;

    TextView textView_name,textView_email,textView_xu,textViewdate;
    ImageView imageView_user;
    Button button_Logout,buttonspin;

    String useremail ="",userimage="",username ="";
    int xu =0;
    int check=0;
    private DatabaseReference mDatabase;
// ...

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        recently_menu = findViewById(R.id.recently_menu);
        addlist();
        anhxa();
//check đang nhap
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();



//        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
//    adduser(currentUser.getUid(),currentUser.getDisplayName(),currentUser.getEmail(),0);



        if(currentUser == null){
            Toast.makeText(Account_activity.this,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();
            button_Logout.setText("LOGIN");
        }
        else {
            button_Logout.setText("LOGOUT");
            checkuser(currentUser);
//            Intent intent = getIntent();
//            useremail = intent.getStringExtra("useremail");
//            userimage = intent.getStringExtra("userimage");
//            username = intent.getStringExtra("username");
//        xu = Integer.parseInt(intent.getStringExtra("userxu"));
//        Log.d("Xu",userimage);

            textView_email.setText("Email: "+currentUser.getEmail());
            textView_name.setText(currentUser.getDisplayName());
//            textView_xu.setText(String.valueOf("Xu: "+"xin chờ 10s ..."));
            new Account_activity.DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_user)).execute(currentUser.getPhotoUrl().toString());

//add user
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(check == 0){
//                        adduser(currentUser.getUid(),currentUser.getDisplayName(),currentUser.getEmail(),0);
                    }
                    else {

                    }


                }
            },5000);
        }


        buttonspin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Account_activity.this, Spinwheel.class);
                intent1.putExtra("id",currentUser.getUid());
                startActivity(intent1);
            }
        });

    }





    private  void anhxa(){
        textView_name =findViewById(R.id.textView_name);
        textView_email =findViewById(R.id.textView_email);
        textView_xu= findViewById(R.id.textView_xu);
        imageView_user=findViewById(R.id.imageView_user);
        button_Logout = findViewById(R.id.button_Logout);
        textViewdate = findViewById(R.id.textViewdate);
        buttonspin = findViewById(R.id.buttonspin);
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

    //onclick logout
    public void onClick_logout(View v) {
        mAuth.getInstance().signOut();
        Intent intent = new Intent(Account_activity.this, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_right,R.anim.out_right);

    }

//    private void adduser(String userId, String name, String email, int xu){
//        User user = new User(name, email,xu,userId,);
//        mDatabase.child(userId).setValue(user);
//    }

    private void checkuser(FirebaseUser currentUser){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
//                    Log.d("Data_abc_string", abcData.getUrl());
                    Log.d("USER1", String.valueOf(user.xu) + currentUser.getUid() + "|"+user.id);
                    if(currentUser.getUid().equals(user.id)){
                        check=1;
                        textView_xu.setText(String.valueOf("Xu: "+user.xu));
                        textViewdate.setText("Ngày hết hạn: "+user.date);
                        Log.d("USER", String.valueOf(user.xu));
                        break;
                    }
                    else {
                        check =0;
                    }
//                    Log.d("Data_abc_list", String.valueOf(listabc_data.size()));


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Log.w("Data", "loadPost:onCancelled", error.toException());
            }
        });
    }




    private void addlist(){

        // adding data to model
        vipList = new ArrayList<>();
       vipList.add(new Vip("1 Days","1000 Xu",ac1,face_blowing_a_kiss_50px));
        vipList.add(new Vip("7 Days","6000 Xu", ac2,kissing_face_with_closed_eyes_50px));
       vipList.add(new Vip("30 Days","24000 Xu",  ac3,smiling_face_with_hearts_50px));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));


        setDiscountedRecycler(vipList);

    }

    private void setDiscountedRecycler(List<Vip> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recently_menu.setLayoutManager(layoutManager);
        vip_adapter = new Vip_Adapter (this,dataList);
        recently_menu.setAdapter(vip_adapter);
    }




}




