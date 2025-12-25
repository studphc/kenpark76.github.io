package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import java.io.IOException;
/* loaded from: classes3.dex */
public interface LoadErrorHandlingPolicy {
    @Deprecated
    long getBlacklistDurationMsFor(int i, long j, IOException iOException, int i2);

    long getBlacklistDurationMsFor(LoadErrorInfo loadErrorInfo);

    int getMinimumLoadableRetryCount(int i);

    @Deprecated
    long getRetryDelayMsFor(int i, long j, IOException iOException, int i2);

    long getRetryDelayMsFor(LoadErrorInfo loadErrorInfo);

    void onLoadTaskConcluded(long j);

    /* loaded from: classes3.dex */
    public static final class LoadErrorInfo {
        public final int errorCount;
        public final IOException exception;
        public final LoadEventInfo loadEventInfo;
        public final MediaLoadData mediaLoadData;

        public LoadErrorInfo(LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, int i) {
            this.loadEventInfo = loadEventInfo;
            this.mediaLoadData = mediaLoadData;
            this.exception = iOException;
            this.errorCount = i;
        }
    }

    /* renamed from: com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy$-CC  reason: invalid class name */
    /* loaded from: classes3.dex */
    public final /* synthetic */ class CC {
        public static void $default$onLoadTaskConcluded(LoadErrorHandlingPolicy _this, long j) {
        }

        @Deprecated
        public static long $default$getBlacklistDurationMsFor(LoadErrorHandlingPolicy _this, int i, long j, IOException iOException, int i2) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        public static long $default$getRetryDelayMsFor(LoadErrorHandlingPolicy _this, int i, long j, IOException iOException, int i2) {
            throw new UnsupportedOperationException();
        }
    }
}
