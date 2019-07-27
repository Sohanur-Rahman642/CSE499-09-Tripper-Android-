package com.example.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginForGuide extends AppCompatActivity {

    private EditText guide_login_email;
    private EditText guide_login_password;
    private Button guide_login_button;
    private TextView guide_signup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView guide_forgot_pass;
    private void init1(){
        guide_signup=(TextView) findViewById(R.id.guide_signup);
        guide_signup.setOnClickListener(new View.OnClickListener(){

                                           @Override
                                           public void onClick(View v) {
                                               Intent s=new Intent(LoginForGuide.this, Signup.class);
                                               startActivity(s);
                                           }
                                       }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_guide);
        init1();

        guide_login_email=(EditText)findViewById(R.id.guide_login_email);
        guide_login_password=(EditText)findViewById(R.id.guide_login_password);
        guide_login_button=(Button)findViewById(R.id.guide_login_button);
        guide_forgot_pass=(TextView)findViewById(R.id.guide_forgot_pass);


        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            finish();
            startActivity(new Intent(LoginForGuide.this, MyToursForGuide.class));
        }
        guide_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(guide_login_email.getText().toString(),guide_login_password.getText().toString());
            }
        });
        guide_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginForGuide.this, UserForgotPass.class));
            }
        });
    }
    private void validate(String email, String password){
        progressDialog.setMessage("Logging In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginForGuide.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginForGuide.this, MyToursForGuide.class));
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginForGuide.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
