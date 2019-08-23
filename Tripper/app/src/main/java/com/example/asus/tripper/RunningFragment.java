package com.example.asus.tripper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

public class RunningFragment extends Fragment {


    private FirebaseAuth mAuth;

    private ImageView SearchButton;
    // private EditText SearchInputText;


    private RecyclerView channel_packages_recycle;

    private DatabaseReference packagesRef, usersRef;
    private Button confirmed_text;


    private FloatingActionButton channel_add_package_floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_running, container, false);


        mAuth = FirebaseAuth.getInstance();
        packagesRef = FirebaseDatabase.getInstance().getReference().child("Channel Packages");


        channel_packages_recycle = (RecyclerView) rootView.findViewById(R.id.channel_running_recyclerView);
        channel_packages_recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        channel_packages_recycle.setHasFixedSize(true);
        channel_packages_recycle.setItemViewCacheSize(20);
        channel_packages_recycle.setDrawingCacheEnabled(true);
        channel_packages_recycle.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        channel_add_package_floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.channel_add_package_floating_btn);
        channel_add_package_floatingActionButton.setOnClickListener(new View.OnClickListener() {

                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            Intent i = new Intent(getActivity(), ChannelAddPackage.class);
                                                                            startActivity(i);
                                                                        }
                                                                    }
        );


        DisplayAllPackagesOfChannel();


        return rootView;

    }

    private void DisplayAllPackagesOfChannel() {


        Query SortPackagesInDescendingOrder = packagesRef.orderByChild("counter");

        FirebaseRecyclerOptions<ChannelPackageModel> options =
                new FirebaseRecyclerOptions.Builder<ChannelPackageModel>()
                        .setQuery(SortPackagesInDescendingOrder, ChannelPackageModel.class)
                        .build();


        FirebaseRecyclerAdapter<ChannelPackageModel, RunningFragment.PackageViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ChannelPackageModel, RunningFragment.PackageViewHolder>(options)

                {
                    @Override
                    protected void onBindViewHolder(@NonNull RunningFragment.PackageViewHolder holder, int position, @NonNull ChannelPackageModel model) {

                        /*holder.setFullname(model.getFullname());
                        holder.setProfileimage(getActivity().getApplicationContext(),model.getProfileimage());
                        holder.setPackageimage(getActivity().getApplicationContext(),model.getPackageimage());
                        holder.setDate(model.getDate());
                        holder.setTime(model.getTime());
                        holder.setPackagename(model.getPackagename());
                        holder.setPrice(model.getPrice());
                        holder.setGroupmembers(model.getGroupmembers());*/

                        final String packagekey = getRef(position).getKey();

                        //final String userkey = getRef(position).getKey();
                        //final String userkey= getIntent().getExtras().get("userkey").toString();


                        holder.confirmed_text.setVisibility(View.INVISIBLE);

                        holder.package_user_name.setText(model.getFullname());
                        holder.package_name.setText(model.getPackagename());
                        holder.package_date.setText(model.getDate());
                        holder.package_time.setText(model.getTime());
                        holder.package_price.setText(model.getPrice());
                        holder.package_group_members.setText(model.getGroupmembers());
                        //Picasso.get().load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                        //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                        Glide.with(getActivity()).load(model.getPackageimage()).placeholder(R.drawable.loadingpic).centerCrop().into(holder.package_image);
                        Glide.with(getActivity()).load(model.getProfileimage()).placeholder(R.drawable.userpic).into(holder.package_profile_image);

                        holder.package_image.setOnClickListener(new View.OnClickListener() {  //it can be holder.mView
                            @Override
                            public void onClick(View v) {

                                Intent clickpackageIntent = new Intent(getActivity(), ClickPackage.class);
                                clickpackageIntent.putExtra("packagekey", packagekey);
                                startActivity(clickpackageIntent);
                            }
                        });

                       /* holder.package_user_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent clickusernameIntent = new Intent(getActivity(), SeeGuidesProfileAfterConfirmingTrip.class);
                                clickusernameIntent.putExtra("userkey", userkey);
                                startActivity(clickusernameIntent);
                            }
                        });*/

                        holder.package_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent clickpackagenameIntent = new Intent(getActivity(), ClickPackage.class);
                                clickpackagenameIntent.putExtra("packagekey", packagekey);
                                startActivity(clickpackagenameIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public RunningFragment.PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel_all_packages_layout,viewGroup,false);
                        PackageViewHolder viewHolder = new PackageViewHolder(view);
                        return viewHolder;
                    }
                };


        firebaseRecyclerAdapter.startListening();
        channel_packages_recycle.setAdapter(firebaseRecyclerAdapter);



    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {

        View mView;

        TextView package_user_name, package_date, package_time, package_name, package_price, package_group_members;
        CircleImageView package_profile_image;
        ImageView package_image;
        Button confirmed_text;


        public PackageViewHolder(@NonNull View itemView) {
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
            confirmed_text = itemView.findViewById(R.id.confirmed_text);


        }


    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentGuide= mAuth.getCurrentUser();

        if(currentGuide==null){

            sendGuideToLoginActivity();
        }
    }

    private void sendGuideToLoginActivity() {

        Intent loginIntent= new Intent(getActivity(), LoginUser.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        getActivity().finish();
    }
}
