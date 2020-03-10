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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/3/10.
 * 以梦为马，明日天涯。
 */
public class Demo9View extends View {

    private static final int MAX_PROGRESS = 100;

    private static final float START_ANGLE = -90.0F;

    private Paint circlePaint = new Paint();
    private Paint commaPaint = new Paint();
    private Paint downRectPaint = new Paint();
    private Paint smallRectPaint = new Paint();
    private Paint tickPaint = new Paint();

    private PathMeasure commaPathMeasure1;
    private PathMeasure commaPathMeasure2;
    private PathMeasure downPathMeasure1;
    private PathMeasure downPathMeasure2;
    private PathMeasure forkPathMeasure1;
    private PathMeasure forkPathMeasure2;
    private PathMeasure forkPathMeasure3;
    private PathMeasure tickPathMeasure;

    private float commaPercent = 0;
    private float downPercent = 0;
    private float forkPercent = 0;
    private int shockPercent = 0;
    private float tickPercent = 0;

    private float curSweepAngle = 0;
    private float endAngle;

    public boolean isSuccess = false;

    private ValueAnimator mCommaAnimator;
    private ValueAnimator mDownAnimator;
    private ValueAnimator mForkAnimator;
    private ValueAnimator mRotateAnimator;
    private ValueAnimator mTickAnimator;
    private ValueAnimator mShockAnimator;

    private int progress = 0;
    private int status = 0;
    private int strokeWidth = 20;

    private float radius = 0;

    private RectF mRectF = new RectF();

    public Demo9View(Context context) {
        super(context);
        init();
    }

    public Demo9View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo9View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.argb(255, 48, 63, 159));
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);

        smallRectPaint.setAntiAlias(true);
        smallRectPaint.setColor(Color.argb(255, 48, 63, 159));
        smallRectPaint.setStrokeWidth(strokeWidth / 2);
        smallRectPaint.setStyle(Paint.Style.STROKE);

        downRectPaint.setAntiAlias(true);
        downRectPaint.setColor(Color.argb(255, 48, 63, 159));
        downRectPaint.setStrokeWidth(strokeWidth);
        downRectPaint.setStyle(Paint.Style.FILL);

        commaPaint.setAntiAlias(true);
        commaPaint.setColor(Color.argb(255, 229, 57, 53));
        commaPaint.setStrokeWidth(strokeWidth);
        commaPaint.setStyle(Paint.Style.STROKE);

        tickPaint.setAntiAlias(true);
        tickPaint.setColor(Color.argb(255, 0, 150, 136));
        tickPaint.setStrokeWidth(strokeWidth);
        tickPaint.setStyle(Paint.Style.STROKE);

        endAngle = (float) Math.atan(1.3333333730697632D);

        mRotateAnimator = ValueAnimator.ofFloat(0f, endAngle * 0.9f);
        mRotateAnimator.setDuration(500);
        mRotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curSweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mRotateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                curSweepAngle = 0;
                if (isSuccess) {
                    status = 2;
                    mDownAnimator.start();
                } else {
                    status = 5;
                    mCommaAnimator.start();
                }
            }
        });

        mDownAnimator = ValueAnimator.ofFloat(0f, 1f);
        mDownAnimator.setDuration(500);
        mDownAnimator.setInterpolator(new AccelerateInterpolator());
        mDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                downPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mDownAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                status = 3;
                mForkAnimator.start();
            }
        });

        mForkAnimator = ValueAnimator.ofFloat(0f, 1f);
        mForkAnimator.setDuration(100);
        mForkAnimator.setInterpolator(new LinearInterpolator());
        mForkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                forkPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mForkAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mTickAnimator.start();
            }
        });

        mTickAnimator = ValueAnimator.ofFloat(0f, 1f);
        mTickAnimator.setStartDelay(1000);
        mTickAnimator.setDuration(500);
        mTickAnimator.setInterpolator(new AccelerateInterpolator());
        mTickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tickPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mTickAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                status = 4;
            }
        });

        mCommaAnimator = ValueAnimator.ofFloat(0f, 1f);
        mCommaAnimator.setDuration(300);
        mCommaAnimator.setInterpolator(new AccelerateInterpolator());
        mCommaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                commaPercent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mCommaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                status = 6;
                mShockAnimator.start();
            }
        });

        mShockAnimator = ValueAnimator.ofInt(-1, 0, 1, 0, -1, 0, 1, 0);
        mShockAnimator.setDuration(500);
        mShockAnimator.setInterpolator(new LinearInterpolator());
        mShockAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shockPercent = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void start() {
        post(new Runnable() {
            @Override
            public void run() {
                mRotateAnimator.start();
            }
        });
    }

    public void finishFail() {
        setProgress(100);
        isSuccess = false;
        status = 1;
        start();
    }

    public void finishSuccess() {
        setProgress(100);
        isSuccess = true;
        status = 1;
        start();
    }

    public void setProgress(int progress) {
        this.progress = Math.min(progress, MAX_PROGRESS);
        postInvalidate();
        if (progress == 0) {
            status = 0;
        }
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float f;
        switch (status) {
            case 0:
                f = 1f * progress / 100.0f;
                canvas.drawArc(mRectF, START_ANGLE - 270.0F * f, -(60F + 300F * f), false,
                        circlePaint);
                return;
            case 1:
                drawSmallRectFly(canvas);
                return;
            case 2:
                drawRectDown(canvas);
                return;
            case 3:
                drawFork(canvas);
                return;
            case 4:
                drawTick(canvas);
                return;
            case 5:
                drawComma(canvas);
                return;
            case 6:
                break;
            default:
                return;
        }
        drawShockComma(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View.MeasureSpec.getMode(widthMeasureSpec);
        widthMeasureSpec = View.MeasureSpec.getSize(widthMeasureSpec);
        View.MeasureSpec.getMode(heightMeasureSpec);
        heightMeasureSpec = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(this.strokeWidth * 10 + widthMeasureSpec,
                this.strokeWidth * 10 + heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = (Math.min(getMeasuredWidth(), getMeasuredHeight()) / 4f - strokeWidth);
        mRectF.set(new RectF(radius + strokeWidth, radius + strokeWidth,
                3.0F * radius + strokeWidth, 3.0F * radius + strokeWidth));
        Path path1 = new Path();
        path1.moveTo(2.0F * radius + strokeWidth, strokeWidth * 1f);
        path1.lineTo(2.0F * radius + strokeWidth, radius + strokeWidth);
        Path path2 = new Path();
        path2.moveTo(2.0F * radius + strokeWidth, strokeWidth * 1f);
        path2.lineTo(2.0F * radius + strokeWidth, 2.0F * radius + strokeWidth);
        downPathMeasure1 = new PathMeasure(path1, false);
        downPathMeasure2 = new PathMeasure(path2, false);
        path1 = new Path();
        path1.moveTo(2.0F * radius + strokeWidth, 2.0F * radius + strokeWidth);
        path1.lineTo(2.0F * radius + strokeWidth, 3.0F * radius + strokeWidth);
        float f1 = (float) Math.sin(1.0471975511965976D);
        float f2 = (float) Math.cos(1.0471975511965976D);
        path2 = new Path();
        path2.moveTo(2.0F * radius + strokeWidth, 2.0F * radius + strokeWidth);
        path2.lineTo(2.0F * radius - radius * f1 + strokeWidth,
                2.0F * radius + radius * f2 + strokeWidth);
        Path path3 = new Path();
        path3.moveTo(2.0F * radius + strokeWidth, 2.0F * radius + strokeWidth);
        path3.lineTo(2.0F * radius + radius * f1 + strokeWidth,
                2.0F * radius + radius * f2 + strokeWidth);
        forkPathMeasure1 = new PathMeasure(path1, false);
        forkPathMeasure2 = new PathMeasure(path2, false);
        forkPathMeasure3 = new PathMeasure(path3, false);
        path1 = new Path();
        path1.moveTo(1.5F * radius + strokeWidth, 2.0F * radius + strokeWidth);
        path1.lineTo(1.5F * radius + 0.3F * radius + strokeWidth,
                2.0F * radius + 0.3F * radius + strokeWidth);
        path1.lineTo(2.0F * radius + 0.5F * radius + strokeWidth,
                2.0F * radius - 0.3F * radius + strokeWidth);
        tickPathMeasure = new PathMeasure(path1, false);
        path1 = new Path();
        path2 = new Path();
        path1.moveTo(2.0F * radius + strokeWidth, 1.25F * radius + strokeWidth);
        path1.lineTo(2.0F * radius + strokeWidth, 2.25F * radius + strokeWidth);
        path2.moveTo(2.0F * radius + strokeWidth, 2.75F * radius + strokeWidth);
        path2.lineTo(2.0F * radius + strokeWidth, 2.5F * radius + strokeWidth);
        commaPathMeasure1 = new PathMeasure(path1, false);
        commaPathMeasure2 = new PathMeasure(path2, false);
        super.onSizeChanged(w, h, oldw, oldh);
    }


    private void drawSmallRectFly(Canvas canvas) {
        canvas.save();
        canvas.translate(radius / 2.0F + strokeWidth, 2.0F * radius + strokeWidth);
        float f = 5.0F * radius / 2.0F;
        canvas.drawLine((float) (f * Math.cos(curSweepAngle)),
                -(float) (f * Math.sin(curSweepAngle)),
                (float) (f * Math.cos(curSweepAngle + 0.05 * endAngle + 0.1 * endAngle * (1 - curSweepAngle / 0.9 * endAngle))), -(float) (f * Math.sin(curSweepAngle + 0.05 * endAngle + 0.1 * endAngle * (1 - curSweepAngle / 0.9 * endAngle))), smallRectPaint);
        canvas.restore();
        circlePaint.setColor(Color.argb(255, 48, 63, 159));
        canvas.drawArc(mRectF, 0, 360, false, circlePaint);
    }

    private void drawRectDown(Canvas canvas) {
        float[] arrayOfFloat1 = new float[2];
        float[] arrayOfFloat2 = new float[2];
        downPathMeasure1.getPosTan(downPercent * downPathMeasure1.getLength(), arrayOfFloat1,
                arrayOfFloat2);
        arrayOfFloat2 = new float[2];
        float[] arrayOfFloat3 = new float[2];
        downPathMeasure2.getPosTan(downPercent * downPathMeasure2.getLength(), arrayOfFloat2,
                arrayOfFloat3);
        Rect rect = new Rect(Math.round(mRectF.left),
                Math.round(mRectF.top + mRectF.height() * 0.1F * downPercent),
                Math.round(mRectF.right),
                Math.round(mRectF.bottom - mRectF.height() * 0.1F * downPercent));
        Region region2 = new Region(Math.round(arrayOfFloat1[0]) - strokeWidth / 4,
                Math.round(arrayOfFloat1[1]), Math.round(arrayOfFloat2[0] + (strokeWidth / 4f)),
                Math.round(arrayOfFloat2[1]));
        region2.op(rect, Region.Op.DIFFERENCE);
        drawRegion(canvas, region2, downRectPaint);
        Region region1 = new Region(Math.round(arrayOfFloat1[0]) - strokeWidth / 2,
                Math.round(arrayOfFloat1[1]), Math.round(arrayOfFloat2[0] + (strokeWidth / 2f)),
                Math.round(arrayOfFloat2[1]));
        boolean bool = region1.op(rect, Region.Op.INTERSECT);
        drawRegion(canvas, region1, downRectPaint);
        if (bool) {
            float f = (arrayOfFloat2[1] - radius) / radius;
            canvas.drawArc(new RectF(mRectF.left, mRectF.top + mRectF.height() * 0.1F * f,
                    mRectF.right, mRectF.bottom - mRectF.height() * 0.1F * f), 0.0F, 360.0F,
                    false, circlePaint);
            return;
        }
        canvas.drawArc(mRectF, 0.0F, 360.0F, false, circlePaint);
    }

    private void drawFork(Canvas canvas) {
        float[] arrayOfFloat1 = new float[2];
        float[] arrayOfFloat2 = new float[2];
        forkPathMeasure1.getPosTan(forkPercent * forkPathMeasure1.getLength(), arrayOfFloat1,
                arrayOfFloat2);
        arrayOfFloat2 = new float[2];
        float[] arrayOfFloat3 = new float[2];
        forkPathMeasure2.getPosTan(forkPercent * forkPathMeasure2.getLength(), arrayOfFloat2,
                arrayOfFloat3);
        arrayOfFloat3 = new float[2];
        float[] arrayOfFloat4 = new float[2];
        forkPathMeasure3.getPosTan(forkPercent * forkPathMeasure3.getLength(), arrayOfFloat3,
                arrayOfFloat4);
        float f1 = radius;
        float f2 = strokeWidth;
        float f3 = radius;
        float f4 = strokeWidth;
        float f5 = radius;
        float f6 = strokeWidth;
        float f7 = radius;
        canvas.drawLine(2.0F * f1 + f2, f4 + f3, f6 + 2.0F * f5, strokeWidth + 2.0F * f7,
                downRectPaint);
        f1 = radius;
        f2 = strokeWidth;
        f3 = radius;
        canvas.drawLine(2.0F * f1 + f2, strokeWidth + 2.0F * f3, arrayOfFloat1[0],
                arrayOfFloat1[1], downRectPaint);
        f1 = radius;
        f2 = strokeWidth;
        f3 = radius;
        canvas.drawLine(2.0F * f1 + f2, strokeWidth + 2.0F * f3, arrayOfFloat2[0],
                arrayOfFloat2[1], downRectPaint);
        f1 = radius;
        f2 = strokeWidth;
        f3 = radius;
        canvas.drawLine(2.0F * f1 + f2, strokeWidth + 2.0F * f3, arrayOfFloat3[0],
                arrayOfFloat3[1], downRectPaint);
        canvas.drawArc(new RectF(mRectF.left,
                mRectF.top + mRectF.height() * 0.1F * (1.0F - forkPercent), mRectF.right,
                mRectF.bottom - mRectF.height() * 0.1F * (1.0F - forkPercent)), 0.0F, 360.0F,
                false, circlePaint);
    }

    private void drawTick(Canvas canvas) {
        Path path = new Path();
        tickPathMeasure.getSegment(0.0F, tickPercent * tickPathMeasure.getLength(), path, true);
        path.rLineTo(0.0F, 0.0F);
        canvas.drawPath(path, tickPaint);
        canvas.drawArc(mRectF, 0.0F, 360.0F, false, tickPaint);
    }

    private void drawComma(Canvas canvas) {
        Path path1 = new Path();
        commaPathMeasure1.getSegment(0.0F, commaPercent * commaPathMeasure1.getLength(), path1,
                true);
        path1.rLineTo(0.0F, 0.0F);
        Path path2 = new Path();
        commaPathMeasure2.getSegment(0.0F, commaPercent * commaPathMeasure2.getLength(), path2,
                true);
        path2.rLineTo(0.0F, 0.0F);
        canvas.drawPath(path1, commaPaint);
        canvas.drawPath(path2, commaPaint);
        canvas.drawArc(mRectF, 0.0F, 360.0F, false, commaPaint);
    }

    private void drawShockComma(Canvas canvas) {
        Path path1 = new Path();
        commaPathMeasure1.getSegment(0.0F, commaPathMeasure1.getLength(), path1, true);
        path1.rLineTo(0.0F, 0.0F);
        Path path2 = new Path();
        commaPathMeasure2.getSegment(0.0F, commaPathMeasure2.getLength(), path2, true);
        path2.rLineTo(0.0F, 0.0F);
        if (shockPercent != 0) {
            canvas.save();
            int shockDir = 20;
            if (shockPercent == 1) {
                canvas.rotate(shockDir, radius * 2.0F, radius * 2.0F);
            } else if (shockPercent == -1) {
                canvas.rotate(-shockDir, radius * 2.0F, radius * 2.0F);
            }
        }
        canvas.drawPath(path1, commaPaint);
        canvas.drawPath(path2, commaPaint);
        canvas.drawArc(mRectF, 0.0F, 360.0F, false, commaPaint);
        if (shockPercent != 0) canvas.restore();
    }

    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();
        while (regionIterator.next(rect)) canvas.drawRect(rect, paint);
    }
}
