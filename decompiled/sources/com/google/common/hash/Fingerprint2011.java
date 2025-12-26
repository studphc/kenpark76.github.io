package com.google.common.hash;

import com.google.common.base.Preconditions;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Fingerprint2011 extends AbstractNonStreamingHashFunction {
    static final HashFunction FINGERPRINT_2011 = new Fingerprint2011();
    private static final long K0 = -6505348102511208375L;
    private static final long K1 = -8261664234251669945L;
    private static final long K2 = -4288712594273399085L;
    private static final long K3 = -4132994306676758123L;

    static long hash128to64(long high, long low) {
        long j = (low ^ high) * K3;
        long j2 = (high ^ (j ^ (j >>> 47))) * K3;
        return (j2 ^ (j2 >>> 47)) * K3;
    }

    private static long shiftMix(long val) {
        return val ^ (val >>> 47);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public String toString() {
        return "Hashing.fingerprint2011()";
    }

    Fingerprint2011() {
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] input, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, input.length);
        return HashCode.fromLong(fingerprint(input, off, len));
    }

    static long fingerprint(byte[] bytes, int offset, int length) {
        long fullFingerprint;
        if (length <= 32) {
            fullFingerprint = murmurHash64WithSeed(bytes, offset, length, -1397348546323613475L);
        } else if (length <= 64) {
            fullFingerprint = hashLength33To64(bytes, offset, length);
        } else {
            fullFingerprint = fullFingerprint(bytes, offset, length);
        }
        long j = K0;
        long load64 = length >= 8 ? LittleEndianByteArray.load64(bytes, offset) : -6505348102511208375L;
        if (length >= 9) {
            j = LittleEndianByteArray.load64(bytes, (offset + length) - 8);
        }
        long hash128to64 = hash128to64(fullFingerprint + j, load64);
        return (hash128to64 == 0 || hash128to64 == 1) ? hash128to64 - 2 : hash128to64;
    }

    private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
        long load64 = LittleEndianByteArray.load64(bytes, offset);
        long load642 = LittleEndianByteArray.load64(bytes, offset + 8);
        long load643 = LittleEndianByteArray.load64(bytes, offset + 16);
        long load644 = LittleEndianByteArray.load64(bytes, offset + 24);
        long j = seedA + load64;
        long j2 = load642 + j + load643;
        output[0] = j2 + load644;
        output[1] = Long.rotateRight(seedB + j + load644, 51) + Long.rotateRight(j2, 23) + j;
    }

    private static long fullFingerprint(byte[] bytes, int offset, int length) {
        long load64 = LittleEndianByteArray.load64(bytes, offset);
        int i = offset + length;
        long load642 = LittleEndianByteArray.load64(bytes, i - 16) ^ K1;
        long load643 = LittleEndianByteArray.load64(bytes, i - 56) ^ K0;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long j = length;
        weakHashLength32WithSeeds(bytes, i - 64, j, load642, jArr);
        weakHashLength32WithSeeds(bytes, i - 32, j * K1, K0, jArr2);
        long shiftMix = load643 + (shiftMix(jArr[1]) * K1);
        long rotateRight = Long.rotateRight(shiftMix + load64, 39) * K1;
        long rotateRight2 = Long.rotateRight(load642, 33) * K1;
        int i2 = offset;
        int i3 = (length - 1) & (-64);
        while (true) {
            long rotateRight3 = Long.rotateRight(rotateRight + rotateRight2 + jArr[0] + LittleEndianByteArray.load64(bytes, i2 + 16), 37) * K1;
            long rotateRight4 = Long.rotateRight(rotateRight2 + jArr[1] + LittleEndianByteArray.load64(bytes, i2 + 48), 42) * K1;
            long j2 = rotateRight3 ^ jArr2[1];
            long j3 = rotateRight4 ^ jArr[0];
            long rotateRight5 = Long.rotateRight(shiftMix ^ jArr2[0], 33);
            weakHashLength32WithSeeds(bytes, i2, jArr[1] * K1, j2 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bytes, i2 + 32, jArr2[1] + rotateRight5, j3, jArr2);
            i2 += 64;
            i3 -= 64;
            if (i3 == 0) {
                return hash128to64(hash128to64(jArr[0], jArr2[0]) + (shiftMix(j3) * K1) + j2, hash128to64(jArr[1], jArr2[1]) + rotateRight5);
            }
            rotateRight = rotateRight5;
            shiftMix = j2;
            rotateRight2 = j3;
        }
    }

    private static long hashLength33To64(byte[] bytes, int offset, int length) {
        long load64 = LittleEndianByteArray.load64(bytes, offset + 24);
        int i = offset + length;
        int i2 = i - 16;
        long load642 = LittleEndianByteArray.load64(bytes, offset) + ((length + LittleEndianByteArray.load64(bytes, i2)) * K0);
        long rotateRight = Long.rotateRight(load642 + load64, 52);
        long rotateRight2 = Long.rotateRight(load642, 37);
        long load643 = load642 + LittleEndianByteArray.load64(bytes, offset + 8);
        int i3 = offset + 16;
        long load644 = load643 + LittleEndianByteArray.load64(bytes, i3);
        long j = load64 + load644;
        long rotateRight3 = rotateRight + Long.rotateRight(load644, 31) + rotateRight2 + Long.rotateRight(load643, 7);
        long load645 = LittleEndianByteArray.load64(bytes, i3) + LittleEndianByteArray.load64(bytes, i - 32);
        long load646 = LittleEndianByteArray.load64(bytes, i - 8);
        long rotateRight4 = Long.rotateRight(load645 + load646, 52);
        long rotateRight5 = Long.rotateRight(load645, 37);
        long load647 = load645 + LittleEndianByteArray.load64(bytes, i - 24);
        long load648 = load647 + LittleEndianByteArray.load64(bytes, i2);
        return shiftMix((shiftMix(((j + rotateRight4 + Long.rotateRight(load648, 31) + rotateRight5 + Long.rotateRight(load647, 7)) * K2) + ((load646 + load648 + rotateRight3) * K0)) * K0) + rotateRight3) * K2;
    }

    static long murmurHash64WithSeed(byte[] bytes, int offset, int length, long seed) {
        int i = length & (-8);
        int i2 = length & 7;
        long j = seed ^ (length * K3);
        for (int i3 = 0; i3 < i; i3 += 8) {
            j = (j ^ (shiftMix(LittleEndianByteArray.load64(bytes, offset + i3) * K3) * K3)) * K3;
        }
        if (i2 != 0) {
            j = (LittleEndianByteArray.load64Safely(bytes, offset + i, i2) ^ j) * K3;
        }
        return shiftMix(shiftMix(j) * K3);
    }
}
