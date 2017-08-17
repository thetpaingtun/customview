package com.example.leo.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LEO on 17/08/2017.
 */

public class ShapeSelectorView extends View {

    private int shapeColor;
    private boolean displayShapeName;


    private int shapeWidth = 100;
    private int shapeHeight = 100;
    private int textXOffset = 0;
    private int textYOffset = 30;

    private Paint mPaint;

    private String[] shapeNames = {"square", "circle", "triangle"};
    private int currentIndex = 0;

    public ShapeSelectorView(Context context) {
        super(context);
        setupPaint();

    }

    public ShapeSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttribute(attrs);
        setupPaint();
    }

    private void setAttribute(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeSelectorView, 0, 0);

        try {
            shapeColor = typedArray.getColor(R.styleable.ShapeSelectorView_shpaeColor, Color.BLACK);
            displayShapeName = typedArray.getBoolean(R.styleable.ShapeSelectorView_displayShpaeName, true);
        } finally {
            typedArray.recycle();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int textpadding = 10;

        int minW = getPaddingLeft() + shapeWidth + getPaddingRight();

        int minH = getPaddingTop() + shapeHeight + getPaddingBottom();

        if (isDisplayShapeName()) {
            minH += (textYOffset + textpadding);
        }

        int w = resolveSizeAndState(minW, widthMeasureSpec, 0);
        int h = resolveSizeAndState(minH, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String shapeSelected = shapeNames[currentIndex];


        if (shapeSelected.equals("square")) {
            canvas.drawRect(0, 0, shapeWidth, shapeHeight, mPaint);

        } else if (shapeSelected.equals("circle")) {
            canvas.drawCircle(shapeWidth / 2, shapeHeight / 2, shapeWidth / 2, mPaint);
        } else {
            canvas.drawPath(getTrainglePath(), mPaint);
        }

        if (displayShapeName) {
            canvas.drawText(shapeSelected, 0, (shapeHeight + textYOffset), mPaint);
        }

    }

    private void setupPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(25);
        mPaint.setColor(shapeColor);
    }

    public int getShapeColor() {
        return shapeColor;
    }

    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        requestLayout();
        invalidate();

    }

    public boolean isDisplayShapeName() {
        return displayShapeName;
    }

    public void setDisplayShapeName(boolean displayShapeName) {
        this.displayShapeName = displayShapeName;
        requestLayout();
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentIndex += 1;
            currentIndex = currentIndex % shapeNames.length;
            invalidate();
            return true;
        }

        return result;
    }

    private Path getTrainglePath() {
        Path path = new Path();
        path.moveTo(shapeWidth / 2, 0);
        path.lineTo(0, shapeHeight);
        path.lineTo(shapeWidth, shapeHeight);

        return path;
    }


    public String getSelectedShapeName(){
        return shapeNames[currentIndex];
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState",super.onSaveInstanceState());
        bundle.putInt("currentIndex",currentIndex);

        return bundle;
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = null;
        if(state instanceof  Bundle){
            bundle = (Bundle) state;
        }

        this.currentIndex = bundle.getInt("currentIndex");
        super.onRestoreInstanceState(bundle.getParcelable("superState"));

    }
}
