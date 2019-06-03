package com.example.asus.tripper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends AppCompatActivity {

    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton, SendImagefileButton;
    private EditText userMessageInput;
    private RecyclerView userMessageList;

    private String messageReceiverID, messageReceiverName, username1;

    private TextView receiverName;
    private CircleImageView receiverProfileImage;
    private DatabaseReference RootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        RootRef = FirebaseDatabase.getInstance().getReference();

        //messageReceiverID = getIntent().getExtras().get("visit_user_id").toString();
        messageReceiverName = getIntent().getExtras().get("userkey").toString();

        InitializeFields();

        DisplayReceiverInfo();
    }

    private void DisplayReceiverInfo() {

        receiverName.setText(messageReceiverName);

        RootRef.child("Packages").child(messageReceiverName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    username1 = dataSnapshot.child("fullname").getValue().toString();

                    receiverName.setText(username1);

                    final String profileImage = dataSnapshot.child("profileimage").getValue().toString();
                    //Picasso.with(Message.this).load(profileImage).placeholder(R.drawable.userpic).into(receiverProfileImage);
                    Picasso.get().load(profileImage).resize(5,5).centerCrop().into(receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {

        ChattoolBar = findViewById(R.id.chat_bar_layout);
        setSupportActionBar(ChattoolBar);

        ActionBar actionBar = getSupportActionBar();    //connect chat custom bar to the chat activity
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater layoutInflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.chat_custom_bar, null);
        actionBar.setCustomView(action_bar_view);       //upto this(chat custom bar to the chat activity)

        receiverName = findViewById(R.id.custom_profile_name);
        receiverProfileImage= findViewById(R.id.custom_profile_image);

        SendMessageButton = findViewById(R.id.send_message_button);
        SendImagefileButton = findViewById(R.id.send_image_file_button);
        userMessageInput= findViewById(R.id.input_message);

    }
}
