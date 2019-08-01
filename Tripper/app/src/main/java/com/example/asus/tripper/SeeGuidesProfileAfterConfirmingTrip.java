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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeeGuidesProfileAfterConfirmingTrip extends AppCompatActivity {


    private FloatingActionButton message_float1, btn_ratings;
    private CircleImageView profilepic1;
    private TextView username1, country1 , phone1, ratings1, trips1, cross_btn1;
    private DatabaseReference databaseReference, UsersRef, tripsRef, RatingsRef, RatingsRef2, RatingsRef3 ;
    private FirebaseAuth mAuth;
    private int countConfirmedTrips = 0;
    //private Button btn_ratings;

    private TextView great_no, good_no, poor_no;
    private int countRatings;

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


        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        //UsersRef = FirebaseDatabase.getInstance().getReference().child("Packages"); //this is new

        //databaseUserId = getIntent().getExtras().get("packagekey").toString(); //this is new

        userkey= getIntent().getExtras().get("userkey").toString();
        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(userkey);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userkey);
        tripsRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedPackages");

        RatingsRef = FirebaseDatabase.getInstance().getReference().child("Ratings");
        RatingsRef2 = FirebaseDatabase.getInstance().getReference().child("Ratings2");
        RatingsRef3 = FirebaseDatabase.getInstance().getReference().child("Ratings3");

        great_no = findViewById(R.id.great_no);
        good_no = findViewById(R.id.good_no);
        poor_no = findViewById(R.id.poor_no);

        message_float1 = findViewById(R.id.message_float);
        profilepic1 = findViewById(R.id.profilepic);
        username1 = findViewById(R.id.username);
        country1 = findViewById(R.id.country);
        phone1 = findViewById(R.id.phone);
        //ratings1 = findViewById(R.id.ratings);
        //trips1 = findViewById(R.id.trips);
        //cross_btn1 = findViewById(R.id.cross_btn);

        btn_ratings = findViewById(R.id.btn_ratings);



       /* tripsRef.child(currentUserId).addValueEventListener(new ValueEventListener() {    //at first only child(currentUserId)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    countConfirmedTrips = (int)dataSnapshot.getChildrenCount();
                    trips1.setText(Integer.toString(countConfirmedTrips));
                }
                else {

                    trips1.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/


        setRatingButtonStatus(userkey);
        setRatingButtonStatus2(userkey);
        setRatingButtonStatus3(userkey);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {  //for validation

                    //username = dataSnapshot.child("fullname").getValue().toString();

                    username = dataSnapshot.child("username").getValue().toString();
                    phone = dataSnapshot.child("phone").getValue().toString();
                    country = dataSnapshot.child("country").getValue().toString();
                    profileimage = dataSnapshot.child("profileimage").getValue().toString();
                    //databaseUserId = dataSnapshot.child("gid").getValue().toString();

                    username1.setText(username);
                    phone1.setText(phone);
                    country1.setText(country);
                    //Picasso.get().load(profileimage).into(profilepic1);
                    Glide.with(SeeGuidesProfileAfterConfirmingTrip.this).load(profileimage).placeholder(R.drawable.userpic).into(profilepic1);



                    //if (currentUserId.equals(databaseUserId)) {


                        //message_float1.setVisibility(View.GONE);

                        /*deletepackagebtn.setVisibility(View.VISIBLE);
                        deletepackagebtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                        editpackagebtn.setVisibility(View.VISIBLE);
                        confirm_package_btn.setVisibility(View.INVISIBLE);
                        cancel_package_btn.setVisibility(View.INVISIBLE);*/


                           /* confirm_package_btn.setOnClickListener(new View.OnClickListener() {     //this is new
                                @Override
                                public void onClick(View v) {                                       //this is new

                                    confirm_package_btn.setEnabled(false);

                                    if (CURRENT_STATE.equals("confirmation_received")) {

                                        AcceptTrip();
                                    }
                                }
                            });*/




                    //}
                   /* else{

                        // confirm_package_btn.setVisibility(View.VISIBLE);  // this can be removed


                    } */                                                                           // new up to this


                    message_float1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i= new Intent(SeeGuidesProfileAfterConfirmingTrip.this, Message.class);
                            i.putExtra("userkey", userkey);
                            startActivity(i);
                        }
                    });

                    btn_ratings.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i= new Intent(SeeGuidesProfileAfterConfirmingTrip.this, Ratings.class);
                            i.putExtra("userkey", userkey);
                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setRatingButtonStatus(final String userkey) {

        RatingsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                    //great_btn.setImageResource(R.drawable.great_color);
                    great_no.setText(Integer.toString(countRatings));
                   /* good_btn.setImageResource(R.drawable.good_color);
                    good_number.setText(Integer.toString(countRatings));
                    poor_btn.setImageResource(R.drawable.bad_color);
                    poor_number.setText(Integer.toString(countRatings));*/
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                    //great_btn.setImageResource(R.drawable.great_colorless);
                    great_no.setText(Integer.toString(countRatings));
                    //good_btn.setImageResource(R.drawable.good_colorless);
                   // good_number.setText(Integer.toString(countRatings));
                    //poor_btn.setImageResource(R.drawable.bad_colorless);
                    //poor_number.setText(Integer.toString(countRatings));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void setRatingButtonStatus2(final String userkey) {

        RatingsRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                   /* great_btn.setImageResource(R.drawable.great_color);
                    great_number.setText(Integer.toString(countRatings));*/
                    //good_btn.setImageResource(R.drawable.good_color);
                    good_no.setText(Integer.toString(countRatings));
                    /*poor_btn.setImageResource(R.drawable.bad_color);
                    poor_number.setText(Integer.toString(countRatings));*/
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                    //great_btn.setImageResource(R.drawable.great_colorless);
                    //great_number.setText(Integer.toString(countRatings));
                    //good_btn.setImageResource(R.drawable.good_colorless);
                    good_no.setText(Integer.toString(countRatings));
                    //poor_btn.setImageResource(R.drawable.bad_colorless);
                    //poor_no.setText(Integer.toString(countRatings));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setRatingButtonStatus3(final String userkey) {

        RatingsRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                   /* great_btn.setImageResource(R.drawable.great_color);
                    great_number.setText(Integer.toString(countRatings));*/
                   /* good_btn.setImageResource(R.drawable.good_color);
                    good_number.setText(Integer.toString(countRatings));*/
                    //poor_btn.setImageResource(R.drawable.bad_color);
                    poor_no.setText(Integer.toString(countRatings));
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                   /* great_btn.setImageResource(R.drawable.great_colorless);
                    great_number.setText(Integer.toString(countRatings));*/
                    /*good_btn.setImageResource(R.drawable.good_colorless);
                    good_number.setText(Integer.toString(countRatings));*/
                    //poor_btn.setImageResource(R.drawable.bad_colorless);
                    poor_no.setText(Integer.toString(countRatings));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
