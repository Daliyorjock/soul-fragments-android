package com.soulfragments.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private static final String TAG = "SoulFragments";
    private Context appContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        log("=== 应用启动 ===");
        log("设备：" + Build.BRAND + " " + Build.MODEL);
        log("系统：Android " + Build.VERSION.RELEASE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("进入 onCreate...");
        
        try {
            super.onCreate(savedInstanceState);
            log("✓ super.onCreate 完成");
            
            appContext = getApplicationContext();
            log("✓ ApplicationContext 获取成功");
            
            // 荣耀设备延迟初始化（关键！）
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                try {
                    log("开始初始化 WebView...");
                    
                    WebView webView = new WebView(appContext);
                    log("✓ WebView 创建成功");
                    
                    // 禁用硬件加速（荣耀设备关键！）
                    webView.setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null);
                    log("✓ 软件渲染模式已启用");
                    
                    WebSettings settings = webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    settings.setDomStorageEnabled(true);
                    settings.setAllowFileAccess(true);
                    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
                    log("✓ WebView 配置完成");
                    
                    setContentView(webView);
                    log("✓ 页面已设置");
                    
                    webView.loadUrl("file:///android_asset/www/index.html");
                    log("✓ 开始加载页面");
                    
                } catch (Exception e) {
                    log("❌ WebView 初始化失败：" + e.getMessage());
                    showToast("错误：" + e.getMessage());
                    finish();
                }
            }, 2000); // 延迟 2 秒（荣耀设备需要更长延迟）
            
        } catch (Exception e) {
            log("❌ onCreate 失败：" + e.getMessage());
            showToast("启动失败：" + e.getMessage());
            finish();
        } catch (Error err) {
            log("❌ onCreate 错误：" + err.getMessage());
            showToast("启动错误：" + err.getMessage());
            finish();
        }
    }
    
    private void log(String msg) {
        Log.d(TAG, msg);
        if (msg.contains("✓") || msg.contains("❌") || msg.contains("===")) {
            showToast(msg);
        }
    }
    
    private void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(TAG, "Toast 失败：" + e.getMessage());
            }
        });
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }
    
    @Override
    protected void onDestroy() {
        log("应用关闭");
        super.onDestroy();
    }
}
