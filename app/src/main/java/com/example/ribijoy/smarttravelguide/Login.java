package com.example.ribijoy.smarttravelguide;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;
public class Login extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    private EditText etEmail, etPass;
    private Button btnLogin, btnSignup, btnInfo;

    private String email, pass;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private int counter = 5;
    private TextView Info;

   // FirebaseAuth mAuth;

    //String codeSent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        btnInfo = (Button) findViewById(R.id.btnLmore);
        Info = (TextView) findViewById(R.id.tvInfo);


        //----------auth-----------------------

        FirebaseUser user = null;

        Info.setText("No of attempts remaining: 5");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(Login.this, activity_home.class));
        }

        //Login button----------------------------------------------------
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(email.isEmpty() || pass.isEmpty()){
                    Toast.makeText(Login.this, "Enter Email and password", Toast.LENGTH_SHORT).show();
                }else{
                    validate(etEmail.getText().toString(), etPass.getText().toString());
                }

            }
        });
        //--------------auth-----------------------



        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Pop .class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, activity_signup.class);
                startActivity(intent);
            }
        });


    /*    btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phnNum = etEmail.getText().toString();
                //if (phnNum.length()<1 || phnNum.length()!=11){
                  //  etPhone.setError("Enter Valid Phone Number!");
                    //etPhone.setText("");
                    //etPhone.requestFocus();
                //}
                //else{
                  //  etPhone.setText("login Successfull");

                    // open activity
                Intent intent = new Intent(Login.this, activity_home.class);
                startActivity(intent);
                //}

            }


        });
*/

    }




    //----------auth----------------------------------------------------------------------
    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("Please wait until you are verified!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, activity_home.class));
                    //checkEmailVerification();
                } else {
                    Toast.makeText(Login.this, "Email or Password is incorrect, Login Failed!", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if (counter == 0) {
                        btnLogin.setEnabled(false);
                    }
                }
            }
        });

        //-----------------auth-----------------------------------------------------------------------------
        //back press exit


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {


        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            //
            finishAffinity();
            System.exit(0);

            //getActivity().finish();
            //System.exit(0);
        } else {
            backToast = Toast.makeText(getBaseContext(),"Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
