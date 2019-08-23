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
import android.widget.Toast;

import com.example.asus.tripper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterUser extends AppCompatActivity {

    private EditText register_emailad,  register_pass, register_pass_confirm;
    private Button register_button7;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        loadingbar = new ProgressDialog(this);

        mAuth =FirebaseAuth.getInstance();

        //register_fullname=findViewById(R.id.register_fullname);
        register_emailad=findViewById(R.id.register_emailad);
        //register_phone=findViewById(R.id.register_phone);
        register_pass=findViewById(R.id.register_pass);
        register_pass_confirm=findViewById(R.id.register_pass_confirm);
        //register_address=findViewById(R.id.register_address);
        register_button7=findViewById(R.id.register_button7);

        register_button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateNewAccount();
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

    private void CreateNewAccount() {

        String email= register_emailad.getText().toString();
        String password= register_pass.getText().toString();
        String confirmPassword= register_pass_confirm.getText().toString();
        //String fullname= register_fullname.getText().toString();
        //String phone= register_phone.getText().toString();
        //String address= register_address.getText().toString();

       /* if(TextUtils.isEmpty(fullname)){

            Toast.makeText(this, "Please Enter Your Fullname", Toast.LENGTH_SHORT).show();
        }*/
        if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
        }
       /* else if(TextUtils.isEmpty(address)){

            Toast.makeText(this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();
        }*/

       /* else if(TextUtils.isEmpty(phone)){

            Toast.makeText(this, "Please Enter Your Phone number", Toast.LENGTH_SHORT).show();
        }*/
        else if(TextUtils.isEmpty(password)){

            Toast.makeText(this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(confirmPassword)){

            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_SHORT).show();
        }



        else if(!password.equals(confirmPassword)){

            Toast.makeText(this, "Password did not match!", Toast.LENGTH_SHORT).show();
        }
        else{

            loadingbar.setTitle("Creating a new account");
            loadingbar.setMessage("Please wait until we are creating new account...");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        SendEmailVerificationMessage();

                        //SendUserToHome();

                        //Toast.makeText(RegisterUser.this, "Account is created successfully! ", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                    else {

                        String message = task.getException().getMessage();
                        Toast.makeText(RegisterUser.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }

    }


    private void SendEmailVerificationMessage(){

        FirebaseUser user = mAuth.getCurrentUser();

        if (user!= null){

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(RegisterUser.this, "Registration successful. Please check your mail and verify your account", Toast.LENGTH_SHORT).show();
                        SendUserToHome();
                        mAuth.signOut();
                    }

                    else {

                        String error = task.getException().getMessage();
                        Toast.makeText(RegisterUser.this, "Error: "+ error, Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }

                }
            });
        }
    }

    private void SendUserToHome() {

        Intent intent = new Intent(RegisterUser.this, LoginUser.class);   //it was MytoursForGuide.class before
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
