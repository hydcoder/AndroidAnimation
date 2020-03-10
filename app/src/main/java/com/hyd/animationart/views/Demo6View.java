package com.hyd.animationart.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hyd.animationart.R;
import com.hyd.animationart.other.JumpingSpan;

import java.util.regex.Matcher;

import static android.text.Spanned.SPAN_PARAGRAPH;
import static android.text.Spanned.SPAN_POINT_MARK;

/**
 * Created by hydCoder on 2020/3/4.
 * 以梦为马，明日天涯。
 */
@SuppressLint("AppCompatCustomView")
public class Demo6View extends TextView {

    private boolean autoPlay;

    private JumpingSpan dotOne;
    private JumpingSpan dotTwo;
    private JumpingSpan dotThree;

    private Handler handler;

    private boolean isHide;

    private boolean isPlaying;

    private int jumpHeight;

    private boolean lockDotOne;
    private boolean lockDotTwo;
    private boolean lockDotThree;

    private AnimatorSet mAnimatorSet = new AnimatorSet();

    private int period;

    private int showSpeed = 700;

    private long startTime;

    private float textWidth;

    public Demo6View(Context context) {
        super(context);
        init(context, null);
    }

    public Demo6View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Demo6View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attrs) {
        handler = new Handler(Looper.getMainLooper());
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaitingDots);
            period = typedArray.getInt(1, 1000);
            jumpHeight = typedArray.getInt(2, (int) (getTextSize() / 4.0f));
            autoPlay = typedArray.getBoolean(3, true);
            typedArray.recycle();
        }
        dotOne = new JumpingSpan();
        dotTwo = new JumpingSpan();
        dotThree = new JumpingSpan();
        SpannableString spannableString = new SpannableString("...");
        spannableString.setSpan(dotOne, 0, 1, SPAN_POINT_MARK);
        spannableString.setSpan(dotTwo, 1, 2, SPAN_POINT_MARK);
        spannableString.setSpan(dotThree, 2, 3, SPAN_POINT_MARK);
        setText(spannableString, BufferType.SPANNABLE);
        textWidth = getPaint().measureText(".", 0, 1);
        ObjectAnimator objectAnimator = createDotJumpAnimator(dotOne, 0L);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        mAnimatorSet.playTogether(objectAnimator, createDotJumpAnimator(dotTwo, period / 6), createDotJumpAnimator(dotThree, period * 2 / 6));
        isPlaying = autoPlay;
        if (autoPlay) {
            start();
        }
    }

    private void start() {
        isPlaying = true;
        setAllAnimationsRepeatCount(ValueAnimator.INFINITE);
        mAnimatorSet.start();
    }

    private void stop() {
        isPlaying = false;
        setAllAnimationsRepeatCount(0);
    }

    private void setAllAnimationsRepeatCount(int repeatCount) {
        for (Animator animator : mAnimatorSet.getChildAnimations()) {
            if (animator instanceof ObjectAnimator) {
                ((ObjectAnimator) animator).setRepeatCount(repeatCount);
            }
        }
    }

    private ObjectAnimator createDotJumpAnimator(JumpingSpan jumpingSpan, long startDelay) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(jumpingSpan, "translationY", 0f, -jumpHeight);
        objectAnimator.setEvaluator(new TypeEvaluator<Number>() {
            @Override
            public Number evaluate(float fraction, Number startValue, Number endValue) {
                return Math.max(0.0d, Math.sin(fraction * Math.PI * 2.0d)) * (endValue.floatValue() - startValue.floatValue());
            }
        });
        objectAnimator.setDuration(period);
        objectAnimator.setStartDelay(startDelay);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        return objectAnimator;
    }

    public boolean isHide() {
        return isHide;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void show() {
        createDotShowAnimator(dotThree, 2).start();
        ObjectAnimator objectAnimator = createDotShowAnimator(dotTwo, 1);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        objectAnimator.start();
        isHide = false;
    }

    public void showAndPlay() {
        show();
        start();
    }

    public void hideAndStop() {
        hide();
        stop();
    }

    public void hide() {
        createDotHideAnimator(dotThree, 2.0f).start();
        ObjectAnimator objectAnimator = createDotHideAnimator(dotTwo, 1);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
        objectAnimator.start();
        isHide = true;
    }

    private ObjectAnimator createDotShowAnimator(JumpingSpan jumpingSpan, int value) {
        return createDotHorizontalAnimator(jumpingSpan, -textWidth * value, 0.0f);
    }

    private ObjectAnimator createDotHorizontalAnimator(JumpingSpan jumpingSpan, float value1, float value2) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(jumpingSpan, "translationX", value1, value2);
        objectAnimator.setDuration(showSpeed);
        return objectAnimator;
    }

    private ObjectAnimator createDotHideAnimator(JumpingSpan jumpingSpan, float value) {
        return createDotHorizontalAnimator(jumpingSpan, 0.0f, -textWidth * value);
    }
}

