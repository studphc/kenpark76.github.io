package com.google.zxing.aztec;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
/* loaded from: classes3.dex */
public final class AztecReader implements Reader {
    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return decode(binaryBitmap, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0070 A[LOOP:0: B:40:0x006e->B:41:0x0070, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a1  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.google.zxing.Result decode(com.google.zxing.BinaryBitmap r11, java.util.Map<com.google.zxing.DecodeHintType, ?> r12) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException {
        /*
            r10 = this;
            com.google.zxing.aztec.detector.Detector r0 = new com.google.zxing.aztec.detector.Detector
            com.google.zxing.common.BitMatrix r11 = r11.getBlackMatrix()
            r0.<init>(r11)
            r11 = 0
            r1 = 0
            com.google.zxing.aztec.AztecDetectorResult r2 = r0.detect(r1)     // Catch: com.google.zxing.FormatException -> L2e com.google.zxing.NotFoundException -> L36
            com.google.zxing.ResultPoint[] r3 = r2.getPoints()     // Catch: com.google.zxing.FormatException -> L2e com.google.zxing.NotFoundException -> L36
            int r4 = r2.getErrorsCorrected()     // Catch: com.google.zxing.FormatException -> L2a com.google.zxing.NotFoundException -> L2c
            com.google.zxing.aztec.decoder.Decoder r5 = new com.google.zxing.aztec.decoder.Decoder     // Catch: com.google.zxing.FormatException -> L26 com.google.zxing.NotFoundException -> L28
            r5.<init>()     // Catch: com.google.zxing.FormatException -> L26 com.google.zxing.NotFoundException -> L28
            com.google.zxing.common.DecoderResult r2 = r5.decode(r2)     // Catch: com.google.zxing.FormatException -> L26 com.google.zxing.NotFoundException -> L28
            r5 = r4
            r4 = r3
            r3 = r11
            r11 = r2
            r2 = r3
            goto L3c
        L26:
            r2 = move-exception
            goto L31
        L28:
            r2 = move-exception
            goto L39
        L2a:
            r2 = move-exception
            goto L30
        L2c:
            r2 = move-exception
            goto L38
        L2e:
            r2 = move-exception
            r3 = r11
        L30:
            r4 = 0
        L31:
            r5 = r4
            r4 = r3
            r3 = r2
            r2 = r11
            goto L3c
        L36:
            r2 = move-exception
            r3 = r11
        L38:
            r4 = 0
        L39:
            r5 = r4
            r4 = r3
            r3 = r11
        L3c:
            if (r11 != 0) goto L5f
            r11 = 1
            com.google.zxing.aztec.AztecDetectorResult r11 = r0.detect(r11)     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            com.google.zxing.ResultPoint[] r4 = r11.getPoints()     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            int r5 = r11.getErrorsCorrected()     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            com.google.zxing.aztec.decoder.Decoder r0 = new com.google.zxing.aztec.decoder.Decoder     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            r0.<init>()     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            com.google.zxing.common.DecoderResult r11 = r0.decode(r11)     // Catch: com.google.zxing.FormatException -> L55 com.google.zxing.NotFoundException -> L57
            goto L5f
        L55:
            r11 = move-exception
            goto L58
        L57:
            r11 = move-exception
        L58:
            if (r2 != 0) goto L5e
            if (r3 == 0) goto L5d
            throw r3
        L5d:
            throw r11
        L5e:
            throw r2
        L5f:
            r6 = r4
            r0 = r5
            if (r12 == 0) goto L78
            com.google.zxing.DecodeHintType r2 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            java.lang.Object r12 = r12.get(r2)
            com.google.zxing.ResultPointCallback r12 = (com.google.zxing.ResultPointCallback) r12
            if (r12 == 0) goto L78
            int r2 = r6.length
        L6e:
            if (r1 >= r2) goto L78
            r3 = r6[r1]
            r12.foundPossibleResultPoint(r3)
            int r1 = r1 + 1
            goto L6e
        L78:
            com.google.zxing.Result r12 = new com.google.zxing.Result
            java.lang.String r3 = r11.getText()
            byte[] r4 = r11.getRawBytes()
            int r5 = r11.getNumBits()
            com.google.zxing.BarcodeFormat r7 = com.google.zxing.BarcodeFormat.AZTEC
            long r8 = java.lang.System.currentTimeMillis()
            r2 = r12
            r2.<init>(r3, r4, r5, r6, r7, r8)
            java.util.List r1 = r11.getByteSegments()
            if (r1 == 0) goto L9b
            com.google.zxing.ResultMetadataType r2 = com.google.zxing.ResultMetadataType.BYTE_SEGMENTS
            r12.putMetadata(r2, r1)
        L9b:
            java.lang.String r1 = r11.getECLevel()
            if (r1 == 0) goto La6
            com.google.zxing.ResultMetadataType r2 = com.google.zxing.ResultMetadataType.ERROR_CORRECTION_LEVEL
            r12.putMetadata(r2, r1)
        La6:
            java.lang.Integer r1 = r11.getErrorsCorrected()
            int r1 = r1.intValue()
            int r0 = r0 + r1
            com.google.zxing.ResultMetadataType r1 = com.google.zxing.ResultMetadataType.ERRORS_CORRECTED
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r12.putMetadata(r1, r0)
            com.google.zxing.ResultMetadataType r0 = com.google.zxing.ResultMetadataType.SYMBOLOGY_IDENTIFIER
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "]z"
            r1.<init>(r2)
            int r11 = r11.getSymbologyModifier()
            r1.append(r11)
            java.lang.String r11 = r1.toString()
            r12.putMetadata(r0, r11)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.AztecReader.decode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }
}
