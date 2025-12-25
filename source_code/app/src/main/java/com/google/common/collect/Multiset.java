package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface Multiset<E> extends Collection<E> {

    /* loaded from: classes3.dex */
    public interface Entry<E> {
        boolean equals(@CheckForNull Object o);

        int getCount();

        @ParametricNullness
        E getElement();

        int hashCode();

        String toString();
    }

    int add(@ParametricNullness E element, int occurrences);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean add(@ParametricNullness E element);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean contains(@CheckForNull Object element);

    @Override // java.util.Collection
    boolean containsAll(Collection<?> elements);

    int count(@CheckForNull Object element);

    Set<E> elementSet();

    Set<Entry<E>> entrySet();

    @Override // com.google.common.collect.Multiset
    boolean equals(@CheckForNull Object object);

    @Override // com.google.common.collect.Multiset
    int hashCode();

    @Override // java.util.Collection, java.lang.Iterable, com.google.common.collect.Multiset
    Iterator<E> iterator();

    int remove(@CheckForNull Object element, int occurrences);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean remove(@CheckForNull Object element);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean removeAll(Collection<?> c);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    boolean retainAll(Collection<?> c);

    int setCount(@ParametricNullness E element, int count);

    boolean setCount(@ParametricNullness E element, int oldCount, int newCount);

    @Override // java.util.Collection, com.google.common.collect.Multiset
    int size();

    String toString();
}
