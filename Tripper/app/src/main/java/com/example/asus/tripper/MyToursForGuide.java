package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyToursForGuide extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private DatabaseReference userRef;


   /* private FloatingActionButton floatingActionButton;
    private void init() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatformytours);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent i = new Intent(MyToursForGuide.this, AddingPackages.class);
                                                        startActivity(i);
                                                    }
                                                }
        );

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tours_for_guide);
        //init();

       firebaseAuth=  FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

        //FloatingActionButton floatingActionButton=findViewById(R.id.floatformytours);

        BottomNavigationView bottomNav= findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

       /* BottomNavigationView topNav= findViewById(R.id.top_nav_guide);
        topNav.setItemIconTintList(null);
        topNav.setOnNavigationItemSelectedListener(navListener1);*/

        //floatingActionButton.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        //Toast.makeText(MyToursForGuide.this, "Clicked",Toast.LENGTH_SHORT).show();
        //}
        //});
    }

   /* private BottomNavigationView.OnNavigationItemSelectedListener navListener1= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.active_package:
                    selectedFragment= new ActivePackageFragment();
                    break;

                case R.id.completed_package:
                    selectedFragment= new CompletedPackageFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };*/

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser= firebaseAuth.getCurrentUser();

        if(currentUser==null){

            sendUserToLoginActivity();
        }
        else {

            CheckUserExistence();
        }
    }

    private void CheckUserExistence() {

        final String current_user_id = firebaseAuth.getCurrentUser().getUid();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(current_user_id)){

                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SendUserToSetupActivity() {

        Intent setupIntent= new Intent(MyToursForGuide.this, SetupUser.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void sendUserToLoginActivity() {

        Intent loginIntent= new Intent(MyToursForGuide.this, MainDashBoard.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.nav_mytours:
                    selectedFragment= new MyToursFragment();
                    break;

                case R.id.nav_tourists:
                    selectedFragment= new TouristsFragment();
                    break;

                case R.id.nav_inbox:
                    selectedFragment= new InboxFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment= new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}
