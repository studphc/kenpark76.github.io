package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.math.IntMath;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class CartesianList<E> extends AbstractList<List<E>> implements RandomAccess {
    private final transient ImmutableList<List<E>> axes;
    private final transient int[] axesSizeProduct;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <E> List<List<E>> create(List<? extends List<? extends E>> lists) {
        ImmutableList.Builder builder = new ImmutableList.Builder(lists.size());
        for (List<? extends E> list : lists) {
            ImmutableList copyOf = ImmutableList.copyOf((Collection) list);
            if (copyOf.isEmpty()) {
                return ImmutableList.of();
            }
            builder.add((ImmutableList.Builder) copyOf);
        }
        return new CartesianList(builder.build());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CartesianList(ImmutableList<List<E>> axes) {
        this.axes = axes;
        int[] iArr = new int[axes.size() + 1];
        iArr[axes.size()] = 1;
        try {
            for (int size = axes.size() - 1; size >= 0; size--) {
                iArr[size] = IntMath.checkedMultiply(iArr[size + 1], axes.get(size).size());
            }
            this.axesSizeProduct = iArr;
        } catch (ArithmeticException unused) {
            throw new IllegalArgumentException("Cartesian product too large; must have size at most Integer.MAX_VALUE");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAxisIndexForProductIndex(int index, int axis) {
        return (index / this.axesSizeProduct[axis + 1]) % this.axes.get(axis).size();
    }

    @Override // java.util.AbstractList, java.util.List
    public int indexOf(@CheckForNull Object o) {
        if (o instanceof List) {
            List list = (List) o;
            if (list.size() != this.axes.size()) {
                return -1;
            }
            ListIterator<E> listIterator = list.listIterator();
            int i = 0;
            while (listIterator.hasNext()) {
                int nextIndex = listIterator.nextIndex();
                int indexOf = this.axes.get(nextIndex).indexOf(listIterator.next());
                if (indexOf == -1) {
                    return -1;
                }
                i += indexOf * this.axesSizeProduct[nextIndex + 1];
            }
            return i;
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public int lastIndexOf(@CheckForNull Object o) {
        if (o instanceof List) {
            List list = (List) o;
            if (list.size() != this.axes.size()) {
                return -1;
            }
            ListIterator<E> listIterator = list.listIterator();
            int i = 0;
            while (listIterator.hasNext()) {
                int nextIndex = listIterator.nextIndex();
                int lastIndexOf = this.axes.get(nextIndex).lastIndexOf(listIterator.next());
                if (lastIndexOf == -1) {
                    return -1;
                }
                i += lastIndexOf * this.axesSizeProduct[nextIndex + 1];
            }
            return i;
        }
        return -1;
    }

    @Override // java.util.AbstractList, java.util.List
    public ImmutableList<E> get(final int index) {
        Preconditions.checkElementIndex(index, size());
        return new ImmutableList<E>() { // from class: com.google.common.collect.CartesianList.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // com.google.common.collect.ImmutableCollection
            public boolean isPartialView() {
                return true;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return CartesianList.this.axes.size();
            }

            @Override // java.util.List
            public E get(int axis) {
                Preconditions.checkElementIndex(axis, size());
                return (E) ((List) CartesianList.this.axes.get(axis)).get(CartesianList.this.getAxisIndexForProductIndex(index, axis));
            }
        };
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.axesSizeProduct[0];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(@CheckForNull Object object) {
        if (object instanceof List) {
            List<E> list = (List) object;
            if (list.size() != this.axes.size()) {
                return false;
            }
            int i = 0;
            for (E e : list) {
                if (!this.axes.get(i).contains(e)) {
                    return false;
                }
                i++;
            }
            return true;
        }
        return false;
    }
}
