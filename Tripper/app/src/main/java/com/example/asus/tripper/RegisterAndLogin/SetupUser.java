package com.example.asus.tripper.RegisterAndLogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.tripper.MyToursForGuide;
import com.example.asus.tripper.R;
import com.example.asus.tripper.UserDashBoard;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupUser extends AppCompatActivity {

    private Button setup_button7;
    private EditText setup_username, setup_fullname, setup_address, setup_phone, setup_country;
    private CircleImageView setup_propic;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private StorageReference UserProfileImageRef;

    private Uri ImageUri;

    private ProgressDialog loadingbar;

    String currentUserId;
    final static int Gallery_Pick = 1;

    //public static final String TAG = "profileimage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_user);

        loadingbar = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("User Profile Images");

        setup_username= findViewById(R.id.setup_username);
        setup_fullname= findViewById(R.id.setup_fullname);
        setup_country= findViewById(R.id.setup_country);
        setup_button7= findViewById(R.id.setup_button7);
        setup_propic= findViewById(R.id.setup_propic);
        setup_address= findViewById(R.id.setup_address);
        setup_phone=findViewById(R.id.setup_phone);

        setup_button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveAccountSetupInfo();
            }
        });

        setup_propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, Gallery_Pick);
            }
        });

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    if (dataSnapshot.hasChild("profileimage")) {

                        String image = dataSnapshot.child("profileimage").getValue().toString();

                        //Picasso.get().load(image).into(setup_propic);

                        //Picasso.get().load(image).placeholder(R.drawable.userpic).into(setup_propic);
                        // Picasso.get().load(image).fit().centerCrop().into(setup_propic);
                        //Picasso.get().load(image).placeholder(R.drawable.userpic).into(setup_propic);
                        Picasso.with(SetupUser.this).load(image).into(setup_propic);

                        /*Picasso.get()
                                .load(image)
                                .resize(50, 50)
                                .centerCrop()
                                .into(setup_propic);*/
                    }
                    else {
                        Toast.makeText(SetupUser.this, "Please select profile image first", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        /*if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data.getData()!=null){
            Uri imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                setup_propic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            setup_propic.setImageURI(ImageUri);
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                setup_propic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }



            //CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);


       /* if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            CropImage.ActivityResult result=CropImage.getActivityResult(data);*/

           /* if (resultCode==RESULT_OK){

                loadingbar.setTitle("Profile Image");
                loadingbar.setMessage("Please wait until we are uploading profile image...");
                loadingbar.show();
                loadingbar.setCanceledOnTouchOutside(true);

                Uri resultUri = result.getUri();
*/
            final StorageReference filePath = UserProfileImageRef.child( currentUserId + ".jpg");

            filePath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadUrl = uri.toString();
                            usersRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Toast.makeText(getApplicationContext(),"Image save in Database Successfully...",Toast.LENGTH_SHORT).show();
                                        //loadingBar.dismiss();
                                        Toast.makeText(SetupUser.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                    else{
                                        //String message = task.getException().toString();
                                        //Toast.makeText(getApplicationContext(),"Error:"+message,Toast.LENGTH_SHORT).show();
                                        //loadingBar.dismiss();
                                        String message = task.getException().getMessage();
                                        Toast.makeText(SetupUser.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }
                            });
                        }
                    });
                }
            });


               /* filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                     @Override

                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){
                             //filePath.getDownloadUrl();

                            //Toast.makeText(SetupUser.this, "Profile image stored successfully", Toast.LENGTH_SHORT).show();

                            final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                            //SaveAccountSetupInfo();

                            usersRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        //Intent selfIntent = new Intent(SetupUser.this, SetupUser.class);
                                        //startActivity(selfIntent);

                                        Toast.makeText(SetupUser.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                    else {

                                        String message = task.getException().getMessage();
                                        Toast.makeText(SetupUser.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }

                                }
                            });
                        }
                    }
                });*/
        }

        else {

            Toast.makeText(this, "Error: Image cannot be cropped. ", Toast.LENGTH_SHORT).show();
            loadingbar.dismiss();
        }
    }
    //}

    private void SaveAccountSetupInfo() {

        String username = setup_username.getText().toString();
        String fullname = setup_fullname.getText().toString();
        String address = setup_address.getText().toString();
        String country = setup_country.getText().toString();
        String phone = setup_phone.getText().toString();

        /*if(ImageUri==null){

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }*/

        if(TextUtils.isEmpty(username)){

            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(fullname)){

            Toast.makeText(this, "Please enter fullname", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){

            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(country)){

            Toast.makeText(this, "Please enter country", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){

            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingbar.setTitle("Saving Information");
            loadingbar.setMessage("Please wait until we are creating new account...");
            loadingbar.show();
            loadingbar.setCanceledOnTouchOutside(true);

            HashMap usermap = new HashMap();
            usermap.put("username", username);
            usermap.put("fullname", fullname);
            usermap.put("address", address);
            usermap.put("country", country);
            usermap.put("phone", phone);
            usersRef.updateChildren(usermap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()){

                        SendUserToHome();

                        Toast.makeText(SetupUser.this, "Account is created successfully! ", Toast.LENGTH_LONG).show();

                        loadingbar.dismiss();
                    }
                    else {

                        String message= task.getException().getMessage();
                        Toast.makeText(SetupUser.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void SendUserToHome() {

        Intent intent = new Intent(SetupUser.this, MyToursForGuide.class);      //before mytoursforguide
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
