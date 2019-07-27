package com.example.asus.tripper;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ClickPackage extends AppCompatActivity {

    private ImageView clickaddpackagepic;
    private TextView clickpackagename, clickdetail, clickstartdate, clickenddate, clickstarttime, clickendtime, clicklocation, clickmeetpoint, clickprice, clickmembers;
    private Button editpackagebtn, deletepackagebtn;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String packagekey , currentUserId, databaseUserId, details, packagename,packageimage, startdate,enddate,starttime,endtime,location,price,groupmembers, meetpoint;  //userid who is online

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_package);


        getWindow().setFlags(WindowManager.LayoutParams
                .FLAG_FULLSCREEN, WindowManager.LayoutParams
                .FLAG_FULLSCREEN);

        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        packagekey= getIntent().getExtras().get("packagekey").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(packagekey);

        clickaddpackagepic = findViewById(R.id.clickaddpackagepic);
        clickpackagename=findViewById(R.id.clickpackagename);
        clickdetail=findViewById(R.id.clickdetail);
        clickstartdate=findViewById(R.id.clickstartdate);
        clickenddate=findViewById(R.id.clickenddate);
        clickstarttime=findViewById(R.id.clickstarttime);
        clickendtime=findViewById(R.id.clickendtime);
        clicklocation=findViewById(R.id.clicklocation);
        clickmeetpoint=findViewById(R.id.clickmeetpoint);
        clickprice=findViewById(R.id.clickprice);
        clickmembers=findViewById(R.id.clickmembers);
        editpackagebtn=findViewById(R.id.editpackagebtn);
        deletepackagebtn=findViewById(R.id.deletepackagebtn);

        deletepackagebtn.setVisibility(View.INVISIBLE);
        editpackagebtn.setVisibility(View.INVISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {  //for validation

                    details = dataSnapshot.child("details").getValue().toString();
                    packagename = dataSnapshot.child("packagename").getValue().toString();
                    packageimage = dataSnapshot.child("packageimage").getValue().toString();
                    startdate = dataSnapshot.child("startdate").getValue().toString();
                    enddate = dataSnapshot.child("enddate").getValue().toString();
                    starttime = dataSnapshot.child("starttime").getValue().toString();
                    endtime = dataSnapshot.child("endtime").getValue().toString();
                    location = dataSnapshot.child("location").getValue().toString();
                    meetpoint = dataSnapshot.child("meetpoint").getValue().toString();
                    price = dataSnapshot.child("price").getValue().toString();
                    groupmembers = dataSnapshot.child("groupmembers").getValue().toString();
                    databaseUserId = dataSnapshot.child("gid").getValue().toString();

                    clickdetail.setText(details);
                    clickpackagename.setText(packagename);
                    clickstartdate.setText(startdate);
                    clickenddate.setText(enddate);
                    clickstarttime.setText(starttime);
                    clickendtime.setText(endtime);
                    clicklocation.setText(location);
                    clickmeetpoint.setText(meetpoint);
                    clickprice.setText(price);
                    clickmembers.setText(groupmembers);
                    Picasso.get().load(packageimage).placeholder(R.drawable.hill).into(clickaddpackagepic);

                    if (currentUserId.equals(databaseUserId)) {

                        deletepackagebtn.setVisibility(View.VISIBLE);
                        editpackagebtn.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        deletepackagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteCurrentPackage();
            }
        });
    }

    private void deleteCurrentPackage() {

        databaseReference.removeValue();

        SendUserToHome();
        Toast.makeText(this, "Package is deleted", Toast.LENGTH_SHORT).show();
    }

    private void SendUserToHome() {

        Intent i = new Intent(ClickPackage.this, MyToursForGuide.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
