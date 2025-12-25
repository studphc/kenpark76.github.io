package com.google.common.collect;

import com.google.common.base.Preconditions;
import j$.util.Objects;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class EnumBiMap<K extends Enum<K>, V extends Enum<V>> extends AbstractBiMap<K, V> {
    private static final long serialVersionUID = 0;
    transient Class<K> keyTypeOrObjectUnderJ2cl;
    transient Class<V> valueTypeOrObjectUnderJ2cl;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractBiMap
    /* bridge */ /* synthetic */ Object checkKey(Object key) {
        return checkKey((EnumBiMap<K, V>) ((Enum) key));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractBiMap
    /* bridge */ /* synthetic */ Object checkValue(Object value) {
        return checkValue((EnumBiMap<K, V>) ((Enum) value));
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ boolean containsValue(@CheckForNull Object value) {
        return super.containsValue(value);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.BiMap
    @CheckForNull
    public /* bridge */ /* synthetic */ Object forcePut(@ParametricNullness Object key, @ParametricNullness Object value) {
        return super.forcePut(key, value);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CheckForNull
    public /* bridge */ /* synthetic */ Object put(@ParametricNullness Object key, @ParametricNullness Object value) {
        return super.put(key, value);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ void putAll(Map map) {
        super.putAll(map);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    @CheckForNull
    public /* bridge */ /* synthetic */ Object remove(@CheckForNull Object key) {
        return super.remove(key);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set values() {
        return super.values();
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Class<K> keyType, Class<V> valueType) {
        return new EnumBiMap<>(keyType, valueType);
    }

    public static <K extends Enum<K>, V extends Enum<V>> EnumBiMap<K, V> create(Map<K, V> map) {
        EnumBiMap<K, V> create = create(inferKeyTypeOrObjectUnderJ2cl(map), inferValueTypeOrObjectUnderJ2cl(map));
        create.putAll(map);
        return create;
    }

    private EnumBiMap(Class<K> keyTypeOrObjectUnderJ2cl, Class<V> valueTypeOrObjectUnderJ2cl) {
        super(new EnumMap(keyTypeOrObjectUnderJ2cl), new EnumMap(valueTypeOrObjectUnderJ2cl));
        this.keyTypeOrObjectUnderJ2cl = keyTypeOrObjectUnderJ2cl;
        this.valueTypeOrObjectUnderJ2cl = valueTypeOrObjectUnderJ2cl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K extends Enum<K>> Class<K> inferKeyTypeOrObjectUnderJ2cl(Map<K, ?> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).keyTypeOrObjectUnderJ2cl;
        }
        if (map instanceof EnumHashBiMap) {
            return ((EnumHashBiMap) map).keyTypeOrObjectUnderJ2cl;
        }
        Preconditions.checkArgument(!map.isEmpty());
        return Platform.getDeclaringClassOrObjectForJ2cl(map.keySet().iterator().next());
    }

    private static <V extends Enum<V>> Class<V> inferValueTypeOrObjectUnderJ2cl(Map<?, V> map) {
        if (map instanceof EnumBiMap) {
            return ((EnumBiMap) map).valueTypeOrObjectUnderJ2cl;
        }
        Preconditions.checkArgument(!map.isEmpty());
        return Platform.getDeclaringClassOrObjectForJ2cl(map.values().iterator().next());
    }

    public Class<K> keyType() {
        return this.keyTypeOrObjectUnderJ2cl;
    }

    public Class<V> valueType() {
        return this.valueTypeOrObjectUnderJ2cl;
    }

    K checkKey(K key) {
        return (K) Preconditions.checkNotNull(key);
    }

    V checkValue(V value) {
        return (V) Preconditions.checkNotNull(value);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.keyTypeOrObjectUnderJ2cl);
        stream.writeObject(this.valueTypeOrObjectUnderJ2cl);
        Serialization.writeMap(this, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyTypeOrObjectUnderJ2cl = (Class) Objects.requireNonNull(stream.readObject());
        this.valueTypeOrObjectUnderJ2cl = (Class) Objects.requireNonNull(stream.readObject());
        setDelegates(new EnumMap(this.keyTypeOrObjectUnderJ2cl), new EnumMap(this.valueTypeOrObjectUnderJ2cl));
        Serialization.populateMap(this, stream);
    }
}
