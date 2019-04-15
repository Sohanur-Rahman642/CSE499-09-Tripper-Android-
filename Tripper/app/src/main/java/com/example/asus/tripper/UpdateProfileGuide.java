package com.example.asus.tripper;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileGuide extends AppCompatActivity {

    private EditText upname,upaddress1,upemail,upphone;
    private TextView upnid;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button updatebutton;
    private Button changepassbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_guide);

        upname=findViewById(R.id.tv_name1);
        upaddress1=findViewById(R.id.tv_address2);
        upemail=findViewById(R.id.tv_email1);
        upphone=findViewById(R.id.tv_phone1);
        upnid=findViewById(R.id.tv_nid1);
        updatebutton=findViewById(R.id.updatebtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        final DatabaseReference databaseReference=firebaseDatabase.getReference("Guides").child(firebaseAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GuideProfile guideProfile=dataSnapshot.getValue(GuideProfile.class);
                upname.setText(guideProfile.getName());
                upaddress1.setText(guideProfile.getAddress());
                upemail.setText(guideProfile.getEmail());
                upphone.setText(guideProfile.getPhone());
                upnid.setText(guideProfile.getNid());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfileGuide.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=upname.getText().toString();
                String address=upaddress1.getText().toString();
                String email=upemail.getText().toString();
                String phone=upphone.getText().toString();
                String nid= upnid.getText().toString();

                GuideProfile guideProfile=new GuideProfile(name,address,email,phone,nid);

                databaseReference.setValue(guideProfile);

                finish();

            }
        });
    }
}
