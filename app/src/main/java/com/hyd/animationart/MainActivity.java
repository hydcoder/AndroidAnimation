package com.hyd.animationart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.vector_demo).setOnClickListener(this);
        findViewById(R.id.second_bezier).setOnClickListener(this);
        findViewById(R.id.third_bezier).setOnClickListener(this);
        findViewById(R.id.draw_pad).setOnClickListener(this);
        findViewById(R.id.path_morphing).setOnClickListener(this);
        findViewById(R.id.bezier_wave).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vector_demo:
                startActivity(new Intent(this, VectorDemoActivity.class));
                break;
            case R.id.second_bezier:
                startActivity(new Intent(this, SecondBezierActivity.class));
                break;
            case R.id.third_bezier:
                startActivity(new Intent(this, ThirdBezierActivity.class));
                break;
            case R.id.draw_pad:
                startActivity(new Intent(this, DrawPadActivity.class));
                break;
            case R.id.path_morphing:
                startActivity(new Intent(this, PathMorphingActivity.class));
                break;
            case R.id.bezier_wave:
                startActivity(new Intent(this, WaveActivity.class));
                break;
        }
    }
}
