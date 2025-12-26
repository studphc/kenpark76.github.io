package com.tvhome.app.requests;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.tvhome.app.SP;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/* compiled from: HttpClient.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u0002J\b\u0010\u001a\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0012\u001a\u00020\u00138FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\f\u001a\u0004\b\u0014\u0010\u0015¨\u0006\u001b"}, d2 = {"Lcom/pjy/koreatv/requests/HttpClient;", "", "()V", "DOWNLOAD_HOST", "", "HOST", "TAG", "configService", "Lcom/pjy/koreatv/requests/ConfigService;", "getConfigService", "()Lcom/pjy/koreatv/requests/ConfigService;", "configService$delegate", "Lkotlin/Lazy;", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "okHttpClient$delegate", "releaseService", "Lcom/pjy/koreatv/requests/ReleaseService;", "getReleaseService", "()Lcom/pjy/koreatv/requests/ReleaseService;", "releaseService$delegate", "enableTls12OnPreLollipop", "Lokhttp3/OkHttpClient$Builder;", "client", "getUnsafeOkHttpClient", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class HttpClient {
    public static final String DOWNLOAD_HOST = "https://www.gitlink.org.cn/lizongying/my-tv-0/releases/download/";
    private static final String HOST = "https://www.gitlink.org.cn/lizongying/my-tv-0/raw/";
    public static final String TAG = "HttpClient";
    public static final HttpClient INSTANCE = new HttpClient();
    private static final Lazy okHttpClient$delegate = LazyKt.lazy(new Function0<OkHttpClient>() { // from class: com.pjy.koreatv.requests.HttpClient$okHttpClient$2
        @Override // kotlin.jvm.functions.Function0
        public final OkHttpClient invoke() {
            OkHttpClient unsafeOkHttpClient;
            unsafeOkHttpClient = HttpClient.INSTANCE.getUnsafeOkHttpClient();
            return unsafeOkHttpClient;
        }
    });
    private static final Lazy releaseService$delegate = LazyKt.lazy(new Function0<ReleaseService>() { // from class: com.pjy.koreatv.requests.HttpClient$releaseService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ReleaseService invoke() {
            return (ReleaseService) new Retrofit.Builder().baseUrl("https://www.gitlink.org.cn/lizongying/my-tv-0/raw/").client(HttpClient.INSTANCE.getOkHttpClient()).addConverterFactory(GsonConverterFactory.create()).build().create(ReleaseService.class);
        }
    });
    private static final Lazy configService$delegate = LazyKt.lazy(new Function0<ConfigService>() { // from class: com.pjy.koreatv.requests.HttpClient$configService$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final ConfigService invoke() {
            return (ConfigService) new Retrofit.Builder().client(HttpClient.INSTANCE.getOkHttpClient()).build().create(ConfigService.class);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getUnsafeOkHttpClient$lambda$1(String str, SSLSession sSLSession) {
        return true;
    }

    private HttpClient() {
    }

    public final OkHttpClient getOkHttpClient() {
        return (OkHttpClient) okHttpClient$delegate.getValue();
    }

    public final ReleaseService getReleaseService() {
        Object value = releaseService$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ReleaseService) value;
    }

    public final ConfigService getConfigService() {
        Object value = configService$delegate.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (ConfigService) value;
    }

    private final OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder builder) {
        if (Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sSLContext = SSLContext.getInstance("TLSv1.2");
                sSLContext.init(null, null, null);
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (!(trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager))) {
                    StringBuilder sb = new StringBuilder("Unexpected default trust managers:");
                    String arrays = Arrays.toString(trustManagers);
                    Intrinsics.checkNotNullExpressionValue(arrays, "toString(...)");
                    sb.append(arrays);
                    throw new IllegalStateException(sb.toString().toString());
                }
                TrustManager trustManager = trustManagers[0];
                Intrinsics.checkNotNull(trustManager, "null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
                SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
                Intrinsics.checkNotNullExpressionValue(socketFactory, "getSocketFactory(...)");
                builder.sslSocketFactory(new Tls12SocketFactory(socketFactory), (X509TrustManager) trustManager);
                ConnectionSpec build = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_2).build();
                ArrayList arrayList = new ArrayList();
                Intrinsics.checkNotNull(build);
                arrayList.add(build);
                ConnectionSpec COMPATIBLE_TLS = ConnectionSpec.COMPATIBLE_TLS;
                Intrinsics.checkNotNullExpressionValue(COMPATIBLE_TLS, "COMPATIBLE_TLS");
                arrayList.add(COMPATIBLE_TLS);
                ConnectionSpec CLEARTEXT = ConnectionSpec.CLEARTEXT;
                Intrinsics.checkNotNullExpressionValue(CLEARTEXT, "CLEARTEXT");
                arrayList.add(CLEARTEXT);
                builder.connectionSpecs(arrayList);
            } catch (Exception e) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", e);
            }
        }
        return builder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: com.pjy.koreatv.requests.HttpClient$getUnsafeOkHttpClient$trustAllCerts$1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            SSLSocketFactory socketFactory = sSLContext.getSocketFactory();
            TrustManager trustManager = trustManagerArr[0];
            Intrinsics.checkNotNull(trustManager, "null cannot be cast to non-null type javax.net.ssl.X509TrustManager");
            OkHttpClient.Builder dns = builder.sslSocketFactory(socketFactory, (X509TrustManager) trustManager).hostnameVerifier(new HostnameVerifier() { // from class: com.pjy.koreatv.requests.HttpClient$$ExternalSyntheticLambda0
                @Override // javax.net.ssl.HostnameVerifier
                public final boolean verify(String str, SSLSession sSLSession) {
                    boolean unsafeOkHttpClient$lambda$1;
                    unsafeOkHttpClient$lambda$1 = HttpClient.getUnsafeOkHttpClient$lambda$1(str, sSLSession);
                    return unsafeOkHttpClient$lambda$1;
                }
            }).connectionSpecs(CollectionsKt.listOf((Object[]) new ConnectionSpec[]{ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT})).dns(new DnsCache());
            if (!Intrinsics.areEqual(SP.INSTANCE.getProxy(), "")) {
                Log.i(TAG, "proxy " + SP.INSTANCE.getProxy());
                Uri parse = Uri.parse(SP.INSTANCE.getProxy());
                dns.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(parse.getHost(), parse.getPort())));
            }
            Intrinsics.checkNotNull(dns);
            OkHttpClient build = enableTls12OnPreLollipop(dns).build();
            Intrinsics.checkNotNullExpressionValue(build, "build(...)");
            return build;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
