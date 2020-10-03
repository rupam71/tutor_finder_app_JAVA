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

public class MyAdapter extends FirebaseRecyclerAdapter<ModelClass, MyAdapter.myviewholder> {
    Context context;
    public MyAdapter(@NonNull FirebaseRecyclerOptions<ModelClass> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent,false);
        return new myviewholder(view);
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
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ModelClass model) {
        final String userId = model.getUser_ID();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,StudentToTeacherProfileActivity.class);
                in.putExtra("ID",userId);
                context.startActivity(in);
            }
        });
        holder.userName.setText(model.getName());
        holder.userSub.setText(model.getSubject());
    }


}
