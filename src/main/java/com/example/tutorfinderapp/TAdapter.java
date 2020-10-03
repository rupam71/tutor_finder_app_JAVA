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

public class TAdapter extends FirebaseRecyclerAdapter<ModelClassT, TAdapter.myviewholder> {
    Context context;
    public TAdapter(@NonNull FirebaseRecyclerOptions<ModelClassT> options, Context context) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public TAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_list_layout, parent,false);
        return new TAdapter.myviewholder(view);
    }
    class myviewholder extends RecyclerView.ViewHolder{
        TextView userName;
        View view;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameS);
            view = itemView;
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull final TAdapter.myviewholder holder, int position, @NonNull final ModelClassT model) {
        final String userId = model.getUser_ID();
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,TeacherToStudentActivity.class);
                in.putExtra("ID",userId);
                context.startActivity(in);
            }
        });
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Student").child(userId);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                holder.userName.setText(snapshot.child("Name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
