package com.example.asus.tripper;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class UserDashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_dashboard_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
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
