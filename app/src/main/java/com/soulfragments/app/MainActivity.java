package com.soulfragments.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebChromeClient;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {
    
    private WebView webView;
    private static final String TAG = "SoulFragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 荣耀 MagicOS 适配：延迟初始化
        getWindow().getDecorView().postDelayed(this::initWebView, 100);
    }
    
    private void initWebView() {
        try {
            webView = new WebView(this.getApplicationContext());
            setContentView(webView);
            
            WebSettings settings = webView.getSettings();
            
            // 荣耀 MagicOS 特殊配置
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setAllowContentAccess(true);
            
            // 适配折叠屏
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            
            // 缓存配置
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            
            // 荣耀设备特殊处理
            if (Build.MANUFACTURER.toLowerCase().contains("honor")) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                Log.d(TAG, "荣耀设备检测到，已启用混合内容模式");
            }
            
            // 设置 WebViewClient
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    Log.d(TAG, "页面加载完成：" + url);
                    runOnUiThread(() -> 
                        Toast.makeText(MainActivity.this, "加载完成", Toast.LENGTH_SHORT).show()
                    );
                }
                
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Log.e(TAG, "错误：" + errorCode + " - " + description);
                    runOnUiThread(() -> 
                        Toast.makeText(MainActivity.this, "错误：" + description, Toast.LENGTH_LONG).show()
                    );
                }
            });
            
            // 加载本地页面
            webView.loadUrl("file:///android_asset/www/index.html");
            
        } catch (Exception e) {
            Log.e(TAG, "初始化失败：" + e.getMessage());
            Toast.makeText(this, "初始化失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
