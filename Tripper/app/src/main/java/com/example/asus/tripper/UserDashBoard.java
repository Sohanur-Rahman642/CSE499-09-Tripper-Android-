package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_dashboard_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser= mAuth.getCurrentUser();

        if(currentUser==null){

            sendUserToLoginActivity();
        }
        else {

            CheckUserExistence();
        }
    }

    private void CheckUserExistence() {

        final String current_user_id = mAuth.getCurrentUser().getUid();

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

        Intent setupIntent= new Intent(UserDashBoard.this, SetupUser.class);   //before it was SetupUser
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void sendUserToLoginActivity() {

        Intent loginIntent= new Intent(UserDashBoard.this, MainDashBoard.class);   //it was maindashboard before
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
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

                case R.id.settings:
                    selectedFragment = new fragment_user_settings();
                    break;



               /* case R.id.add_package:
                    selectedFragment = new AddPackagesFragment();
                    break;*/


            }

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.user_dashboard_fragment_container,
                            selectedFragment).commit();


            return true;


        }

    };
}
