package com.soulfragments.app;

import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private WebView webView;
    private static final String TAG = "SoulFragments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        setupWebView();
        
        // 加载本地 www 文件夹中的 index.html
        String localUrl = "file:///android_asset/www/index.html";
        Log.d(TAG, "Loading URL: " + localUrl);
        webView.loadUrl(localUrl);
        
        Toast.makeText(this, "应用启动中...", Toast.LENGTH_SHORT).show();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        
        // 启用 JavaScript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        
        // 支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        
        // 缓存设置
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);
        
        // 允许文件访问
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        
        // 混合内容（HTTPS 加载 HTTP 资源）
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        
        // 用户代理（适配移动端）
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " SoulFragmentsApp/1.0");
        
        // 启用 WebView 调试（可用 Chrome 远程调试）
        WebView.setWebContentsDebuggingEnabled(true);
        
        // WebViewClient - 在 WebView 内打开链接
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "Page finished loading: " + url);
                Toast.makeText(MainActivity.this, "页面加载完成", Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "加载错误: " + errorCode + " - " + description + " - URL: " + failingUrl);
                Toast.makeText(MainActivity.this, "加载失败：" + description, Toast.LENGTH_LONG).show();
            }
            
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Log.d(TAG, "请求 URL: " + request.getUrl().toString());
                return super.shouldInterceptRequest(view, request);
            }
        });
        
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG, "JS Console: " + consoleMessage.message() + " -- From line " 
                    + consoleMessage.lineNumber() + " of " + consoleMessage.sourceId());
                return true;
            }
            
            @Override
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                Log.d(TAG, "JS Console: " + message + " -- From line " + lineNumber + " of " + sourceID);
            }
        });
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
