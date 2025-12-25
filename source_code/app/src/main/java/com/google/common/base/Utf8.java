package com.google.common.base;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Utf8 {
    public static int encodedLength(CharSequence sequence) {
        int length = sequence.length();
        int i = 0;
        while (i < length && sequence.charAt(i) < 128) {
            i++;
        }
        int i2 = length;
        while (true) {
            if (i < length) {
                char charAt = sequence.charAt(i);
                if (charAt >= 2048) {
                    i2 += encodedLengthGeneral(sequence, i);
                    break;
                }
                i2 += (127 - charAt) >>> 31;
                i++;
            } else {
                break;
            }
        }
        if (i2 >= length) {
            return i2;
        }
        throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (i2 + 4294967296L));
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int length = sequence.length();
        int i = 0;
        while (start < length) {
            char charAt = sequence.charAt(start);
            if (charAt < 2048) {
                i += (127 - charAt) >>> 31;
            } else {
                i += 2;
                if (55296 <= charAt && charAt <= 57343) {
                    if (Character.codePointAt(sequence, start) == charAt) {
                        throw new IllegalArgumentException(unpairedSurrogateMsg(start));
                    }
                    start++;
                }
            }
            start++;
        }
        return i;
    }

    public static boolean isWellFormed(byte[] bytes) {
        return isWellFormed(bytes, 0, bytes.length);
    }

    public static boolean isWellFormed(byte[] bytes, int off, int len) {
        int i = len + off;
        Preconditions.checkPositionIndexes(off, i, bytes.length);
        while (off < i) {
            if (bytes[off] < 0) {
                return isWellFormedSlowPath(bytes, off, i);
            }
            off++;
        }
        return true;
    }

    private static boolean isWellFormedSlowPath(byte[] bytes, int off, int end) {
        byte b;
        while (off < end) {
            int i = off + 1;
            byte b2 = bytes[off];
            if (b2 < 0) {
                if (b2 < -32) {
                    if (i != end && b2 >= -62) {
                        off = i + 1;
                        if (bytes[i] > -65) {
                        }
                    }
                    return false;
                } else if (b2 < -16) {
                    int i2 = i + 1;
                    if (i2 < end && (b = bytes[i]) <= -65 && ((b2 != -32 || b >= -96) && (b2 != -19 || -96 > b))) {
                        off = i2 + 1;
                        if (bytes[i2] > -65) {
                        }
                    }
                    return false;
                } else if (i + 2 >= end) {
                    return false;
                } else {
                    int i3 = i + 1;
                    byte b3 = bytes[i];
                    if (b3 <= -65 && (((b2 << Ascii.FS) + (b3 + 112)) >> 30) == 0) {
                        int i4 = i3 + 1;
                        if (bytes[i3] <= -65) {
                            i = i4 + 1;
                            if (bytes[i4] > -65) {
                            }
                        }
                    }
                    return false;
                }
            }
            off = i;
        }
        return true;
    }

    private static String unpairedSurrogateMsg(int i) {
        return "Unpaired surrogate at index " + i;
    }

    private Utf8() {
    }
}
