package com.example.tutorfinderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SAdapter extends FirebaseRecyclerAdapter<ModelClass, SAdapter.myviewholder> {
    Context context;
    public SAdapter(@NonNull FirebaseRecyclerOptions<ModelClass> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public SAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent,false);
        return new SAdapter.myviewholder(view);
    }
    class myviewholder extends RecyclerView.ViewHolder{
        TextView userName,userSub,userId;
        View view;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userSub = itemView.findViewById(R.id.userSub);
            view = itemView;
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull final SAdapter.myviewholder holder, int position, @NonNull final ModelClass model) {
        final String userId = model.getUser_ID();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,StudentToTeacherProfileActivity.class);
                in.putExtra("ID",userId);
                context.startActivity(in);
            }
        });
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Teacher").child(userId);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                holder.userName.setText(snapshot.child("Name").getValue().toString());/*
                holder.userSub.setText(snapshot.child("Subject").getValue().toString());*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
