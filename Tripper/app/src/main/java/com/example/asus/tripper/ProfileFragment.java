package com.example.asus.tripper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
     private ImageView signout,editimage;
     private CircleImageView propic;
     private View v;
     private FirebaseAuth firebaseAuth;
     private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private TextView tv_username, tv_fullname, tv_address1, tv_country, tv_phone;


     public ProfileFragment(){

     }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

         firebaseAuth=FirebaseAuth.getInstance();
         firebaseDatabase=FirebaseDatabase.getInstance();
         firebaseStorage=FirebaseStorage.getInstance();

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
         propic=(CircleImageView) v.findViewById(R.id.propic);

         final DatabaseReference databaseReference=firebaseDatabase.getReference("Users").child(firebaseAuth.getUid());

         StorageReference storageReference=firebaseStorage.getReference();
         storageReference.child("User Profile Images").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
             @Override
             public void onSuccess(Uri uri) {
                 //propic.setImageURI(uri);
                 Picasso.get().load(uri).fit().centerCrop().into(propic);
             }
         });



         databaseReference.addValueEventListener(new ValueEventListener() {
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
                 firebaseAuth.signOut();
                 getActivity().finish();
                 Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                 Intent i= new Intent(getActivity(), LoginForGuide.class);
                 startActivity(i);

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

