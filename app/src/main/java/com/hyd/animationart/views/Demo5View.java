package com.hyd.animationart.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/1/18.
 * 以梦为马，明日天涯。
 */
public class Demo5View extends View {

    private static final float C = 0.55191505f;

    private float mDistanceBezier;

    private float mFraction = 0.0f;

    private float mFractionDegree = 0.0f;

    private int mRadiusBig = 120;
    private int mRadiusSmall = 60;

    private int mWidth;
    private int mHeight;
    private int mMinWidth = mRadiusSmall * 2 * 3;

    private float mLength;

    private Paint mPaint = new Paint();

    private Path mPathBezier;
    private Path mPathCircle;

    private PathMeasure mPathMeasure;

    private float[] mPointCtrl = new float[16];
    private float[] mPointData = new float[8];
    private float[] mPos = new float[2];

    private ValueAnimator mAnimator;

    public Demo5View(Context context) {
        super(context);
        init();
    }

    public Demo5View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo5View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#7c1c1c"));
        mPaint.setAntiAlias(true);

        mPathCircle = new Path();
        mPathBezier = new Path();
        mPathMeasure = new PathMeasure();

        mAnimator = ValueAnimator.ofFloat(0f, 1f, 0f);
        mAnimator.setDuration(3000L);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFractionDegree = (float) animation.getAnimatedValue();
                mFraction = animation.getAnimatedFraction();
                invalidate();
            }
        });
    }

    private void setBezierPath() {
        mPathBezier.reset();
        float f3 = (mRadiusSmall * 2 + mRadiusSmall / 2.0f) * mFraction;
        float f4 = mRadiusSmall * (1.0f - 0.6f * mFraction);
        float f2 = f4 - C * f3 * (mFraction * 0.5f - 0.5f);
        float f1 = f2;
        if (mDistanceBezier != 0.0f) {
            if (f2 < mDistanceBezier) {
                f1 = mDistanceBezier;
            }
        }
        mPathBezier.moveTo(-f3, f4);
        mPathBezier.cubicTo(-f3, f1, f3, f1, f3, f4);
        if (mDistanceBezier == 0.0f) {
            mPathMeasure.setPath(mPathBezier, false);
            mLength = mPathMeasure.getLength();
            mPathMeasure.getPosTan(mLength / 2.0f, mPos, null);
            if (mPos[1] < 8.0f) {
                mDistanceBezier = f1;
                mPathBezier.reset();
                mPathBezier.moveTo(-f3, f4);
                mPathBezier.cubicTo(-f3, mDistanceBezier, f3, mDistanceBezier, f3, f4);
                mPathBezier.lineTo(f3, -f4);
                mPathBezier.cubicTo(f3, -mDistanceBezier, -f3, -mDistanceBezier, -f3, -f4);
                mPathBezier.close();
                return;
            }
        }
        mPathBezier.lineTo(f3, -f4);
        mPathBezier.cubicTo(f3, -f1, -f3, -f1, -f3, -f4);
        mPathBezier.close();
    }

    private void setCirclePath() {
        mPointData[0] = -mRadiusBig + mRadiusSmall / 2.0F * mFraction * 3.0F;
        mPointData[1] = 0.0F;
        mPointData[2] = 0.0F;
        mPointData[3] = mRadiusBig - mRadiusBig / 2.0F * mFraction * 3.0F;
        mPointData[4] = mRadiusBig - mRadiusSmall / 2.0F * mFraction * 3.0F;
        mPointData[5] = 0.0F;
        mPointData[6] = mPointData[2];
        mPointData[7] = -mPointData[3];

        mPointCtrl[0] = mPointData[0];
        mPointCtrl[1] = mRadiusBig * C;
        mPointCtrl[2] = mPointData[2] - mRadiusBig * C;
        mPointCtrl[3] = mPointData[3];
        mPointCtrl[4] = mPointData[2] + mRadiusBig * C;
        mPointCtrl[5] = mPointData[3];
        mPointCtrl[6] = mPointData[4];
        mPointCtrl[7] = mPointCtrl[1];
        mPointCtrl[8] = mPointData[4];
        mPointCtrl[9] = -mPointCtrl[1];
        mPointCtrl[10] = mPointCtrl[4];
        mPointCtrl[11] = mPointData[7];
        mPointCtrl[12] = mPointCtrl[2];
        mPointCtrl[13] = mPointData[7];
        mPointCtrl[14] = mPointData[0];
        mPointCtrl[15] = -mPointCtrl[1];

        mPathCircle.reset();
        mPathCircle.moveTo(mPointData[0], mPointData[1]);
        mPathCircle.cubicTo(mPointCtrl[0], mPointCtrl[1], mPointCtrl[2], mPointCtrl[3],
                mPointData[2], mPointData[3]);
        mPathCircle.cubicTo(mPointCtrl[4], mPointCtrl[5], mPointCtrl[6], mPointCtrl[7],
                mPointData[4], mPointData[5]);
        mPathCircle.cubicTo(mPointCtrl[8], mPointCtrl[9], mPointCtrl[10], mPointCtrl[11],
                mPointData[6], mPointData[7]);
        mPathCircle.cubicTo(mPointCtrl[12], mPointCtrl[13], mPointCtrl[14], mPointCtrl[15],
                mPointData[0], mPointData[1]);
    }

    private void setDoubleCirClePath() {
        mPathCircle.reset();
        if (mFraction < 0.33333334F) {
            mPathCircle.addCircle(-mRadiusSmall / 2.0F * mFraction * 3.0F, 0.0F, mRadiusSmall,
                    Path.Direction.CW);
            mPathCircle.addCircle(mRadiusSmall / 2.0F * mFraction * 3.0F, 0.0F, mRadiusSmall,
                    Path.Direction.CW);
            return;
        }
        float f =
                (mFraction - 0.33333334F) / 0.6666667F * ((mRadiusSmall * 2) + mRadiusSmall / 2.0F);
        mPathCircle.addCircle(-mRadiusSmall / 2.0F - f, 0.0F, mRadiusSmall, Path.Direction.CW);
        mPathCircle.addCircle(mRadiusSmall / 2.0F + f, 0.0F, mRadiusSmall, Path.Direction.CW);
    }

    private void setLastBezierPath() {
        float f =
                -mRadiusSmall / 2.0F - (mFraction - 0.33333334F) / 0.6666667F * ((mRadiusSmall * 2) + mRadiusSmall / 2.0F);
        mPathBezier.reset();
        mPathBezier.moveTo(f, mRadiusSmall);
        mPathBezier.quadTo(f, 0.0F, mRadiusSmall + f + mRadiusSmall * (4.0F - mFraction * 4.0F),
                0.0F);
        mPathBezier.quadTo(f, 0.0F, f, -mRadiusSmall);
        mPathBezier.lineTo(f, mRadiusSmall);
        mPathBezier.moveTo(-f, mRadiusSmall);
        mPathBezier.quadTo(-f, 0.0F, -f - mRadiusSmall - mRadiusSmall * (4.0F - mFraction * 4.0F)
                , 0.0F);
        mPathBezier.quadTo(-f, 0.0F, -f, -mRadiusSmall);
        mPathBezier.lineTo(-f, mRadiusSmall);
        mPathBezier.close();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAnimator.isRunning()) mAnimator.start();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator.isRunning()) mAnimator.cancel();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate((mWidth / 2f), (mHeight / 2f));
        canvas.scale(1.0F, -1.0F);
        canvas.rotate(-360.0F * mFractionDegree);
        setDoubleCirClePath();
        canvas.drawPath(mPathCircle, mPaint);
        if (mFraction < 0.33333334F) {
            setCirclePath();
            canvas.drawPath(mPathCircle, mPaint);
            return;
        }
        if (mFraction < 0.75F) {
            setBezierPath();
            canvas.drawPath(mPathBezier, mPaint);
            return;
        }
        setLastBezierPath();
        canvas.drawPath(mPathBezier, mPaint);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        int i = View.MeasureSpec.getMode(widthMeasureSpec);
        int j = View.MeasureSpec.getMode(heightMeasureSpec);
        if (i != Integer.MIN_VALUE && j != Integer.MIN_VALUE) {
            if (mWidth < mMinWidth) mWidth = mMinWidth;
            if (mHeight < mMinWidth) mHeight = mMinWidth;
        } else if (widthMeasureSpec != Integer.MIN_VALUE) {
            if (mWidth < mMinWidth) mWidth = mMinWidth;
        } else if (heightMeasureSpec != Integer.MIN_VALUE && mHeight < mMinWidth) {
            mHeight = mMinWidth;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


}
