package com.example.tutorfinderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileEditActivity extends AppCompatActivity {
    EditText editStudentName, editStudentEmail, editStudentPassword, editStudentPhone, editStudentLocation;
    String user_ID;
    Button editStudentButton;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private ProgressDialog mPro;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile_edit);
        editStudentName = findViewById(R.id.editStudentName);
        editStudentEmail = findViewById(R.id.editStudentEmail);
        editStudentPassword = findViewById(R.id.editStudentPassword);
        editStudentLocation = findViewById(R.id.editStudentLocation);
        editStudentPhone = findViewById(R.id.editStudentPhone);
        editStudentButton = findViewById(R.id.editStudentButton);


        mAuth = FirebaseAuth.getInstance();




        user_ID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student").child(user_ID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();
                String loc = dataSnapshot.child("Location").getValue().toString();
                String uid = dataSnapshot.child("User_ID").getValue().toString();
                editStudentName.setText(name);
                editStudentEmail.setText(email);
                editStudentPhone.setText(phone);
                editStudentLocation.setText(loc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mPro = new ProgressDialog(this);
        editStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = editStudentEmail.getText().toString();
                final String phone = editStudentPhone.getText().toString();
                final String name = editStudentName.getText().toString();
                final String location = editStudentLocation.getText().toString();
                if (emailID.isEmpty()) {
                    editStudentEmail.setError("Provide Your Email First!");
                    editStudentEmail.requestFocus();
                } else if (phone.isEmpty()) {
                    editStudentPhone.setError("Provide Your Phone Number First!");
                    editStudentPhone.requestFocus();
                }  else if (name.isEmpty()) {
                    editStudentName.setError("Provide Your name First!");
                    editStudentName.requestFocus();
                } else if (location.isEmpty()) {
                    editStudentLocation.setError("Provide Your Location First!");
                    editStudentLocation.requestFocus();
                } else if (emailID.isEmpty()) {
                    Toast.makeText(StudentProfileEditActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty())) {

                    mPro.setMessage("Edit Profile");
                    mPro.show();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(emailID)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(StudentProfileEditActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                    mDatabase.child("Name").setValue(name);
                    mDatabase.child("Email").setValue(emailID);
                    mDatabase.child("Phone").setValue(phone);
                    mDatabase.child("Location").setValue(location);

                    startActivity(new Intent(StudentProfileEditActivity.this, StudentProfileActivity.class));

                } else {
                    Toast.makeText(StudentProfileEditActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}