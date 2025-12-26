package com.google.common.net;

import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class PercentEscaper extends UnicodeEscaper {
    private static final char[] PLUS_SIGN = {'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String safeChars, boolean plusForSpace) {
        Preconditions.checkNotNull(safeChars);
        if (safeChars.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        String str = safeChars + "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        if (plusForSpace && str.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = plusForSpace;
        this.safeOctets = createSafeOctets(str);
    }

    private static boolean[] createSafeOctets(String safeChars) {
        char[] charArray = safeChars.toCharArray();
        int i = -1;
        for (char c : charArray) {
            i = Math.max((int) c, i);
        }
        boolean[] zArr = new boolean[i + 1];
        for (char c2 : charArray) {
            zArr[c2] = true;
        }
        return zArr;
    }

    @Override // com.google.common.escape.UnicodeEscaper
    protected int nextEscapeIndex(CharSequence csq, int index, int end) {
        Preconditions.checkNotNull(csq);
        while (index < end) {
            char charAt = csq.charAt(index);
            boolean[] zArr = this.safeOctets;
            if (charAt >= zArr.length || !zArr[charAt]) {
                break;
            }
            index++;
        }
        return index;
    }

    @Override // com.google.common.escape.UnicodeEscaper, com.google.common.escape.Escaper
    public String escape(String s) {
        Preconditions.checkNotNull(s);
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char charAt = s.charAt(i);
            boolean[] zArr = this.safeOctets;
            if (charAt >= zArr.length || !zArr[charAt]) {
                return escapeSlow(s, i);
            }
        }
        return s;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.escape.UnicodeEscaper
    @CheckForNull
    public char[] escape(int cp) {
        boolean[] zArr = this.safeOctets;
        if (cp >= zArr.length || !zArr[cp]) {
            if (cp == 32 && this.plusForSpace) {
                return PLUS_SIGN;
            }
            if (cp <= 127) {
                char[] cArr = UPPER_HEX_DIGITS;
                return new char[]{'%', cArr[cp >>> 4], cArr[cp & 15]};
            } else if (cp <= 2047) {
                char[] cArr2 = UPPER_HEX_DIGITS;
                char[] cArr3 = {'%', cArr2[(r14 >>> 4) | 12], cArr2[r14 & 15], '%', cArr2[(r14 & 3) | 8], cArr2[cp & 15]};
                int i = cp >>> 4;
                int i2 = i >>> 2;
                return cArr3;
            } else if (cp <= 65535) {
                char[] cArr4 = UPPER_HEX_DIGITS;
                char[] cArr5 = {'%', 'E', cArr4[r14 >>> 2], '%', cArr4[(r14 & 3) | 8], cArr4[r14 & 15], '%', cArr4[(r14 & 3) | 8], cArr4[cp & 15]};
                int i3 = cp >>> 4;
                int i4 = i3 >>> 2;
                int i5 = i4 >>> 4;
                return cArr5;
            } else if (cp <= 1114111) {
                char[] cArr6 = UPPER_HEX_DIGITS;
                char[] cArr7 = {'%', 'F', cArr6[(r14 >>> 2) & 7], '%', cArr6[(r14 & 3) | 8], cArr6[r14 & 15], '%', cArr6[(r14 & 3) | 8], cArr6[r14 & 15], '%', cArr6[(r14 & 3) | 8], cArr6[cp & 15]};
                int i6 = cp >>> 4;
                int i7 = i6 >>> 2;
                int i8 = i7 >>> 4;
                int i9 = i8 >>> 2;
                int i10 = i9 >>> 4;
                return cArr7;
            } else {
                throw new IllegalArgumentException("Invalid unicode character value " + cp);
            }
        }
        return null;
    }
}
