package com.example.vqhkidenglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vqhkidenglish.Phatam_Activity;
import com.example.vqhkidenglish.R;
import com.example.vqhkidenglish.model.Top;

import java.util.List;

public class Top_Adapter extends RecyclerView.Adapter<Top_Adapter.DiscountedProductViewHolder> {

    Context context;
    List<Top> topList;

    public Top_Adapter(Context context, List<Top> topList) {
        this.context = context;
        this.topList = topList;
    }

    @NonNull
    @Override
    public DiscountedProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.discounted_row_items, parent, false);
        return new DiscountedProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountedProductViewHolder holder, int position) {

        holder.discountImageView.setImageResource(topList.get(position).getImageurl());

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
                        Intent intent = new Intent(context, Phatam_Activity.class);
                        intent.putExtra("key","ABC");
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(context, Phatam_Activity.class);
                        intent1.putExtra("key","PET");
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context, Phatam_Activity.class);
                        intent2.putExtra("key","WILDANIMAL");
                        context.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(context, Phatam_Activity.class);
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
        return topList.size();
    }

    public static class DiscountedProductViewHolder extends  RecyclerView.ViewHolder{

        ImageView discountImageView;

        public DiscountedProductViewHolder(@NonNull View itemView) {
            super(itemView);

            discountImageView = itemView.findViewById(R.id.discountImage);

        }
    }
}
