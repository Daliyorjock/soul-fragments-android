@echo off
chcp 65001 >nul
echo ====================================
echo 上传到 GitHub
echo ====================================
echo.

cd /d D:\openclaw\soul-fragments-android

echo 正在配置 Git...
git config --global user.email "user@example.com" 2>nul
git config --global user.name "User" 2>nul

echo.
echo 正在添加所有文件...
git add -A

echo.
echo 正在提交...
git commit -m "Complete rebuild with blank test app"

echo.
echo 正在推送到 GitHub...
echo （如果网络不好，可能需要重试）
echo.

git push -u origin main

echo.
if %ERRORLEVEL% EQU 0 (
    echo ✅ 上传成功！
    echo.
    echo 下一步：
    echo 1. 访问 https://github.com/Daliyorjock/soul-fragments-android/actions
    echo 2. 等待构建完成（5-10 分钟）
    echo 3. 下载最新 APK
) else (
    echo ❌ 推送失败（网络问题）
    echo.
    echo 请手动上传：
    echo 1. 访问 https://github.com/Daliyorjock/soul-fragments-android
    echo 2. 点击 Add file → Upload files
    echo 3. 拖拽所有文件上传
    echo 4. 点击 Commit changes
)

echo.
pause
