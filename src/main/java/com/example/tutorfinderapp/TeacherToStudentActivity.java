package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class TeacherToStudentActivity extends AppCompatActivity {
    TextView StudentName,StudentEmail,StudentLoc,StudentSub,StudentPhn;
    Button acptBtn;
            FirebaseAuth mAuth;
private DatabaseReference mDatabase;
        String name , email, phone, loc, sub, uid;
        String sname , semail, sphone, ssub,sloc,  suid;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_to_student);
        StudentName = findViewById(R.id.tsProname);
        StudentEmail = findViewById(R.id.tsProEmail);
        StudentPhn = findViewById(R.id.tsProPhn);
        StudentLoc = findViewById(R.id.tsProLoc);
        acptBtn =findViewById(R.id.acptBtn);

final String id = getIntent().getStringExtra("ID");
    StudentName.setText(id);

        mAuth = FirebaseAuth.getInstance();
final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(id);

        mDatabase.addValueEventListener(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        sname = dataSnapshot.child("Name").getValue().toString();
        semail = dataSnapshot.child("Email").getValue().toString();
        sphone = dataSnapshot.child("Phone").getValue().toString();
        sloc = dataSnapshot.child("Location").getValue().toString();
    StudentName.setText(sname);
    StudentEmail.setText(semail);
    StudentPhn.setText(sphone);
    StudentLoc.setText(sloc);
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {

        }

        });

        uid = mAuth.getCurrentUser().getUid();
        final DatabaseReference sDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher").child(uid);

        sDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        name = dataSnapshot.child("Name").getValue().toString();
        email = dataSnapshot.child("Email").getValue().toString();
        phone = dataSnapshot.child("Phone").getValue().toString();
        sub = dataSnapshot.child("Subject").getValue().toString();
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

        acptBtn.setText("Connected");
    } else {

        acptBtn.setText("Accept Request");
    }
}
@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });

        acptBtn.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
                        DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("Connect").child(mAuth.getCurrentUser().getUid());
                        tDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(id)) {

                        Toast.makeText(TeacherToStudentActivity.this, "Friend", Toast.LENGTH_SHORT).show();
                        acptBtn.setText("Connected");


                        }else{
                        DatabaseReference DB= FirebaseDatabase.getInstance().getReference().child("Connect").child(mAuth.getCurrentUser().getUid()).child(id);
                        final DatabaseReference SB= FirebaseDatabase.getInstance().getReference().child("Connect").child(id).child(mAuth.getCurrentUser().getUid());

                        DB.child("User_ID").setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    SB.child("User_ID").setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                FirebaseDatabase.getInstance().getReference().child("Request").child(id).child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            FirebaseDatabase.getInstance().getReference().child("Request").child(uid).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){

                                                                        Toast.makeText(TeacherToStudentActivity.this, "Connected", Toast.LENGTH_SHORT).show();

                                                                        acptBtn.setText("Connected");
                                                                    }else {

                                                                        FirebaseDatabase.getInstance().getReference().child("Request").child(uid).child(id).removeValue();
                                                                    }
                                                                }
                                                            });
                                                        }else{
                                                            FirebaseDatabase.getInstance().getReference().child("Request").child(id).child(uid).removeValue();
                                                        }
                                                    }
                                                });
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

        }
        }