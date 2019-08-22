package com.example.asus.tripper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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

public class AddingPackages extends AppCompatActivity {

    private ProgressDialog loadingBar;

    private ImageView back_button_for_adding_packages, addpackage;
    private Button createpackage_btn;
    private EditText start_date_tv1, start_time_tv1, end_date_tv1, end_time_tv1, location1, price1, meetpoint1, group_members1, details1, package_name_ed1;

    private String details;
    private String start_date;
    private String end_date;
    private String start_time;
    private String end_time;
    private String location;
    private String price;
    private String meetpoint;
    private String group_members;
    private String package_name , packagekey;

    private StorageReference postimagesreference;
    private DatabaseReference guidesRef, packagesRef, usersRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_guide_id;

    private long countPackages = 0; //new

    private static final int Gallery_Pick=1;
    private Uri ImageUri;

    private void init() {
        back_button_for_adding_packages = (ImageView) findViewById(R.id.back_button_for_adding_packages);
        back_button_for_adding_packages.setOnClickListener(new View.OnClickListener() {

                                                               @Override
                                                               public void onClick(View v) {
                                                                   Intent i = new Intent(AddingPackages.this, MyToursForGuide.class);
                                                                   startActivity(i);
                                                               }
                                                           }
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_packages);
        init();

        mAuth= FirebaseAuth.getInstance();
        current_guide_id=mAuth.getCurrentUser().getUid();

        //packagekey= getIntent().getExtras().get("packagekey").toString();

        postimagesreference= FirebaseStorage.getInstance().getReference();
        guidesRef= FirebaseDatabase.getInstance().getReference().child("Users");
        //usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(current_guide_id);
        packagesRef= FirebaseDatabase.getInstance().getReference().child("Packages");

        back_button_for_adding_packages=findViewById(R.id.back_button_for_adding_packages);
        addpackage=findViewById(R.id.addpackage);
        createpackage_btn=findViewById(R.id.createpackage_btn);
        package_name_ed1= findViewById(R.id.package_name_ed1);
        start_date_tv1=findViewById(R.id.start_date_tv1);
        start_time_tv1=findViewById(R.id.start_time_tv1);
        end_date_tv1=findViewById(R.id.end_date_tv1);
        end_time_tv1=findViewById(R.id.end_time_tv1);
        location1=findViewById(R.id.location1);
        price1=findViewById(R.id.price1);
        meetpoint1=findViewById(R.id.meetpoint1);
        group_members1=findViewById(R.id.group_members1);
        details1=findViewById(R.id.details1);
        loadingBar= new ProgressDialog(this);


        addpackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        createpackage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidatePostInfo();
            }
        });
    }

    private void ValidatePostInfo() {

        details = details1.getText().toString();
        package_name = package_name_ed1.getText().toString();
        start_date = start_date_tv1.getText().toString();
        end_date = end_date_tv1.getText().toString();
        start_time = start_time_tv1.getText().toString();
        end_time = end_time_tv1.getText().toString();
        location = location1.getText().toString();
        price = price1.getText().toString();
        meetpoint = meetpoint1.getText().toString();
        group_members = group_members1.getText().toString();

        if (ImageUri==null){

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(package_name)){

            Toast.makeText(this, "Please enter package name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(start_date)){

            Toast.makeText(this, "Please enter start date", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(end_date)){

            Toast.makeText(this, "Please enter end date", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(start_time)){

            Toast.makeText(this, "Please enter start time", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(end_time)){

            Toast.makeText(this, "Please enter end time", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(location)){

            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(price)){

            Toast.makeText(this, "Please enter tour price", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(meetpoint)){

            Toast.makeText(this, "Please enter meeting point", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(details)){

            Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show();
        }

        else {

            loadingBar.setTitle("Adding New Package...");
            loadingBar.setMessage("Please wait until we are updating new package...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            StoringImageToFirebaseStorage();
        }
    }

    private void StoringImageToFirebaseStorage() {

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;


        final StorageReference filepath = postimagesreference.child("Package Images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl = uri.toString();
                        packagesRef.child(current_guide_id + postRandomName).child("packageimage").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //Toast.makeText(getApplicationContext(),"Image save in Database Successfully...",Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    Toast.makeText(AddingPackages.this, "Package is created successfully", Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    SavingPackageInformationToDatabase();
                                }
                                else{
                                    //String message = task.getException().toString();
                                    //Toast.makeText(getApplicationContext(),"Error:"+message,Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    String message = task.getException().getMessage();
                                    Toast.makeText(AddingPackages.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        });

        /*filepath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                if (task.isSuccessful()){

                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();

                    Toast.makeText(AddingPackages.this, "Package is created successfully", Toast.LENGTH_SHORT).show();

                    SavingPackageInformationToDatabase();
                }
                else {
                    String message = task.getException().getMessage();
                    Toast.makeText(AddingPackages.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    private void SavingPackageInformationToDatabase() {

        packagesRef.addValueEventListener(new ValueEventListener() {        //new start, counting packages
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    countPackages = dataSnapshot.getChildrenCount();

                }
                else {

                    countPackages = 0;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });                                                                       //new end

        guidesRef.child(current_guide_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    final String guideFullName= dataSnapshot.child("username").getValue().toString();
                    final String guidePhone = dataSnapshot.child("phone").getValue().toString();
                    final String guideCountry = dataSnapshot.child("country").getValue().toString();
                    final String guideProfileImage= dataSnapshot.child("profileimage").getValue().toString();

                    HashMap packagesMap = new HashMap();
                    packagesMap.put("gid",current_guide_id);
                    packagesMap.put("date",saveCurrentDate);
                    packagesMap.put("time",saveCurrentTime);
                    packagesMap.put("packagename", package_name);
                    packagesMap.put("details",details);
                    packagesMap.put("startdate",start_date);
                    packagesMap.put("starttime",start_time);
                    packagesMap.put("enddate",end_date);
                    packagesMap.put("endtime",end_time);
                    packagesMap.put("location",location);
                    packagesMap.put("price",price);
                    packagesMap.put("meetpoint",meetpoint);
                    packagesMap.put("groupmembers",group_members);
                    //packagesMap.put("packageimage",downloadUrl);
                    packagesMap.put("profileimage",guideProfileImage);
                    packagesMap.put("fullname",guideFullName);
                    packagesMap.put("phone", guidePhone);
                    packagesMap.put("country", guideCountry);
                    packagesMap.put("counter", countPackages);        //newwww

                    packagesRef.child(current_guide_id + postRandomName).updateChildren(packagesMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if(task.isSuccessful()){

                                //SendUserToMainActivity();

                                startActivity(new Intent(AddingPackages.this, MyToursForGuide.class));

                                finish();

                                Toast.makeText(AddingPackages.this, "New package is created successfully.", Toast.LENGTH_SHORT).show();

                                loadingBar.dismiss();
                            }
                            else {

                                Toast.makeText(AddingPackages.this, "Error occurred while creating the package.", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void OpenGallery() {

        Intent galleryIntent= new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            ImageUri = data.getData();
            addpackage.setImageURI(ImageUri);

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                addpackage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
