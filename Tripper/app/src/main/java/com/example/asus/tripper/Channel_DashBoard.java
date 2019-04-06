package com.example.asus.tripper;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class Channel_DashBoard extends AppCompatActivity {

    private DrawerLayout channelDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__dash_board);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        channelDrawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,channelDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        channelDrawer.addDrawerListener(toggle);
        toggle.syncState();

        BottomNavigationView bottomNavigationView = findViewById(R.id.travel_channel_bnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
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

                case R.id.add_package:
                    selectedFragment = new AddPackagesFragment();
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
