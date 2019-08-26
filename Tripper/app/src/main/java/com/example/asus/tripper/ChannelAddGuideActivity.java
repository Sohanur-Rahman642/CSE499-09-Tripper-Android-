
package com.example.asus.tripper;

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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChannelAddGuideActivity extends AppCompatActivity {


    private EditText channel_guide_fullname, channel_guide_address, channel_guide_nid , channel_guide_passport, channel_guide_phone;
    private Button channel_add_guide_btn;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference channelAddGuideRef;


    String guide_photo, guide_name, guide_addr, guide_nid, guide_passport,guide_phone;
    String postRandomName;
    private ImageView channel_add_guide_propic1;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri ImageUri;
    private StorageReference postGuideImageRef;


    private void initCAGA(){

        channel_add_guide_propic1=(ImageView) findViewById(R.id.channel_guide_propic1);

        channel_guide_fullname = (EditText) findViewById(R.id.channel_guide_fullname);

        channel_guide_address = (EditText) findViewById(R.id.channel_guide_address);

        channel_guide_phone = (EditText) findViewById(R.id.channel_guide_phone);
        channel_guide_nid = (EditText) findViewById(R.id.channel_guide_nid);
        channel_guide_passport = (EditText) findViewById(R.id.channel_guide_passport);


        channel_add_guide_btn = (Button) findViewById(R.id.channel_add_guide_btn);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null){
            ImageUri=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                channel_add_guide_propic1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_add_guide);

        initCAGA();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage= FirebaseStorage.getInstance();


        postGuideImageRef= FirebaseStorage.getInstance().getReference("Agency Guide Images");

        channelAddGuideRef= FirebaseDatabase.getInstance().getReference().child("Agency").child("Guide List");

        channel_add_guide_propic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");//application/*--for all document, audio/*--for audio
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });




        channel_add_guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });




    }


    public void StoringGuideImageToFirebaseStorage(){




        final StorageReference filepath = postGuideImageRef.child(ImageUri.getLastPathSegment());

        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        channelAddGuideRef.child("profileimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Toast.makeText(getApplicationContext(),"Image save in Database Successfully...",Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    Toast.makeText(ChannelAddGuideActivity.this, "Profile image stored to firebase database successfully", Toast.LENGTH_SHORT).show();

                                }
                                else{
                                    //String message = task.getException().toString();
                                    //Toast.makeText(getApplicationContext(),"Error:"+message,Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    String message = task.getException().getMessage();
                                    Toast.makeText(ChannelAddGuideActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                });



    }

    private void SaveAddGuideProfileSetupInfo() {



        String name = channel_guide_fullname.getText().toString();
        String addr = channel_guide_address.getText().toString();
        String nid = channel_guide_nid.getText().toString();
        String passport = channel_guide_passport.getText().toString();
        String phone = channel_guide_phone.getText().toString();

        /*if(ImageUri==null){

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }*/

        if(TextUtils.isEmpty(name)){

            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addr)){

            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(nid) || TextUtils.isEmpty(passport)){

            Toast.makeText(this, "Please enter nid or passport", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){

            Toast.makeText(this, "Please enter phone number", Toast.LENGTH_SHORT).show();
        }
        else {



            HashMap guidemap = new HashMap();
            guidemap.put("guideName", name);
            guidemap.put("address", addr);
            guidemap.put("nid", nid);
            guidemap.put("passport", passport);
            guidemap.put("phone", phone);
            channelAddGuideRef.updateChildren(guidemap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()){

                        SendUserToChannelDASH();

                        Toast.makeText(ChannelAddGuideActivity.this, "Account is created successfully! ", Toast.LENGTH_LONG).show();


                    }
                    else {

                        String message= task.getException().getMessage();
                        Toast.makeText(ChannelAddGuideActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


    private void SendUserToChannelDASH() {

        Intent intent = new Intent(ChannelAddGuideActivity.this, Channel_DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }




}

