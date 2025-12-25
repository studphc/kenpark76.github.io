package androidx.leanback.util;
/* loaded from: classes.dex */
public final class MathUtil {
    private MathUtil() {
    }

    public static int safeLongToInt(long j) {
        int i = (int) j;
        if (i == j) {
            return i;
        }
        throw new ArithmeticException("Input overflows int.\n");
    }
}
