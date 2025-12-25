# PJY TV 보안 검토 리포트

**검토 일자**: 2025-12-25  
**앱 버전**: 3.0.0  
**검토 방법**: APK 디컴파일 및 소스코드 분석

---

## 🔍 종합 평가

**전체 위험도**: ⚠️ **중간** (Medium Risk)

이 앱은 피싱이나 스캠 프로그램이 **아니며**, 악의적인 데이터 유출도 발견되지 않았습니다. 다만 일부 권한 및 보안 설정에서 개선이 필요한 부분이 있습니다.

---

## ✅ 안전한 부분

### 1. 개인정보 수집 없음
- ✅ **전화번호, IMEI, 디바이스 ID 수집 코드 없음**
- ✅ 연락처, SMS, 통화 기록 접근 코드 없음
- ✅ 위치 정보 수집 코드 없음
- ✅ 카메라, 마이크 접근 코드 없음

### 2. 네트워크 통신 투명성
모든 네트워크 통신은 공개된 GitHub 저장소로만 이루어집니다:

```
✅ https://kenpark76.github.io/koreatv.json (채널 목록)
✅ https://kenpark76.github.io/koreatvEPG.xml (EPG 데이터)
✅ https://kenpark76.github.io/message/message_ko.txt (공지사항)
✅ https://www.gitlink.org.cn/lizongying/my-tv-0/ (오픈소스 프로젝트)
```

### 3. 데이터 전송 없음
- ✅ 사용자 데이터를 외부 서버로 전송하는 코드 없음
- ✅ 분석 도구(Analytics) 없음
- ✅ 광고 SDK 없음
- ✅ 추적(Tracking) 코드 없음

### 4. 악성 코드 패턴 없음
- ✅ 루팅 감지/우회 코드 없음
- ✅ 암호화된 페이로드 없음
- ✅ 난독화된 악성 코드 없음
- ✅ 원격 코드 실행(RCE) 패턴 없음

---

## ⚠️ 주의가 필요한 부분

### 1. 과도한 권한 요청

#### 🔴 REQUEST_INSTALL_PACKAGES (APK 설치 권한)
```xml
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```
- **위험도**: 중간
- **사유**: 자동 업데이트 기능을 위한 권한
- **우려사항**: 악용 시 사용자 동의 없이 다른 앱 설치 가능
- **실제 사용**: `UpdateManager.java`에서 APK 다운로드 및 설치에 사용
- **권장**: 이 권한이 없어도 수동 업데이트는 가능함

#### 🟡 RECEIVE_BOOT_COMPLETED (부팅 시 자동 실행)
```xml
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```
- **위험도**: 낮음
- **사유**: TV 앱 특성상 부팅 시 자동 실행이 필요할 수 있음
- **실제 사용**: `BootReceiver.java`에서 부팅 이벤트 수신

#### 🟡 WRITE_EXTERNAL_STORAGE / READ_EXTERNAL_STORAGE
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
- **위험도**: 낮음
- **사유**: 채널 목록 및 설정 저장
- **실제 사용**: 로컬 캐시 저장 (`channels.txt` 등)

### 2. 보안 설정 취약점

#### 🔴 SSL 인증서 검증 비활성화
```java
// HttpClient.java - getUnsafeOkHttpClient()
public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
    // 비어있음 - 모든 인증서 허용
}

public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
    // 비어있음 - 모든 인증서 허용
}
```
- **위험도**: 높음
- **문제**: Man-in-the-Middle (MITM) 공격에 취약
- **사유**: 일부 스트리밍 서버가 자체 서명 인증서를 사용
- **권장**: 신뢰할 수 있는 인증서만 허용하도록 수정

#### 🟡 cleartext traffic 허용
```xml
android:usesCleartextTraffic="true"
```
- **위험도**: 중간
- **문제**: HTTP(암호화되지 않은) 통신 허용
- **사유**: 일부 스트리밍 URL이 HTTP 사용
- **실제 영향**: 제3자가 트래픽 엿볼 수 있음

### 3. 외부 데이터 소스

#### 🟡 타오바오 API 시간 동기화
```java
// Utils.java - getTimestampFromServer()
"https://api.m.taobao.com/rest/api3.do?api=mtop.common.getTimestamp"
```
- **위험도**: 낮음
- **목적**: 서버 시간 동기화 (EPG 시간 정확도)
- **데이터 전송**: 없음 (시간만 받음)
- **우려사항**: 중국 서버 의존성

---

## 📊 네트워크 통신 전체 분석

### 신뢰 가능한 통신
| 목적 | URL | 데이터 전송 | 안전성 |
|------|-----|------------|--------|
| 채널 목록 | `kenpark76.github.io/koreatv.json` | 없음 | ✅ 안전 |
| EPG 데이터 | `kenpark76.github.io/koreatvEPG.xml` | 없음 | ✅ 안전 |
| 공지사항 | `kenpark76.github.io/message/*.txt` | 없음 | ✅ 안전 |
| 오픈소스 | `gitlink.org.cn/lizongying/my-tv-0/` | 없음 | ✅ 안전 |
| 시간 동기화 | `api.m.taobao.com` (타오바오) | 없음 | ⚠️ 주의 |

### 스트리밍 URL
- 동적으로 `koreatv.json`에서 로드
- 한국, 중국, 영어 채널 200개 이상
- HLS (.m3u8) 프로토콜 사용

---

## 🛡️ 권장 사항

### 즉시 개선 필요
1. ❌ **SSL 인증서 검증 활성화**
   ```java
   // HttpClient.java 수정 필요
   // 신뢰할 수 있는 인증서만 허용
   ```

2. ❌ **불필요한 권한 제거**
   - `REQUEST_INSTALL_PACKAGES` 제거 (수동 업데이트 사용)
   - 또는 사용자에게 명확한 안내 제공

### 장기 개선 사항
3. ⚠️ **cleartext traffic 제한**
   - HTTPS 스트리밍 URL만 허용
   - Network Security Config 강화

4. ⚠️ **코드 서명 및 무결성 검증**
   - APK 서명 검증 추가
   - 업데이트 파일 해시 검증

---

## 🔐 최종 결론

### 이 앱은 안전한가?

**예, 기본적으로 안전합니다.**

#### ✅ 안전한 이유:
1. 개인정보 수집 없음
2. 악성 코드 패턴 없음
3. 데이터 유출 시도 없음
4. 오픈소스 기반 (lizongying/my-tv-0)
5. 투명한 네트워크 통신

#### ⚠️ 개선 필요:
1. SSL 인증서 검증 활성화
2. 불필요한 권한 제거
3. 보안 설정 강화

### 사용 권장 사항

**✅ 안전하게 사용 가능:**
- TV 스트리밍 시청
- 즐겨찾기 관리
- EPG 정보 확인

**⚠️ 주의사항:**
- 자동 업데이트 기능 사용 시 주의
- 공개 Wi-Fi에서 사용 시 VPN 권장 (MITM 공격 방지)
- APK 설치 권한 요청 시 거부 가능 (수동 업데이트 선택)

---

## 📋 상세 파일 분석

### 주요 파일 검토 결과

| 파일 | 위험도 | 비고 |
|------|--------|------|
| `MainActivity.java` | ✅ 안전 | 악성 코드 없음 |
| `HttpClient.java` | ⚠️ 중간 | SSL 검증 비활성화 |
| `UpdateManager.java` | ⚠️ 중간 | APK 설치 권한 사용 |
| `TVList.java` | ✅ 안전 | 로컬 데이터만 처리 |
| `Utils.java` | ⚠️ 낮음 | 타오바오 시간 API 사용 |

---

## 🔎 추가 검증 방법

사용자가 직접 확인할 수 있는 방법:

1. **네트워크 모니터링**
   ```bash
   # Android Studio의 Network Profiler 사용
   # 또는 Wireshark로 트래픽 분석
   ```

2. **권한 확인**
   ```
   설정 > 앱 > PJY TV > 권한
   ```

3. **로그 확인**
   ```bash
   adb logcat | grep "pjy.koreatv"
   ```

---

**검토자**: Claude (AI Security Analyst)  
**마지막 업데이트**: 2025-12-25
