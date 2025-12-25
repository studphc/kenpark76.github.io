package okio;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;
/* loaded from: classes3.dex */
public final class Options extends AbstractList<ByteString> implements RandomAccess {
    final ByteString[] byteStrings;
    final int[] trie;

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b7, code lost:
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static okio.Options of(okio.ByteString... r11) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okio.Options.of(okio.ByteString[]):okio.Options");
    }

    private static void buildTrieRecursive(long j, Buffer buffer, int i, List<ByteString> list, int i2, int i3, List<Integer> list2) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Buffer buffer2;
        if (i2 >= i3) {
            throw new AssertionError();
        }
        for (int i9 = i2; i9 < i3; i9++) {
            if (list.get(i9).size() < i) {
                throw new AssertionError();
            }
        }
        ByteString byteString = list.get(i2);
        ByteString byteString2 = list.get(i3 - 1);
        if (i == byteString.size()) {
            int intValue = list2.get(i2).intValue();
            int i10 = i2 + 1;
            i5 = i10;
            i4 = intValue;
            byteString = list.get(i10);
        } else {
            i4 = -1;
            i5 = i2;
        }
        if (byteString.getByte(i) != byteString2.getByte(i)) {
            int i11 = 1;
            for (int i12 = i5 + 1; i12 < i3; i12++) {
                if (list.get(i12 - 1).getByte(i) != list.get(i12).getByte(i)) {
                    i11++;
                }
            }
            long intCount = j + intCount(buffer) + 2 + (i11 * 2);
            buffer.writeInt(i11);
            buffer.writeInt(i4);
            for (int i13 = i5; i13 < i3; i13++) {
                byte b = list.get(i13).getByte(i);
                if (i13 == i5 || b != list.get(i13 - 1).getByte(i)) {
                    buffer.writeInt(b & 255);
                }
            }
            Buffer buffer3 = new Buffer();
            int i14 = i5;
            while (i14 < i3) {
                byte b2 = list.get(i14).getByte(i);
                int i15 = i14 + 1;
                int i16 = i15;
                while (true) {
                    if (i16 >= i3) {
                        i7 = i3;
                        break;
                    } else if (b2 != list.get(i16).getByte(i)) {
                        i7 = i16;
                        break;
                    } else {
                        i16++;
                    }
                }
                if (i15 == i7 && i + 1 == list.get(i14).size()) {
                    buffer.writeInt(list2.get(i14).intValue());
                    i8 = i7;
                    buffer2 = buffer3;
                } else {
                    buffer.writeInt((int) ((intCount(buffer3) + intCount) * (-1)));
                    i8 = i7;
                    buffer2 = buffer3;
                    buildTrieRecursive(intCount, buffer3, i + 1, list, i14, i7, list2);
                }
                buffer3 = buffer2;
                i14 = i8;
            }
            Buffer buffer4 = buffer3;
            buffer.write(buffer4, buffer4.size());
            return;
        }
        int min = Math.min(byteString.size(), byteString2.size());
        int i17 = 0;
        for (int i18 = i; i18 < min && byteString.getByte(i18) == byteString2.getByte(i18); i18++) {
            i17++;
        }
        long intCount2 = 1 + j + intCount(buffer) + 2 + i17;
        buffer.writeInt(-i17);
        buffer.writeInt(i4);
        int i19 = i;
        while (true) {
            i6 = i + i17;
            if (i19 >= i6) {
                break;
            }
            buffer.writeInt(byteString.getByte(i19) & 255);
            i19++;
        }
        if (i5 + 1 == i3) {
            if (i6 != list.get(i5).size()) {
                throw new AssertionError();
            }
            buffer.writeInt(list2.get(i5).intValue());
            return;
        }
        Buffer buffer5 = new Buffer();
        buffer.writeInt((int) ((intCount(buffer5) + intCount2) * (-1)));
        buildTrieRecursive(intCount2, buffer5, i6, list, i5, i3, list2);
        buffer.write(buffer5, buffer5.size());
    }

    @Override // java.util.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.byteStrings.length;
    }

    private static int intCount(Buffer buffer) {
        return (int) (buffer.size() / 4);
    }
}
