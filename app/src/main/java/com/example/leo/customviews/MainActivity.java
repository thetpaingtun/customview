package com.example.leo.customviews;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnPress;
    LovelyView lovelyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPress = (Button) findViewById(R.id.btn_press);
        lovelyView = (LovelyView) findViewById(R.id.lovely_view);



        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lovelyView.setCircleColor(Color.BLUE);
                lovelyView.setLabelText("PRESSED");
            }
        });
    }
}
