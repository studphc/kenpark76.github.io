package com.google.zxing.pdf417.encoder;

import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.ECIInput;
import com.google.zxing.common.MinimalECIInput;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import kotlin.io.encoding.Base64;
/* loaded from: classes3.dex */
final class PDF417HighLevelEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BYTE_COMPACTION = 1;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final int LATCH_TO_BYTE = 924;
    private static final int LATCH_TO_BYTE_PADDED = 901;
    private static final int LATCH_TO_NUMERIC = 902;
    private static final int LATCH_TO_TEXT = 900;
    private static final byte[] MIXED;
    private static final int NUMERIC_COMPACTION = 2;
    private static final int SHIFT_TO_BYTE = 913;
    private static final int SUBMODE_ALPHA = 0;
    private static final int SUBMODE_LOWER = 1;
    private static final int SUBMODE_MIXED = 2;
    private static final int SUBMODE_PUNCTUATION = 3;
    private static final int TEXT_COMPACTION = 0;
    private static final byte[] TEXT_MIXED_RAW = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, Ascii.CR, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, Base64.padSymbol, 94, 0, 32, 0, 0, 0};
    private static final byte[] TEXT_PUNCTUATION_RAW = {59, 60, 62, SignedBytes.MAX_POWER_OF_TWO, 91, 92, 93, 95, 96, 126, 33, Ascii.CR, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
    private static final byte[] PUNCTUATION = new byte[128];
    private static final Charset DEFAULT_ENCODING = StandardCharsets.ISO_8859_1;

    private static boolean isAlphaLower(char c) {
        return c == ' ' || (c >= 'a' && c <= 'z');
    }

    private static boolean isAlphaUpper(char c) {
        return c == ' ' || (c >= 'A' && c <= 'Z');
    }

    private static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isText(char c) {
        return c == '\t' || c == '\n' || c == '\r' || (c >= ' ' && c <= '~');
    }

    static {
        byte[] bArr = new byte[128];
        MIXED = bArr;
        Arrays.fill(bArr, (byte) -1);
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = TEXT_MIXED_RAW;
            if (i2 >= bArr2.length) {
                break;
            }
            byte b = bArr2[i2];
            if (b > 0) {
                MIXED[b] = (byte) i2;
            }
            i2++;
        }
        Arrays.fill(PUNCTUATION, (byte) -1);
        while (true) {
            byte[] bArr3 = TEXT_PUNCTUATION_RAW;
            if (i >= bArr3.length) {
                return;
            }
            byte b2 = bArr3[i];
            if (b2 > 0) {
                PUNCTUATION[b2] = (byte) i;
            }
            i++;
        }
    }

    private PDF417HighLevelEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String encodeHighLevel(String str, Compaction compaction, Charset charset, boolean z) throws WriterException {
        ECIInput noECIInput;
        CharacterSetECI characterSetECI;
        if (str.isEmpty()) {
            throw new WriterException("Empty message not allowed");
        }
        if (charset == null && !z) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) > 255) {
                    throw new WriterException("Non-encodable character detected: " + str.charAt(i) + " (Unicode: " + ((int) str.charAt(i)) + "). Consider specifying EncodeHintType.PDF417_AUTO_ECI and/or EncodeTypeHint.CHARACTER_SET.");
                }
            }
        }
        StringBuilder sb = new StringBuilder(str.length());
        if (z) {
            noECIInput = new MinimalECIInput(str, charset, -1);
        } else {
            noECIInput = new NoECIInput(str, null);
            if (charset == null) {
                charset = DEFAULT_ENCODING;
            } else if (!DEFAULT_ENCODING.equals(charset) && (characterSetECI = CharacterSetECI.getCharacterSetECI(charset)) != null) {
                encodingECI(characterSetECI.getValue(), sb);
            }
        }
        int length = noECIInput.length();
        int i2 = AnonymousClass1.$SwitchMap$com$google$zxing$pdf417$encoder$Compaction[compaction.ordinal()];
        if (i2 == 1) {
            encodeText(noECIInput, 0, length, sb, 0);
        } else if (i2 != 2) {
            if (i2 != 3) {
                int i3 = 0;
                int i4 = 0;
                int i5 = 0;
                while (i3 < length) {
                    while (i3 < length && noECIInput.isECI(i3)) {
                        encodingECI(noECIInput.getECIValue(i3), sb);
                        i3++;
                    }
                    if (i3 >= length) {
                        break;
                    }
                    int determineConsecutiveDigitCount = determineConsecutiveDigitCount(noECIInput, i3);
                    if (determineConsecutiveDigitCount >= 13) {
                        sb.append((char) 902);
                        encodeNumeric(noECIInput, i3, determineConsecutiveDigitCount, sb);
                        i3 += determineConsecutiveDigitCount;
                        i4 = 0;
                        i5 = 2;
                    } else {
                        int determineConsecutiveTextCount = determineConsecutiveTextCount(noECIInput, i3);
                        if (determineConsecutiveTextCount >= 5 || determineConsecutiveDigitCount == length) {
                            if (i5 != 0) {
                                sb.append((char) 900);
                                i4 = 0;
                                i5 = 0;
                            }
                            i4 = encodeText(noECIInput, i3, determineConsecutiveTextCount, sb, i4);
                            i3 += determineConsecutiveTextCount;
                        } else {
                            int determineConsecutiveBinaryCount = determineConsecutiveBinaryCount(noECIInput, i3, z ? null : charset);
                            if (determineConsecutiveBinaryCount == 0) {
                                determineConsecutiveBinaryCount = 1;
                            }
                            byte[] bytes = z ? null : noECIInput.subSequence(i3, i3 + determineConsecutiveBinaryCount).toString().getBytes(charset);
                            if ((!(bytes == null && determineConsecutiveBinaryCount == 1) && (bytes == null || bytes.length != 1)) || i5 != 0) {
                                if (z) {
                                    encodeMultiECIBinary(noECIInput, i3, i3 + determineConsecutiveBinaryCount, i5, sb);
                                } else {
                                    encodeBinary(bytes, 0, bytes.length, i5, sb);
                                }
                                i4 = 0;
                                i5 = 1;
                            } else if (z) {
                                encodeMultiECIBinary(noECIInput, i3, 1, 0, sb);
                            } else {
                                encodeBinary(bytes, 0, 1, 0, sb);
                            }
                            i3 += determineConsecutiveBinaryCount;
                        }
                    }
                }
            } else {
                sb.append((char) 902);
                encodeNumeric(noECIInput, 0, length, sb);
            }
        } else if (z) {
            encodeMultiECIBinary(noECIInput, 0, noECIInput.length(), 0, sb);
        } else {
            byte[] bytes2 = noECIInput.toString().getBytes(charset);
            encodeBinary(bytes2, 0, bytes2.length, 1, sb);
        }
        return sb.toString();
    }

    /* renamed from: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$encoder$Compaction;

        static {
            int[] iArr = new int[Compaction.values().length];
            $SwitchMap$com$google$zxing$pdf417$encoder$Compaction = iArr;
            try {
                iArr[Compaction.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$encoder$Compaction[Compaction.BYTE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$encoder$Compaction[Compaction.NUMERIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x010c A[EDGE_INSN: B:77:0x010c->B:60:0x010c ?: BREAK  , SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int encodeText(com.google.zxing.common.ECIInput r16, int r17, int r18, java.lang.StringBuilder r19, int r20) throws com.google.zxing.WriterException {
        /*
            Method dump skipped, instructions count: 315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.encodeText(com.google.zxing.common.ECIInput, int, int, java.lang.StringBuilder, int):int");
    }

    private static void encodeMultiECIBinary(ECIInput eCIInput, int i, int i2, int i3, StringBuilder sb) throws WriterException {
        int min = Math.min(i2 + i, eCIInput.length());
        int i4 = i;
        while (true) {
            if (i4 < min && eCIInput.isECI(i4)) {
                encodingECI(eCIInput.getECIValue(i4), sb);
                i4++;
            } else {
                int i5 = i4;
                while (i5 < min && !eCIInput.isECI(i5)) {
                    i5++;
                }
                int i6 = i5 - i4;
                if (i6 <= 0) {
                    return;
                }
                encodeBinary(subBytes(eCIInput, i4, i5), 0, i6, i4 == i ? i3 : 1, sb);
                i4 = i5;
            }
        }
    }

    static byte[] subBytes(ECIInput eCIInput, int i, int i2) {
        byte[] bArr = new byte[i2 - i];
        for (int i3 = i; i3 < i2; i3++) {
            bArr[i3 - i] = (byte) (eCIInput.charAt(i3) & 255);
        }
        return bArr;
    }

    private static void encodeBinary(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append((char) 913);
        } else if (i2 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + (bArr[i4 + i5] & 255);
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) (j % 900);
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    private static void encodeNumeric(ECIInput eCIInput, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger valueOf = BigInteger.valueOf(900L);
        BigInteger valueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int min = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append((Object) eCIInput.subSequence(i4, i4 + min));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(valueOf).intValue());
                bigInteger = bigInteger.divide(valueOf);
            } while (!bigInteger.equals(valueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += min;
        }
    }

    private static boolean isMixed(char c) {
        return MIXED[c] != -1;
    }

    private static boolean isPunctuation(char c) {
        return PUNCTUATION[c] != -1;
    }

    private static int determineConsecutiveDigitCount(ECIInput eCIInput, int i) {
        int length = eCIInput.length();
        int i2 = 0;
        if (i < length) {
            while (i < length && !eCIInput.isECI(i) && isDigit(eCIInput.charAt(i))) {
                i2++;
                i++;
            }
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0027, code lost:
        return (r1 - r6) - r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int determineConsecutiveTextCount(com.google.zxing.common.ECIInput r5, int r6) {
        /*
            int r0 = r5.length()
            r1 = r6
        L5:
            if (r1 >= r0) goto L3f
            r2 = 0
        L8:
            r3 = 13
            if (r2 >= r3) goto L23
            if (r1 >= r0) goto L23
            boolean r4 = r5.isECI(r1)
            if (r4 != 0) goto L23
            char r4 = r5.charAt(r1)
            boolean r4 = isDigit(r4)
            if (r4 == 0) goto L23
            int r2 = r2 + 1
            int r1 = r1 + 1
            goto L8
        L23:
            if (r2 < r3) goto L28
            int r1 = r1 - r6
            int r1 = r1 - r2
            return r1
        L28:
            if (r2 <= 0) goto L2b
            goto L5
        L2b:
            boolean r2 = r5.isECI(r1)
            if (r2 != 0) goto L3f
            char r2 = r5.charAt(r1)
            boolean r2 = isText(r2)
            if (r2 != 0) goto L3c
            goto L3f
        L3c:
            int r1 = r1 + 1
            goto L5
        L3f:
            int r1 = r1 - r6
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.determineConsecutiveTextCount(com.google.zxing.common.ECIInput, int):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002e, code lost:
        return r1 - r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int determineConsecutiveBinaryCount(com.google.zxing.common.ECIInput r6, int r7, java.nio.charset.Charset r8) throws com.google.zxing.WriterException {
        /*
            if (r8 != 0) goto L4
            r8 = 0
            goto L8
        L4:
            java.nio.charset.CharsetEncoder r8 = r8.newEncoder()
        L8:
            int r0 = r6.length()
            r1 = r7
        Ld:
            if (r1 >= r0) goto L64
            r2 = 0
            r3 = r1
        L11:
            r4 = 13
            if (r2 >= r4) goto L2b
            boolean r5 = r6.isECI(r3)
            if (r5 != 0) goto L2b
            char r3 = r6.charAt(r3)
            boolean r3 = isDigit(r3)
            if (r3 == 0) goto L2b
            int r2 = r2 + 1
            int r3 = r1 + r2
            if (r3 < r0) goto L11
        L2b:
            if (r2 < r4) goto L2f
            int r1 = r1 - r7
            return r1
        L2f:
            if (r8 == 0) goto L61
            char r2 = r6.charAt(r1)
            boolean r2 = r8.canEncode(r2)
            if (r2 == 0) goto L3c
            goto L61
        L3c:
            char r6 = r6.charAt(r1)
            com.google.zxing.WriterException r7 = new com.google.zxing.WriterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r0 = "Non-encodable character detected: "
            r8.<init>(r0)
            r8.append(r6)
            java.lang.String r0 = " (Unicode: "
            r8.append(r0)
            r8.append(r6)
            r6 = 41
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            r7.<init>(r6)
            throw r7
        L61:
            int r1 = r1 + 1
            goto Ld
        L64:
            int r1 = r1 - r7
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.encoder.PDF417HighLevelEncoder.determineConsecutiveBinaryCount(com.google.zxing.common.ECIInput, int, java.nio.charset.Charset):int");
    }

    private static void encodingECI(int i, StringBuilder sb) throws WriterException {
        if (i >= 0 && i < 900) {
            sb.append((char) 927);
            sb.append((char) i);
        } else if (i < 810900) {
            sb.append((char) 926);
            sb.append((char) ((i / 900) - 1));
            sb.append((char) (i % 900));
        } else if (i < 811800) {
            sb.append((char) 925);
            sb.append((char) (810900 - i));
        } else {
            throw new WriterException("ECI number not in valid range from 0..811799, but was " + i);
        }
    }

    /* loaded from: classes3.dex */
    private static final class NoECIInput implements ECIInput {
        String input;

        @Override // com.google.zxing.common.ECIInput
        public int getECIValue(int i) {
            return -1;
        }

        @Override // com.google.zxing.common.ECIInput
        public boolean isECI(int i) {
            return false;
        }

        /* synthetic */ NoECIInput(String str, AnonymousClass1 anonymousClass1) {
            this(str);
        }

        private NoECIInput(String str) {
            this.input = str;
        }

        @Override // com.google.zxing.common.ECIInput
        public int length() {
            return this.input.length();
        }

        @Override // com.google.zxing.common.ECIInput
        public char charAt(int i) {
            return this.input.charAt(i);
        }

        @Override // com.google.zxing.common.ECIInput
        public boolean haveNCharacters(int i, int i2) {
            return i + i2 <= this.input.length();
        }

        @Override // com.google.zxing.common.ECIInput
        public CharSequence subSequence(int i, int i2) {
            return this.input.subSequence(i, i2);
        }

        public String toString() {
            return this.input;
        }
    }
}
