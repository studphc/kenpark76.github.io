package j$.time;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
/* loaded from: classes2.dex */
public final class OffsetTime implements Temporal, TemporalAdjuster, Comparable, Serializable {
    private static final long serialVersionUID = 7264499704384272492L;
    private final ZoneOffset offset;
    private final LocalTime time;
    public static final OffsetTime MIN = LocalTime.MIN.atOffset(ZoneOffset.MAX);
    public static final OffsetTime MAX = LocalTime.MAX.atOffset(ZoneOffset.MIN);

    private OffsetTime(LocalTime localTime, ZoneOffset zoneOffset) {
        this.time = (LocalTime) Objects.requireNonNull(localTime, "time");
        this.offset = (ZoneOffset) Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
    }

    public static OffsetTime of(LocalTime localTime, ZoneOffset zoneOffset) {
        return new OffsetTime(localTime, zoneOffset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static OffsetTime readExternal(ObjectInput objectInput) {
        return of(LocalTime.readExternal(objectInput), ZoneOffset.readExternal(objectInput));
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private long toEpochNano() {
        return this.time.toNanoOfDay() - (this.offset.getTotalSeconds() * 1000000000);
    }

    private OffsetTime with(LocalTime localTime, ZoneOffset zoneOffset) {
        return (this.time == localTime && this.offset.equals(zoneOffset)) ? this : new OffsetTime(localTime, zoneOffset);
    }

    private Object writeReplace() {
        return new Ser((byte) 9, this);
    }

    @Override // j$.time.temporal.TemporalAdjuster
    public Temporal adjustInto(Temporal temporal) {
        return temporal.with(ChronoField.NANO_OF_DAY, this.time.toNanoOfDay()).with(ChronoField.OFFSET_SECONDS, this.offset.getTotalSeconds());
    }

    @Override // java.lang.Comparable
    public int compareTo(OffsetTime offsetTime) {
        int compare;
        return (this.offset.equals(offsetTime.offset) || (compare = Long.compare(toEpochNano(), offsetTime.toEpochNano())) == 0) ? this.time.compareTo(offsetTime.time) : compare;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetTime) {
            OffsetTime offsetTime = (OffsetTime) obj;
            return this.time.equals(offsetTime.time) && this.offset.equals(offsetTime.offset);
        }
        return false;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public int get(TemporalField temporalField) {
        return TemporalAccessor.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public long getLong(TemporalField temporalField) {
        return temporalField instanceof ChronoField ? temporalField == ChronoField.OFFSET_SECONDS ? this.offset.getTotalSeconds() : this.time.getLong(temporalField) : temporalField.getFrom(this);
    }

    public int hashCode() {
        return this.time.hashCode() ^ this.offset.hashCode();
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        return temporalField instanceof ChronoField ? temporalField.isTimeBased() || temporalField == ChronoField.OFFSET_SECONDS : temporalField != null && temporalField.isSupportedBy(this);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime minus(long j, TemporalUnit temporalUnit) {
        return j == Long.MIN_VALUE ? plus(Long.MAX_VALUE, temporalUnit).plus(1L, temporalUnit) : plus(-j, temporalUnit);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime plus(long j, TemporalUnit temporalUnit) {
        return temporalUnit instanceof ChronoUnit ? with(this.time.plus(j, temporalUnit), this.offset) : (OffsetTime) temporalUnit.addTo(this, j);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public Object query(TemporalQuery temporalQuery) {
        if (temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.zone()) {
            return this.offset;
        }
        if (((temporalQuery == TemporalQueries.zoneId()) || (temporalQuery == TemporalQueries.chronology())) || temporalQuery == TemporalQueries.localDate()) {
            return null;
        }
        return temporalQuery == TemporalQueries.localTime() ? this.time : temporalQuery == TemporalQueries.precision() ? ChronoUnit.NANOS : temporalQuery.queryFrom(this);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public ValueRange range(TemporalField temporalField) {
        return temporalField instanceof ChronoField ? temporalField == ChronoField.OFFSET_SECONDS ? temporalField.range() : this.time.range(temporalField) : temporalField.rangeRefinedBy(this);
    }

    public String toString() {
        String localTime = this.time.toString();
        String zoneOffset = this.offset.toString();
        return localTime + zoneOffset;
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime with(TemporalAdjuster temporalAdjuster) {
        return temporalAdjuster instanceof LocalTime ? with((LocalTime) temporalAdjuster, this.offset) : temporalAdjuster instanceof ZoneOffset ? with(this.time, (ZoneOffset) temporalAdjuster) : temporalAdjuster instanceof OffsetTime ? (OffsetTime) temporalAdjuster : (OffsetTime) temporalAdjuster.adjustInto(this);
    }

    @Override // j$.time.temporal.Temporal
    public OffsetTime with(TemporalField temporalField, long j) {
        return temporalField instanceof ChronoField ? temporalField == ChronoField.OFFSET_SECONDS ? with(this.time, ZoneOffset.ofTotalSeconds(((ChronoField) temporalField).checkValidIntValue(j))) : with(this.time.with(temporalField, j), this.offset) : (OffsetTime) temporalField.adjustInto(this, j);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeExternal(ObjectOutput objectOutput) {
        this.time.writeExternal(objectOutput);
        this.offset.writeExternal(objectOutput);
    }
}
