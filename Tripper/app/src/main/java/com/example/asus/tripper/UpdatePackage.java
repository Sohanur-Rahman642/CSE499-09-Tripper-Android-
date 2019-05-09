package com.example.asus.tripper;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePackage extends AppCompatActivity {

    private EditText update_packagename,update_details, update_startdate, update_enddate, update_starttime, update_endtime, update_location, update_meetpoint, update_groupmembers, update_price;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private Button update_button_for_update_package, back_button_for_update_package;

    private DatabaseReference databaseReference;

    private ProgressDialog loadingBar;

    private String packagekey , currentUserId, databaseUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_package);

        update_packagename=findViewById(R.id.update_packagename);
        update_details=findViewById(R.id.update_details);
        update_startdate=findViewById(R.id.update_startdate);
        update_enddate=findViewById(R.id.update_enddate);
        update_starttime=findViewById(R.id.update_starttime);
        update_endtime=findViewById(R.id.update_endtime);
        update_location=findViewById(R.id.update_location);
        update_meetpoint=findViewById(R.id.update_meetpoint);
        update_groupmembers=findViewById(R.id.update_groupmembers);
        update_price=findViewById(R.id.update_price);
        update_button_for_update_package=findViewById(R.id.update_button_for_update_package);

        loadingBar= new ProgressDialog(this);

        currentUserId = firebaseAuth.getCurrentUser().getUid();

        packagekey= getIntent().getExtras().get("packagekey").toString();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        //final DatabaseReference databaseReference=firebaseDatabase.getReference("Packages").child(firebaseAuth.getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Packages").child(packagekey);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    PackagesModel1 packagesModel = dataSnapshot.getValue(PackagesModel1.class);
                    update_packagename.setText(packagesModel.getPackagename());
                    update_details.setText(packagesModel.getDetails());
                    update_startdate.setText(packagesModel.getStartdate());
                    update_enddate.setText(packagesModel.getEnddate());
                    update_starttime.setText(packagesModel.getStarttime());
                    update_endtime.setText(packagesModel.getEndtime());
                    update_location.setText(packagesModel.getLocation());
                    update_meetpoint.setText(packagesModel.getMeetpoint());
                    update_groupmembers.setText(packagesModel.getGroupmembers());
                    update_price.setText(packagesModel.getPrice());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdatePackage.this,databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });
        update_button_for_update_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packagename=update_packagename.getText().toString();
                String details=update_details.getText().toString();
                String startdate=update_startdate.getText().toString();
                String enddate=update_enddate.getText().toString();
                String starttime=update_starttime.getText().toString();
                String endtime= update_endtime.getText().toString();
                String location=update_location.getText().toString();
                String meetpoint= update_meetpoint.getText().toString();
                String groupmembers=update_groupmembers.getText().toString();
                String price= update_price.getText().toString();

                PackagesModel1 packagesModel=new PackagesModel1(packagename, details, startdate, enddate, starttime, endtime, location, meetpoint, groupmembers, price);

                databaseReference.setValue(packagesModel);

                finish();

                loadingBar.setTitle("Updating Package");
                loadingBar.setMessage("Please wait until we are updating package..");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);

            }
        });
    }

}
