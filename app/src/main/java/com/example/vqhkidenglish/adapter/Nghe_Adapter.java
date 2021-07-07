package com.example.vqhkidenglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vqhkidenglish.Home_Activity;
import com.example.vqhkidenglish.R;
import com.example.vqhkidenglish.model.Nghe;
import com.example.vqhkidenglish.model.Phatam;

import java.util.List;

public class Nghe_Adapter extends RecyclerView.Adapter<Nghe_Adapter.RecentlyViewedViewHolder> {

    Context context;
    List<Nghe> NgheList;

    public Nghe_Adapter(Context context, List<Nghe> ngheList) {
        this.context = context;
        this.NgheList = ngheList;
    }

    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recently_viewed_items, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, final int position) {

        holder.name.setText(NgheList.get(position).getName());
        holder.bg.setBackgroundResource(NgheList.get(position).getImageUrl());
        holder.vi.setText(NgheList.get(position).getVi());

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
                switch (position){
                    case 0:
                        Intent intent = new Intent(context, Home_Activity.class);
                        intent.putExtra("key","ABC");
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(context,Home_Activity.class);
                        intent1.putExtra("key","PET");
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context,Home_Activity.class);
                        intent2.putExtra("key","WILDANIMAL");
                        context.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(context,Home_Activity.class);
                        intent3.putExtra("key","SEAANIMAL");
                        context.startActivity(intent3);
                        break;

                    default:
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return NgheList.size();
    }

    public  static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{

        TextView name,vi;
        ConstraintLayout bg;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            vi = itemView.findViewById(R.id.product_name_vi);
            bg = itemView.findViewById(R.id.recently_layout);

        }
    }

}
