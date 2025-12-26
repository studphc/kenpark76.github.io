package com.tvhome.app;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.Metadata;
/* compiled from: IgnoreSSLCertificate.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/IgnoreSSLCertificate;", "", "()V", "ignore", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class IgnoreSSLCertificate {
    public static final IgnoreSSLCertificate INSTANCE = new IgnoreSSLCertificate();

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean ignore$lambda$0(String str, SSLSession sSLSession) {
        return true;
    }

    private IgnoreSSLCertificate() {
    }

    public final void ignore() {
        try {
            TrustManager[] trustManagerArr = {new X509TrustManager() { // from class: com.pjy.koreatv.IgnoreSSLCertificate$ignore$trustAllCerts$1
                @Override // javax.net.ssl.X509TrustManager
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                @Override // javax.net.ssl.X509TrustManager
                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) {
                }

                @Override // javax.net.ssl.X509TrustManager
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            sSLContext.init(null, trustManagerArr, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sSLContext.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { // from class: com.pjy.koreatv.IgnoreSSLCertificate$$ExternalSyntheticLambda0
                @Override // javax.net.ssl.HostnameVerifier
                public final boolean verify(String str, SSLSession sSLSession) {
                    boolean ignore$lambda$0;
                    ignore$lambda$0 = IgnoreSSLCertificate.ignore$lambda$0(str, sSLSession);
                    return ignore$lambda$0;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
