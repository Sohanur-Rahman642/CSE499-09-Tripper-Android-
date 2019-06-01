package com.example.asus.tripper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TouristsFragment extends Fragment {

    private RecyclerView confirmed_trips_recycle;
    private DatabaseReference confirmedTripsRef, usersRef;
    private FirebaseAuth mAuth;
    private String online_user_id;


   /* TextView confirmed_trips, accepted_trips;   //new
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;           //upto this*/



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.fragment_tourists, container, false);

        mAuth = FirebaseAuth.getInstance();
        online_user_id = mAuth.getCurrentUser().getUid();
        confirmedTripsRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedPackages").child(online_user_id);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Packages");  //it could be users in bracket

        confirmed_trips_recycle = v.findViewById(R.id.confirmed_trips_recycle);


       /* confirmed_trips = v.findViewById(R.id.confirmed_trips_text);    // new
        accepted_trips = v.findViewById(R.id.accepted_trips_text);
        viewPager = v.findViewById(R.id.viewpager_for_confirmed);       //upto this*/


       /* pagerViewAdapter = new PagerViewAdapter(getActivity().getSupportFragmentManager());    //new

        viewPager.setAdapter(pagerViewAdapter);

        confirmed_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(0);
            }
        });

        accepted_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(1);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                onChangeTab(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });                                                                 //upto this*/

        confirmed_trips_recycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        confirmed_trips_recycle.setLayoutManager(linearLayoutManager);

        DisplayConfirmedTrips();


        return v;
    }

   /* private void onChangeTab(int i) {                            //new

        if (i == 0){

            confirmed_trips.setTextSize(25);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.brightcolor));   //need api 23

            accepted_trips.setTextSize(20);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.lightcolor));
        }

        if (i == 1){

            confirmed_trips.setTextSize(20);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.lightcolor));   //need api 23

            accepted_trips.setTextSize(25);
            //confirmed_trips.setTextColor(getActivity().getColor(R.color.brightcolor));
        }
    }                                    //upto this*/

    private void DisplayConfirmedTrips() {

        FirebaseRecyclerOptions<ConfirmedTripsModel> options =
                new FirebaseRecyclerOptions.Builder<ConfirmedTripsModel>()
                        .setQuery(confirmedTripsRef, ConfirmedTripsModel.class)
                        .build();

        FirebaseRecyclerAdapter<ConfirmedTripsModel, confirmedTripsViewHolder> firebaseRecyclerAdapter
                 = new FirebaseRecyclerAdapter<ConfirmedTripsModel, confirmedTripsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final confirmedTripsViewHolder holder, int position, @NonNull ConfirmedTripsModel model) {

                holder.setConfirm_type(model.getConfirm_type());

                final String usersId = getRef(position).getKey();

                usersRef.child(usersId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()){

                            final String packageName = dataSnapshot.child("packagename").getValue().toString();
                            final String packageImage = dataSnapshot.child("packageimage").getValue().toString();
                            final String packageFullname= dataSnapshot.child("fullname").getValue().toString();
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

                           holder.setPackagename(packageName);
                           holder.setPackageimage(getActivity().getApplicationContext(), packageImage);
                           holder.setFullname(packageFullname);
                           holder.setDate(packageDate);
                           holder.setPrice(packagePrice);
                           holder.setGroupmembers(packageGroupMembers);
                           holder.setStartdate(packageStartDate);
                           holder.setStarttime(packageStartTime);
                           holder.setEnddate(packageStartDate);
                           holder.setEndtime(packageEndTime);
                           holder.setMeetpoint(packageMeetPoint);
                           holder.setLocation(packageLocation);
                          // holder.setProfileimage(getActivity().getApplicationContext(), packageProfileImage);



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
            public confirmedTripsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_confirmed_packages_layout,viewGroup,false);

                TouristsFragment.confirmedTripsViewHolder viewHolder = new TouristsFragment.confirmedTripsViewHolder(view);
                return viewHolder;
            }
        };

        firebaseRecyclerAdapter.startListening();
        confirmed_trips_recycle.setAdapter(firebaseRecyclerAdapter);
    }

    public static class confirmedTripsViewHolder extends RecyclerView.ViewHolder{

        View mView;

        /*TextView package_user_name, package_date, confirm_type, package_name, package_price, package_group_members;
        CircleImageView package_profile_image;
        ImageView package_image;*/

        public confirmedTripsViewHolder(@NonNull View itemView) {

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


        public void setFullname(String fullname){
            TextView name = (TextView)mView.findViewById(R.id.confirmed_username);
            name.setText(fullname);
        }

       /* public void setProfileimage(Context ctx, String profileimage){

            CircleImageView image = (CircleImageView) mView.findViewById(R.id.package_profile_image);
            Picasso.get().load(profileimage).into(image);
        }*/

        public void setPackageimage(Context ctx1, String packageimage){

            ImageView postimage = (ImageView) mView.findViewById(R.id.confirmed_package_image);
            Picasso.get().load(packageimage).into(postimage);
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
