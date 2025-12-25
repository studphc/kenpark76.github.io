package androidx.media3.extractor.text;

import android.os.Bundle;
import android.os.Parcel;
import androidx.media3.common.text.CueGroup$$ExternalSyntheticLambda1;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.BundleCollectionUtil;
import java.util.ArrayList;
/* loaded from: classes.dex */
public final class CueDecoder {
    static final String BUNDLE_FIELD_CUES = "c";
    static final String BUNDLE_FIELD_DURATION_US = "d";

    public CuesWithTiming decode(long j, byte[] bArr) {
        return decode(j, bArr, 0, bArr.length);
    }

    public CuesWithTiming decode(long j, byte[] bArr, int i, int i2) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, i, i2);
        obtain.setDataPosition(0);
        Bundle readBundle = obtain.readBundle(Bundle.class.getClassLoader());
        obtain.recycle();
        return new CuesWithTiming(BundleCollectionUtil.fromBundleList(new CueGroup$$ExternalSyntheticLambda1(), (ArrayList) Assertions.checkNotNull(readBundle.getParcelableArrayList(BUNDLE_FIELD_CUES))), j, readBundle.getLong("d"));
    }
}
