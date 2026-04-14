# 心灵碎片 - Android APK 构建指南

## 📦 项目说明

这是一个完整的 Android Studio 项目，包含 WebView 应用，加载 `app/src/main/assets/www/` 中的 Web 应用。

**应用信息：**
- 应用名称：心灵碎片
- 包名：`com.soulfragments.app`
- 版本：1.0.0
- 最低 Android 版本：7.0 (API 24)

---

## 🚀 构建步骤

### 方法 1：Android Studio（推荐）

1. **打开项目**
   - 启动 Android Studio
   - File → Open → 选择 `soul-fragments-android` 文件夹

2. **等待 Gradle 同步**
   - 首次打开会自动下载 Gradle 和依赖
   - 确保网络畅通（需要访问 Maven 仓库）

3. **构建 APK**
   - **Debug 版**：Build → Build Bundle(s) / APK(s) → Build APK(s)
   - **Release 版**：Build → Generate Signed Bundle / APK → 选择 APK

4. **获取 APK 文件**
   - Debug: `app/build/outputs/apk/debug/app-debug.apk`
   - Release: `app/build/outputs/apk/release/app-release.apk`

---

### 方法 2：命令行构建

```bash
cd soul-fragments-android

# Windows
gradlew.bat assembleDebug

# macOS/Linux
./gradlew assembleDebug
```

APK 输出位置：`app/build/outputs/apk/debug/app-debug.apk`

---

## 📁 项目结构

```
soul-fragments-android/
├── app/
│   ├── src/main/
│   │   ├── java/com/soulfragments/app/
│   │   │   └── MainActivity.java      # 主活动（WebView）
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml  # 布局文件
│   │   │   ├── values/
│   │   │   │   ├── strings.xml        # 字符串资源
│   │   │   │   └── themes.xml         # 主题样式
│   │   │   └── mipmap-*/
│   │   │       └── ic_launcher.xml    # 应用图标
│   │   ├── assets/www/                # Web 应用文件
│   │   │   ├── index.html
│   │   │   ├── manifest.json
│   │   │   └── capacitor.config.json
│   │   └── AndroidManifest.xml        # 应用清单
│   └── build.gradle                   # 应用级构建配置
├── build.gradle                       # 项目级构建配置
├── settings.gradle                    # 项目设置
├── gradle.properties                  # Gradle 属性
└── gradle/wrapper/
    └── gradle-wrapper.properties      # Gradle 版本
```

---

## ⚙️ 配置说明

### AndroidManifest.xml 权限
- `INTERNET` - 网络访问
- `ACCESS_NETWORK_STATE` - 网络状态
- `WRITE/READ_EXTERNAL_STORAGE` - 文件存储

### WebView 配置
- ✅ JavaScript 已启用
- ✅ DOM Storage 已启用
- ✅ 允许文件访问
- ✅ 混合内容模式（HTTP/HTTPS）
- ✅ 支持缩放

---

## 🎨 自定义图标

如果要替换应用图标：

1. 准备 512x512 PNG 图标
2. 替换以下文件：
   - `res/mipmap-mdpi/ic_launcher.png` (48x48)
   - `res/mipmap-hdpi/ic_launcher.png` (72x72)
   - `res/mipmap-xhdpi/ic_launcher.png` (96x96)
   - `res/mipmap-xxhdpi/ic_launcher.png` (144x144)
   - `res/mipmap-xxxhdpi/ic_launcher.png` (192x192)

或者使用 Android Studio 的 Image Asset 工具自动生成。

---

## 📱 安装到手机

### 方法 1：USB 调试
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 方法 2：直接传输
1. 将 APK 文件传到手机
2. 在手机上点击安装
3. 允许"未知来源"安装

---

## 🔧 常见问题

### Q: Gradle 同步失败
**A:** 检查网络连接，确保能访问：
- https://services.gradle.org
- https://dl.google.com
- https://repo.maven.apache.org

### Q: 构建时内存不足
**A:** 修改 `gradle.properties`:
```
org.gradle.jvmargs=-Xmx4096m
```

### Q: APK 安装后白屏
**A:** 检查 `assets/www/index.html` 是否存在，查看 Logcat 日志。

---

## 📞 技术支持

如有问题，查看 Android Studio 的 Build Output 或 Logcat 日志。
