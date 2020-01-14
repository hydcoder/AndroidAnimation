package com.hyd.animationart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hyd.animationart.demo.Demo1Activity;
import com.hyd.animationart.demo.Demo2Activity;
import com.hyd.animationart.demo.Demo3Activity;

public class TenAnimationDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_animation_demo);
    }

    public void demo1(View view) {
        startActivity(new Intent(this, Demo1Activity.class));
    }

    public void demo2(View view) {
        startActivity(new Intent(this, Demo2Activity.class));
    }

    public void demo3(View view) {
        startActivity(new Intent(this, Demo3Activity.class));
    }

    public void demo4(View view) {
    }

    public void demo5(View view) {
    }

    public void demo6(View view) {
    }

    public void demo7(View view) {
    }

    public void demo8(View view) {
    }

    public void demo9(View view) {
    }

    public void demo10(View view) {
    }
}
