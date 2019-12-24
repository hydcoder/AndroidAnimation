package com.hyd.animationart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2019/12/23.
 * 以梦为马，明日天涯。
 */
public class ThirdBezierView extends View {

    private float mStartPointX;
    private float mStartPointY;

    private float mEndPointX;
    private float mEndPointY;

    private float mFlagPointOneX;
    private float mFlagPointOneY;

    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;
    private Paint mPaintBezier;
    private Paint mPaintFlag;
    private Paint mPaintText;

    private boolean isSecondPoint = false;

    public ThirdBezierView(Context context) {
        super(context);
        init();
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        init();
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintFlag = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintFlag.setStrokeWidth(5);
        mPaintFlag.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setTextSize(26);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mStartPointX = w / 4;
        mStartPointY = h / 2 - 200;

        mEndPointX = w * 3 / 4;
        mEndPointY = h / 2 - 200;

        mFlagPointOneX = w / 2 - 100;
        mFlagPointOneY = h / 2 - 300;
        mFlagPointTwoX = w / 2 + 100;
        mFlagPointTwoY = h / 2 - 300;

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX, mStartPointY);
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mEndPointX, mEndPointY);

        canvas.drawPoint(mStartPointX, mStartPointY, mPaintFlag);
        canvas.drawText("起点", mStartPointX, mStartPointY, mPaintText);
        canvas.drawPoint(mEndPointX, mEndPointY, mPaintFlag);
        canvas.drawText("终点", mEndPointX, mEndPointY, mPaintText);
        canvas.drawPoint(mFlagPointOneX, mFlagPointOneY, mPaintFlag);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mPaintText);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mPaintText);
        canvas.drawLine(mStartPointX, mStartPointY, mFlagPointOneX, mFlagPointOneY, mPaintFlag);
        canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag);
        canvas.drawLine(mEndPointX, mEndPointY, mFlagPointTwoX, mFlagPointTwoY, mPaintFlag);

        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondPoint = true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondPoint = false;
                break;
            case MotionEvent.ACTION_MOVE:
                mFlagPointOneX = event.getX(0);
                mFlagPointOneY = event.getY(0);
                if (isSecondPoint) {
                    mFlagPointTwoX = event.getX(1);
                    mFlagPointTwoY = event.getY(1);
                }
                invalidate();
                break;
        }
        return true;
    }
}
