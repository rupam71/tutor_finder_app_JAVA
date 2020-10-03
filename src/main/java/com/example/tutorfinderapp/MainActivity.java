package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    EditText emailTxt,passTxt;
    Button lgnBtn, sgnBtnT, sgnBtnS;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.getInstance().signOut();
        emailTxt = findViewById(R.id.lgnEmail);
        passTxt =  findViewById(R.id.lgnPass);
        lgnBtn = findViewById(R.id.lgnBtn);
        sgnBtnT = findViewById(R.id.sgnBtnT);
        sgnBtnS = findViewById(R.id.sgnBtnS);

        sgnBtnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, TeacherSignUp.class);
                startActivity(I);
            }
        });

        sgnBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(MainActivity.this, StudentSignUpActivity.class);
                startActivity(I);
            }
        });

        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = emailTxt.getText().toString();
                String userPaswd = passTxt.getText().toString();
                if (userEmail.isEmpty()) {
                    emailTxt.setError("Provide Your Email first!");
                    emailTxt.requestFocus();

                }else if (userPaswd.isEmpty()) {
                    passTxt.setError("Provide Your Password first!");
                    passTxt.requestFocus();

                }  else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Field Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Not Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference tDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");
                                DatabaseReference sDatabase = FirebaseDatabase.getInstance().getReference().child("Student");

                                tDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())) {
                                            startActivity(new Intent(MainActivity.this, TeacherProfileActivity.class));
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                sDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())) {
                                            startActivity(new Intent(MainActivity.this, StudentProfileActivity.class));
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                    });
                } else {
                }
            }
        });

    }


}