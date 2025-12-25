package com.google.common.collect;

import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Hashing {
    private static final long C1 = -862048943;
    private static final long C2 = 461845907;
    private static final int MAX_TABLE_SIZE = 1073741824;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean needsResizing(int size, int tableSize, double loadFactor) {
        return ((double) size) > loadFactor * ((double) tableSize) && tableSize < 1073741824;
    }

    private Hashing() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int smear(int hashCode) {
        return (int) (Integer.rotateLeft((int) (hashCode * C1), 15) * C2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int smearedHash(@CheckForNull Object o) {
        return smear(o == null ? 0 : o.hashCode());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int closedTableSize(int expectedEntries, double loadFactor) {
        int max = Math.max(expectedEntries, 2);
        int highestOneBit = Integer.highestOneBit(max);
        if (max > ((int) (loadFactor * highestOneBit))) {
            int i = highestOneBit << 1;
            if (i > 0) {
                return i;
            }
            return 1073741824;
        }
        return highestOneBit;
    }
}
