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
     ImageView signout,editimage;
     CircleImageView propic;
     View v;
     FirebaseAuth firebaseAuth;
     FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    TextView tv_name, tv_address1, tv_email, tv_phone, nid;


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
         tv_name=(TextView) v.findViewById(R.id.tv_name);
         tv_address1=(TextView) v.findViewById(R.id.tv_address1);
         tv_email=(TextView) v.findViewById(R.id.tv_email);
         tv_phone=(TextView) v.findViewById(R.id.tv_phone);
         nid=(TextView) v.findViewById(R.id.nid);
         propic=(CircleImageView) v.findViewById(R.id.propic);

         final DatabaseReference databaseReference=firebaseDatabase.getReference("Guides").child(firebaseAuth.getUid());

         StorageReference storageReference=firebaseStorage.getReference();
         storageReference.child("Uploaded Profile Pictures For Guides").child(firebaseAuth.getUid()).child("Images for guide/Profile pic for guide").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                 tv_name.setText("" + guideProfile.getName());
                 tv_address1.setText("" + guideProfile.getAddress());
                 tv_email.setText("" + guideProfile.getEmail());
                 tv_phone.setText("" + guideProfile.getPhone());
                 nid.setText("" + guideProfile.getNid());

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
                 //finish();
                 Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(getActivity(), LoginForGuide.class));
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

