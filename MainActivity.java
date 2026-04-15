package com.soulfragments.app;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private WebView webView;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            // 创建简单布局
            FrameLayout layout = new FrameLayout(this);
            setContentView(layout);
            
            // 状态文本
            statusText = new TextView(this);
            statusText.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
            statusText.setTextSize(16);
            statusText.setTextColor(Color.BLACK);
            statusText.setPadding(20, 40, 20, 20);
            statusText.setText("正在启动...");
            layout.addView(statusText);
            
            // 创建 WebView
            webView = new WebView(this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
            params.topMargin = 100;
            webView.setLayoutParams(params);
            layout.addView(webView);
            
            statusText.setText("✓ 应用启动成功");
            
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            
            // 加载简单测试页面
            String testHtml = "<html><body style='padding:20px;font-family:Arial;'><h1>✅ 测试成功</h1><p>WebView 工作正常</p></body></html>";
            webView.loadDataWithBaseURL(null, testHtml, "text/html", "UTF-8", null);
            
        } catch (Exception e) {
            if (statusText != null) {
                statusText.setText("❌ 错误：" + e.getMessage());
            }
            Toast.makeText(this, "错误：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
