package com.example.asus.tripper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.WindowManager;

public class MainDashBoard extends AppCompatActivity {

    private CardView rootCardView1,rootCardView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dash_board);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        rootCardView1 = (CardView) findViewById(R.id.rootCardView1);

        rootCardView3 = (CardView) findViewById(R.id.rootCardView3);

        rootCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashBoard.this,UserDashBoard.class);
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
