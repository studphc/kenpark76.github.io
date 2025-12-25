package com.google.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.concurrent.LazyInit;
import j$.util.Objects;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class ImmutableListMultimap<K, V> extends ImmutableMultimap<K, V> implements ListMultimap<K, V> {
    private static final long serialVersionUID = 0;
    @CheckForNull
    @LazyInit
    private transient ImmutableListMultimap<V, K> inverse;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ ImmutableCollection get(Object key) {
        return get((ImmutableListMultimap<K, V>) key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ Collection get(Object key) {
        return get((ImmutableListMultimap<K, V>) key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.Multimap
    public /* bridge */ /* synthetic */ List get(Object key) {
        return get((ImmutableListMultimap<K, V>) key);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @Deprecated
    public /* bridge */ /* synthetic */ ImmutableCollection replaceValues(Object key, Iterable values) {
        return replaceValues((ImmutableListMultimap<K, V>) key, values);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @Deprecated
    public /* bridge */ /* synthetic */ Collection replaceValues(Object key, Iterable values) {
        return replaceValues((ImmutableListMultimap<K, V>) key, values);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @Deprecated
    public /* bridge */ /* synthetic */ List replaceValues(Object key, Iterable values) {
        return replaceValues((ImmutableListMultimap<K, V>) key, values);
    }

    public static <K, V> ImmutableListMultimap<K, V> of() {
        return EmptyImmutableListMultimap.INSTANCE;
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1) {
        Builder builder = builder();
        builder.put((Builder) k1, (K) v1);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2) {
        Builder builder = builder();
        builder.put((Builder) k1, (K) v1);
        builder.put((Builder) k2, (K) v2);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        Builder builder = builder();
        builder.put((Builder) k1, (K) v1);
        builder.put((Builder) k2, (K) v2);
        builder.put((Builder) k3, (K) v3);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder builder = builder();
        builder.put((Builder) k1, (K) v1);
        builder.put((Builder) k2, (K) v2);
        builder.put((Builder) k3, (K) v3);
        builder.put((Builder) k4, (K) v4);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder builder = builder();
        builder.put((Builder) k1, (K) v1);
        builder.put((Builder) k2, (K) v2);
        builder.put((Builder) k3, (K) v3);
        builder.put((Builder) k4, (K) v4);
        builder.put((Builder) k5, (K) v5);
        return builder.build();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    /* loaded from: classes3.dex */
    public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public /* bridge */ /* synthetic */ ImmutableMultimap.Builder put(Object key, Object value) {
            return put((Builder<K, V>) key, value);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public /* bridge */ /* synthetic */ ImmutableMultimap.Builder putAll(Object key, Iterable values) {
            return putAll((Builder<K, V>) key, values);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public /* bridge */ /* synthetic */ ImmutableMultimap.Builder putAll(Object key, Object[] values) {
            return putAll((Builder<K, V>) key, values);
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> put(K key, V value) {
            super.put((Builder<K, V>) key, (K) value);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll((Iterable) entries);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> putAll(K key, Iterable<? extends V> values) {
            super.putAll((Builder<K, V>) key, (Iterable) values);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> putAll(K key, V... values) {
            super.putAll((Builder<K, V>) key, (Object[]) values);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            super.putAll((Multimap) multimap);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> orderKeysBy(Comparator<? super K> keyComparator) {
            super.orderKeysBy((Comparator) keyComparator);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> orderValuesBy(Comparator<? super V> valueComparator) {
            super.orderValuesBy((Comparator) valueComparator);
            return this;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public Builder<K, V> combine(ImmutableMultimap.Builder<K, V> other) {
            super.combine((ImmutableMultimap.Builder) other);
            return this;
        }

        @Override // com.google.common.collect.ImmutableMultimap.Builder
        public ImmutableListMultimap<K, V> build() {
            return (ImmutableListMultimap) super.build();
        }
    }

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap.isEmpty()) {
            return of();
        }
        if (multimap instanceof ImmutableListMultimap) {
            ImmutableListMultimap<K, V> immutableListMultimap = (ImmutableListMultimap) multimap;
            if (!immutableListMultimap.isPartialView()) {
                return immutableListMultimap;
            }
        }
        return fromMapEntries(multimap.asMap().entrySet(), null);
    }

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return new Builder().putAll((Iterable) entries).build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K, V> ImmutableListMultimap<K, V> fromMapEntries(Collection<? extends Map.Entry<? extends K, ? extends Collection<? extends V>>> mapEntries, @CheckForNull Comparator<? super V> valueComparator) {
        ImmutableList sortedCopyOf;
        if (mapEntries.isEmpty()) {
            return of();
        }
        ImmutableMap.Builder builder = new ImmutableMap.Builder(mapEntries.size());
        int i = 0;
        for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : mapEntries) {
            K key = entry.getKey();
            Collection<? extends V> value = entry.getValue();
            if (valueComparator == null) {
                sortedCopyOf = ImmutableList.copyOf((Collection) value);
            } else {
                sortedCopyOf = ImmutableList.sortedCopyOf(valueComparator, value);
            }
            if (!sortedCopyOf.isEmpty()) {
                builder.put(key, sortedCopyOf);
                i += sortedCopyOf.size();
            }
        }
        return new ImmutableListMultimap<>(builder.buildOrThrow(), i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> map, int size) {
        super(map, size);
    }

    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.Multimap
    public ImmutableList<V> get(K key) {
        ImmutableList<V> immutableList = (ImmutableList) this.map.get(key);
        return immutableList == null ? ImmutableList.of() : immutableList;
    }

    @Override // com.google.common.collect.ImmutableMultimap
    public ImmutableListMultimap<V, K> inverse() {
        ImmutableListMultimap<V, K> immutableListMultimap = this.inverse;
        if (immutableListMultimap == null) {
            ImmutableListMultimap<V, K> invert = invert();
            this.inverse = invert;
            return invert;
        }
        return immutableListMultimap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private ImmutableListMultimap<V, K> invert() {
        Builder builder = builder();
        UnmodifiableIterator it = entries().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            builder.put((Builder) entry.getValue(), entry.getKey());
        }
        ImmutableListMultimap<V, K> build = builder.build();
        build.inverse = this;
        return build;
    }

    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.Multimap
    @Deprecated
    public final ImmutableList<V> removeAll(@CheckForNull Object key) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.ImmutableMultimap, com.google.common.collect.AbstractMultimap, com.google.common.collect.Multimap
    @Deprecated
    public final ImmutableList<V> replaceValues(K key, Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMultimap(this, stream);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int readInt = stream.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException("Invalid key count " + readInt);
        }
        ImmutableMap.Builder builder = ImmutableMap.builder();
        int i = 0;
        for (int i2 = 0; i2 < readInt; i2++) {
            Object requireNonNull = Objects.requireNonNull(stream.readObject());
            int readInt2 = stream.readInt();
            if (readInt2 <= 0) {
                throw new InvalidObjectException("Invalid value count " + readInt2);
            }
            ImmutableList.Builder builder2 = ImmutableList.builder();
            for (int i3 = 0; i3 < readInt2; i3++) {
                builder2.add((ImmutableList.Builder) Objects.requireNonNull(stream.readObject()));
            }
            builder.put(requireNonNull, builder2.build());
            i += readInt2;
        }
        try {
            ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((Serialization.FieldSetter<ImmutableMultimap>) this, (Object) builder.buildOrThrow());
            ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((Serialization.FieldSetter<ImmutableMultimap>) this, i);
        } catch (IllegalArgumentException e) {
            throw ((InvalidObjectException) new InvalidObjectException(e.getMessage()).initCause(e));
        }
    }
}
