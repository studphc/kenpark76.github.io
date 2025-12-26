package com.google.common.hash;

import com.google.common.base.Preconditions;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class FarmHashFingerprint64 extends AbstractNonStreamingHashFunction {
    static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();
    private static final long K0 = -4348849565147123417L;
    private static final long K1 = -5435081209227447693L;
    private static final long K2 = -7286425919675154353L;

    private static long hashLength16(long u, long v, long mul) {
        long j = (u ^ v) * mul;
        long j2 = ((j ^ (j >>> 47)) ^ v) * mul;
        return (j2 ^ (j2 >>> 47)) * mul;
    }

    private static long shiftMix(long val) {
        return val ^ (val >>> 47);
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public String toString() {
        return "Hashing.farmHashFingerprint64()";
    }

    FarmHashFingerprint64() {
    }

    @Override // com.google.common.hash.AbstractNonStreamingHashFunction, com.google.common.hash.AbstractHashFunction, com.google.common.hash.HashFunction
    public HashCode hashBytes(byte[] input, int off, int len) {
        Preconditions.checkPositionIndexes(off, off + len, input.length);
        return HashCode.fromLong(fingerprint(input, off, len));
    }

    static long fingerprint(byte[] bytes, int offset, int length) {
        if (length <= 32) {
            if (length <= 16) {
                return hashLength0to16(bytes, offset, length);
            }
            return hashLength17to32(bytes, offset, length);
        } else if (length <= 64) {
            return hashLength33To64(bytes, offset, length);
        } else {
            return hashLength65Plus(bytes, offset, length);
        }
    }

    private static void weakHashLength32WithSeeds(byte[] bytes, int offset, long seedA, long seedB, long[] output) {
        long load64 = LittleEndianByteArray.load64(bytes, offset);
        long load642 = LittleEndianByteArray.load64(bytes, offset + 8);
        long load643 = LittleEndianByteArray.load64(bytes, offset + 16);
        long load644 = LittleEndianByteArray.load64(bytes, offset + 24);
        long j = seedA + load64;
        long j2 = load642 + j + load643;
        output[0] = j2 + load644;
        output[1] = Long.rotateRight(seedB + j + load644, 21) + Long.rotateRight(j2, 44) + j;
    }

    private static long hashLength0to16(byte[] bytes, int offset, int length) {
        if (length >= 8) {
            long j = (length * 2) + K2;
            long load64 = LittleEndianByteArray.load64(bytes, offset) + K2;
            long load642 = LittleEndianByteArray.load64(bytes, (offset + length) - 8);
            return hashLength16((Long.rotateRight(load642, 37) * j) + load64, (Long.rotateRight(load64, 25) + load642) * j, j);
        } else if (length >= 4) {
            return hashLength16(length + ((LittleEndianByteArray.load32(bytes, offset) & 4294967295L) << 3), LittleEndianByteArray.load32(bytes, (offset + length) - 4) & 4294967295L, (length * 2) + K2);
        } else if (length > 0) {
            return shiftMix((((bytes[offset] & 255) + ((bytes[(length >> 1) + offset] & 255) << 8)) * K2) ^ ((length + ((bytes[offset + (length - 1)] & 255) << 2)) * K0)) * K2;
        } else {
            return K2;
        }
    }

    private static long hashLength17to32(byte[] bytes, int offset, int length) {
        long j = (length * 2) + K2;
        long load64 = LittleEndianByteArray.load64(bytes, offset) * K1;
        long load642 = LittleEndianByteArray.load64(bytes, offset + 8);
        int i = offset + length;
        long load643 = LittleEndianByteArray.load64(bytes, i - 8) * j;
        return hashLength16((LittleEndianByteArray.load64(bytes, i - 16) * K2) + Long.rotateRight(load64 + load642, 43) + Long.rotateRight(load643, 30), load643 + load64 + Long.rotateRight(load642 + K2, 18), j);
    }

    private static long hashLength33To64(byte[] bytes, int offset, int length) {
        long j = (length * 2) + K2;
        long load64 = LittleEndianByteArray.load64(bytes, offset) * K2;
        long load642 = LittleEndianByteArray.load64(bytes, offset + 8);
        int i = offset + length;
        long load643 = LittleEndianByteArray.load64(bytes, i - 8) * j;
        long rotateRight = Long.rotateRight(load64 + load642, 43) + Long.rotateRight(load643, 30) + (LittleEndianByteArray.load64(bytes, i - 16) * K2);
        long hashLength16 = hashLength16(rotateRight, load643 + Long.rotateRight(load642 + K2, 18) + load64, j);
        long load644 = LittleEndianByteArray.load64(bytes, offset + 16) * j;
        long load645 = LittleEndianByteArray.load64(bytes, offset + 24);
        long load646 = (rotateRight + LittleEndianByteArray.load64(bytes, i - 32)) * j;
        return hashLength16(((hashLength16 + LittleEndianByteArray.load64(bytes, i - 24)) * j) + Long.rotateRight(load644 + load645, 43) + Long.rotateRight(load646, 30), load644 + Long.rotateRight(load645 + load64, 18) + load646, j);
    }

    private static long hashLength65Plus(byte[] bytes, int offset, int length) {
        long j = 81;
        long j2 = (j * K1) + 113;
        long shiftMix = shiftMix((j2 * K2) + 113) * K2;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        long load64 = (j * K2) + LittleEndianByteArray.load64(bytes, offset);
        int i = length - 1;
        int i2 = offset + ((i / 64) * 64);
        int i3 = i & 63;
        int i4 = (i2 + i3) - 63;
        int i5 = offset;
        while (true) {
            long rotateRight = Long.rotateRight(load64 + j2 + jArr[0] + LittleEndianByteArray.load64(bytes, i5 + 8), 37) * K1;
            long rotateRight2 = Long.rotateRight(j2 + jArr[1] + LittleEndianByteArray.load64(bytes, i5 + 48), 42) * K1;
            long j3 = rotateRight ^ jArr2[1];
            long load642 = rotateRight2 + jArr[0] + LittleEndianByteArray.load64(bytes, i5 + 40);
            long rotateRight3 = Long.rotateRight(shiftMix + jArr2[0], 33) * K1;
            weakHashLength32WithSeeds(bytes, i5, jArr[1] * K1, j3 + jArr2[0], jArr);
            weakHashLength32WithSeeds(bytes, i5 + 32, rotateRight3 + jArr2[1], load642 + LittleEndianByteArray.load64(bytes, i5 + 16), jArr2);
            int i6 = i5 + 64;
            if (i6 == i2) {
                long j4 = K1 + ((j3 & 255) << 1);
                long j5 = jArr2[0] + i3;
                jArr2[0] = j5;
                long j6 = jArr[0] + j5;
                jArr[0] = j6;
                jArr2[0] = jArr2[0] + j6;
                long rotateRight4 = (Long.rotateRight(((rotateRight3 + load642) + jArr[0]) + LittleEndianByteArray.load64(bytes, i4 + 8), 37) * j4) ^ (jArr2[1] * 9);
                long rotateRight5 = (Long.rotateRight(load642 + jArr[1] + LittleEndianByteArray.load64(bytes, i4 + 48), 42) * j4) + (jArr[0] * 9) + LittleEndianByteArray.load64(bytes, i4 + 40);
                long rotateRight6 = Long.rotateRight(j3 + jArr2[0], 33) * j4;
                weakHashLength32WithSeeds(bytes, i4, jArr[1] * j4, rotateRight4 + jArr2[0], jArr);
                weakHashLength32WithSeeds(bytes, i4 + 32, rotateRight6 + jArr2[1], rotateRight5 + LittleEndianByteArray.load64(bytes, i4 + 16), jArr2);
                return hashLength16(hashLength16(jArr[0], jArr2[0], j4) + (shiftMix(rotateRight5) * K0) + rotateRight4, hashLength16(jArr[1], jArr2[1], j4) + rotateRight6, j4);
            }
            i5 = i6;
            shiftMix = j3;
            j2 = load642;
            load64 = rotateRight3;
        }
    }
}
