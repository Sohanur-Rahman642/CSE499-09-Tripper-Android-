package com.example.asus.tripper;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTrips extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView my_trips_recycle;

    private FirebaseAuth mAuth;
    private DatabaseReference myTripsRef;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trips);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        myTripsRef = FirebaseDatabase.getInstance().getReference().child("Packages");


        mToolbar = findViewById(R.id.my_trips_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("My Trips");

        my_trips_recycle = findViewById(R.id.my_trips_recycle);
        my_trips_recycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        my_trips_recycle.setLayoutManager(linearLayoutManager);


        DisplayMyAllTrips();
    }

    private void DisplayMyAllTrips() {

        Query myTripsQuery = myTripsRef.orderByChild("gid")
                .startAt(currentUserID).endAt(currentUserID + "\uf8ff");

        FirebaseRecyclerOptions<PackagesModel> options =
                new FirebaseRecyclerOptions.Builder<PackagesModel>()
                        .setQuery(myTripsQuery, PackagesModel.class)
                        .build();

        FirebaseRecyclerAdapter<PackagesModel, MyTripsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<PackagesModel, MyTripsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyTripsViewHolder holder, int position, @NonNull PackagesModel model) {


                final String packagekey = getRef(position).getKey();

                final String userkey = getRef(position).getKey();




                holder.package_user_name.setText(model.getFullname());
                holder.package_name.setText(model.getPackagename());
                holder.package_date.setText(model.getDate());
                holder.package_time.setText(model.getTime());
                holder.package_price.setText(model.getPrice());
                holder.package_group_members.setText(model.getGroupmembers());
                //Picasso.get().load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                Picasso.with(MyTrips.this).load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                Picasso.with(MyTrips.this).load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);


            }

            @NonNull
            @Override
            public MyTripsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_packages_layout,viewGroup,false);

                MyTrips.MyTripsViewHolder viewHolder = new MyTrips.MyTripsViewHolder(view);
                return viewHolder;
            }
        };

        firebaseRecyclerAdapter.startListening();
        my_trips_recycle.setAdapter(firebaseRecyclerAdapter);
    }






    public static class MyTripsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView package_user_name, package_date, package_time, package_name, package_price, package_group_members;
        CircleImageView package_profile_image;
        ImageView package_image;

        public MyTripsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            package_user_name = itemView.findViewById(R.id.package_user_name);
            package_date = itemView.findViewById(R.id.package_date);
            package_time = itemView.findViewById(R.id.package_time);
            package_name = itemView.findViewById(R.id.package_name);
            package_price = itemView.findViewById(R.id.package_price);
            package_group_members = itemView.findViewById(R.id.package_group_members);
            package_profile_image = itemView.findViewById(R.id.package_profile_image);
            package_image = itemView.findViewById(R.id.package_image);
        }
    }
}
