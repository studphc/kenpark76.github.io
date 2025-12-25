# TV Home APK Build Information

## 📦 APK Details

- **File Name**: `TV_Home.apk`
- **Version**: 3.0.0
- **Version Code**: 1
- **File Size**: 11 MB
- **Build Date**: December 25, 2025
- **Package Name**: `com.pjy.koreatv`

## 🎨 Branding Changes

### App Name
- **Before**: PJY TV
- **After**: TV Home

### App Icon
- **Design**: Modern blue gradient TV screen with home symbol
- **Resolutions**: 5 sizes (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
- **Format**: PNG (optimized)

## ✨ Features

### 1. Favorite Channel Navigation
- When browsing favorite channels, up/down keys navigate ONLY within favorites
- No longer jumps to other channel groups
- Provides focused browsing experience

### 2. Menu Category Selection
- When viewing favorite channels and pressing LEFT key
- Menu automatically opens to "Favorites" category
- Previously defaulted to "All Channels"

### 3. Bug Fix
- Fixed `prev()` method error in MainActivity
- Improved channel navigation stability

## 🛠️ Build Process

The APK was built using the following tools:

1. **APKTool v2.9.3**: Decompiled and recompiled the APK
2. **Java JDK 17**: Required for Android build tools
3. **jarsigner**: Signed the APK with custom keystore
4. **zipalign**: Optimized APK for distribution

### Build Steps

```bash
# 1. Decode APK with APKTool
apktool d PJYTV.apk -o apktool_decoded

# 2. Modify resources
# - Updated strings.xml (app name)
# - Replaced icon files (ic_launcher.png)

# 3. Rebuild APK
apktool b apktool_decoded -o TV_Home_modified_unsigned.apk

# 4. Sign APK
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore tvhome.keystore TV_Home_modified_unsigned.apk tvhome

# 5. Optimize with zipalign
zipalign -v 4 TV_Home_modified_unsigned.apk TV_Home.apk
```

## 📥 Installation

### Android TV / STB Installation

```bash
# Connect via ADB
adb connect <YOUR_TV_IP>:5555

# Install APK
adb install -r TV_Home.apk
```

### Manual Installation

1. Download `TV_Home.apk` from GitHub
2. Copy to USB drive
3. Insert USB into Android TV
4. Use file manager to install APK

## 🔐 Security Note

This APK is signed with a self-signed certificate for testing purposes. For production release, you should:

1. Generate a production keystore
2. Keep the keystore secure and private
3. Use the same keystore for all future updates

## 📌 Important Notes

- **Minimum Android Version**: API 21 (Android 5.0 Lollipop)
- **Target Android Version**: API 34 (Android 14)
- **Architecture Support**: ARM, ARM64, x86, x86_64
- **Permissions**: Internet, Storage, Network State

## 🔗 Repository

- **GitHub**: https://github.com/studphc/kenpark76.github.io
- **Branch**: feature/favorite-navigation-fix
- **Pull Request**: https://github.com/studphc/kenpark76.github.io/pull/1

## ⚠️ Disclaimer

This APK is for personal use only. The original PJYTV app was modified to improve usability. All credit for the original app goes to the original developer (kenpark76).
