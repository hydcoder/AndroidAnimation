package com.hyd.animationart.views;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import androidx.annotation.Nullable;

import com.hyd.animationart.utils.Utils;

/**
 * Created by hydCoder on 2020/3/5.
 * 以梦为马，明日天涯。
 */
public class DotsView extends View {

    private static final int COLOR1 = Color.parseColor("#ffffc107");
    private static final int COLOR2 = Color.parseColor("#ffff9800");
    private static final int COLOR3 = Color.parseColor("#ffff5722");
    private static final int COLOR4 = Color.parseColor("#fff44336");

    private static final int DOTS_COUNT = 7;

    private static final int OUTER_DOTS_POSITION_ANGEL = 51;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private int centerX;
    private int centerY;

    private final Paint[] circlePaints = new Paint[4];

    private float currentDotSize1 = 0f;
    private float currentDotSize2 = 0f;

    private float currentProgress = 0f;

    private float currentRadius1 = 0f;
    private float currentRadius2 = 0f;

    private float maxDotSize;

    private float maxInnerDotsRadius;
    private float maxOuterDotsRadius;

    public static final Property<DotsView, Float> DOTS_PROGRESS =
            new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView dotsView) {
            return dotsView.getCurrentProgress();
        }

        @Override
        public void set(DotsView dotsView, Float value) {
            dotsView.setCurrentProgress(value);
        }
    };

    public DotsView(Context context) {
        super(context);
        init();
    }

    public DotsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        for (int i = 0; i < circlePaints.length; i++) {
            circlePaints[i] = new Paint();
            circlePaints[i].setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxDotSize = 20f;
        maxOuterDotsRadius = centerX - maxDotSize * 2f;
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius;
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int j = (int) (centerX + currentRadius1 * Math.cos((i * 51) * Math.PI / 180d));
            int k = (int) (centerY + currentRadius1 * Math.sin((i * 51) * Math.PI / 180d));
            canvas.drawCircle(j, k, currentDotSize1, circlePaints[i % circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int j = (int) (centerX + currentRadius2 * Math.cos((i * 51 - 10) * Math.PI / 180d));
            int k = (int) (centerY + currentRadius2 * Math.sin((i * 51 - 10) * Math.PI / 180d));
            canvas.drawCircle(j, k, currentDotSize2, circlePaints[(i + 1) % circlePaints.length]);
        }
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();
        postInvalidate();
    }

    private void updateDotsAlpha() {
        int i = (int) Utils.mapValueFromRangeToRange((float) Utils.clamp(currentProgress,
                0.6000000238418579D, 1.0D), 0.6000000238418579D, 1.0D, 255.0D, 0.0D);
        for (Paint circlePaint : circlePaints) {
            circlePaint.setAlpha(i);
        }
    }

    private void updateDotsPaints() {
        if (currentProgress < 0.5f) {
            float f = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0d, 0.5d, 0.0d, 1.0d);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(f, COLOR1, COLOR2));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(f, COLOR2, COLOR3));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(f, COLOR3, COLOR4));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(f, COLOR4, COLOR1));
        } else {
            float f = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5d, 1.0d, 0.0d, 1.0d);
            circlePaints[0].setColor((Integer) argbEvaluator.evaluate(f, COLOR2, COLOR3));
            circlePaints[1].setColor((Integer) argbEvaluator.evaluate(f, COLOR3, COLOR4));
            circlePaints[2].setColor((Integer) argbEvaluator.evaluate(f, COLOR4, COLOR1));
            circlePaints[3].setColor((Integer) argbEvaluator.evaluate(f, COLOR1, COLOR2));
        }
    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0d, 0.30000001192092896D, 0.0D, (maxOuterDotsRadius * 0.8F));
        } else {
            currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.30000001192092896D, 1.0D, (maxOuterDotsRadius * 0.8F), maxOuterDotsRadius);
        }

        if (currentProgress < 0.7f) {
            currentDotSize1 = maxDotSize;
        } else {
            currentDotSize1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.699999988079071D, 1.0D, maxDotSize, 0.0D);
        }
    }

    private void updateInnerDotsPosition() {
        if (currentProgress < 0.3f) {
            currentRadius2 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0d, 0.30000001192092896D, 0.0D, (maxInnerDotsRadius * 0.8F));
        } else {
            currentRadius2 = maxInnerDotsRadius;
        }

        if (currentProgress < 0.2f) {
            currentDotSize2 = maxDotSize;
            return;
        }
        if (currentProgress < 0.5f) {
            currentDotSize2 = (float)Utils.mapValueFromRangeToRange(currentProgress, 0.20000000298023224D, 0.5D, maxDotSize, maxDotSize * 0.3D);
            return;
        }
        currentDotSize2 = (float)Utils.mapValueFromRangeToRange(currentProgress, 0.5D, 1.0D, (maxDotSize * 0.3D), 0.0D);
    }
}
