package com.hyd.animationart.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/1/14.
 * 以梦为马，明日天涯。
 */
public class Demo2View extends View {

    private final String TAG = "QQBrowserLoading";

    private int color = Color.parseColor("#0000FF");

    private int currentY;

    private float density = getResources().getDisplayMetrics().density;

    private int endY;

    private Paint mPaint;

    private int radius = 10;

    private RectF rectF;

    private int startX;

    private int startY;

    public Demo2View(Context context) {
        super(context);
        init();
    }

    public Demo2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = getWidth() / 2;
        endY = getHeight() / 2;
        startY = endY * 5 / 6;
        mPaint.setColor(color);
        if (currentY == 0) {
            playAnimator();
            return;
        }
        drawCircle(canvas);
        drawShader(canvas);
    }

    private void drawShader(Canvas canvas) {
        int i = endY;
        int j = startY;
        float f = (currentY - startY) * 1.0f / (i - j);
        if (f <= 0.3f) {
            return;
        }
        i = (int) (radius * f * density);
        mPaint.setColor(Color.parseColor("#3F3B2D"));
        rectF = new RectF(startX - i, endY + 10, startX + i, endY + 15);
        canvas.drawOval(rectF, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        if (endY - currentY > 10) {
            canvas.drawCircle(startX, currentY, radius * density, mPaint);
            Log.i(TAG, "circle");
            return;
        }
        rectF = new RectF(startX - radius * density - 2.0f, currentY - radius * density + 5.0f,
                startX + radius * density + 2.0f, currentY + radius * density);
        canvas.drawOval(rectF, mPaint);
        Log.i(TAG, "oval");
    }

    private void playAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startY, endY);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentY = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator(1.2F));
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public void setColor(int color) {
        this.color = color;
    }
}
