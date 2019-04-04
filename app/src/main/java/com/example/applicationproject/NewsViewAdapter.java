package com.example.applicationproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder>{

    private static final String TAG = "NewsViewAdapter";

    private ArrayList<News> mSlotList;
    private Context mContext;
    private FirebaseFirestore fs;

    public NewsViewAdapter(Context mContext, ArrayList<News> mSlotList) {
        this.mSlotList = mSlotList;
        this.mContext = mContext;
        fs = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public NewsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_listitem, parent, false);
        NewsViewAdapter.ViewHolder holder = new NewsViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

            holder.newsSlot.setText(mSlotList.get(position).getBody());
            holder.newsHeader.setText(mSlotList.get(position).getHeader());
            holder.newsDate.setText(mSlotList.get(position).getDate());


    }

    @Override
    public int getItemCount() {
        return mSlotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsDate;
        TextView newsHeader;
        TextView newsSlot;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            newsDate = itemView.findViewById(R.id.newsDate);
            newsHeader = itemView.findViewById(R.id.newsHead);
            newsSlot = itemView.findViewById(R.id.newsText);
            parentLayout = itemView.findViewById(R.id.news_parent_layout);
        }
    }
}
