package com.example.asus.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TouristsFragment extends Fragment {

    private Button fakesignout1;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_tourists, container, false);


        firebaseAuth = FirebaseAuth.getInstance();

        fakesignout1 = (Button) v.findViewById(R.id.fakesignout1);
        fakesignout1.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               /*firebaseAuth.signOut();
                                               getActivity().finish();
                                               Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();*/
                                               Intent i = new Intent(getActivity(), FindGuide.class);
                                               startActivity(i);
                                           }
                                       }
        );

        return v;
    }
}
