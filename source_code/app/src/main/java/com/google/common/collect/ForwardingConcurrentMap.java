package com.google.common.collect;

import j$.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingConcurrentMap<K, V> extends ForwardingMap<K, V> implements ConcurrentMap<K, V>, j$.util.concurrent.ConcurrentMap {
    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object compute(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$compute(this, obj, biFunction);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object computeIfAbsent(Object obj, Function function) {
        return ConcurrentMap.CC.$default$computeIfAbsent(this, obj, function);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object computeIfPresent(Object obj, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$computeIfPresent(this, obj, biFunction);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingMap, com.google.common.collect.ForwardingObject
    public abstract java.util.concurrent.ConcurrentMap<K, V> delegate();

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ void forEach(BiConsumer biConsumer) {
        ConcurrentMap.CC.$default$forEach(this, biConsumer);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return ConcurrentMap.CC.$default$getOrDefault(this, obj, obj2);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return ConcurrentMap.CC.$default$merge(this, obj, obj2, biFunction);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.concurrent.ConcurrentMap, j$.util.Map
    public /* synthetic */ void replaceAll(BiFunction biFunction) {
        ConcurrentMap.CC.$default$replaceAll(this, biFunction);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    @CheckForNull
    public V putIfAbsent(K key, V value) {
        return delegate().putIfAbsent(key, value);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public boolean remove(@CheckForNull Object key, @CheckForNull Object value) {
        return delegate().remove(key, value);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    @CheckForNull
    public V replace(K key, V value) {
        return delegate().replace(key, value);
    }

    @Override // java.util.Map, java.util.concurrent.ConcurrentMap, j$.util.Map
    public boolean replace(K key, V oldValue, V newValue) {
        return delegate().replace(key, oldValue, newValue);
    }
}
