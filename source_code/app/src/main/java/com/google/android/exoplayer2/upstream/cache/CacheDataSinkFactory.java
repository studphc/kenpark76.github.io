package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;
@Deprecated
/* loaded from: classes3.dex */
public final class CacheDataSinkFactory implements DataSink.Factory {
    private final int bufferSize;
    private final Cache cache;
    private final long fragmentSize;

    public CacheDataSinkFactory(Cache cache, long j) {
        this(cache, j, 20480);
    }

    public CacheDataSinkFactory(Cache cache, long j, int i) {
        this.cache = cache;
        this.fragmentSize = j;
        this.bufferSize = i;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSink.Factory
    public DataSink createDataSink() {
        return new CacheDataSink(this.cache, this.fragmentSize, this.bufferSize);
    }
}
