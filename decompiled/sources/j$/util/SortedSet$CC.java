package j$.util;

import j$.util.Spliterators;
import java.util.SortedSet;
/* renamed from: j$.util.SortedSet$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class SortedSet$CC {
    public static Spliterator $default$spliterator(final SortedSet sortedSet) {
        return new Spliterators.IteratorSpliterator(sortedSet, 21) { // from class: j$.util.SortedSet$1
            @Override // j$.util.Spliterators.IteratorSpliterator, j$.util.Spliterator
            public java.util.Comparator getComparator() {
                return sortedSet.comparator();
            }
        };
    }
}
