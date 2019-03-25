package com.example.asus.tourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Channel_Register_Activity extends AppCompatActivity {

    private Button createChannelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel__register_);

        createChannelButton = (Button) findViewById(R.id.Create_Channel_Button);

        createChannelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cd = new Intent(Channel_Register_Activity.this,Channel_DashBoard.class);
                startActivity(cd);
            }
        });


    }
}
