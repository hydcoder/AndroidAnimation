package com.hyd.animationart.other;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.hyd.animationart.utils.BezierUtil;

/**
 * Created by hydCoder on 2020/1/2.
 * 以梦为马，明日天涯。
 */
public class ThirdBezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1;

    private PointF pointF2;

    public ThirdBezierEvaluator(PointF paramPointF1, PointF paramPointF2) {
        this.pointF1 = paramPointF1;
        this.pointF2 = paramPointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForCubic(fraction, startValue, pointF1, pointF2, endValue);
    }
}
