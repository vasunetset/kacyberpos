package com.kacyber.pos.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kacyber.pos.R;
import com.kacyber.pos.ui.base.BaseActivity;


public class WebViewActivity extends BaseActivity {

    String url;
    private String urlType;
    private String title;
    private final String googleDocs = "https://docs.google.com/viewer?url=";
    private WebView webview;

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        url = getIntent().getStringExtra("url");
        urlType = getIntent().getStringExtra("urltype");
        title = getIntent().getStringExtra("title");
        //setupActionBar();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               // startProgressDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               // stopProgressDialog();
            }
        });

        if (urlType.equals("pdf")) {
            webview.getSettings().setAllowFileAccess(true);
            webview.getSettings().setLoadsImagesAutomatically(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.getSettings().setTextZoom(10);
            webview.loadUrl(googleDocs + url);
        } else {
            webview.loadUrl(url);
        }
        setTitle(title);
    }

    /*private void setupActionBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_view;

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTitleView.setText(title);
     //   setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
