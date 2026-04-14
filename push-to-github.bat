@echo off
echo ====================================
echo 上传 Soul Fragments 到 GitHub
echo ====================================
echo.

cd /d D:\openclaw\soul-fragments-android

echo 正在推送到 GitHub...
git remote add origin https://github.com/Daliyorjock/soul-fragments-android.git
git push -u origin main

echo.
if %ERRORLEVEL% EQU 0 (
    echo ✅ 推送成功！
    echo.
    echo 接下来：
    echo 1. 访问 https://github.com/Daliyorjock/soul-fragments-android/actions
    echo 2. 等待 Build APK 任务完成（约 5-10 分钟）
    echo 3. 下载 app-debug.apk
) else (
    echo ❌ 推送失败，请检查网络连接
    echo.
    echo 如果使用了代理，请先设置：
    echo git config --global http.proxy http://你的代理地址
)

pause
