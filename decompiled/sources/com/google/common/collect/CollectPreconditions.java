package com.google.common.collect;

import com.google.common.base.Preconditions;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class CollectPreconditions {
    CollectPreconditions() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkEntryNotNull(Object key, Object value) {
        if (key == null) {
            throw new NullPointerException("null key in entry: null=" + value);
        } else if (value != null) {
        } else {
            throw new NullPointerException("null value in entry: " + key + "=null");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int checkNonnegative(int value, String name) {
        if (value >= 0) {
            return value;
        }
        throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static long checkNonnegative(long value, String name) {
        if (value >= 0) {
            return value;
        }
        throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkPositive(int value, String name) {
        if (value > 0) {
            return;
        }
        throw new IllegalArgumentException(name + " must be positive but was: " + value);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void checkRemove(boolean canRemove) {
        Preconditions.checkState(canRemove, "no calls to next() since the last call to remove()");
    }
}
