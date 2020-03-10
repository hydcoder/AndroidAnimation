package com.hyd.animationart.other;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by hydCoder on 2020/3/4.
 * 以梦为马，明日天涯。
 */
public class JumpingSpan extends ReplacementSpan {

    private float translationX = 0.0F;

    private float translationY = 0.0F;

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        return (int) paint.measureText(text, start, end);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        canvas.drawText(text, start, end, x + translationX, y + translationY, paint);
    }
}
