package com.google.android.exoplayer2.util;

import android.util.SparseBooleanArray;
/* loaded from: classes3.dex */
public class MutableFlags {
    private final SparseBooleanArray flags = new SparseBooleanArray();

    public void clear() {
        this.flags.clear();
    }

    public void add(int i) {
        this.flags.append(i, true);
    }

    public boolean contains(int i) {
        return this.flags.get(i);
    }

    public boolean containsAny(int... iArr) {
        for (int i : iArr) {
            if (contains(i)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return this.flags.size();
    }

    public int get(int i) {
        Assertions.checkArgument(i >= 0 && i < size());
        return this.flags.keyAt(i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MutableFlags) {
            return this.flags.equals(((MutableFlags) obj).flags);
        }
        return false;
    }

    public int hashCode() {
        return this.flags.hashCode();
    }
}
