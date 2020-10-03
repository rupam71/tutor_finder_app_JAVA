package com.example.tutorfinderapp;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentToTeacherProfileActivity extends AppCompatActivity {
    TextView teacherName,teacherEmail,teacherLoc,teacherSub,teacherPhn;
    Button sTBtn,callBtn;
    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    String name , email, phone, loc,  uid;
    String tname , temail, tphone, tsub,tloc,  tuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_to_teacher_profile);
        teacherName = findViewById(R.id.stProname);
        teacherEmail = findViewById(R.id.stProEmail);
        teacherPhn = findViewById(R.id.stProPhn);
        teacherLoc = findViewById(R.id.stProLoc);
        teacherSub = findViewById(R.id.stProSub);
        sTBtn =findViewById(R.id.sTBtn);
        callBtn =findViewById(R.id.callBtn);

        final String id = getIntent().getStringExtra("ID");
        teacherName.setText(id);

        mAuth = FirebaseAuth.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher").child(id);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 tname = dataSnapshot.child("Name").getValue().toString();
                 temail = dataSnapshot.child("Email").getValue().toString();
                 tphone = dataSnapshot.child("Phone").getValue().toString();
                 tsub = dataSnapshot.child("Subject").getValue().toString();
                 tloc = dataSnapshot.child("Location").getValue().toString();
                teacherName.setText(tname);
                teacherEmail.setText(temail);
                teacherPhn.setText(tphone);
                teacherLoc.setText(tloc);
                teacherSub.setText(tsub);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        uid = mAuth.getCurrentUser().getUid();
        final DatabaseReference sDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(uid);

        sDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("Name").getValue().toString();
                email = dataSnapshot.child("Email").getValue().toString();
                phone = dataSnapshot.child("Phone").getValue().toString();
                loc = dataSnapshot.child("Location").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("Connect").child(mAuth.getCurrentUser().getUid());
        tDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(id)) {

                    sTBtn.setText("Connected");
                }else{
                    DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("Request").child(mAuth.getCurrentUser().getUid());
                    tDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(id)) {

                                sTBtn.setText("Request Sent");
                            }else{
                                sTBtn.setText("Send Request");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    sTBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                         DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("Connect").child(mAuth.getCurrentUser().getUid());
                        tDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(id)) {

                                    Toast.makeText(StudentToTeacherProfileActivity.this, "Friend", Toast.LENGTH_SHORT).show();
                                    sTBtn.setText("Connected");


                                }else{
                                   DatabaseReference DB= FirebaseDatabase.getInstance().getReference().child("Request").child(mAuth.getCurrentUser().getUid()).child(id);
                                   final DatabaseReference SB= FirebaseDatabase.getInstance().getReference().child("Request").child(id).child(mAuth.getCurrentUser().getUid());

                                                /*DB.child("Status").setValue("Sent");*/
                                                DB.child("User_ID").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            SB.child("User_ID").setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){

                                                                        sTBtn.setText("Request Sent");
                                                                        /*SB.child("Status").setValue("Receive");*/
                                                                        Toast.makeText(StudentToTeacherProfileActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }

                        });



                    }


    });

    callBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String s= "tel:"+ tphone;
            Intent in = new Intent(Intent.ACTION_CALL);
            in.setData(Uri.parse(s));
            startActivity(in);
        }
    });

    }
}