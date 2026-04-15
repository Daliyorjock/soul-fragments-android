package com.soulfragments.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    
    private WebView webView;
    private TextView errorTextView;
    private static final String TAG = "SoulFragments";
    private StringBuilder errorLog = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 创建主布局
        FrameLayout mainLayout = new FrameLayout(this);
        setContentView(mainLayout);
        
        // 创建 WebView
        webView = new WebView(this);
        webView.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));
        mainLayout.addView(webView);
        
        // 创建错误显示层
        createErrorOverlay(mainLayout);
        
        setupWebView();
        
        String localUrl = "file:///android_asset/www/index.html";
        logError("启动应用，加载 URL: " + localUrl);
        webView.loadUrl(localUrl);
        
        Toast.makeText(this, "应用启动中...", Toast.LENGTH_SHORT).show();
    }
    
    private void createErrorOverlay(FrameLayout mainLayout) {
        // 创建半透明错误显示层
        errorTextView = new TextView(this);
        errorTextView.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));
        errorTextView.setBackgroundColor(0xCC000000); // 半透明黑色
        errorTextView.setTextColor(0xFF00FF00); // 绿色文字
        errorTextView.setTextSize(12);
        errorTextView.setPadding(20, 20, 20, 20);
        errorTextView.setVisibility(View.GONE); // 初始隐藏
        errorTextView.setClickable(true);
        
        // 点击切换显示/隐藏
        errorTextView.setOnClickListener(v -> {
            if (errorTextView.getVisibility() == View.VISIBLE) {
                errorTextView.setVisibility(View.GONE);
            } else {
                errorTextView.setVisibility(View.VISIBLE);
            }
        });
        
        mainLayout.addView(errorTextView);
        
        logError("=== 应用启动 ===");
        logError("时间：" + getCurrentTime());
        logError("点击屏幕显示/隐藏日志");
    }
    
    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " SoulFragmentsApp/1.0");
        
        WebView.setWebContentsDebuggingEnabled(true);
        
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Page finished loading: " + url);
                logError("✅ 页面加载完成：" + url);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "页面加载完成", Toast.LENGTH_SHORT).show());
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String errorMsg = "❌ 加载错误：" + errorCode + " - " + description;
                Log.e(TAG, errorMsg + " - URL: " + failingUrl);
                logError(errorMsg);
                logError("URL: " + failingUrl);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "加载失败：" + description, Toast.LENGTH_LONG).show());
            }
            
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.contains("http")) {
                    logError("🌐 网络请求：" + url);
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
        
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                String msg = "📝 JS: " + consoleMessage.message();
                Log.d(TAG, msg + " -- 第" + consoleMessage.lineNumber() + "行");
                logError(msg);
                return true;
            }
            
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                String msg = "📝 JS: " + message + " (第" + lineNumber + "行)";
                Log.d(TAG, msg);
                logError(msg);
            }
            
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                logError("🎬 全屏视频请求");
                super.onShowCustomView(view, callback);
            }
        });
    }
    
    private void logError(String message) {
        String timestamp = getCurrentTime();
        errorLog.append("[").append(timestamp).append("] ").append(message).append("\n");
        
        // 保持最新 500 行
        if (errorLog.length() > 50000) {
            errorLog = new StringBuilder(errorLog.substring(errorLog.length() - 40000));
        }
        
        // 更新 UI
        runOnUiThread(() -> {
            if (errorTextView != null) {
                errorTextView.setText(errorLog.toString());
                
                // 自动滚动到底部
                ScrollView scrollView = new ScrollView(MainActivity.this);
                scrollView.addView(errorTextView);
            }
        });
        
        // 也输出到 Logcat
        Log.d(TAG, message);
    }
    
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return sdf.format(new Date());
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
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
