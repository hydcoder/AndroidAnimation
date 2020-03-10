package com.hyd.animationart.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/1/18.
 * 以梦为马，明日天涯。
 */
public class Demo4View extends View {

    private ValueAnimator mAnimator;

    private int mCenterY;

    private long mDuration = 700L;

    private float mFraction = 0.0f;

    private int mHeight;

    private Paint mPaint;

    private Path mPath;

    private float mPathLength;

    private PathMeasure mPathMeasure;

    private int mPointColor = Color.BLACK;

    private int mPointRadius = 25;

    private int mPointCount = 6;

    private float[] mPos = new float[2];

    private boolean mThroughAbove = true;

    private int mTranslateX;
    private int mTranslateY;

    private int mWidth;

    public Demo4View(Context context) {
        super(context);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPointColor);

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        mAnimator = ValueAnimator.ofFloat(0f, 1f);
        mAnimator.setDuration(mDuration);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                mThroughAbove = !mThroughAbove;
            }
        });
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoading();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int j = View.MeasureSpec.getSize(widthMeasureSpec);
        int k = View.MeasureSpec.getSize(heightMeasureSpec);
        int i = View.MeasureSpec.getMode(widthMeasureSpec);
        int m = View.MeasureSpec.getMode(heightMeasureSpec);
        mWidth = (mPointCount * 3 + 1) * mPointRadius;
        mHeight = mPointRadius * 2 * 10;
        if (i != Integer.MIN_VALUE && m != Integer.MIN_VALUE) {
            heightMeasureSpec = j;
            if (j < mWidth) heightMeasureSpec = mWidth;
            widthMeasureSpec = k;
            i = heightMeasureSpec;
            if (k < mHeight) {
                widthMeasureSpec = mHeight;
                i = heightMeasureSpec;
            }
        } else if (widthMeasureSpec != Integer.MIN_VALUE) {
            widthMeasureSpec = k;
            i = j;
            if (j < mWidth) {
                i = mWidth;
                widthMeasureSpec = k;
            }
        } else {
            widthMeasureSpec = k;
            i = j;
            if (heightMeasureSpec != Integer.MIN_VALUE) {
                widthMeasureSpec = k;
                i = j;
                if (k < mHeight) {
                    widthMeasureSpec = mHeight;
                    i = j;
                }
            }
        }
        mTranslateX = i / 2 - mWidth / 2;
        mTranslateY = widthMeasureSpec / 2 - mHeight / 2;
        mCenterY = mHeight / 2;
        mPath.moveTo((mPointRadius * 2), mCenterY);
        mPath.cubicTo((mPointRadius * 2), mCenterY, (mWidth / 2), 0.0F,
                (mWidth - mPointRadius * 2), mCenterY);
        mPathMeasure.setPath(mPath, false);
        mPathLength = mPathMeasure.getLength();
        setMeasuredDimension(i, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mTranslateX, mTranslateY);
        for (int i = 0; i < mPointCount; i++) {
            if (i == 0) {
                float f1;
                mPathMeasure.getPosTan(mPathLength * mFraction, mPos, null);
                float f2 = mPos[0];
                if (mThroughAbove) {
                    f1 = mPos[1];
                } else {
                    f1 = mHeight - mPos[1];
                }
                canvas.drawCircle(f2, f1, mPointRadius, mPaint);
            } else {
                canvas.drawCircle(((i * 3 + 2) * mPointRadius) - mFraction * mPointRadius * 3.0f,
                        mCenterY, mPointRadius, mPaint);
            }
        }
    }

    private void stopLoading() {
        if (mAnimator.isRunning()) mAnimator.cancel();
    }

    private void startLoading() {
        if (!mAnimator.isRunning()) mAnimator.start();
    }
}
