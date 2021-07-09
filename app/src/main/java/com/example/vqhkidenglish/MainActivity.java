package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.vqhkidenglish.adapter.Nghe_Adapter;
import com.example.vqhkidenglish.adapter.Top_Adapter;
import com.example.vqhkidenglish.adapter.Phatam_Adapter;
import com.example.vqhkidenglish.model.Nghe;
import com.example.vqhkidenglish.model.Top;
import com.example.vqhkidenglish.model.Phatam;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.vqhkidenglish.R.drawable.*;

public class MainActivity extends AppCompatActivity {

    RecyclerView discountRecyclerView, recentlyViewedRecycler,recently_listen;

    Top_Adapter topAdapter;
    List<Top> topList;


    Phatam_Adapter phatamAdapter;
    List<Phatam> phatamList;

    Nghe_Adapter nghe_adapter;
    List<Nghe> ngheList;

//    FirebaseFirestore filestore;
//    int checkuser = 0;
//    int xu =0;
    String useremail ="",userimage="",username ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        discountRecyclerView = findViewById(R.id.discountedRecycler);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        recently_listen = findViewById(R.id.recently_menu);

        Intent intent = getIntent();
        String userid = intent.getStringExtra("userid");
        useremail = intent.getStringExtra("useremail");
        userimage = intent.getStringExtra("userimage");
        username = intent.getStringExtra("username");
//        Log.d("Xu",userimage);
        //check user
//        filestore = FirebaseFirestore.getInstance();
//        CollectionReference reference = filestore.collection("User");

//        checkuser(userid);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                Log.d("Checkuser1", String.valueOf(checkuser));
////                Log.d("Checkuser: ", String.valueOf(xu));
//                adduser(userid,useremail);
//            }
//        },5500);

        addlist();



    }

//// check user
//    private void checkuser(String userid){
//
//
//        filestore.collection("user")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("Checkuser: ", document.getId() + " => " + document.getData());
//                                Log.d("Checkuser: ", document.get("userid").toString());
//                                if(document.get("userid").toString().equals(userid) ){
//                                    checkuser = 1;
////                                    Log.d("Checkuser: ", "tìm thấy");
//                                    xu = Integer.parseInt(document.get("xu").toString());
//                                    break;
//                                }
//                                else {
//                                    Log.d("Checkuser: ", "k tìm thấy");
//                                }
//                            }
//                        } else {
//                            Log.d("Checkuser: ", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }

//    private void adduser(String userid, String useremail){
//        if(checkuser == 0){
//            Map<String, Object> city = new HashMap<>();
//            city.put("email", useremail);
//            city.put("userid", userid);
//            city.put("xu", 0);
//
//            filestore.collection("user").document(userid)
//                    .set(city)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d("addnewusser:", "DocumentSnapshot successfully written!");
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("addnewusser:", "Error writing document", e);
//                        }
//                    });
//        }
//    }

    private void addlist(){

    // adding data to model
    topList = new ArrayList<>();
    topList.add(new Top(1, abc_min));
    topList.add(new Top(2, abc_min));
    topList.add(new Top(3, abc_min));
    topList.add(new Top(4, abc_min));
//        discountedProductsList.add(new DiscountedProducts(5, abc));
//        discountedProductsList.add(new DiscountedProducts(6, abc));

    // adding data to model


    // adding data to model
    phatamList = new ArrayList<>();
    phatamList.add(new Phatam("Alphabet","bảng chữ cái", abc));
    phatamList.add(new Phatam("Pet","thú cưng", pet));
    phatamList.add(new Phatam("Wild Animal","động vật hoang dã",  animal));
    phatamList.add(new Phatam("Sea Animal","động vật biển", ocean));


    // adding data to model
    ngheList = new ArrayList<>();
    ngheList.add(new Nghe("Alphabet","bảng chữ cái", abc));
    ngheList.add(new Nghe("Pet","thú cưng", pet));
    ngheList.add(new Nghe("Wild Animal","động vật hoang dã",  animal));
    ngheList.add(new Nghe("Sea Animal","động vật biển", ocean));

    setDiscountedRecycler(topList);
    setRecentlyViewedRecycler(phatamList);
    setRecentlyViewedNghe(ngheList);

}

    private void setDiscountedRecycler(List<Top> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        discountRecyclerView.setLayoutManager(layoutManager);
        topAdapter = new Top_Adapter(this,dataList);
        discountRecyclerView.setAdapter(topAdapter);
    }

    private void setRecentlyViewedRecycler(List<Phatam> phatamDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recentlyViewedRecycler.setLayoutManager(layoutManager);
        phatamAdapter = new Phatam_Adapter(this, phatamDataList);
        recentlyViewedRecycler.setAdapter(phatamAdapter);
    }

    private void setRecentlyViewedNghe(List<Nghe> ngheDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recently_listen.setLayoutManager(layoutManager);
        nghe_adapter = new Nghe_Adapter(this, ngheDataList);
        recently_listen.setAdapter(nghe_adapter);
    }

    //onclick menu
    public void onClickmenu(View v) {
        Intent intent = new Intent(MainActivity.this, Menu_Activity.class);
//        String xu1 = String.valueOf(xu);
//        intent.putExtra("userxu",xu1);
        intent.putExtra("useremail",useremail);
        intent.putExtra("userimage",userimage);
        intent.putExtra("username",username);
        startActivity(intent);
    }
}