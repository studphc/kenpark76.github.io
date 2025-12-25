package kotlin;

import java.util.Iterator;
/* compiled from: D8$$SyntheticClass */
/* loaded from: classes3.dex */
public final /* synthetic */ class UByte$$ExternalSyntheticBackport0 {
    public static /* synthetic */ int m(double d) {
        long doubleToLongBits = Double.doubleToLongBits(d);
        return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
    }

    public static /* synthetic */ int m(int i, int i2) {
        return (int) ((i & 4294967295L) % (i2 & 4294967295L));
    }

    public static /* synthetic */ int m(long j) {
        return (int) (j ^ (j >>> 32));
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ long m605m(long j, long j2) {
        if (j2 < 0) {
            return (j ^ Long.MIN_VALUE) < (Long.MIN_VALUE ^ j2) ? j : j - j2;
        } else if (j >= 0) {
            return j % j2;
        } else {
            long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
            if ((j3 ^ Long.MIN_VALUE) < (Long.MIN_VALUE ^ j2)) {
                j2 = 0;
            }
            return j3 - j2;
        }
    }

    public static /* synthetic */ String m(CharSequence charSequence, Iterable iterable) {
        if (charSequence != null) {
            StringBuilder sb = new StringBuilder();
            Iterator it = iterable.iterator();
            if (it.hasNext()) {
                while (true) {
                    sb.append((CharSequence) it.next());
                    if (!it.hasNext()) {
                        break;
                    }
                    sb.append(charSequence);
                }
            }
            return sb.toString();
        }
        throw new NullPointerException("delimiter");
    }

    public static /* synthetic */ int m$1(int i, int i2) {
        return (int) ((i & 4294967295L) / (i2 & 4294967295L));
    }

    public static /* synthetic */ long m$1(long j, long j2) {
        if (j2 < 0) {
            return (j ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE) ? 0L : 1L;
        } else if (j >= 0) {
            return j / j2;
        } else {
            long j3 = ((j >>> 1) / j2) << 1;
            return j3 + (((j - (j3 * j2)) ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE) ? 0 : 1);
        }
    }
}
