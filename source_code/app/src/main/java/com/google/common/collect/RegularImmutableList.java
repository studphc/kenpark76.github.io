package com.google.common.collect;

import com.google.common.base.Preconditions;
import j$.util.Objects;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class RegularImmutableList<E> extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList(new Object[0], 0);
    final transient Object[] array;
    private final transient int size;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayStart() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public boolean isPartialView() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public RegularImmutableList(Object[] array, int size) {
        this.array = array;
        this.size = size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public Object[] internalArray() {
        return this.array;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableCollection
    public int internalArrayEnd() {
        return this.size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableList, com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] dst, int dstOff) {
        System.arraycopy(this.array, 0, dst, dstOff, this.size);
        return dstOff + this.size;
    }

    @Override // java.util.List
    public E get(int index) {
        Preconditions.checkElementIndex(index, this.size);
        return (E) Objects.requireNonNull(this.array[index]);
    }
}
