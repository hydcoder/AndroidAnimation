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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.vector_demo) {
            startActivity(new Intent(this, VectorDemoActivity.class));
        } else if (v.getId() == R.id.second_bezier) {
            startActivity(new Intent(this, SecondBezierActivity.class));
        } else if (v.getId() == R.id.third_bezier) {
            startActivity(new Intent(this, ThirdBezierActivity.class));
        }
    }
}
