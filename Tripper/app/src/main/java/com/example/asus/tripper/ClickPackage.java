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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClickPackage extends AppCompatActivity {

    private ImageView clickaddpackagepic;
    private ImageButton back_button_for_adding_packages1;
    private TextView clickpackagename, clickdetail, clickstartdate, clickenddate, clickstarttime, clickendtime, clicklocation, clickmeetpoint, clickprice, clickmembers;
    private Button  deletepackagebtn, confirm_package_btn, cancel_package_btn;
    private DatabaseReference databaseReference, UsersRef, confirmRef, tripRef , databaseReference2;
    private FirebaseAuth mAuth;

    private String packagekey , userkey, currentUserId, receiverUserId, CURRENT_STATE, databaseUserId, databaseUserId2, saveCurrentDate, details, packagename,packageimage, startdate,enddate,starttime,endtime,location,price,groupmembers, meetpoint;  //userid who is online

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_package);

        back_button_for_adding_packages1 = findViewById(R.id.back_button_for_adding_packages1);

        back_button_for_adding_packages1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ClickPackage.this, MyToursForGuide.class);
                startActivity(i);
            }
        });

        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();


        UsersRef = FirebaseDatabase.getInstance().getReference().child("Packages"); //this is new

        //databaseUserId = getIntent().getExtras().get("packagekey").toString(); //this is new

        //userkey= getIntent().getExtras().get("userkey").toString();

        packagekey= getIntent().getExtras().get("packagekey").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(packagekey);
        //databaseReference2 = FirebaseDatabase.getInstance().getReference().child("Packages").child("fullname");
        confirmRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedPackages");             //this is new
        tripRef = FirebaseDatabase.getInstance().getReference().child("Trips");    //this is new

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
        //editpackagebtn=findViewById(R.id.editpackagebtn);
        deletepackagebtn=findViewById(R.id.deletepackagebtn);
        confirm_package_btn=findViewById(R.id.confirm_package_btn);
        cancel_package_btn=findViewById(R.id.cancel_package_btn);


        CURRENT_STATE = "not_confirm";   //this is new

        deletepackagebtn.setVisibility(View.INVISIBLE);
        //editpackagebtn.setVisibility(View.INVISIBLE);
        //cancel_package_btn.setVisibility(View.INVISIBLE);     // implemented after commenting it in the if condition
        //confirm_package_btn.setVisibility(View.INVISIBLE);  // same as before

        //UsersRef.child(receiverUserId).addValue....


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
                    // currentUserId=dataSnapshot.child("fullname").getValue().toString();

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
                    //Picasso.get().load(packageimage).placeholder(R.drawable.hill).into(clickaddpackagepic);
                    Glide.with(ClickPackage.this).load(packageimage).placeholder(R.drawable.hill).centerCrop().into(clickaddpackagepic);

                    MaintenanceOfButtons();   //this is new


                    if (currentUserId.equals(databaseUserId)) {

                        deletepackagebtn.setVisibility(View.VISIBLE);
                        deletepackagebtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                        //editpackagebtn.setVisibility(View.VISIBLE);
                        confirm_package_btn.setVisibility(View.INVISIBLE);
                        cancel_package_btn.setVisibility(View.INVISIBLE);


                           /* confirm_package_btn.setOnClickListener(new View.OnClickListener() {     //this is new
                                @Override
                                public void onClick(View v) {                                       //this is new

                                    confirm_package_btn.setEnabled(false);

                                    if (CURRENT_STATE.equals("confirmation_received")) {

                                        AcceptTrip();
                                    }
                                }
                            });*/




                    }
                    else{

                        // confirm_package_btn.setVisibility(View.VISIBLE);  // this can be removed

                        confirm_package_btn.setOnClickListener(new View.OnClickListener() {     //this is new
                            @Override
                            public void onClick(View v) {                                       //this is new

                                confirm_package_btn.setEnabled(true);   //it was false before

                                if (CURRENT_STATE.equals("not_confirm")){

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ClickPackage.this);

                                    builder.setTitle("Are you sure?").setMessage("You won't be able to cancel the trip later")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    ConfirmTrip();

                                                }


                                            }).setNegativeButton("CANCEL", null);

                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }

                                if (CURRENT_STATE.equals("confirmation_sent")){

                                    //CancelTrip();

                                    ViewProfile();
                                }
                               /* if (CURRENT_STATE.equals("confirmation_receive")){

                                    AcceptTrip();
                                }*/
                            }
                        });
                    }                                                                            // new up to this

                   /* editpackagebtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i= new Intent(ClickPackage.this, UpdatePackage.class);
                            startActivity(i);
                        }
                    });*/
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




        cancel_package_btn.setVisibility(View.INVISIBLE);  // this is new
        cancel_package_btn.setEnabled(false);              // this is new

       /* if (!currentUserId.equals(databaseUserId)){                                 //this is new

            confirm_package_btn.setOnClickListener(new View.OnClickListener() {     //this is new
                @Override
                public void onClick(View v) {                                       //this is new

                    confirm_package_btn.setEnabled(false);
                }
            });
        }
        else {

            cancel_package_btn.setVisibility(View.INVISIBLE);
            confirm_package_btn.setVisibility(View.INVISIBLE);
        }      */                                                                     // up to this is new
    }

    private void ViewProfile() {

        confirm_package_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ClickPackage.this, FindGuide.class);
                //i.putExtra("userkey", userkey);
                startActivity(i);
            }
        });
    }

   /* private void AcceptTrip() {      //this is new

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        tripRef.child(currentUserId).child(packagekey).child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            tripRef.child(databaseUserId).child(packagekey).child("date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                confirmRef.child(currentUserId).child(packagekey)        //remove from database after accepting
                                                        .removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()){

                                                                    confirmRef.child(databaseUserId).child(packagekey)
                                                                            .removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                                    if (task.isSuccessful()){

                                                                                        confirm_package_btn.setEnabled(true);
                                                                                        CURRENT_STATE = "trips_accept";          //trips that will occur
                                                                                        confirm_package_btn.setVisibility(View.VISIBLE);     //new added
                                                                                        confirm_package_btn.setText("Decline Trip");



                                                                                        cancel_package_btn.setVisibility(View.INVISIBLE);
                                                                                        cancel_package_btn.setEnabled(false);


                                                                                        confirm_package_btn.setBackgroundResource(R.drawable.button_delete_packages);
                                                                                        confirm_package_btn.setTextColor(getResources().getColor(android.R.color.black));

                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    });
                        }
                    }
                });
    }*/                                                                                     //up to this




   /* private void CancelTrip() {           //this is new

        confirmRef.child(currentUserId).child(packagekey)  //previously databaseUserId
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            confirmRef.child(databaseUserId).child(packagekey)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                confirm_package_btn.setEnabled(true);
                                                CURRENT_STATE = "not_confirm";
                                                confirm_package_btn.setText("Confirm Trip");

                                                cancel_package_btn.setVisibility(View.INVISIBLE);
                                                cancel_package_btn.setEnabled(false);

                                                confirm_package_btn.setBackgroundResource(R.drawable.button_create_packages);
                                                confirm_package_btn.setTextColor(getResources().getColor(android.R.color.white));


                                            }
                                        }
                                    });
                        }
                    }
                });
    }                                          */                                                      //up to this

    private void MaintenanceOfButtons() {         //this is new

        confirmRef.child(currentUserId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(packagekey)){    //previously databaseUserId in bracket

                            String confirm_type_user = dataSnapshot.child(packagekey).child("confirm_type_user").getValue().toString();
                            //String confirm_type_guide = dataSnapshot.child(packagekey).child("confirm_type_guide").getValue().toString();

                            if (confirm_type_user.equals("confirmed at "+saveCurrentDate)){

                                CURRENT_STATE = "confirmation_sent";
                                confirm_package_btn.setBackgroundResource(R.drawable.button_delete_packages);
                                confirm_package_btn.setText("Search for the Guide");   //it was cancel trip
                                confirm_package_btn.setTextColor(getResources().getColor(android.R.color.black));

                                cancel_package_btn.setEnabled(true);   // before it was false
                                cancel_package_btn.setVisibility(View.VISIBLE);    //before it was visible
                                /*cancel_package_btn.setText("View Profile");  // it was not here
                                cancel_package_btn.setTextColor(getResources().getColor(android.R.color.white)); //it was not here before
*/
                                confirm_package_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent i =new Intent(ClickPackage.this, FindGuide.class);
                                        startActivity(i);
                                    }
                                });


                            }
                            /*else if (confirm_type_guide.equals("requested by "+currentUserId)){     //for receiver if accept or decline

                                CURRENT_STATE = "confirmation_receive";
                                confirm_package_btn.setEnabled(false);                //new added, was not before
                                confirm_package_btn.setVisibility(View.INVISIBLE);     //new added
                                confirm_package_btn.setText("Accept Request");



                                deletepackagebtn.setEnabled(false);              //new added
                                deletepackagebtn.setVisibility(View.INVISIBLE);   //new added
                                deletepackagebtn.setText("Decline Request");    //new added

                                cancel_package_btn.setVisibility(View.INVISIBLE);  //before it was visible
                                cancel_package_btn.setEnabled(false);  //before it was false


                            }*/


                           /* else if (confirm_type.equals("trips_accepted")){     //for receiver if accept or decline

                                confirm_package_btn.setEnabled(true);  //new added, was not before
                                CURRENT_STATE = "trips_accept";
                                confirm_package_btn.setVisibility(View.VISIBLE);     //new added
                                confirm_package_btn.setText("Cancel Request");
                                confirm_package_btn.setBackgroundResource(R.drawable.button_delete_packages);
                                confirm_package_btn.setTextColor(getResources().getColor(android.R.color.black));



                                deletepackagebtn.setEnabled(true);              //new added
                                deletepackagebtn.setVisibility(View.VISIBLE);   //new added
                                deletepackagebtn.setText("Decline Request");    //new added

                                cancel_package_btn.setVisibility(View.INVISIBLE);  //before it was visible
                                cancel_package_btn.setEnabled(false);  //before it was false


                            }*/
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






    }                                                                                   //up to this

    private void ConfirmTrip() {   // this is new

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        confirmRef.child(currentUserId).child(packagekey)
                .child("confirm_type_user").setValue("confirmed at "+saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            confirmRef.child(databaseUserId).child(packagekey)
                                    .child("confirm_type_user").setValue("requested by "+currentUserId)    //before currentUserId
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                confirm_package_btn.setEnabled(true);
                                                CURRENT_STATE = "confirmation_sent";
                                                confirm_package_btn.setText("Search for the Guide");    //it was cancel trip

                                               /* cancel_package_btn.setEnabled(true);
                                                cancel_package_btn.setVisibility(View.VISIBLE);
                                                cancel_package_btn.setText("View Profile");
                                                cancel_package_btn.setBackgroundResource(R.drawable.button_create_packages);
                                                cancel_package_btn.setTextColor(getResources().getColor(android.R.color.white));*/

                                                cancel_package_btn.setVisibility(View.INVISIBLE);   //before it was here
                                                cancel_package_btn.setEnabled(false);              //before it was here

                                                //confirm_package_btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                                confirm_package_btn.setBackgroundResource(R.drawable.button_delete_packages);
                                                confirm_package_btn.setTextColor(getResources().getColor(android.R.color.black));

                                                confirm_package_btn.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                   /* AlertDialog.Builder builder = new AlertDialog.Builder(ClickPackage.this);

                                    builder.setTitle("Are you sure?")
                                            .setMessage("You can't be able to cancel the trip later")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent i =new Intent(ClickPackage.this, SeeGuidesProfileAfterConfirmingTrip.class);
                                                    startActivity(i);
                                                    finish();

                                                    confirm_package_btn.setVisibility(View.INVISIBLE);
                                                    confirm_package_btn.setEnabled(false);

                                                }
                                            }).setNegativeButton("CANCEL", null);

                                    AlertDialog alert = builder.create();
                                    alert.show();*/

                                                        Intent i = new Intent(ClickPackage.this, SeeGuidesProfileAfterConfirmingTrip.class);
                                                        startActivity(i);
                                                    }
                                                });

                                            }
                                        }
                                    });


                        }
                    }
                });


        /*confirmRef.child(currentUserId).child(packagekey)
                .child("confirm_type").setValue("confirmed")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            confirmRef.child(databaseUserId).child(packagekey)
                                    .child("confirm_type").setValue("confirmation_received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()){

                                                confirm_package_btn.setEnabled(true);
                                                CURRENT_STATE = "confirmation_sent";
                                                confirm_package_btn.setText("View Profile");    //it was cancel trip

                                               *//* cancel_package_btn.setEnabled(true);
                                                cancel_package_btn.setVisibility(View.VISIBLE);
                                                cancel_package_btn.setText("View Profile");
                                                cancel_package_btn.setBackgroundResource(R.drawable.button_create_packages);
                                                cancel_package_btn.setTextColor(getResources().getColor(android.R.color.white));*//*

                                                cancel_package_btn.setVisibility(View.INVISIBLE);   //before it was here
                                                cancel_package_btn.setEnabled(false);              //before it was here

                                                //confirm_package_btn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                                confirm_package_btn.setBackgroundResource(R.drawable.button_delete_packages);
                                                confirm_package_btn.setTextColor(getResources().getColor(android.R.color.black));

                                            }
                                        }
                                    });

                            confirm_package_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                   *//* AlertDialog.Builder builder = new AlertDialog.Builder(ClickPackage.this);

                                    builder.setTitle("Are you sure?")
                                            .setMessage("You can't be able to cancel the trip later")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    Intent i =new Intent(ClickPackage.this, SeeGuidesProfileAfterConfirmingTrip.class);
                                                    startActivity(i);
                                                    finish();

                                                    confirm_package_btn.setVisibility(View.INVISIBLE);
                                                    confirm_package_btn.setEnabled(false);

                                                }
                                            }).setNegativeButton("CANCEL", null);

                                    AlertDialog alert = builder.create();
                                    alert.show();*//*

                                    Intent i =new Intent(ClickPackage.this, SeeGuidesProfileAfterConfirmingTrip.class);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });*/
        // up to this


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

        AlertDialog.Builder builder = new AlertDialog.Builder(ClickPackage.this);

        builder.setTitle("Are you sure?").setMessage("Once you delete it won't be recovered")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseReference.removeValue();

                        SendUserToHome();
                        Toast.makeText(ClickPackage.this, "Package is deleted", Toast.LENGTH_SHORT).show();

                    }


                }).setNegativeButton("CANCEL", null);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void SendUserToHome() {

        Intent i = new Intent(ClickPackage.this, MyToursForGuide.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}
