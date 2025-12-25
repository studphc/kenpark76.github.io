# Android Studio 빌드 가이드

## 📋 개요

이 문서는 Android Studio를 사용하여 TV Home 앱을 수동으로 빌드하는 방법을 설명합니다.

**GitHub Actions 자동 빌드는 제외되었으며**, 사용자가 직접 Android Studio에서 빌드를 수행합니다.

## 🔧 사전 요구사항

### 1. Android Studio 설치
- **최소 버전**: Android Studio Giraffe (2022.3.1) 이상
- **권장 버전**: Android Studio Hedgehog (2023.1.1) 이상
- 다운로드: https://developer.android.com/studio

### 2. JDK 설치
- **필수 버전**: Java 17 (OpenJDK)
- Android Studio에 포함되어 있지만, 별도 설치도 가능

### 3. Android SDK
- **Target SDK**: API 34 (Android 14)
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- Android Studio의 SDK Manager에서 설치

## 📁 프로젝트 구조

```
kenpark76.github.io/
├── source_code/              # Android Studio 프로젝트 디렉터리
│   ├── app/
│   │   ├── build.gradle      # 앱 레벨 Gradle 설정
│   │   └── src/
│   │       └── main/
│   │           ├── java/com/tvhome/app/
│   │           │   ├── MainActivity.java
│   │           │   ├── MyTVApplication.java
│   │           │   ├── BootReceiver.java
│   │           │   └── ...
│   │           ├── res/
│   │           │   ├── mipmap-*/ic_launcher.png
│   │           │   └── ...
│   │           └── AndroidManifest.xml
│   ├── build.gradle          # 프로젝트 레벨 Gradle 설정
│   ├── settings.gradle
│   └── gradle.properties
├── TV_Home.apk               # 빌드된 APK
└── tvhome.keystore           # 서명용 키스토어
```

## 🚀 빌드 프로세스

### 단계 1: 프로젝트 열기

1. **Android Studio 실행**
2. **File → Open** 선택
3. `source_code/` 디렉터리 선택
4. **OK** 클릭

### 단계 2: Gradle 동기화

프로젝트가 열리면 자동으로 Gradle 동기화가 시작됩니다.

수동으로 동기화하려면:
```
File → Sync Project with Gradle Files
```

또는 툴바의 🐘 (Gradle Elephant) 아이콘 클릭

### 단계 3: 빌드 설정 확인

#### build.gradle (프로젝트 레벨)
```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.0'
    }
}
```

#### build.gradle (앱 레벨)
```gradle
android {
    compileSdk 34
    
    defaultConfig {
        applicationId "com.tvhome.app"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "3.0.0"
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
}
```

### 단계 4: 디버그 APK 빌드

#### 방법 1: Build 메뉴 사용
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

빌드 완료 후 알림 팝업에서 **Locate** 클릭

#### 방법 2: Gradle 명령어 사용
```bash
cd source_code
./gradlew assembleDebug
```

**빌드된 APK 위치:**
```
source_code/app/build/outputs/apk/debug/app-debug.apk
```

### 단계 5: 릴리스 APK 빌드 (서명)

#### 5.1. 키스토어 확인

프로젝트 루트에 `tvhome.keystore` 파일이 있는지 확인:
```bash
ls -la tvhome.keystore
```

키스토어 정보:
- **파일명**: tvhome.keystore
- **비밀번호**: tvhome2024
- **Alias**: tvhome
- **Alias 비밀번호**: tvhome2024

#### 5.2. 서명된 APK 생성

**방법 1: Android Studio UI 사용**

1. **Build → Generate Signed Bundle / APK** 선택
2. **APK** 선택 → **Next**
3. 키스토어 정보 입력:
   - **Key store path**: `tvhome.keystore` 경로 선택
   - **Key store password**: `tvhome2024`
   - **Key alias**: `tvhome`
   - **Key password**: `tvhome2024`
4. **Next** 클릭
5. **Build Variants**: `release` 선택
6. **Signature Versions**: V1, V2, V3 모두 체크
7. **Finish** 클릭

**빌드된 APK 위치:**
```
source_code/app/release/app-release.apk
```

**방법 2: Gradle 명령어 사용**

`build.gradle` (앱 레벨)에 서명 설정 추가:

```gradle
android {
    signingConfigs {
        release {
            storeFile file('../tvhome.keystore')
            storePassword 'tvhome2024'
            keyAlias 'tvhome'
            keyPassword 'tvhome2024'
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

빌드 실행:
```bash
cd source_code
./gradlew assembleRelease
```

## ✅ 빌드 검증

### APK 서명 확인

```bash
# apksigner 사용
apksigner verify --verbose TV_Home.apk

# 또는 jarsigner 사용
jarsigner -verify -verbose -certs TV_Home.apk
```

**예상 출력:**
```
Verifies
Verified using v1 scheme (JAR signing): true
Verified using v2 scheme (APK Signature Scheme v2): true
Verified using v3 scheme (APK Signature Scheme v3): true
```

### APK 정보 확인

```bash
aapt dump badging TV_Home.apk | grep -E "package:|application-label:|sdkVersion:|targetSdkVersion:"
```

**예상 출력:**
```
package: name='com.tvhome.app' versionCode='1' versionName='3.0.0'
application-label:'TV Home'
sdkVersion:'21'
targetSdkVersion:'34'
```

## 📱 APK 설치

### 방법 1: adb 사용

```bash
adb install TV_Home.apk
```

### 방법 2: Android Studio에서 직접 실행

1. 디바이스를 USB로 연결 (또는 에뮬레이터 실행)
2. 툴바에서 디바이스 선택
3. **Run 'app'** (▶️) 버튼 클릭

### 방법 3: 파일 매니저 사용

1. APK 파일을 Android TV로 전송
2. 파일 매니저에서 APK 선택
3. "설치" 클릭
4. "알 수 없는 출처" 허용 (필요시)

## 🔧 문제 해결

### Gradle 동기화 실패

**증상**: "Sync failed: ..." 오류 메시지

**해결방법:**
1. **File → Invalidate Caches / Restart** 실행
2. `.gradle` 폴더 삭제 후 재동기화
3. Gradle 버전 확인 (8.0 이상 필요)

### Java 버전 오류

**증상**: "Unsupported Java version" 오류

**해결방법:**
1. **File → Settings → Build, Execution, Deployment → Build Tools → Gradle**
2. **Gradle JDK**: Java 17 선택
3. **Apply** → **OK**

### 서명 오류

**증상**: "Failed to sign APK" 오류

**해결방법:**
1. 키스토어 파일 경로 확인
2. 비밀번호 확인 (tvhome2024)
3. 키스토어가 손상되었다면 새로 생성:

```bash
keytool -genkey -v -keystore tvhome.keystore -alias tvhome -keyalg RSA -keysize 2048 -validity 10000
```

### 설치 오류

**증상**: "App not installed" 또는 "Package is invalid"

**해결방법:**
1. 기존 앱 삭제: `adb uninstall com.tvhome.app`
2. APK 서명 확인: `apksigner verify TV_Home.apk`
3. 자세한 로그 확인: `adb logcat | grep INSTALL`

자세한 내용은 `docs/INSTALLATION_TROUBLESHOOTING.md` 참조

## 📊 빌드 통계

### 디버그 빌드
- **빌드 시간**: 약 2-5분 (첫 빌드)
- **APK 크기**: 약 11MB
- **서명**: Debug 키로 자동 서명

### 릴리스 빌드
- **빌드 시간**: 약 3-7분 (첫 빌드)
- **APK 크기**: 약 11MB
- **서명**: tvhome.keystore로 서명
- **최적화**: ProGuard/R8 (선택사항)

## 🎯 권장 설정

### Gradle 메모리 설정

`gradle.properties` 파일에 추가:
```properties
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m
org.gradle.parallel=true
org.gradle.caching=true
```

### 빌드 속도 개선

1. **Instant Run** 활성화 (Android Studio 설정)
2. **Gradle Daemon** 사용
3. **병렬 빌드** 활성화
4. **빌드 캐시** 활성화

## 📚 추가 정보

### 관련 문서
- `BUILD_GUIDE.md` - 일반 빌드 가이드
- `APK_BUILD_INFO.md` - APK 빌드 상세 정보
- `INSTALLATION_TROUBLESHOOTING.md` - 설치 문제 해결
- `APK_VERIFICATION_REPORT.md` - APK 검증 보고서

### 외부 링크
- Android Studio: https://developer.android.com/studio
- Gradle 문서: https://gradle.org/
- Android 개발자 가이드: https://developer.android.com/guide

---

**마지막 업데이트**: 2025-12-25  
**작성자**: AI Developer  
**상태**: ✅ 완료 - Android Studio 수동 빌드 가이드
