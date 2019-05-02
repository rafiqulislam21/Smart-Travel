package com.example.ribijoy.smarttravelguide;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class activity_signup extends AppCompatActivity {

    private TextView linkLogin;
    private Button btnSignup;
    private EditText etName, etEmail, etPass;

    public String email, name, password;

    //---- auth---
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        linkLogin = (TextView) findViewById(R.id.link_login);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);



        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_signup.this, Login.class);
                startActivity(intent);
            }
        });





        //----------auth----------------------------------------
        firebaseAuth = FirebaseAuth.getInstance();



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    String user_email = etEmail.getText().toString().trim();
                    String user_password = etPass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                //sendEmailVerification();
                                //sendUserData();
                                //firebaseAuth.signOut();
                                Toast.makeText(activity_signup.this, "Successfully Registered !", Toast.LENGTH_SHORT).show();
                                //finish();
                                startActivity(new Intent(activity_signup.this, Login.class));
                            }else{
                                Toast.makeText(activity_signup.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }


            private Boolean validate(){
                Boolean result = false;

                name = etName.getText().toString();
                password = etPass.getText().toString();
                email = etEmail.getText().toString();



                if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
                    Toast.makeText(activity_signup.this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                }else{
                    result = true;
                }

                return result;
            }
        });
        //-----------------auth----------------------------



    }


    //-------------------auth-------------------------
  /*  private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_signup.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(activity_signup.this, "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });
        UserProfile userProfile = new UserProfile(age, email, name);
        myRef.setValue(userProfile);
    }
*/
    //-----------------auth------------------------------------------------------------------

    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(activity_signup.this, Login.class);
        startActivity(intent);

    }






}
