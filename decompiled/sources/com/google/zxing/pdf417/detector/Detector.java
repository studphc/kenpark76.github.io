package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/* loaded from: classes3.dex */
public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final float MAX_AVG_VARIANCE = 0.42f;
    private static final float MAX_INDIVIDUAL_VARIANCE = 0.8f;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final float MAX_STOP_PATTERN_HEIGHT_VARIANCE = 0.5f;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};
    private static final int[] ROTATIONS = {0, 180, 270, 90};

    private Detector() {
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException {
        int[] iArr;
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        for (int i : ROTATIONS) {
            BitMatrix applyRotation = applyRotation(blackMatrix, i);
            List<ResultPoint[]> detect = detect(z, applyRotation);
            if (!detect.isEmpty()) {
                return new PDF417DetectorResult(applyRotation, detect, i);
            }
        }
        return new PDF417DetectorResult(blackMatrix, new ArrayList(), 0);
    }

    private static BitMatrix applyRotation(BitMatrix bitMatrix, int i) {
        if (i % 360 == 0) {
            return bitMatrix;
        }
        BitMatrix m475clone = bitMatrix.m475clone();
        m475clone.rotate(i);
        return m475clone;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
        if (r4 != false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
        r3 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
        if (r3.hasNext() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
        r4 = (com.google.zxing.ResultPoint[]) r3.next();
        r7 = r4[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
        if (r7 == null) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0034, code lost:
        r2 = (int) java.lang.Math.max(r2, r7.getY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
        r4 = r4[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
        if (r4 == null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
        r2 = java.lang.Math.max(r2, (int) r4.getY());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.util.List<com.google.zxing.ResultPoint[]> detect(boolean r8, com.google.zxing.common.BitMatrix r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = 0
        L7:
            r3 = 0
            r4 = 0
        L9:
            int r5 = r9.getHeight()
            if (r2 >= r5) goto L78
            com.google.zxing.ResultPoint[] r3 = findVertices(r9, r2, r3)
            r5 = r3[r1]
            r6 = 1
            if (r5 != 0) goto L4f
            r5 = 3
            r7 = r3[r5]
            if (r7 != 0) goto L4f
            if (r4 != 0) goto L20
            goto L78
        L20:
            java.util.Iterator r3 = r0.iterator()
        L24:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L4c
            java.lang.Object r4 = r3.next()
            com.google.zxing.ResultPoint[] r4 = (com.google.zxing.ResultPoint[]) r4
            r7 = r4[r6]
            if (r7 == 0) goto L3e
            float r2 = (float) r2
            float r7 = r7.getY()
            float r2 = java.lang.Math.max(r2, r7)
            int r2 = (int) r2
        L3e:
            r4 = r4[r5]
            if (r4 == 0) goto L24
            float r4 = r4.getY()
            int r4 = (int) r4
            int r2 = java.lang.Math.max(r2, r4)
            goto L24
        L4c:
            int r2 = r2 + 5
            goto L7
        L4f:
            r0.add(r3)
            if (r8 != 0) goto L55
            goto L78
        L55:
            r2 = 2
            r4 = r3[r2]
            if (r4 == 0) goto L66
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
            goto L74
        L66:
            r2 = 4
            r4 = r3[r2]
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
        L74:
            int r2 = (int) r2
            r3 = r4
            r4 = 1
            goto L9
        L78:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.detector.Detector.detect(boolean, com.google.zxing.common.BitMatrix):java.util.List");
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        ResultPoint[] resultPointArr = new ResultPoint[8];
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i, i2, 10, START_PATTERN), INDEXES_START_PATTERN);
        ResultPoint resultPoint = resultPointArr[4];
        if (resultPoint != null) {
            i2 = (int) resultPoint.getX();
            i = (int) resultPointArr[4].getY();
            ResultPoint resultPoint2 = resultPointArr[5];
            if (resultPoint2 != null) {
                i3 = i;
                i4 = i2;
                i5 = (int) Math.max((((int) resultPoint2.getY()) - i) * 0.5f, 10.0f);
                copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i3, i4, i5, STOP_PATTERN), INDEXES_STOP_PATTERN);
                return resultPointArr;
            }
        }
        i3 = i;
        i4 = i2;
        i5 = 10;
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i3, i4, i5, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return resultPointArr;
    }

    private static void copyToResult(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i = 0; i < iArr.length; i++) {
            resultPointArr[iArr[i]] = resultPointArr2[i];
        }
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        boolean z;
        int i6;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i7 = i3;
        while (true) {
            if (i7 >= i) {
                z = false;
                break;
            }
            int[] findGuardPattern = findGuardPattern(bitMatrix, i4, i7, i2, iArr, iArr2);
            if (findGuardPattern != null) {
                int i8 = i7;
                int[] iArr3 = findGuardPattern;
                int i9 = i8;
                while (true) {
                    if (i9 <= 0) {
                        i6 = i9;
                        break;
                    }
                    int i10 = i9 - 1;
                    int[] findGuardPattern2 = findGuardPattern(bitMatrix, i4, i10, i2, iArr, iArr2);
                    if (findGuardPattern2 == null) {
                        i6 = i10 + 1;
                        break;
                    }
                    iArr3 = findGuardPattern2;
                    i9 = i10;
                }
                float f = i6;
                resultPointArr[0] = new ResultPoint(iArr3[0], f);
                resultPointArr[1] = new ResultPoint(iArr3[1], f);
                i7 = i6;
                z = true;
            } else {
                i7 += 5;
            }
        }
        int i11 = i7 + 1;
        if (z) {
            int[] iArr4 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i12 = i11;
            int i13 = 0;
            while (i12 < i) {
                int[] findGuardPattern3 = findGuardPattern(bitMatrix, iArr4[0], i12, i2, iArr, iArr2);
                if (findGuardPattern3 != null && Math.abs(iArr4[0] - findGuardPattern3[0]) < 5 && Math.abs(iArr4[1] - findGuardPattern3[1]) < 5) {
                    iArr4 = findGuardPattern3;
                    i13 = 0;
                } else if (i13 > 25) {
                    break;
                } else {
                    i13++;
                }
                i12++;
            }
            i11 = i12 - (i13 + 1);
            float f2 = i11;
            resultPointArr[2] = new ResultPoint(iArr4[0], f2);
            resultPointArr[3] = new ResultPoint(iArr4[1], f2);
        }
        if (i11 - i7 < i5) {
            Arrays.fill(resultPointArr, (Object) null);
        }
        return resultPointArr;
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int i, int i2, int i3, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int i4 = 0;
        while (bitMatrix.get(i, i2) && i > 0) {
            int i5 = i4 + 1;
            if (i4 >= 3) {
                break;
            }
            i--;
            i4 = i5;
        }
        int length = iArr.length;
        int i6 = i;
        int i7 = 0;
        boolean z = false;
        while (i < i3) {
            if (bitMatrix.get(i, i2) != z) {
                iArr2[i7] = iArr2[i7] + 1;
            } else {
                if (i7 != length - 1) {
                    i7++;
                } else if (patternMatchVariance(iArr2, iArr) < MAX_AVG_VARIANCE) {
                    return new int[]{i6, i};
                } else {
                    i6 += iArr2[0] + iArr2[1];
                    int i8 = i7 - 1;
                    System.arraycopy(iArr2, 2, iArr2, 0, i8);
                    iArr2[i8] = 0;
                    iArr2[i7] = 0;
                    i7--;
                }
                iArr2[i7] = 1;
                z = !z;
            }
            i++;
        }
        if (i7 != length - 1 || patternMatchVariance(iArr2, iArr) >= MAX_AVG_VARIANCE) {
            return null;
        }
        return new int[]{i6, i - 1};
    }

    private static float patternMatchVariance(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f = i;
        float f2 = f / i2;
        float f3 = MAX_INDIVIDUAL_VARIANCE * f2;
        float f4 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f5 = iArr2[i4] * f2;
            float f6 = iArr[i4];
            float f7 = f6 > f5 ? f6 - f5 : f5 - f6;
            if (f7 > f3) {
                return Float.POSITIVE_INFINITY;
            }
            f4 += f7;
        }
        return f4 / f;
    }
}
