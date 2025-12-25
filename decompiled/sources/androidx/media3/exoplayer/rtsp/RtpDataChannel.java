package androidx.media3.exoplayer.rtsp;

import androidx.media3.datasource.DataSource;
import androidx.media3.exoplayer.rtsp.RtspMessageChannel;
import java.io.IOException;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public interface RtpDataChannel extends DataSource {

    /* loaded from: classes.dex */
    public interface Factory {

        /* renamed from: androidx.media3.exoplayer.rtsp.RtpDataChannel$Factory$-CC  reason: invalid class name */
        /* loaded from: classes.dex */
        public final /* synthetic */ class CC {
            public static Factory $default$createFallbackDataChannelFactory(Factory _this) {
                return null;
            }
        }

        RtpDataChannel createAndOpenDataChannel(int i) throws IOException;

        Factory createFallbackDataChannelFactory();
    }

    RtspMessageChannel.InterleavedBinaryDataListener getInterleavedBinaryDataListener();

    int getLocalPort();

    String getTransport();

    boolean needsClosingOnLoadCompletion();
}
