package com.hyd.animationart.demo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hyd.animationart.R;
import com.hyd.animationart.views.Demo1View;

public class Demo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        findViewById(R.id.demo1View).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Demo1View) v).addHeart();
            }
        });
    }
}
