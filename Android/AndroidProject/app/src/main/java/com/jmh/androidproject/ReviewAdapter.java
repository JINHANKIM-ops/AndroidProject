package com.jmh.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>{

    //---데이터 잘 불러오는지 임시 확인하는 코드---
    String[] list;
    String[] reviews;

    public ReviewAdapter(String[]list, String[] reviews){
        this.list=list;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {
        //---데이터 잘 불러오는지 임시 확인하는 코드---
        holder.title.setText(list[position]);
        holder.review.setText(reviews[position]);

    }

    @Override
    public int getItemCount() {
        //---데이터 잘 불러오는지 임시 확인하는 코드---
        return list.length;

        //return 0;
    }

    //class for ViewHolder
    static class MyViewHolder extends RecyclerView.ViewHolder{

        RoundedImageView poster;
        TextView title, review;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.title);
            review = itemView.findViewById(R.id.review);
        }
    }
}
