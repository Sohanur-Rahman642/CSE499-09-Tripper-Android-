package com.example.asus.tripper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class fragment_user_home extends Fragment {

    List<Package> packageList;

    //the recyclerview
    RecyclerView recyclerView;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_home, container, false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        packageList = new ArrayList<>();


        packageList.add(
                new Package(1,
                        R.drawable.saintmartinprofilenew,
                        "Saint-Martin Trek",
                        R.drawable.saintmartinnew,
                        "Saint-Martin in Canada",
                        "Saint-Martin Trek is one of the most awarded fitness retreat and health spas in Bangladesh."));

        packageList.add(
                new Package(2,
                        R.drawable.bandarbanprofilenew,
                        "Bandarban",
                        R.drawable.bandarbannew,

                        "Bandarban Trip",
                        "Bandarban is an Indonesian island known for its forested volcanic mountains"));


        packageList.add(
                new Package(3,
                        R.drawable.sajekprofile,
                        "Sajek Trek",
                        R.drawable.sajek,
                        "Sajek Trek in Khagrachori",
                        "Sajek is known for beautiful mountains surrounding with fog."));

        packageList.add(
                new Package(4,
                        R.drawable.saintmartinprofilenew,
                        "Saint-Martin Trek",
                        R.drawable.saintmartinnew,
                        "Saint-Martin in Canada",
                        "Saint-Martin Trek is one of the most awarded fitness retreat and health spas in Bangladesh."));

        packageList.add(
                new Package(5,
                        R.drawable.bandarbanprofilenew,
                        "Bandarban",
                        R.drawable.bandarbannew,

                        "Bandarban Trip",
                        "Bandarban is an Indonesian island known for its forested volcanic mountains"));

        packageList.add(
                new Package(6,
                        R.drawable.sajekprofile,
                        "Sajek Trek",
                        R.drawable.sajek,
                        "Sajek Trek in Khagrachori",
                        "Sajek is known for beautiful mountains surrounding with fog."));

        packageList.add(
                new Package(7,
                        R.drawable.saintmartinprofilenew,
                        "Saint-Martin Trek",
                        R.drawable.saintmartinnew,
                        "Saint-Martin in Canada",
                        "Saint-Martin Trek is one of the most awarded fitness retreat and health spas in Bangladesh."));

        packageList.add(
                new Package(8,
                        R.drawable.bandarbanprofilenew,
                        "Bandarban",
                        R.drawable.bandarbannew,

                        "Bandarban Trip",
                        "Bandarban is an Indonesian island known for its forested volcanic mountains"));


        packageList.add(
                new Package(9,
                        R.drawable.sajekprofile,
                        "Sajek Trek",
                        R.drawable.sajek,
                        "Sajek Trek in Khagrachori",
                        "Sajek is known for beautiful mountains surrounding with fog."));

        packageList.add(
                new Package(10,
                        R.drawable.saintmartinprofilenew,
                        "Saint-Martin Trek",
                        R.drawable.saintmartinnew,
                        "Saint-Martin in Canada",
                        "Saint-Martin Trek is one of the most awarded fitness retreat and health spas in Bangladesh."));

        packageList.add(
                new Package(11,
                        R.drawable.bandarbanprofilenew,
                        "Bandarban",
                        R.drawable.bandarbannew,

                        "Bandarban Trip",
                        "Bandarban is an Indonesian island known for its forested volcanic mountains"));


        packageList.add(
                new Package(12,
                        R.drawable.sajekprofile,
                        "Sajek Trek",
                        R.drawable.sajek,
                        "Sajek Trek in Khagrachori",
                        "Sajek is known for beautiful mountains surrounding with fog."));



        PackageAdapter adapter = new PackageAdapter(getContext(), packageList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}
