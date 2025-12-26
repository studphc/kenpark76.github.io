package com.google.common.base;

import com.google.common.base.CharMatcher;
import java.util.BitSet;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class SmallCharMatcher extends CharMatcher.NamedFastMatcher {
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5d;
    static final int MAX_SIZE = 1023;
    private final boolean containsZero;
    private final long filter;
    private final char[] table;

    private SmallCharMatcher(char[] table, long filter, boolean containsZero, String description) {
        super(description);
        this.table = table;
        this.filter = filter;
        this.containsZero = containsZero;
    }

    static int smear(int hashCode) {
        return Integer.rotateLeft(hashCode * C1, 15) * C2;
    }

    private boolean checkFilter(int c) {
        return 1 == ((this.filter >> c) & 1);
    }

    static int chooseTableSize(int setSize) {
        if (setSize == 1) {
            return 2;
        }
        int highestOneBit = Integer.highestOneBit(setSize - 1) << 1;
        while (highestOneBit * 0.5d < setSize) {
            highestOneBit <<= 1;
        }
        return highestOneBit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static CharMatcher from(BitSet chars, String description) {
        int i;
        int cardinality = chars.cardinality();
        boolean z = chars.get(0);
        int chooseTableSize = chooseTableSize(cardinality);
        char[] cArr = new char[chooseTableSize];
        int i2 = chooseTableSize - 1;
        int nextSetBit = chars.nextSetBit(0);
        long j = 0;
        while (nextSetBit != -1) {
            long j2 = (1 << nextSetBit) | j;
            int smear = smear(nextSetBit);
            while (true) {
                i = smear & i2;
                if (cArr[i] == 0) {
                    break;
                }
                smear = i + 1;
            }
            cArr[i] = (char) nextSetBit;
            nextSetBit = chars.nextSetBit(nextSetBit + 1);
            j = j2;
        }
        return new SmallCharMatcher(cArr, j, z, description);
    }

    @Override // com.google.common.base.CharMatcher
    public boolean matches(char c) {
        if (c == 0) {
            return this.containsZero;
        }
        if (checkFilter(c)) {
            int length = this.table.length - 1;
            int smear = smear(c) & length;
            int i = smear;
            do {
                char c2 = this.table[i];
                if (c2 == 0) {
                    return false;
                }
                if (c2 == c) {
                    return true;
                }
                i = (i + 1) & length;
            } while (i != smear);
            return false;
        }
        return false;
    }

    @Override // com.google.common.base.CharMatcher
    void setBits(BitSet table) {
        char[] cArr;
        if (this.containsZero) {
            table.set(0);
        }
        for (char c : this.table) {
            if (c != 0) {
                table.set(c);
            }
        }
    }
}
