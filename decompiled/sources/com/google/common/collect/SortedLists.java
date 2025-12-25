package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class SortedLists {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum KeyAbsentBehavior {
        NEXT_LOWER { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.1
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            int resultIndex(int higherIndex) {
                return higherIndex - 1;
            }
        },
        NEXT_HIGHER { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.2
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            public int resultIndex(int higherIndex) {
                return higherIndex;
            }
        },
        INVERTED_INSERTION_INDEX { // from class: com.google.common.collect.SortedLists.KeyAbsentBehavior.3
            @Override // com.google.common.collect.SortedLists.KeyAbsentBehavior
            public int resultIndex(int higherIndex) {
                return ~higherIndex;
            }
        };

        abstract int resultIndex(int higherIndex);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public enum KeyPresentBehavior {
        ANY_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.1
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex) {
                return foundIndex;
            }
        },
        LAST_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex) {
                int size = list.size() - 1;
                while (foundIndex < size) {
                    int i = ((foundIndex + size) + 1) >>> 1;
                    if (comparator.compare((E) list.get(i), key) > 0) {
                        size = i - 1;
                    } else {
                        foundIndex = i;
                    }
                }
                return foundIndex;
            }
        },
        FIRST_PRESENT { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex) {
                int i = 0;
                while (i < foundIndex) {
                    int i2 = (i + foundIndex) >>> 1;
                    if (comparator.compare((E) list.get(i2), key) < 0) {
                        i = i2 + 1;
                    } else {
                        foundIndex = i2;
                    }
                }
                return i;
            }
        },
        FIRST_AFTER { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.4
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex) {
                return LAST_PRESENT.resultIndex(comparator, key, list, foundIndex) + 1;
            }
        },
        LAST_BEFORE { // from class: com.google.common.collect.SortedLists.KeyPresentBehavior.5
            @Override // com.google.common.collect.SortedLists.KeyPresentBehavior
            public <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex) {
                return FIRST_PRESENT.resultIndex(comparator, key, list, foundIndex) - 1;
            }
        };

        abstract <E> int resultIndex(Comparator<? super E> comparator, @ParametricNullness E key, List<? extends E> list, int foundIndex);
    }

    private SortedLists() {
    }

    public static <E extends Comparable> int binarySearch(List<? extends E> list, E e, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        Preconditions.checkNotNull(e);
        return binarySearch(list, e, Ordering.natural(), presentBehavior, absentBehavior);
    }

    public static <E, K extends Comparable> int binarySearch(List<E> list, Function<? super E, K> keyFunction, K key, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        Preconditions.checkNotNull(key);
        return binarySearch(list, keyFunction, key, Ordering.natural(), presentBehavior, absentBehavior);
    }

    public static <E, K> int binarySearch(List<E> list, Function<? super E, K> keyFunction, @ParametricNullness K key, Comparator<? super K> keyComparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        return binarySearch(Lists.transform(list, keyFunction), key, keyComparator, presentBehavior, absentBehavior);
    }

    public static <E> int binarySearch(List<? extends E> list, @ParametricNullness E key, Comparator<? super E> comparator, KeyPresentBehavior presentBehavior, KeyAbsentBehavior absentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(presentBehavior);
        Preconditions.checkNotNull(absentBehavior);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList(list);
        }
        int size = list.size() - 1;
        int i = 0;
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            int compare = comparator.compare(key, (E) list.get(i2));
            if (compare < 0) {
                size = i2 - 1;
            } else if (compare <= 0) {
                return i + presentBehavior.resultIndex(comparator, key, list.subList(i, size + 1), i2 - i);
            } else {
                i = i2 + 1;
            }
        }
        return absentBehavior.resultIndex(i);
    }
}
