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
import com.tiper.MaterialSpinner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChannelAddPackage extends AppCompatActivity {


    private ProgressDialog loadingBar;

    private ImageView back_button_for_adding_packages_of_channel, channel_add_package_image;

    private Button createpackage_btn_of_channel;

    private EditText channel_start_date_tv1, channel_start_time_tv1, channel_end_date_tv1, channel_end_time_tv1,
            chaneel_location1, channel_price1, channel_meetpoint1, channel_group_members1, channel_details1, channel_package_name_ed1;

    MaterialSpinner selecyedGuideNameSpinner;

    private String channel_details;
    private String channel_start_date;
    private String channel_end_date;
    private String channel_start_time;
    private String channel_end_time;
    private String channel_location;
    private String channel_price;
    private String channel_meetpoint;
    private String channel_group_members;
    private String channel_package_name, channel_packagekey;
    private String selecteddGuideName;


    private StorageReference postimagesreference;
    private DatabaseReference guidesRef, packagesRef, usersRef;
    private FirebaseAuth mAuth;

    private String saveCurrentDate, saveCurrentTime, postRandomName, downloadUrl, current_guide_id;

    private long countPackages = 0; //new

    private static final int Gallery_Pick = 1;
    private Uri ImageUri;


    private void init() {
        back_button_for_adding_packages_of_channel = (ImageView) findViewById(R.id.back_button_for_adding_packages_of_channel);
        back_button_for_adding_packages_of_channel.setOnClickListener(new View.OnClickListener() {

                                                               @Override
                                                               public void onClick(View v) {
                                                                   Intent i = new Intent(ChannelAddPackage.this, Channel_DashBoard.class);
                                                                   startActivity(i);
                                                               }
                                                           }
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_add_package);

        init();

        mAuth = FirebaseAuth.getInstance();
        current_guide_id = mAuth.getCurrentUser().getUid();


        postimagesreference = FirebaseStorage.getInstance().getReference();

        guidesRef= FirebaseDatabase.getInstance().getReference().child("Agency");
        //usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(current_guide_id);
        packagesRef= FirebaseDatabase.getInstance().getReference().child("Agency").child("Channel Packages");



        back_button_for_adding_packages_of_channel = findViewById(R.id.back_button_for_adding_packages_of_channel);
        channel_add_package_image = findViewById(R.id.channel_add_package_image);
        createpackage_btn_of_channel = findViewById(R.id.channel_createpackage_btn);
        channel_package_name_ed1 = findViewById(R.id.channel_package_name_ed1);
        channel_start_date_tv1 = findViewById(R.id.channel_start_date_tv1);
        channel_start_time_tv1 = findViewById(R.id.channel_start_time_tv1);
        channel_end_date_tv1 = findViewById(R.id.channel_end_date_tv1);
        channel_end_time_tv1 = findViewById(R.id.channel_end_time_tv1);
        chaneel_location1 = findViewById(R.id.channel_location1);
        channel_price1 = findViewById(R.id.channel_price1);
        channel_meetpoint1 = findViewById(R.id.channel_meetpoint1);
        channel_group_members1 = findViewById(R.id.channel_group_members1);
        channel_details1 = findViewById(R.id.channel_details1);
        selecyedGuideNameSpinner = findViewById(R.id.channel_select_guide);

        loadingBar = new ProgressDialog(this);

        channel_add_package_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });


        createpackage_btn_of_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });


    }

    public void ValidatePostInfo() {


        channel_details = channel_details1.getText().toString();
        channel_package_name = channel_package_name_ed1.getText().toString();
        channel_start_date = channel_start_date_tv1.getText().toString();
        channel_end_date = channel_end_date_tv1.getText().toString();
        channel_start_time = channel_start_time_tv1.getText().toString();
        channel_end_time = channel_end_time_tv1.getText().toString();
        channel_location = chaneel_location1.getText().toString();
        channel_price = channel_price1.getText().toString();
        channel_meetpoint = channel_meetpoint1.getText().toString();
        channel_group_members = channel_group_members1.getText().toString();
        selecteddGuideName = selecyedGuideNameSpinner.toString();


        if (ImageUri == null) {

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_package_name)) {

            Toast.makeText(this, "Please enter package name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_start_date)) {

            Toast.makeText(this, "Please enter start date", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_end_date)) {

            Toast.makeText(this, "Please enter end date", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_start_time)) {

            Toast.makeText(this, "Please enter start time", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_end_time)) {

            Toast.makeText(this, "Please enter end time", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_location)) {

            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_price)) {

            Toast.makeText(this, "Please enter tour price", Toast.LENGTH_SHORT).show();
        } /*else if (TextUtils.isEmpty(selecteddGuideName)) {

            Toast.makeText(this, "Please choose a guide", Toast.LENGTH_SHORT).show();
        }*/ else if (TextUtils.isEmpty(channel_meetpoint)) {

            Toast.makeText(this, "Please enter meeting point", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(channel_details)) {

            Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show();
        } else {

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


        final StorageReference filepath = postimagesreference.child("Channel Add Package Images")
                .child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

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
                                if (task.isSuccessful()) {
                                    //Toast.makeText(getApplicationContext(),"Image save in Database Successfully...",Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    Toast.makeText(ChannelAddPackage.this, "Package is created successfully", Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    SavingPackageInformationToDatabase();
                                } else {
                                    //String message = task.getException().toString();
                                    //Toast.makeText(getApplicationContext(),"Error:"+message,Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                    String message = task.getException().getMessage();
                                    Toast.makeText(ChannelAddPackage.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                    //loadingBar.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        });


    }


    private void SavingPackageInformationToDatabase() {

        packagesRef.addValueEventListener(new ValueEventListener() {        //new start, counting packages
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    countPackages = dataSnapshot.getChildrenCount();

                } else {

                    countPackages = 0;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        guidesRef.child(current_guide_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    final String guideFullName= dataSnapshot.child("ownerName").getValue().toString();
                    final String guidePhone = dataSnapshot.child("phone").getValue().toString();
                    final String guideCountry = dataSnapshot.child("location").getValue().toString();
                    final String guideProfileImage= dataSnapshot.child("profileimage").getValue().toString();

                    HashMap packagesMap = new HashMap();
                    packagesMap.put("gid",current_guide_id);
                    packagesMap.put("date",saveCurrentDate);
                    packagesMap.put("time",saveCurrentTime);
                    packagesMap.put("packagename", channel_package_name);
                    packagesMap.put("details",channel_details);
                    packagesMap.put("startdate",channel_start_date);
                    packagesMap.put("starttime",channel_start_time);
                    packagesMap.put("enddate",channel_end_date);
                    packagesMap.put("endtime",channel_end_time);
                    packagesMap.put("location",channel_location);
                    packagesMap.put("price",channel_price);
                    packagesMap.put("meetpoint",channel_meetpoint);
                    packagesMap.put("groupmembers",channel_group_members);
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

                                startActivity(new Intent(ChannelAddPackage.this, Channel_DashBoard.class));

                                finish();

                                Toast.makeText(ChannelAddPackage.this, "New package is created successfully.", Toast.LENGTH_SHORT).show();

                                loadingBar.dismiss();
                            }
                            else {

                                Toast.makeText(ChannelAddPackage.this, "Error occurred while creating the package.", Toast.LENGTH_SHORT).show();
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
            channel_add_package_image.setImageURI(ImageUri);

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),ImageUri);
                channel_add_package_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


}
