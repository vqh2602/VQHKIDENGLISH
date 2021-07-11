package com.example.vqhkidenglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vqhkidenglish.adapter.Menu_Adapter;
import com.example.vqhkidenglish.adapter.Top_Adapter;
import com.example.vqhkidenglish.model.Menu;
import com.example.vqhkidenglish.model.Nghe;
import com.example.vqhkidenglish.model.Phatam;
import com.example.vqhkidenglish.model.Top;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.vqhkidenglish.R.drawable.abc;
import static com.example.vqhkidenglish.R.drawable.abc_min;
import static com.example.vqhkidenglish.R.drawable.ane2048_g;
import static com.example.vqhkidenglish.R.drawable.animal;
import static com.example.vqhkidenglish.R.drawable.box_480px;
import static com.example.vqhkidenglish.R.drawable.building_construction_256px;
import static com.example.vqhkidenglish.R.drawable.cv1;
import static com.example.vqhkidenglish.R.drawable.cv2;
import static com.example.vqhkidenglish.R.drawable.cv3;
import static com.example.vqhkidenglish.R.drawable.cv4;
import static com.example.vqhkidenglish.R.drawable.cv5;
import static com.example.vqhkidenglish.R.drawable.info_480px;
import static com.example.vqhkidenglish.R.drawable.menu_50px;
import static com.example.vqhkidenglish.R.drawable.ocean;
import static com.example.vqhkidenglish.R.drawable.pet;
import static com.example.vqhkidenglish.R.drawable.sun_500px;
import static com.example.vqhkidenglish.R.drawable.sweet_banana_480px;
import static com.example.vqhkidenglish.R.drawable.tic_tac_toe_480px;

public class Menu_Activity extends AppCompatActivity {
    RecyclerView recently_menu;

    Menu_Adapter menu_adapter;
    List<Menu> menuList;

    TextView textView_name,textView_email,textView_xu;
    ImageView imageView_user;
    Button button_Logout;

    String useremail ="",userimage="",username ="";
    int xu =0;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);





        recently_menu = findViewById(R.id.recently_menu);
        addlist();
        anhxa();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            Toast.makeText(Menu_Activity.this,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();
            button_Logout.setText("LOGIN");
        }
        else {
            button_Logout.setText("LOGOUT");

//            Intent intent = getIntent();
//            useremail = intent.getStringExtra("useremail");
//            userimage = intent.getStringExtra("userimage");
//            username = intent.getStringExtra("username");
//        xu = Integer.parseInt(intent.getStringExtra("userxu"));
//        Log.d("Xu",userimage);

            textView_email.setText("Email: "+currentUser.getEmail());
            textView_name.setText(currentUser.getDisplayName());
            textView_xu.setText(String.valueOf("Xu: "+"sắp ra mắt"));
            new Menu_Activity.DownloadImageFromInternet((ImageView) findViewById(R.id.imageView_user)).execute(currentUser.getPhotoUrl().toString());

        }


    }


    private void addlist(){

        // adding data to model
        menuList = new ArrayList<>();
        menuList.add(new Menu("Bản Đồ Thời Tiết","Weather Map", cv1,sun_500px));
        menuList.add(new Menu("Chiếc Hộp Kì Quái","Weird Box", cv2,box_480px));
        menuList.add(new Menu("Cờ Caro","Tic Tac Toe",  cv3,tic_tac_toe_480px));
        menuList.add(new Menu("Lật Thẻ Bài","Sweet Game", cv4,sweet_banana_480px));
        menuList.add(new Menu("2048","2048", cv5,ane2048_g));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));
//        menuList.add(new Menu("Sea Animal","động vật biển", cv5,menu_50px));
        menuList.add(new Menu("Xây Dựng Toà Tháp","Tower Building Game", cv1,building_construction_256px));
        menuList.add(new Menu("Thông Tin Ứng Dụng","About", cv2,info_480px));

        setDiscountedRecycler(menuList);

    }

    private void setDiscountedRecycler(List<Menu> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recently_menu.setLayoutManager(layoutManager);
        menu_adapter = new Menu_Adapter (this,dataList);
        recently_menu.setAdapter(menu_adapter);
    }

private  void anhxa(){
    textView_name =findViewById(R.id.textView_name);
            textView_email =findViewById(R.id.textView_email);
                    textView_xu= findViewById(R.id.textView_xu);
    imageView_user=findViewById(R.id.imageView_user);
    button_Logout = findViewById(R.id.button_Logout);
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
        Intent intent = new Intent(Menu_Activity.this, Login_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}