package com.example.leo.customviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leo.customviews.view.GoalProgressBar;

import java.util.Random;

public class ProgressActivity extends AppCompatActivity {


    Button btnProgess;
    GoalProgressBar progressBar;
    GoalProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        btnProgess = (Button) findViewById(R.id.btn_set_progress);
        progressBar = (GoalProgressBar) findViewById(R.id.goal_progress);
//        progressBar2 = (GoalProgressBar) findViewById(R.id.goal_progress1);


        progressBar.setGoal(80);
//        progressBar2.setGoal(40);

        btnProgess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int progess = new Random().nextInt(100);
                int progress2 = new Random().nextInt(100);
                progressBar.setProgress(progess);
//                progressBar2.setProgress(progress2);

            }
        });



    }
}
