package com.hyd.animationart.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hyd.animationart.R;
import com.hyd.animationart.views.Demo9View;

public class Demo9Activity extends AppCompatActivity {

    private Demo9View mSuperLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo9);

        mSuperLoadingProgress = findViewById(R.id.progress);

        findViewById(R.id.btn_failed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        if (executeAnimator()) return;
                        mSuperLoadingProgress.finishFail();
                    }
                }.start();
            }
        });

        findViewById(R.id.btn_success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        if (executeAnimator()) return;
                        mSuperLoadingProgress.finishSuccess();
                    }
                }.start();
            }
        });
    }

    private boolean executeAnimator() {
        try {
            mSuperLoadingProgress.setProgress(0);
            while (mSuperLoadingProgress.getProgress() < 100) {
                Thread.sleep(10);
                mSuperLoadingProgress.setProgress(mSuperLoadingProgress.getProgress() + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
