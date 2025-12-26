package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Comparator;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class SignedBytes {
    public static final byte MAX_POWER_OF_TWO = 64;

    public static int compare(byte a, byte b) {
        return a - b;
    }

    public static byte saturatedCast(long value) {
        if (value > 127) {
            return Byte.MAX_VALUE;
        }
        if (value < -128) {
            return Byte.MIN_VALUE;
        }
        return (byte) value;
    }

    private SignedBytes() {
    }

    public static byte checkedCast(long value) {
        byte b = (byte) value;
        Preconditions.checkArgument(((long) b) == value, "Out of range: %s", value);
        return b;
    }

    public static byte min(byte... array) {
        Preconditions.checkArgument(array.length > 0);
        byte b = array[0];
        for (int i = 1; i < array.length; i++) {
            byte b2 = array[i];
            if (b2 < b) {
                b = b2;
            }
        }
        return b;
    }

    public static byte max(byte... array) {
        Preconditions.checkArgument(array.length > 0);
        byte b = array[0];
        for (int i = 1; i < array.length; i++) {
            byte b2 = array[i];
            if (b2 > b) {
                b = b2;
            }
        }
        return b;
    }

    public static String join(String separator, byte... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(array.length * 5);
        sb.append((int) array[0]);
        for (int i = 1; i < array.length; i++) {
            sb.append(separator);
            sb.append((int) array[i]);
        }
        return sb.toString();
    }

    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    /* loaded from: classes3.dex */
    private enum LexicographicalComparator implements Comparator<byte[]> {
        INSTANCE;

        @Override // java.lang.Enum
        public String toString() {
            return "SignedBytes.lexicographicalComparator()";
        }

        @Override // java.util.Comparator
        public int compare(byte[] left, byte[] right) {
            int min = Math.min(left.length, right.length);
            for (int i = 0; i < min; i++) {
                int compare = SignedBytes.compare(left[i], right[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return left.length - right.length;
        }
    }

    public static void sortDescending(byte[] array) {
        Preconditions.checkNotNull(array);
        sortDescending(array, 0, array.length);
    }

    public static void sortDescending(byte[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        Arrays.sort(array, fromIndex, toIndex);
        Bytes.reverse(array, fromIndex, toIndex);
    }
}
