package com.example.applicationproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applicationproject.R;

import java.util.ArrayList;

public class VolunteerViewAdapter extends RecyclerView.Adapter<VolunteerViewAdapter.ViewHolder>{

    private static final String TAG = "NewsViewAdapter";

    private ArrayList<String> mNameList;
    private ArrayList<Integer> mHoursList;
    private Context mContext;

    public VolunteerViewAdapter(Context mContext, ArrayList<String> nameList, ArrayList<Integer> hoursList) {
        this.mNameList = nameList;
        this.mHoursList = hoursList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VolunteerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_volunteer_item, parent, false);
        VolunteerViewAdapter.ViewHolder holder = new VolunteerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.nameSlot.setText(mNameList.get(position));
        holder.hoursSlot.setText("" + mHoursList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameSlot;
        TextView hoursSlot;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameSlot = itemView.findViewById(R.id.volunteerName);
            hoursSlot = itemView.findViewById(R.id.volunteerHours);
            parentLayout = itemView.findViewById(R.id.volunteer_parent_layout);
        }
    }
}
