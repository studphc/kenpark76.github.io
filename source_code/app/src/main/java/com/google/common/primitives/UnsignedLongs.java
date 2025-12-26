package com.google.common.primitives;

import androidx.media3.exoplayer.rtsp.SessionDescription;
import com.google.common.base.Preconditions;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;

    private static long flip(long a) {
        return a ^ Long.MIN_VALUE;
    }

    private UnsignedLongs() {
    }

    public static int compare(long a, long b) {
        return Longs.compare(flip(a), flip(b));
    }

    public static long min(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long flip = flip(array[0]);
        for (int i = 1; i < array.length; i++) {
            long flip2 = flip(array[i]);
            if (flip2 < flip) {
                flip = flip2;
            }
        }
        return flip(flip);
    }

    public static long max(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long flip = flip(array[0]);
        for (int i = 1; i < array.length; i++) {
            long flip2 = flip(array[i]);
            if (flip2 > flip) {
                flip = flip2;
            }
        }
        return flip(flip);
    }

    public static String join(String separator, long... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(array.length * 5);
        sb.append(toString(array[0]));
        for (int i = 1; i < array.length; i++) {
            sb.append(separator);
            sb.append(toString(array[i]));
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    /* loaded from: classes3.dex */
    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(long[] left, long[] right) {
            int min = Math.min(left.length, right.length);
            for (int i = 0; i < min; i++) {
                long j = left[i];
                long j2 = right[i];
                if (j != j2) {
                    return UnsignedLongs.compare(j, j2);
                }
            }
            return left.length - right.length;
        }
    }

    public static void sort(long[] array) {
        Preconditions.checkNotNull(array);
        sort(array, 0, array.length);
    }

    public static void sort(long[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        for (int i = fromIndex; i < toIndex; i++) {
            array[i] = flip(array[i]);
        }
        Arrays.sort(array, fromIndex, toIndex);
        while (fromIndex < toIndex) {
            array[fromIndex] = flip(array[fromIndex]);
            fromIndex++;
        }
    }

    public static void sortDescending(long[] array) {
        Preconditions.checkNotNull(array);
        sortDescending(array, 0, array.length);
    }

    public static void sortDescending(long[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        for (int i = fromIndex; i < toIndex; i++) {
            array[i] = Long.MAX_VALUE ^ array[i];
        }
        Arrays.sort(array, fromIndex, toIndex);
        while (fromIndex < toIndex) {
            array[fromIndex] = array[fromIndex] ^ Long.MAX_VALUE;
            fromIndex++;
        }
    }

    public static long divide(long dividend, long divisor) {
        if (divisor < 0) {
            return compare(dividend, divisor) < 0 ? 0L : 1L;
        } else if (dividend >= 0) {
            return dividend / divisor;
        } else {
            long j = ((dividend >>> 1) / divisor) << 1;
            return j + (compare(dividend - (j * divisor), divisor) < 0 ? 0 : 1);
        }
    }

    public static long remainder(long dividend, long divisor) {
        if (divisor < 0) {
            return compare(dividend, divisor) < 0 ? dividend : dividend - divisor;
        } else if (dividend >= 0) {
            return dividend % divisor;
        } else {
            long j = dividend - ((((dividend >>> 1) / divisor) << 1) * divisor);
            if (compare(j, divisor) < 0) {
                divisor = 0;
            }
            return j - divisor;
        }
    }

    public static long parseUnsignedLong(String string) {
        return parseUnsignedLong(string, 10);
    }

    public static long parseUnsignedLong(String string, int radix) {
        Preconditions.checkNotNull(string);
        if (string.length() != 0) {
            if (radix < 2 || radix > 36) {
                throw new NumberFormatException("illegal radix: " + radix);
            }
            int i = ParseOverflowDetection.maxSafeDigits[radix] - 1;
            long j = 0;
            for (int i2 = 0; i2 < string.length(); i2++) {
                int digit = Character.digit(string.charAt(i2), radix);
                if (digit == -1) {
                    throw new NumberFormatException(string);
                }
                if (i2 > i && ParseOverflowDetection.overflowInParse(j, digit, radix)) {
                    throw new NumberFormatException("Too large for unsigned long: " + string);
                }
                j = (j * radix) + digit;
            }
            return j;
        }
        throw new NumberFormatException("empty string");
    }

    public static long decode(String stringValue) {
        ParseRequest fromString = ParseRequest.fromString(stringValue);
        try {
            return parseUnsignedLong(fromString.rawValue, fromString.radix);
        } catch (NumberFormatException e) {
            NumberFormatException numberFormatException = new NumberFormatException("Error parsing value: " + stringValue);
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class ParseOverflowDetection {
        static final long[] maxValueDivs = new long[37];
        static final int[] maxValueMods = new int[37];
        static final int[] maxSafeDigits = new int[37];

        private ParseOverflowDetection() {
        }

        static {
            BigInteger bigInteger = new BigInteger("10000000000000000", 16);
            for (int i = 2; i <= 36; i++) {
                long j = i;
                maxValueDivs[i] = UnsignedLongs.divide(-1L, j);
                maxValueMods[i] = (int) UnsignedLongs.remainder(-1L, j);
                maxSafeDigits[i] = bigInteger.toString(i).length() - 1;
            }
        }

        static boolean overflowInParse(long current, int digit, int radix) {
            if (current >= 0) {
                long j = maxValueDivs[radix];
                if (current < j) {
                    return false;
                }
                return current > j || digit > maxValueMods[radix];
            }
            return true;
        }
    }

    public static String toString(long x) {
        return toString(x, 10);
    }

    public static String toString(long x, int radix) {
        long divide;
        Preconditions.checkArgument(radix >= 2 && radix <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", radix);
        int i = (x > 0L ? 1 : (x == 0L ? 0 : -1));
        if (i == 0) {
            return SessionDescription.SUPPORTED_SDP_VERSION;
        }
        if (i > 0) {
            return Long.toString(x, radix);
        }
        int i2 = 64;
        char[] cArr = new char[64];
        int i3 = radix - 1;
        if ((radix & i3) == 0) {
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(radix);
            do {
                i2--;
                cArr[i2] = Character.forDigit(((int) x) & i3, radix);
                x >>>= numberOfTrailingZeros;
            } while (x != 0);
        } else {
            if ((radix & 1) == 0) {
                divide = (x >>> 1) / (radix >>> 1);
            } else {
                divide = divide(x, radix);
            }
            long j = radix;
            int i4 = 63;
            cArr[63] = Character.forDigit((int) (x - (divide * j)), radix);
            while (divide > 0) {
                i4--;
                cArr[i4] = Character.forDigit((int) (divide % j), radix);
                divide /= j;
            }
            i2 = i4;
        }
        return new String(cArr, i2, 64 - i2);
    }
}
