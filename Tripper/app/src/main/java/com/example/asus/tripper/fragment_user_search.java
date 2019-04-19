package com.example.asus.tripper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;


public class fragment_user_search extends Fragment {

    private MenuItem mSearchItem;
    private Toolbar mToolbar;
    SearchView searchView;
    private Context mContext;
    private Button fakesignout;
    private FirebaseAuth firebaseAuth;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_search, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        setHasOptionsMenu(true);

        fakesignout = (Button) rootView.findViewById(R.id.fakesignout);
        fakesignout.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               firebaseAuth.signOut();
                                               getActivity().finish();
                                               Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                                               Intent i = new Intent(getActivity(), MainDashBoard.class);
                                               startActivity(i);
                                           }
                                       }
        );


        return rootView;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.user_search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView search = (SearchView) item.getActionView();
        search.setLayoutParams(new Toolbar.LayoutParams(Gravity.RIGHT));





    }



}


