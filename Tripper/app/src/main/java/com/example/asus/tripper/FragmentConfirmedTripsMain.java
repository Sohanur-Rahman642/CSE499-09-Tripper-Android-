package com.example.asus.tripper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentConfirmedTripsMain extends Fragment {

   TextView confirmed_trips, accepted_trips;
   ViewPager viewPager;
   PagerViewAdapter pagerViewAdapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_tourists, container, false);

        confirmed_trips = v.findViewById(R.id.confirmed_trips_text);
        accepted_trips = v.findViewById(R.id.accepted_trips_text);


        viewPager = v.findViewById(R.id.viewpager_for_confirmed);

        pagerViewAdapter = new PagerViewAdapter(getActivity().getSupportFragmentManager());

        viewPager.setAdapter(pagerViewAdapter);

        confirmed_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(0);
            }
        });

        accepted_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                onChangeTab(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        return v;
    }

    private void onChangeTab(int i) {

        if (i == 0){

            confirmed_trips.setTextSize(25);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.brightcolor));   //need api 23

            accepted_trips.setTextSize(20);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.lightcolor));
        }

        if (i == 1){

            confirmed_trips.setTextSize(20);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.lightcolor));   //need api 23

            accepted_trips.setTextSize(25);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.brightcolor));
        }
    }
}
