package com.example.tutorfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherReqActivity extends AppCompatActivity  {
    Button searchBtn;
    RecyclerView recview;
    String sub;
    TAdapter adapter;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_req);
        DatabaseReference mDatabase;
        mAuth = FirebaseAuth.getInstance();




        recview = findViewById(R.id.reqRes);
        recview.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Request").child(mAuth.getCurrentUser().getUid());
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