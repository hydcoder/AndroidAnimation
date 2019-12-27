package com.hyd.animationart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PathMeasureDemoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure_demo);

        findViewById(R.id.path_tracing).setOnClickListener(this);
        findViewById(R.id.path_paint).setOnClickListener(this);
        findViewById(R.id.path_pos_tan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.path_tracing:
                startActivity(new Intent(this, PathTracingActivity.class));
                break;
            case R.id.path_paint:
                startActivity(new Intent(this, PathPaintActivity.class));
                break;
            case R.id.path_pos_tan:
                startActivity(new Intent(this, PathPosTanActivity.class));
                break;
        }
    }
}
