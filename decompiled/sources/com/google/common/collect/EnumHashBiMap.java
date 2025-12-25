package com.google.common.collect;

import com.google.common.base.Preconditions;
import j$.util.Objects;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.Enum;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class EnumHashBiMap<K extends Enum<K>, V> extends AbstractBiMap<K, V> {
    private static final long serialVersionUID = 0;
    transient Class<K> keyTypeOrObjectUnderJ2cl;

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractBiMap
    /* bridge */ /* synthetic */ Object checkKey(Object key) {
        return checkKey((EnumHashBiMap<K, V>) ((Enum) key));
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.BiMap
    @CheckForNull
    public /* bridge */ /* synthetic */ Object forcePut(Object key, @ParametricNullness Object value) {
        return forcePut((EnumHashBiMap<K, V>) ((Enum) key), (Enum) value);
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.BiMap
    public /* bridge */ /* synthetic */ BiMap inverse() {
        return super.inverse();
    }

    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map
    public /* bridge */ /* synthetic */ Set keySet() {
        return super.keySet();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.common.collect.AbstractBiMap, com.google.common.collect.ForwardingMap, java.util.Map, com.google.common.collect.BiMap
    @CheckForNull
    public /* bridge */ /* synthetic */ Object put(Object key, @ParametricNullness Object value) {
        return put((EnumHashBiMap<K, V>) ((Enum) key), (Enum) value);
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

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Class<K> keyType) {
        return new EnumHashBiMap<>(keyType);
    }

    public static <K extends Enum<K>, V> EnumHashBiMap<K, V> create(Map<K, ? extends V> map) {
        EnumHashBiMap<K, V> create = create(EnumBiMap.inferKeyTypeOrObjectUnderJ2cl(map));
        create.putAll(map);
        return create;
    }

    private EnumHashBiMap(Class<K> keyType) {
        super(new EnumMap(keyType), new HashMap());
        this.keyTypeOrObjectUnderJ2cl = keyType;
    }

    K checkKey(K key) {
        return (K) Preconditions.checkNotNull(key);
    }

    @CheckForNull
    public V put(K key, @ParametricNullness V value) {
        return (V) super.put((EnumHashBiMap<K, V>) key, (K) value);
    }

    @CheckForNull
    public V forcePut(K key, @ParametricNullness V value) {
        return (V) super.forcePut((EnumHashBiMap<K, V>) key, (K) value);
    }

    public Class<K> keyType() {
        return this.keyTypeOrObjectUnderJ2cl;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(this.keyTypeOrObjectUnderJ2cl);
        Serialization.writeMap(this, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.keyTypeOrObjectUnderJ2cl = (Class) Objects.requireNonNull(stream.readObject());
        setDelegates(new EnumMap(this.keyTypeOrObjectUnderJ2cl), new HashMap());
        Serialization.populateMap(this, stream);
    }
}
