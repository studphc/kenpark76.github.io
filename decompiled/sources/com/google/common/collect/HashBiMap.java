package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap<K, V>, Serializable {
    private static final int ABSENT = -1;
    private static final int ENDPOINT = -2;
    @LazyInit
    private transient Set<Map.Entry<K, V>> entrySet;
    private transient int firstInInsertionOrder;
    private transient int[] hashTableKToV;
    private transient int[] hashTableVToK;
    @CheckForNull
    @LazyInit
    private transient BiMap<V, K> inverse;
    @LazyInit
    private transient Set<K> keySet;
    transient K[] keys;
    private transient int lastInInsertionOrder;
    transient int modCount;
    private transient int[] nextInBucketKToV;
    private transient int[] nextInBucketVToK;
    private transient int[] nextInInsertionOrder;
    private transient int[] prevInInsertionOrder;
    transient int size;
    @LazyInit
    private transient Set<V> valueSet;
    transient V[] values;

    public static <K, V> HashBiMap<K, V> create() {
        return create(16);
    }

    public static <K, V> HashBiMap<K, V> create(int expectedSize) {
        return new HashBiMap<>(expectedSize);
    }

    public static <K, V> HashBiMap<K, V> create(Map<? extends K, ? extends V> map) {
        HashBiMap<K, V> create = create(map.size());
        create.putAll(map);
        return create;
    }

    private HashBiMap(int expectedSize) {
        init(expectedSize);
    }

    void init(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        int closedTableSize = Hashing.closedTableSize(expectedSize, 1.0d);
        this.size = 0;
        this.keys = (K[]) new Object[expectedSize];
        this.values = (V[]) new Object[expectedSize];
        this.hashTableKToV = createFilledWithAbsent(closedTableSize);
        this.hashTableVToK = createFilledWithAbsent(closedTableSize);
        this.nextInBucketKToV = createFilledWithAbsent(expectedSize);
        this.nextInBucketVToK = createFilledWithAbsent(expectedSize);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(expectedSize);
        this.nextInInsertionOrder = createFilledWithAbsent(expectedSize);
    }

    private static int[] createFilledWithAbsent(int size) {
        int[] iArr = new int[size];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static int[] expandAndFillWithAbsent(int[] array, int newSize) {
        int length = array.length;
        int[] copyOf = Arrays.copyOf(array, newSize);
        Arrays.fill(copyOf, length, newSize, -1);
        return copyOf;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.size;
    }

    private void ensureCapacity(int minCapacity) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < minCapacity) {
            int expandedCapacity = ImmutableCollection.Builder.expandedCapacity(iArr.length, minCapacity);
            this.keys = (K[]) Arrays.copyOf(this.keys, expandedCapacity);
            this.values = (V[]) Arrays.copyOf(this.values, expandedCapacity);
            this.nextInBucketKToV = expandAndFillWithAbsent(this.nextInBucketKToV, expandedCapacity);
            this.nextInBucketVToK = expandAndFillWithAbsent(this.nextInBucketVToK, expandedCapacity);
            this.prevInInsertionOrder = expandAndFillWithAbsent(this.prevInInsertionOrder, expandedCapacity);
            this.nextInInsertionOrder = expandAndFillWithAbsent(this.nextInInsertionOrder, expandedCapacity);
        }
        if (this.hashTableKToV.length < minCapacity) {
            int closedTableSize = Hashing.closedTableSize(minCapacity, 1.0d);
            this.hashTableKToV = createFilledWithAbsent(closedTableSize);
            this.hashTableVToK = createFilledWithAbsent(closedTableSize);
            for (int i = 0; i < this.size; i++) {
                int bucket = bucket(Hashing.smearedHash(this.keys[i]));
                int[] iArr2 = this.nextInBucketKToV;
                int[] iArr3 = this.hashTableKToV;
                iArr2[i] = iArr3[bucket];
                iArr3[bucket] = i;
                int bucket2 = bucket(Hashing.smearedHash(this.values[i]));
                int[] iArr4 = this.nextInBucketVToK;
                int[] iArr5 = this.hashTableVToK;
                iArr4[i] = iArr5[bucket2];
                iArr5[bucket2] = i;
            }
        }
    }

    private int bucket(int hash) {
        return hash & (this.hashTableKToV.length - 1);
    }

    int findEntryByKey(@CheckForNull Object key) {
        return findEntryByKey(key, Hashing.smearedHash(key));
    }

    int findEntryByKey(@CheckForNull Object key, int keyHash) {
        return findEntry(key, keyHash, this.hashTableKToV, this.nextInBucketKToV, this.keys);
    }

    int findEntryByValue(@CheckForNull Object value) {
        return findEntryByValue(value, Hashing.smearedHash(value));
    }

    int findEntryByValue(@CheckForNull Object value, int valueHash) {
        return findEntry(value, valueHash, this.hashTableVToK, this.nextInBucketVToK, this.values);
    }

    int findEntry(@CheckForNull Object o, int oHash, int[] hashTable, int[] nextInBucket, Object[] array) {
        int i = hashTable[bucket(oHash)];
        while (i != -1) {
            if (Objects.equal(array[i], o)) {
                return i;
            }
            i = nextInBucket[i];
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(@CheckForNull Object key) {
        return findEntryByKey(key) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsValue(@CheckForNull Object value) {
        return findEntryByValue(value) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V get(@CheckForNull Object key) {
        int findEntryByKey = findEntryByKey(key);
        if (findEntryByKey == -1) {
            return null;
        }
        return this.values[findEntryByKey];
    }

    @CheckForNull
    K getInverse(@CheckForNull Object value) {
        int findEntryByValue = findEntryByValue(value);
        if (findEntryByValue == -1) {
            return null;
        }
        return this.keys[findEntryByValue];
    }

    @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
    @CheckForNull
    public V put(@ParametricNullness K key, @ParametricNullness V value) {
        return put(key, value, false);
    }

    @CheckForNull
    V put(@ParametricNullness K key, @ParametricNullness V value, boolean force) {
        int smearedHash = Hashing.smearedHash(key);
        int findEntryByKey = findEntryByKey(key, smearedHash);
        if (findEntryByKey != -1) {
            V v = this.values[findEntryByKey];
            if (Objects.equal(v, value)) {
                return value;
            }
            replaceValueInEntry(findEntryByKey, value, force);
            return v;
        }
        int smearedHash2 = Hashing.smearedHash(value);
        int findEntryByValue = findEntryByValue(value, smearedHash2);
        if (!force) {
            Preconditions.checkArgument(findEntryByValue == -1, "Value already present: %s", value);
        } else if (findEntryByValue != -1) {
            removeEntryValueHashKnown(findEntryByValue, smearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i = this.size;
        kArr[i] = key;
        this.values[i] = value;
        insertIntoTableKToV(i, smearedHash);
        insertIntoTableVToK(this.size, smearedHash2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // com.google.common.collect.BiMap
    @CheckForNull
    public V forcePut(@ParametricNullness K key, @ParametricNullness V value) {
        return put(key, value, true);
    }

    @CheckForNull
    K putInverse(@ParametricNullness V value, @ParametricNullness K key, boolean force) {
        int smearedHash = Hashing.smearedHash(value);
        int findEntryByValue = findEntryByValue(value, smearedHash);
        if (findEntryByValue != -1) {
            K k = this.keys[findEntryByValue];
            if (Objects.equal(k, key)) {
                return key;
            }
            replaceKeyInEntry(findEntryByValue, key, force);
            return k;
        }
        int i = this.lastInInsertionOrder;
        int smearedHash2 = Hashing.smearedHash(key);
        int findEntryByKey = findEntryByKey(key, smearedHash2);
        if (!force) {
            Preconditions.checkArgument(findEntryByKey == -1, "Key already present: %s", key);
        } else if (findEntryByKey != -1) {
            i = this.prevInInsertionOrder[findEntryByKey];
            removeEntryKeyHashKnown(findEntryByKey, smearedHash2);
        }
        ensureCapacity(this.size + 1);
        K[] kArr = this.keys;
        int i2 = this.size;
        kArr[i2] = key;
        this.values[i2] = value;
        insertIntoTableKToV(i2, smearedHash2);
        insertIntoTableVToK(this.size, smearedHash);
        int i3 = i == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i];
        setSucceeds(i, this.size);
        setSucceeds(this.size, i3);
        this.size++;
        this.modCount++;
        return null;
    }

    private void setSucceeds(int prev, int next) {
        if (prev == -2) {
            this.firstInInsertionOrder = next;
        } else {
            this.nextInInsertionOrder[prev] = next;
        }
        if (next == -2) {
            this.lastInInsertionOrder = prev;
        } else {
            this.prevInInsertionOrder[next] = prev;
        }
    }

    private void insertIntoTableKToV(int entry, int keyHash) {
        Preconditions.checkArgument(entry != -1);
        int bucket = bucket(keyHash);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[entry] = iArr2[bucket];
        iArr2[bucket] = entry;
    }

    private void insertIntoTableVToK(int entry, int valueHash) {
        Preconditions.checkArgument(entry != -1);
        int bucket = bucket(valueHash);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[entry] = iArr2[bucket];
        iArr2[bucket] = entry;
    }

    private void deleteFromTableKToV(int entry, int keyHash) {
        Preconditions.checkArgument(entry != -1);
        int bucket = bucket(keyHash);
        int[] iArr = this.hashTableKToV;
        int i = iArr[bucket];
        if (i == entry) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[bucket] = iArr2[entry];
            iArr2[entry] = -1;
            return;
        }
        int i2 = this.nextInBucketKToV[i];
        while (true) {
            int i3 = i;
            i = i2;
            if (i == -1) {
                throw new AssertionError("Expected to find entry with key " + this.keys[entry]);
            } else if (i != entry) {
                i2 = this.nextInBucketKToV[i];
            } else {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i3] = iArr3[entry];
                iArr3[entry] = -1;
                return;
            }
        }
    }

    private void deleteFromTableVToK(int entry, int valueHash) {
        Preconditions.checkArgument(entry != -1);
        int bucket = bucket(valueHash);
        int[] iArr = this.hashTableVToK;
        int i = iArr[bucket];
        if (i == entry) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[bucket] = iArr2[entry];
            iArr2[entry] = -1;
            return;
        }
        int i2 = this.nextInBucketVToK[i];
        while (true) {
            int i3 = i;
            i = i2;
            if (i == -1) {
                throw new AssertionError("Expected to find entry with value " + this.values[entry]);
            } else if (i != entry) {
                i2 = this.nextInBucketVToK[i];
            } else {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i3] = iArr3[entry];
                iArr3[entry] = -1;
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceValueInEntry(int entry, @ParametricNullness V newValue, boolean force) {
        Preconditions.checkArgument(entry != -1);
        int smearedHash = Hashing.smearedHash(newValue);
        int findEntryByValue = findEntryByValue(newValue, smearedHash);
        if (findEntryByValue != -1) {
            if (force) {
                removeEntryValueHashKnown(findEntryByValue, smearedHash);
                if (entry == this.size) {
                    entry = findEntryByValue;
                }
            } else {
                throw new IllegalArgumentException("Value already present in map: " + newValue);
            }
        }
        deleteFromTableVToK(entry, Hashing.smearedHash(this.values[entry]));
        this.values[entry] = newValue;
        insertIntoTableVToK(entry, smearedHash);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void replaceKeyInEntry(int entry, @ParametricNullness K newKey, boolean force) {
        int i;
        Preconditions.checkArgument(entry != -1);
        int smearedHash = Hashing.smearedHash(newKey);
        int findEntryByKey = findEntryByKey(newKey, smearedHash);
        int i2 = this.lastInInsertionOrder;
        if (findEntryByKey == -1) {
            i = -2;
        } else if (force) {
            i2 = this.prevInInsertionOrder[findEntryByKey];
            i = this.nextInInsertionOrder[findEntryByKey];
            removeEntryKeyHashKnown(findEntryByKey, smearedHash);
            if (entry == this.size) {
                entry = findEntryByKey;
            }
        } else {
            throw new IllegalArgumentException("Key already present in map: " + newKey);
        }
        if (i2 == entry) {
            i2 = this.prevInInsertionOrder[entry];
        } else if (i2 == this.size) {
            i2 = findEntryByKey;
        }
        if (i == entry) {
            findEntryByKey = this.nextInInsertionOrder[entry];
        } else if (i != this.size) {
            findEntryByKey = i;
        }
        setSucceeds(this.prevInInsertionOrder[entry], this.nextInInsertionOrder[entry]);
        deleteFromTableKToV(entry, Hashing.smearedHash(this.keys[entry]));
        this.keys[entry] = newKey;
        insertIntoTableKToV(entry, Hashing.smearedHash(newKey));
        setSucceeds(i2, entry);
        setSucceeds(entry, findEntryByKey);
    }

    @Override // java.util.AbstractMap, java.util.Map
    @CheckForNull
    public V remove(@CheckForNull Object key) {
        int smearedHash = Hashing.smearedHash(key);
        int findEntryByKey = findEntryByKey(key, smearedHash);
        if (findEntryByKey == -1) {
            return null;
        }
        V v = this.values[findEntryByKey];
        removeEntryKeyHashKnown(findEntryByKey, smearedHash);
        return v;
    }

    @CheckForNull
    K removeInverse(@CheckForNull Object value) {
        int smearedHash = Hashing.smearedHash(value);
        int findEntryByValue = findEntryByValue(value, smearedHash);
        if (findEntryByValue == -1) {
            return null;
        }
        K k = this.keys[findEntryByValue];
        removeEntryValueHashKnown(findEntryByValue, smearedHash);
        return k;
    }

    void removeEntry(int entry) {
        removeEntryKeyHashKnown(entry, Hashing.smearedHash(this.keys[entry]));
    }

    private void removeEntry(int entry, int keyHash, int valueHash) {
        Preconditions.checkArgument(entry != -1);
        deleteFromTableKToV(entry, keyHash);
        deleteFromTableVToK(entry, valueHash);
        setSucceeds(this.prevInInsertionOrder[entry], this.nextInInsertionOrder[entry]);
        moveEntryToIndex(this.size - 1, entry);
        K[] kArr = this.keys;
        int i = this.size;
        kArr[i - 1] = null;
        this.values[i - 1] = null;
        this.size = i - 1;
        this.modCount++;
    }

    void removeEntryKeyHashKnown(int entry, int keyHash) {
        removeEntry(entry, keyHash, Hashing.smearedHash(this.values[entry]));
    }

    void removeEntryValueHashKnown(int entry, int valueHash) {
        removeEntry(entry, Hashing.smearedHash(this.keys[entry]), valueHash);
    }

    private void moveEntryToIndex(int src, int dest) {
        int i;
        int i2;
        if (src == dest) {
            return;
        }
        int i3 = this.prevInInsertionOrder[src];
        int i4 = this.nextInInsertionOrder[src];
        setSucceeds(i3, dest);
        setSucceeds(dest, i4);
        K[] kArr = this.keys;
        K k = kArr[src];
        V[] vArr = this.values;
        V v = vArr[src];
        kArr[dest] = k;
        vArr[dest] = v;
        int bucket = bucket(Hashing.smearedHash(k));
        int[] iArr = this.hashTableKToV;
        int i5 = iArr[bucket];
        if (i5 == src) {
            iArr[bucket] = dest;
        } else {
            int i6 = this.nextInBucketKToV[i5];
            while (true) {
                i = i5;
                i5 = i6;
                if (i5 == src) {
                    break;
                }
                i6 = this.nextInBucketKToV[i5];
            }
            this.nextInBucketKToV[i] = dest;
        }
        int[] iArr2 = this.nextInBucketKToV;
        iArr2[dest] = iArr2[src];
        iArr2[src] = -1;
        int bucket2 = bucket(Hashing.smearedHash(v));
        int[] iArr3 = this.hashTableVToK;
        int i7 = iArr3[bucket2];
        if (i7 == src) {
            iArr3[bucket2] = dest;
        } else {
            int i8 = this.nextInBucketVToK[i7];
            while (true) {
                i2 = i7;
                i7 = i8;
                if (i7 == src) {
                    break;
                }
                i8 = this.nextInBucketVToK[i7];
            }
            this.nextInBucketVToK[i2] = dest;
        }
        int[] iArr4 = this.nextInBucketVToK;
        iArr4[dest] = iArr4[src];
        iArr4[src] = -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static abstract class View<K, V, T> extends AbstractSet<T> {
        final HashBiMap<K, V> biMap;

        @ParametricNullness
        abstract T forEntry(int entry);

        View(HashBiMap<K, V> biMap) {
            this.biMap = biMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<T> iterator() {
            return new Iterator<T>() { // from class: com.google.common.collect.HashBiMap.View.1
                private int expectedModCount;
                private int index;
                private int indexToRemove = -1;
                private int remaining;

                {
                    this.index = ((HashBiMap) View.this.biMap).firstInInsertionOrder;
                    this.expectedModCount = View.this.biMap.modCount;
                    this.remaining = View.this.biMap.size;
                }

                private void checkForComodification() {
                    if (View.this.biMap.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    checkForComodification();
                    return this.index != -2 && this.remaining > 0;
                }

                @Override // java.util.Iterator
                @ParametricNullness
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T t = (T) View.this.forEntry(this.index);
                    this.indexToRemove = this.index;
                    this.index = ((HashBiMap) View.this.biMap).nextInInsertionOrder[this.index];
                    this.remaining--;
                    return t;
                }

                @Override // java.util.Iterator
                public void remove() {
                    checkForComodification();
                    CollectPreconditions.checkRemove(this.indexToRemove != -1);
                    View.this.biMap.removeEntry(this.indexToRemove);
                    if (this.index == View.this.biMap.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = View.this.biMap.modCount;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.biMap.size;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            this.biMap.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.keySet;
        if (set == null) {
            KeySet keySet = new KeySet();
            this.keySet = keySet;
            return keySet;
        }
        return set;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class KeySet extends View<K, V, K> {
        KeySet() {
            super(HashBiMap.this);
        }

        @Override // com.google.common.collect.HashBiMap.View
        @ParametricNullness
        K forEntry(int entry) {
            return (K) NullnessCasts.uncheckedCastNullableTToT(HashBiMap.this.keys[entry]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object o) {
            return HashBiMap.this.containsKey(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object o) {
            int smearedHash = Hashing.smearedHash(o);
            int findEntryByKey = HashBiMap.this.findEntryByKey(o, smearedHash);
            if (findEntryByKey != -1) {
                HashBiMap.this.removeEntryKeyHashKnown(findEntryByKey, smearedHash);
                return true;
            }
            return false;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<V> values() {
        Set<V> set = this.valueSet;
        if (set == null) {
            ValueSet valueSet = new ValueSet();
            this.valueSet = valueSet;
            return valueSet;
        }
        return set;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class ValueSet extends View<K, V, V> {
        ValueSet() {
            super(HashBiMap.this);
        }

        @Override // com.google.common.collect.HashBiMap.View
        @ParametricNullness
        V forEntry(int entry) {
            return (V) NullnessCasts.uncheckedCastNullableTToT(HashBiMap.this.values[entry]);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object o) {
            return HashBiMap.this.containsValue(o);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object o) {
            int smearedHash = Hashing.smearedHash(o);
            int findEntryByValue = HashBiMap.this.findEntryByValue(o, smearedHash);
            if (findEntryByValue != -1) {
                HashBiMap.this.removeEntryValueHashKnown(findEntryByValue, smearedHash);
                return true;
            }
            return false;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySet;
        if (set == null) {
            EntrySet entrySet = new EntrySet();
            this.entrySet = entrySet;
            return entrySet;
        }
        return set;
    }

    /* loaded from: classes3.dex */
    final class EntrySet extends View<K, V, Map.Entry<K, V>> {
        EntrySet() {
            super(HashBiMap.this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                Object value = entry.getValue();
                int findEntryByKey = HashBiMap.this.findEntryByKey(key);
                return findEntryByKey != -1 && Objects.equal(value, HashBiMap.this.values[findEntryByKey]);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                Object value = entry.getValue();
                int smearedHash = Hashing.smearedHash(key);
                int findEntryByKey = HashBiMap.this.findEntryByKey(key, smearedHash);
                if (findEntryByKey == -1 || !Objects.equal(value, HashBiMap.this.values[findEntryByKey])) {
                    return false;
                }
                HashBiMap.this.removeEntryKeyHashKnown(findEntryByKey, smearedHash);
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.HashBiMap.View
        public Map.Entry<K, V> forEntry(int entry) {
            return new EntryForKey(entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public final class EntryForKey extends AbstractMapEntry<K, V> {
        int index;
        @ParametricNullness
        final K key;

        EntryForKey(int index) {
            this.key = (K) NullnessCasts.uncheckedCastNullableTToT(HashBiMap.this.keys[index]);
            this.index = index;
        }

        void updateIndex() {
            int i = this.index;
            if (i == -1 || i > HashBiMap.this.size || !Objects.equal(HashBiMap.this.keys[this.index], this.key)) {
                this.index = HashBiMap.this.findEntryByKey(this.key);
            }
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getKey() {
            return this.key;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getValue() {
            updateIndex();
            return this.index == -1 ? (V) NullnessCasts.unsafeNull() : (V) NullnessCasts.uncheckedCastNullableTToT(HashBiMap.this.values[this.index]);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V setValue(@ParametricNullness V value) {
            updateIndex();
            if (this.index == -1) {
                HashBiMap.this.put(this.key, value);
                return (V) NullnessCasts.unsafeNull();
            }
            V v = (V) NullnessCasts.uncheckedCastNullableTToT(HashBiMap.this.values[this.index]);
            if (Objects.equal(v, value)) {
                return value;
            }
            HashBiMap.this.replaceValueInEntry(this.index, value, false);
            return v;
        }
    }

    @Override // com.google.common.collect.BiMap
    public BiMap<V, K> inverse() {
        BiMap<V, K> biMap = this.inverse;
        if (biMap == null) {
            Inverse inverse = new Inverse(this);
            this.inverse = inverse;
            return inverse;
        }
        return biMap;
    }

    /* loaded from: classes3.dex */
    static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap<V, K>, Serializable {
        private final HashBiMap<K, V> forward;
        private transient Set<Map.Entry<V, K>> inverseEntrySet;

        Inverse(HashBiMap<K, V> forward) {
            this.forward = forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsKey(@CheckForNull Object key) {
            return this.forward.containsValue(key);
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K get(@CheckForNull Object key) {
            return this.forward.getInverse(key);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public boolean containsValue(@CheckForNull Object value) {
            return this.forward.containsKey(value);
        }

        @Override // java.util.AbstractMap, java.util.Map, com.google.common.collect.BiMap
        @CheckForNull
        public K put(@ParametricNullness V value, @ParametricNullness K key) {
            return this.forward.putInverse(value, key, false);
        }

        @Override // com.google.common.collect.BiMap
        @CheckForNull
        public K forcePut(@ParametricNullness V value, @ParametricNullness K key) {
            return this.forward.putInverse(value, key, true);
        }

        @Override // com.google.common.collect.BiMap
        public BiMap<K, V> inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        @CheckForNull
        public K remove(@CheckForNull Object value) {
            return this.forward.removeInverse(value);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<V> keySet() {
            return this.forward.values();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<K> values() {
            return this.forward.keySet();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public Set<Map.Entry<V, K>> entrySet() {
            Set<Map.Entry<V, K>> set = this.inverseEntrySet;
            if (set == null) {
                InverseEntrySet inverseEntrySet = new InverseEntrySet(this.forward);
                this.inverseEntrySet = inverseEntrySet;
                return inverseEntrySet;
            }
            return set;
        }

        private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
            in.defaultReadObject();
            ((HashBiMap) this.forward).inverse = this;
        }
    }

    /* loaded from: classes3.dex */
    static class InverseEntrySet<K, V> extends View<K, V, Map.Entry<V, K>> {
        InverseEntrySet(HashBiMap<K, V> biMap) {
            super(biMap);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(@CheckForNull Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                Object value = entry.getValue();
                int findEntryByValue = this.biMap.findEntryByValue(key);
                return findEntryByValue != -1 && Objects.equal(this.biMap.keys[findEntryByValue], value);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(@CheckForNull Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) o;
                Object key = entry.getKey();
                Object value = entry.getValue();
                int smearedHash = Hashing.smearedHash(key);
                int findEntryByValue = this.biMap.findEntryByValue(key, smearedHash);
                if (findEntryByValue == -1 || !Objects.equal(this.biMap.keys[findEntryByValue], value)) {
                    return false;
                }
                this.biMap.removeEntryValueHashKnown(findEntryByValue, smearedHash);
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.HashBiMap.View
        public Map.Entry<V, K> forEntry(int entry) {
            return new EntryForValue(this.biMap, entry);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class EntryForValue<K, V> extends AbstractMapEntry<V, K> {
        final HashBiMap<K, V> biMap;
        int index;
        @ParametricNullness
        final V value;

        EntryForValue(HashBiMap<K, V> biMap, int index) {
            this.biMap = biMap;
            this.value = (V) NullnessCasts.uncheckedCastNullableTToT(biMap.values[index]);
            this.index = index;
        }

        private void updateIndex() {
            int i = this.index;
            if (i == -1 || i > this.biMap.size || !Objects.equal(this.value, this.biMap.values[this.index])) {
                this.index = this.biMap.findEntryByValue(this.value);
            }
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public V getKey() {
            return this.value;
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K getValue() {
            updateIndex();
            return this.index == -1 ? (K) NullnessCasts.unsafeNull() : (K) NullnessCasts.uncheckedCastNullableTToT(this.biMap.keys[this.index]);
        }

        @Override // com.google.common.collect.AbstractMapEntry, java.util.Map.Entry
        @ParametricNullness
        public K setValue(@ParametricNullness K key) {
            updateIndex();
            if (this.index == -1) {
                this.biMap.putInverse(this.value, key, false);
                return (K) NullnessCasts.unsafeNull();
            }
            K k = (K) NullnessCasts.uncheckedCastNullableTToT(this.biMap.keys[this.index]);
            if (Objects.equal(k, key)) {
                return key;
            }
            this.biMap.replaceKeyInEntry(this.index, key, false);
            return k;
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        Serialization.writeMap(this, stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int readCount = Serialization.readCount(stream);
        init(16);
        Serialization.populateMap(this, stream, readCount);
    }
}
