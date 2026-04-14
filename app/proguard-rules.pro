# WebView 保留规则
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# 保留 JavaScript 接口
-keepattributes JavascriptInterface
-keepattributes *Annotation*

# 保留 WebView 相关
-keepclassmembers class * extends android.webkit.WebView {
    public *;
}
