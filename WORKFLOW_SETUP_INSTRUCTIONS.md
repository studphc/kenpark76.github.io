# 🚀 GitHub Actions 워크플로우 파일 이동 가이드

## 📁 생성된 파일
루트 디렉토리에 `build-apk.yml` 파일이 생성되었습니다.

## 📋 이동 방법

### ✅ 방법 1: GitHub 웹 인터페이스 (가장 쉬움! 권장)

1. **GitHub 저장소로 이동**
   - https://github.com/studphc/kenpark76.github.io

2. **Actions 탭 클릭**
   - 상단 메뉴에서 "Actions" 클릭

3. **새 워크플로우 생성**
   - "New workflow" 버튼 클릭
   - 또는 "set up a workflow yourself" 클릭

4. **파일 내용 복사**
   - 루트의 `build-apk.yml` 파일 내용 전체 복사
   - GitHub 에디터에 붙여넣기

5. **파일 경로 확인**
   - 파일명: `build-apk.yml`
   - 자동으로 `.github/workflows/` 경로에 생성됨

6. **커밋**
   - "Commit new file" 또는 "Commit changes" 클릭
   - 커밋 메시지: `feat: Add GitHub Actions workflow for automatic APK builds`

---

### 방법 2: GitHub CLI (gh 명령어)

```bash
# 저장소 루트에서 실행
cd /home/user/webapp

# 디렉토리 생성
mkdir -p .github/workflows

# 파일 복사
cp build-apk.yml .github/workflows/

# Git에 추가
git add .github/workflows/build-apk.yml

# 커밋
git commit -m "feat: Add GitHub Actions workflow for automatic APK builds"

# 푸시 (권한 문제로 실패할 수 있음)
git push origin feature/favorite-navigation-fix
```

⚠️ **주의**: 로컬에서 푸시 시 GitHub App 권한 문제로 실패할 수 있습니다.  
→ 이 경우 **방법 1 (웹 인터페이스)** 사용을 권장합니다.

---

### 방법 3: GitHub 파일 업로드 (간단!)

1. **GitHub 저장소로 이동**
   - https://github.com/studphc/kenpark76.github.io

2. **파일 업로드**
   - 저장소 메인 페이지에서 "Add file" > "Create new file" 클릭

3. **파일 경로 입력**
   - 파일명 입력창에: `.github/workflows/build-apk.yml`
   - (슬래시를 입력하면 자동으로 폴더 생성)

4. **내용 붙여넣기**
   - 루트의 `build-apk.yml` 파일 내용 복사
   - GitHub 에디터에 붙여넣기

5. **커밋**
   - 커밋 메시지: `feat: Add GitHub Actions workflow for automatic APK builds`
   - "Commit new file" 클릭

---

## 🎯 최종 파일 위치

워크플로우 파일이 다음 위치에 있어야 합니다:

```
kenpark76.github.io/
├── .github/
│   └── workflows/
│       └── build-apk.yml    ← 이 위치!
├── source_code/
├── docs/
├── TV_Home.apk
└── README.md
```

## ✅ 확인 방법

1. **GitHub에서 확인**
   - 저장소의 "Actions" 탭 확인
   - "Build Android APK" 워크플로우가 표시되어야 함

2. **자동 실행 테스트**
   - `source_code/` 또는 `apktool_decoded/` 폴더에 변경 사항 푸시
   - Actions 탭에서 자동으로 빌드가 시작되는지 확인

3. **수동 실행 테스트**
   - Actions 탭에서 "Build Android APK" 클릭
   - "Run workflow" 버튼으로 수동 실행 가능

## 🔧 권한 설정 (중요!)

워크플로우가 실행되려면 GitHub Actions 권한이 필요합니다:

1. **Settings** > **Actions** > **General**로 이동

2. **Workflow permissions** 섹션에서:
   - ✅ "Read and write permissions" 선택
   - ✅ "Allow GitHub Actions to create and approve pull requests" 체크

3. **Save** 클릭

## 🚀 실행 결과

워크플로우가 성공적으로 실행되면:

1. **APK 빌드**: TV_Home.apk 생성
2. **Artifacts**: Actions 탭에서 APK 다운로드 가능
3. **자동 실행**: 코드 변경 시 자동으로 빌드

## 📝 트러블슈팅

### 문제: 워크플로우가 표시되지 않음
**해결**: 파일이 `.github/workflows/` 경로에 있는지 확인

### 문제: 빌드 실패
**해결**: Actions 탭에서 로그 확인 후 오류 메시지 확인

### 문제: 권한 오류
**해결**: Settings에서 Workflow permissions 설정 확인

---

## 🎉 완료!

워크플로우 파일을 이동한 후:
1. ✅ Actions 탭에서 워크플로우 확인
2. ✅ "Run workflow" 버튼으로 수동 실행 테스트
3. ✅ 빌드된 APK를 Artifacts에서 다운로드

**추천 방법**: 방법 1 (GitHub 웹 인터페이스) - 가장 쉽고 안전합니다! 🌟
