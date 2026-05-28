# 📺 Realme Smart TV IR Remote

Control your **Realme Smart TV** from your **Mi/Xiaomi phone's IR blaster**.

---

## 📲 How to get the APK (no PC needed)

### Step 1 — Create a GitHub account
- Go to **github.com** on your phone browser
- Sign up for a free account

### Step 2 — Upload this project
1. Tap **+** → **New repository**
2. Name it `realme-remote`, set to **Public**
3. Tap **creating a new file**
4. You need to upload each file. The easiest way:
   - Use the **GitHub Android app** (install from Play Store)
   - Or use **GitHub web editor** on your browser

### Step 3 — Upload files (easiest method using GitHub web)
Upload the files in this order, keeping the folder structure:
```
realme-remote/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew               ← important!
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── .github/workflows/
│   └── build.yml         ← this triggers auto-build
└── app/
    ├── build.gradle
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/irremote/realme/
        │   ├── MainActivity.java
        │   └── RealmeTVCodes.java
        └── res/
            ├── layout/activity_main.xml
            ├── values/styles.xml
            ├── values/strings.xml
            ├── values/colors.xml
            └── drawable/ic_launcher_foreground.xml
```

**Tip:** On GitHub web, when creating a file in a subfolder, type the path directly in the filename box:
- Type `.github/workflows/build.yml` → it auto-creates the folders

### Step 4 — Watch it build automatically
1. Go to your repo → **Actions** tab
2. You'll see "Build APK" running (takes ~3-5 minutes)
3. Wait for the green ✓

### Step 5 — Download your APK
1. Click the finished workflow run
2. Scroll down to **Artifacts**
3. Tap **RealmeRemote-APK** to download the zip
4. Extract the zip → you get the `.apk` file

### Step 6 — Install on your Mi phone
1. Go to **Settings → Additional Settings → Privacy**
2. Enable **"Install unknown apps"** for your browser/file manager
3. Open the APK file and install
4. Done! 🎉

---

## 🔧 If buttons don't work (wrong IR codes)

Realme TV models may have slightly different codes. Try these fixes:

### Option A — Try alternate address bytes
Edit `RealmeTVCodes.java`, change:
```java
private static final int ADDR = 0x04;
```
Try `0x10`, `0x00`, `0x08`, or `0x40` one at a time.

### Option B — Use a universal IR database app
Install **"Mi Remote"** (official Xiaomi app) and select Realme TV.
The codes that work there are the ones to use.

### Option C — Check your TV model
- Realme TV (32") → address `0x04`
- Realme Smart TV 4K → address `0x10`  
- Realme TV Neo → address `0x08`

---

## 📱 Requirements
- Android phone **with IR blaster** (most Xiaomi/Redmi/POCO have one)
- Android 5.0+ (minSdk 21)
- Realme Smart TV (any model)

---

## 🔄 Re-building after changes
Any time you edit a file on GitHub → push/commit → Actions auto-builds a new APK.
