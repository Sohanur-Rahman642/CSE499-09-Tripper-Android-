package com.example.asus.tripper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter {


    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = null;

        switch (i){

            case 0:     //0
                fragment = new TouristsFragment();
                break;
            case 1:
                fragment = new AcceptedTripsFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;  //4
    }
}
