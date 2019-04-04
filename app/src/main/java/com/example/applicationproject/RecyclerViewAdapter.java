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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mSlotList = new ArrayList<>();
    private Context mContext;
    private String mDate;
    private FirebaseFirestore fs;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> mSlotList, String mDate) {
        this.mSlotList = mSlotList;
        this.mContext = mContext;
        this.mDate = mDate;
        this.fs = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.timeSlot.setText(mSlotList.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "onClick: clicked on: " + mSlotList.get(position));
                Log.d(TAG, "mDate is: " + mDate);


                final DocumentReference docRef = fs.collection("shifts").document(mDate);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){ // if the date exists
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
        });
    }

    @Override
    public int getItemCount() {
        return mSlotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView timeSlot;
        RelativeLayout parentLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            timeSlot = itemView.findViewById(R.id.signHours);
            parentLayout = itemView.findViewById(R.id.schedule_parent_layout);
        }
    }
}