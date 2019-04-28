package com.example.asus.tripper.RegisterAndLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tripper.MyToursForGuide;
import com.example.asus.tripper.R;
import com.example.asus.tripper.UserDashBoard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginUser extends AppCompatActivity {

    private Button login_button;
    private EditText login_email, login_password;
    private TextView login_signup;
    private ProgressDialog loadingbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        login_signup = findViewById(R.id.login_signup);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);

        loadingbar = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendUserToRegisterActivity();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllowingUserToLogin();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= mAuth.getCurrentUser();

        if(currentUser!=null){

            SendUserToHome();
        }
    }

    private void AllowingUserToLogin() {

        String email = login_email.getText().toString();
        String password = login_password.getText().toString();

        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }

        else {

            loadingbar.setTitle("Login");
            loadingbar.setMessage("Please wait until we are logging into your account...");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        SendUserToHome();

                        Toast.makeText(LoginUser.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                    else {

                        String message = task.getException().getMessage();
                        Toast.makeText(LoginUser.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void SendUserToHome() {

        Intent i = new Intent(LoginUser.this, UserDashBoard.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void SendUserToRegisterActivity() {

        Intent registerIntent = new Intent(LoginUser.this, RegisterUser.class);
        startActivity(registerIntent);
        finish();

    }
}
