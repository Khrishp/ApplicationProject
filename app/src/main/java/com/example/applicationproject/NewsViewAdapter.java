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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder>{

    private static final String TAG = "NewsViewAdapter";

    private ArrayList<String> mBodyList = new ArrayList<>();
    private ArrayList<String> mHeaderList = new ArrayList<>();
    private ArrayList<String> mDateList = new ArrayList<>();
    private Context mContext;
    private FirebaseFirestore fs;

    public NewsViewAdapter(Context mContext, ArrayList<String> dateList, ArrayList<String> headerList, ArrayList<String> bodyList) {
        this.mDateList = dateList;
        this.mHeaderList = headerList;
        this.mBodyList = bodyList;
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

        holder.dateSlot.setText(mDateList.get(position));
        holder.headerSlot.setText(mHeaderList.get(position));
        holder.bodySlot.setText(mBodyList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateSlot;
        TextView headerSlot;
        TextView bodySlot;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            dateSlot = itemView.findViewById(R.id.newsDate);
            headerSlot = itemView.findViewById(R.id.newsHead);
            bodySlot = itemView.findViewById(R.id.newsText);
            parentLayout = itemView.findViewById(R.id.news_parent_layout);
        }
    }
}
