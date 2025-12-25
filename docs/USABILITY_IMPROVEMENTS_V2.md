# PJYTV 앱 사용성 개선 v2.0

## 📋 개선 내용 요약

### ✅ 완료된 개선사항 (v1.0)
1. **즐겨찾기 채널 내 탐색 개선**
   - 즐겨찾기 카테고리에서 위/아래 키로 이동 시 즐겨찾기 채널 내에서만 이동
   - 전체 채널 목록으로 넘어가지 않음

2. **메뉴 카테고리 기본 선택 개선**  
   - 즐겨찾기 채널 시청 중 메뉴(왼쪽 키) 열 때 즐겨찾기 카테고리가 기본 선택됨
   - 기존: 전체 채널 카테고리로 이동
   - 개선: 즐겨찾기 카테고리 유지

### 🆕 추가 개선사항 (v2.0)

#### 1. 즐겨찾기 빈 목록 처리
**문제**: 즐겨찾기가 비어있을 때 채널 이동 시 예외 발생 가능성

**해결책**:
```java
// MainActivity.java의 prev() / next() 메서드에서
if (favoriteList != null && favoriteList.size() > 0) {
    // 즐겨찾기 내 이동
} else {
    // 즐겨찾기가 비어있으면 토스트 메시지 표시
    Toast.makeText(this, "즐겨찾기에 채널이 없습니다", Toast.LENGTH_SHORT).show();
    return;
}
```

#### 2. 즐겨찾기 모드 시각적 표시
**제안**: 즐겨찾기 모드일 때 UI에 표시

**구현 방법**:
- `InfoFragment` 또는 `ChannelFragment`에 "⭐ 즐겨찾기" 아이콘/텍스트 추가
- `isInFavoriteMode` 상태에 따라 표시/숨김

#### 3. 채널 번호 입력 시 즐겨찾기 우선
**제안**: 숫자 키로 채널 직접 입력 시 즐겨찾기 채널 우선 검색

**현재 동작**: 전체 채널에서 검색
**개선안**: 즐겨찾기 모드일 때 즐겨찾기 채널에서 먼저 검색

## 🔧 구현된 코드 수정사항

### MainActivity.java

#### 1. 즐겨찾기 모드 추적 변수
```java
// Line 70
private boolean isInFavoriteMode = false;
```

#### 2. prev() 메서드 수정
```java
public final void prev() {
    int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
    
    // 즐겨찾기 모드일 때는 즐겨찾기 채널 내에서만 이동
    if (groupIndex == 0) {  // 0 = 즐겨찾기 카테고리
        isInFavoriteMode = true;
        TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
        if (favoriteList != null && favoriteList.size() > 0) {
            int currentPos = findCurrentPositionInFavorites();
            int newPos = currentPos - 1;
            if (newPos < 0) {
                newPos = favoriteList.size() - 1;
            }
            TVList.INSTANCE.setPosition(favoriteList.getTv(newPos).getId());
            return;
        }
    } else {
        isInFavoriteMode = false;
    }
    
    // 기존 로직 (전체 채널 이동)
    Integer value = TVList.INSTANCE.getPosition().getValue();
    int intValue = value != null ? value.intValue() - 1 : 0;
    if (intValue == -1) {
        intValue = TVList.INSTANCE.size() - 1;
    }
    TVList.INSTANCE.setPosition(intValue);
    int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
    if (groupIndex2 != groupIndex) {
        this.menuFragment.updateList(groupIndex2);
    }
}
```

#### 3. next() 메서드 수정
```java
public final void next() {
    int groupIndex = TVList.INSTANCE.getTVModel().getGroupIndex();
    
    // 즐겨찾기 모드일 때는 즐겨찾기 채널 내에서만 이동
    if (groupIndex == 0) {  // 0 = 즐겨찾기 카테고리
        isInFavoriteMode = true;
        TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
        if (favoriteList != null && favoriteList.size() > 0) {
            int currentPos = findCurrentPositionInFavorites();
            int newPos = currentPos + 1;
            if (newPos >= favoriteList.size()) {
                newPos = 0;
            }
            TVList.INSTANCE.setPosition(favoriteList.getTv(newPos).getId());
            return;
        }
    } else {
        isInFavoriteMode = false;
    }
    
    // 기존 로직 (전체 채널 이동)
    Integer value = TVList.INSTANCE.getPosition().getValue();
    int intValue = value != null ? value.intValue() + 1 : 0;
    TVList.INSTANCE.setPosition(intValue != TVList.INSTANCE.size() ? intValue : 0);
    int groupIndex2 = TVList.INSTANCE.getTVModel().getGroupIndex();
    if (groupIndex2 != groupIndex) {
        this.menuFragment.updateList(groupIndex2);
    }
}
```

#### 4. 즐겨찾기 위치 찾기 헬퍼 메서드
```java
// Line 554
private int findCurrentPositionInFavorites() {
    TVListModel favoriteList = TVList.INSTANCE.getGroupModel().getTVListModel(0);
    if (favoriteList == null) return 0;
    
    int currentId = TVList.INSTANCE.getTVModel().getTv().getId();
    for (int i = 0; i < favoriteList.size(); i++) {
        if (favoriteList.getTv(i).getId() == currentId) {
            return i;
        }
    }
    return 0;
}
```

#### 5. LEFT 키 이벤트 처리 수정 (onKeyDown)
```java
// Line 806 - 메뉴 열 때 즐겨찾기 카테고리로 이동
if (r3.isInFavoriteMode) {
    com.pjy.koreatv.models.TVList.INSTANCE.getGroupModel().setPosition(0);
}
```

## ✅ 테스트 시나리오

### 시나리오 1: 즐겨찾기 채널 탐색
1. 즐겨찾기 카테고리에서 채널 선택
2. 위/아래 키로 채널 이동
3. **예상 결과**: 즐겨찾기 채널 내에서만 순환 이동

### 시나리오 2: 메뉴 열기
1. 즐겨찾기 채널 시청 중
2. 왼쪽 키 눌러 메뉴 열기
3. **예상 결과**: 즐겨찾기 카테고리가 선택된 상태로 메뉴 표시

### 시나리오 3: 일반 채널로 전환
1. 메뉴에서 다른 카테고리(예: 지상파) 선택
2. 채널 선택 후 위/아래 키 이동
3. **예상 결과**: 전체 채널 목록에서 이동 (기존 동작)

### 시나리오 4: 즐겨찾기 빈 목록
1. 즐겨찾기에 채널이 없는 상태
2. 즐겨찾기 카테고리 선택
3. 위/아래 키 눌러 이동 시도
4. **예상 결과**: 에러 없이 정상 동작 또는 안내 메시지

## 📱 빌드 및 테스트 방법

### 1. Android Studio에서 빌드
```bash
# 프로젝트 열기
cd source_code
# Android Studio에서 열기: File > Open > source_code 폴더 선택

# Gradle 동기화
# Gradle Sync 완료 대기

# Debug APK 빌드
./gradlew assembleDebug

# 생성된 APK 위치
# source_code/app/build/outputs/apk/debug/app-debug.apk
```

### 2. APK 설치 및 테스트
```bash
# Android TV/박스에 ADB 연결
adb connect <TV_IP>:5555

# APK 설치
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 앱 실행
adb shell am start -n com.pjy.koreatv/.MainActivity
```

### 3. 로그 확인
```bash
# 앱 로그 모니터링
adb logcat | grep "MainActivity1"
```

## 🐛 알려진 제한사항

1. **디컴파일 코드 기반**: JADX로 디컴파일한 코드이므로 일부 구조가 원본과 다를 수 있음
2. **Goto 레이블**: 디컴파일 과정에서 생성된 goto 구문이 포함되어 있어 가독성이 낮음
3. **빌드 테스트 필요**: 실제 컴파일 및 실행 테스트 필요

## 🔄 향후 개선 계획

### 단기 (즉시 적용 가능)
- [ ] 즐겨찾기 빈 목록 처리 추가
- [ ] 즐겨찾기 모드 시각적 표시
- [ ] 버그 수정 및 안정성 개선

### 중기 (리팩토링 필요)
- [ ] Kotlin으로 완전 변환
- [ ] 코드 구조 정리 (goto 제거)
- [ ] 단위 테스트 추가

### 장기 (기능 추가)
- [ ] 즐겨찾기 그룹 기능 (예: 즐겨찾기 1, 2, 3)
- [ ] 채널 순서 변경 기능
- [ ] 즐겨찾기 백업/복원 기능

## 📞 지원 및 문의

이 패치는 PJYTV 앱의 사용성 개선을 위한 커뮤니티 기여입니다.

**주의사항**:
- 이 수정사항은 디컴파일된 코드를 기반으로 함
- 원저작자의 권리를 존중하며 개인 사용 목적으로만 사용하세요
- 상업적 용도로 사용하지 마세요

---

**마지막 업데이트**: 2025-12-25  
**버전**: v2.0  
**작성자**: AI Assistant
