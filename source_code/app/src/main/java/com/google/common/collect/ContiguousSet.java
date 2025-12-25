package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSortedSet;
import j$.util.Objects;
import java.lang.Comparable;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ContiguousSet<C extends Comparable> extends ImmutableSortedSet<C> {
    final DiscreteDomain<C> domain;

    abstract ContiguousSet<C> headSetImpl(C toElement, boolean inclusive);

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> other);

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType lowerBoundType, BoundType upperBoundType);

    abstract ContiguousSet<C> subSetImpl(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive);

    abstract ContiguousSet<C> tailSetImpl(C fromElement, boolean inclusive);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet headSet(Object toElement) {
        return headSet((ContiguousSet<C>) ((Comparable) toElement));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet headSet(Object toElement, boolean inclusive) {
        return headSet((ContiguousSet<C>) ((Comparable) toElement), inclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet headSet(Object toElement, boolean inclusive) {
        return headSet((ContiguousSet<C>) ((Comparable) toElement), inclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet headSet(Object toElement) {
        return headSet((ContiguousSet<C>) ((Comparable) toElement));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet headSetImpl(Object toElement, boolean inclusive) {
        return headSetImpl((ContiguousSet<C>) ((Comparable) toElement), inclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
        return subSet((boolean) ((Comparable) fromElement), fromInclusive, (boolean) ((Comparable) toElement), toInclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet subSet(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
        return subSet((boolean) ((Comparable) fromElement), fromInclusive, (boolean) ((Comparable) toElement), toInclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    /* bridge */ /* synthetic */ ImmutableSortedSet subSetImpl(Object fromElement, boolean fromInclusive, Object toElement, boolean toInclusive) {
        return subSetImpl((boolean) ((Comparable) fromElement), fromInclusive, (boolean) ((Comparable) toElement), toInclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet tailSet(Object fromElement) {
        return tailSet((ContiguousSet<C>) ((Comparable) fromElement));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ ImmutableSortedSet tailSet(Object fromElement, boolean inclusive) {
        return tailSet((ContiguousSet<C>) ((Comparable) fromElement), inclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet
    public /* bridge */ /* synthetic */ NavigableSet tailSet(Object fromElement, boolean inclusive) {
        return tailSet((ContiguousSet<C>) ((Comparable) fromElement), inclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet, java.util.SortedSet
    public /* bridge */ /* synthetic */ SortedSet tailSet(Object fromElement) {
        return tailSet((ContiguousSet<C>) ((Comparable) fromElement));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableSortedSet
    /* bridge */ /* synthetic */ ImmutableSortedSet tailSetImpl(Object fromElement, boolean inclusive) {
        return tailSetImpl((ContiguousSet<C>) ((Comparable) fromElement), inclusive);
    }

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> range, DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(range);
        Preconditions.checkNotNull(domain);
        try {
            Range<C> intersection = !range.hasLowerBound() ? range.intersection(Range.atLeast(domain.minValue())) : range;
            if (!range.hasUpperBound()) {
                intersection = intersection.intersection(Range.atMost(domain.maxValue()));
            }
            boolean z = true;
            if (!intersection.isEmpty() && Range.compareOrThrow((Comparable) Objects.requireNonNull(range.lowerBound.leastValueAbove(domain)), (Comparable) Objects.requireNonNull(range.upperBound.greatestValueBelow(domain))) <= 0) {
                z = false;
            }
            if (z) {
                return new EmptyContiguousSet(domain);
            }
            return new RegularContiguousSet(intersection, domain);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static ContiguousSet<Integer> closed(int lower, int upper) {
        return create(Range.closed(Integer.valueOf(lower), Integer.valueOf(upper)), DiscreteDomain.integers());
    }

    public static ContiguousSet<Long> closed(long lower, long upper) {
        return create(Range.closed(Long.valueOf(lower), Long.valueOf(upper)), DiscreteDomain.longs());
    }

    public static ContiguousSet<Integer> closedOpen(int lower, int upper) {
        return create(Range.closedOpen(Integer.valueOf(lower), Integer.valueOf(upper)), DiscreteDomain.integers());
    }

    public static ContiguousSet<Long> closedOpen(long lower, long upper) {
        return create(Range.closedOpen(Long.valueOf(lower), Long.valueOf(upper)), DiscreteDomain.longs());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ContiguousSet(DiscreteDomain<C> domain) {
        super(Ordering.natural());
        this.domain = domain;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> headSet(C toElement) {
        return headSetImpl((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(toElement)), false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> headSet(C toElement, boolean inclusive) {
        return headSetImpl((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(toElement)), inclusive);
    }

    @Override // com.google.common.collect.ImmutableSortedSet, java.util.NavigableSet, java.util.SortedSet
    public ContiguousSet<C> subSet(C fromElement, C toElement) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(comparator().compare(fromElement, toElement) <= 0);
        return subSetImpl((boolean) fromElement, true, (boolean) toElement, false);
    }

    public ContiguousSet<C> subSet(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
        Preconditions.checkNotNull(fromElement);
        Preconditions.checkNotNull(toElement);
        Preconditions.checkArgument(comparator().compare(fromElement, toElement) <= 0);
        return subSetImpl((boolean) fromElement, fromInclusive, (boolean) toElement, toInclusive);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> tailSet(C fromElement) {
        return tailSetImpl((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(fromElement)), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ContiguousSet<C> tailSet(C fromElement, boolean inclusive) {
        return tailSetImpl((ContiguousSet<C>) ((Comparable) Preconditions.checkNotNull(fromElement)), inclusive);
    }

    @Override // com.google.common.collect.ImmutableSortedSet
    ImmutableSortedSet<C> createDescendingSet() {
        return new DescendingImmutableSortedSet(this);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        return range().toString();
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }
}
