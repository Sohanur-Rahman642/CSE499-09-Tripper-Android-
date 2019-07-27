package com.example.tourguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginForUser extends AppCompatActivity {


    private EditText user_login_email;
    private EditText user_login_password;
    private Button user_login_button;
    private TextView user_signup;
    private ImageView user_fb_pic;
    private ImageView user_twitter_pic;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView user_forgot_pass;
    private void init1(){
        user_signup=(TextView) findViewById(R.id.user_signup);
        user_signup.setOnClickListener(new View.OnClickListener(){

                                       @Override
                                       public void onClick(View v) {
                                           Intent s=new Intent(LoginForUser.this, Signup.class);
                                           startActivity(s);
                                       }
                                   }
        );
    }

    /*private LoginButton fb_login_button;
    private CircleImageView user_profile_pic;
    private TextView user_profile_name, user_profile_email, user_profile_phone;

    private CallbackManager callbackManager;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_for_user);
        init1();

        user_login_email=(EditText)findViewById(R.id.user_login_email);
        user_login_password=(EditText)findViewById(R.id.user_login_password);
        user_login_button=(Button)findViewById(R.id.user_login_button);
        user_forgot_pass=(TextView)findViewById(R.id.user_forgot_pass);
        user_fb_pic=findViewById(R.id.user_fb_pic);
        user_twitter_pic=findViewById(R.id.user_twitter_pic);

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            finish();
            startActivity(new Intent(LoginForUser.this, HomeForUser.class));
        }
        user_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(user_login_email.getText().toString(),user_login_password.getText().toString());
            }
        });
        user_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginForUser.this, UserForgotPass.class));
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
                    Toast.makeText(LoginForUser.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginForUser.this, HomeForUser.class));
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginForUser.this, "Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*fb_login_button=findViewById(R.id.fb_login_button);
        user_profile_email=findViewById(R.id.user_profile_email);
        user_profile_name=findViewById(R.id.user_profile_name);
        user_profile_phone=findViewById(R.id.user_profile_phone);
        user_profile_pic=findViewById(R.id.user_profile_pic);

        callbackManager= CallbackManager.Factory.create();
        fb_login_button.setReadPermissions(Arrays.asList("email","public_profile"));
        checkLoginStatus();

        fb_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker= new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if(currentAccessToken==null){

                user_profile_name.setText("");
                user_profile_email.setText("");
                //user_profile_phone.setText("");
                user_profile_pic.setImageResource(0);
                Toast.makeText(LoginForUser.this, "User Logged Out", Toast.LENGTH_LONG).show();
            }
            else
                loadUserProfile(currentAccessToken);

        }
    };

    private void loadUserProfile(AccessToken newAccessToken){

        GraphRequest request= GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    String first_name= object.getString("first_name");
                    String last_name= object.getString("last_name");
                    String email= object.getString("email");
                    //String phone= object.getString("phone");
                    String id= object.getString("id");

                    String image_url = "https://graph.facebook.com/"+id+ "/picture?type=normal";


                    user_profile_email.setText(email);
                    user_profile_name.setText(first_name +" "+last_name);
                    //user_profile_phone.setText(phone);
                    RequestOptions requestOptions= new RequestOptions();
                    requestOptions.dontAnimate();

                    Glide.with(LoginForUser.this).load(image_url).into(user_profile_pic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void checkLoginStatus(){

        if(AccessToken.getCurrentAccessToken()!=null){

            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }*/
    }

}
