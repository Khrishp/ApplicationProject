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
import android.widget.Toast;

import com.example.applicationproject.Objects.Shifts;
import com.example.applicationproject.Objects.User;
import com.example.applicationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * This is a Recycler View Adapter for inside the SignUp Activity. It displays the free hours that can be volunteered for.
 * Clicking on a free hour will display a toast, add the hour to your hours count, and make the hour not free for
 * anyone to sign up for anymore.
 */
public class ShiftViewAdapter extends RecyclerView.Adapter<ShiftViewAdapter.ViewHolder>{

    private static final String TAG = "ShiftViewAdapter";

    private ArrayList<String> mSlotList = new ArrayList<>();
    private Context mContext;
    private String mDate;
    private FirebaseFirestore fs;

    public ShiftViewAdapter(Context mContext, ArrayList<String> mSlotList, String mDate) {
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

                final FirebaseAuth mAuth = FirebaseAuth.getInstance();


                final DocumentReference docRef = fs.collection("shifts").document(mDate);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){ // if the date exists
                                Log.d(TAG, "this is the document data: " + document.getData());
                                Shifts data = document.toObject(Shifts.class);
                                if(data.getShifts().contains(mSlotList.get(position))) // if the document has the string
                                {
                                    Log.d(TAG, "This shift already exists! mSlotList: " + mSlotList.get(position));
                                } else { // then create a new shift string
                                    data.getShifts().add(mSlotList.get(position));
                                    docRef.set(data);
                                    Toast.makeText(mContext, "Signed up for shift: " + mSlotList.get(position), Toast.LENGTH_SHORT).show();
                                    final DocumentReference doc = fs.collection("users").document(mAuth.getCurrentUser().getEmail());
                                    doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                Log.d(TAG, "task successful");
                                                DocumentSnapshot documentAcc = task.getResult();
                                                if (documentAcc.exists()) {
                                                    User user = documentAcc.toObject(User.class);
                                                    user.setHoursCount(user.getHoursCount() + 1);
                                                    doc.set(user);
                                                }
                                            }
                                        }
                                    });
                                }
                            } else { // than create a new date object
                                Log.d(TAG, "No such Document");
                                docRef.set(new Shifts(mSlotList.get(position)));
                                Toast.makeText(mContext, "Signed up for shift: " + mSlotList.get(position), Toast.LENGTH_SHORT).show();
                                final DocumentReference doc = fs.collection("users").document(mAuth.getCurrentUser().getEmail());
                                doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "task successful");
                                            DocumentSnapshot documentAcc = task.getResult();
                                            if (documentAcc.exists()) {
                                                User user = documentAcc.toObject(User.class);
                                                user.setHoursCount(user.getHoursCount() + 1);
                                                doc.set(user);
                                            }
                                        }
                                    }
                                });
                            }
                        } else{
                            Log.d(TAG, "Get failed with ", task.getException());
                            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
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