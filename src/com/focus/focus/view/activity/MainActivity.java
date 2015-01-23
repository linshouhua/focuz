package com.focus.focus.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.focus.R;
import com.focus.base.view.BaseActivity;
import com.focus.focus.view.fragment.FocusInfoFragment;
import com.focus.focus.view.fragment.SetFragment;

public class MainActivity extends BaseActivity implements OnTabChangeListener {
    private FragmentTabHost mTabHost;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_tab_id_content);
        mTabHost.addTab(mTabHost.newTabSpec("首页").setIndicator(newTabIndicator("首页", R.drawable.main_tab_home_bg)), FocusInfoFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("设置").setIndicator(newTabIndicator("设置", R.drawable.main_tab_set_bg)), SetFragment.class, null);
    }

    @SuppressLint("InflateParams")
    private View newTabIndicator(String resTxt, int ico) {
        View indicator = LayoutInflater.from(this).inflate(R.layout.fragment_indicator, null);
        TextView tvName = (TextView) indicator.findViewById(R.id.main_tab_id_text);
        ImageView imageView = (ImageView) indicator.findViewById(R.id.main_tab_id_icon);
        imageView.setBackgroundResource(ico);
        tvName.setText(resTxt);
        return indicator;
    }

    @Override
    public void onTabChanged(String arg0) {
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }
}
