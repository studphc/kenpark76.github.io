package com.google.android.exoplayer2.upstream;

import android.net.Uri;
import com.google.android.exoplayer2.upstream.DataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
/* loaded from: classes3.dex */
public final class DummyDataSource implements DataSource {
    public static final DummyDataSource INSTANCE = new DummyDataSource();
    public static final DataSource.Factory FACTORY = new DataSource.Factory() { // from class: com.google.android.exoplayer2.upstream.DummyDataSource$$ExternalSyntheticLambda0
        @Override // com.google.android.exoplayer2.upstream.DataSource.Factory
        public final DataSource createDataSource() {
            return DummyDataSource.$r8$lambda$9BpKLyGsZEvVQGK2JL1PVuvhcCc();
        }
    };

    public static /* synthetic */ DummyDataSource $r8$lambda$9BpKLyGsZEvVQGK2JL1PVuvhcCc() {
        return new DummyDataSource();
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void addTransferListener(TransferListener transferListener) {
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public void close() {
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public /* synthetic */ Map getResponseHeaders() {
        Map emptyMap;
        emptyMap = Collections.emptyMap();
        return emptyMap;
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public Uri getUri() {
        return null;
    }

    private DummyDataSource() {
    }

    @Override // com.google.android.exoplayer2.upstream.DataSource
    public long open(DataSpec dataSpec) throws IOException {
        throw new IOException("DummyDataSource cannot be opened");
    }

    @Override // com.google.android.exoplayer2.upstream.DataReader
    public int read(byte[] bArr, int i, int i2) {
        throw new UnsupportedOperationException();
    }
}
