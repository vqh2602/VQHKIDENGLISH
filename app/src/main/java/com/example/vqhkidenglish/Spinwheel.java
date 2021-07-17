package com.example.vqhkidenglish;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.vqhkidenglish.data.User;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Spinwheel extends AppCompatActivity {

    ImageView imageView_wheel;
    ImageButton imageButton_spin;
    int degree = 0;
    int degree_old = 0;
    Random r;
    int score=0;
    public static final float FACTOR = 15f;

    TextView textView;
    int intValue;
    String current_score;
    LottieAnimationView animationView;

    private RewardedAd mRewardedAd;
    private DatabaseReference mDatabase;


    String id = "";
int xu =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinwheel);
        imageView_wheel = (ImageView) findViewById(R.id.wheel);
        imageButton_spin = (ImageButton) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textview);
        animationView = findViewById(R.id.animationView);

            Intent intent = getIntent();
            id = intent.getStringExtra("id");

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        checkuser();
        adLoad();

anview();





//
//getSupportActionBar().hide();

current_score  = currentNumber(360 - (degree % 360));


        r = new Random();


        imageButton_spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;

                RotateAnimation rotateAnimation = new RotateAnimation(degree_old, degree,
                        RotateAnimation.RELATIVE_TO_SELF, .5f,
                        RotateAnimation.RELATIVE_TO_SELF, .5f);


                rotateAnimation.setDuration(3600);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());


                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                        textView.setText("score");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {



                        textView.setText( currentNumber(360 - (degree % 360)));


                        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();

                        int n =  intValue+score;
                        editor.putInt("your_int_key", n);
                        editor.commit();


                        SharedPreferences spe = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        int  myIntValue = spe.getInt("your_int_key", 0);

//
//                        user_id_child.child("scores").setValue(+n);





                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView_wheel.startAnimation(rotateAnimation);

                if (mRewardedAd != null) {
                    Activity activityContext = Spinwheel.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d("TAG", "The user earned the reward.");
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();

                            diaglog();


                        }
                    });
                } else {
                    Log.d("TAG", "The rewarded ad wasn't ready yet.");
                }
            }
        });


    }


    private String currentNumber(int degree){

        String text = "";

        if(degree>= (FACTOR*1) && degree<(FACTOR*3)  ){

            text = "150";

            score = score+150;

        }


        if(degree>= (FACTOR*3) && degree<(FACTOR*5)  ){

            text = "50";
            score = score+50;
        }

        if(degree>= (FACTOR*5) && degree<(FACTOR*7)  ){

            text = "10";
            score = score+10;
        }

        if(degree>= (FACTOR*7) && degree<(FACTOR*9)  ){

            text = "300";
            score = score+300;

        }

        if(degree>= (FACTOR*9) && degree<(FACTOR*11)  ){

            text = "500";
            score = score+500;
        }

        if(degree>= (FACTOR*11) && degree<(FACTOR*13)  ){

            text = "200";
            score = score+200;
        }

        if(degree>= (FACTOR*13) && degree<(FACTOR*15)  ){

            text = "450";
            score = score+450;
        }

        if(degree>= (FACTOR*15) && degree<(FACTOR*17)  ){

            text = "250";
            score = score+250;
        }

        if(degree>= (FACTOR*17) && degree<(FACTOR*19)  ){

            text = "1000";
            score = score+1000;
        }

        if(degree>= (FACTOR*19) && degree<(FACTOR*21)  ){

            text = "500";
            score = score+500;
        }

        if(degree>= (FACTOR*21) && degree<(FACTOR*23)  ){

            text = "200";
            score = score+200;
        }

        if(degree>= (FACTOR*23) && degree<(360) || degree>=0 && degree <(FACTOR*1) ){

            text = "100 point";
            score = score+100;

        }

        return text;



    }

public void diaglog(){

    final Dialog dialog = new Dialog(Spinwheel.this);
    dialog.setContentView(R.layout.custom_dialog);
    Button dialogButton = (Button) dialog.findViewById(R.id.cool_id);
    TextView textView = (TextView)dialog.findViewById(R.id.dialog_score_id);
    String a = currentNumber(360 - (degree % 360));

     textView.setText(a+" "+"Points");

     adduser(id, Integer.parseInt(a)+xu);


    // if button is clicked, close the custom dialog

    dialogButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();

            anview();
            adLoad();

        }
    });
    dialog.show();

}

    @Override
    protected void onStart() {
        super.onStart();
//
//        Intent mIntent = getIntent();
//     intValue = mIntent.getIntExtra("INT", 0);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void adLoad(){
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("TAGad", loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d("TAGad", "Ad was loaded.");
                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d("TAG", "Ad was shown.");


                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d("TAG", "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d("TAG", "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                    }
                });
    }

    private void adduser(String userId, int xu){
//        User user = new User(name, email,xu,userId,date);
//        mDatabase.child(userId).setValue(user);
        mDatabase.child(userId).child("xu").setValue(xu);
//        mDatabase.child(userId).child("date").setValue(date);


    }

    private void checkuser(){

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    // TODO: handle the post
                    User user = postSnapshot.getValue(User.class);
//                    Log.d("Data_abc_string", abcData.getUrl());
//                    Log.d("USER1", String.valueOf(user.xu) + currentUser.getUid() + "|"+user.id);
                    if(id.equals(user.id)){
                        xu = user.xu;
//                        check=1;
//                        textView_xu.setText(String.valueOf("Xu: "+user.xu));
//                        textViewdate.setText("Ngày hết hạn: "+user.date);
                        Log.d("USER", String.valueOf(user.xu));
                        break;
                    }
                    else {
//                        check =0;
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

    private  void anview(){
        animationView.setVisibility(View.VISIBLE);
        imageView_wheel.setVisibility(View.GONE);
        imageButton_spin.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationView.setVisibility(View.GONE);
                imageView_wheel.setVisibility(View.VISIBLE);
                imageButton_spin.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        },4000);
    }
}
