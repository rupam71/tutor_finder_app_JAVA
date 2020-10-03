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

public class TeacherProfileActivity extends AppCompatActivity {
    TextView teacherProName, teacherProPhn,teacherProEmail, teacherProLoc, teacherProSub, userID;
    Button teacherLogoutBtn,reqBtn,cnnctStudent, editTeacherProfile;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String user_ID;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        teacherProName = findViewById(R.id.tProname);
        teacherProEmail = findViewById(R.id.tProEmail);
        teacherProPhn = findViewById(R.id.tProPhn);
        teacherProLoc = findViewById(R.id.tProLoc);
        teacherProSub = findViewById(R.id.tProSub);
        userID = findViewById(R.id.userID);
        teacherLogoutBtn = findViewById(R.id.tLogout);
        reqBtn = findViewById(R.id.req);
        cnnctStudent = findViewById(R.id.cnctListBtn);
        editTeacherProfile = findViewById(R.id.editTeacherProfile);
        mAuth = FirebaseAuth.getInstance();




        user_ID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher").child(user_ID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();
                String sub = dataSnapshot.child("Subject").getValue().toString();
                String loc = dataSnapshot.child("Location").getValue().toString();
                String uid = dataSnapshot.child("User_ID").getValue().toString();
                teacherProName.setText(name);
                teacherProEmail.setText(email);
                teacherProPhn.setText(phone);
                teacherProLoc.setText(loc);
                teacherProSub.setText(sub);
                userID.setText(uid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TeacherProfileActivity.this, TeacherReqActivity.class));
            }
        });
        cnnctStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TeacherProfileActivity.this, TSConnectActivity2.class));
            }
        });


        teacherLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(TeacherProfileActivity.this, MainActivity.class));
            }
        });

        editTeacherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherProfileActivity.this, TeacherProfileEditActivity.class));
            }
        });
    }
}