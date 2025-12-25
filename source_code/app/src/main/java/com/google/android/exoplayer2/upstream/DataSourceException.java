package com.google.android.exoplayer2.upstream;

import java.io.IOException;
/* loaded from: classes3.dex */
public final class DataSourceException extends IOException {
    public static final int POSITION_OUT_OF_RANGE = 0;
    public final int reason;

    /* JADX WARN: Code restructure failed: missing block: B:0:?, code lost:
        r1 = r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isCausedByPositionOutOfRange(java.io.IOException r1) {
        /*
        L0:
            if (r1 == 0) goto L14
            boolean r0 = r1 instanceof com.google.android.exoplayer2.upstream.DataSourceException
            if (r0 == 0) goto Lf
            r0 = r1
            com.google.android.exoplayer2.upstream.DataSourceException r0 = (com.google.android.exoplayer2.upstream.DataSourceException) r0
            int r0 = r0.reason
            if (r0 != 0) goto Lf
            r1 = 1
            return r1
        Lf:
            java.lang.Throwable r1 = r1.getCause()
            goto L0
        L14:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.DataSourceException.isCausedByPositionOutOfRange(java.io.IOException):boolean");
    }

    public DataSourceException(int i) {
        this.reason = i;
    }
}
