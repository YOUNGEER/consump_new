package com.youyou.xiaofeibao.version2.home;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 作者：young on 2016/9/6 16:36
 */
public class InfoWebView extends BaseTitleActivity {
    @ViewInject(R.id.webview)
    WebView webView;
    @ViewInject(R.id.myProgressBar)
    ProgressBar mProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.v2_activity_mainbanner;
    }

    @Override
    protected int getTitleText() {
        return R.string.title_activity_webview;
    }

    @Override
    protected void initData() {
        super.initData();

        setTitle(getIntent().getStringExtra("name"));

        WebSettings settings = webView.getSettings();

        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAllowFileAccess(true);

        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        settings.setDefaultTextEncodingName("utf-8");
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        String url = getIntent().getAction();
        webView.loadUrl(url);

    }
}
