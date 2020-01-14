package com.hyd.animationart.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/1/14.
 * 以梦为马，明日天涯。
 */
public class Demo3View extends View {
    private static final int DEFAULT_ANIMATOR_DURATION = 1000;
    private static final int DEFAULT_DISTANCE = 100;
    private static final int DEFAULT_MAX_RADIUS = 75;
    private static final int DEFAULT_MIN_RADIUS = 25;
    private static final int DEFAULT_ONE_BALL_COLOR = Color.parseColor("#40df73");
    private static final int DEFAULT_TWO_BALL_COLOR = Color.parseColor("#ffdf3e");

    private AnimatorSet animatorSet;

    private int distance = DEFAULT_DISTANCE;

    private long duration = DEFAULT_ANIMATOR_DURATION;

    private float mCenterX;
    private float mCenterY;

    private Ball mOneBall;
    private Ball mTwoBall;

    private Paint mPaint;

    private float maxRadius = DEFAULT_MAX_RADIUS;
    private float minRadius = DEFAULT_MIN_RADIUS;

    public Demo3View(Context context) {
        super(context);
        init();
    }

    public Demo3View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mOneBall = new Ball();
        mTwoBall = new Ball();
        mOneBall.setColor(DEFAULT_ONE_BALL_COLOR);
        mTwoBall.setColor(DEFAULT_TWO_BALL_COLOR);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        configAnimator();
    }

    private void configAnimator() {
        float f = (maxRadius + minRadius) * 0.5f;
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mOneBall, "radius",
                f, maxRadius, f, minRadius, f);
        objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(-1.0f, 0.0f, 1.0f, 0.0f, -1.0f);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f1 = (float) animation.getAnimatedValue();
                float f2 = mCenterX;
                float f3 = distance;
                mOneBall.setCenterX(f2 + f3 * f1);
                invalidate();
            }
        });
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mTwoBall, "radius",
                f, minRadius, f, maxRadius, f);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1.0f, 0.0f, -1.0f, 0.0f, 1.0f);
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f1 = (float) animation.getAnimatedValue();
                float f2 = mCenterX;
                float f3 = distance;
                mTwoBall.setCenterX(f2 + f3 * f1);
            }
        });
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator1, valueAnimator1, objectAnimator2, valueAnimator2);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOneBall.getRadius() > mTwoBall.getRadius()) {
            mPaint.setColor(mTwoBall.getColor());
            canvas.drawCircle(mTwoBall.getCenterX(), mCenterY, mTwoBall.getRadius(), mPaint);
            mPaint.setColor(mOneBall.getColor());
            canvas.drawCircle(mOneBall.getCenterX(), mCenterY, mOneBall.getRadius(), mPaint);
        } else {
            mPaint.setColor(mOneBall.getColor());
            canvas.drawCircle(mOneBall.getCenterX(), mCenterY, mOneBall.getRadius(), mPaint);
            mPaint.setColor(mTwoBall.getColor());
            canvas.drawCircle(mTwoBall.getCenterX(), mCenterY, mTwoBall.getRadius(), mPaint);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimator();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            stopAnimator();
        } else {
            startAnimator();
        }
    }

    private void stopAnimator() {
        if (animatorSet != null) {
            animatorSet.end();
        }
    }

    private void startAnimator() {
        if (getVisibility() == View.VISIBLE && !animatorSet.isRunning() && animatorSet != null) {
            animatorSet.start();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setMaxRadius(float maxRadius) {
        this.maxRadius = maxRadius;
    }

    public void setMinRadius(float minRadius) {
        this.minRadius = minRadius;
    }

    public void setOneBallColor(@ColorInt int color) {
        mOneBall.setColor(color);
    }

    public void setTwoBallColor(@ColorInt int color) {
        mTwoBall.setColor(color);
    }

    public class Ball {
        private float centerX;

        private int color;

        private float radius;

        public float getCenterX() { return this.centerX; }

        public int getColor() { return this.color; }

        public float getRadius() { return this.radius; }

        public void setCenterX(float param1Float) { this.centerX = param1Float; }

        public void setColor(int param1Int) { this.color = param1Int; }

        public void setRadius(float param1Float) { this.radius = param1Float; }
    }
}
