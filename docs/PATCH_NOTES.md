# PJY TV 사용성 개선 패치 노트

## 수정 개요
이 패치는 즐겨찾기 채널 탐색 시 사용성을 개선합니다.

## 주요 수정 사항

### 1. 즐겨찾기 내 채널 이동 개선
**문제**: 즐겨찾기 채널 시청 중 위/아래 키를 누르면 전체 채널 목록을 기준으로 이동
**해결**: 즐겨찾기 모드일 때는 즐겨찾기 채널 목록 내에서만 이동

### 2. 메뉴 카테고리 기본값 개선
**문제**: 즐겨찾기 시청 중 왼쪽 키로 메뉴를 열면 전체 채널 카테고리가 기본 표시
**해결**: 즐겨찾기 시청 중이면 즐겨찾기 카테고리가 기본으로 표시

## 기술적 세부사항

### MainActivity.java 수정사항

#### 1. 즐겨찾기 모드 추적 변수 추가
```java
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

#### 4. 즐겨찾기 내 위치 찾기 헬퍼 메서드 추가
```java
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

#### 5. onKey() 메서드 수정 (왼쪽 키 처리)
```java
// KEYCODE_DPAD_LEFT (21) 처리 부분 수정
case 21:  // LEFT 키
    if (this.settingFragment.isHidden()) {
        // 즐겨찾기 모드일 때는 즐겨찾기 카테고리로 이동
        if (isInFavoriteMode) {
            TVList.INSTANCE.getGroupModel().setPosition(0);  // 즐겨찾기 카테고리
        }
        
        if (TVList.INSTANCE.getEpgReceived()) {
            showFragment(this.menuFragment);
        } else {
            Toast.makeText(this, getString(R.string.updating_please_wait), 1).show();
        }
    }
    return true;
```

## 테스트 시나리오

1. **즐겨찾기 내 채널 이동**
   - 즐겨찾기 카테고리 선택
   - 채널 재생
   - 위/아래 키로 채널 변경
   - 예상: 즐겨찾기에 등록된 채널들 사이에서만 이동

2. **메뉴 복귀 시 카테고리**
   - 즐겨찾기 채널 시청 중
   - 왼쪽 키로 메뉴 열기
   - 예상: 즐겨찾기 카테고리가 선택된 상태로 표시

3. **전체 채널 모드**
   - 전체 채널 또는 다른 카테고리 선택
   - 채널 재생
   - 위/아래 키로 채널 변경
   - 예상: 기존과 동일하게 전체 채널 목록에서 이동

## 빌드 방법

1. Android Studio에서 프로젝트 열기
2. Gradle Sync 실행
3. Build > Build Bundle(s) / APK(s) > Build APK(s)
4. 생성된 APK 파일을 기기에 설치

## 주의사항

- 이 패치는 디컴파일된 코드를 기반으로 작성되었습니다
- 원본 Kotlin 소스코드가 있다면 해당 코드를 수정하는 것이 더 안정적입니다
- APK 재빌드 시 서명 키가 필요할 수 있습니다
