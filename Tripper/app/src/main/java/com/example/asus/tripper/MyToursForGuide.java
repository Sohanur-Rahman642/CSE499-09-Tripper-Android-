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

public class MyToursForGuide extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tours_for_guide);
        init();

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
