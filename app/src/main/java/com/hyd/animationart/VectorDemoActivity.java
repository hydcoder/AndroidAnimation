package com.hyd.animationart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class VectorDemoActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_demo);

        ImageView arrow = findViewById(R.id.iv_arrow);
        final ImageView square = findViewById(R.id.iv_square);
        final ImageView searchBar = findViewById(R.id.iv_search_bar);
        final ImageView star = findViewById(R.id.iv_star);
        final ImageView fiveStar = findViewById(R.id.iv_five_star);
        init(arrow);
        square.postDelayed(new Runnable() {
            @Override
            public void run() {
                init(square);
            }
        }, 1000);

        searchBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                init(searchBar);
            }
        }, 1000);

        star.postDelayed(new Runnable() {
            @Override
            public void run() {
                init(star);
            }
        }, 1000);
        fiveStar.postDelayed(new Runnable() {
            @Override
            public void run() {
                init(fiveStar);
            }
        }, 1000);
    }

    private void init(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
    }
}

