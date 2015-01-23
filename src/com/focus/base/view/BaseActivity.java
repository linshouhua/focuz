package com.focus.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.focus.R;

public abstract class BaseActivity extends FragmentActivity {
    private ViewGroup mHeaderContainer;
    private ViewGroup mConent;
    private View mHeader;

    abstract protected int getLayoutResID();

    abstract protected void initView(@Nullable Bundle savedInstanceState);

    protected View getHeader() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.layout_base);
        mConent = (ViewGroup) findViewById(R.id.base_id_content);
        mHeaderContainer = (ViewGroup) findViewById(R.id.base_header_container);
        mHeader = getHeader();
        if (mHeader != null) {
            mHeader.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            mHeaderContainer.addView(mHeader);
        }
        int layoutResID = getLayoutResID();
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        mConent.addView(view);
        initView(savedInstanceState);
    }
}
