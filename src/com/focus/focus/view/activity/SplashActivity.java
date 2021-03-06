package com.focus.focus.view.activity;

import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.focus.R;
import com.focus.base.AppController;
import com.focus.base.view.BaseActivity;

public class SplashActivity extends BaseActivity {
    private static final int DELAY = 2;

    @Override
    protected void onStart() {
        super.onStart();
        AppController.getInstance().getScheduleExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }, DELAY, TimeUnit.SECONDS);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
    }
}
