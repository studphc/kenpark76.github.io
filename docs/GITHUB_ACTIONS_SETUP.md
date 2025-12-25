# GitHub Actions 자동 APK 빌드 설정 가이드

## 개요
코드 변경 시 자동으로 APK를 빌드하는 GitHub Actions 워크플로우 설정 가이드입니다.

## 사전 요구사항
- GitHub 저장소에 대한 관리자 권한
- GitHub Actions workflows 권한 활성화

## 워크플로우 파일 생성

저장소 루트에 `.github/workflows/build-apk.yml` 파일을 생성하고 다음 내용을 추가하세요:

```yaml
name: Build Android APK

on:
  push:
    branches:
      - main
      - feature/*
    paths:
      - 'source_code/**'
      - 'apktool_decoded/**'
  pull_request:
    branches:
      - main
    paths:
      - 'source_code/**'
      - 'apktool_decoded/**'
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - name: Checkout repository
      uses: actions/checkout@v4
      
    - name: Set up Java 17
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Install Android SDK
      uses: android-actions/setup-android@v3
      
    - name: Install required tools
      run: |
        sudo apt-get update
        sudo apt-get install -y apktool zipalign
        
    - name: Decode APK with apktool
      run: |
        if [ -f PJYTV.apk ]; then
          apktool d PJYTV.apk -o apktool_decoded_temp -f
        fi
        
    - name: Copy source code modifications
      run: |
        if [ -d apktool_decoded_temp ]; then
          # Copy modified strings.xml
          if [ -f apktool_decoded/res/values/strings.xml ]; then
            cp apktool_decoded/res/values/strings.xml apktool_decoded_temp/res/values/
          fi
          
          # Copy new icons
          if [ -d source_code/app/src/main/res ]; then
            find source_code/app/src/main/res/mipmap-* -name "ic_launcher.png" -exec cp {} apktool_decoded_temp/res/mipmap-{} \;
          fi
          
          # Copy modified Java/Kotlin code
          if [ -d source_code/app/src/main/java ]; then
            cp -r source_code/app/src/main/java/* apktool_decoded_temp/smali/ || true
          fi
        fi
        
    - name: Build APK with apktool
      run: |
        if [ -d apktool_decoded_temp ]; then
          apktool b apktool_decoded_temp -o TV_Home_unsigned.apk
        fi
        
    - name: Generate keystore
      run: |
        keytool -genkey -v \
          -keystore tvhome.keystore \
          -alias tvhome \
          -keyalg RSA \
          -keysize 2048 \
          -validity 10000 \
          -storepass android \
          -keypass android \
          -dname "CN=TV Home, OU=Development, O=TV Home, L=Seoul, ST=Seoul, C=KR"
          
    - name: Sign APK
      run: |
        if [ -f TV_Home_unsigned.apk ]; then
          jarsigner -verbose -sigalg SHA256withRSA -digestalg SHA-256 \
            -keystore tvhome.keystore \
            -storepass android \
            -keypass android \
            TV_Home_unsigned.apk tvhome
        fi
        
    - name: Zipalign APK
      run: |
        if [ -f TV_Home_unsigned.apk ]; then
          zipalign -v -p 4 TV_Home_unsigned.apk TV_Home.apk
        fi
        
    - name: Verify APK
      run: |
        if [ -f TV_Home.apk ]; then
          aapt dump badging TV_Home.apk | grep -E "package:|application-label:|launchable-activity:"
          ls -lh TV_Home.apk
        fi
        
    - name: Upload APK artifact
      uses: actions/upload-artifact@v4
      if: success()
      with:
        name: TV-Home-APK
        path: TV_Home.apk
        retention-days: 30
```

## 설정 단계

### 1. GitHub 저장소 설정
1. GitHub 저장소로 이동
2. **Settings** > **Actions** > **General** 클릭
3. **Workflow permissions** 섹션에서:
   - "Read and write permissions" 선택
   - "Allow GitHub Actions to create and approve pull requests" 체크

### 2. 워크플로우 파일 추가
웹 브라우저에서 직접 추가하는 방법:
1. GitHub 저장소로 이동
2. **Actions** 탭 클릭
3. **New workflow** 클릭
4. **set up a workflow yourself** 클릭
5. 위의 YAML 내용을 붙여넣기
6. 파일명을 `build-apk.yml`로 설정
7. **Commit changes** 클릭

또는 로컬에서 추가하는 방법:
```bash
# 디렉토리 생성
mkdir -p .github/workflows

# 파일 생성 (위의 YAML 내용 붙여넣기)
nano .github/workflows/build-apk.yml

# Git에 추가 및 커밋
git add .github/workflows/build-apk.yml
git commit -m "feat: Add GitHub Actions workflow for automatic APK builds"
git push origin main
```

### 3. 워크플로우 트리거
워크플로우는 다음 경우에 자동으로 실행됩니다:
- `source_code/` 또는 `apktool_decoded/` 디렉토리에 변경사항이 푸시될 때
- Pull Request가 생성되거나 업데이트될 때
- **Actions** 탭에서 수동으로 실행 (workflow_dispatch)

### 4. 빌드 결과 확인
1. **Actions** 탭에서 워크플로우 실행 상태 확인
2. 완료되면 **Artifacts** 섹션에서 빌드된 APK 다운로드

## 트러블슈팅

### 권한 오류
```
refusing to allow a GitHub App to create or update workflow
```
**해결방법:**
- 웹 브라우저에서 직접 워크플로우 파일 생성
- 또는 저장소 관리자에게 workflows 권한 요청

### 빌드 실패
**확인사항:**
1. Java 17이 올바르게 설치되었는지 확인
2. apktool 버전 호환성 확인
3. 원본 APK 파일이 저장소에 있는지 확인

### APK 서명 오류
**해결방법:**
- keystore 비밀번호 확인
- jarsigner 명령어 옵션 재확인

## 추가 기능

### 릴리스 자동 생성
APK를 GitHub Release로 자동 배포하려면 워크플로우에 다음 단계 추가:

```yaml
- name: Create Release
  uses: softprops/action-gh-release@v1
  if: startsWith(github.ref, 'refs/tags/')
  with:
    files: TV_Home.apk
  env:
    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

### 슬랙 알림
빌드 완료 시 슬랙 알림을 받으려면:

```yaml
- name: Slack Notification
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    text: APK Build ${{ job.status }}
    webhook_url: ${{ secrets.SLACK_WEBHOOK }}
  if: always()
```

## 참고 자료
- [GitHub Actions 공식 문서](https://docs.github.com/en/actions)
- [Android 빌드 가이드](https://developer.android.com/studio/build)
- [apktool 문서](https://ibotpeaches.github.io/Apktool/)

## 버전 정보
- 워크플로우 버전: 1.0
- 마지막 업데이트: 2025-12-25
- Android Target SDK: 34
- Java Version: 17
