# 心灵碎片 - APK 构建方案

## 🚀 方案 A：GitHub Actions 在线构建（最简单）

**优点**：不需要本地安装任何工具，GitHub 自动构建

### 步骤：

1. **创建 GitHub 仓库**
   - 访问 https://github.com/new
   - 仓库名：`soul-fragments-android`
   - 设为公开或私有均可

2. **上传项目**
   ```bash
   cd D:\openclaw\soul-fragments-android
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/你的用户名/soul-fragments-android.git
   git push -u origin main
   ```

3. **等待构建完成**
   - 访问 https://github.com/你的用户名/soul-fragments-android/actions
   - 等待 Build APK 任务完成（约 5-10 分钟）

4. **下载 APK**
   - 点击最新的构建记录
   - 在 "Artifacts" 部分下载 `app-debug.apk`

---

## 🖥️ 方案 B：本地 Android Studio 构建

**优点**：完全控制，可构建 Release 版本

### 步骤：

1. **下载安装 Android Studio**
   - 访问：https://developer.android.com/studio
   - 下载并安装

2. **打开项目**
   - 启动 Android Studio
   - File → Open → 选择 `D:\openclaw\soul-fragments-android`

3. **等待 Gradle 同步**
   - 首次打开会自动下载 Gradle 和依赖
   - 确保网络畅通

4. **构建 APK**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)

5. **获取 APK**
   - 位置：`app/build/outputs/apk/debug/app-debug.apk`

---

## 📦 方案 C：使用已打包的 ZIP

项目已打包好，可以直接复制到任何有 Android 环境的电脑：

**ZIP 位置**：`D:\openclaw\soul-fragments-android.zip`

---

## 💡 推荐

**如果你只是想快速测试**：使用方案 A（GitHub Actions）

**如果你需要频繁构建**：使用方案 B（本地 Android Studio）

---

## 📱 APK 信息

- 应用名称：心灵碎片
- 包名：com.soulfragments.app
- 版本：1.0.0
- 最低 Android：7.0 (API 24)
