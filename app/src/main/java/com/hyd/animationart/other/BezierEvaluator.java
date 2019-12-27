package com.hyd.animationart.other;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.hyd.animationart.utils.BezierUtil;

/**
 * Created by hydCoder on 2019/12/27.
 * 以梦为马，明日天涯。
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mFlagPointF;

    public BezierEvaluator(PointF flagPointF) {
        this.mFlagPointF = flagPointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(fraction, startValue, mFlagPointF, endValue);
    }
}
