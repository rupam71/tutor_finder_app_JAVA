package com.example.tutorfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TeacherSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText teacherName, teacherEmail, teacherPass, teacherPhn, teacherLocation;
    String subject;
    Button teacherSignUpBtn;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mPro;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_sign_up);
        teacherName = findViewById(R.id.sgnNameT);
        teacherEmail = findViewById(R.id.sgnEmailT);
        teacherPass = findViewById(R.id.sgnPassT);
        teacherLocation = findViewById(R.id.sgnLocationT);
        teacherPhn = findViewById(R.id.sgnPhnT);
        teacherSignUpBtn = findViewById(R.id.tSgnBtn);

        Spinner spinner1 = findViewById(R.id.sgnSubjectT);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sgnSubjectT, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);




        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Teacher");

        mPro = new ProgressDialog(this);
        teacherSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = teacherEmail.getText().toString();
                String paswd = teacherPass.getText().toString();
                final String phone = teacherPhn.getText().toString();
                final String name = teacherName.getText().toString();
                final String location = teacherLocation.getText().toString();
                if (name.isEmpty()) {
                    teacherName.setError("Provide Your Name First!");
                    teacherName.requestFocus();
                } else if (emailID.isEmpty()) {
                    teacherEmail.setError("Provide Your Email First!");
                    teacherEmail.requestFocus();
                } else if (paswd.isEmpty()) {
                    teacherPass.setError("Provide Your Password First!");
                    teacherPass.requestFocus();
                }  else if (phone.isEmpty()) {
                    teacherPhn.setError("Provide Your Phone Number First!");
                    teacherPhn.requestFocus();
                } else if (location.isEmpty()) {
                    teacherLocation.setError("Provide Your Location First!");
                    teacherLocation.requestFocus();
                } else if (subject == null) {
                    Toast.makeText(TeacherSignUp.this, "Select Subject", Toast.LENGTH_SHORT).show();
                } else if (emailID.isEmpty() && paswd.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty() && subject == null) {
                    Toast.makeText(TeacherSignUp.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(emailID.isEmpty() && paswd.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty() && subject == null )) {

                    mPro.setMessage("Signing Up");
                    mPro.show();

                    mAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener
                            (TeacherSignUp.this, new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {



                                        Toast.makeText(TeacherSignUp.this.getApplicationContext(),
                                                "SignUp unsuccessful :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        mPro.dismiss();

                                    } else {
                                        String user_ID = mAuth.getCurrentUser().getUid();
                                        DatabaseReference current_user_DB = mDatabase.child(user_ID);

                                        current_user_DB.child("Name").setValue(name);
                                        current_user_DB.child("Email").setValue(emailID);
                                        current_user_DB.child("Phone").setValue(phone);
                                        current_user_DB.child("Location").setValue(location);
                                        current_user_DB.child("Subject").setValue(subject);
                                        current_user_DB.child("User_ID").setValue(user_ID);
                                        current_user_DB.child("Image").setValue("defult");

                                        mPro.dismiss();

                                        Intent I = new Intent(TeacherSignUp.this, TeacherProfileActivity.class);
                                        startActivity(I);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(TeacherSignUp.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        subject = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        subject = null;
    }
}