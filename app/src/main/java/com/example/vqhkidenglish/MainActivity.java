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
import com.example.vqhkidenglish.data.User;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //kh???i t???o qu???ng c??o
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

//check ??ang nhap
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

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
//            Toast.makeText(getApplicationContext(), "No Internet Connection \n Kh??ng c?? k???t n???i m???ng", Toast.LENGTH_SHORT).show();
            thongbao();
        }


  if(currentUser == null){

  }else {
      checkuser(currentUser);
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              if(check == 0){
                  DateFormat dform = new SimpleDateFormat("dd/MM/yyyy");
                  Date obj = new Date();


                  adduser(currentUser.getUid(),currentUser.getDisplayName(),currentUser.getEmail(),0, String.valueOf(dform.format(obj)));
              }
              else {

              }

          }
      },5000);
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
////                                    Log.d("Checkuser: ", "t??m th???y");
//                                    xu = Integer.parseInt(document.get("xu").toString());
//                                    break;
//                                }
//                                else {
//                                    Log.d("Checkuser: ", "k t??m th???y");
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
    phatamList.add(new Phatam("Alphabet","b???ng ch??? c??i", abc));
    phatamList.add(new Phatam("Pet","th?? c??ng", pet));
    phatamList.add(new Phatam("Wild Animal","?????ng v???t hoang d??",  animal));
    phatamList.add(new Phatam("Sea Animal","?????ng v???t bi???n", ocean));


    // adding data to model
    ngheList = new ArrayList<>();
    ngheList.add(new Nghe("Alphabet","b???ng ch??? c??i", abc));
    ngheList.add(new Nghe("Pet","th?? c??ng", pet));
    ngheList.add(new Nghe("Wild Animal","?????ng v???t hoang d??",  animal));
    ngheList.add(new Nghe("Sea Animal","?????ng v???t bi???n", ocean));

        // adding data to model
        docList = new ArrayList<>();
        docList.add(new Doc("Alphabet","b???ng ch??? c??i", abc));
        docList.add(new Doc("Pet","th?? c??ng", pet));
        docList.add(new Doc("Wild Animal","?????ng v???t hoang d??",  animal));
        docList.add(new Doc("Sea Animal","?????ng v???t bi???n", ocean));

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

    // th??ng b??o
    private void thongbao(){
        //T???o ?????i t?????ng
        mActivity = MainActivity.this;
        AlertDialog.Builder b = new AlertDialog.Builder(this);
//Thi???t l???p ti??u ?????
        b.setTitle("Kh??ng c?? k???t n???i m???ng");
        b.setMessage("Ch??a b???t k???t n???i m???ng \n H??y b???t k???t wifi, d??? li???u di ?????ng sau ???? click k???t n???i l???i");
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



    private void adduser(String userId, String name, String email, int xu, String date){
        User user = new User(name, email,xu,userId,date);
        mDatabase.child(userId).setValue(user);
    }

    private void checkuser(FirebaseUser currentUser){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
//                    Log.d("Data_abc_string", abcData.getUrl());
                    Log.d("USER1", String.valueOf(user.xu) + currentUser.getUid() + "|"+user.id);
                    Log.d("USER1", String.valueOf(currentUser.getUid().equals(user.id)));
                    if(currentUser.getUid().equals(user.id) ){
                        check=1;
//                        textView_xu.setText(String.valueOf("Xu: "+user.xu));
                        Log.d("USER", String.valueOf(user.xu));
                        break;
                    }
                    else {
                        check =0;
                        Log.d("USER", "erro");
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
}