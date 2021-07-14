package com.example.vqhkidenglish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.vqhkidenglish.adapter.Doc_Adapter;
import com.example.vqhkidenglish.adapter.Nghe_Adapter;
import com.example.vqhkidenglish.adapter.Top_Adapter;
import com.example.vqhkidenglish.adapter.Phatam_Adapter;
import com.example.vqhkidenglish.model.Doc;
import com.example.vqhkidenglish.model.Nghe;
import com.example.vqhkidenglish.model.Top;
import com.example.vqhkidenglish.model.Phatam;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

    RecyclerView discountRecyclerView, recentlyViewedRecycler,recently_listen,recently_doc;

    Top_Adapter topAdapter;
    List<Top> topList;


    Phatam_Adapter phatamAdapter;
    List<Phatam> phatamList;

    Nghe_Adapter nghe_adapter;
    List<Nghe> ngheList;

    Doc_Adapter doc_adapter;
    List<Doc> docList;

    Activity mActivity;
    AdView mAdView;
//    FirebaseFirestore filestore;
//    int checkuser = 0;
//    int xu =0;
    String useremail ="",userimage="",username ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //khởi tạo quảng cáo
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);



        discountRecyclerView = findViewById(R.id.discountedRecycler);
        recentlyViewedRecycler = findViewById(R.id.recently_item);
        recently_listen = findViewById(R.id.recently_menu);
        recently_doc = findViewById(R.id.recently_doc);

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


//check internet
        if (isConnected()) {
            addlist();
        } else {
//            Toast.makeText(getApplicationContext(), "No Internet Connection \n Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            thongbao();
        }

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
    topList.add(new Top(2, pet_min));
    topList.add(new Top(3, ocean_min));
    topList.add(new Top(4, animal_min));
    topList.add(new Top(5, pet_min2));

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

        // adding data to model
        docList = new ArrayList<>();
        docList.add(new Doc("Alphabet","bảng chữ cái", abc));
        docList.add(new Doc("Pet","thú cưng", pet));
        docList.add(new Doc("Wild Animal","động vật hoang dã",  animal));
        docList.add(new Doc("Sea Animal","động vật biển", ocean));

    setDiscountedRecycler(topList);
    setRecentlyViewedRecycler(phatamList);
    setRecentlyViewedNghe(ngheList);
    setRecentlyViewedDoc(docList);

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

    private void setRecentlyViewedDoc(List<Doc> docDataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recently_doc.setLayoutManager(layoutManager);
        doc_adapter = new Doc_Adapter(this, docDataList);
        recently_doc.setAdapter(doc_adapter);
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
        overridePendingTransition(R.anim.in_left,R.anim.out_left);

    }

    //check internet

public boolean isConnected() {
        boolean connected = false;
        try {
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        return connected;
        } catch (Exception e) {
        Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
        }

    // thông báo
    private void thongbao(){
        //Tạo đối tượng
        mActivity = MainActivity.this;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thiết lập tiêu đề
        b.setTitle("Không có kết nối mạng");
        b.setMessage("Chưa bật kết nối mạng \n Hãy bật kết wifi, dữ liệu di động sau đó click kết nối lại");
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
//                dialog.cancel();
                finish();
            }
        });
//Tạo dialog
        AlertDialog al = b.create();
//Hiển thị
        al.show();
    }
}