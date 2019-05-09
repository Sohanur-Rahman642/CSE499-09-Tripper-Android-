package com.example.asus.tripper;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private ImageView clickaddpackagepic, editpackagebtn;
    private TextView clickpackagename, clickdetail, clickstartdate, clickenddate, clickstarttime, clickendtime, clicklocation, clickmeetpoint, clickprice, clickmembers;
    private Button  deletepackagebtn;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String packagekey , currentUserId, databaseUserId, details, packagename,packageimage, startdate,enddate,starttime,endtime,location,price,groupmembers, meetpoint;  //userid who is online

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_package);

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

                    editpackagebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i= new Intent(ClickPackage.this, UpdatePackage.class);
                            startActivity(i);
                        }
                    });
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

   /* private void EditCurrentPackage(String details, String packagename,  String startdate, String enddate, String starttime, String endtime, String location, String meetpoint, String price, String groupmembers) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ClickPackage.this);
        builder.setTitle("Edit Package:");

        final EditText inputField = new EditText(ClickPackage.this);
        final EditText inputField1 = new EditText(ClickPackage.this);
        final EditText inputField2 = new EditText(ClickPackage.this);
        final EditText inputField3 = new EditText(ClickPackage.this);
        final EditText inputField4 = new EditText(ClickPackage.this);
        final EditText inputField5 = new EditText(ClickPackage.this);
        final EditText inputField6 = new EditText(ClickPackage.this);
        final EditText inputField7 = new EditText(ClickPackage.this);
        final EditText inputField8 = new EditText(ClickPackage.this);
        final EditText inputField9 = new EditText(ClickPackage.this);
        inputField.setText(details);
        builder.setView(inputField);
        inputField.setText(packagename);
        builder.setView(inputField1);
        //inputField.setText(packageimage);
        inputField.setText(startdate);
        builder.setView(inputField2);
        inputField.setText(enddate);
        builder.setView(inputField3);
        inputField.setText(starttime);
        builder.setView(inputField4);
        inputField.setText(endtime);
        builder.setView(inputField5);
        inputField.setText(location);
        builder.setView(inputField6);
        inputField.setText(meetpoint);
        builder.setView(inputField7);
        inputField.setText(price);
        builder.setView(inputField8);
        inputField.setText(groupmembers);
        builder.setView(inputField9);

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                databaseReference.child("details").setValue(inputField.getText().toString());
                databaseReference.child("packagename").setValue(inputField1.getText().toString());
                databaseReference.child("startdate").setValue(inputField2.getText().toString());
                databaseReference.child("enddate").setValue(inputField3.getText().toString());
                databaseReference.child("starttime").setValue(inputField4.getText().toString());
                databaseReference.child("endtime").setValue(inputField5.getText().toString());
                databaseReference.child("location").setValue(inputField6.getText().toString());
                databaseReference.child("meetpoint").setValue(inputField7.getText().toString());
                databaseReference.child("price").setValue(inputField8.getText().toString());
                databaseReference.child("groupmembers").setValue(inputField9.getText().toString());
                Toast.makeText(ClickPackage.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_dark);
    }*/

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
