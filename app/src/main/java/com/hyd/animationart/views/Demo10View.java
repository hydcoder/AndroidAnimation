package com.hyd.animationart.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/3/10.
 * 以梦为马，明日天涯。
 */
public class Demo10View extends View {

    private static final int DEFAULT_DOT_SIZE = 3;
    private static final int DEFAULT_DURATION = 3000;
    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#ff000000");
    private static final int DEFAULT_LINE_WIDTH = 9;
    private static final int STATE_SEARCHING = 0;
    private static final int STATE_WAIT = 1;

    private boolean isDotShowing = true;

    private Paint mArcPaint;

    private Path mArcPath;

    private float mCenterX;

    private float mCenterY;

    private float mCircleRadius;

    private float[] mCurrentPos;

    private int mCurrentState;

    private float[] mCurrentTan;

    private int mDotSize;

    private int mDuration;

    private float mFraction;

    private Paint mPaint;

    private Path mPath;

    private float mPathLength;

    private PathMeasure mPathMeasure;

    private RectF mRectF;

    public Demo10View(Context context) {
        super(context);
        init();
    }

    public Demo10View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo10View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(DEFAULT_LINE_WIDTH);
        mPaint.setColor(DEFAULT_LINE_COLOR);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setColor(Color.parseColor("#ffffffff"));
        mDotSize = DEFAULT_DOT_SIZE;
        mDuration = DEFAULT_DURATION;
        mCurrentState = STATE_WAIT;
        mCurrentPos = new float[2];
        mCurrentTan = new float[2];
        mPath = new Path();
        mArcPath = new Path();
        mPathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFraction <= 0.2D) {
            canvas.drawCircle(mCenterX, mCenterY, mCircleRadius - mCircleRadius * mFraction,
                    mPaint);
            float f12 = mCenterX;
            float f13 = mCircleRadius / (float) Math.sqrt(2.0D);
            float f14 = 1.2F * mCircleRadius / (float) Math.sqrt(2.0D) / 0.2F;
            float f15 = mFraction;
            float f16 = mCenterY;
            float f17 = mCircleRadius / (float) Math.sqrt(2.0D);
            float f18 = 1.2F * mCircleRadius / (float) Math.sqrt(2.0D) / 0.2F;
            float f19 = mFraction;
            float f20 = mCenterX;
            float f21 = 2.2F * mCircleRadius / (float) Math.sqrt(2.0D);
            float f22 = mCenterY;
            canvas.drawLine(f14 * f15 + f12 + f13, f18 * f19 + f16 + f17, f21 + f20,
                    2.2F * mCircleRadius / (float) Math.sqrt(2.0D) + f22, mPaint);
            return;
        }
        if (mFraction <= 0.8D) {
            mPathMeasure.getPosTan(0.0F + mPathLength / 0.6F * (mFraction - 0.2F), mCurrentPos,
                    mCurrentTan);
            if (mCurrentPos[1] == mCenterY && mCurrentPos[0] <= mCenterX + mCircleRadius / 3.0F && mCurrentPos[0] >= mCenterX - mCircleRadius / 3.0F) {
                if (isDotShowing) {
                    isDotShowing = false;
                } else {
                    canvas.drawCircle(mCurrentPos[0], mCurrentPos[1], mDotSize, mPaint);
                    isDotShowing = true;
                }
            } else {
                canvas.drawCircle(mCurrentPos[0], mCurrentPos[1], mDotSize, mPaint);
            }
            if (mFraction <= 0.3D) {
                canvas.drawCircle(mCenterX, mCenterY,
                        mCircleRadius * 0.8F + mCircleRadius * 2.0F * (mFraction - 0.2F), mPaint);
            } else {
                canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mPaint);
            }
            if (mFraction <= 0.35D && mFraction > 0.3D) {
                canvas.drawArc(mRectF, 45.0F - 1100.0F * (mFraction - 0.3F),
                        (mFraction - 0.3F) * 2200.0F, false, mArcPaint);
            } else if (mFraction <= 0.4D && mFraction > 0.35D) {
                canvas.drawArc(mRectF, 45.0F - 1100.0F * (0.4F - mFraction),
                        (0.4F - mFraction) * 2200.0F, false, mArcPaint);
            }
            if (mFraction <= 0.75D && mFraction > 0.7D) {
                mArcPath.reset();
                mArcPath.moveTo(mCenterX + mCircleRadius, mCenterY);
                mArcPath.cubicTo(mCenterX + mCircleRadius + (mFraction - 0.7F) * 160.0F,
                        mCenterY + mCircleRadius / 2.0F + (mFraction - 0.7F) * 160.0F,
                        mCenterX + mCircleRadius / 2.0F + (mFraction - 0.7F) * 160.0F,
                        mCenterY + mCircleRadius + (mFraction - 0.7F) * 160.0F, mCenterX,
                        mCenterY + mCircleRadius);
                canvas.drawPath(mArcPath, mPaint);
                return;
            }
            if (mFraction <= 0.8D && mFraction > 0.75D) {
                mArcPath.reset();
                mArcPath.moveTo(mCenterX + mCircleRadius, mCenterY);
                mArcPath.cubicTo(mCenterX + mCircleRadius + (0.8F - mFraction) * 160.0F,
                        mCenterY + mCircleRadius / 2.0F + (0.8F - mFraction) * 160.0F,
                        mCenterX + mCircleRadius / 2.0F + (0.8F - mFraction) * 160.0F,
                        mCenterY + mCircleRadius + (0.8F - mFraction) * 160.0F, mCenterX,
                        mCenterY + mCircleRadius);
                canvas.drawPath(mArcPath, mPaint);
                return;
            }
            return;
        }
        canvas.drawCircle(mCenterX, mCenterY, mCircleRadius, mPaint);
        float f1 = mCenterX;
        float f2 = 2.2F * mCircleRadius / (float) Math.sqrt(2.0D);
        float f3 = 1.2F * mCircleRadius / (float) Math.sqrt(2.0D) / 0.2F;
        float f4 = mFraction;
        float f5 = mCenterY;
        float f6 = 2.2F * mCircleRadius / (float) Math.sqrt(2.0D);
        float f7 = 1.2F * mCircleRadius / (float) Math.sqrt(2.0D) / 0.2F;
        float f8 = mFraction;
        float f9 = mCenterX;
        float f10 = 2.2F * mCircleRadius / (float) Math.sqrt(2.0D);
        float f11 = mCenterY;
        canvas.drawLine(f1 + f2 - f3 * (f4 - 0.8F), f5 + f6 - f7 * (f8 - 0.8F), f10 + f9,
                2.2F * mCircleRadius / (float) Math.sqrt(2.0D) + f11, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = (float) w / 2.0F;
        mCenterY = (float) h / 2.0F;
        mCircleRadius = (float) w / 4.0F;
        mRectF = new RectF(mCenterX - 0.95F * mCircleRadius, mCenterY - 0.95F * mCircleRadius,
                mCenterX + 0.95F * mCircleRadius, mCenterY + 0.95F * mCircleRadius);
        mPath.moveTo(mCenterX + 2.2F * mCircleRadius / (float) Math.sqrt(2.0D),
                mCenterY + 2.2F * mCircleRadius / (float) Math.sqrt(2.0D));
        mPath.lineTo(mCenterX, mCenterY);
        mPath.lineTo(mCenterX - 0.45F * mCircleRadius * (float) Math.sqrt(3.0D),
                mCenterY + 0.45F * mCircleRadius);
        mPath.lineTo(mCenterX - 0.45F * mCircleRadius * (float) Math.sqrt(3.0D),
                mCenterY - 0.45F * mCircleRadius);
        mPath.lineTo(mCenterX + 0.45F * mCircleRadius * (float) Math.sqrt(3.0D), mCenterY);
        mPath.lineTo(mCenterX, mCenterY);
        mPath.lineTo(mCenterX + 2.2F * mCircleRadius / (float) Math.sqrt(2.0D),
                mCenterY + 2.2F * mCircleRadius / (float) Math.sqrt(2.0D));
        mPathMeasure.setPath(mPath, false);
        mPathLength = mPathMeasure.getLength();
    }

    public void start() {
        if (mCurrentState == STATE_SEARCHING)
            return;
        mCurrentState = STATE_SEARCHING;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0F, 100.0F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animator) {
                mFraction = animator.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
                mCurrentState = STATE_WAIT; 
            }
        });
        valueAnimator.start();
    }
}
