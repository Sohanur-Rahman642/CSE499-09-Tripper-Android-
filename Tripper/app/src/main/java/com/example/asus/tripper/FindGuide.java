package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FindGuide extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton SearchButton;
    private EditText SearchInputText;

    private RecyclerView SearchList;

    private DatabaseReference allGuidesDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_guide);

        allGuidesDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mToolbar = findViewById(R.id.find_guide_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search");

        SearchList = findViewById(R.id.search_list);
        SearchList.setHasFixedSize(true);
        SearchList.setLayoutManager(new LinearLayoutManager(this));


        SearchButton = findViewById(R.id.search_btn);
        SearchInputText = findViewById(R.id.search_box);

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchBoxInput = SearchInputText.getText().toString();
                SearchGuide(searchBoxInput);
            }
        });
    }

    private void SearchGuide(String searchBoxInput) {

        Toast.makeText(this, "Searching...", Toast.LENGTH_LONG).show();

        Query searchGuideQuery = allGuidesDatabaseRef.orderByChild("username")
                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");

        FirebaseRecyclerOptions<FindGuideModel> options =
                new FirebaseRecyclerOptions.Builder<FindGuideModel>()
                        .setQuery(searchGuideQuery, FindGuideModel.class)
                        .build();

        FirebaseRecyclerAdapter<FindGuideModel, FindGuidesViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<FindGuideModel, FindGuidesViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FindGuidesViewHolder holder, int position, @NonNull FindGuideModel model) {

                final String userkey = getRef(position).getKey();




                holder.username.setText(model.getUsername());
                holder.phone.setText(model.getPhone());
                holder.country.setText(model.getCountry());
                //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.profileimage);
                Picasso.with(FindGuide.this).load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.profileimage);

               /* holder.package_image.setOnClickListener(new View.OnClickListener() {  //it can be holder.mView
                    @Override
                    public void onClick(View v) {

                        Intent clickpackageIntent = new Intent(FindGuide.this, ClickPackage.class);
                        clickpackageIntent.putExtra("guidekey", guidekey);
                        startActivity(clickpackageIntent);
                    }
                });*/

                holder.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent clickusernameIntent = new Intent(FindGuide.this, SeeGuidesProfileAfterConfirmingTrip.class);
                        clickusernameIntent.putExtra("userkey", userkey);
                        startActivity(clickusernameIntent);
                    }
                });


            }

            @NonNull
            @Override
            public FindGuidesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_guide_display_layout,viewGroup,false);

                FindGuide.FindGuidesViewHolder viewHolder = new FindGuide.FindGuidesViewHolder(view);
                return viewHolder;
            }
        };

        firebaseRecyclerAdapter.startListening();
        SearchList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class FindGuidesViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView  phone, username, country;
        CircleImageView profileimage;

        public FindGuidesViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            username = itemView.findViewById(R.id.all_guides_username);
            phone = itemView.findViewById(R.id.all_guides_profile_full_name);
            country = itemView.findViewById(R.id.all_guides_country);
            profileimage = itemView.findViewById(R.id.all_guides_profileimage);

        }

    }
}
