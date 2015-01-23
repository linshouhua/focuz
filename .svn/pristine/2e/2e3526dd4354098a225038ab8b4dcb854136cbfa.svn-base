package com.focus.focus.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.focus.R;
import com.focus.base.view.BaseActivity;
import com.focus.focus.KEY;

public class WebActivity extends BaseActivity implements OnClickListener {
    private String mTitle;
    private String mUrl;
    private WebView mWebView;
    private ImageView mImageView;
    private TextView mTextView;

    private class CommonWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            super.onProgressChanged(view, progress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

    private class CommonWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web;
    }

    @Override
    protected View getHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_head_common, null);
        mImageView = (ImageView) view.findViewById(R.id.headLeft);
        mTextView = (TextView) view.findViewById(R.id.headTitle);
        mImageView.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        if (null != getIntent()) {
            mUrl = getIntent().getStringExtra(KEY.URL);
            mTitle = getIntent().getStringExtra(KEY.TITLE);
        }
        mWebView = (WebView) findViewById(R.id.web_id_webview);
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new CommonWebViewClient());
        mWebView.setWebChromeClient(new CommonWebChromeClient());
        mWebView.loadUrl(mUrl);
        mTextView.setText(mTitle);
    }

    @Override
    public void onClick(View arg0) {
        finish();
    }
}
