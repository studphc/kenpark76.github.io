package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import java.io.FileNotFoundException;
import java.io.IOException;
/* loaded from: classes3.dex */
public class DefaultLoadErrorHandlingPolicy implements LoadErrorHandlingPolicy {
    private static final int DEFAULT_BEHAVIOR_MIN_LOADABLE_RETRY_COUNT = -1;
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT_PROGRESSIVE_LIVE = 6;
    public static final long DEFAULT_TRACK_BLACKLIST_MS = 60000;
    private final int minimumLoadableRetryCount;

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public /* synthetic */ long getBlacklistDurationMsFor(int i, long j, IOException iOException, int i2) {
        return LoadErrorHandlingPolicy.CC.$default$getBlacklistDurationMsFor(this, i, j, iOException, i2);
    }

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public /* synthetic */ long getRetryDelayMsFor(int i, long j, IOException iOException, int i2) {
        return LoadErrorHandlingPolicy.CC.$default$getRetryDelayMsFor(this, i, j, iOException, i2);
    }

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public /* synthetic */ void onLoadTaskConcluded(long j) {
        LoadErrorHandlingPolicy.CC.$default$onLoadTaskConcluded(this, j);
    }

    public DefaultLoadErrorHandlingPolicy() {
        this(-1);
    }

    public DefaultLoadErrorHandlingPolicy(int i) {
        this.minimumLoadableRetryCount = i;
    }

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public long getBlacklistDurationMsFor(LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        IOException iOException = loadErrorInfo.exception;
        if (iOException instanceof HttpDataSource.InvalidResponseCodeException) {
            int i = ((HttpDataSource.InvalidResponseCodeException) iOException).responseCode;
            return (i == 403 || i == 404 || i == 410 || i == 416 || i == 500 || i == 503) ? 60000L : -9223372036854775807L;
        }
        return -9223372036854775807L;
    }

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public long getRetryDelayMsFor(LoadErrorHandlingPolicy.LoadErrorInfo loadErrorInfo) {
        IOException iOException = loadErrorInfo.exception;
        if ((iOException instanceof ParserException) || (iOException instanceof FileNotFoundException) || (iOException instanceof HttpDataSource.CleartextNotPermittedException) || (iOException instanceof Loader.UnexpectedLoaderException)) {
            return -9223372036854775807L;
        }
        return Math.min((loadErrorInfo.errorCount - 1) * 1000, 5000);
    }

    @Override // com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy
    public int getMinimumLoadableRetryCount(int i) {
        int i2 = this.minimumLoadableRetryCount;
        return i2 == -1 ? i == 7 ? 6 : 3 : i2;
    }
}
