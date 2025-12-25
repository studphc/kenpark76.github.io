package androidx.media3.common.util;
/* loaded from: classes.dex */
public final class ConstantRateTimestampIterator implements TimestampIterator {
    private double currentTimestampUs;
    private final long durationUs;
    private final float frameRate;
    private final double framesDurationUs;
    private int framesToAdd;
    private final long startingTimestampUs;

    public ConstantRateTimestampIterator(long j, float f) {
        this(j, f, 0L);
    }

    public ConstantRateTimestampIterator(long j, float f, long j2) {
        Assertions.checkArgument(j > 0);
        Assertions.checkArgument(f > 0.0f);
        Assertions.checkArgument(j2 >= 0);
        this.durationUs = j;
        this.frameRate = f;
        this.startingTimestampUs = j2;
        this.currentTimestampUs = j2;
        this.framesToAdd = Math.round((((float) j) / 1000000.0f) * f);
        this.framesDurationUs = 1000000.0f / f;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public boolean hasNext() {
        return this.framesToAdd != 0;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public long next() {
        Assertions.checkState(hasNext());
        this.framesToAdd--;
        long round = Math.round(this.currentTimestampUs);
        this.currentTimestampUs += this.framesDurationUs;
        return round;
    }

    @Override // androidx.media3.common.util.TimestampIterator
    public ConstantRateTimestampIterator copyOf() {
        return new ConstantRateTimestampIterator(this.durationUs, this.frameRate, this.startingTimestampUs);
    }
}
