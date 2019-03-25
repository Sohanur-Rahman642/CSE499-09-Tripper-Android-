package com.example.tourguide;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeForUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_for_user);

        //FloatingActionButton floatingActionButton=findViewById(R.id.floatforuserhome);

        BottomNavigationView bottomNav= findViewById(R.id.bottom_nav_user);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        BottomNavigationView topNav= findViewById(R.id.top_nav_user);
        topNav.setItemIconTintList(null);
        topNav.setOnNavigationItemSelectedListener(navListener1);
        //floatingActionButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //Toast.makeText(HomeForUser.this, "Clicked",Toast.LENGTH_SHORT).show();
            //}
        //});
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener1= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.top_country:
                    selectedFragment= new CountryFragment();
                    break;

                case R.id.top_city:
                    selectedFragment= new CityFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user, selectedFragment).commit();

            return true;
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment= null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment= new HomeFragment();
                    break;

                case R.id.nav_history:
                    selectedFragment= new HistoryFragment();
                    break;

                case R.id.nav_user_inbox:
                    selectedFragment= new UserInboxFragment();
                    break;

                case R.id.nav_user_profile:
                    selectedFragment= new UserProfileFragment();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user, selectedFragment).commit();

            return true;
        }
    };
}
