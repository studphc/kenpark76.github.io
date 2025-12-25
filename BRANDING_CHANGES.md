# 🎨 브랜딩 변경사항

## 📋 변경 요약

**이전**: PJYTV  
**변경 후**: TV Home

---

## ✨ 변경된 내용

### 1. 앱 이름 변경
- **이전**: PJY TV
- **변경 후**: TV Home
- **파일**: `app/src/main/res/values/strings.xml`

### 2. 프로젝트 이름 변경
- **이전**: PJYTV
- **변경 후**: TVHome
- **파일**: `settings.gradle`

### 3. 새로운 아이콘 디자인 🎨

#### 디자인 컨셉
- **스타일**: 모던, 미니멀리즘
- **색상**: 그라디언트 블루 (진한 파랑 → 밝은 파랑)
- **모양**: 둥근 사각형 배경
- **심볼**: TV 화면 + 집(Home) 아이콘

#### 아이콘 구성요소
1. **배경**: 
   - 그라디언트 블루 (#1E64C8 → #64A0FF)
   - 둥근 모서리 (Corner Radius: 20%)

2. **TV 화면**:
   - 흰색 테두리
   - 밝은 파랑 내부 화면
   - 하단 스탠드

3. **홈 심볼**:
   - 흰색 집 모양
   - TV 화면 좌측 상단에 배치

#### 생성된 아이콘 파일
```
app/src/main/res/
├── mipmap-mdpi/ic_launcher.png       (48x48)
├── mipmap-hdpi/ic_launcher.png       (72x72)
├── mipmap-xhdpi/ic_launcher.png      (96x96)
├── mipmap-xxhdpi/ic_launcher.png     (144x144)
├── mipmap-xxxhdpi/ic_launcher.png    (192x192)
├── drawable/logo0.png                (192x192)
└── drawable/banner0.png              (320x180 - Android TV 배너)
```

### 4. AndroidManifest 업데이트
- **아이콘 참조**: `@drawable/logo0` → `@mipmap/ic_launcher`
- **배너**: 새로운 "TV Home" 배너 이미지
- **로고**: 새로운 "TV Home" 로고 이미지

---

## 🖼️ 아이콘 미리보기

아이콘 디자인을 확인하려면 `TV_HOME_ICON_PREVIEW.png` 파일을 참조하세요.

**아이콘 특징**:
- ✅ 깔끔하고 현대적인 디자인
- ✅ Android TV에 최적화
- ✅ 모든 해상도 지원 (mdpi ~ xxxhdpi)
- ✅ 투명 배경 지원 (PNG with alpha)
- ✅ Material Design 가이드라인 준수

---

## 📱 Android TV 배너

**크기**: 320x180 픽셀  
**디자인**:
- 왼쪽: TV + 홈 아이콘
- 오른쪽: "TV Home" 텍스트
- 배경: 그라디언트 블루

---

## 🔧 기술적 변경사항

### strings.xml
```xml
<!-- 이전 -->
<string name="app_name">PJY TV</string>
<string name="app_URL">PJY TV (kenpark76.github.io)</string>

<!-- 변경 후 -->
<string name="app_name">TV Home</string>
<string name="app_URL">TV Home (kenpark76.github.io)</string>
```

### settings.gradle
```gradle
// 이전
rootProject.name = "PJYTV"

// 변경 후
rootProject.name = "TVHome"
```

### AndroidManifest.xml
```xml
<!-- 이전 -->
android:icon="@drawable/logo0"

<!-- 변경 후 -->
android:icon="@mipmap/ic_launcher"
```

---

## ✅ 테스트 체크리스트

빌드 후 다음 사항을 확인하세요:

- [ ] 앱 아이콘이 "TV Home" 디자인으로 표시됨
- [ ] 앱 이름이 "TV Home"으로 표시됨
- [ ] Android TV 런처에서 배너가 올바르게 표시됨
- [ ] 설정 > 앱에서 아이콘이 올바르게 표시됨
- [ ] 최근 앱 목록에서 아이콘이 올바르게 표시됨

---

## 🎨 색상 팔레트

앱에서 사용된 주요 색상:

| 색상 | HEX | RGB | 용도 |
|------|-----|-----|------|
| 진한 파랑 | #1E64C8 | (30, 100, 200) | 배경 그라디언트 시작 |
| 밝은 파랑 | #64A0FF | (100, 160, 255) | 배경 그라디언트 끝 |
| 밝은 하늘색 | #64B4FF | (100, 180, 255) | TV 화면 내부 |
| 흰색 | #FFFFFF | (255, 255, 255) | TV 테두리, 홈 아이콘 |

---

## 📐 디자인 가이드라인

### 아이콘 크기 가이드라인
- **mdpi**: 48x48 (1x)
- **hdpi**: 72x72 (1.5x)
- **xhdpi**: 96x96 (2x)
- **xxhdpi**: 144x144 (3x)
- **xxxhdpi**: 192x192 (4x)

### 안전 영역
- **패딩**: 전체 크기의 10%
- **아이콘 중심 영역**: 전체 크기의 80%

### Android TV 배너
- **크기**: 320x180 픽셀
- **비율**: 16:9
- **포맷**: PNG with alpha

---

## 🔄 롤백 방법

이전 브랜딩으로 돌아가려면:

```bash
# Git에서 이전 커밋으로 복원
git checkout HEAD~1 -- app/src/main/res/values/strings.xml
git checkout HEAD~1 -- settings.gradle
git checkout HEAD~1 -- app/src/main/res/mipmap-*
git checkout HEAD~1 -- app/src/main/res/drawable/logo0.png
git checkout HEAD~1 -- app/src/main/res/drawable/banner0.png
```

---

## 📝 추가 참고사항

1. **패키지 이름**: `com.pjy.koreatv` (변경하지 않음)
   - 패키지 이름 변경은 기존 사용자의 앱 업데이트에 영향을 줄 수 있음
   - 내부 구조는 유지하고 외부 표시만 변경

2. **버전 정보**: `3.0.0` (유지)
   - 브랜딩 변경은 마이너 업데이트로 처리 가능
   - 필요시 `3.1.0`으로 업데이트 고려

3. **호환성**: Android 5.0 (API 21) 이상
   - 모든 Android TV 및 스마트폰에서 작동
   - 기존 사용자 데이터 유지

---

## 🎉 완료!

브랜딩이 "TV Home"으로 성공적으로 변경되었습니다!

**변경 요약**:
- ✅ 앱 이름: TV Home
- ✅ 프로젝트 이름: TVHome
- ✅ 새로운 아이콘 디자인
- ✅ 새로운 Android TV 배너
- ✅ 모든 해상도 지원

**다음 단계**: Android Studio에서 APK 빌드 및 테스트

---

**마지막 업데이트**: 2025-12-25  
**작성자**: AI Assistant
