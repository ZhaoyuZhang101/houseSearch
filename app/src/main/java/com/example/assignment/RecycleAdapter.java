package com.example.assignment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import Methods.House;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyHolder> {
    ArrayList<Integer> posS = new ArrayList<>();
    ArrayList<House> houses;
    Drawable drawable;
    ArrayList<Bitmap> pics;
    protected boolean isScrolling = false;
    public RecycleAdapter (ArrayList<House> houses, Drawable drawable, ArrayList<Bitmap> pics) {
        this.houses = houses;
        this.drawable = drawable;
        this.pics = pics;
    }

    public void addData(ArrayList<House> houses2) {
        houses.addAll(houses2);
        notifyItemRangeChanged(0,houses.size());
    }

    public void removeAll() {
        this.houses.clear();
        notifyItemRangeRemoved(0, houses.size());
    }

    public void changeState(boolean isScrolling) {
        this.isScrolling = isScrolling;
    }

    public String remove_startedSpace(String name) {
        return name.trim();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textView2;
        ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_Name);
            imageView = itemView.findViewById(R.id.item_image);
            textView2 = itemView.findViewById(R.id.item_text);
            int width = ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getWidth();
            Random random = new Random();
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            params.width = width/2;
            itemView.setLayoutParams(params);
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        House house = this.houses.get(position);
        if (!houses.isEmpty()&&!isScrolling) {
            holder.imageView.setImageBitmap(pics.get((Integer.parseInt(house.getID())-1)%pics.size()));
            posS.add(position);
        } else if (isScrolling&&!posS.contains(position)) {
            holder.imageView.setImageDrawable(drawable);
        } else if (isScrolling&&posS.contains(position)) {
            holder.imageView.setImageBitmap(pics.get((Integer.parseInt(house.getID())-1)%pics.size()));
        }
        holder.textView.setText(Objects.requireNonNull(house).getID());
        String text = remove_startedSpace(house.getLocation().getStreet()) +", "+ remove_startedSpace(house.getLocation().getCity());
        holder.textView2.setText(text);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(Objects.requireNonNull(houses.get(position)).getID());
    }

    @Override
    public int getItemCount() {
        return houses.size();
    }
}
