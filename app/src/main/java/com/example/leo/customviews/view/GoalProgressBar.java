package com.example.leo.customviews.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.leo.customviews.R;

 /**
 * Created by LEO on 21/10/2017.
 */

public class GoalProgressBar extends View {


    private int goal;
    private int progress;

    private float goalIndicatorHeight;
    private float goalIndicatorWidth;
    private float barHeight;
    private int goalReachedColor;
    private int goalNotReachedColor;
    private int unfilledSectionColor;

    private ValueAnimator barAnimator;

    private Paint mPaint;

    public GoalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    private void init(AttributeSet attrs) {

        //initialize Paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.GoalProgressBar, 0, 0);

        try {

//            Log.d("PRO_TAG","barheightxml : "+typedArray.getDimension(R.styleable.GoalProgressBar_barHeight,inPx(4)));
//            Log.d("PRO_TAG","barheightxml-px : "+typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_barHeight, (int) inPx(4)));


            setGoalIndicatorHeight(typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_goalIndicatorHeight, 10));
            setGoalIndicatorWidth(typedArray.getDimensionPixelSize(R.styleable.GoalProgressBar_goalIndicatorWidth, 4));
            setBarHeight(typedArray.getDimension(R.styleable.GoalProgressBar_barHeight, 4));

            setGoalReachedColor(typedArray.getColor(R.styleable.GoalProgressBar_goalReachedColor, ContextCompat.getColor(getContext(), R.color.colorPrimary)));
            setGoalNotReachedColor(typedArray.getColor(R.styleable.GoalProgressBar_goalNotReachedColor, Color.BLACK));
            setUnfilledSectionColor(typedArray.getColor(R.styleable.GoalProgressBar_unfilledSectionColor, Color.DKGRAY));


        } finally {
            typedArray.recycle();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpec = MeasureSpec.getSize(heightMeasureSpec);



        Log.d("PRO_TAG","h mode : "+MeasureSpec.getMode(heightMeasureSpec));
        Log.d("PRO_TAG","h size : "+MeasureSpec.getSize(heightMeasureSpec));

        int height = heightSpec;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                height = (int) ((int) Math.max(barHeight, goalIndicatorHeight) + inPx(30));
                break;
            case MeasureSpec.AT_MOST:
                height = (int) Math.min(heightSpec, Math.max(barHeight, goalIndicatorHeight)+inPx(30));
                break;
            case MeasureSpec.EXACTLY:
                height = heightSpec;
                break;

        }

        Log.d("PRO_TAG","real height : "+height);
        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {


        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * (progress / 100f));

//        Log.d("PRO_TAG", "proend : " + progressEndX);
//        Log.d("PRO_TAG", "width : " + getWidth());
        //filled portion
        int filledColor = (progress > goal) ? goalReachedColor : goalNotReachedColor;
        mPaint.setColor(filledColor);
        mPaint.setStrokeWidth(barHeight);
        canvas.drawLine(0, halfHeight, progressEndX, halfHeight, mPaint);

        //unfilled portion
        mPaint.setColor(unfilledSectionColor);
        canvas.drawLine(progressEndX, halfHeight, getWidth(), halfHeight, mPaint);

        //goal indication
        float indicatorPosition = getWidth() * goal / 100f;
        mPaint.setColor(goalReachedColor);
        mPaint.setStrokeWidth(goalIndicatorWidth);
        canvas.drawLine(indicatorPosition, halfHeight - goalIndicatorHeight / 2, indicatorPosition, halfHeight + goalIndicatorHeight / 2, mPaint);

        //show progress text
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(0);
        float scaleDensity = getResources().getDisplayMetrics().scaledDensity;
        int textSize = (int) inPx(14);
        mPaint.setTextSize(textSize);
        canvas.drawText("PROGRESS : "+progress+"%",0,getHeight(),mPaint);

    }


    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();

        bundle.putInt("progress", progress);
        bundle.putInt("goal", goal);
        bundle.putParcelable("superState", super.onSaveInstanceState());

        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            setProgress(bundle.getInt("progress"));
            setGoal(bundle.getInt("goal"));

            state = bundle.getParcelable("superState");

        }
        super.onRestoreInstanceState(state);
    }


    private float inPx(float dp){
        return getResources().getDisplayMetrics().scaledDensity * dp;
    }

    //----------------------------- setters and getters ---------------------------------------------
    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
        postInvalidate();
    }

    public int getProgress() {
        return progress;
    }


    public void setProgress(final int progress, final boolean animate) {
        if (animate) {

            setProgress(0,false);

            barAnimator = ValueAnimator.ofFloat(0,1);
            barAnimator.setInterpolator(new DecelerateInterpolator());

            barAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animValue = (float) animation.getAnimatedValue();

                    setProgress((int) (animValue * progress),false);
                }
            });

            if(!barAnimator.isStarted()){
                barAnimator.start();
            }


        } else {
            this.progress = progress;
            postInvalidate();
        }

    }

    public void setProgress(int progress) {
       setProgress(progress,true);
    }

    public void setGoalIndicatorHeight(float goalIndicatorHeight) {
        this.goalIndicatorHeight = goalIndicatorHeight;
        postInvalidate();
    }

    public void setGoalIndicatorWidth(float goalIndicatorWidth) {
        this.goalIndicatorWidth = goalIndicatorWidth;
        postInvalidate();
    }

    public void setBarHeight(float barHeight) {
        this.barHeight = barHeight;
        postInvalidate();
    }

    public void setGoalReachedColor(int goalReachedColor) {
        this.goalReachedColor = goalReachedColor;
        postInvalidate();
    }

    public void setGoalNotReachedColor(int goalNotReachedColor) {
        this.goalNotReachedColor = goalNotReachedColor;
        postInvalidate();
    }

    public void setUnfilledSectionColor(int unfilledSectionColor) {
        this.unfilledSectionColor = unfilledSectionColor;
        postInvalidate();
    }
}
