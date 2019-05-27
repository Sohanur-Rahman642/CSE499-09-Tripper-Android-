package com.example.asus.tripper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeeGuidesProfileAfterConfirmingTrip extends AppCompatActivity {


    private FloatingActionButton message_float1;
    private CircleImageView profilepic1;
    private TextView username1, country1 , phone1, ratings1, trips1, cross_btn1;
    private DatabaseReference databaseReference, UsersRef ;
    private FirebaseAuth mAuth;

    private String username, fullname, userkey, phone, country, profileimage,  packagekey , currentUserId, receiverUserId, CURRENT_STATE, databaseUserId, saveCurrentDate  ;  //userid who is online


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_guides_profile_after_confirming_trip);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .7));


       /* mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        //UsersRef = FirebaseDatabase.getInstance().getReference().child("Packages"); //this is new

        //databaseUserId = getIntent().getExtras().get("packagekey").toString(); //this is new

        userkey= getIntent().getExtras().get("userkey").toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(userkey);

        message_float1 = findViewById(R.id.message_float);
        profilepic1 = findViewById(R.id.profilepic);
        username1 = findViewById(R.id.username);
        country1 = findViewById(R.id.country);
        phone1 = findViewById(R.id.phone);
        ratings1 = findViewById(R.id.ratings);
        trips1 = findViewById(R.id.trips);
        cross_btn1 = findViewById(R.id.cross_btn);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {  //for validation

                    username = dataSnapshot.child("fullname").getValue().toString();
                    //phone = dataSnapshot.child("phone").getValue().toString();
                    //country = dataSnapshot.child("country").getValue().toString();
                    profileimage = dataSnapshot.child("profileimage").getValue().toString();
                    databaseUserId = dataSnapshot.child("gid").getValue().toString();

                    username1.setText(username);
                    //phone1.setText(phone);
                    //country1.setText(country);
                    Picasso.get().load(profileimage).placeholder(R.drawable.deeptapic).into(profilepic1);*/



                   /* if (currentUserId.equals(databaseUserId)) {

                        *//*deletepackagebtn.setVisibility(View.VISIBLE);
                        deletepackagebtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                        editpackagebtn.setVisibility(View.VISIBLE);
                        confirm_package_btn.setVisibility(View.INVISIBLE);
                        cancel_package_btn.setVisibility(View.INVISIBLE);*//*


                           *//* confirm_package_btn.setOnClickListener(new View.OnClickListener() {     //this is new
                                @Override
                                public void onClick(View v) {                                       //this is new

                                    confirm_package_btn.setEnabled(false);

                                    if (CURRENT_STATE.equals("confirmation_received")) {

                                        AcceptTrip();
                                    }
                                }
                            });*//*




                    }
                    else{

                        // confirm_package_btn.setVisibility(View.VISIBLE);  // this can be removed


                    }                  */                                                          // new up to this

                    /*message_float1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i= new Intent(SeeGuidesProfileAfterConfirmingTrip.this, ClickPackage.class);
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/
    }
}
