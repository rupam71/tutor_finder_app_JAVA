package com.example.tutorfinderapp;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentSignUpActivity extends AppCompatActivity {
    EditText studentName, studentEmail, studentPass, studentPhn, studentLocation;
    String subject;
    Button studentSignUpBtn;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog mPro;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);
        studentName = findViewById(R.id.sgnNameS);
        studentEmail = findViewById(R.id.sgnEmailS);
        studentPass = findViewById(R.id.sgnPassS);
        studentLocation = findViewById(R.id.sgnLocationS);
        studentPhn = findViewById(R.id.sgnPhnS);
        studentSignUpBtn = findViewById(R.id.sSgnBtn);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");

        mPro = new ProgressDialog(this);
        studentSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = studentEmail.getText().toString();
                String paswd = studentPass.getText().toString();
                final String phone = studentPhn.getText().toString();
                final String name = studentName.getText().toString();
                final String location = studentLocation.getText().toString();
                if (emailID.isEmpty() && paswd.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty()) {
                    Toast.makeText(StudentSignUpActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                }   else if (name.isEmpty()) {
                    studentName.setError("Provide Your Name First!");
                    studentName.requestFocus();
                } else if (emailID.isEmpty()) {
                    studentEmail.setError("Provide Your Email First!");
                    studentEmail.requestFocus();
                } else if (paswd.isEmpty()) {
                    studentPass.setError("Provide Your Password First!");
                    studentPass.requestFocus();
                }else if (phone.isEmpty()) {
                    studentPhn.setError("Provide Your Phone Number First!");
                    studentPhn.requestFocus();
                } else if (location.isEmpty()) {
                    studentLocation.setError("Provide Your Location First!");
                    studentLocation.requestFocus();
                }   else if (!(emailID.isEmpty() && paswd.isEmpty() && phone.isEmpty() && name.isEmpty() && location.isEmpty())) {

                    mPro.setMessage("Signing Up");
                    mPro.show();

                    mAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener
                            (StudentSignUpActivity.this, new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {



                                        Toast.makeText(StudentSignUpActivity.this.getApplicationContext(),
                                                "SignUp unsuccessful :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                        mPro.dismiss();

                                    } else {
                                        String user_ID = mAuth.getCurrentUser().getUid();
                                        DatabaseReference current_user_DB = mDatabase.child(user_ID);

                                        current_user_DB.child("Name").setValue(name);
                                        current_user_DB.child("Email").setValue(emailID);
                                        current_user_DB.child("Phone").setValue(phone);
                                        current_user_DB.child("Location").setValue(location);
                                        current_user_DB.child("User_ID").setValue(user_ID);
                                        current_user_DB.child("Image").setValue("defult");

                                        mPro.dismiss();

                                        Intent I = new Intent(StudentSignUpActivity.this, StudentProfileActivity.class);
                                        startActivity(I);
                                    }
                                }
                            });
                } else {
                    Toast.makeText(StudentSignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}