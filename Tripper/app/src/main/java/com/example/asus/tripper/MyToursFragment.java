package com.example.asus.tripper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyToursFragment extends Fragment  {

    private FirebaseAuth mAuth;

    private ImageView SearchButton;
   // private EditText SearchInputText;

    private FloatingActionButton floatingActionButton;

    private RecyclerView packages_recycle;

    private DatabaseReference packagesRef;
    private Button confirmed_text;
    //private Query query;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_mytours, container, false);

        mAuth=FirebaseAuth.getInstance();
        packagesRef = FirebaseDatabase.getInstance().getReference().child("Packages");
        //query=FirebaseDatabase.getInstance().getReference().child("Packages");

        packages_recycle = (RecyclerView) v.findViewById(R.id.packages_recycle);
        packages_recycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        packages_recycle.setLayoutManager(linearLayoutManager);

        //confirmed_text=v.findViewById(R.id.confirmed_text);
        //confirmed_text.setVisibility(View.INVISIBLE);

        SearchButton = (ImageView) v.findViewById(R.id.searchbtn_for_mytours);
        //SearchInputText = (EditText) v.findViewById(R.id.search_box1);

        floatingActionButton = (FloatingActionButton) v.findViewById(R.id.floatformytours);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent i = new Intent(getActivity(), AddingPackages.class);
                                                        startActivity(i);
                                                    }
                                                }
        );

        SearchButton.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent i = new Intent(getActivity(), SearchForPackage.class);
                                                        startActivity(i);
                                                    }
                                                }
        );

       /* SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchBoxInput = SearchInputText.getText().toString();
                DisplayAllPackages(searchBoxInput);
            }
        });*/

        DisplayAllPackages();


        return v;



    }

    private void DisplayAllPackages() {

       /* Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_LONG).show();


        Query searchPackageQuery = packagesRef.orderByChild("packagename")
                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");*/

       Query SortPackagesInDescendingOrder = packagesRef.orderByChild("counter");  //neww

        FirebaseRecyclerOptions<PackagesModel> options =
                new FirebaseRecyclerOptions.Builder<PackagesModel>()
                .setQuery(SortPackagesInDescendingOrder, PackagesModel.class)
                .build();

        FirebaseRecyclerAdapter<PackagesModel, PackageViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PackagesModel, PackageViewHolder>(options)

                         {
                    @Override
                    protected void onBindViewHolder(@NonNull PackageViewHolder holder, int position, @NonNull PackagesModel model) {

                        /*holder.setFullname(model.getFullname());
                        holder.setProfileimage(getActivity().getApplicationContext(),model.getProfileimage());
                        holder.setPackageimage(getActivity().getApplicationContext(),model.getPackageimage());
                        holder.setDate(model.getDate());
                        holder.setTime(model.getTime());
                        holder.setPackagename(model.getPackagename());
                        holder.setPrice(model.getPrice());
                        holder.setGroupmembers(model.getGroupmembers());*/

                        final String packagekey = getRef(position).getKey();

                        final String userkey = getRef(position).getKey();

                        holder.confirmed_text.setVisibility(View.INVISIBLE);

                        holder.package_user_name.setText(model.getFullname());
                        holder.package_name.setText(model.getPackagename());
                        holder.package_date.setText(model.getDate());
                        holder.package_time.setText(model.getTime());
                        holder.package_price.setText(model.getPrice());
                        holder.package_group_members.setText(model.getGroupmembers());
                        //Picasso.get().load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                        //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                        Picasso.with(getActivity()).load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                        Picasso.with(getActivity()).load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                        holder.package_image.setOnClickListener(new View.OnClickListener() {  //it can be holder.mView
                            @Override
                            public void onClick(View v) {

                                Intent clickpackageIntent = new Intent(getActivity(), ClickPackage.class);
                                clickpackageIntent.putExtra("packagekey", packagekey);
                                startActivity(clickpackageIntent);
                            }
                        });

                        holder.package_user_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent clickusernameIntent = new Intent(getActivity(), SeeGuidesProfileAfterConfirmingTrip.class);
                                clickusernameIntent.putExtra("userkey", userkey);
                                startActivity(clickusernameIntent);
                            }
                        });

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
                    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_packages_layout,viewGroup,false);

                        PackageViewHolder viewHolder = new PackageViewHolder(view);
                        return viewHolder;
                    }
                };


        firebaseRecyclerAdapter.startListening();
        packages_recycle.setAdapter(firebaseRecyclerAdapter);





    }


    public static class PackageViewHolder extends RecyclerView.ViewHolder{

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

      /*  public void setFullname(String fullname){
            TextView name = (TextView)mView.findViewById(R.id.package_user_name);
            name.setText(fullname);
        }

        public void setProfileimage(Context ctx, String profileimage){

            CircleImageView image = (CircleImageView) mView.findViewById(R.id.package_profile_image);
            Picasso.get().load(profileimage).into(image);
        }

        public void setPackageimage(Context ctx1, String packageimage){

            ImageView postimage = (ImageView) mView.findViewById(R.id.package_image);
            Picasso.get().load(packageimage).into(postimage);
        }

        public void setTime(String time){

            TextView posttime=(TextView) mView.findViewById(R.id.package_time);
            posttime.setText(" "+time);
        }

        public void setDate(String date){

            TextView postdate = (TextView) mView.findViewById(R.id.package_date);
            postdate.setText(" "+date);
        }

        public void setPackagename(String packagename){

            TextView postname = (TextView) mView.findViewById(R.id.package_name);
            postname.setText(packagename);
        }

        public void setPrice(String price){

            TextView postprice = (TextView) mView.findViewById(R.id.package_price);
            postprice.setText(price);
        }

        public void setGroupmembers(String groupmembers){

            TextView postgroup = (TextView) mView.findViewById(R.id.package_group_members);
            postgroup.setText(groupmembers);
        }*/
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

        Intent loginIntent= new Intent(getActivity(), MainDashBoard.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        getActivity().finish();
    }
}

