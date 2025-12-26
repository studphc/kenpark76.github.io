# TV Home APK 설치 문제 진단 가이드

## 완전히 수정된 APK (v3.0.0)

### ✅ 완료된 수정 사항
1. **패키지명 변경**: `com.pjy.koreatv` → `com.tvhome.app`
2. **smali 코드 완전 변경**: 15,297+ 파일의 모든 클래스 경로 수정
3. **디렉토리 구조 재구성**: `smali*/com/pjy/koreatv/` → `smali*/com/tvhome/app/`
4. **AndroidManifest.xml 업데이트**: 모든 권한 및 프로바이더 경로 변경

### 📥 다운로드 링크
```
https://github.com/studphc/kenpark76.github.io/raw/feature/favorite-navigation-fix/TV_Home.apk
```

## 🔍 설치 실패 시 로그 확인 방법

### 방법 1: ADB를 통한 설치 및 로그 확인 (권장)

#### 1단계: ADB 연결
```bash
# PC에 Android 디바이스 연결 (USB 디버깅 활성화 필요)
adb devices
```

#### 2단계: 기존 앱 확인 및 삭제 (필요시)
```bash
# 기존 PJYTV 앱 확인
adb shell pm list packages | grep -E "pjy|tvhome"

# 필요시 삭제
adb uninstall com.pjy.koreatv
adb uninstall com.tvhome.app
```

#### 3단계: APK 설치 및 로그 확인
```bash
# 실시간 로그 모니터링 시작
adb logcat | grep -i "packageinstaller\|packagemanager"

# 다른 터미널에서 APK 설치
adb install -r TV_Home.apk

# 또는 자세한 에러 출력
adb install -r -d TV_Home.apk 2>&1
```

#### 4단계: 설치 실패 시 상세 로그
```bash
# 패키지 매니저 로그
adb logcat PackageManager:V *:S

# 설치 관련 전체 로그
adb logcat | grep -E "INSTALL|FAILED|Error"
```

### 방법 2: 디바이스에서 직접 확인

#### 설정에서 확인
1. **설정** → **앱 및 알림** → **앱 정보**
2. 이미 설치된 앱 목록 확인
3. 동일 패키지명 앱이 있는지 확인

#### 파일 관리자에서 설치 시
1. APK 파일 다운로드 완료 확인
2. 파일 크기: **11MB** 정확히 다운로드되었는지 확인
3. "알 수 없는 출처" 설치 허용
4. 에러 메시지 스크린샷 캡처

### 방법 3: 개발자 옵션 활성화

#### Android 설정
1. **설정** → **디바이스 정보** → **빌드 번호** 7번 탭
2. **개발자 옵션** 활성화
3. **USB 디버깅** 활성화
4. **앱 설치 확인** 비활성화
5. **알 수 없는 출처 허용** 활성화

## 🐛 일반적인 설치 오류 및 해결 방법

### 1. INSTALL_FAILED_INVALID_APK
**원인**: APK 파일이 손상되었거나 불완전
**해결**: 
- APK 재다운로드
- 파일 크기 확인 (정확히 11MB)
- MD5/SHA256 체크섬 확인

### 2. INSTALL_FAILED_UPDATE_INCOMPATIBLE
**원인**: 이전 버전과 서명이 다름
**해결**:
```bash
adb uninstall com.pjy.koreatv
adb install TV_Home.apk
```

### 3. INSTALL_FAILED_INSUFFICIENT_STORAGE
**원인**: 저장 공간 부족
**해결**: 최소 50MB 이상 확보

### 4. INSTALL_PARSE_FAILED_MANIFEST_MALFORMED
**원인**: AndroidManifest.xml 문제
**해결**: 개발자에게 보고 (이 APK에서는 수정 완료)

### 5. INSTALL_FAILED_VERIFICATION_FAILURE
**원인**: 서명 검증 실패
**해결**:
- Play Protect 일시 비활성화
- 보안 설정에서 "앱 확인" 비활성화

## 📊 APK 정보 확인

### ADB로 APK 정보 확인
```bash
# 패키지 정보
adb shell pm dump com.tvhome.app

# APK 경로 확인
adb shell pm path com.tvhome.app

# 설치된 버전 확인
adb shell dumpsys package com.tvhome.app | grep version
```

### PC에서 APK 분석
```bash
# APK 서명 확인
keytool -printcert -jarfile TV_Home.apk

# APK 내용 확인
unzip -l TV_Home.apk | grep -E "manifest|classes.dex"
```

## 🔬 고급 진단

### APK 서명 검증
```bash
jarsigner -verify -verbose -certs TV_Home.apk
```

### APK 구조 검증
```bash
aapt dump badging TV_Home.apk
aapt dump permissions TV_Home.apk
aapt dump configurations TV_Home.apk
```

### Smali 코드 검증
```bash
# APK 디코딩
apktool d TV_Home.apk -o verify_apk

# 패키지 구조 확인
ls -la verify_apk/smali*/com/tvhome/app/

# MainActivity 클래스 확인
grep "^.class" verify_apk/smali*/com/tvhome/app/MainActivity.smali
```

## 📝 에러 보고 시 포함할 정보

1. **Android 버전**: Settings → About phone
2. **디바이스 모델**: 제조사 및 모델명
3. **설치 방법**: 파일 관리자 / ADB / 기타
4. **에러 메시지**: 정확한 에러 텍스트 또는 스크린샷
5. **logcat 로그**: 
```bash
adb logcat -d > install_error.log
```
6. **이전 앱 설치 여부**: PJYTV 앱 설치 이력
7. **파일 크기**: 다운로드된 APK 정확한 크기

## 🎯 최종 확인 사항

### 설치 전 체크리스트
- [ ] APK 파일 크기: 11MB (정확히)
- [ ] 저장 공간: 50MB 이상
- [ ] 알 수 없는 출처: 허용됨
- [ ] 기존 PJYTV: 삭제 또는 없음
- [ ] USB 디버깅: 활성화 (ADB 사용 시)
- [ ] Play Protect: 일시 비활성화 (필요 시)

### 설치 후 확인
- [ ] 앱 이름: "TV Home"으로 표시
- [ ] 아이콘: 파란색 그라데이션 TV+집 디자인
- [ ] 패키지명: `com.tvhome.app`
- [ ] 버전: 3.0.0
- [ ] 즐겨찾기 네비게이션: 정상 작동

## 🆘 추가 도움이 필요한 경우

### 상세 로그 수집
```bash
# 전체 시스템 로그
adb logcat -d > full_log.txt

# 설치 관련 로그만
adb logcat -d | grep -i install > install_log.txt

# 에러 로그만
adb logcat -d | grep -E "Error|Exception|Failed" > error_log.txt
```

이 로그 파일들을 개발자에게 전송하세요.

---

**업데이트**: 2025-12-25  
**APK 버전**: 3.0.0  
**패키지명**: com.tvhome.app  
**파일 크기**: 11MB (11,388,005 bytes)
