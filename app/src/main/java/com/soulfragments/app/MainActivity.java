package com.soulfragments.app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private WebView webView;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 创建简单布局
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        
        // 状态文本
        statusText = new TextView(this);
        statusText.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT));
        statusText.setTextSize(16);
        statusText.setPadding(20, 40, 20, 20);
        statusText.setText("应用启动中...");
        layout.addView(statusText);
        
        // 创建 WebView
        webView = new WebView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT);
        params.topMargin = 100;
        webView.setLayoutParams(params);
        layout.addView(webView);
        
        statusText.setText("1. WebView 创建成功");
        
        try {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            statusText.setText("2. JavaScript 已启用");
            
            // 加载简单测试页面
            String testHtml = "<html><body><h1>测试页面</h1><p>如果能看到这个，说明 WebView 正常</p></body></html>";
            webView.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null);
            statusText.setText("3. 测试页面已加载");
            
            // 3 秒后加载实际页面
            new android.os.Handler().postDelayed(() -> {
                try {
                    String localUrl = "file:///android_asset/www/index.html";
                    webView.loadUrl(localUrl);
                    statusText.setText("4. 正在加载 index.html...");
                } catch (Exception e) {
                    statusText.setText("❌ 加载失败：" + e.getMessage());
                }
            }, 3000);
            
        } catch (Exception e) {
            statusText.setText("❌ 错误：" + e.getMessage());
            Toast.makeText(this, "错误：" + e.getMessage(), Toast.LENGTH_LONG).show();
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
}
