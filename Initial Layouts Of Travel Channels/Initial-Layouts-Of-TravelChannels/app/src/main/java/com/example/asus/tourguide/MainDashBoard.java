package com.example.asus.tourguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainDashBoard extends AppCompatActivity {

    private Button Open_A_Travel_Agency_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        Open_A_Travel_Agency_Button = (Button) findViewById(R.id.Open_A_Travel_Agency_Button);

        Open_A_Travel_Agency_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent channelIntent = new Intent(MainDashBoard.this,Channel_Register_Activity.class);
                startActivity(channelIntent);
            }
        });

    }
}
