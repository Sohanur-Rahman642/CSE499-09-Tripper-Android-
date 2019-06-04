package com.example.asus.tripper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Message extends AppCompatActivity {

    private Toolbar ChattoolBar;
    private ImageButton SendMessageButton, SendImagefileButton;
    private EditText userMessageInput;
    private RecyclerView userMessageList;
    private final List<MessagesModel> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessagesAdapter messageAdapter;

    private String messageReceiverID, messageReceiverName, username1, messageSenderID, saveCurrentDate, saveCurrentTime;

    private TextView receiverName;
    private CircleImageView receiverProfileImage;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mAuth = FirebaseAuth.getInstance();
        messageSenderID = mAuth.getCurrentUser().getUid();

        RootRef = FirebaseDatabase.getInstance().getReference();

        //messageReceiverID = getIntent().getExtras().get("visit_user_id").toString();
        messageReceiverName = getIntent().getExtras().get("userkey").toString();

        InitializeFields();

        DisplayReceiverInfo();

        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SendMessage();
            }
        });

        FetchMessages();
    }

    private void FetchMessages() {

        RootRef.child("Messages").child(messageSenderID).child(messageReceiverName)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()){

                            MessagesModel messages = dataSnapshot.getValue(MessagesModel.class);
                            messagesList.add(messages);
                            messageAdapter.notifyDataSetChanged();    //whenever a new msg is added it will be displayed
                            userMessageList.smoothScrollToPosition(userMessageList.getAdapter().getItemCount()-1);    //newly added
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void SendMessage() {

        String messageText = userMessageInput.getText().toString();

        if (TextUtils.isEmpty(messageText)){

            Toast.makeText(this, "Please type a message", Toast.LENGTH_SHORT).show();
        }
        else {

            String message_sender_ref = "Messages/"+messageSenderID+ "/" +messageReceiverName;
            String message_receiver_ref = "Messages/"+messageReceiverName+ "/" +messageSenderID;

            DatabaseReference user_message_key = RootRef.child("Messages").child(messageSenderID)
                    .child(messageReceiverName).push();   //.push will create unique random key

            String message_push_id = user_message_key.getKey();

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm aa");
            saveCurrentTime = currentTime.format(calForTime.getTime());

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);


            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(message_sender_ref + "/" + message_push_id, messageTextBody);
            messageBodyDetails.put(message_receiver_ref + "/" + message_push_id, messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if (task.isSuccessful()){

                        Toast.makeText(Message.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");
                    }
                    else {

                        String message = task.getException().getMessage();
                        Toast.makeText(Message.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                        userMessageInput.setText("");
                    }

                    //userMessageInput.setText("");
                }
            });
        }
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
                    Picasso.with(Message.this).load(profileImage).placeholder(R.drawable.userpic).into(receiverProfileImage);
                    //Picasso.get().load(profileImage).resize(5,5).centerCrop().into(receiverProfileImage);
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

        messageAdapter = new MessagesAdapter(messagesList);
        userMessageList = findViewById(R.id.chat_recycle);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessageList.setHasFixedSize(true);
        userMessageList.setLayoutManager(linearLayoutManager);
        userMessageList.setAdapter(messageAdapter);

    }
}
