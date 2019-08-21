package com.example.asus.tripper;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ratings extends AppCompatActivity {

    ImageButton great_btn, good_btn, poor_btn;
    TextView great_number, good_number, poor_number;

    private DatabaseReference RatingsRef, RatingsRef2, RatingsRef3;
    //Boolean RatingChecker = false;
    private String RatingChecker = "0";
    private String userkey, currentUserId;
    private FirebaseAuth mAuth;
    int countRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        userkey= getIntent().getExtras().get("userkey").toString();
        mAuth=FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 1.0), (int) (height * .2));

        RatingsRef = FirebaseDatabase.getInstance().getReference().child("Ratings");
        RatingsRef2 = FirebaseDatabase.getInstance().getReference().child("Ratings2");
        RatingsRef3 = FirebaseDatabase.getInstance().getReference().child("Ratings3");


        great_btn = findViewById(R.id.great_btn);
        good_btn = findViewById(R.id.good_btn);
        poor_btn = findViewById(R.id.poor_btn);

        great_number = findViewById(R.id.great_number);
        good_number = findViewById(R.id.good_number);
        poor_number = findViewById(R.id.poor_number);


        great_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingChecker = "1";     //true

                RatingsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       if (RatingChecker.equals("1")){       //true

                           if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                               RatingsRef.child(userkey).child(currentUserId).removeValue();
                               RatingChecker = "0";     //false
                           }
                           else {

                               RatingsRef.child(userkey).child(currentUserId).setValue("1");    //true
                               RatingChecker = "0";     //false
                               RatingsRef2.child(userkey).child(currentUserId).removeValue();
                               RatingChecker = "0";     //false
                               RatingsRef3.child(userkey).child(currentUserId).removeValue();
                               RatingChecker = "0";    //false
                           }
                       }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        setRatingButtonStatus(userkey);

        good_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingChecker = "1";

                RatingsRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (RatingChecker.equals("1")){

                            if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                                RatingsRef2.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                            }
                            else {

                                RatingsRef2.child(userkey).child(currentUserId).setValue("1");
                                RatingChecker = "0";
                                RatingsRef.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                                RatingsRef3.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        setRatingButtonStatus2(userkey);

        poor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RatingChecker = "1";

                RatingsRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (RatingChecker.equals("1")){

                            if (dataSnapshot.child(userkey).hasChild(currentUserId)){

                                RatingsRef3.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                            }
                            else {

                                RatingsRef3.child(userkey).child(currentUserId).setValue("1");
                                RatingChecker = "0";
                                RatingsRef2.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                                RatingsRef.child(userkey).child(currentUserId).removeValue();
                                RatingChecker = "0";
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        setRatingButtonStatus3(userkey);
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
                    poor_btn.setImageResource(R.drawable.bad_color);
                    poor_number.setText(Integer.toString(countRatings));
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                   /* great_btn.setImageResource(R.drawable.great_colorless);
                    great_number.setText(Integer.toString(countRatings));*/
                    /*good_btn.setImageResource(R.drawable.good_colorless);
                    good_number.setText(Integer.toString(countRatings));*/
                    poor_btn.setImageResource(R.drawable.bad_colorless);
                    poor_number.setText(Integer.toString(countRatings));
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
                    good_btn.setImageResource(R.drawable.good_color);
                    good_number.setText(Integer.toString(countRatings));
                    /*poor_btn.setImageResource(R.drawable.bad_color);
                    poor_number.setText(Integer.toString(countRatings));*/
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                   /* great_btn.setImageResource(R.drawable.great_colorless);
                    great_number.setText(Integer.toString(countRatings));*/
                    good_btn.setImageResource(R.drawable.good_colorless);
                    good_number.setText(Integer.toString(countRatings));
                   /* poor_btn.setImageResource(R.drawable.bad_colorless);
                    poor_number.setText(Integer.toString(countRatings));*/
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
                    great_btn.setImageResource(R.drawable.great_color);
                    great_number.setText(Integer.toString(countRatings));
                   /* good_btn.setImageResource(R.drawable.good_color);
                    good_number.setText(Integer.toString(countRatings));
                    poor_btn.setImageResource(R.drawable.bad_color);
                    poor_number.setText(Integer.toString(countRatings));*/
                }
                else {

                    countRatings = (int) dataSnapshot.child(userkey).getChildrenCount();
                    great_btn.setImageResource(R.drawable.great_colorless);
                    great_number.setText(Integer.toString(countRatings));
                    /*good_btn.setImageResource(R.drawable.good_colorless);
                    good_number.setText(Integer.toString(countRatings));
                    poor_btn.setImageResource(R.drawable.bad_colorless);
                    poor_number.setText(Integer.toString(countRatings));*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
