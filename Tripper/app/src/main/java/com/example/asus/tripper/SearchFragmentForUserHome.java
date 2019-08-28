package com.example.asus.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchFragmentForUserHome extends Fragment {


    FirebaseAuth firebaseAuth;
    MaterialSearchBar materialSearchBar;
    DatabaseReference searchForAgencyRef;
    RecyclerView rcvUserHomeSearch;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_fragment_for_user_home, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        materialSearchBar = rootView.findViewById(R.id.searchbar_for_tour_agencies);



        searchForAgencyRef = FirebaseDatabase.getInstance().getReference().child("Agency");

        rcvUserHomeSearch = (RecyclerView) rootView.findViewById(R.id.recyclerview_of_user_home_search);
        rcvUserHomeSearch.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcvUserHomeSearch.setLayoutManager(linearLayoutManager);



        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

            }

            @Override
            public void onButtonClicked(int buttonCode) {

                if (buttonCode == MaterialSearchBar.BUTTON_NAVIGATION) {

                    //opening or closing drawer layout

                } else if (buttonCode == MaterialSearchBar.BUTTON_BACK) {
                    materialSearchBar.disableSearch();
                }

            }
        });



        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                SearchAgencies(materialSearchBar.getText());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });






        return rootView;
    }

    private void SearchAgencies(String searchBoxInput) {

        // Toast.makeText(this, "Searching...", Toast.LENGTH_LONG).show();

        Query searchAgencyQuery = searchForAgencyRef.orderByChild("ownerName")
                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");

        FirebaseRecyclerOptions<FindAgencyGuideModel> options =
                new FirebaseRecyclerOptions.Builder<FindAgencyGuideModel>()
                        .setQuery(searchAgencyQuery, FindAgencyGuideModel.class)
                        .build();

        FirebaseRecyclerAdapter<FindAgencyGuideModel, SearchFragmentForUserHome.SearchForUserViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindAgencyGuideModel, SearchFragmentForUserHome.SearchForUserViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull SearchForUserViewHolder holder, int position, @NonNull FindAgencyGuideModel model) {







                holder.name.setText(model.getOwnerName());
                holder.location.setText(model.getLocation());


                //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.profileimage);
                Glide.with(SearchFragmentForUserHome.this).load(model.getProfileimage()).centerCrop().into(holder.searchChaanelProPic);


            }

            @NonNull
            @Override
            public SearchForUserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_home_searchview_content,viewGroup,false);

                SearchFragmentForUserHome.SearchForUserViewHolder viewHolder = new SearchFragmentForUserHome.SearchForUserViewHolder(view);
                return viewHolder;
            }


            };


        firebaseRecyclerAdapter.startListening();
        rcvUserHomeSearch.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SearchForUserViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView   name, location;
        CircleImageView searchChaanelProPic;

        public SearchForUserViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            name = itemView.findViewById(R.id.user_search_channel_name);
            location = itemView.findViewById(R.id.user_search_channel_location);
            searchChaanelProPic = itemView.findViewById(R.id.user_search_channel_propic);

        }

    }
}
