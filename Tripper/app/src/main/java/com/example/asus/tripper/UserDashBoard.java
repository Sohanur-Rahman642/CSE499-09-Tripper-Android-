package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;

import android.view.WindowManager;


import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDashBoard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        mAuth=FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");

/*        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_dashboard_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);*/
    }








    /*private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch (menuItem.getItemId()){

                case R.id.search_packages:
                    selectedFragment = new fragment_user_search();
                    break;
                case R.id.explore_nearby:
                    selectedFragment = new fragment_explore_nearby();
                    break;
                case R.id.home:
                    selectedFragment = new fragment_user_home();
                    break;

                case R.id.profile:
                    selectedFragment = new fragment_user_profile();
                    break;





            }


            getSupportFragmentManager().beginTransaction().
                    replace(R.id.user_dashboard_fragment_container,
                            selectedFragment).commit();


            return true;


        }

    };*/
}
