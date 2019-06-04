package com.example.asus.tripper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AcceptedTrips extends AppCompatActivity {

    private RecyclerView accepted_trips_recycle;
    private DatabaseReference acceptedTripsRef, usersRef;
    private FirebaseAuth mAuth;
    private String online_user_id, databaseUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_trips);

        mAuth = FirebaseAuth.getInstance();
        online_user_id = mAuth.getCurrentUser().getUid();
        acceptedTripsRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedPackages").child(online_user_id);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Packages");  //it could be users in bracket

        /*databaseUserId = getIntent().getExtras().get("gid").toString();*/  //gid

        accepted_trips_recycle = findViewById(R.id.accepted_trips_recycle);

        accepted_trips_recycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(AcceptedTrips.this);
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        accepted_trips_recycle.setLayoutManager(linearLayoutManager);

        DisplayAcceptedTrips();
    }

    private void DisplayAcceptedTrips() {

        FirebaseRecyclerOptions<ConfirmedTripsModel> options =
                new FirebaseRecyclerOptions.Builder<ConfirmedTripsModel>()
                        .setQuery(acceptedTripsRef, ConfirmedTripsModel.class)
                        .build();

        FirebaseRecyclerAdapter<ConfirmedTripsModel, AcceptedTrips.acceptedTripsViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<ConfirmedTripsModel, AcceptedTrips.acceptedTripsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final AcceptedTrips.acceptedTripsViewHolder holder, int position, @NonNull ConfirmedTripsModel model) {

                holder.setConfirm_type(model.getConfirm_type());

                final String usersId = getRef(position).getKey();

                usersRef.child(usersId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                                final String packageName = dataSnapshot.child("packagename").getValue().toString();
                                final String packageImage = dataSnapshot.child("packageimage").getValue().toString();
                                //final String packageFullname = dataSnapshot.child("fullname").getValue().toString();
                                final String packageDate = dataSnapshot.child("date").getValue().toString();
                                final String packagePrice = dataSnapshot.child("price").getValue().toString();
                                final String packageGroupMembers = dataSnapshot.child("groupmembers").getValue().toString();
                                final String packageStartDate = dataSnapshot.child("startdate").getValue().toString();
                                final String packageStartTime = dataSnapshot.child("starttime").getValue().toString();
                                final String packageEndDate = dataSnapshot.child("enddate").getValue().toString();
                                final String packageEndTime = dataSnapshot.child("endtime").getValue().toString();
                                final String packageMeetPoint = dataSnapshot.child("meetpoint").getValue().toString();
                                final String packageLocation = dataSnapshot.child("location").getValue().toString();
                                //final String packageProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                                //final String databaseUserId = dataSnapshot.child("gid").getValue().toString();     //new

                                holder.setPackagename(packageName);
                                holder.setPackageimage(getApplicationContext(), packageImage);
                                //holder.setFullname(packageFullname);
                                holder.setDate(packageDate);
                                holder.setPrice(packagePrice);
                                holder.setGroupmembers(packageGroupMembers);
                                holder.setStartdate(packageStartDate);
                                holder.setStarttime(packageStartTime);
                                holder.setEnddate(packageEndDate);
                                holder.setEndtime(packageEndTime);
                                holder.setMeetpoint(packageMeetPoint);
                                holder.setLocation(packageLocation);
                                // holder.setProfileimage(getActivity().getApplicationContext(), packageProfileImage);





                           /* if (online_user_id.equals(databaseUserId)) {


                                deletepackagebtn.setVisibility(View.VISIBLE);
                                deletepackagebtn.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                                editpackagebtn.setVisibility(View.VISIBLE);
                                confirm_package_btn.setVisibility(View.INVISIBLE);
                                cancel_package_btn.setVisibility(View.INVISIBLE);





                            }*/


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //final String userkey = getRef(position).getKey();




               /* holder.package_user_name.setText(model.getFullname());
                holder.package_name.setText(model.getPackagename());
                holder.package_date.setText(model.getDate());
                holder.package_time.setText(model.getTime());
                holder.package_price.setText(model.getPrice());
                holder.package_group_members.setText(model.getGroupmembers());
                Picasso.get().load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                holder.package_image.setOnClickListener(new View.OnClickListener() {  //it can be holder.mView
                    @Override
                    public void onClick(View v) {

                        Intent clickpackageIntent = new Intent(getActivity(), ClickPackage.class);
                        clickpackageIntent.putExtra("packagekey", packagekey);
                        startActivity(clickpackageIntent);
                    }
                });*/

                       /* holder.package_user_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent clickusernameIntent = new Intent(getActivity(), SeeGuidesProfileAfterConfirmingTrip.class);
                                clickusernameIntent.putExtra("userkey", userkey);
                                startActivity(clickusernameIntent);
                            }
                        });*/

               /* holder.package_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent clickpackagenameIntent = new Intent(getActivity(), ClickPackage.class);
                        clickpackagenameIntent.putExtra("packagekey", packagekey);
                        startActivity(clickpackagenameIntent);
                    }
                });*/

            }

            @NonNull
            @Override
            public AcceptedTrips.acceptedTripsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_confirmed_packages_layout,viewGroup,false);

                AcceptedTrips.acceptedTripsViewHolder viewHolder = new AcceptedTrips.acceptedTripsViewHolder(view);
                return viewHolder;
            }
        };

        firebaseRecyclerAdapter.startListening();
        accepted_trips_recycle.setAdapter(firebaseRecyclerAdapter);
    }


    public static class acceptedTripsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        /*TextView package_user_name, package_date, confirm_type, package_name, package_price, package_group_members;
        CircleImageView package_profile_image;
        ImageView package_image;*/

        public acceptedTripsViewHolder(@NonNull View itemView) {

            super(itemView);

            mView = itemView;

            /*package_user_name = itemView.findViewById(R.id.package_user_name);
            package_date = itemView.findViewById(R.id.package_date);
            confirm_type = itemView.findViewById(R.id.package_time);
            package_name = itemView.findViewById(R.id.package_name);
            package_price = itemView.findViewById(R.id.package_price);
            package_group_members = itemView.findViewById(R.id.package_group_members);
            package_profile_image = itemView.findViewById(R.id.package_profile_image);
            package_image = itemView.findViewById(R.id.package_image);*/
        }


       /* public void setFullname(String fullname){
            TextView name = (TextView)mView.findViewById(R.id.confirmed_username);
            name.setText(fullname);
        }*/

       /* public void setProfileimage(Context ctx, String profileimage){

            CircleImageView image = (CircleImageView) mView.findViewById(R.id.package_profile_image);
            Picasso.get().load(profileimage).into(image);
        }*/

        public void setPackageimage(Context ctx1, String packageimage){

            ImageView postimage = (ImageView) mView.findViewById(R.id.confirmed_package_image);
            Picasso.with(ctx1).load(packageimage).into(postimage);
        }

        public void setConfirm_type(String confirm_type){

            Button confirmType=(Button) mView.findViewById(R.id.confirmed_text);
            confirmType.setText(""+confirm_type);
        }

        public void setDate(String date){

            TextView postdate = (TextView) mView.findViewById(R.id.confirmed_date);
            postdate.setText(" "+date);
        }

        public void setPackagename(String packagename){

            TextView postname = (TextView) mView.findViewById(R.id.confirmed_package_name);
            postname.setText(packagename);
        }

        public void setPrice(String price){

            TextView postprice = (TextView) mView.findViewById(R.id.confirmed_package_price);
            postprice.setText(price);
        }

        public void setGroupmembers(String groupmembers){

            TextView postgroup = (TextView) mView.findViewById(R.id.confirmed_group_members);
            postgroup.setText(groupmembers);
        }

        public void setStartdate(String startdate){

            TextView poststartdate = (TextView) mView.findViewById(R.id.confirmed_start_date);
            poststartdate.setText(startdate);
        }

        public void setEnddate(String enddate){

            TextView postenddate = (TextView) mView.findViewById(R.id.confirmed_end_date);
            postenddate.setText(enddate);
        }

        public void setStarttime(String starttime){

            TextView poststarttime = (TextView) mView.findViewById(R.id.confirmed_start_time);
            poststarttime.setText(starttime);
        }

        public void setEndtime(String endtime){

            TextView postendtime = (TextView) mView.findViewById(R.id.confirmed_end_time);
            postendtime.setText(endtime);
        }

        public void setMeetpoint(String meetpoint){

            TextView postmeetpoint = (TextView) mView.findViewById(R.id.confirmed_meeting_point);
            postmeetpoint.setText(meetpoint);
        }

        public void setLocation(String location){

            TextView postlocation = (TextView) mView.findViewById(R.id.confirmed_location);
            postlocation.setText(location);
        }
    }

}
