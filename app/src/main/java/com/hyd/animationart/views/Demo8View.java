package com.hyd.animationart.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyd.animationart.R;

/**
 * Created by hydCoder on 2020/3/5.
 * 以梦为马，明日天涯。
 */
public class Demo8View extends FrameLayout implements View.OnClickListener {

    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR;

    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR;

    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR;

    static {
        ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
        DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
        OVERSHOOT_INTERPOLATOR = new OvershootInterpolator();
    }

    private AnimatorSet animatorSet;

    private boolean isChecked;

    private ImageView mIvStar;

    private CircleView mCircleView;

    private DotsView mDotsView;

    public Demo8View(@NonNull Context context) {
        super(context);
        init();
    }

    public Demo8View(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo8View(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.demo8_view, this, true);
        mIvStar = view.findViewById(R.id.ivStar);
        mDotsView = view.findViewById(R.id.dotsView);
        mCircleView = view.findViewById(R.id.circleView);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i;
        isChecked = !isChecked;
        if (isChecked) {
            i = R.drawable.ic_star_rate_on;
        } else {
            i = R.drawable.ic_star_rate_off;
        }
        mIvStar.setImageResource(i);
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (isChecked) {
            mIvStar.animate().cancel();
            mIvStar.setScaleX(0f);
            mIvStar.setScaleY(0f);
            mCircleView.setInnerCircleRadiusProgress(0f);
            mCircleView.setOuterCircleRadiusProgress(0f);
            mDotsView.setCurrentProgress(0f);

            animatorSet = new AnimatorSet();
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mCircleView,
                    CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1F, 1.0F);
            objectAnimator1.setDuration(250L);
            objectAnimator1.setInterpolator(DECELERATE_INTERPOLATOR);

            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mCircleView,
                    CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1F, 1.0F);
            objectAnimator2.setDuration(200L);
            objectAnimator2.setStartDelay(200L);
            objectAnimator2.setInterpolator(DECELERATE_INTERPOLATOR);

            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_Y,
                    0.2F, 1.0F);
            objectAnimator3.setDuration(350L);
            objectAnimator3.setStartDelay(250L);
            objectAnimator3.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_X,
                    0.2F, 1.0F);
            objectAnimator4.setDuration(350L);
            objectAnimator4.setStartDelay(250L);
            objectAnimator4.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mDotsView,
                    DotsView.DOTS_PROGRESS, 0.0F, 1.0F);
            objectAnimator5.setDuration(900L);
            objectAnimator5.setStartDelay(50L);
            objectAnimator5.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            animatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3,
                    objectAnimator4, objectAnimator5);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator param1Animator) {
                    mCircleView.setInnerCircleRadiusProgress(0.0F);
                    mCircleView.setOuterCircleRadiusProgress(0.0F);
                    mDotsView.setCurrentProgress(0.0F);
                    mIvStar.setScaleX(1.0F);
                    mIvStar.setScaleY(1.0F);
                }
            });
            animatorSet.start();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean bool1;
        float f2;
        float f1;
        switch (event.getAction()) {
            default:
                return true;
            case 0:
                this.mIvStar.animate().scaleX(0.7F).scaleY(0.7F).setDuration(150L).setInterpolator(DECELERATE_INTERPOLATOR);
                setPressed(true);
                return true;
            case 2:
                f1 = event.getX();
                f2 = event.getY();
                bool1 = false;
                if (f1 > 0.0F) {
                    if (f1 < getWidth()) {
                        if (f2 > 0.0F) {
                            if (f2 < getHeight()) bool1 = true;
                        }
                    }
                }
                if (isPressed() != bool1) {
                    setPressed(bool1);
                    return true;
                }
            case 1:
                break;
        }
        this.mIvStar.animate().scaleX(1.0F).scaleY(1.0F).setInterpolator(DECELERATE_INTERPOLATOR);
        if (isPressed()) {
            performClick();
            setPressed(false);
        }
        return true;
    }
}
