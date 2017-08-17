package com.example.leo.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LEO on 17/08/2017.
 */

public class LovelyView extends View {

    private int circleColor, labelColor;
    private String labelText;

    private int defaultColor = Color.BLUE;
    private boolean toggle = true;

    private Paint mPaint;

    public LovelyView(Context context) {
        super(context);

        setUpPaint();
    }

    public LovelyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaint();
        setAttribute(attrs);
    }


    private void setUpPaint() {
        mPaint = new Paint();
    }


    private void setAttribute(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.LovelyView, 0, 0);

        try {
            circleColor = typedArray.getColor(R.styleable.LovelyView_circle_color, Color.BLACK);
            labelColor = typedArray.getColor(R.styleable.LovelyView_label_color, Color.WHITE);
            labelText = typedArray.getString(R.styleable.LovelyView_circle_label);
        } finally {
            typedArray.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidthHalf = this.getMeasuredWidth() / 2;
        int viewHeightHalf = this.getMeasuredHeight() / 2;


        int radius = 0;

        if (viewWidthHalf < viewHeightHalf) {
            radius = viewWidthHalf - 10;
        } else {
            radius = viewHeightHalf - 10;
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);

        mPaint.setColor(circleColor);

        //draw circle
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, mPaint);

        mPaint.setColor(labelColor);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(50);

        canvas.drawText(labelText, viewWidthHalf, viewHeightHalf, mPaint);

    }

    public int getCircleColor() {
        return circleColor;
    }

    public int getLabelColor() {
        return labelColor;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
        requestLayout();
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        invalidate();
        requestLayout();
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
        invalidate();
        requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            setCircleColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));

            return true;
        }

        return false;
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putInt("circleColor", circleColor);

        return bundle;

    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = null;

        if (state instanceof Bundle) {
            bundle = (Bundle) state;
        }

        circleColor = bundle.getInt("circleColor");

        super.onRestoreInstanceState(bundle.getParcelable("superState"));

    }
}
