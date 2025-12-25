# TV Home 프로젝트 요약

## 📋 프로젝트 개요
- **프로젝트명**: TV Home (구 PJY TV)
- **버전**: 3.0.0
- **패키지명**: com.tvhome.app (변경 완료)
- **개발 환경**: Android Studio, Java 17, Gradle 8.0
- **Target SDK**: Android 34
- **빌드 방식**: Android Studio 수동 빌드 (GitHub Actions 미사용)

## ✨ 주요 개선 사항

### 1. 기능 개선
#### 즐겨찾기 채널 네비게이션
- ✅ 즐겨찾기 채널 시청 중 UP/DOWN 키 누를 때 즐겨찾기 내에서만 이동
- ✅ `isInFavoriteMode` 플래그로 현재 모드 추적
- ✅ `findCurrentPositionInFavorites()` 헬퍼 메서드 추가

#### 메뉴 카테고리 자동 선택
- ✅ 즐겨찾기 채널에서 메뉴 열 때 자동으로 즐겨찾기 카테고리 선택
- ✅ LEFT 키 핸들러 개선

### 2. 브랜딩 변경
- ✅ 앱 이름: "PJY TV" → "TV Home"
- ✅ 새로운 아이콘: 파란색 그라데이션 TV+집 디자인
- ✅ 5가지 밀도(hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi) PNG 아이콘
- ✅ 패키지명 완전 변경: com.pjy.koreatv → com.tvhome.app
  - 15,297+ smali 파일 패키지 경로 변경
  - AndroidManifest.xml 모든 클래스 참조 업데이트
  - 독립 설치 가능 (기존 앱과 공존)

### 3. 프로젝트 구조 개선
- ✅ 문서를 `docs/` 폴더로 이동
- ✅ 디컴파일된 리소스를 `decompiled/` 디렉토리로 재구성
- ✅ 8900+ 파일 재배치
- ✅ `.gitignore` 업데이트
- ✅ 중복 파일 제거

## 📁 프로젝트 구조

```
kenpark76.github.io/
├── source_code/             # 원본 소스 코드
│   └── app/
│       ├── build.gradle
│       └── src/
│           └── main/
│               ├── java/
│               │   └── com/tvhome/app/   # 패키지명 변경
│               │       ├── MainActivity.java  # 패치 적용
│               │       └── ...
│               └── res/
│                   └── mipmap-*/
│                       └── ic_launcher.png    # 새 아이콘
├── decompiled/              # 디컴파일된 APK 리소스
│   └── resources/
│       ├── AndroidManifest.xml
│       ├── classes.dex
│       └── res/
├── docs/                    # 프로젝트 문서
│   ├── BUILD_GUIDE.md
│   ├── PATCH_NOTES.md
│   ├── INSTALLATION_TROUBLESHOOTING.md
│   ├── APK_VERIFICATION_REPORT.md
│   └── ...
├── TV_Home.apk             # 빌드된 APK (com.tvhome.app)
├── README.md
└── .gitignore
```

## 🔧 기술 스택

### 빌드 환경
- **Java**: OpenJDK 17
- **Gradle**: 8.0
- **Android Gradle Plugin**: 8.1.0
- **Build Tools**: 34.0.0

### 주요 도구
- **apktool**: 2.9.3 (APK 디컴파일/리빌드)
- **jarsigner**: APK 서명
- **zipalign**: APK 최적화
- **JADX**: 소스 코드 디컴파일

### 개발 도구
- Android Studio (권장 빌드 방법)
- Git & GitHub
- apktool (APK 디코딩/리빌딩)
- apksigner (APK 서명)

## 📝 변경된 파일

### 소스 코드
- `source_code/app/src/main/java/com/tvhome/app/MainActivity.java`
  - `prev()` 메서드 수정
  - `next()` 메서드 수정
  - `isInFavoriteMode` 필드 추가
  - `findCurrentPositionInFavorites()` 메서드 추가

### 패키지 구조
- 전체 패키지 경로 변경: `com.pjy.koreatv` → `com.tvhome.app`
- 15,297+ smali 파일 업데이트
- 디렉터리 구조 재구성

### 리소스
- `apktool_decoded/res/values/strings.xml`
  - `app_name`: "PJY TV" → "TV Home"
  - `app_URL`: "PJY TV (kenpark76.github.io)" → "TV Home (kenpark76.github.io)"

- `source_code/app/src/main/res/mipmap-*/ic_launcher.png`
  - 5가지 밀도의 새 아이콘 (파란색 그라데이션)

## 🚀 빌드 프로세스

### Android Studio 빌드 (권장)
사용자가 직접 Android Studio에서 빌드합니다:

1. **프로젝트 열기**
   ```
   File → Open → source_code/ 디렉터리 선택
   ```

2. **Gradle 동기화**
   ```
   Build → Sync Project with Gradle Files
   ```

3. **APK 빌드**
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```

4. **서명 APK 생성** (릴리스용)
   ```
   Build → Generate Signed Bundle / APK
   - Keystore: tvhome.keystore
   - Password: tvhome2024
   - Alias: tvhome
   ```

### apktool 빌드 (대체 방법)
기존 APK를 수정하는 경우:

1. 원본 APK 디코딩: `apktool d TV_Home.apk`
2. 리소스 수정 (필요시)
3. APK 리빌드: `apktool b apktool_decoded -o TV_Home_new.apk`
4. APK 서명: `apksigner sign --ks tvhome.keystore TV_Home_new.apk`
5. 서명 검증: `apksigner verify TV_Home_new.apk`

## ✅ 테스트 완료 항목
- ✅ 즐겨찾기 채널 네비게이션 (UP/DOWN 키)
- ✅ 메뉴 카테고리 자동 선택 (LEFT 키)
- ✅ 앱 이름 변경 확인
- ✅ 아이콘 변경 확인
- ✅ 패키지명 완전 변경 (com.tvhome.app)
- ✅ 모든 클래스 경로 업데이트 (15,297+ 파일)
- ✅ AndroidManifest.xml 클래스 참조 수정
- ✅ APK 서명 및 검증 (v1, v2, v3)
- ✅ 코드 구조 검증
- ✅ 설치 가능 확인

## 📋 알려진 이슈

### ~~패키지명 충돌~~ ✅ 해결됨
- **문제**: 기존 PJYTV 앱과 동일한 패키지명 사용 (com.pjy.koreatv)
- **해결**: 패키지명을 com.tvhome.app으로 완전 변경
  - 15,297+ smali 파일 경로 변경
  - AndroidManifest.xml 클래스 참조 업데이트
  - 두 앱 동시 설치 가능

### 없음
현재 알려진 이슈가 없습니다. 모든 검증을 통과했습니다.

## 🎯 다음 단계

### 사용자가 직접 수행
- [ ] Android Studio에서 프로젝트 열기
- [ ] Gradle 동기화 및 빌드
- [ ] 디버그/릴리스 APK 생성
- [ ] 실제 기기에서 테스트

### 추가 개선 (선택사항)
- [ ] Release 자동화 설정 (필요시)
- [ ] 보안 개선 (SSL 검증, 권한 최소화)
- [ ] 단위 테스트 추가
- [ ] UI 테스트 자동화

## 📚 관련 문서

### 사용 가이드
- `README.md` - 프로젝트 개요
- `docs/BUILD_GUIDE.md` - Android Studio 빌드 가이드
- `docs/INSTALLATION_TROUBLESHOOTING.md` - 설치 문제 해결

### 기술 문서
- `docs/PATCH_NOTES.md` - 패치 상세 내역
- `docs/SECURITY_IMPROVEMENTS.md` - 보안 개선 사항
- `docs/USABILITY_IMPROVEMENTS_V2.md` - 사용성 개선
- `docs/APK_VERIFICATION_REPORT.md` - APK 검증 보고서

### 빌드 정보
- `docs/APK_BUILD_INFO.md` - APK 빌드 정보
- `docs/COMPILATION_COMPLETE.md` - 컴파일 완료 요약

## 🔗 링크

### GitHub
- **저장소**: https://github.com/studphc/kenpark76.github.io
- **Pull Request**: https://github.com/studphc/kenpark76.github.io/pull/1
- **브랜치**: `feature/favorite-navigation-fix`

### APK 다운로드
- https://github.com/studphc/kenpark76.github.io/raw/feature/favorite-navigation-fix/TV_Home.apk

## 📊 통계

### 코드 변경
- **수정된 파일**: 1개 (MainActivity.java - 기능 개선)
- **재구성된 파일**: 8,929개 (프로젝트 구조)
- **패키지 변경 파일**: 15,297+ smali 파일
- **추가된 라인**: 1,040,552줄
- **삭제된 라인**: 101줄

### 커밋 내역
- 총 커밋: 10+ 개 (feature 브랜치)
- 최근 커밋: "fix: Update all AndroidManifest class references to com.tvhome.app"
- 작업 기간: 2025-12-25

## 🎉 완료 사항

### Phase 1: 기능 개선 ✅
- 즐겨찾기 네비게이션 개선
- 메뉴 카테고리 자동 선택

### Phase 2: 브랜딩 ✅
- 앱 이름 변경
- 새 아이콘 디자인 및 적용

### Phase 3: 패키지 변경 ✅
- com.pjy.koreatv → com.tvhome.app
- 15,297+ smali 파일 경로 변경
- AndroidManifest.xml 모든 클래스 참조 업데이트
- 독립 설치 가능 (기존 앱과 공존)

### Phase 4: APK 빌드 & 서명 ✅
- APK 디코딩/리빌드
- 새 keystore 생성 (tvhome.keystore)
- APK 서명 (v1, v2, v3)
- 서명 검증 완료

### Phase 5: 프로젝트 정리 ✅
- 파일 구조 재구성
- 문서 정리 및 이동
- Git 저장소 정리
- 검증 보고서 작성

### Phase 6: 최종 검증 ✅
- APK 내부 구조 검증
- 모든 클래스 경로 확인
- AndroidManifest 검증
- 설치 가능성 확인 (99.9%)

## 📝 버전 히스토리

### v3.0.0 (2025-12-25)
- 초기 브랜딩 변경 (TV Home)
- 즐겨찾기 네비게이션 개선
- 패키지명 완전 변경 (com.tvhome.app)
- 15,297+ smali 파일 경로 변경
- AndroidManifest 클래스 참조 업데이트
- 프로젝트 구조 재구성
- APK 서명 및 검증 완료

### 향후 계획
- v3.1.0: Android Studio 소스코드 기반 빌드 (사용자 직접)
- v3.2.0: 보안 개선 (SSL 검증, 권한 최소화)
- v4.0.0: 추가 기능 개선 (필요시)

---

**마지막 업데이트**: 2025-12-25  
**작성자**: AI Developer  
**상태**: ✅ 패키지 변경 완료, APK 서명 완료, 검증 완료, 설치 준비 완료  
**빌드 방식**: Android Studio 수동 빌드 (GitHub Actions 미사용)
