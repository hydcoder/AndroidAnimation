package com.hyd.animationart.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.hyd.animationart.R;
import com.hyd.animationart.views.Demo2View;

public class Demo2Activity extends AppCompatActivity {
    private int[] colors = new int[] { Color.parseColor("#ff0000"),
            Color.parseColor("#00ff00"), Color.parseColor("#0000ff"),
            Color.parseColor("#ffff00") };

    private Demo2View loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

        loadingView = findViewById(R.id.loadingView);
    }

    public void changeColor(View view) {
        int i = (int) (Math.random() * 4.0);
        loadingView.setColor(colors[i]);
    }
}
