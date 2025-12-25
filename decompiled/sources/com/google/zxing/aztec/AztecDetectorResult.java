package com.google.zxing.aztec;

import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
/* loaded from: classes3.dex */
public final class AztecDetectorResult extends DetectorResult {
    private final boolean compact;
    private final int errorsCorrected;
    private final int nbDatablocks;
    private final int nbLayers;

    public AztecDetectorResult(BitMatrix bitMatrix, ResultPoint[] resultPointArr, boolean z, int i, int i2) {
        this(bitMatrix, resultPointArr, z, i, i2, 0);
    }

    public AztecDetectorResult(BitMatrix bitMatrix, ResultPoint[] resultPointArr, boolean z, int i, int i2, int i3) {
        super(bitMatrix, resultPointArr);
        this.compact = z;
        this.nbDatablocks = i;
        this.nbLayers = i2;
        this.errorsCorrected = i3;
    }

    public int getNbLayers() {
        return this.nbLayers;
    }

    public int getNbDatablocks() {
        return this.nbDatablocks;
    }

    public boolean isCompact() {
        return this.compact;
    }

    public int getErrorsCorrected() {
        return this.errorsCorrected;
    }
}
