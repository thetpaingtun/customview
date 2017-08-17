package com.example.leo.customviews;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btnPress;
    ShapeSelectorView shapeSelectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPress = (Button) findViewById(R.id.btn_press);
        shapeSelectorView = (ShapeSelectorView) findViewById(R.id.shape_view);

        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,shapeSelectorView.getSelectedShapeName(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
