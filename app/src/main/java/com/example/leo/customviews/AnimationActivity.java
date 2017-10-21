package com.example.leo.customviews;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.ImageView;

public class AnimationActivity extends AppCompatActivity {

    ImageView imgBell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);


        imgBell = (ImageView) findViewById(R.id.ic_bell);
    }

    public void animate(final View view) {


        ValueAnimator animator = ValueAnimator.ofFloat(1,0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                imgBell.setAlpha((float)animation.getAnimatedValue());


            }
        });



        animator.start();
    }
}