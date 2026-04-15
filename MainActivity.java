package com.soulfragments.app;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.util.Log;
import java.io.File;

public class MainActivity extends Activity {
    
    private TextView statusText;
    private Handler mainHandler;
    private StringBuilder log = new StringBuilder();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(Looper.getMainLooper());
        
        // 创建诊断界面
        createDiagnosticUI();
        
        // 逐步诊断
        new Handler().postDelayed(this::step1_checkSystem, 500);
    }
    
    private void createDiagnosticUI() {
        FrameLayout layout = new FrameLayout(this);
        layout.setBackgroundColor(Color.parseColor("#1a1a2e"));
        
        statusText = new TextView(this);
        statusText.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));
        statusText.setTextColor(Color.parseColor("#00ff00"));
        statusText.setTextSize(14);
        statusText.setPadding(20, 30, 20, 30);
        statusText.setText("🔍 开始诊断...\n\n设备信息收集中...\n");
        
        layout.addView(statusText);
        setContentView(layout);
        
        appendLog("=== 应用启动 ===");
        appendLog("设备品牌：" + Build.BRAND);
        appendLog("设备型号：" + Build.MODEL);
        appendLog("系统版本：Android " + Build.VERSION.RELEASE);
        appendLog("制造商：" + Build.MANUFACTURER);
        appendLog("");
    }
    
    private void step1_checkSystem() {
        appendLog("✓ 步骤 1/5: 系统信息收集完成");
        
        // 检查 WebView
        try {
            WebView testWebView = new WebView(this.getApplicationContext());
            appendLog("✓ 步骤 2/5: WebView 组件可用");
            testWebView.destroy();
            
            new Handler().postDelayed(this::step3_checkPermissions, 500);
        } catch (Exception e) {
            appendLog("❌ 步骤 2/5: WebView 初始化失败");
            appendLog("   错误：" + e.getMessage());
            appendLog("");
            appendLog("=== 诊断完成 ===");
            appendLog("结论：WebView 组件不可用，可能是系统限制");
            finish();
            return;
        }
    }
    
    private void step3_checkPermissions() {
        boolean hasInternet = checkSelfPermission("android.permission.INTERNET") == PackageManager.PERMISSION_GRANTED;
        appendLog((hasInternet ? "✓" : "❌") + " 步骤 3/5: 网络权限：" + (hasInternet ? "已授予" : "未授予"));
        
        new Handler().postDelayed(this::step4_checkAssets, 500);
    }
    
    private void step4_checkAssets() {
        try {
            String[] assets = getAssets().list("www");
            boolean hasIndex = assets != null && assets.length > 0;
            appendLog((hasIndex ? "✓" : "❌") + " 步骤 4/5: 资源文件：" + (hasIndex ? "存在 (" + assets.length + " 个文件)" : "不存在"));
            
            if (assets != null) {
                for (String asset : assets) {
                    appendLog("   - " + asset);
                }
            }
            
            new Handler().postDelayed(this::step5_loadPage, 500);
        } catch (Exception e) {
            appendLog("❌ 步骤 4/5: 资源文件检查失败：" + e.getMessage());
            appendLog("");
            appendLog("=== 诊断完成 ===");
            finish();
        }
    }
    
    private void step5_loadPage() {
        appendLog("✓ 步骤 5/5: 开始加载页面...");
        appendLog("");
        
        try {
            WebView webView = new WebView(this.getApplicationContext());
            setContentView(webView);
            
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setAllowFileAccess(true);
            
            appendLog("✅ 诊断完成！");
            appendLog("所有检查通过，正在加载页面...");
            
            webView.loadUrl("file:///android_asset/www/index.html");
            
        } catch (Exception e) {
            appendLog("❌ 加载页面失败：" + e.getMessage());
            appendLog("");
            appendLog("=== 诊断完成 ===");
        }
    }
    
    private void appendLog(String message) {
        log.append(message).append("\n");
        mainHandler.post(() -> statusText.setText(log.toString()));
        Log.d("SoulFragments", message);
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }
}
