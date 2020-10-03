package com.example.tutorfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TSConnectActivity2 extends AppCompatActivity {


    RecyclerView recview;
    TAdapter adapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_s_connect2);
        DatabaseReference mDatabase;
        mAuth = FirebaseAuth.getInstance();


 recview = findViewById(R.id.cnnctList);
        recview.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Connect").child(mAuth.getCurrentUser().getUid());
        FirebaseRecyclerOptions<ModelClassT> options =
                new FirebaseRecyclerOptions.Builder<ModelClassT>()
                        .setQuery(mDatabase,ModelClassT.class).build();
        adapter = new TAdapter(options,getApplicationContext());
        recview.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
