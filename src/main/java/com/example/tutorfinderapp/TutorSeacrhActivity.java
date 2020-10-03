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
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TutorSeacrhActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText searchLoc;
    Button searchBtn;
    RecyclerView recview;
    String sub;
    MyAdapter adapter1;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_seacrh);
        searchBtn = findViewById(R.id.srSubBtn);
        DatabaseReference mDatabase;
        mAuth = FirebaseAuth.getInstance();

        Spinner spinner1 = findViewById(R.id.srSub);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.srSub, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sub != null )
                {processSearch(sub);}
                else {

                    Toast.makeText(TutorSeacrhActivity.this, "Select Subject First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        recview = findViewById(R.id.srRes);
        recview.setLayoutManager(new LinearLayoutManager(this));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");
        FirebaseRecyclerOptions<ModelClass> options =
                new FirebaseRecyclerOptions.Builder<ModelClass>()
                        .setQuery(mDatabase,ModelClass.class).build();
        adapter1 = new MyAdapter(options,getApplicationContext());
        recview.setAdapter(adapter1);
    }

    private void processSearch(String sub) {
        FirebaseRecyclerOptions<ModelClass> options =
                new FirebaseRecyclerOptions.Builder<ModelClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Teacher").orderByChild("Subject").startAt(sub).endAt(sub+"\uf8ff"),ModelClass.class).build();
        adapter1 = new MyAdapter(options,getApplicationContext());
        adapter1.startListening();
        recview.setAdapter(adapter1);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sub = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        sub = null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter1.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter1.stopListening();
    }

}