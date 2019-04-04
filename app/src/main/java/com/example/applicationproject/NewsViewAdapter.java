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

    private ArrayList<String> mSlotList = new ArrayList<>();
    private Context mContext;
    private FirebaseFirestore fs;

    public NewsViewAdapter(Context mContext, ArrayList<String> mSlotList) {
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

        holder.newsSlot.setText(mSlotList.get(position));

        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on: " + mSlotList.get(position));

//TEMP HARD CODE    !!!
                // ... .document("4-3-19");
//Change to a variable
                final DocumentReference docRef = fs.collection("news").document("4-3-19");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                Log.d(TAG, "this is the document data: " + document.getData());
                                Date data = document.toObject(Date.class);
                                if(data.shifts.contains(mSlotList.get(position))) // if the document has the string
                                {
                                    Log.d(TAG, "This shift already exists! mSlotList: " + mSlotList.get(position));
                                } else { // then create a new shift string
                                    data.shifts.add(mSlotList.get(position));
                                    docRef.set(data);

                                }
                            } else { // than create a new date object
                                Log.d(TAG, "No such Document");
                                docRef.set(new Date(mSlotList.get(position)));
                            }
                        } else{
                            Log.d(TAG, "Get failed with ", task.getException());
                        }
                    }
                });


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mSlotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView newsSlot;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            newsSlot = itemView.findViewById(R.id.newsText);
            parentLayout = itemView.findViewById(R.id.news_parent_layout);
        }
    }
}
