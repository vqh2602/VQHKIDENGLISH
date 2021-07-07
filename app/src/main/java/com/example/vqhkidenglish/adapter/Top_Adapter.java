package com.example.vqhkidenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
