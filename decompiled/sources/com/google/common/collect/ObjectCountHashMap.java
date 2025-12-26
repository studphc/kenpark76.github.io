package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Arrays;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public class ObjectCountHashMap<K> {
    static final float DEFAULT_LOAD_FACTOR = 1.0f;
    static final int DEFAULT_SIZE = 3;
    private static final long HASH_MASK = -4294967296L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final long NEXT_MASK = 4294967295L;
    static final int UNSET = -1;
    transient long[] entries;
    transient Object[] keys;
    private transient float loadFactor;
    transient int modCount;
    transient int size;
    private transient int[] table;
    private transient int threshold;
    transient int[] values;

    private static int getHash(long entry) {
        return (int) (entry >>> 32);
    }

    private static int getNext(long entry) {
        return (int) entry;
    }

    private static long swapNext(long entry, int newNext) {
        return (entry & HASH_MASK) | (NEXT_MASK & newNext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int nextIndexAfterRemove(int oldNextIndex, int removedIndex) {
        return oldNextIndex - 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K> ObjectCountHashMap<K> create() {
        return new ObjectCountHashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <K> ObjectCountHashMap<K> createWithExpectedSize(int expectedSize) {
        return new ObjectCountHashMap<>(expectedSize);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ObjectCountHashMap() {
        init(3, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ObjectCountHashMap(ObjectCountHashMap<? extends K> map) {
        init(map.size(), 1.0f);
        int firstIndex = map.firstIndex();
        while (firstIndex != -1) {
            put(map.getKey(firstIndex), map.getValue(firstIndex));
            firstIndex = map.nextIndex(firstIndex);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ObjectCountHashMap(int capacity) {
        this(capacity, 1.0f);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ObjectCountHashMap(int expectedSize, float loadFactor) {
        init(expectedSize, loadFactor);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init(int expectedSize, float loadFactor) {
        Preconditions.checkArgument(expectedSize >= 0, "Initial capacity must be non-negative");
        Preconditions.checkArgument(loadFactor > 0.0f, "Illegal load factor");
        int closedTableSize = Hashing.closedTableSize(expectedSize, loadFactor);
        this.table = newTable(closedTableSize);
        this.loadFactor = loadFactor;
        this.keys = new Object[expectedSize];
        this.values = new int[expectedSize];
        this.entries = newEntries(expectedSize);
        this.threshold = Math.max(1, (int) (closedTableSize * loadFactor));
    }

    private static int[] newTable(int size) {
        int[] iArr = new int[size];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private static long[] newEntries(int size) {
        long[] jArr = new long[size];
        Arrays.fill(jArr, -1L);
        return jArr;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int firstIndex() {
        return this.size == 0 ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int nextIndex(int index) {
        int i = index + 1;
        if (i < this.size) {
            return i;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int size() {
        return this.size;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @ParametricNullness
    public K getKey(int index) {
        Preconditions.checkElementIndex(index, this.size);
        return (K) this.keys[index];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getValue(int index) {
        Preconditions.checkElementIndex(index, this.size);
        return this.values[index];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setValue(int index, int newValue) {
        Preconditions.checkElementIndex(index, this.size);
        this.values[index] = newValue;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Multiset.Entry<K> getEntry(int index) {
        Preconditions.checkElementIndex(index, this.size);
        return new MapEntry(index);
    }

    /* loaded from: classes3.dex */
    class MapEntry extends Multisets.AbstractEntry<K> {
        @ParametricNullness
        final K key;
        int lastKnownIndex;

        MapEntry(int index) {
            this.key = (K) ObjectCountHashMap.this.keys[index];
            this.lastKnownIndex = index;
        }

        @Override // com.google.common.collect.Multiset.Entry
        @ParametricNullness
        public K getElement() {
            return this.key;
        }

        void updateLastKnownIndex() {
            int i = this.lastKnownIndex;
            if (i == -1 || i >= ObjectCountHashMap.this.size() || !Objects.equal(this.key, ObjectCountHashMap.this.keys[this.lastKnownIndex])) {
                this.lastKnownIndex = ObjectCountHashMap.this.indexOf(this.key);
            }
        }

        @Override // com.google.common.collect.Multiset.Entry
        public int getCount() {
            updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                return 0;
            }
            return ObjectCountHashMap.this.values[this.lastKnownIndex];
        }

        public int setCount(int count) {
            updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                ObjectCountHashMap.this.put(this.key, count);
                return 0;
            }
            int i = ObjectCountHashMap.this.values[this.lastKnownIndex];
            ObjectCountHashMap.this.values[this.lastKnownIndex] = count;
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > this.entries.length) {
            resizeEntries(minCapacity);
        }
        if (minCapacity >= this.threshold) {
            resizeTable(Math.max(2, Integer.highestOneBit(minCapacity - 1) << 1));
        }
    }

    public int put(@ParametricNullness K key, int value) {
        CollectPreconditions.checkPositive(value, "count");
        long[] jArr = this.entries;
        Object[] objArr = this.keys;
        int[] iArr = this.values;
        int smearedHash = Hashing.smearedHash(key);
        int hashTableMask = hashTableMask() & smearedHash;
        int i = this.size;
        int[] iArr2 = this.table;
        int i2 = iArr2[hashTableMask];
        if (i2 == -1) {
            iArr2[hashTableMask] = i;
        } else {
            while (true) {
                long j = jArr[i2];
                if (getHash(j) == smearedHash && Objects.equal(key, objArr[i2])) {
                    int i3 = iArr[i2];
                    iArr[i2] = value;
                    return i3;
                }
                int next = getNext(j);
                if (next == -1) {
                    jArr[i2] = swapNext(j, i);
                    break;
                }
                i2 = next;
            }
        }
        if (i == Integer.MAX_VALUE) {
            throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
        }
        int i4 = i + 1;
        resizeMeMaybe(i4);
        insertEntry(i, key, value, smearedHash);
        this.size = i4;
        if (i >= this.threshold) {
            resizeTable(this.table.length * 2);
        }
        this.modCount++;
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void insertEntry(int entryIndex, @ParametricNullness K key, int value, int hash) {
        this.entries[entryIndex] = (hash << 32) | NEXT_MASK;
        this.keys[entryIndex] = key;
        this.values[entryIndex] = value;
    }

    private void resizeMeMaybe(int newSize) {
        int length = this.entries.length;
        if (newSize > length) {
            int max = Math.max(1, length >>> 1) + length;
            if (max < 0) {
                max = Integer.MAX_VALUE;
            }
            if (max != length) {
                resizeEntries(max);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void resizeEntries(int newCapacity) {
        this.keys = Arrays.copyOf(this.keys, newCapacity);
        this.values = Arrays.copyOf(this.values, newCapacity);
        long[] jArr = this.entries;
        int length = jArr.length;
        long[] copyOf = Arrays.copyOf(jArr, newCapacity);
        if (newCapacity > length) {
            Arrays.fill(copyOf, length, newCapacity, -1L);
        }
        this.entries = copyOf;
    }

    private void resizeTable(int newCapacity) {
        if (this.table.length >= 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        int i = ((int) (newCapacity * this.loadFactor)) + 1;
        int[] newTable = newTable(newCapacity);
        long[] jArr = this.entries;
        int length = newTable.length - 1;
        for (int i2 = 0; i2 < this.size; i2++) {
            int hash = getHash(jArr[i2]);
            int i3 = hash & length;
            int i4 = newTable[i3];
            newTable[i3] = i2;
            jArr[i2] = (hash << 32) | (i4 & NEXT_MASK);
        }
        this.threshold = i;
        this.table = newTable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int indexOf(@CheckForNull Object key) {
        int smearedHash = Hashing.smearedHash(key);
        int i = this.table[hashTableMask() & smearedHash];
        while (i != -1) {
            long j = this.entries[i];
            if (getHash(j) == smearedHash && Objects.equal(key, this.keys[i])) {
                return i;
            }
            i = getNext(j);
        }
        return -1;
    }

    public boolean containsKey(@CheckForNull Object key) {
        return indexOf(key) != -1;
    }

    public int get(@CheckForNull Object key) {
        int indexOf = indexOf(key);
        if (indexOf == -1) {
            return 0;
        }
        return this.values[indexOf];
    }

    public int remove(@CheckForNull Object key) {
        return remove(key, Hashing.smearedHash(key));
    }

    private int remove(@CheckForNull Object key, int hash) {
        int hashTableMask = hashTableMask() & hash;
        int i = this.table[hashTableMask];
        if (i == -1) {
            return 0;
        }
        int i2 = -1;
        while (true) {
            if (getHash(this.entries[i]) == hash && Objects.equal(key, this.keys[i])) {
                int i3 = this.values[i];
                if (i2 == -1) {
                    this.table[hashTableMask] = getNext(this.entries[i]);
                } else {
                    long[] jArr = this.entries;
                    jArr[i2] = swapNext(jArr[i2], getNext(jArr[i]));
                }
                moveLastEntry(i);
                this.size--;
                this.modCount++;
                return i3;
            }
            int next = getNext(this.entries[i]);
            if (next == -1) {
                return 0;
            }
            i2 = i;
            i = next;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int removeEntry(int entryIndex) {
        return remove(this.keys[entryIndex], getHash(this.entries[entryIndex]));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void moveLastEntry(int dstIndex) {
        int size = size() - 1;
        if (dstIndex < size) {
            Object[] objArr = this.keys;
            objArr[dstIndex] = objArr[size];
            int[] iArr = this.values;
            iArr[dstIndex] = iArr[size];
            objArr[size] = null;
            iArr[size] = 0;
            long[] jArr = this.entries;
            long j = jArr[size];
            jArr[dstIndex] = j;
            jArr[size] = -1;
            int hash = getHash(j) & hashTableMask();
            int[] iArr2 = this.table;
            int i = iArr2[hash];
            if (i == size) {
                iArr2[hash] = dstIndex;
                return;
            }
            while (true) {
                long j2 = this.entries[i];
                int next = getNext(j2);
                if (next == size) {
                    this.entries[i] = swapNext(j2, dstIndex);
                    return;
                }
                i = next;
            }
        } else {
            this.keys[dstIndex] = null;
            this.values[dstIndex] = 0;
            this.entries[dstIndex] = -1;
        }
    }

    public void clear() {
        this.modCount++;
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, 0);
        Arrays.fill(this.table, -1);
        Arrays.fill(this.entries, -1L);
        this.size = 0;
    }
}
