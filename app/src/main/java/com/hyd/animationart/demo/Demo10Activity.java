package com.hyd.animationart.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.hyd.animationart.R;
import com.hyd.animationart.views.Demo10View;

public class Demo10Activity extends AppCompatActivity {

    private Demo10View mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo10);

        mSearchView = findViewById(R.id.search_view);

        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.start();
            }
        });
    }
}
