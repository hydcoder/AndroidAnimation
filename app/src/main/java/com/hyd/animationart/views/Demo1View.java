package com.hyd.animationart.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hyd.animationart.R;
import com.hyd.animationart.other.ThirdBezierEvaluator;

import java.util.Random;

/**
 * Created by hydCoder on 2020/1/2.
 * 以梦为马，明日天涯。
 */
public class Demo1View extends RelativeLayout {

    private Interpolator acc = new AccelerateInterpolator();
    private Interpolator accDec = new AccelerateDecelerateInterpolator();
    private Interpolator dec = new DecelerateInterpolator();
    private Interpolator line = new LinearInterpolator();

    private Interpolator[] interpolators;

    private int dHeight;  // 爱心的高度
    private int dWidth;   // 爱心的宽度

    private int mHeight;
    private int mWidth;

    private Drawable[] drawables;

    private RelativeLayout.LayoutParams layoutParams;

    private Random random = new Random();

    public Demo1View(Context context) {
        super(context);
        init();
    }

    public Demo1View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        drawables = new Drawable[3];
        Drawable drawableBlue = getResources().getDrawable(R.drawable.pl_blue);
        Drawable drawableRed = getResources().getDrawable(R.drawable.pl_red);
        Drawable drawableYellow = getResources().getDrawable(R.drawable.pl_yellow);
        drawables[0] = drawableBlue;
        drawables[1] = drawableRed;
        drawables[2] = drawableYellow;
        dHeight = drawableBlue.getIntrinsicHeight();
        dWidth = drawableBlue.getIntrinsicWidth();

        layoutParams = new RelativeLayout.LayoutParams(dWidth, dHeight);
        layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);

        interpolators = new Interpolator[4];
        interpolators[0] = line;
        interpolators[1] = acc;
        interpolators[2] = dec;
        interpolators[3] = accDec;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private PointF getPointF(int paramInt) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt(mWidth - 100);
        pointF.y = (random.nextInt(mHeight - 100) / paramInt);
        return pointF;
    }

    public void addHeart() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(drawables[random.nextInt(3)]);
        imageView.setLayoutParams(layoutParams);
        addView(imageView);
        Animator animator = getAnimator(imageView);
        animator.addListener(new AnimEndListener(imageView));
        animator.start();
    }

    private Animator getAnimator(View view) {
        AnimatorSet animatorSet1 = getEnterAnimator(view);
        ValueAnimator valueAnimator = getBezierValueAnimator(view);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playSequentially(animatorSet1);
        animatorSet2.playSequentially(animatorSet1, valueAnimator);
        animatorSet2.setInterpolator(interpolators[random.nextInt(4)]);
        animatorSet2.setTarget(view);
        return animatorSet2;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private ValueAnimator getBezierValueAnimator(View view) {
        ValueAnimator valueAnimator =
                ValueAnimator.ofObject(new ThirdBezierEvaluator(getPointF(2), getPointF(1)),
                        new PointF(((this.mWidth - this.dWidth) / 2), mHeight - dHeight),
                        new PointF(this.random.nextInt(getWidth()), 0.0F));
        valueAnimator.addUpdateListener(new BezierListener(view));
        valueAnimator.setTarget(view);
        valueAnimator.setDuration(3000);
        return valueAnimator;
    }

    private AnimatorSet getEnterAnimator(View view) {
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, View.ALPHA, 0.2f, 1.0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3);
        animatorSet.setTarget(view);
        return animatorSet;
    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            Demo1View.this.removeView(target);
        }
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        BezierListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            target.setAlpha(1.0F - animation.getAnimatedFraction());
        }
    }
}
