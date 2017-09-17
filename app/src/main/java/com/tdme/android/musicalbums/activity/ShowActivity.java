package com.tdme.android.musicalbums.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tdme.android.musicalbums.R;

public class ShowActivity extends AppCompatActivity {
    private static final String TAG = "ShowActivity";
    private WebView webView;
    private Button loadBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initView();

        initWebView();
    }

    public void initView() {
        webView = (WebView) findViewById(R.id.show_webview);
        loadBtn = (Button) findViewById(R.id.reload);
    }

    public void initWebView() {
        WebChromeClient wvcc = new WebChromeClient();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebChromeClient(wvcc);
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        };
        webView.setWebViewClient(wvc);


        // 加载Web地址

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println(TAG + id);
        String playmode = intent.getStringExtra("playmode");
        System.out.println(TAG + playmode);
        webView.loadUrl("http://alumt279.leanapp.cn/?albumId=" + id + "&modelId=0" + playmode);
    }
}
