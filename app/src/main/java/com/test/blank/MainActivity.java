package com.test.blank;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Color;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView text = new TextView(this);
        text.setLayoutParams(new android.widget.FrameLayout.LayoutParams(
            android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
            android.widget.FrameLayout.LayoutParams.MATCH_PARENT));
        text.setBackgroundColor(Color.WHITE);
        text.setTextColor(Color.BLACK);
        text.setTextSize(20);
        text.setGravity(android.view.Gravity.CENTER);
        text.setText("✅ 安装成功！\n\n这是一个空白测试包\n\n如果能看到这个，说明 APK 兼容你的设备");
        
        setContentView(text);
    }
}
