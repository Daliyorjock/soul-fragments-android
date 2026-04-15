@echo off
chcp 65001 >nul
echo ====================================
echo Android 应用调试日志收集器
echo ====================================
echo.
echo 使用说明：
echo 1. 先启动雷电模拟器
echo 2. 安装并打开 APK
echo 3. 运行此脚本
echo 4. 等待错误出现
echo 5. 按 Ctrl+C 停止，日志保存在 logcat_output.txt
echo.
echo 按任意键开始...
pause >nul

cd C:\platform-tools 2>nul
if not exist adb.exe (
    echo ❌ 未找到 ADB，请先下载 platform-tools
    echo 下载地址：https://dl.google.com/android/repository/platform-tools-latest-windows.zip
    pause
    exit /b
)

echo.
echo ✅ 开始收集日志...
echo （打开应用后，错误信息会显示在这里）
echo.

adb logcat -c
adb logcat -s SoulFragments:* AndroidRuntime:* > "%~dp0logcat_output.txt"

echo.
echo ✅ 日志已保存到：logcat_output.txt
echo 用记事本打开查看错误信息
pause
