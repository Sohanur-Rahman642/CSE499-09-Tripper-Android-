package com.example.asus.tripper.RegisterAndLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tripper.GuideForgotPass;
import com.example.asus.tripper.MyToursForGuide;
import com.example.asus.tripper.R;
import com.example.asus.tripper.UserDashBoard;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginUser extends AppCompatActivity {

    private Button login_button;
    private EditText login_email, login_password;
    private TextView login_signup, login_forgot_pass;
    private ProgressDialog loadingbar;
    private ImageView login_google_pic;

    private FirebaseAuth mAuth;

    private static final int RC_SIGN_IN=1;
    private GoogleApiClient mGoogleSignInClient;
    private static final String TAG = "LoginUser";

    private Boolean emailAddressChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        login_signup = findViewById(R.id.login_signup);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        login_google_pic=findViewById(R.id.login_google_pic);
        login_forgot_pass=findViewById(R.id.login_forgot_pass);

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

        login_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginUser.this, GuideForgotPass.class);
                startActivity(i);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("273154760847-7tl0a11v8accil7l3vvbfp22grp7hmah.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(LoginUser.this, "Connection to Google Sign In failed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        login_google_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();
            }
        });
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            loadingbar.setTitle("Google Sign In");
            loadingbar.setMessage("Please wait until we are logging using your google account...");
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                Toast.makeText(this, "Please wait while we are getting your auth result...", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(this, "Can't get auth result", Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            SendUserToHome();
                            loadingbar.dismiss();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            String message = task.getException().toString();
                            SendUserToLoginActivity();
                            Toast.makeText(LoginUser.this, "Not authenticated : " + message, Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();

                        }

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
            loadingbar.setCanceledOnTouchOutside(true);
            loadingbar.show();


            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){

                        VerifyEmailAdress();

                        //SendUserToHome();    //it was before work with email verification

                        //Toast.makeText(LoginUser.this, "Logged in successfully!", Toast.LENGTH_SHORT).show(); // it was before work with email verification
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


    private void VerifyEmailAdress(){

        FirebaseUser user = mAuth.getCurrentUser();
        emailAddressChecker = user.isEmailVerified();

        if (emailAddressChecker){

            SendUserToHome();

        }
        else {

            Toast.makeText(this, "Please verify your account first", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    private void SendUserToHome() {

        Intent i = new Intent(LoginUser.this, MyToursForGuide.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void SendUserToLoginActivity() {

        Intent i = new Intent(LoginUser.this, LoginUser.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    private void SendUserToRegisterActivity() {

        Intent registerIntent = new Intent(LoginUser.this, RegisterUser.class);
        startActivity(registerIntent);
    }
}
