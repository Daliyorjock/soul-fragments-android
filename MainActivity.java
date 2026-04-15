package com.soulfragments.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends Activity {
    
    private static final String TAG = "SoulFragments";
    private Context appContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 最早能捕获的地方
        log("=== 应用启动 (attachBaseContext) ===");
        log("设备：" + Build.BRAND + " " + Build.MODEL);
        log("系统：Android " + Build.VERSION.RELEASE);
        log("SDK: " + Build.VERSION.SDK_INT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        log("进入 onCreate...");
        
        try {
            super.onCreate(savedInstanceState);
            log("✓ super.onCreate 完成");
            
            appContext = getApplicationContext();
            log("✓ 获取 ApplicationContext 成功");
            
            // 检查 WebView
            log("检查 WebView...");
            WebView webView = new WebView(appContext);
            log("✓ WebView 创建成功");
            webView.destroy();
            
            // 检查权限
            int check = checkSelfPermission("android.permission.INTERNET");
            log("网络权限：" + (check == 0 ? "已授予" : "未授予"));
            
            // 检查资源
            try {
                String[] assets = getAssets().list("www");
                log("资源文件：" + (assets != null ? assets.length : 0) + " 个");
                if (assets != null) {
                    for (String a : assets) log("  - " + a);
                }
            } catch (Exception e) {
                log("❌ 资源检查失败：" + e.getMessage());
            }
            
            // 延迟加载页面
            new Handler().postDelayed(() -> {
                try {
                    log("开始加载页面...");
                    WebView wv = new WebView(appContext);
                    wv.getSettings().setJavaScriptEnabled(true);
                    wv.getSettings().setDomStorageEnabled(true);
                    wv.getSettings().setAllowFileAccess(true);
                    setContentView(wv);
                    wv.loadUrl("file:///android_asset/www/index.html");
                    log("✅ 加载完成");
                } catch (Exception e) {
                    log("❌ 加载失败：" + e.getMessage());
                    showToast("错误：" + e.getMessage());
                }
            }, 1000);
            
        } catch (Exception e) {
            log("❌ onCreate 异常：" + e.getMessage());
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
        // 也显示 Toast（前 3 条）
        if (msg.contains("✓") || msg.contains("❌") || msg.contains("===")) {
            showToast(msg);
        }
    }
    
    private void showToast(String msg) {
        new Handler(Looper.getMainLooper()).post(() -> 
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        );
    }
    
    @Override
    protected void onDestroy() {
        log("应用关闭");
        super.onDestroy();
    }
}
