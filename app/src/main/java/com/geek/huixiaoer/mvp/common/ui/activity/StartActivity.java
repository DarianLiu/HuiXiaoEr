package com.geek.huixiaoer.mvp.common.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.geek.huixiaoer.R;

/**
 * Created by iRefly on 2018/4/12.
 */

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        },3000);
    }

    //    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.activity_start);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(StartActivity.this, MainActivity.class));
//                finish();
//            }
//        },2000);
//    }
}
