# TV Home - Android TV App

> Improved version of PJYTV with better usability and modern design

[![Build APK](https://github.com/studphc/kenpark76.github.io/actions/workflows/build-apk.yml/badge.svg)](https://github.com/studphc/kenpark76.github.io/actions/workflows/build-apk.yml)

## ✨ Features

### 🎯 Usability Improvements

1. **Favorite Channel Navigation**
   - Stay within favorites when browsing favorite channels
   - Up/Down keys navigate only within the favorites list
   - No jumping to other channel groups

2. **Smart Menu Category Selection**
   - Automatically opens to "Favorites" when viewing favorite channels
   - Previously defaulted to "All Channels"

3. **Bug Fixes**
   - Fixed `prev()` method error in MainActivity
   - Improved channel navigation stability

### 🎨 Branding

- **App Name**: TV Home
- **Icon**: Modern blue gradient TV + Home design
- **Package**: com.pjy.koreatv

## 📥 Download & Install

### Latest Release

Download the latest APK from [Releases](https://github.com/studphc/kenpark76.github.io/releases) or use the direct link:

```bash
# Download
curl -L -o TV_Home.apk "https://github.com/studphc/kenpark76.github.io/raw/feature/favorite-navigation-fix/TV_Home.apk"

# Install via ADB
adb connect <ANDROID_TV_IP>:5555
adb install -r TV_Home.apk
```

### Requirements

- **Minimum Android**: 5.0 (API 21)
- **Target Android**: 14 (API 34)
- **Architecture**: ARM, ARM64, x86, x86_64

## 🔧 Build from Source

### Prerequisites

- JDK 17
- Android SDK (API 34)
- Gradle 8.0+

### Build Steps

```bash
# Clone repository
git clone https://github.com/studphc/kenpark76.github.io.git
cd kenpark76.github.io

# Switch to development branch
git checkout feature/favorite-navigation-fix

# Build APK
cd source_code
./gradlew assembleDebug

# APK output: app/build/outputs/apk/debug/app-debug.apk
```

## 🤖 Automated Builds

This project uses GitHub Actions to automatically build APKs when code changes are pushed.

- **Workflow**: `.github/workflows/build-apk.yml`
- **Trigger**: Push to `feature/favorite-navigation-fix` branch
- **Output**: Signed APK uploaded to GitHub Releases

## 📚 Documentation

All documentation is available in the [`docs/`](docs/) directory:

- [Build Guide](docs/BUILD_GUIDE.md) - Detailed build instructions
- [APK Build Info](docs/APK_BUILD_INFO.md) - APK technical details
- [Usability Improvements](docs/USABILITY_IMPROVEMENTS_V2.md) - Feature details
- [Security Audit](docs/SECURITY_AUDIT.md) - Security analysis
- [Quick Test Guide](docs/QUICK_TEST_GUIDE.md) - Testing instructions

## 🛠️ Development

### Project Structure

```
├── source_code/           # Android app source code
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/     # Java/Kotlin source
│   │   │   └── res/      # Resources
│   │   └── build.gradle
│   ├── build.gradle
│   └── settings.gradle
├── docs/                  # Documentation
├── .github/workflows/     # CI/CD workflows
└── TV_Home.apk           # Latest release APK
```

### Key Modifications

1. **MainActivity.java**
   - Added `isInFavoriteMode` flag
   - Modified `prev()` and `next()` methods
   - Added `findCurrentPositionInFavorites()` helper

2. **Resources**
   - Updated app name to "TV Home"
   - New launcher icons (5 resolutions)

## ⚠️ Disclaimer

This is a modified version of PJYTV for personal use. All credit for the original app goes to the original developer (kenpark76).

## 📄 License

This project follows the same license as the original PJYTV application.

---

**Version**: 3.0.0  
**Build Date**: December 25, 2025  
**Status**: ✅ Ready for Testing
