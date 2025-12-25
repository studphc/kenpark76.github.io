package com.google.common.escape;

import com.google.common.base.Preconditions;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class UnicodeEscaper extends Escaper {
    private static final int DEST_PAD = 32;

    /* JADX INFO: Access modifiers changed from: protected */
    @CheckForNull
    public abstract char[] escape(int cp);

    @Override // com.google.common.escape.Escaper
    public String escape(String string) {
        Preconditions.checkNotNull(string);
        int length = string.length();
        int nextEscapeIndex = nextEscapeIndex(string, 0, length);
        return nextEscapeIndex == length ? string : escapeSlow(string, nextEscapeIndex);
    }

    protected int nextEscapeIndex(CharSequence csq, int start, int end) {
        while (start < end) {
            int codePointAt = codePointAt(csq, start, end);
            if (codePointAt < 0 || escape(codePointAt) != null) {
                break;
            }
            start += Character.isSupplementaryCodePoint(codePointAt) ? 2 : 1;
        }
        return start;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String escapeSlow(String s, int index) {
        int length = s.length();
        char[] charBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int i = 0;
        int i2 = 0;
        while (index < length) {
            int codePointAt = codePointAt(s, index, length);
            if (codePointAt < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] escape = escape(codePointAt);
            int i3 = (Character.isSupplementaryCodePoint(codePointAt) ? 2 : 1) + index;
            if (escape != null) {
                int i4 = index - i;
                int i5 = i2 + i4;
                int length2 = escape.length + i5;
                if (charBufferFromThreadLocal.length < length2) {
                    charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i2, length2 + (length - index) + 32);
                }
                if (i4 > 0) {
                    s.getChars(i, index, charBufferFromThreadLocal, i2);
                    i2 = i5;
                }
                if (escape.length > 0) {
                    System.arraycopy(escape, 0, charBufferFromThreadLocal, i2, escape.length);
                    i2 += escape.length;
                }
                i = i3;
            }
            index = nextEscapeIndex(s, i3, length);
        }
        int i6 = length - i;
        if (i6 > 0) {
            int i7 = i6 + i2;
            if (charBufferFromThreadLocal.length < i7) {
                charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i2, i7);
            }
            s.getChars(i, length, charBufferFromThreadLocal, i2);
            i2 = i7;
        }
        return new String(charBufferFromThreadLocal, 0, i2);
    }

    protected static int codePointAt(CharSequence seq, int index, int end) {
        Preconditions.checkNotNull(seq);
        if (index < end) {
            int i = index + 1;
            char charAt = seq.charAt(index);
            if (charAt < 55296 || charAt > 57343) {
                return charAt;
            }
            if (charAt > 56319) {
                StringBuilder sb = new StringBuilder("Unexpected low surrogate character '");
                sb.append(charAt);
                sb.append("' with value ");
                sb.append((int) charAt);
                sb.append(" at index ");
                sb.append(i - 1);
                sb.append(" in '");
                sb.append((Object) seq);
                sb.append("'");
                throw new IllegalArgumentException(sb.toString());
            } else if (i == end) {
                return -charAt;
            } else {
                char charAt2 = seq.charAt(i);
                if (Character.isLowSurrogate(charAt2)) {
                    return Character.toCodePoint(charAt, charAt2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + charAt2 + "' with value " + ((int) charAt2) + " at index " + i + " in '" + ((Object) seq) + "'");
            }
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static char[] growBuffer(char[] dest, int index, int size) {
        if (size < 0) {
            throw new AssertionError("Cannot increase internal buffer any further");
        }
        char[] cArr = new char[size];
        if (index > 0) {
            System.arraycopy(dest, 0, cArr, 0, index);
        }
        return cArr;
    }
}
