package com.hyd.animationart.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hyd.animationart.R;

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by hydCoder on 2020/3/4.
 * 以梦为马，明日天涯。
 */
public class Demo7View extends View {

    private Bitmap bg;

    private Bitmap bitmap;

    private float controlX;
    private float controlY;

    private int height;

    private boolean isIncrease;

    private boolean isRefresh = true;

    private Canvas mCanvas;

    private Paint paint;

    private Path path;

    private PorterDuffXfermode porterDuffXfermode;

    private float waveY;

    private int width;

    public Demo7View(Context context) {
        super(context);
        init();
    }

    public Demo7View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo7View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#ffc9394a"));

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        waveY = 0.875f * height;
        controlY = 1.0625f * height;
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        path = new Path();
        mCanvas = new Canvas();
        bg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(bg);
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTargetBitmap();
        canvas.drawBitmap(bg, getPaddingLeft(), getPaddingTop(), null);
        if (isRefresh) {
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int m = View.MeasureSpec.getMode(widthMeasureSpec);
        int j = View.MeasureSpec.getSize(widthMeasureSpec);
        int k = View.MeasureSpec.getMode(heightMeasureSpec);
        int i = View.MeasureSpec.getSize(heightMeasureSpec);
        if (m == EXACTLY) {
            widthMeasureSpec = j;
        } else {
            heightMeasureSpec = this.width + getPaddingLeft() + getPaddingRight();
            widthMeasureSpec = heightMeasureSpec;
            if (m == Integer.MIN_VALUE)
                widthMeasureSpec = Math.min(heightMeasureSpec, j);
        }
        if (k == EXACTLY) {
            heightMeasureSpec = i;
        } else {
            j = this.height + getPaddingTop() + getPaddingBottom();
            heightMeasureSpec = j;
            if (k == Integer.MIN_VALUE)
                heightMeasureSpec = Math.min(j, i);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawTargetBitmap() {
        float f;
        path.reset();
        bg.eraseColor(Color.parseColor("#00ffffff"));
        if (controlX >= width) {
            isIncrease = false;
        } else if (controlX < 0) {
            isIncrease = true;
        }
        if (isIncrease) {
            f = controlX + 10.0F;
        } else {
            f = controlX - 10.0F;
        }
        controlX = f;
        if (controlY >= 0.0F) {
            controlY--;
            waveY--;
        } else {
            waveY = 0.875F * height;
            controlY = 1.0625F * height;
        }
        path.moveTo(0.0F, waveY);
        path.cubicTo(controlX / 2.0F, waveY - controlY - waveY, (controlX + width) / 2.0F,
                controlY, width, waveY);
        path.lineTo(width, height);
        path.lineTo(0.0F, height);
        path.close();
        mCanvas.drawBitmap(bitmap, 0.0F, 0.0F, paint);
        paint.setXfermode(porterDuffXfermode);
        mCanvas.drawPath(path, paint);
        paint.setXfermode(null);
    }
}
