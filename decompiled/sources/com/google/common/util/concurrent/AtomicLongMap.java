package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.errorprone.annotations.concurrent.LazyInit;
import j$.util.concurrent.ConcurrentHashMap;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class AtomicLongMap<K> implements Serializable {
    @CheckForNull
    @LazyInit
    private transient Map<K, Long> asMap;
    private final ConcurrentHashMap<K, AtomicLong> map;

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> map) {
        this.map = (ConcurrentHashMap) Preconditions.checkNotNull(map);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap<>(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> m) {
        AtomicLongMap<K> create = create();
        create.putAll(m);
        return create;
    }

    public long get(K key) {
        AtomicLong atomicLong = this.map.get(key);
        if (atomicLong == null) {
            return 0L;
        }
        return atomicLong.get();
    }

    public long incrementAndGet(K key) {
        return addAndGet(key, 1L);
    }

    public long decrementAndGet(K key) {
        return addAndGet(key, -1L);
    }

    public long addAndGet(K key, long delta) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(key);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(key, new AtomicLong(delta))) == null) {
                return delta;
            }
            while (true) {
                long j = atomicLong.get();
                if (j == 0) {
                    break;
                }
                long j2 = j + delta;
                if (atomicLong.compareAndSet(j, j2)) {
                    return j2;
                }
            }
        } while (!this.map.replace(key, atomicLong, new AtomicLong(delta)));
        return delta;
    }

    public long getAndIncrement(K key) {
        return getAndAdd(key, 1L);
    }

    public long getAndDecrement(K key) {
        return getAndAdd(key, -1L);
    }

    public long getAndAdd(K key, long delta) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(key);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(key, new AtomicLong(delta))) == null) {
                return 0L;
            }
            while (true) {
                long j = atomicLong.get();
                if (j == 0) {
                    break;
                } else if (atomicLong.compareAndSet(j, j + delta)) {
                    return j;
                }
            }
        } while (!this.map.replace(key, atomicLong, new AtomicLong(delta)));
        return 0L;
    }

    public long put(K key, long newValue) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(key);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(key, new AtomicLong(newValue))) == null) {
                return 0L;
            }
            while (true) {
                long j = atomicLong.get();
                if (j == 0) {
                    break;
                } else if (atomicLong.compareAndSet(j, newValue)) {
                    return j;
                }
            }
        } while (!this.map.replace(key, atomicLong, new AtomicLong(newValue)));
        return 0L;
    }

    public void putAll(Map<? extends K, ? extends Long> m) {
        for (Map.Entry<? extends K, ? extends Long> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue().longValue());
        }
    }

    public long remove(K key) {
        long j;
        AtomicLong atomicLong = this.map.get(key);
        if (atomicLong == null) {
            return 0L;
        }
        do {
            j = atomicLong.get();
            if (j == 0) {
                break;
            }
        } while (!atomicLong.compareAndSet(j, 0L));
        this.map.remove(key, atomicLong);
        return j;
    }

    boolean remove(K key, long value) {
        AtomicLong atomicLong = this.map.get(key);
        if (atomicLong == null) {
            return false;
        }
        long j = atomicLong.get();
        if (j != value) {
            return false;
        }
        if (j == 0 || atomicLong.compareAndSet(j, 0L)) {
            this.map.remove(key, atomicLong);
            return true;
        }
        return false;
    }

    public boolean removeIfZero(K key) {
        return remove(key, 0L);
    }

    public void removeAllZeros() {
        Iterator<Map.Entry<K, AtomicLong>> it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            AtomicLong value = it.next().getValue();
            if (value != null && value.get() == 0) {
                it.remove();
            }
        }
    }

    public long sum() {
        long j = 0;
        for (AtomicLong atomicLong : this.map.values()) {
            j += atomicLong.get();
        }
        return j;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> map = this.asMap;
        if (map == null) {
            Map<K, Long> createAsMap = createAsMap();
            this.asMap = createAsMap;
            return createAsMap;
        }
        return map;
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(Maps.transformValues(this.map, new Function<AtomicLong, Long>(this) { // from class: com.google.common.util.concurrent.AtomicLongMap.1
            @Override // com.google.common.base.Function
            public Long apply(AtomicLong atomic) {
                return Long.valueOf(atomic.get());
            }
        }));
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    long putIfAbsent(K key, long newValue) {
        AtomicLong atomicLong;
        do {
            atomicLong = this.map.get(key);
            if (atomicLong == null && (atomicLong = this.map.putIfAbsent(key, new AtomicLong(newValue))) == null) {
                return 0L;
            }
            long j = atomicLong.get();
            if (j != 0) {
                return j;
            }
        } while (!this.map.replace(key, atomicLong, new AtomicLong(newValue)));
        return 0L;
    }

    boolean replace(K key, long expectedOldValue, long newValue) {
        if (expectedOldValue == 0) {
            return putIfAbsent(key, newValue) == 0;
        }
        AtomicLong atomicLong = this.map.get(key);
        if (atomicLong == null) {
            return false;
        }
        return atomicLong.compareAndSet(expectedOldValue, newValue);
    }
}
