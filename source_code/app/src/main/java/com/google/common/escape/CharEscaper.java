package com.google.common.escape;

import com.google.common.base.Preconditions;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class CharEscaper extends Escaper {
    private static final int DEST_PAD_MULTIPLIER = 2;

    /* JADX INFO: Access modifiers changed from: protected */
    @CheckForNull
    public abstract char[] escape(char c);

    @Override // com.google.common.escape.Escaper
    public String escape(String string) {
        Preconditions.checkNotNull(string);
        int length = string.length();
        for (int i = 0; i < length; i++) {
            if (escape(string.charAt(i)) != null) {
                return escapeSlow(string, i);
            }
        }
        return string;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final String escapeSlow(String s, int index) {
        int length = s.length();
        char[] charBufferFromThreadLocal = Platform.charBufferFromThreadLocal();
        int length2 = charBufferFromThreadLocal.length;
        int i = 0;
        int i2 = 0;
        while (index < length) {
            char[] escape = escape(s.charAt(index));
            if (escape != null) {
                int length3 = escape.length;
                int i3 = index - i;
                int i4 = i2 + i3;
                int i5 = i4 + length3;
                if (length2 < i5) {
                    length2 = ((length - index) * 2) + i5;
                    charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i2, length2);
                }
                if (i3 > 0) {
                    s.getChars(i, index, charBufferFromThreadLocal, i2);
                    i2 = i4;
                }
                if (length3 > 0) {
                    System.arraycopy(escape, 0, charBufferFromThreadLocal, i2, length3);
                    i2 += length3;
                }
                i = index + 1;
            }
            index++;
        }
        int i6 = length - i;
        if (i6 > 0) {
            int i7 = i6 + i2;
            if (length2 < i7) {
                charBufferFromThreadLocal = growBuffer(charBufferFromThreadLocal, i2, i7);
            }
            s.getChars(i, length, charBufferFromThreadLocal, i2);
            i2 = i7;
        }
        return new String(charBufferFromThreadLocal, 0, i2);
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
