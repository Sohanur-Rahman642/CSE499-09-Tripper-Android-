package com.example.asus.tripper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.tripper.RegisterAndLogin.LoginUser;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.Timer;

public class MainDashBoard extends AppCompatActivity {


    private CardView rootCardView1,rootCardView3, rootCardView2;

    ViewPager viewPager;
    CustomSwipeAdapter adapter;

   /* private Timer timer;
    private int current_position= 0;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        viewPager = findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        rootCardView1 = (CardView) findViewById(R.id.rootCardView1);

        rootCardView2= (CardView) findViewById(R.id.rootCardView2) ;

        rootCardView3 = (CardView) findViewById(R.id.rootCardView3);

        rootCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this, LoginUser.class);
                startActivity(i);
            }
        });

        rootCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this, LoginUser.class);
                startActivity(i);
            }
        });

        rootCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this,Channel_DashBoard.class);
                startActivity(i);
            }
        });
    }



}
