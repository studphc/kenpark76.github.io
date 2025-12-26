package com.pjy.koreatv.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.exoplayer.upstream.CmcdData;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Tls12SocketFactory.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0005\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\u0006\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J(\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000bH\u0016J(\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0013\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015H\u0016¢\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u0015H\u0016¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0007H\u0002R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u001a"}, d2 = {"Lcom/pjy/koreatv/requests/Tls12SocketFactory;", "Ljavax/net/ssl/SSLSocketFactory;", "delegate", "(Ljavax/net/ssl/SSLSocketFactory;)V", "getDelegate", "()Ljavax/net/ssl/SSLSocketFactory;", "createSocket", "Ljava/net/Socket;", "host", "Ljava/net/InetAddress;", "port", "", "address", "localAddress", "localPort", CmcdData.Factory.STREAMING_FORMAT_SS, "", "autoClose", "", "localHost", "getDefaultCipherSuites", "", "()[Ljava/lang/String;", "getSupportedCipherSuites", "patch", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class Tls12SocketFactory extends SSLSocketFactory {
    public static final Companion Companion = new Companion(null);
    private static final String[] TLS_V12_ONLY = {"TLSv1.2"};
    private final SSLSocketFactory delegate;

    public Tls12SocketFactory(SSLSocketFactory delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    public final SSLSocketFactory getDelegate() {
        return this.delegate;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        String[] defaultCipherSuites = this.delegate.getDefaultCipherSuites();
        Intrinsics.checkNotNullExpressionValue(defaultCipherSuites, "getDefaultCipherSuites(...)");
        return defaultCipherSuites;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String[] supportedCipherSuites = this.delegate.getSupportedCipherSuites();
        Intrinsics.checkNotNullExpressionValue(supportedCipherSuites, "getSupportedCipherSuites(...)");
        return supportedCipherSuites;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket s, String host, int i, boolean z) throws IOException {
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(host, "host");
        Socket createSocket = this.delegate.createSocket(s, host, i, z);
        Intrinsics.checkNotNullExpressionValue(createSocket, "createSocket(...)");
        return patch(createSocket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int i) throws IOException, UnknownHostException {
        Intrinsics.checkNotNullParameter(host, "host");
        Socket createSocket = this.delegate.createSocket(host, i);
        Intrinsics.checkNotNullExpressionValue(createSocket, "createSocket(...)");
        return patch(createSocket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String host, int i, InetAddress localHost, int i2) throws IOException, UnknownHostException {
        Intrinsics.checkNotNullParameter(host, "host");
        Intrinsics.checkNotNullParameter(localHost, "localHost");
        Socket createSocket = this.delegate.createSocket(host, i, localHost, i2);
        Intrinsics.checkNotNullExpressionValue(createSocket, "createSocket(...)");
        return patch(createSocket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress host, int i) throws IOException {
        Intrinsics.checkNotNullParameter(host, "host");
        Socket createSocket = this.delegate.createSocket(host, i);
        Intrinsics.checkNotNullExpressionValue(createSocket, "createSocket(...)");
        return patch(createSocket);
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress address, int i, InetAddress localAddress, int i2) throws IOException {
        Intrinsics.checkNotNullParameter(address, "address");
        Intrinsics.checkNotNullParameter(localAddress, "localAddress");
        Socket createSocket = this.delegate.createSocket(address, i, localAddress, i2);
        Intrinsics.checkNotNullExpressionValue(createSocket, "createSocket(...)");
        return patch(createSocket);
    }

    private final Socket patch(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(TLS_V12_ONLY);
        }
        return socket;
    }

    /* compiled from: Tls12SocketFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/pjy/koreatv/requests/Tls12SocketFactory$Companion;", "", "()V", "TLS_V12_ONLY", "", "", "[Ljava/lang/String;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
