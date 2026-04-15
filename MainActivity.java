package com.soulfragments.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    
    private WebView webView;
    private TextView statusText;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 必须在 super.onCreate 之前设置某些窗口特性
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        mainHandler = new Handler(Looper.getMainLooper());
        
        try {
            // 创建简单布局
            FrameLayout layout = new FrameLayout(this);
            setContentView(layout);
            
            // 状态文本
            statusText = new TextView(this);
            statusText.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
            statusText.setTextSize(18);
            statusText.setTextColor(Color.BLACK);
            statusText.setPadding(30, 60, 30, 30);
            statusText.setText("🚀 应用启动中...");
            layout.addView(statusText);
            
            // 创建 WebView
            webView = new WebView(this.getApplicationContext()); // 使用 Application Context
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
            params.topMargin = 120;
            webView.setLayoutParams(params);
            layout.addView(webView);
            
            statusText.setText("✓ WebView 创建成功");
            
            // 延迟初始化 WebView 设置
            mainHandler.postDelayed(() -> {
                try {
                    WebSettings settings = webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setDomStorageEnabled(true);
                    settings.setAllowFileAccess(true);
                    
                    statusText.setText("✓ JavaScript 已启用");
                    
                    // 设置 WebViewClient
                    webView.setWebViewClient(new WebViewClient());
                    
                    // 加载测试页面
                    String testHtml = "<html><body style='padding:30px;font-family:Arial,sans-serif;'>" +
                        "<h1 style='color:#28a745;'>✅ 测试成功！</h1>" +
                        "<p style='font-size:18px;'>WebView 工作正常</p>" +
                        "<p style='color:#666;'>设备信息：<span id='info'></span></p>" +
                        "<script>document.getElementById('info').innerText=navigator.userAgent;</script>" +
                        "</body></html>";
                    
                    webView.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null);
                    statusText.setText("✅ 应用正常运行");
                    
                    Toast.makeText(MainActivity.this, "欢迎使用心灵碎片！", Toast.LENGTH_LONG).show();
                    
                } catch (Exception e) {
                    handleError("配置错误", e);
                }
            }, 500);
            
        } catch (Exception e) {
            handleError("启动错误", e);
        }
    }
    
    private void handleError(String title, Exception e) {
        String errorMsg = "❌ " + title + "\n\n" + e.getMessage();
        if (statusText != null) {
            statusText.setText(errorMsg);
        }
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
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
            webView = null;
        }
        super.onDestroy();
    }
}
