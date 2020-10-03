package com.example.tutorfinderapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfileEditActivity extends AppCompatActivity {
    EditText editTeacherName, editTeacherEmail, editStudentPassword, editTeacherPhone, editTeacherLocation;
    String user_ID;
    Button editTeacherButton;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private ProgressDialog mPro;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_edit);
        editTeacherName = findViewById(R.id.editTeacherName);
        editTeacherEmail = findViewById(R.id.editTeacherEmail);
        editTeacherLocation = findViewById(R.id.editTeacherLocation);
        editTeacherPhone = findViewById(R.id.editTeacherPhone);
        editTeacherButton = findViewById(R.id.editTeacherButton);


        mAuth = FirebaseAuth.getInstance();




        user_ID = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher").child(user_ID);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String name = dataSnapshot.child("Name").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String phone = dataSnapshot.child("Phone").getValue().toString();
                String loc = dataSnapshot.child("Location").getValue().toString();
                String uid = dataSnapshot.child("User_ID").getValue().toString();
                editTeacherName.setText(name);
                editTeacherEmail.setText(email);
                editTeacherPhone.setText(phone);
                editTeacherLocation.setText(loc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mPro = new ProgressDialog(this);
        editTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = editTeacherEmail.getText().toString();
                final String phone = editTeacherPhone.getText().toString();
                final String name = editTeacherName.getText().toString();
                final String location = editTeacherLocation.getText().toString();
                if (emailID.isEmpty()) {
                    editTeacherEmail.setError("Provide Your Email First!");
                    editTeacherEmail.requestFocus();
                } else if (phone.isEmpty()) {
                    editTeacherPhone.setError("Provide Your Phone Number First!");
                    editTeacherPhone.requestFocus();
                }  else if (name.isEmpty()) {
                    editTeacherName.setError("Provide Your name First!");
                    editTeacherName.requestFocus();
                } else if (location.isEmpty()) {
                    editTeacherLocation.setError("Provide Your Location First!");
                    editTeacherLocation.requestFocus();
                } else if (emailID.isEmpty()) {
                    Toast.makeText(TeacherProfileEditActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty())) {

                    mPro.setMessage("Edit Profile");
                    mPro.show();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(emailID)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(TeacherProfileEditActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                    mDatabase.child("Name").setValue(name);
                    mDatabase.child("Email").setValue(emailID);
                    mDatabase.child("Phone").setValue(phone);
                    mDatabase.child("Location").setValue(location);

                    startActivity(new Intent(TeacherProfileEditActivity.this, TeacherProfileActivity.class));

                } else {
                    Toast.makeText(TeacherProfileEditActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
