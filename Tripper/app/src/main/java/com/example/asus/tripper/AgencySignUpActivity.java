package com.example.asus.tripper;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.example.asus.tripper.RegisterAndLogin.RegisterUser;
import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgencySignUpActivity extends AppCompatActivity {

    private EditText channel_fullname,channel_email, channel_location, channel_bl, channel_passport ,channel_phone;
    Button channel_add_btn_firebase;
    private FirebaseAuth firebaseAuth;


    private ImageView channel_profilepicture;

    private FirebaseAuth mAuth;
    private DatabaseReference agencyRef;
    private StorageReference AgencyProfileImageRef;

    private Uri ImageUri;

    private ProgressDialog loadingbar;

    String currentUserId;
    final static int Gallery_Pick = 1;



   /* private void init1() {
        guide_button7 = (Button) findViewById(R.id.guide_button7);
        guide_button7.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {
                                                 Intent s = new Intent(SignupForGuide.this, MainDashBoard.class);
                                                 startActivity(s);
                                             }
                                         }
        );
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agency_sign_up);


        channel_add_btn_firebase = (Button) findViewById(R.id.channel_add_btn_firebase);

        channel_fullname = (EditText) findViewById(R.id.channel_fullname);

        channel_location = (EditText) findViewById(R.id.channel_location_point);
        channel_email = (EditText) findViewById(R.id.channel_email);
        channel_phone = (EditText) findViewById(R.id.channel_phone);
        channel_passport = (EditText) findViewById(R.id.channel_passport);
        channel_bl = (EditText) findViewById(R.id.channel_bl);
        channel_profilepicture=(ImageView) findViewById(R.id.channel_profilepicture);


        loadingbar = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        agencyRef= FirebaseDatabase.getInstance().getReference().child("Agency").child(currentUserId);
        AgencyProfileImageRef = FirebaseStorage.getInstance().getReference().child("Agency Profile Images");









        // Button On Click
        channel_add_btn_firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveAgencyProfileInfo();
            }
        });


        channel_profilepicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                chooseImageFromGallery();

            }
        });




        agencyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("profileimage")) {

                        String image = dataSnapshot.child("profileimage").getValue().toString();

                        //Picasso.get().load(image).into(setup_propic);

                        //Picasso.get().load(image).placeholder(R.drawable.userpic).into(setup_propic);
                        // Picasso.get().load(image).fit().centerCrop().into(setup_propic);
                        //Picasso.get().load(image).placeholder(R.drawable.userpic).into(setup_propic);
                        Picasso.with(AgencySignUpActivity.this).load(image).into(channel_profilepicture);

                        /*Picasso.get()
                                .load(image)
                                .resize(50, 50)
                                .centerCrop()
                                .into(setup_propic);*/
                    }
                    else {
                        Toast.makeText(AgencySignUpActivity.this, "Please select profile image first", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



    private void chooseImageFromGallery() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent galleryIntent = new Intent();
                            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                            galleryIntent.setType("image/*");
                            startActivityForResult(galleryIntent, Gallery_Pick);
                        }
                        Intent galleryIntent = new Intent();
                        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent, Gallery_Pick);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            channel_profilepicture.setImageURI(ImageUri);
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                channel_profilepicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }



            final StorageReference filePath = AgencyProfileImageRef.child( currentUserId + ".jpg");

            filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUrl = uri.toString();
                            agencyRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Toast.makeText(getApplicationContext(),"Image save in Database Successfully...",Toast.LENGTH_SHORT).show();
                                        //loadingBar.dismiss();
                                        Toast.makeText(AgencySignUpActivity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                    else{
                                        //String message = task.getException().toString();
                                        //Toast.makeText(getApplicationContext(),"Error:"+message,Toast.LENGTH_SHORT).show();
                                        //loadingBar.dismiss();
                                        String message = task.getException().getMessage();
                                        Toast.makeText(AgencySignUpActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });
                        }
                    });
                }
            });



        }

        else {

            Toast.makeText(this, "Error: Image cannot be cropped. ", Toast.LENGTH_SHORT).show();
            loadingbar.dismiss();
        }
    }



    private void SaveAgencyProfileInfo() {

        String name = channel_fullname.getText().toString();
        String email = channel_email.getText().toString();
        String location = channel_location.getText().toString();
        String bl = channel_bl.getText().toString();
        String passport = channel_passport.getText().toString();
        String phone = channel_phone.getText().toString();

        /*if(ImageUri==null){

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }*/

        if(TextUtils.isEmpty(name)){

            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(email)){

            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(location)){

            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(bl)){

            Toast.makeText(this, "Please enter Business License", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(passport)){

            Toast.makeText(this, "Please enter passport", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){

            Toast.makeText(this, "Please enter phone", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingbar.setTitle("Saving Information");
            loadingbar.setMessage("Please wait until we are creating new account...");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

            HashMap agencymap = new HashMap();
            agencymap.put("ownerName", name);
            agencymap.put("email", email);
            agencymap.put("location", location);
            agencymap.put("basenessLicense", bl);
            agencymap.put("passport", passport);
            agencymap.put("phone", phone);
            agencyRef.updateChildren(agencymap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()){

                        SendUserToHome();

                        Toast.makeText(AgencySignUpActivity.this, "Your channel is created successfully! ", Toast.LENGTH_LONG).show();

                        loadingbar.dismiss();
                    }
                    else {

                        String message= task.getException().getMessage();
                        Toast.makeText(AgencySignUpActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }




    /*@Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= firebaseAuth.getCurrentUser();

        if(currentUser!=null){

            SendUserToHome();
        }
    }*/



    private void SendUserToHome() {

        Intent intent = new Intent(AgencySignUpActivity.this, Channel_DashBoard.class);      //before mytoursforguide
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}