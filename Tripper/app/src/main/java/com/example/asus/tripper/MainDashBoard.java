package com.example.asus.tripper;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.asus.tripper.RegisterAndLogin.LoginUser;

public class MainDashBoard extends AppCompatActivity {

    private ConstraintLayout layoutExplore,layoutGuide, layoutChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        layoutExplore = (ConstraintLayout) findViewById(R.id.layout_explore);
        layoutGuide = (ConstraintLayout) findViewById(R.id.layout_guide);
        layoutChannel = (ConstraintLayout) findViewById(R.id.layout_channel);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



        layoutExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this, LoginUser.class);
                startActivity(i);
                finish();
            }
        });

        layoutGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this, MyToursForGuide.class);
                startActivity(i);
                finish();
            }
        });

        layoutChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this,Channel_DashBoard.class);
                startActivity(i);
                finish();
            }
        });
    }
}
