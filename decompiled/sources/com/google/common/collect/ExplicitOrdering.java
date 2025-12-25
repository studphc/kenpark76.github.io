package com.google.common.collect;

import com.google.common.collect.Ordering;
import java.io.Serializable;
import java.util.List;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableMap<T, Integer> rankMap;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExplicitOrdering(List<T> valuesInOrder) {
        this(Maps.indexMap(valuesInOrder));
    }

    ExplicitOrdering(ImmutableMap<T, Integer> rankMap) {
        this.rankMap = rankMap;
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(T left, T right) {
        return rank(left) - rank(right);
    }

    private int rank(T value) {
        Integer num = this.rankMap.get(value);
        if (num == null) {
            throw new Ordering.IncomparableValueException(value);
        }
        return num.intValue();
    }

    @Override // java.util.Comparator
    public boolean equals(@CheckForNull Object object) {
        if (object instanceof ExplicitOrdering) {
            return this.rankMap.equals(((ExplicitOrdering) object).rankMap);
        }
        return false;
    }

    public int hashCode() {
        return this.rankMap.hashCode();
    }

    public String toString() {
        return "Ordering.explicit(" + this.rankMap.keySet() + ")";
    }
}
