package com.hyd.animationart.views;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import androidx.annotation.Nullable;

import com.hyd.animationart.utils.Utils;

/**
 * Created by hydCoder on 2020/3/5.
 * 以梦为马，明日天涯。
 */
public class CircleView extends View {

    private static final int START_COLOR = Color.parseColor("#ffff5722");
    private static final int END_COLOR = Color.parseColor("#ffffc107");

    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
        @Override
        public Float get(CircleView circleView) {
            return circleView.getInnerCircleRadiusProgress();
        }

        @Override
        public void set(CircleView circleView, Float value) {
            circleView.setInnerCircleRadiusProgress(value);
        }
    };

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
        @Override
        public Float get(CircleView circleView) {
            return circleView.getOuterCircleRadiusProgress();
        }

        @Override
        public void set(CircleView circleView, Float value) {
            circleView.setOuterCircleRadiusProgress(value);
        }
    };

    private float innerCircleRadiusProgress = 0.0f;
    private float outerCircleRadiusProgress = 0.0f;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private Paint circlePaint = new Paint();
    private Paint maskPaint = new Paint();

    private int maxCircleSize;

    private Bitmap tempBitmap;

    private Canvas tempCanvas;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint.setStyle(Paint.Style.FILL);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public float getInnerCircleRadiusProgress() {
        return innerCircleRadiusProgress;
    }

    public float getOuterCircleRadiusProgress() {
        return outerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        postInvalidate();
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();
        postInvalidate();
    }

    private void updateCircleColor() {
        float f = (float) Utils.mapValueFromRangeToRange(Utils.clamp(outerCircleRadiusProgress, 0.5d, 1.0d), 0.5d, 1.0d, 0d, 1.0d);
        circlePaint.setColor((Integer) argbEvaluator.evaluate(f, START_COLOR, END_COLOR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tempCanvas.drawColor(Color.parseColor("#00ffffff"), PorterDuff.Mode.CLEAR);
        tempCanvas.drawCircle((getWidth() / 2f), (getHeight() / 2f), outerCircleRadiusProgress * maxCircleSize, circlePaint);
        tempCanvas.drawCircle((getWidth() / 2f), (getHeight() / 2f), innerCircleRadiusProgress * maxCircleSize, circlePaint);
        canvas.drawBitmap(tempBitmap, 0, 0, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxCircleSize = w / 2;
        tempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(tempBitmap);
    }
}
