package com.example.asus.tripper;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener{
     private ImageView signout,editimage,propic;
     private Button button_mytrips,button_acceptedtrips, button_settings;
     //private CircleImageView propic;
     private View v;
     private FirebaseAuth firebaseAuth;
     private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView tv_username, tv_fullname, tv_address1, tv_country, tv_phone, numberoftripstext, numberofacceptedtripstext;
    private DatabaseReference mytripsRef, confirmRef, usersRef;
    private String currentUserId, packagekey;
    private int countMyTrips = 0, getCountMyAcceptedTrips = 0;



     public ProfileFragment(){

     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

         firebaseAuth=FirebaseAuth.getInstance();
         currentUserId = firebaseAuth.getCurrentUser().getUid();
         firebaseDatabase=FirebaseDatabase.getInstance();
         firebaseStorage=FirebaseStorage.getInstance();

         usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);

         //packagekey= getIntent().getExtras().get("packagekey").toString();

         mytripsRef = FirebaseDatabase.getInstance().getReference().child("Packages");
         confirmRef = FirebaseDatabase.getInstance().getReference().child("ConfirmedPackages");

         v=inflater.inflate(R.layout.fragment_profile,container,false);
         signout= (ImageView) v.findViewById(R.id.signout);
         signout.setOnClickListener(this);
         editimage= (ImageView) v.findViewById(R.id.editimage);
         editimage.setOnClickListener(this);
         tv_username=(TextView) v.findViewById(R.id.tv_username);
         tv_fullname=(TextView) v.findViewById(R.id.tv_fullname);
         tv_address1=(TextView) v.findViewById(R.id.tv_address1);
         tv_country=(TextView) v.findViewById(R.id.tv_country);
         tv_phone=(TextView) v.findViewById(R.id.tv_phone);
         propic=(ImageView) v.findViewById(R.id.propic);
         numberoftripstext = v.findViewById(R.id.numberoftripstext);
         //numberofacceptedtripstext=v.findViewById(R.id.numberofacceptedtripstext);


         //numberoftripstext.setVisibility(View.INVISIBLE);     //this could be removed later for counting total trips


         button_mytrips=v.findViewById(R.id.button_mytrips);
         //button_acceptedtrips=v.findViewById(R.id.button_acceptedtrips);
         button_settings = v.findViewById(R.id.button_settings);

        /* button_mytrips.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i=new Intent(getActivity(), MyTrips.class);
                 startActivity(i);
             }
         });*/

        //final String packagekey = mytripsRef.getKey();

        /* button_acceptedtrips.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i=new Intent(getActivity(), AcceptedTrips.class);
                 i.putExtra("packagekey", packagekey);
                 startActivity(i);
             }
         });*/

         button_settings.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent i=new Intent(getActivity(), Settings.class);
                 startActivity(i);
             }
         });


         mytripsRef.orderByChild("gid")     //code for counting total trips
                 .startAt(currentUserId).endAt(currentUserId + "\uf8ff")
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         if (dataSnapshot.exists()){

                             countMyTrips = (int) dataSnapshot.getChildrenCount();
                             numberoftripstext.setText(Integer.toString(countMyTrips) + " Trips");

                             button_mytrips.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     Intent i=new Intent(getActivity(), MyTrips.class);
                                     //i.putExtra("packagekey", packagekey);
                                     startActivity(i);
                                 }
                             });
                         }
                         else {

                             numberoftripstext.setText("0 Trips");
                             button_mytrips.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     Toast.makeText(getActivity(), "You have no trips to show", Toast.LENGTH_SHORT).show();
                                 }
                             });



                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });             //up to this(counting total trips)



        /* confirmRef.orderByChild("confirm_type_guide")     //code for counting total trips
                 .startAt(currentUserId).endAt(currentUserId + "\uf8ff")
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                         if (dataSnapshot.exists()){

                             getCountMyAcceptedTrips = (int) dataSnapshot.getChildrenCount();
                             numberofacceptedtripstext.setText(Integer.toString(getCountMyAcceptedTrips) + " Trips");

                             button_acceptedtrips.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     Intent i=new Intent(getActivity(), AcceptedTrips.class);
                                     i.putExtra("packagekey", packagekey);
                                     startActivity(i);
                                 }
                             });
                         }
                         else {

                             numberoftripstext.setText("0 Trips");
                             button_mytrips.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     Toast.makeText(getActivity(), "You have no trips to show", Toast.LENGTH_SHORT).show();
                                 }
                             });



                         }
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });             //up to this(counting total trips)*/

         final DatabaseReference databaseReference=firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

         StorageReference storageReference=firebaseStorage.getReference();
         storageReference.child("User Profile Images").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() { //before it was this
             @Override
             public void onSuccess(Uri uri) {
                 //propic.setImageURI(uri);
                 //Picasso.get().load(uri).fit().centerCrop().into(propic);
                 //Picasso.with(getActivity()).load(uri).fit().centerCrop().into(propic);
                 //Glide.with(getActivity()).load(uri).into(propic);
                 Glide.with(getActivity()).load(uri).placeholder(R.drawable.userpicture).into(propic);
             }
         });



         databaseReference.addValueEventListener(new ValueEventListener() {   //before it was databaseReference
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 GuideProfile guideProfile = dataSnapshot.getValue(GuideProfile.class);
                 tv_username.setText("" + guideProfile.getUsername());
                 tv_fullname.setText("" + guideProfile.getFullname());
                 tv_address1.setText("" + guideProfile.getAddress());
                 tv_country.setText("" + guideProfile.getCountry());
                 tv_phone.setText("" + guideProfile.getPhone());

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(getContext(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

             }
         });




         return v;
     }


     @Override
    public void onClick(View v){

         switch (v.getId()){
             case R.id.signout:

                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                 builder.setTitle("Log Out").setMessage("Do you want to log out?")
                         .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {

                                 firebaseAuth.signOut();
                                 getActivity().finish();
                                 Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                                 Intent i= new Intent(getActivity(), LoginUser.class);
                                 startActivity(i);

                             }


                         }).setNegativeButton("No", null);

                 AlertDialog alert = builder.create();
                 alert.show();
                /* firebaseAuth.signOut();
                 getActivity().finish();
                 Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                 Intent i= new Intent(getActivity(), LoginUser.class);
                 startActivity(i);*/

                 break;
             case R.id.editimage:

                 //Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(getActivity(), UpdateProfileGuide.class));
                 break;
             default:
                 break;
         }

         /*if(v==signout){
             Toast.makeText(getActivity(),"Logged out", Toast.LENGTH_SHORT).show();

         }*/

     }


    /*private void init() {
        signout = (ImageView) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                                           Intent i = new Intent(ProfileFragment.this, LoginForGuide.class);
                                           startActivity(i);
                                       }
                                   }
        );
    }*/





    }

