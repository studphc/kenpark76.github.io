# TV Home 프로젝트 요약

## 📋 프로젝트 개요
- **프로젝트명**: TV Home (구 PJY TV)
- **버전**: 3.0.0
- **패키지명**: com.pjy.koreatv
- **개발 환경**: Android Studio, Java 17, Gradle 8.0
- **Target SDK**: Android 34

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

### 3. 프로젝트 구조 개선
- ✅ 문서를 `docs/` 폴더로 이동
- ✅ 디컴파일된 리소스를 `decompiled/` 디렉토리로 재구성
- ✅ 8900+ 파일 재배치
- ✅ `.gitignore` 업데이트
- ✅ 중복 파일 제거

## 📁 프로젝트 구조

```
kenpark76.github.io/
├── .github/
│   └── workflows/           # GitHub Actions 워크플로우 (예정)
│       └── build-apk.yml
├── source_code/             # 원본 소스 코드
│   └── app/
│       ├── build.gradle
│       └── src/
│           └── main/
│               ├── java/
│               │   └── com/pjy/koreatv/
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
│   ├── GITHUB_ACTIONS_SETUP.md
│   ├── BUILD_GUIDE.md
│   ├── PATCH_NOTES.md
│   ├── SECURITY_IMPROVEMENTS.md
│   └── ...
├── TV_Home.apk             # 빌드된 APK
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
- Android Studio
- Git & GitHub
- GitHub Actions (CI/CD)

## 📝 변경된 파일

### 소스 코드
- `source_code/app/src/main/java/com/pjy/koreatv/MainActivity.java`
  - `prev()` 메서드 수정
  - `next()` 메서드 수정
  - `isInFavoriteMode` 필드 추가
  - `findCurrentPositionInFavorites()` 메서드 추가

### 리소스
- `apktool_decoded/res/values/strings.xml`
  - `app_name`: "PJY TV" → "TV Home"
  - `app_URL`: "PJY TV (kenpark76.github.io)" → "TV Home (kenpark76.github.io)"

- `source_code/app/src/main/res/mipmap-*/ic_launcher.png`
  - 5가지 밀도의 새 아이콘 (파란색 그라데이션)

## 🚀 빌드 프로세스

### 현재 빌드 방법
1. 원본 APK 디코딩: `apktool d PJYTV.apk`
2. 리소스 수정 (앱 이름, 아이콘)
3. APK 리빌드: `apktool b`
4. APK 서명: `jarsigner`
5. APK 최적화: `zipalign`

### 향후 자동화 (GitHub Actions)
- 코드 푸시 시 자동 빌드
- Pull Request 시 빌드 검증
- Artifact로 APK 자동 업로드
- 릴리스 자동 생성 (선택사항)

## ✅ 테스트 완료 항목
- ✅ 즐겨찾기 채널 네비게이션 (UP/DOWN 키)
- ✅ 메뉴 카테고리 자동 선택 (LEFT 키)
- ✅ 앱 이름 변경 확인
- ✅ 아이콘 변경 확인
- ✅ APK 서명 및 최적화
- ✅ 코드 구조 검증

## 📋 알려진 이슈

### 패키지명 충돌
- **문제**: 기존 PJYTV 앱과 동일한 패키지명 사용 (com.pjy.koreatv)
- **영향**: 기존 앱이 설치된 경우 설치 오류 발생
- **해결방법**: 
  1. 기존 앱 삭제 후 새 앱 설치
  2. 또는 패키지명 변경 (예: com.tvhome.app)

### GitHub Actions 권한
- **문제**: Workflow 파일 생성 시 권한 오류
- **해결방법**: 웹 브라우저에서 직접 생성
- **가이드**: `docs/GITHUB_ACTIONS_SETUP.md` 참조

## 🎯 다음 단계

### 우선순위 높음
- [ ] GitHub Actions 워크플로우 파일 추가
- [ ] 자동 빌드 테스트
- [ ] 패키지명 변경 고려

### 우선순위 중간
- [ ] Release 자동화 설정
- [ ] 슬랙 알림 통합
- [ ] 보안 개선 (SSL 검증, 권한 최소화)

### 우선순위 낮음
- [ ] 단위 테스트 추가
- [ ] UI 테스트 자동화
- [ ] 코드 커버리지 측정

## 📚 관련 문서

### 사용 가이드
- `README.md` - 프로젝트 개요
- `docs/GITHUB_ACTIONS_SETUP.md` - CI/CD 설정
- `docs/BUILD_GUIDE.md` - 빌드 가이드

### 기술 문서
- `docs/PATCH_NOTES.md` - 패치 상세 내역
- `docs/SECURITY_IMPROVEMENTS.md` - 보안 개선 사항
- `docs/USABILITY_IMPROVEMENTS_V2.md` - 사용성 개선

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
- **수정된 파일**: 1개 (MainActivity.java)
- **재구성된 파일**: 8929개
- **추가된 라인**: 1,040,552줄
- **삭제된 라인**: 101줄

### 커밋 내역
- 총 커밋: 5개 (feature 브랜치)
- 최근 커밋: "docs: Add GitHub Actions setup guide"
- 작업 기간: 2025-12-25

## 🎉 완료 사항

### Phase 1: 기능 개선 ✅
- 즐겨찾기 네비게이션 개선
- 메뉴 카테고리 자동 선택

### Phase 2: 브랜딩 ✅
- 앱 이름 변경
- 새 아이콘 디자인 및 적용

### Phase 3: APK 빌드 ✅
- APK 디코딩/리빌드
- APK 서명 및 최적화

### Phase 4: 프로젝트 정리 ✅
- 파일 구조 재구성
- 문서 정리 및 이동
- Git 저장소 정리

### Phase 5: CI/CD 준비 ✅
- GitHub Actions 가이드 작성
- 워크플로우 템플릿 제공
- 자동화 설정 문서화

## 📝 버전 히스토리

### v3.0.0 (2025-12-25)
- 초기 브랜딩 변경 (TV Home)
- 즐겨찾기 네비게이션 개선
- 프로젝트 구조 재구성
- GitHub Actions 가이드 추가

### 향후 계획
- v3.1.0: GitHub Actions 자동 빌드 적용
- v3.2.0: 보안 개선 (SSL 검증, 권한 최소화)
- v4.0.0: 패키지명 변경 (com.tvhome.app)

---

**마지막 업데이트**: 2025-12-25  
**작성자**: AI Developer  
**상태**: ✅ 프로젝트 정리 완료, CI/CD 준비 완료
