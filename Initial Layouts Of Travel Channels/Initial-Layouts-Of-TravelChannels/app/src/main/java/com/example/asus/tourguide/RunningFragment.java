package com.example.asus.tourguide;

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

public class RunningFragment extends Fragment {

    List<Package> packageList;

    //the recyclerview
    RecyclerView recyclerView;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_running,container,false);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        packageList = new ArrayList<>();


        packageList.add(
                new Package(1,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(2,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                                "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(3,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));
        packageList.add(
                new Package(4,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(5,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                        "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(6,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));
        packageList.add(
                new Package(7,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(8,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                        "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(9,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));




        packageList.add(
                new Package(10,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(11,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                        "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(12,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));

        packageList.add(
                new Package(13,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(14,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                        "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(15,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));

        packageList.add(
                new Package(16,
                        R.drawable.mountain_trek_title,
                        "Mountain Trek",
                        R.drawable.mountain_trek,
                        "Mountain Trek in Canada",
                        "Mountain Trek is one of the most awarded fitness retreat and health spas in Canada."));

        packageList.add(
                new Package(17,
                        R.drawable.bali_title,
                        "Bali",
                        R.drawable.balinew,

                        "Bali Trip",
                        "Bali is an Indonesian island known for its forested volcanic mountains, iconic rice paddies, beaches and coral reefs."));

        packageList.add(
                new Package(18,
                        R.drawable.alska_title,
                        "Alaska",
                        R.drawable.alaska,
                        "Alaska Adventure",
                        "Alaska, northwest of Canada, is the largest and most sparsely populated U.S. state. "));




        PackageAdapter adapter = new PackageAdapter(getContext(), packageList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;



    }


}
