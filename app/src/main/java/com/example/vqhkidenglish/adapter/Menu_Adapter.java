package com.example.vqhkidenglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vqhkidenglish.R;
import com.example.vqhkidenglish.exten.Windy_Activity;
import com.example.vqhkidenglish.model.Menu;

import java.util.List;

public class Menu_Adapter extends RecyclerView.Adapter<Menu_Adapter.RecentlyViewedViewHolder> {

    Context context;
    List<Menu> menuList;

    public Menu_Adapter(Context context, List<Menu> ngheList) {
        this.context = context;
        this.menuList = ngheList;
    }

    @NonNull
    @Override
    public RecentlyViewedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_viewed_items, parent, false);

        return new RecentlyViewedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentlyViewedViewHolder holder, final int position) {

        holder.name.setText(menuList.get(position).getName());
        holder.bg.setBackgroundResource(menuList.get(position).getImageUrl());
        holder.vi.setText(menuList.get(position).getVi());
        holder.image.setImageResource(menuList.get(position).getImageicon());

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
                        Intent intent = new Intent(context, Windy_Activity.class);
                        intent.putExtra("idexten","https://vqh2602.github.io/windi.github.io/");
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(context,Windy_Activity.class);
                        intent1.putExtra("idexten","https://beaklock.herokuapp.com/");
                        context.startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(context,Windy_Activity.class);
                        intent2.putExtra("idexten","https://vqh2602.github.io/tic_tac_toe/");
                        context.startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(context,Windy_Activity.class);
                        intent3.putExtra("idexten","https://vqh2602.github.io/swet_game/");
                        context.startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(context,Windy_Activity.class);
                        intent4.putExtra("idexten","https://vqh2602.github.io/2048/");
                        context.startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(context,Windy_Activity.class);
                        intent5.putExtra("idexten","https://vqh2602.github.io/tower_game/");
                        context.startActivity(intent5);
//
                        break;
                    default:
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public  static class RecentlyViewedViewHolder extends RecyclerView.ViewHolder{

        TextView name,vi;
        ConstraintLayout bg;
        ImageView image;

        public RecentlyViewedViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            vi = itemView.findViewById(R.id.product_name_vi);
            bg = itemView.findViewById(R.id.recently_layout);
            image = itemView.findViewById(R.id.imageView_icon);

        }
    }

}
