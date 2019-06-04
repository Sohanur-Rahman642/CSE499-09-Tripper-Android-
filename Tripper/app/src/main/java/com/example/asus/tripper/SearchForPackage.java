package com.example.asus.tripper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchForPackage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private ImageView SearchButton, backbutton;
    private EditText SearchInputText;

    private RecyclerView packages_recycle2;

    private DatabaseReference packagesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_package);

        mAuth=FirebaseAuth.getInstance();
        packagesRef = FirebaseDatabase.getInstance().getReference().child("Packages");
        //query=FirebaseDatabase.getInstance().getReference().child("Packages");

        packages_recycle2 = (RecyclerView) findViewById(R.id.packages_recycle2);
        packages_recycle2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        linearLayoutManager .setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        packages_recycle2.setLayoutManager(linearLayoutManager);

        backbutton = findViewById(R.id.back_button_for_search_packages);

        SearchButton = (ImageView) findViewById(R.id.search_btn2);
        SearchInputText = (EditText) findViewById(R.id.search_box2);


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchBoxInput = SearchInputText.getText().toString();
                DisplayAllPackages(searchBoxInput);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i= new Intent(SearchForPackage.this, MyToursForGuide.class);
                startActivity(i);
            }
        });



        //DisplayAllPackages();




    }

    private void DisplayAllPackages(String searchBoxInput) {

        Toast.makeText(this, "Searching...", Toast.LENGTH_LONG).show();


        Query searchPackageQuery = packagesRef.orderByChild("packagename")
                .startAt(searchBoxInput).endAt(searchBoxInput + "\uf8ff");

        FirebaseRecyclerOptions<PackagesModel> options =
                new FirebaseRecyclerOptions.Builder<PackagesModel>()
                        .setQuery(searchPackageQuery, PackagesModel.class)
                        .build();

        FirebaseRecyclerAdapter<PackagesModel, SearchForPackage.PackageViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<PackagesModel, SearchForPackage.PackageViewHolder>(options)

                {
                    @Override
                    protected void onBindViewHolder(@NonNull SearchForPackage.PackageViewHolder holder, int position, @NonNull PackagesModel model) {

                        /*holder.setFullname(model.getFullname());
                        holder.setProfileimage(getActivity().getApplicationContext(),model.getProfileimage());
                        holder.setPackageimage(getActivity().getApplicationContext(),model.getPackageimage());
                        holder.setDate(model.getDate());
                        holder.setTime(model.getTime());
                        holder.setPackagename(model.getPackagename());
                        holder.setPrice(model.getPrice());
                        holder.setGroupmembers(model.getGroupmembers());*/

                        final String packagekey = getRef(position).getKey();




                        holder.package_user_name.setText(model.getFullname());
                        holder.package_name.setText(model.getPackagename());
                        holder.package_date.setText(model.getDate());
                        holder.package_time.setText(model.getTime());
                        holder.package_price.setText(model.getPrice());
                        holder.package_group_members.setText(model.getGroupmembers());
                        //Picasso.get().load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                        //Picasso.get().load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                        Picasso.with(SearchForPackage.this).load(model.getPackageimage()).placeholder(R.drawable.hill).fit().centerCrop().into(holder.package_image);
                        Picasso.with(SearchForPackage.this).load(model.getProfileimage()).placeholder(R.drawable.userpic).fit().centerCrop().into(holder.package_profile_image);

                        holder.package_image.setOnClickListener(new View.OnClickListener() {  //it can be holder.mView
                            @Override
                            public void onClick(View v) {

                                Intent clickpackageIntent = new Intent(SearchForPackage.this, ClickPackage.class);
                                clickpackageIntent.putExtra("packagekey", packagekey);
                                startActivity(clickpackageIntent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SearchForPackage.PackageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_packages_layout,viewGroup,false);

                        SearchForPackage.PackageViewHolder viewHolder = new SearchForPackage.PackageViewHolder(view);
                        return viewHolder;
                    }
                };


        firebaseRecyclerAdapter.startListening();
        packages_recycle2.setAdapter(firebaseRecyclerAdapter);





    }


    public static class PackageViewHolder extends RecyclerView.ViewHolder{

        View mView;

        TextView package_user_name, package_date, package_time, package_name, package_price, package_group_members;
        CircleImageView package_profile_image;
        ImageView package_image;


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
        }
    }
}
