package com.google.zxing.datamatrix.decoder;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.google.common.base.Ascii;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.ECIStringBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;
import kotlin.text.Typography;
/* loaded from: classes3.dex */
final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_BASIC_SET_CHARS;
    private static final char[] TEXT_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE,
        ECI_ENCODE
    }

    static {
        char[] cArr = {'!', Typography.quote, '#', Typography.dollar, '%', Typography.amp, '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', Typography.less, '=', Typography.greater, '?', '@', '[', '\\', ']', '^', '_'};
        C40_SHIFT2_SET_CHARS = cArr;
        TEXT_BASIC_SET_CHARS = new char[]{'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        TEXT_SHIFT2_SET_CHARS = cArr;
        TEXT_SHIFT3_SET_CHARS = new char[]{'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', Ascii.MAX};
    }

    private DecodedBitStreamParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.google.zxing.common.DecoderResult decode(byte[] r13) throws com.google.zxing.FormatException {
        /*
            com.google.zxing.common.BitSource r0 = new com.google.zxing.common.BitSource
            r0.<init>(r13)
            com.google.zxing.common.ECIStringBuilder r1 = new com.google.zxing.common.ECIStringBuilder
            r2 = 100
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            r2.<init>(r3)
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 1
            java.lang.Integer r7 = java.lang.Integer.valueOf(r6)
            r5.<init>(r6)
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r8 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
            java.util.HashSet r9 = new java.util.HashSet
            r9.<init>()
        L27:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r10 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
            if (r8 != r10) goto L30
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r8 = decodeAsciiSegment(r0, r1, r2, r9)
            goto L5a
        L30:
            int[] r10 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.AnonymousClass1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode
            int r8 = r8.ordinal()
            r8 = r10[r8]
            switch(r8) {
                case 1: goto L55;
                case 2: goto L51;
                case 3: goto L4d;
                case 4: goto L49;
                case 5: goto L45;
                case 6: goto L40;
                default: goto L3b;
            }
        L3b:
            com.google.zxing.FormatException r13 = com.google.zxing.FormatException.getFormatInstance()
            throw r13
        L40:
            decodeECISegment(r0, r1)
            r3 = 1
            goto L58
        L45:
            decodeBase256Segment(r0, r1, r5)
            goto L58
        L49:
            decodeEdifactSegment(r0, r1)
            goto L58
        L4d:
            decodeAnsiX12Segment(r0, r1)
            goto L58
        L51:
            decodeTextSegment(r0, r1, r9)
            goto L58
        L55:
            decodeC40Segment(r0, r1, r9)
        L58:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r8 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
        L5a:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r10 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.PAD_ENCODE
            if (r8 == r10) goto L64
            int r10 = r0.available()
            if (r10 > 0) goto L27
        L64:
            int r0 = r2.length()
            if (r0 <= 0) goto L6d
            r1.appendCharacters(r2)
        L6d:
            r0 = 5
            r2 = 4
            if (r3 == 0) goto L9a
            boolean r3 = r9.contains(r4)
            if (r3 != 0) goto L98
            java.lang.Integer r3 = java.lang.Integer.valueOf(r2)
            boolean r3 = r9.contains(r3)
            if (r3 == 0) goto L82
            goto L98
        L82:
            boolean r3 = r9.contains(r7)
            if (r3 != 0) goto L95
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto L93
            goto L95
        L93:
            r12 = 4
            goto Lc3
        L95:
            r6 = 6
            r12 = 6
            goto Lc3
        L98:
            r12 = 5
            goto Lc3
        L9a:
            boolean r3 = r9.contains(r4)
            if (r3 != 0) goto Lc1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            boolean r2 = r9.contains(r2)
            if (r2 == 0) goto Lab
            goto Lc1
        Lab:
            boolean r2 = r9.contains(r7)
            if (r2 != 0) goto Lbe
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            boolean r0 = r9.contains(r0)
            if (r0 == 0) goto Lbc
            goto Lbe
        Lbc:
            r12 = 1
            goto Lc3
        Lbe:
            r6 = 3
            r12 = 3
            goto Lc3
        Lc1:
            r6 = 2
            r12 = 2
        Lc3:
            com.google.zxing.common.DecoderResult r0 = new com.google.zxing.common.DecoderResult
            java.lang.String r9 = r1.toString()
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto Ld0
            r5 = 0
        Ld0:
            r10 = r5
            r11 = 0
            r7 = r0
            r8 = r13
            r7.<init>(r8, r9, r10, r11, r12)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.decode(byte[]):com.google.zxing.common.DecoderResult");
    }

    /* renamed from: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode = iArr;
            try {
                iArr[Mode.C40_ENCODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.TEXT_ENCODE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.ANSIX12_ENCODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.EDIFACT_ENCODE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.BASE256_ENCODE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode[Mode.ECI_ENCODE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, ECIStringBuilder eCIStringBuilder, StringBuilder sb, Set<Integer> set) throws FormatException {
        boolean z = false;
        do {
            int readBits = bitSource.readBits(8);
            if (readBits == 0) {
                throw FormatException.getFormatInstance();
            }
            if (readBits > 128) {
                if (readBits != 129) {
                    if (readBits <= 229) {
                        int i = readBits - 130;
                        if (i < 10) {
                            eCIStringBuilder.append('0');
                        }
                        eCIStringBuilder.append(i);
                    } else {
                        switch (readBits) {
                            case 230:
                                return Mode.C40_ENCODE;
                            case 231:
                                return Mode.BASE256_ENCODE;
                            case 232:
                                set.add(Integer.valueOf(eCIStringBuilder.length()));
                                eCIStringBuilder.append((char) 29);
                                break;
                            case 233:
                            case 234:
                                break;
                            case 235:
                                z = true;
                                break;
                            case 236:
                                eCIStringBuilder.append("[)>\u001e05\u001d");
                                sb.insert(0, "\u001e\u0004");
                                break;
                            case 237:
                                eCIStringBuilder.append("[)>\u001e06\u001d");
                                sb.insert(0, "\u001e\u0004");
                                break;
                            case 238:
                                return Mode.ANSIX12_ENCODE;
                            case 239:
                                return Mode.TEXT_ENCODE;
                            case 240:
                                return Mode.EDIFACT_ENCODE;
                            case 241:
                                return Mode.ECI_ENCODE;
                            default:
                                if (readBits != 254 || bitSource.available() != 0) {
                                    throw FormatException.getFormatInstance();
                                }
                                break;
                        }
                    }
                } else {
                    return Mode.PAD_ENCODE;
                }
            } else {
                if (z) {
                    readBits += 128;
                }
                eCIStringBuilder.append((char) (readBits - 1));
                return Mode.ASCII_ENCODE;
            }
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeC40Segment(BitSource bitSource, ECIStringBuilder eCIStringBuilder, Set<Integer> set) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        boolean z = false;
        int i = 0;
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(readBits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            char[] cArr = C40_SHIFT2_SET_CHARS;
                            if (i3 < cArr.length) {
                                char c = cArr[i3];
                                if (z) {
                                    eCIStringBuilder.append((char) (c + 128));
                                    z = false;
                                } else {
                                    eCIStringBuilder.append(c);
                                }
                            } else if (i3 == 27) {
                                set.add(Integer.valueOf(eCIStringBuilder.length()));
                                eCIStringBuilder.append((char) 29);
                            } else if (i3 != 30) {
                                throw FormatException.getFormatInstance();
                            } else {
                                z = true;
                            }
                            i = 0;
                        } else if (i != 3) {
                            throw FormatException.getFormatInstance();
                        } else {
                            if (z) {
                                eCIStringBuilder.append((char) (i3 + 224));
                                z = false;
                                i = 0;
                            } else {
                                eCIStringBuilder.append((char) (i3 + 96));
                                i = 0;
                            }
                        }
                    } else if (z) {
                        eCIStringBuilder.append((char) (i3 + 128));
                        z = false;
                        i = 0;
                    } else {
                        eCIStringBuilder.append((char) i3);
                        i = 0;
                    }
                } else if (i3 < 3) {
                    i = i3 + 1;
                } else {
                    char[] cArr2 = C40_BASIC_SET_CHARS;
                    if (i3 < cArr2.length) {
                        char c2 = cArr2[i3];
                        if (z) {
                            eCIStringBuilder.append((char) (c2 + 128));
                            z = false;
                        } else {
                            eCIStringBuilder.append(c2);
                        }
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeTextSegment(BitSource bitSource, ECIStringBuilder eCIStringBuilder, Set<Integer> set) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        boolean z = false;
        int i = 0;
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(readBits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                if (i != 0) {
                    if (i != 1) {
                        if (i == 2) {
                            char[] cArr = TEXT_SHIFT2_SET_CHARS;
                            if (i3 < cArr.length) {
                                char c = cArr[i3];
                                if (z) {
                                    eCIStringBuilder.append((char) (c + 128));
                                    z = false;
                                } else {
                                    eCIStringBuilder.append(c);
                                }
                            } else if (i3 == 27) {
                                set.add(Integer.valueOf(eCIStringBuilder.length()));
                                eCIStringBuilder.append((char) 29);
                            } else if (i3 != 30) {
                                throw FormatException.getFormatInstance();
                            } else {
                                z = true;
                            }
                            i = 0;
                        } else if (i == 3) {
                            char[] cArr2 = TEXT_SHIFT3_SET_CHARS;
                            if (i3 < cArr2.length) {
                                char c2 = cArr2[i3];
                                if (z) {
                                    eCIStringBuilder.append((char) (c2 + 128));
                                    z = false;
                                    i = 0;
                                } else {
                                    eCIStringBuilder.append(c2);
                                    i = 0;
                                }
                            } else {
                                throw FormatException.getFormatInstance();
                            }
                        } else {
                            throw FormatException.getFormatInstance();
                        }
                    } else if (z) {
                        eCIStringBuilder.append((char) (i3 + 128));
                        z = false;
                        i = 0;
                    } else {
                        eCIStringBuilder.append((char) i3);
                        i = 0;
                    }
                } else if (i3 < 3) {
                    i = i3 + 1;
                } else {
                    char[] cArr3 = TEXT_BASIC_SET_CHARS;
                    if (i3 < cArr3.length) {
                        char c3 = cArr3[i3];
                        if (z) {
                            eCIStringBuilder.append((char) (c3 + 128));
                            z = false;
                        } else {
                            eCIStringBuilder.append(c3);
                        }
                    } else {
                        throw FormatException.getFormatInstance();
                    }
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, ECIStringBuilder eCIStringBuilder) throws FormatException {
        int readBits;
        int[] iArr = new int[3];
        while (bitSource.available() != 8 && (readBits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(readBits, bitSource.readBits(8), iArr);
            for (int i = 0; i < 3; i++) {
                int i2 = iArr[i];
                if (i2 == 0) {
                    eCIStringBuilder.append('\r');
                } else if (i2 == 1) {
                    eCIStringBuilder.append('*');
                } else if (i2 == 2) {
                    eCIStringBuilder.append(Typography.greater);
                } else if (i2 == 3) {
                    eCIStringBuilder.append(' ');
                } else if (i2 < 14) {
                    eCIStringBuilder.append((char) (i2 + 44));
                } else if (i2 < 40) {
                    eCIStringBuilder.append((char) (i2 + 51));
                } else {
                    throw FormatException.getFormatInstance();
                }
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void parseTwoBytes(int i, int i2, int[] iArr) {
        int i3 = ((i << 8) + i2) - 1;
        int i4 = i3 / 1600;
        iArr[0] = i4;
        int i5 = i3 - (i4 * 1600);
        int i6 = i5 / 40;
        iArr[1] = i6;
        iArr[2] = i5 - (i6 * 40);
    }

    private static void decodeEdifactSegment(BitSource bitSource, ECIStringBuilder eCIStringBuilder) {
        while (bitSource.available() > 16) {
            for (int i = 0; i < 4; i++) {
                int readBits = bitSource.readBits(6);
                if (readBits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                        return;
                    }
                    return;
                }
                if ((readBits & 32) == 0) {
                    readBits |= 64;
                }
                eCIStringBuilder.append((char) readBits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeBase256Segment(BitSource bitSource, ECIStringBuilder eCIStringBuilder, Collection<byte[]> collection) throws FormatException {
        int byteOffset = bitSource.getByteOffset() + 1;
        int i = byteOffset + 1;
        int unrandomize255State = unrandomize255State(bitSource.readBits(8), byteOffset);
        if (unrandomize255State == 0) {
            unrandomize255State = bitSource.available() / 8;
        } else if (unrandomize255State >= 250) {
            unrandomize255State = ((unrandomize255State - 249) * ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION) + unrandomize255State(bitSource.readBits(8), i);
            i++;
        }
        if (unrandomize255State < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[unrandomize255State];
        int i2 = 0;
        while (i2 < unrandomize255State) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            bArr[i2] = (byte) unrandomize255State(bitSource.readBits(8), i);
            i2++;
            i++;
        }
        collection.add(bArr);
        eCIStringBuilder.append(new String(bArr, StandardCharsets.ISO_8859_1));
    }

    private static void decodeECISegment(BitSource bitSource, ECIStringBuilder eCIStringBuilder) throws FormatException {
        if (bitSource.available() < 8) {
            throw FormatException.getFormatInstance();
        }
        int readBits = bitSource.readBits(8);
        if (readBits <= 127) {
            eCIStringBuilder.appendECI(readBits - 1);
        }
    }

    private static int unrandomize255State(int i, int i2) {
        int i3 = i - (((i2 * 149) % 255) + 1);
        return i3 >= 0 ? i3 : i3 + 256;
    }
}
