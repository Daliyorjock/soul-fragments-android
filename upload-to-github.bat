@echo off
chcp 65001 >nul
echo ====================================
echo GitHub 上传脚本
echo ====================================
echo.

cd /d D:\openclaw\soul-fragments-android

echo 正在配置 Git...
git config --global user.email "user@example.com"
git config --global user.name "User"
git config --global http.sslBackend openssl
git config --global http.version HTTP/1.1

echo.
echo 正在添加远程仓库...
git remote remove origin 2>nul
git remote add origin https://github.com/Daliyorjock/soul-fragments-android.git

echo.
echo 正在推送代码到 GitHub...
echo （这可能需要几分钟，请耐心等待）
echo.

git push -u origin main

echo.
if %ERRORLEVEL% EQU 0 (
    echo ✅ 推送成功！
    echo.
    echo 下一步：
    echo 1. 访问 https://github.com/Daliyorjock/soul-fragments-android/actions
    echo 2. 等待 Build APK 完成（约 5-10 分钟）
    echo 3. 下载 app-debug.apk
) else (
    echo ❌ 推送失败
    echo.
    echo 请使用网页上传方式：
    echo 1. 访问 https://github.com/Daliyorjock/soul-fragments-android
    echo 2. 点击 "uploading an existing file"
    echo 3. 拖拽 D:\openclaw\soul-fragments-android 文件夹中的所有文件
    echo 4. 点击 Commit changes
)

echo.
pause
