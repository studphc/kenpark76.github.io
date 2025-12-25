package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class State {
    static final State INITIAL_STATE = new State(Token.EMPTY, 0, 0, 0);
    private final int binaryShiftByteCount;
    private final int binaryShiftCost;
    private final int bitCount;
    private final int mode;
    private final Token token;

    private static int calculateBinaryShiftCost(int i) {
        if (i > 62) {
            return 21;
        }
        if (i > 31) {
            return 20;
        }
        return i > 0 ? 10 : 0;
    }

    private State(Token token, int i, int i2, int i3) {
        this.token = token;
        this.mode = i;
        this.binaryShiftByteCount = i2;
        this.bitCount = i3;
        this.binaryShiftCost = calculateBinaryShiftCost(i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getMode() {
        return this.mode;
    }

    Token getToken() {
        return this.token;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getBinaryShiftByteCount() {
        return this.binaryShiftByteCount;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getBitCount() {
        return this.bitCount;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public State appendFLGn(int i) {
        Token token;
        Token token2 = shiftAndAppend(4, 0).token;
        int i2 = 3;
        if (i < 0) {
            token = token2.add(0, 3);
        } else if (i > 999999) {
            throw new IllegalArgumentException("ECI code must be between 0 and 999999");
        } else {
            byte[] bytes = Integer.toString(i).getBytes(StandardCharsets.ISO_8859_1);
            Token add = token2.add(bytes.length, 3);
            for (byte b : bytes) {
                add = add.add((b - 48) + 2, 4);
            }
            i2 = 3 + (bytes.length * 4);
            token = add;
        }
        return new State(token, this.mode, 0, this.bitCount + i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public State latchAndAppend(int i, int i2) {
        int i3 = this.bitCount;
        Token token = this.token;
        if (i != this.mode) {
            int i4 = HighLevelEncoder.LATCH_TABLE[this.mode][i];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            token = token.add(i5, i6);
            i3 += i6;
        }
        int i7 = i == 2 ? 4 : 5;
        return new State(token.add(i2, i7), i, 0, i3 + i7);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public State shiftAndAppend(int i, int i2) {
        Token token = this.token;
        int i3 = this.mode == 2 ? 4 : 5;
        return new State(token.add(HighLevelEncoder.SHIFT_TABLE[this.mode][i], i3).add(i2, 5), this.mode, 0, this.bitCount + i3 + 5);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public State addBinaryShiftChar(int i) {
        Token token = this.token;
        int i2 = this.mode;
        int i3 = this.bitCount;
        if (i2 == 4 || i2 == 2) {
            int i4 = HighLevelEncoder.LATCH_TABLE[i2][0];
            int i5 = 65535 & i4;
            int i6 = i4 >> 16;
            token = token.add(i5, i6);
            i3 += i6;
            i2 = 0;
        }
        int i7 = this.binaryShiftByteCount;
        State state = new State(token, i2, i7 + 1, i3 + ((i7 == 0 || i7 == 31) ? 18 : i7 == 62 ? 9 : 8));
        return state.binaryShiftByteCount == 2078 ? state.endBinaryShift(i + 1) : state;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public State endBinaryShift(int i) {
        int i2 = this.binaryShiftByteCount;
        return i2 == 0 ? this : new State(this.token.addBinaryShift(i - i2, i2), this.mode, 0, this.bitCount);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isBetterThanOrEqualTo(State state) {
        int i = this.bitCount + (HighLevelEncoder.LATCH_TABLE[this.mode][state.mode] >> 16);
        int i2 = this.binaryShiftByteCount;
        int i3 = state.binaryShiftByteCount;
        if (i2 < i3) {
            i += state.binaryShiftCost - this.binaryShiftCost;
        } else if (i2 > i3 && i3 > 0) {
            i += 10;
        }
        return i <= state.bitCount;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BitArray toBitArray(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        for (Token token = endBinaryShift(bArr.length).token; token != null; token = token.getPrevious()) {
            arrayList.add(token);
        }
        BitArray bitArray = new BitArray();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((Token) arrayList.get(size)).appendTo(bitArray, bArr);
        }
        return bitArray;
    }

    public String toString() {
        return String.format("%s bits=%d bytes=%d", HighLevelEncoder.MODE_NAMES[this.mode], Integer.valueOf(this.bitCount), Integer.valueOf(this.binaryShiftByteCount));
    }
}
