package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata;
import java.io.IOException;
import java.io.InterruptedIOException;
/* loaded from: classes3.dex */
public final class CacheWriter {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    private final boolean allowShortContent;
    private long bytesCached;
    private final Cache cache;
    private final String cacheKey;
    private final CacheDataSource dataSource;
    private final DataSpec dataSpec;
    private long endPosition;
    private boolean initialized;
    private volatile boolean isCanceled;
    private long nextPosition;
    private final ProgressListener progressListener;
    private final byte[] temporaryBuffer;

    /* loaded from: classes3.dex */
    public interface ProgressListener {
        void onProgress(long j, long j2, long j3);
    }

    public CacheWriter(CacheDataSource cacheDataSource, DataSpec dataSpec, boolean z, byte[] bArr, ProgressListener progressListener) {
        this.dataSource = cacheDataSource;
        this.cache = cacheDataSource.getCache();
        this.dataSpec = dataSpec;
        this.allowShortContent = z;
        this.temporaryBuffer = bArr == null ? new byte[131072] : bArr;
        this.progressListener = progressListener;
        this.cacheKey = cacheDataSource.getCacheKeyFactory().buildCacheKey(dataSpec);
        this.nextPosition = dataSpec.position;
    }

    public void cancel() {
        this.isCanceled = true;
    }

    public void cache() throws IOException {
        throwIfCanceled();
        if (!this.initialized) {
            if (this.dataSpec.length != -1) {
                this.endPosition = this.dataSpec.position + this.dataSpec.length;
            } else {
                long contentLength = ContentMetadata.CC.getContentLength(this.cache.getContentMetadata(this.cacheKey));
                if (contentLength == -1) {
                    contentLength = -1;
                }
                this.endPosition = contentLength;
            }
            this.bytesCached = this.cache.getCachedBytes(this.cacheKey, this.dataSpec.position, this.dataSpec.length);
            ProgressListener progressListener = this.progressListener;
            if (progressListener != null) {
                progressListener.onProgress(getLength(), this.bytesCached, 0L);
            }
            this.initialized = true;
        }
        while (true) {
            long j = this.endPosition;
            if (j != -1 && this.nextPosition >= j) {
                return;
            }
            throwIfCanceled();
            long j2 = this.endPosition;
            long cachedLength = this.cache.getCachedLength(this.cacheKey, this.nextPosition, j2 == -1 ? Long.MAX_VALUE : j2 - this.nextPosition);
            if (cachedLength > 0) {
                this.nextPosition += cachedLength;
            } else {
                long j3 = -cachedLength;
                if (j3 == Long.MAX_VALUE) {
                    j3 = -1;
                }
                long j4 = this.nextPosition;
                this.nextPosition = j4 + readBlockToCache(j4, j3);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004b A[Catch: all -> 0x0031, TryCatch #0 {all -> 0x0031, blocks: (B:11:0x0018, B:25:0x004b, B:29:0x006c, B:33:0x0075, B:35:0x0083, B:37:0x008b, B:16:0x0034, B:19:0x003a, B:21:0x0040, B:22:0x0046), top: B:43:0x0016, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0075 A[Catch: all -> 0x0031, TryCatch #0 {all -> 0x0031, blocks: (B:11:0x0018, B:25:0x004b, B:29:0x006c, B:33:0x0075, B:35:0x0083, B:37:0x008b, B:16:0x0034, B:19:0x003a, B:21:0x0040, B:22:0x0046), top: B:43:0x0016, inners: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008b A[Catch: all -> 0x0031, TRY_LEAVE, TryCatch #0 {all -> 0x0031, blocks: (B:11:0x0018, B:25:0x004b, B:29:0x006c, B:33:0x0075, B:35:0x0083, B:37:0x008b, B:16:0x0034, B:19:0x003a, B:21:0x0040, B:22:0x0046), top: B:43:0x0016, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private long readBlockToCache(long r10, long r12) throws java.io.IOException {
        /*
            r9 = this;
            long r0 = r10 + r12
            long r2 = r9.endPosition
            r4 = 1
            r5 = 0
            r6 = -1
            int r8 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r8 == 0) goto L13
            int r0 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r0 != 0) goto L11
            goto L13
        L11:
            r0 = 0
            goto L14
        L13:
            r0 = 1
        L14:
            int r1 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r1 == 0) goto L47
            com.google.android.exoplayer2.upstream.DataSpec r1 = r9.dataSpec     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            com.google.android.exoplayer2.upstream.DataSpec$Builder r1 = r1.buildUpon()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            com.google.android.exoplayer2.upstream.DataSpec$Builder r1 = r1.setPosition(r10)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            com.google.android.exoplayer2.upstream.DataSpec$Builder r12 = r1.setLength(r12)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            com.google.android.exoplayer2.upstream.DataSpec r12 = r12.build()     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r13 = r9.dataSource     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            long r12 = r13.open(r12)     // Catch: java.lang.Throwable -> L31 java.io.IOException -> L33
            goto L49
        L31:
            r10 = move-exception
            goto L91
        L33:
            r12 = move-exception
            boolean r13 = r9.allowShortContent     // Catch: java.lang.Throwable -> L31
            if (r13 == 0) goto L46
            if (r0 == 0) goto L46
            boolean r13 = com.google.android.exoplayer2.upstream.DataSourceException.isCausedByPositionOutOfRange(r12)     // Catch: java.lang.Throwable -> L31
            if (r13 == 0) goto L46
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r12 = r9.dataSource     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.util.Util.closeQuietly(r12)     // Catch: java.lang.Throwable -> L31
            goto L47
        L46:
            throw r12     // Catch: java.lang.Throwable -> L31
        L47:
            r12 = r6
            r4 = 0
        L49:
            if (r4 != 0) goto L66
            r9.throwIfCanceled()     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.DataSpec r12 = r9.dataSpec     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.DataSpec$Builder r12 = r12.buildUpon()     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.DataSpec$Builder r12 = r12.setPosition(r10)     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.DataSpec$Builder r12 = r12.setLength(r6)     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.DataSpec r12 = r12.build()     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r13 = r9.dataSource     // Catch: java.lang.Throwable -> L31
            long r12 = r13.open(r12)     // Catch: java.lang.Throwable -> L31
        L66:
            if (r0 == 0) goto L70
            int r1 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r1 == 0) goto L70
            long r12 = r12 + r10
            r9.onRequestEndPosition(r12)     // Catch: java.lang.Throwable -> L31
        L70:
            r12 = 0
            r13 = 0
        L72:
            r1 = -1
            if (r12 == r1) goto L89
            r9.throwIfCanceled()     // Catch: java.lang.Throwable -> L31
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r12 = r9.dataSource     // Catch: java.lang.Throwable -> L31
            byte[] r2 = r9.temporaryBuffer     // Catch: java.lang.Throwable -> L31
            int r3 = r2.length     // Catch: java.lang.Throwable -> L31
            int r12 = r12.read(r2, r5, r3)     // Catch: java.lang.Throwable -> L31
            if (r12 == r1) goto L72
            long r1 = (long) r12     // Catch: java.lang.Throwable -> L31
            r9.onNewBytesCached(r1)     // Catch: java.lang.Throwable -> L31
            int r13 = r13 + r12
            goto L72
        L89:
            if (r0 == 0) goto L97
            long r0 = (long) r13     // Catch: java.lang.Throwable -> L31
            long r10 = r10 + r0
            r9.onRequestEndPosition(r10)     // Catch: java.lang.Throwable -> L31
            goto L97
        L91:
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r11 = r9.dataSource
            com.google.android.exoplayer2.util.Util.closeQuietly(r11)
            throw r10
        L97:
            long r10 = (long) r13
            com.google.android.exoplayer2.upstream.cache.CacheDataSource r12 = r9.dataSource
            com.google.android.exoplayer2.util.Util.closeQuietly(r12)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.CacheWriter.readBlockToCache(long, long):long");
    }

    private void onRequestEndPosition(long j) {
        if (this.endPosition == j) {
            return;
        }
        this.endPosition = j;
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(getLength(), this.bytesCached, 0L);
        }
    }

    private void onNewBytesCached(long j) {
        this.bytesCached += j;
        ProgressListener progressListener = this.progressListener;
        if (progressListener != null) {
            progressListener.onProgress(getLength(), this.bytesCached, j);
        }
    }

    private long getLength() {
        long j = this.endPosition;
        if (j == -1) {
            return -1L;
        }
        return j - this.dataSpec.position;
    }

    private void throwIfCanceled() throws InterruptedIOException {
        if (this.isCanceled) {
            throw new InterruptedIOException();
        }
    }
}
