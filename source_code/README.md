# TV Home - 사용성 개선 버전

## 📱 개요

PJYTV 앱을 기반으로 "TV Home"이라는 이름으로 사용성을 개선한 버전입니다. JADX를 사용하여 APK를 디컴파일하고, 소스코드를 복원하여 개선사항을 적용했습니다.

## ✨ 주요 개선사항

### 1. 즐겨찾기 채널 탐색 개선
- **문제**: 즐겨찾기 채널 시청 중 위/아래 키로 이동 시 전체 채널 목록으로 넘어감
- **해결**: 즐겨찾기 채널 내에서만 순환 이동하도록 수정

### 2. 메뉴 카테고리 기본 선택 개선  
- **문제**: 즐겨찾기 채널 시청 중 메뉴를 열면 "전체 채널" 카테고리가 선택됨
- **해결**: 즐겨찾기 채널 시청 중 메뉴를 열면 "즐겨찾기" 카테고리가 기본 선택되도록 수정

### 3. 코드 버그 수정
- `prev()` 메서드의 변수 선언 누락 수정
- 즐겨찾기 빈 목록 처리 강화

## 🔧 기술 스택

- **언어**: Kotlin + Java (디컴파일된 코드)
- **빌드 시스템**: Gradle 8.0
- **Android SDK**: 
  - compileSdk: 34
  - minSdk: 21
  - targetSdk: 34
- **주요 라이브러리**:
  - AndroidX Core, AppCompat, ConstraintLayout
  - Media3 ExoPlayer (비디오 재생)
  - OkHttp3, Retrofit2 (네트워킹)
  - Gson (JSON 파싱)
  - Glide (이미지 로딩)
  - ZXing (QR 코드)

## 📁 프로젝트 구조

```
source_code/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── AndroidManifest.xml
│   │       ├── java/
│   │       │   ├── com/pjy/koreatv/       # 메인 앱 코드
│   │       │   ├── androidx/              # AndroidX 라이브러리
│   │       │   └── ... (기타 라이브러리)
│   │       ├── res/                       # 리소스 파일
│   │       ├── assets/                    # 에셋 파일
│   │       └── jniLibs/                   # 네이티브 라이브러리
│   │           ├── arm64-v8a/
│   │           ├── armeabi-v7a/
│   │           ├── x86/
│   │           └── x86_64/
│   ├── build.gradle                       # 앱 빌드 설정
│   └── proguard-rules.pro                 # ProGuard 규칙
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties      # Gradle Wrapper 설정
├── build.gradle                           # 프로젝트 빌드 설정
├── settings.gradle                        # 프로젝트 설정
└── gradle.properties                      # Gradle 속성
```

## 🚀 빌드 방법

### 사전 요구사항
- Android Studio Electric Eel (2022.1.1) 이상
- JDK 17 이상
- Android SDK API 34
- Gradle 8.0 이상

### Android Studio에서 빌드

1. **프로젝트 열기**
   ```bash
   # Android Studio 실행 후
   File > Open > source_code 폴더 선택
   ```

2. **Gradle 동기화**
   - 프로젝트 열린 후 자동으로 Gradle 동기화 시작
   - 또는 `File > Sync Project with Gradle Files`

3. **Debug APK 빌드**
   ```bash
   Build > Build Bundle(s) / APK(s) > Build APK(s)
   ```

4. **Release APK 빌드 (서명 필요)**
   ```bash
   Build > Generate Signed Bundle / APK
   # APK 선택 > 키스토어 정보 입력 > Release 빌드 타입 선택
   ```

### 명령줄에서 빌드

```bash
# 프로젝트 디렉터리로 이동
cd source_code

# Debug APK 빌드
./gradlew assembleDebug

# Release APK 빌드 (서명 설정 필요)
./gradlew assembleRelease

# 빌드 출력 위치
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

## 📦 APK 설치 및 테스트

### ADB를 통한 설치

```bash
# Android TV/박스에 ADB 연결
adb connect <TV_IP>:5555

# APK 설치 (기존 앱 덮어쓰기)
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 앱 실행
adb shell am start -n com.pjy.koreatv/.MainActivity

# 로그 확인
adb logcat | grep "MainActivity1"
```

### 테스트 시나리오

#### ✅ 시나리오 1: 즐겨찾기 채널 탐색
1. 앱 실행
2. 메뉴(왼쪽 키) > 즐겨찾기 선택
3. 즐겨찾기 채널 선택
4. 위/아래 키로 채널 이동
5. **예상 결과**: 즐겨찾기 채널 내에서만 순환 이동

#### ✅ 시나리오 2: 메뉴 기본 카테고리
1. 즐겨찾기 채널 시청 중
2. 왼쪽 키 눌러 메뉴 열기
3. **예상 결과**: 즐겨찾기 카테고리가 선택된 상태로 메뉴 표시

#### ✅ 시나리오 3: 일반 채널 모드 전환
1. 메뉴에서 다른 카테고리(예: 지상파) 선택
2. 채널 선택 후 위/아래 키 이동
3. **예상 결과**: 전체 채널 목록에서 이동 (기존 동작 유지)

## 🔍 주요 코드 수정사항

### MainActivity.java

#### 1. 즐겨찾기 모드 추적 변수 추가
```java
private boolean isInFavoriteMode = false;
```

#### 2. prev() 메서드 수정
- 즐겨찾기 카테고리(groupIndex == 0)일 때 즐겨찾기 내에서만 이동
- `findCurrentPositionInFavorites()` 헬퍼 메서드 사용

#### 3. next() 메서드 수정
- prev()와 동일한 로직 적용

#### 4. onKeyDown() 메서드 수정 (LEFT 키)
- 즐겨찾기 모드일 때 메뉴 열 때 즐겨찾기 카테고리(index 0) 선택

자세한 코드는 `USABILITY_IMPROVEMENTS_V2.md` 참조

## 🛡️ 보안 개선 사항

### ⚠️ 현재 보안 이슈
1. **SSL 인증서 검증 비활성화** (High Risk)
   - `HttpClient.java`에서 모든 인증서 신뢰
   - MITM(중간자 공격) 취약점

2. **불필요한 권한** (Medium Risk)
   - `REQUEST_INSTALL_PACKAGES`: APK 자동 설치 권한

3. **평문 트래픽 허용** (Medium Risk)
   - `android:usesCleartextTraffic="true"`

### 🔧 권장 개선사항
자세한 내용은 `SECURITY_IMPROVEMENTS.md` 참조

## 📚 문서

- `SECURITY_AUDIT.md` - 전체 보안 감사 보고서
- `SECURITY_IMPROVEMENTS.md` - 보안 개선 가이드
- `USABILITY_IMPROVEMENTS_V2.md` - 사용성 개선 상세 문서
- `BUILD_GUIDE.md` - 빌드 상세 가이드
- `PATCH_NOTES.md` - 패치 노트

## ⚖️ 라이선스 및 주의사항

### ⚠️ 중요 고지

이 프로젝트는 PJYTV 앱을 JADX로 디컴파일하여 생성된 코드입니다.

**사용 제한**:
- ✅ **개인 사용 목적**: 자신의 디바이스에서 사용 가능
- ✅ **학습 목적**: 코드 분석 및 학습 가능
- ❌ **재배포 금지**: 수정된 APK를 타인에게 배포 금지
- ❌ **상업적 사용 금지**: 상업적 목적으로 사용 금지
- ❌ **원작자 권리 침해 금지**: 원저작자의 권리를 존중해야 함

### 📝 디스클레이머

이 수정 버전은 사용성 개선을 목적으로 하며:
- 원본 앱의 기능을 변경하지 않음
- 개인정보를 수집하지 않음
- 악의적인 코드를 포함하지 않음
- 사용자 경험 향상만을 목표로 함

**자기 책임 하에 사용하세요**. 이 코드 사용으로 인한 어떠한 문제에 대해서도 작성자는 책임지지 않습니다.

## 👥 기여

이 프로젝트는 커뮤니티 기여로 발전합니다.

**기여 방법**:
1. 이 저장소 Fork
2. 새로운 브랜치 생성 (`git checkout -b feature/amazing-feature`)
3. 변경사항 커밋 (`git commit -m 'Add amazing feature'`)
4. 브랜치에 푸시 (`git push origin feature/amazing-feature`)
5. Pull Request 생성

## 🐛 알려진 이슈

1. **디컴파일 코드 기반**
   - JADX로 디컴파일한 코드이므로 일부 구조가 원본과 다를 수 있음
   - Goto 레이블이 포함되어 가독성이 낮은 부분 존재

2. **빌드 테스트**
   - 이론적으로 빌드 가능하나 실제 Android Studio에서 테스트 필요
   - 일부 의존성 해결 문제가 발생할 수 있음

## 🔄 버전 히스토리

### v2.0 (2025-12-25)
- ✅ 즐겨찾기 채널 탐색 개선
- ✅ 메뉴 카테고리 기본 선택 개선
- ✅ `prev()` 메서드 버그 수정
- ✅ 전체 빌드 환경 구성
- ✅ 보안 감사 완료

### v1.0 (Initial)
- 🎉 APK 디컴파일
- 🎉 소스코드 복원

## 📞 지원

질문이나 문제가 있으시면 GitHub Issues를 통해 문의해주세요.

---

**마지막 업데이트**: 2025-12-25  
**버전**: v2.0  
**기반 APK**: PJYTV v3.0.0  
**작성자**: AI Assistant
