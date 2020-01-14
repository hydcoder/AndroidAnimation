package com.hyd.animationart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.vector_demo).setOnClickListener(this);
        findViewById(R.id.bezier_demo).setOnClickListener(this);
        findViewById(R.id.path_measure_demo).setOnClickListener(this);
        findViewById(R.id.animation_demo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vector_demo:
                startActivity(new Intent(this, VectorDemoActivity.class));
                break;
            case R.id.bezier_demo:
                startActivity(new Intent(this, BezierDemoActivity.class));
                break;
            case R.id.path_measure_demo:
                startActivity(new Intent(this, PathMeasureDemoActivity.class));
                break;
            case R.id.animation_demo:
                startActivity(new Intent(this, TenAnimationDemoActivity.class));
                break;
        }
    }
}
