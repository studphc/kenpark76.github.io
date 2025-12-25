package com.google.common.util.concurrent;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AggregateFuture;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {
    @CheckForNull
    private List<Present<V>> values;

    abstract C combine(List<Present<V>> values);

    CollectionFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
        super(futures, allMustSucceed, true);
        List<Present<V>> newArrayListWithCapacity;
        if (futures.isEmpty()) {
            newArrayListWithCapacity = Collections.emptyList();
        } else {
            newArrayListWithCapacity = Lists.newArrayListWithCapacity(futures.size());
        }
        for (int i = 0; i < futures.size(); i++) {
            newArrayListWithCapacity.add(null);
        }
        this.values = newArrayListWithCapacity;
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    final void collectOneValue(int index, @ParametricNullness V returnValue) {
        List<Present<V>> list = this.values;
        if (list != null) {
            list.set(index, new Present<>(returnValue));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.util.concurrent.AggregateFuture
    final void handleAllCompleted() {
        List<Present<V>> list = this.values;
        if (list != null) {
            set(combine(list));
        }
    }

    @Override // com.google.common.util.concurrent.AggregateFuture
    void releaseResources(AggregateFuture.ReleaseResourcesReason reason) {
        super.releaseResources(reason);
        this.values = null;
    }

    /* loaded from: classes3.dex */
    static final class ListFuture<V> extends CollectionFuture<V, List<V>> {
        /* JADX INFO: Access modifiers changed from: package-private */
        public ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
            super(futures, allMustSucceed);
            init();
        }

        @Override // com.google.common.util.concurrent.CollectionFuture
        public List<V> combine(List<Present<V>> values) {
            ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(values.size());
            Iterator<Present<V>> it = values.iterator();
            while (it.hasNext()) {
                Present<V> next = it.next();
                newArrayListWithCapacity.add(next != null ? next.value : null);
            }
            return Collections.unmodifiableList(newArrayListWithCapacity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class Present<V> {
        @ParametricNullness
        final V value;

        Present(@ParametricNullness V value) {
            this.value = value;
        }
    }
}
