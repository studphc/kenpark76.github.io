package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.ECIStringBuilder;
import com.google.zxing.pdf417.PDF417ResultMetadata;
import java.math.BigInteger;
import java.util.Arrays;
/* loaded from: classes3.dex */
final class DecodedBitStreamParser {
    private static final int AL = 28;
    private static final int AS = 27;
    private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
    private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
    private static final int BYTE_COMPACTION_MODE_LATCH = 901;
    private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
    private static final int ECI_CHARSET = 927;
    private static final int ECI_GENERAL_PURPOSE = 926;
    private static final int ECI_USER_DEFINED = 925;
    private static final BigInteger[] EXP900;
    private static final int LL = 27;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_ADDRESSEE = 4;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_CHECKSUM = 6;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_NAME = 0;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_FILE_SIZE = 5;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SEGMENT_COUNT = 1;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_SENDER = 3;
    private static final int MACRO_PDF417_OPTIONAL_FIELD_TIME_STAMP = 2;
    private static final int MACRO_PDF417_TERMINATOR = 922;
    private static final int MAX_NUMERIC_CODEWORDS = 15;
    private static final int ML = 28;
    private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
    private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
    private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
    private static final int PAL = 29;
    private static final int PL = 25;
    private static final int PS = 29;
    private static final int TEXT_COMPACTION_MODE_LATCH = 900;
    private static final char[] PUNCT_CHARS = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    private static final char[] MIXED_CHARS = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum Mode {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        EXP900 = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger valueOf = BigInteger.valueOf(900L);
        bigIntegerArr[1] = valueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = EXP900;
            if (i >= bigIntegerArr2.length) {
                return;
            }
            bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(valueOf);
            i++;
        }
    }

    private DecodedBitStreamParser() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static DecoderResult decode(int[] iArr, String str) throws FormatException {
        int i;
        ECIStringBuilder eCIStringBuilder = new ECIStringBuilder(iArr.length * 2);
        int textCompaction = textCompaction(iArr, 1, eCIStringBuilder);
        PDF417ResultMetadata pDF417ResultMetadata = new PDF417ResultMetadata();
        while (textCompaction < iArr[0]) {
            int i2 = textCompaction + 1;
            int i3 = iArr[textCompaction];
            if (i3 != MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                switch (i3) {
                    case 900:
                        textCompaction = textCompaction(iArr, i2, eCIStringBuilder);
                        break;
                    case 901:
                        textCompaction = byteCompaction(i3, iArr, i2, eCIStringBuilder);
                        break;
                    case 902:
                        textCompaction = numericCompaction(iArr, i2, eCIStringBuilder);
                        break;
                    default:
                        switch (i3) {
                            case MACRO_PDF417_TERMINATOR /* 922 */:
                            case BEGIN_MACRO_PDF417_OPTIONAL_FIELD /* 923 */:
                                throw FormatException.getFormatInstance();
                            case BYTE_COMPACTION_MODE_LATCH_6 /* 924 */:
                                textCompaction = byteCompaction(i3, iArr, i2, eCIStringBuilder);
                                break;
                            case ECI_USER_DEFINED /* 925 */:
                                i = i2 + 1;
                                textCompaction = i;
                                break;
                            case ECI_GENERAL_PURPOSE /* 926 */:
                                i = i2 + 2;
                                textCompaction = i;
                                break;
                            case ECI_CHARSET /* 927 */:
                                textCompaction = i2 + 1;
                                eCIStringBuilder.appendECI(iArr[i2]);
                                break;
                            case 928:
                                textCompaction = decodeMacroBlock(iArr, i2, pDF417ResultMetadata);
                                break;
                            default:
                                textCompaction = textCompaction(iArr, i2 - 1, eCIStringBuilder);
                                break;
                        }
                }
            } else {
                textCompaction = i2 + 1;
                eCIStringBuilder.append((char) iArr[i2]);
            }
        }
        if (eCIStringBuilder.isEmpty() && pDF417ResultMetadata.getFileId() == null) {
            throw FormatException.getFormatInstance();
        }
        DecoderResult decoderResult = new DecoderResult(null, eCIStringBuilder.toString(), null, str);
        decoderResult.setOther(pDF417ResultMetadata);
        return decoderResult;
    }

    static int decodeMacroBlock(int[] iArr, int i, PDF417ResultMetadata pDF417ResultMetadata) throws FormatException {
        int i2;
        if (i + 2 > iArr[0]) {
            throw FormatException.getFormatInstance();
        }
        int[] iArr2 = new int[2];
        int i3 = 0;
        while (i3 < 2) {
            iArr2[i3] = iArr[i];
            i3++;
            i++;
        }
        String decodeBase900toBase10 = decodeBase900toBase10(iArr2, 2);
        if (decodeBase900toBase10.isEmpty()) {
            pDF417ResultMetadata.setSegmentIndex(0);
        } else {
            try {
                pDF417ResultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10));
            } catch (NumberFormatException unused) {
                throw FormatException.getFormatInstance();
            }
        }
        StringBuilder sb = new StringBuilder();
        while (i < iArr[0] && i < iArr.length && (i2 = iArr[i]) != MACRO_PDF417_TERMINATOR && i2 != BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
            sb.append(String.format("%03d", Integer.valueOf(i2)));
            i++;
        }
        if (sb.length() == 0) {
            throw FormatException.getFormatInstance();
        }
        pDF417ResultMetadata.setFileId(sb.toString());
        int i4 = iArr[i] == BEGIN_MACRO_PDF417_OPTIONAL_FIELD ? i + 1 : -1;
        while (i < iArr[0]) {
            int i5 = iArr[i];
            if (i5 == MACRO_PDF417_TERMINATOR) {
                i++;
                pDF417ResultMetadata.setLastSegment(true);
            } else if (i5 == BEGIN_MACRO_PDF417_OPTIONAL_FIELD) {
                int i6 = i + 1;
                switch (iArr[i6]) {
                    case 0:
                        ECIStringBuilder eCIStringBuilder = new ECIStringBuilder();
                        i = textCompaction(iArr, i6 + 1, eCIStringBuilder);
                        pDF417ResultMetadata.setFileName(eCIStringBuilder.toString());
                        continue;
                    case 1:
                        ECIStringBuilder eCIStringBuilder2 = new ECIStringBuilder();
                        i = numericCompaction(iArr, i6 + 1, eCIStringBuilder2);
                        try {
                            pDF417ResultMetadata.setSegmentCount(Integer.parseInt(eCIStringBuilder2.toString()));
                            continue;
                        } catch (NumberFormatException unused2) {
                            throw FormatException.getFormatInstance();
                        }
                    case 2:
                        ECIStringBuilder eCIStringBuilder3 = new ECIStringBuilder();
                        i = numericCompaction(iArr, i6 + 1, eCIStringBuilder3);
                        try {
                            pDF417ResultMetadata.setTimestamp(Long.parseLong(eCIStringBuilder3.toString()));
                            continue;
                        } catch (NumberFormatException unused3) {
                            throw FormatException.getFormatInstance();
                        }
                    case 3:
                        ECIStringBuilder eCIStringBuilder4 = new ECIStringBuilder();
                        i = textCompaction(iArr, i6 + 1, eCIStringBuilder4);
                        pDF417ResultMetadata.setSender(eCIStringBuilder4.toString());
                        continue;
                    case 4:
                        ECIStringBuilder eCIStringBuilder5 = new ECIStringBuilder();
                        i = textCompaction(iArr, i6 + 1, eCIStringBuilder5);
                        pDF417ResultMetadata.setAddressee(eCIStringBuilder5.toString());
                        continue;
                    case 5:
                        ECIStringBuilder eCIStringBuilder6 = new ECIStringBuilder();
                        i = numericCompaction(iArr, i6 + 1, eCIStringBuilder6);
                        try {
                            pDF417ResultMetadata.setFileSize(Long.parseLong(eCIStringBuilder6.toString()));
                            continue;
                        } catch (NumberFormatException unused4) {
                            throw FormatException.getFormatInstance();
                        }
                    case 6:
                        ECIStringBuilder eCIStringBuilder7 = new ECIStringBuilder();
                        i = numericCompaction(iArr, i6 + 1, eCIStringBuilder7);
                        try {
                            pDF417ResultMetadata.setChecksum(Integer.parseInt(eCIStringBuilder7.toString()));
                            continue;
                        } catch (NumberFormatException unused5) {
                            throw FormatException.getFormatInstance();
                        }
                    default:
                        throw FormatException.getFormatInstance();
                }
            } else {
                throw FormatException.getFormatInstance();
            }
        }
        if (i4 != -1) {
            int i7 = i - i4;
            if (pDF417ResultMetadata.isLastSegment()) {
                i7--;
            }
            if (i7 > 0) {
                pDF417ResultMetadata.setOptionalData(Arrays.copyOfRange(iArr, i4, i7 + i4));
            }
        }
        return i;
    }

    private static int textCompaction(int[] iArr, int i, ECIStringBuilder eCIStringBuilder) throws FormatException {
        int i2 = iArr[0];
        int[] iArr2 = new int[(i2 - i) * 2];
        int[] iArr3 = new int[(i2 - i) * 2];
        Mode mode = Mode.ALPHA;
        boolean z = false;
        int i3 = 0;
        while (i < iArr[0] && !z) {
            int i4 = i + 1;
            int i5 = iArr[i];
            if (i5 < 900) {
                iArr2[i3] = i5 / 30;
                iArr2[i3 + 1] = i5 % 30;
                i3 += 2;
            } else if (i5 == MODE_SHIFT_TO_BYTE_COMPACTION_MODE) {
                iArr2[i3] = MODE_SHIFT_TO_BYTE_COMPACTION_MODE;
                i = i4 + 1;
                iArr3[i3] = iArr[i4];
                i3++;
            } else if (i5 != ECI_CHARSET) {
                if (i5 != 928) {
                    switch (i5) {
                        case 900:
                            iArr2[i3] = 900;
                            i3++;
                            break;
                        default:
                            switch (i5) {
                            }
                        case 901:
                        case 902:
                            i4--;
                            z = true;
                            break;
                    }
                }
                i4--;
                z = true;
            } else {
                Mode decodeTextCompaction = decodeTextCompaction(iArr2, iArr3, i3, eCIStringBuilder, mode);
                int i6 = i4 + 1;
                eCIStringBuilder.appendECI(iArr[i4]);
                int i7 = iArr[0];
                if (i6 > i7) {
                    throw FormatException.getFormatInstance();
                }
                int[] iArr4 = new int[(i7 - i6) * 2];
                i3 = 0;
                mode = decodeTextCompaction;
                i = i6;
                iArr3 = new int[(i7 - i6) * 2];
                iArr2 = iArr4;
            }
            i = i4;
        }
        decodeTextCompaction(iArr2, iArr3, i3, eCIStringBuilder, mode);
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ec A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode decodeTextCompaction(int[] r15, int[] r16, int r17, com.google.zxing.common.ECIStringBuilder r18, com.google.zxing.pdf417.decoder.DecodedBitStreamParser.Mode r19) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.decodeTextCompaction(int[], int[], int, com.google.zxing.common.ECIStringBuilder, com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode):com.google.zxing.pdf417.decoder.DecodedBitStreamParser$Mode");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.zxing.pdf417.decoder.DecodedBitStreamParser$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode = iArr;
            try {
                iArr[Mode.ALPHA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.LOWER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.MIXED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.ALPHA_SHIFT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$zxing$pdf417$decoder$DecodedBitStreamParser$Mode[Mode.PUNCT_SHIFT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0046, code lost:
        if (r11 == com.google.zxing.pdf417.decoder.DecodedBitStreamParser.BYTE_COMPACTION_MODE_LATCH_6) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004a, code lost:
        if (r8 >= r12[0]) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x004e, code lost:
        if (r12[r8] >= 900) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0050, code lost:
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0052, code lost:
        if (r13 >= 6) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0054, code lost:
        r14.append((byte) (r6 >> ((5 - r13) * 8)));
        r13 = r13 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int byteCompaction(int r11, int[] r12, int r13, com.google.zxing.common.ECIStringBuilder r14) throws com.google.zxing.FormatException {
        /*
            r0 = 0
            r1 = 0
        L2:
            r2 = r12[r0]
            if (r13 >= r2) goto L8b
            if (r1 != 0) goto L8b
        L8:
            r2 = r12[r0]
            r3 = 927(0x39f, float:1.299E-42)
            r4 = 1
            if (r13 >= r2) goto L1c
            r5 = r12[r13]
            if (r5 != r3) goto L1c
            int r13 = r13 + 1
            r2 = r12[r13]
            r14.appendECI(r2)
            int r13 = r13 + r4
            goto L8
        L1c:
            if (r13 >= r2) goto L88
            r2 = r12[r13]
            r5 = 900(0x384, float:1.261E-42)
            if (r2 < r5) goto L26
            goto L88
        L26:
            r6 = 0
            r2 = 0
        L29:
            r8 = 900(0x384, double:4.447E-321)
            long r6 = r6 * r8
            int r8 = r13 + 1
            r13 = r12[r13]
            long r9 = (long) r13
            long r6 = r6 + r9
            int r2 = r2 + r4
            r13 = 5
            if (r2 >= r13) goto L42
            r9 = r12[r0]
            if (r8 >= r9) goto L42
            r9 = r12[r8]
            if (r9 < r5) goto L40
            goto L42
        L40:
            r13 = r8
            goto L29
        L42:
            if (r2 != r13) goto L62
            r13 = 924(0x39c, float:1.295E-42)
            if (r11 == r13) goto L50
            r13 = r12[r0]
            if (r8 >= r13) goto L62
            r13 = r12[r8]
            if (r13 >= r5) goto L62
        L50:
            r13 = 0
        L51:
            r2 = 6
            if (r13 >= r2) goto L85
            int r2 = 5 - r13
            int r2 = r2 * 8
            long r2 = r6 >> r2
            int r3 = (int) r2
            byte r2 = (byte) r3
            r14.append(r2)
            int r13 = r13 + 1
            goto L51
        L62:
            int r8 = r8 - r2
        L63:
            r13 = r12[r0]
            if (r8 >= r13) goto L85
            if (r1 != 0) goto L85
            int r13 = r8 + 1
            r2 = r12[r8]
            if (r2 >= r5) goto L75
            byte r2 = (byte) r2
            r14.append(r2)
            r8 = r13
            goto L63
        L75:
            if (r2 != r3) goto L80
            int r2 = r13 + 1
            r13 = r12[r13]
            r14.appendECI(r13)
            r8 = r2
            goto L63
        L80:
            int r13 = r13 + (-1)
            r8 = r13
            r1 = 1
            goto L63
        L85:
            r13 = r8
            goto L2
        L88:
            r1 = 1
            goto L2
        L8b:
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.DecodedBitStreamParser.byteCompaction(int, int[], int, com.google.zxing.common.ECIStringBuilder):int");
    }

    private static int numericCompaction(int[] iArr, int i, ECIStringBuilder eCIStringBuilder) throws FormatException {
        int[] iArr2 = new int[15];
        boolean z = false;
        int i2 = 0;
        while (true) {
            int i3 = iArr[0];
            if (i < i3 && !z) {
                int i4 = i + 1;
                int i5 = iArr[i];
                if (i4 == i3) {
                    z = true;
                }
                if (i5 < 900) {
                    iArr2[i2] = i5;
                    i2++;
                } else {
                    if (i5 != 900 && i5 != 901 && i5 != ECI_CHARSET && i5 != 928) {
                        switch (i5) {
                        }
                    }
                    i4--;
                    z = true;
                }
                if ((i2 % 15 == 0 || i5 == 902 || z) && i2 > 0) {
                    eCIStringBuilder.append(decodeBase900toBase10(iArr2, i2));
                    i2 = 0;
                }
                i = i4;
            }
        }
        return i;
    }

    private static String decodeBase900toBase10(int[] iArr, int i) throws FormatException {
        BigInteger bigInteger = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigInteger = bigInteger.add(EXP900[(i - i2) - 1].multiply(BigInteger.valueOf(iArr[i2])));
        }
        String bigInteger2 = bigInteger.toString();
        if (bigInteger2.charAt(0) != '1') {
            throw FormatException.getFormatInstance();
        }
        return bigInteger2.substring(1);
    }
}
