package com.example.asus.tripper;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

    private DatabaseReference userRef,agencyRef;
    private DrawerLayout channelDrawer;
    NavigationView navigationView;
    ImageView nav_header_profile_image;
    TextView nav_header_channel_name;
    TextView nav_header_channel_location;
    View navHeader;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__dash_board);

        firebaseAuth=  FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        agencyRef = FirebaseDatabase.getInstance().getReference().child("Agency");


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        channelDrawer = findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.channel_nav_view);

        navHeader = navigationView.getHeaderView(0);

        nav_header_channel_name = (TextView) navHeader.findViewById(R.id.nave_header_channel_name);
        nav_header_channel_location = (TextView) navHeader.findViewById(R.id.nav_header_channel_location);
        //imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.nave_header_channel_propic);
        nav_header_profile_image = (ImageView) navHeader.findViewById(R.id.nave_header_channel_propic);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,channelDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        channelDrawer.addDrawerListener(toggle);
        toggle.syncState();



        BottomNavigationView bottomNavigationView = findViewById(R.id.travel_channel_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);


        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.running_packages); // change to whichever id should be default
        }


        //NAV VIEW CLICK LISTENER

        setUpNavigationView();
    }

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

        agencyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChild(current_user_id)){

                    SendUserToAgencySignUpActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }






    private void SendUserToAgencySignUpActivity() {

        Intent setupIntent= new Intent(Channel_DashBoard.this, AgencySignUpActivity.class);
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



    public void  setUpNavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.nav_home:
                        Fragment selectedFragment = new RunningFragment();
                        break;

                    case R.id.nav_profile:
                        break;

                    case R.id.nav_add_guides:
                        Intent intent = new Intent(Channel_DashBoard.this,ChannelAddGuideActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_settings:
                        break;
                }


                return true;
            }
        });
    }


}
