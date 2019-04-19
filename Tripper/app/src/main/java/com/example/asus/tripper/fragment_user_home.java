package com.example.asus.tripper;

import android.content.Intent;
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

import com.example.asus.tripper.RegisterAndLogin.SetupUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fragment_user_home extends Fragment {

   /* private FirebaseAuth mAuth;
    private DatabaseReference userRef;*/

    List<Package> packageList;

    //the recyclerview
    RecyclerView recyclerView;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_home, container, false);

        /*mAuth=FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");*/


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

  /*  @Override
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

        Intent setupIntent= new Intent(getActivity(), SetupUser.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        getActivity().finish();
    }

    private void sendUserToLoginActivity() {

        Intent loginIntent= new Intent(getActivity(), MainDashBoard.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        getActivity().finish();
    }*/
}
