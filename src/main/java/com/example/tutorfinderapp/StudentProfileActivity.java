package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudentProfileActivity extends AppCompatActivity {
    TextView studentProName, studentProPhn,studentProEmail, studentProLoc, studentProSub, userID;
    Button studentLogoutBtn,searchTutorBtn,techerCnnctBtn,editStudentProfile;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String user_ID;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        studentProName = findViewById(R.id.sProname);
        studentProEmail = findViewById(R.id.sProEmail);
        studentProPhn = findViewById(R.id.sProPhn);
        studentProLoc = findViewById(R.id.sProLoc);
        studentProSub = findViewById(R.id.sProSub);
        userID = findViewById(R.id.userID);
        studentLogoutBtn = findViewById(R.id.sLogout);
        searchTutorBtn = findViewById(R.id.srTtrBtn);
        techerCnnctBtn = findViewById(R.id.cnnctList);
        editStudentProfile = findViewById(R.id.editStudentProfile);
        mAuth = FirebaseAuth.getInstance();




        user_ID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(user_ID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                studentProEmail.setText("hello");
                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();
                String loc = dataSnapshot.child("Location").getValue().toString();
                String uid = dataSnapshot.child("User_ID").getValue().toString();
                studentProName.setText(name);
                studentProEmail.setText(email);
                studentProPhn.setText(phone);
                studentProLoc.setText(loc);
                userID.setText(uid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        studentLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StudentProfileActivity.this, MainActivity.class));
            }
        });

        techerCnnctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StudentProfileActivity.this, STConnectedActivity.class));
            }
        });
        searchTutorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this, TutorSeacrhActivity.class));
            }
        });
        editStudentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentProfileActivity.this, StudentProfileEditActivity.class));
            }
        });
    }
}