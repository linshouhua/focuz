package com.focus.focus.view.activity;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.focus.R;
import com.focus.base.view.BaseActivity;
import com.lidroid.xutils.util.LogUtils;

public class AboutUsActivity extends BaseActivity implements OnClickListener {
    private ImageView mLeftView;
    private TextView mTitleView;
    private TextView mContentView;

    @Override
    protected View getHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_head_common, null);
        mLeftView = (ImageView) view.findViewById(R.id.headLeft);
        mTitleView = (TextView) view.findViewById(R.id.headTitle);
        mLeftView.setOnClickListener(this);
        mTitleView.setText("关于我们");
        return view;
    }
    
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_aboutus;
    }

    @Override
    public void onClick(View arg0) {
        finish();
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        mContentView = (TextView) findViewById(R.id.aboutUs);
        try {
            mContentView.setText(IOUtils.toString(getResources().getAssets().open("aboutus.txt")));
        } catch (IOException e) {
            LogUtils.e(e.getMessage(),e);
        }
    }

}
