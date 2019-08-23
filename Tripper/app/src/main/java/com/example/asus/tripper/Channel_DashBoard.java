package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Channel_DashBoard extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    private DatabaseReference userRef;
    private DrawerLayout channelDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__dash_board);


        firebaseAuth=  FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        channelDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,channelDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        channelDrawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.travel_channel_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.running_packages); // change to whichever id should be default
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

        Intent setupIntent= new Intent(Channel_DashBoard.this, SetupUser.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void sendUserToLoginActivity() {

        Intent loginIntent= new Intent(Channel_DashBoard.this, LoginUser.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }















    @Override
    public void onBackPressed() {
        if(channelDrawer.isDrawerOpen(GravityCompat.START)){
            channelDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){

                case R.id.running_packages:
                    selectedFragment = new RunningFragment();
                    break;



                case R.id.completed_packages:
                    selectedFragment = new CompletedPackage();
                    break;


            }

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.travel_channel_fragment_container,
                            selectedFragment).commit();

            return true;


        }

    };


}
