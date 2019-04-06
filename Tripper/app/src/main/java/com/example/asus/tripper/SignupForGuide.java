package com.example.asus.tripper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupForGuide extends AppCompatActivity {

    private EditText guide_fullname, guide_address, guide_emailad, guide_phone, guide_pass, guide_nid;
    private Button guide_button7;
    //private FirebaseAuth firebaseAuth;
    String name,addr,pho,email,ages,gen,pas,nid;
    //private CircleImageView guide_propic1;
    //private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE=123;
    Uri imagePath;
    //private StorageReference storageReference;

    private void init1(){
        guide_button7=(Button) findViewById(R.id.guide_button7);
        guide_button7.setOnClickListener(new View.OnClickListener(){

                                            @Override
                                            public void onClick(View v) {
                                                Intent s=new Intent(SignupForGuide.this, MainDashBoard.class);
                                                startActivity(s);
                                            }
                                        }
        );
    }

    private void init3(){

        guide_fullname=(EditText)findViewById(R.id.guide_fullname);
        guide_button7=(Button)findViewById(R.id.guide_button7);
        guide_address=(EditText)findViewById(R.id.guide_address);
        guide_emailad=(EditText)findViewById(R.id.guide_emailad);
        guide_phone=(EditText)findViewById(R.id.guide_phone);
        guide_pass=(EditText)findViewById(R.id.guide_pass);
        guide_nid=(EditText)findViewById(R.id.guide_nid);
        //guide_propic1=(CircleImageView) findViewById(R.id.guide_propic1);

    }
    //@Override
   /* protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK && data.getData()!=null){
            imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
                guide_propic1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_for_guide);
        init3();
        init1();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .7));

       /* firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();

        storageReference=firebaseStorage.getReference();*/
        //StorageReference myref1= storageReference.child(firebaseAuth.getUid());

        /*guide_propic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");//application/*--for all document, audio/*--for audio
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });*/




        /*guide_button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    String email=guide_emailad.getText().toString().trim();
                    String pas=guide_pass.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(email,pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendUserData();
                                Toast.makeText(SignupForGuide.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(SignupForGuide.this, LoginForUser.class));
                            }else {
                                Toast.makeText(SignupForGuide.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });*/
    }
    private Boolean validate(){
        Boolean result=false;

        name=guide_fullname.getText().toString();
        addr=guide_address.getText().toString();
        email=guide_emailad.getText().toString();
        pho=guide_phone.getText().toString();
        nid=guide_nid.getText().toString();
        pas=guide_pass.getText().toString();

        if(name.isEmpty() || addr.isEmpty() || pho.isEmpty() || email.isEmpty()  || pas.isEmpty() || nid.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else {
            result=true;
        }
        return result;
    }
   /* private void sendUserData(){
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference myref= firebaseDatabase.getReference("Guides").child(firebaseAuth.getUid());
        StorageReference imageReference=storageReference.child("Uploaded Profile Pictures For Guides").child(firebaseAuth.getUid()).child("Images for guide").child("Profile pic for guide");
        UploadTask uploadTask=imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupForGuide.this, "Upload failed", Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SignupForGuide.this, "Upload successful", Toast.LENGTH_SHORT).show();

            }
        });
        GuideProfile guideProfile=new GuideProfile(name,addr,email,pho, nid);
        myref.setValue(guideProfile);
*/


}