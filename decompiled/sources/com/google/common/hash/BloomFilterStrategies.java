package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public enum BloomFilterStrategies implements BloomFilter.Strategy {
    MURMUR128_MITZ_32 { // from class: com.google.common.hash.BloomFilterStrategies.1
        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(@ParametricNullness T object, Funnel<? super T> funnel, int numHashFunctions, LockFreeBitArray bits) {
            long bitSize = bits.bitSize();
            long asLong = Hashing.murmur3_128().hashObject(object, funnel).asLong();
            int i = (int) asLong;
            int i2 = (int) (asLong >>> 32);
            boolean z = false;
            for (int i3 = 1; i3 <= numHashFunctions; i3++) {
                int i4 = (i3 * i2) + i;
                if (i4 < 0) {
                    i4 = ~i4;
                }
                z |= bits.set(i4 % bitSize);
            }
            return z;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(@ParametricNullness T object, Funnel<? super T> funnel, int numHashFunctions, LockFreeBitArray bits) {
            long bitSize = bits.bitSize();
            long asLong = Hashing.murmur3_128().hashObject(object, funnel).asLong();
            int i = (int) asLong;
            int i2 = (int) (asLong >>> 32);
            for (int i3 = 1; i3 <= numHashFunctions; i3++) {
                int i4 = (i3 * i2) + i;
                if (i4 < 0) {
                    i4 = ~i4;
                }
                if (!bits.get(i4 % bitSize)) {
                    return false;
                }
            }
            return true;
        }
    },
    MURMUR128_MITZ_64 { // from class: com.google.common.hash.BloomFilterStrategies.2
        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean put(@ParametricNullness T object, Funnel<? super T> funnel, int numHashFunctions, LockFreeBitArray bits) {
            long bitSize = bits.bitSize();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
            long lowerEight = lowerEight(bytesInternal);
            long upperEight = upperEight(bytesInternal);
            boolean z = false;
            for (int i = 0; i < numHashFunctions; i++) {
                z |= bits.set((Long.MAX_VALUE & lowerEight) % bitSize);
                lowerEight += upperEight;
            }
            return z;
        }

        @Override // com.google.common.hash.BloomFilter.Strategy
        public <T> boolean mightContain(@ParametricNullness T object, Funnel<? super T> funnel, int numHashFunctions, LockFreeBitArray bits) {
            long bitSize = bits.bitSize();
            byte[] bytesInternal = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
            long lowerEight = lowerEight(bytesInternal);
            long upperEight = upperEight(bytesInternal);
            for (int i = 0; i < numHashFunctions; i++) {
                if (!bits.get((Long.MAX_VALUE & lowerEight) % bitSize)) {
                    return false;
                }
                lowerEight += upperEight;
            }
            return true;
        }

        private long lowerEight(byte[] bytes) {
            return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
        }

        private long upperEight(byte[] bytes) {
            return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class LockFreeBitArray {
        private static final int LONG_ADDRESSABLE_BITS = 6;
        private final LongAddable bitCount;
        final AtomicLongArray data;

        /* JADX INFO: Access modifiers changed from: package-private */
        public LockFreeBitArray(long bits) {
            Preconditions.checkArgument(bits > 0, "data length is zero!");
            this.data = new AtomicLongArray(Ints.checkedCast(LongMath.divide(bits, 64L, RoundingMode.CEILING)));
            this.bitCount = LongAddables.create();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public LockFreeBitArray(long[] data) {
            Preconditions.checkArgument(data.length > 0, "data length is zero!");
            this.data = new AtomicLongArray(data);
            this.bitCount = LongAddables.create();
            long j = 0;
            for (long j2 : data) {
                j += Long.bitCount(j2);
            }
            this.bitCount.add(j);
        }

        boolean set(long bitIndex) {
            long j;
            long j2;
            if (get(bitIndex)) {
                return false;
            }
            int i = (int) (bitIndex >>> 6);
            long j3 = 1 << ((int) bitIndex);
            do {
                j = this.data.get(i);
                j2 = j | j3;
                if (j == j2) {
                    return false;
                }
            } while (!this.data.compareAndSet(i, j, j2));
            this.bitCount.increment();
            return true;
        }

        boolean get(long bitIndex) {
            return ((1 << ((int) bitIndex)) & this.data.get((int) (bitIndex >>> 6))) != 0;
        }

        public static long[] toPlainArray(AtomicLongArray atomicLongArray) {
            int length = atomicLongArray.length();
            long[] jArr = new long[length];
            for (int i = 0; i < length; i++) {
                jArr[i] = atomicLongArray.get(i);
            }
            return jArr;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long bitSize() {
            return this.data.length() * 64;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public long bitCount() {
            return this.bitCount.sum();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public LockFreeBitArray copy() {
            return new LockFreeBitArray(toPlainArray(this.data));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void putAll(LockFreeBitArray other) {
            Preconditions.checkArgument(this.data.length() == other.data.length(), "BitArrays must be of equal length (%s != %s)", this.data.length(), other.data.length());
            for (int i = 0; i < this.data.length(); i++) {
                putData(i, other.data.get(i));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void putData(int i, long longValue) {
            long j;
            long j2;
            boolean z;
            while (true) {
                j = this.data.get(i);
                j2 = j | longValue;
                if (j != j2) {
                    if (this.data.compareAndSet(i, j, j2)) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (z) {
                this.bitCount.add(Long.bitCount(j2) - Long.bitCount(j));
            }
        }

        int dataLength() {
            return this.data.length();
        }

        public boolean equals(@CheckForNull Object o) {
            if (o instanceof LockFreeBitArray) {
                return Arrays.equals(toPlainArray(this.data), toPlainArray(((LockFreeBitArray) o).data));
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(toPlainArray(this.data));
        }
    }
}
