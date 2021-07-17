package com.example.vqhkidenglish.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vqhkidenglish.Account_activity;
import com.example.vqhkidenglish.Doc_Voice_Activity;
import com.example.vqhkidenglish.MainActivity;
import com.example.vqhkidenglish.Menu_Activity;
import com.example.vqhkidenglish.R;
import com.example.vqhkidenglish.data.User;
import com.example.vqhkidenglish.exten.Windy_Activity;
import com.example.vqhkidenglish.model.Vip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Vip_Adapter extends RecyclerView.Adapter<Vip_Adapter.RecentlyViewedViewHolder> {

    Context context;
    List<Vip> vipList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
// ...
    String id = "",date="";
    int xu1 =0;
    public Vip_Adapter(Context context, List<Vip> ngheList) {
        this.context = context;
        this.vipList= ngheList;
    }

    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vip_viewed_items, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, final int position) {

        holder.day.setText(vipList.get(position).getDay());
        holder.bg.setBackgroundResource(vipList.get(position).getImageUrl());
        holder.gia.setText(vipList.get(position).getGia());
        holder.image.setImageResource(vipList.get(position).getImageicon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent i=new Intent(context, ProductDetails.class);
//                i.putExtra("name", recentlyViewedList.get(position).getName());
//                i.putExtra("image", recentlyViewedList.get(position).getBigimageurl());
//                i.putExtra("price",recentlyViewedList.get(position).getPrice());
//                i.putExtra("desc",recentlyViewedList.get(position).getDescription());
//                i.putExtra("qty",recentlyViewedList.get(position).getQuantity());
//                i.putExtra("unit",recentlyViewedList.get(position).getUnit());
//
//                context.startActivity(i);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase = FirebaseDatabase.getInstance().getReference("users");
                switch (position){
                    case 0:
//                        Intent intent = new Intent(context, Windy_Activity.class);
                        //check đang nhap
                        if(currentUser == null){
                            Toast.makeText(context,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();

                        }
                        else {
                            canhbao();
                            checkuser(currentUser);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(xu1 <1000){
                                        thongbao2();
                                    }
                                    else {
                                        checkadddate(1000,1);
                                    }


                                }
                            },10000);

                        }

                        break;
                    case 1:
                        if(currentUser == null){
                        Toast.makeText(context,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();

                    }
                        else {
                            canhbao();
                        checkuser(currentUser);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(xu1 <6000){
                                    thongbao2();
                                }
                                else {
                                    checkadddate(6000,7);
                                }


                            }
                        },10000);

                    }
                        break;
                    case 2:
                        if(currentUser == null){
                        Toast.makeText(context,"Bạn chưa đăng nhập",Toast.LENGTH_LONG).show();

                    }
                        else {
                            canhbao();
                        checkuser(currentUser);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(xu1 <24000){
                                    thongbao2();
                                }
                                else {
                                    checkadddate(24000,30);
                                }

                            }
                        },10000);

                    }
                        break;
//                    case 3:
//                        Intent intent3 = new Intent(context,Windy_Activity.class);
//                        intent3.putExtra("idexten","https://vqh2602.github.io/swet_game/");
//                        context.startActivity(intent3);
//                        break;
//                    case 4:
//                        Intent intent4 = new Intent(context,Windy_Activity.class);
//                        intent4.putExtra("idexten","https://vqh2602.github.io/2048/");
//                        context.startActivity(intent4);
//                        break;
//                    case 5:
//                        Intent intent5 = new Intent(context,Windy_Activity.class);
//                        intent5.putExtra("idexten","https://vqh2602.github.io/tower_game/");
//                        context.startActivity(intent5);
////
//                        break;
                    default:
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return vipList.size();
    }

    public  static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{

        TextView day,gia;
        ConstraintLayout bg;
        ImageView image;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.product_day);
            gia = itemView.findViewById(R.id.textView_gia);
            bg = itemView.findViewById(R.id.recently_layout);
            image = itemView.findViewById(R.id.imageView_icon);

        }
    }


//kiem tra va tru tiền + gia hạn ngày
    private void checkadddate(int xu, int ngay){


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
            Date date2 = sdf.parse(date);
            Log.d("D2",date +"\n"+d1+"\n"+date2.compareTo(date1));
            if(date2.compareTo(date1) <= 0){
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(d1));
                c.add(Calendar.DATE, ngay);  // number of days to add
                d1 = sdf.format(c.getTime());
                Log.d("D1",d1);
                adduser(id,xu1 - xu, d1);
            }
            else {
                Calendar c = Calendar.getInstance();
                c.setTime(sdf.parse(date));
                c.add(Calendar.DATE, ngay);  // number of days to add
                date = sdf.format(c.getTime());
                Log.d("D2",date);
                adduser(id,xu1 - xu, date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void adduser(String userId, int xu, String date){
//        User user = new User(name, email,xu,userId,date);
//        mDatabase.child(userId).setValue(user);
        mDatabase.child(userId).child("xu").setValue(xu);
        mDatabase.child(userId).child("date").setValue(date);

        StyleableToast.Builder st = new StyleableToast.Builder(context)
                .text("Thanh Toán Hoàn Tất !")
                .backgroundColor(Color.parseColor("#01c88c"))
                .textColor(Color.parseColor("#ffffff"))
                .iconStart(R.drawable.sweet_banana_480px)
                .build();
        st.show();


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
//                        check=1;
                        id = user.id;
                        xu1 = user.xu;
                        date = user.date;

//                        if(user.xu - xu >=0){
//                          id =
//                            Log.d("USER", String.valueOf(user.xu));
//                            break;
//                        }
//                        else {
//                            Toast.makeText(context,"Không đủ xu",Toast.LENGTH_LONG).show();
//                        }
                        break;
                    }
                    else {
//                        check =0;
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

    private void canhbao(){
            //Tạo đối tượng
//            mActivity = MainActivity.this;
        StyleableToast.Builder st = new StyleableToast.Builder(context)
                .text("Đang Thanh Thanh Toán !")
                .backgroundColor(Color.parseColor("#09d828"))
                .textColor(Color.parseColor("#ffffff"))
                .iconStart(R.drawable.sweet_banana_480px)
                .build();
        st.show();

            AlertDialog.Builder b = new AlertDialog.Builder(context);
//Thiết lập tiêu đề
            b.setTitle("Đang Thục Hiện Giao Dịch");
            b.setMessage("Giao dịch đang thực hiện vui lòng chờ \n Trong quá trình này vui lòng có kết nối mạng ổn định và không thoát app. . .");
// Nút Ok
            b.setCancelable(false);

//Nút Cancel
//Tạo dialog
            AlertDialog al = b.create();
//Hiển thị

            al.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    al.hide();
                    al.cancel();
                    al.dismiss();

                }
            },10000);



    }


    // thông báo
    private void thongbao2() {
        //Tạo đối tượng
        StyleableToast.Builder st = new StyleableToast.Builder(context)
                .text("Xu trong tài khoản không đủ!")
                .backgroundColor(Color.parseColor("#ff0019"))
                .textColor(Color.parseColor("#ffffff"))
                .iconStart(R.drawable.sweet_banana_480px)
                .build();
        st.show();
    }
}
