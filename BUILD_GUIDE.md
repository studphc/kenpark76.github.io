# PJY TV 빌드 가이드

## 📋 목차
1. [시스템 요구사항](#시스템-요구사항)
2. [프로젝트 설정](#프로젝트-설정)
3. [빌드 방법](#빌드-방법)
4. [문제 해결](#문제-해결)
5. [보안 고려사항](#보안-고려사항)

---

## 🖥️ 시스템 요구사항

### 필수 소프트웨어
- **Android Studio**: Electric Eel (2022.1.1) 이상
- **JDK**: Java 17 이상
- **Android SDK**: API Level 34
- **Gradle**: 8.0+ (Android Studio에 포함)

### 하드웨어 권장사항
- RAM: 8GB 이상 (16GB 권장)
- 디스크 공간: 10GB 이상 여유 공간
- CPU: 멀티코어 프로세서 권장

---

## 🔧 프로젝트 설정

### 1. Android Studio에서 프로젝트 열기

```bash
File → Open → 프로젝트 경로/source_code 선택
```

### 2. Gradle Sync

프로젝트가 열리면 자동으로 Gradle Sync가 시작됩니다.
- 상태 표시줄에서 "Gradle Sync" 진행 상황 확인
- 완료 시까지 약 5-10분 소요 (인터넷 속도에 따라 다름)

**주의**: 첫 빌드 시 모든 의존성을 다운로드하므로 시간이 걸릴 수 있습니다.

### 3. Android SDK 확인

```
Tools → SDK Manager → SDK Platforms
✅ Android 14.0 (API 34) 설치 확인
```

```
Tools → SDK Manager → SDK Tools
✅ Android SDK Build-Tools 34.0.0
✅ Android Emulator
✅ Android SDK Platform-Tools
```

---

## 🏗️ 빌드 방법

### 방법 1: Android Studio UI 사용

#### Debug APK 빌드
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```
- 빌드 완료 후 `app/build/outputs/apk/debug/app-debug.apk` 생성
- 빌드 시간: 약 2-5분

#### Release APK 빌드
```
Build → Generate Signed Bundle / APK
→ APK 선택
→ Create new keystore (또는 기존 사용)
→ 키 정보 입력
→ release 선택
→ Finish
```
- 빌드 완료 후 `app/build/outputs/apk/release/app-release.apk` 생성

### 방법 2: 커맨드 라인 사용

#### Debug 빌드
```bash
cd source_code
./gradlew assembleDebug

# Windows
gradlew.bat assembleDebug
```

#### Release 빌드 (서명 필요)
```bash
cd source_code
./gradlew assembleRelease
```

### 방법 3: APK 서명 (커맨드 라인)

```bash
# 1. Keystore 생성 (처음 한 번만)
keytool -genkey -v -keystore pjytv-release.jks \
  -alias pjytv -keyalg RSA -keysize 2048 -validity 10000

# 2. APK 서명
jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
  -keystore pjytv-release.jks \
  app/build/outputs/apk/release/app-release-unsigned.apk pjytv

# 3. Zipalign (최적화)
zipalign -v 4 \
  app/build/outputs/apk/release/app-release-unsigned.apk \
  app/build/outputs/apk/release/app-release-signed.apk
```

---

## 🐛 문제 해결

### 문제 1: Gradle Sync 실패

**증상**: "Could not resolve dependencies" 에러

**해결**:
```bash
# 1. Gradle 캐시 클리어
cd source_code
./gradlew clean --refresh-dependencies

# 2. Android Studio 캐시 무효화
File → Invalidate Caches / Restart → Invalidate and Restart
```

### 문제 2: Out of Memory 에러

**증상**: "Out of memory: Java heap space"

**해결**: `gradle.properties` 수정
```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
```

### 문제 3: API 호환성 에러

**증상**: "Minimum supported Gradle version is 8.0"

**해결**:
```bash
# gradle/wrapper/gradle-wrapper.properties 확인
distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip
```

### 문제 4: 서명 키 분실

**증상**: 기존 APK와 서명이 달라 업데이트 불가

**대응**:
- 새 keystore로 완전히 새 버전 출시
- 사용자에게 기존 앱 삭제 후 재설치 안내

---

## 🔐 보안 고려사항

### ⚠️ 주의: 보안 개선 권장사항

빌드하기 전에 다음 보안 취약점을 수정하는 것을 권장합니다:

#### 1. SSL 인증서 검증 활성화

**파일**: `source_code/app/src/main/java/com/pjy/koreatv/requests/HttpClient.java`

**현재 코드** (취약):
```java
public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
    // 비어있음 - 모든 인증서 허용
}
```

**권장 수정**:
```java
public void checkServerTrusted(X509Certificate[] chain, String authType) 
    throws CertificateException {
    // 시스템 기본 검증 사용
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(
        TrustManagerFactory.getDefaultAlgorithm());
    tmf.init((KeyStore) null);
    for (TrustManager tm : tmf.getTrustManagers()) {
        ((X509TrustManager) tm).checkServerTrusted(chain, authType);
    }
}
```

#### 2. 불필요한 권한 제거

**파일**: `source_code/app/src/main/AndroidManifest.xml`

**제거 권장**:
```xml
<!-- 자동 업데이트가 필요하지 않다면 제거 -->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```

#### 3. Network Security Config 강화

**파일 생성**: `source_code/app/src/main/res/xml/network_security_config.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="false">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
    
    <!-- GitHub 및 신뢰 가능한 도메인만 허용 -->
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">kenpark76.github.io</domain>
        <domain includeSubdomains="true">gitlink.org.cn</domain>
    </domain-config>
</network-security-config>
```

**AndroidManifest.xml** 수정:
```xml
<application
    ...
    android:networkSecurityConfig="@xml/network_security_config"
    android:usesCleartextTraffic="false">
```

---

## 📦 빌드 산출물

### 생성되는 파일

#### Debug 빌드
- **경로**: `app/build/outputs/apk/debug/`
- **파일**: `app-debug.apk`
- **크기**: 약 12-15 MB
- **서명**: Debug 키로 자동 서명
- **용도**: 개발 및 테스트

#### Release 빌드
- **경로**: `app/build/outputs/apk/release/`
- **파일**: `app-release.apk`
- **크기**: 약 10-12 MB (ProGuard 적용 시 더 작음)
- **서명**: 별도 키로 서명 필요
- **용도**: 배포

---

## 🚀 배포

### 1. APK 직접 배포 (현재 방식)

```bash
# GitHub Pages에 APK 업로드
cp app/build/outputs/apk/release/app-release.apk ../PJYTV.apk
git add PJYTV.apk
git commit -m "Update APK to v3.0.1"
git push
```

### 2. Google Play Store 배포 (선택)

**필요사항**:
- Google Play Developer 계정 ($25 일회성)
- 개인정보 처리방침 URL
- 앱 아이콘 및 스크린샷

**단계**:
1. AAB 파일 생성: `Build → Generate Signed Bundle / APK → Android App Bundle`
2. Play Console에 업로드
3. 앱 세부정보 작성
4. 검토 제출

---

## 📊 빌드 최적화

### ProGuard 활성화 (앱 크기 감소)

**파일**: `source_code/app/build.gradle`

```gradle
buildTypes {
    release {
        minifyEnabled true  // false → true로 변경
        shrinkResources true  // 추가
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 
                      'proguard-rules.pro'
    }
}
```

**효과**:
- APK 크기 약 30-40% 감소
- 코드 난독화로 보안 향상
- 빌드 시간 증가 (약 2-3배)

### MultiDex 비활성화 (선택)

현재 minSdk가 21이므로 자동으로 MultiDex 지원됩니다.

---

## 🔍 빌드 검증

### APK 정보 확인

```bash
# APK 정보 출력
aapt dump badging app/build/outputs/apk/release/app-release.apk

# 서명 확인
jarsigner -verify -verbose -certs app-release.apk
```

### 설치 테스트

```bash
# 에뮬레이터/실제 기기에 설치
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 로그 확인
adb logcat | grep "pjy.koreatv"
```

---

## 📝 버전 관리

### 버전 업데이트

**파일**: `source_code/app/build.gradle`

```gradle
defaultConfig {
    versionCode 2  // 1 → 2 (정수, 업데이트마다 증가)
    versionName "3.0.1"  // 사용자에게 표시되는 버전
}
```

**규칙**:
- `versionCode`: 정수, 순차적으로 증가
- `versionName`: 문자열, Semantic Versioning (Major.Minor.Patch)

---

## 🆘 지원

### 문제 보고
- **GitHub Issues**: https://github.com/studphc/kenpark76.github.io/issues
- **보안 이슈**: 비공개로 직접 연락

### 추가 문서
- `SECURITY_AUDIT.md`: 보안 검토 리포트
- `PATCH_NOTES.md`: 사용성 개선 패치 노트
- `README.md`: 프로젝트 개요

---

**작성자**: AI Development Team  
**마지막 업데이트**: 2025-12-25  
**문서 버전**: 1.0
