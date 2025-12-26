# 보안 개선 가이드

이 문서는 PJYTV 앱의 보안을 강화하기 위한 개선사항을 설명합니다.

## ⚠️ 현재 보안 취약점

### 1. SSL 인증서 검증 비활성화 (심각)
**파일**: `HttpClient.java`  
**위험도**: 🔴 높음  
**문제**: Man-in-the-Middle (MITM) 공격에 취약

### 2. 불필요한 권한 (중간)
**파일**: `AndroidManifest.xml`  
**위험도**: 🟡 중간  
**문제**: APK 설치 권한 남용 가능성

### 3. cleartext traffic 허용 (중간)
**파일**: `AndroidManifest.xml`  
**위험도**: 🟡 중간  
**문제**: 암호화되지 않은 HTTP 통신 가능

---

## ✅ 권장 보안 개선사항

### 개선 1: SSL 인증서 검증 활성화

#### 현재 코드 (취약):
```java
// HttpClient.java - line 131-136
@Override
public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
    // 비어있음 - 모든 인증서 허용
}

@Override
public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
    // 비어있음 - 모든 인증서 허용
}
```

#### 개선된 코드:
```java
@Override
public void checkClientTrusted(X509Certificate[] chain, String authType) 
    throws CertificateException {
    // 시스템 기본 검증 사용
    defaultTrustManager.checkClientTrusted(chain, authType);
}

@Override
public void checkServerTrusted(X509Certificate[] chain, String authType) 
    throws CertificateException {
    // 시스템 기본 검증 사용
    defaultTrustManager.checkServerTrusted(chain, authType);
}
```

#### 완전한 구현:
```java
// HttpClient.java에 추가
private final OkHttpClient getSafeOkHttpClient() {
    try {
        // 시스템 기본 TrustManager 사용
        TrustManagerFactory trustManagerFactory = 
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers");
        }
        
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] { trustManager }, new SecureRandom());
        
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
            .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
            .connectionSpecs(Arrays.asList(
                ConnectionSpec.MODERN_TLS,
                ConnectionSpec.COMPATIBLE_TLS
            ));
        
        // 프록시 설정 (기존 로직 유지)
        if (!SP.INSTANCE.getProxy().isEmpty()) {
            Uri proxyUri = Uri.parse(SP.INSTANCE.getProxy());
            builder.proxy(new Proxy(Proxy.Type.HTTP, 
                new InetSocketAddress(proxyUri.getHost(), proxyUri.getPort())));
        }
        
        return enableTls12OnPreLollipop(builder).build();
    } catch (Exception e) {
        throw new RuntimeException("Failed to create safe OkHttpClient", e);
    }
}
```

**변경 방법**:
1. `getUnsafeOkHttpClient()` 메서드명을 `getSafeOkHttpClient()`로 변경
2. 위 코드로 전체 교체
3. 주석에서 "Unsafe" 관련 내용 제거

---

### 개선 2: 불필요한 권한 제거

#### AndroidManifest.xml 수정:

**제거할 권한**:
```xml
<!-- ❌ 제거 권장 - 자동 업데이트 기능이 필요하지 않다면 -->
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
```

**제거 이유**:
- 이 권한은 앱이 다른 APK를 설치할 수 있게 허용
- 악용 시 사용자 동의 없이 악성 앱 설치 가능
- 수동 업데이트로도 충분히 앱 업데이트 가능

**대안**:
사용자가 직접 APK를 다운로드하고 설치하도록 변경
```kotlin
// UpdateManager.kt 수정
fun downloadUpdate(url: String) {
    // APK 다운로드만 수행
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val request = DownloadManager.Request(Uri.parse(url))
    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "PJYTV.apk")
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    downloadManager.enqueue(request)
    
    // 설치는 사용자가 직접
    Toast.makeText(context, "다운로드 완료 후 파일을 탭하여 설치하세요", Toast.LENGTH_LONG).show()
}
```

---

### 개선 3: Network Security Config 추가

#### 1단계: 파일 생성

**파일 경로**: `app/src/main/res/xml/network_security_config.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <!-- 기본 설정: HTTPS만 허용 -->
    <base-config cleartextTrafficPermitted="false">
        <trust-anchors>
            <!-- 시스템 인증서만 신뢰 -->
            <certificates src="system" />
        </trust-anchors>
    </base-config>
    
    <!-- 신뢰할 수 있는 도메인 화이트리스트 -->
    <domain-config cleartextTrafficPermitted="false">
        <!-- GitHub Pages (메인 데이터 소스) -->
        <domain includeSubdomains="true">kenpark76.github.io</domain>
        <domain includeSubdomains="true">github.io</domain>
        
        <!-- GitLink (오픈소스 저장소) -->
        <domain includeSubdomains="true">gitlink.org.cn</domain>
        
        <!-- 로컬 테스트용 (개발 환경에서만) -->
        <domain includeSubdomains="true">localhost</domain>
        <domain includeSubdomains="true">127.0.0.1</domain>
        <domain includeSubdomains="true">10.0.2.2</domain>
    </domain-config>
    
    <!-- 스트리밍 URL용 설정 (HTTP 허용 필요시) -->
    <domain-config cleartextTrafficPermitted="true">
        <!-- 여기에 HTTP 스트리밍을 사용하는 도메인만 추가 -->
        <!-- 예: <domain includeSubdomains="true">stream-server.example.com</domain> -->
    </domain-config>
</network-security-config>
```

#### 2단계: AndroidManifest.xml 수정

```xml
<application
    android:name="com.pjy.koreatv.MyTVApplication"
    ...
    android:networkSecurityConfig="@xml/network_security_config"
    android:usesCleartextTraffic="false">  <!-- true → false 변경 -->
    ...
</application>
```

**효과**:
- ✅ HTTPS 통신 강제 (MITM 공격 방지)
- ✅ 신뢰할 수 있는 도메인만 허용
- ✅ 안전하지 않은 HTTP 차단

---

### 개선 4: ProGuard 활성화 (코드 난독화)

#### app/build.gradle 수정:

```gradle
buildTypes {
    release {
        minifyEnabled true          // false → true
        shrinkResources true         // 추가
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 
                      'proguard-rules.pro'
    }
}
```

**효과**:
- 🔒 코드 난독화로 리버스 엔지니어링 방지
- 📉 APK 크기 30-40% 감소
- ⚡ 성능 최적화

---

## 🚀 적용 우선순위

### 즉시 적용 (필수)
1. ✅ SSL 인증서 검증 활성화
2. ✅ Network Security Config 추가

### 권장 (선택)
3. ⚠️ REQUEST_INSTALL_PACKAGES 권한 제거
4. ⚠️ ProGuard 활성화

---

## 📋 적용 체크리스트

### SSL 인증서 검증
- [ ] `HttpClient.java`의 `getUnsafeOkHttpClient()` 메서드 수정
- [ ] `checkServerTrusted()` 메서드에 실제 검증 로직 추가
- [ ] 테스트: HTTPS 스트리밍 정상 작동 확인

### Network Security Config
- [ ] `res/xml/network_security_config.xml` 파일 생성
- [ ] `AndroidManifest.xml`에 설정 추가
- [ ] `usesCleartextTraffic`을 false로 변경
- [ ] 테스트: 모든 네트워크 통신 정상 확인

### 권한 제거
- [ ] `AndroidManifest.xml`에서 `REQUEST_INSTALL_PACKAGES` 제거
- [ ] `UpdateManager.kt` 수정 (자동 설치 → 다운로드만)
- [ ] 테스트: 수동 업데이트 프로세스 확인

### ProGuard
- [ ] `build.gradle`에서 `minifyEnabled = true` 설정
- [ ] `proguard-rules.pro` 검증
- [ ] 테스트: Release APK 빌드 및 실행 확인

---

## 🧪 테스트 가이드

### 1. SSL 검증 테스트
```bash
# 유효한 인증서 테스트
adb logcat | grep "SSL\|Certificate"

# 예상 결과: 유효한 HTTPS 연결만 허용됨
```

### 2. Network Security Config 테스트
```bash
# HTTP 연결 시도 로그 확인
adb logcat | grep "cleartext"

# 예상 결과: 허용된 도메인만 통신 가능
```

### 3. APK 무결성 테스트
```bash
# APK 서명 확인
jarsigner -verify -verbose app-release.apk

# ProGuard 적용 확인
aapt dump badging app-release.apk | grep "version"
```

---

## ⚠️ 주의사항

### 호환성 문제
일부 스트리밍 서버가 HTTP를 사용하는 경우:
- Option 1: 해당 서버를 HTTPS로 마이그레이션 (권장)
- Option 2: Network Security Config의 `cleartextTrafficPermitted` 도메인에 추가

### 업데이트 영향
권한 제거 시:
- 기존 사용자는 앱 업데이트 가능
- 자동 설치 기능은 사라짐
- 사용자는 다운로드 폴더에서 APK를 수동 설치

---

## 📞 지원

문제 발생 시:
1. **GitHub Issues**: 버그 리포트
2. **보안 이슈**: 비공개로 직접 연락
3. **문서 확인**: BUILD_GUIDE.md, SECURITY_AUDIT.md

---

**작성일**: 2025-12-25  
**버전**: 1.0  
**상태**: 권장사항 (선택적 적용)
