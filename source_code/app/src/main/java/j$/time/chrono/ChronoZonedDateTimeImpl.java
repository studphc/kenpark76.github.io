package j$.time.chrono;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import j$.time.Instant;
import j$.time.LocalDateTime;
import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.chrono.ChronoZonedDateTime;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.ValueRange;
import j$.util.Objects;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.Serializable;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class ChronoZonedDateTimeImpl implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;
    private final transient ChronoLocalDateTimeImpl dateTime;
    private final transient ZoneOffset offset;
    private final transient ZoneId zone;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.time.chrono.ChronoZonedDateTimeImpl$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$ChronoField;

        static {
            int[] iArr = new int[ChronoField.values().length];
            $SwitchMap$java$time$temporal$ChronoField = iArr;
            try {
                iArr[ChronoField.INSTANT_SECONDS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$ChronoField[ChronoField.OFFSET_SECONDS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private ChronoZonedDateTimeImpl(ChronoLocalDateTimeImpl chronoLocalDateTimeImpl, ZoneOffset zoneOffset, ZoneId zoneId) {
        this.dateTime = (ChronoLocalDateTimeImpl) Objects.requireNonNull(chronoLocalDateTimeImpl, "dateTime");
        this.offset = (ZoneOffset) Objects.requireNonNull(zoneOffset, TypedValues.CycleType.S_WAVE_OFFSET);
        this.zone = (ZoneId) Objects.requireNonNull(zoneId, "zone");
    }

    private ChronoZonedDateTimeImpl create(Instant instant, ZoneId zoneId) {
        return ofInstant(getChronology(), instant, zoneId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ChronoZonedDateTimeImpl ensureValid(Chronology chronology, Temporal temporal) {
        ChronoZonedDateTimeImpl chronoZonedDateTimeImpl = (ChronoZonedDateTimeImpl) temporal;
        if (chronology.equals(chronoZonedDateTimeImpl.getChronology())) {
            return chronoZonedDateTimeImpl;
        }
        String id = chronology.getId();
        String id2 = chronoZonedDateTimeImpl.getChronology().getId();
        throw new ClassCastException("Chronology mismatch, required: " + id + ", actual: " + id2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0053, code lost:
        if (r2.contains(r8) != false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static j$.time.chrono.ChronoZonedDateTime ofBest(j$.time.chrono.ChronoLocalDateTimeImpl r6, j$.time.ZoneId r7, j$.time.ZoneOffset r8) {
        /*
            java.lang.String r0 = "localDateTime"
            j$.util.Objects.requireNonNull(r6, r0)
            java.lang.String r0 = "zone"
            j$.util.Objects.requireNonNull(r7, r0)
            boolean r0 = r7 instanceof j$.time.ZoneOffset
            if (r0 == 0) goto L17
            j$.time.chrono.ChronoZonedDateTimeImpl r8 = new j$.time.chrono.ChronoZonedDateTimeImpl
            r0 = r7
            j$.time.ZoneOffset r0 = (j$.time.ZoneOffset) r0
            r8.<init>(r6, r0, r7)
            return r8
        L17:
            j$.time.zone.ZoneRules r0 = r7.getRules()
            j$.time.LocalDateTime r1 = j$.time.LocalDateTime.from(r6)
            java.util.List r2 = r0.getValidOffsets(r1)
            int r3 = r2.size()
            r4 = 1
            r5 = 0
            if (r3 != r4) goto L32
        L2b:
            java.lang.Object r8 = r2.get(r5)
            j$.time.ZoneOffset r8 = (j$.time.ZoneOffset) r8
            goto L55
        L32:
            int r3 = r2.size()
            if (r3 != 0) goto L4d
            j$.time.zone.ZoneOffsetTransition r8 = r0.getTransition(r1)
            j$.time.Duration r0 = r8.getDuration()
            long r0 = r0.getSeconds()
            j$.time.chrono.ChronoLocalDateTimeImpl r6 = r6.plusSeconds(r0)
            j$.time.ZoneOffset r8 = r8.getOffsetAfter()
            goto L55
        L4d:
            if (r8 == 0) goto L2b
            boolean r0 = r2.contains(r8)
            if (r0 == 0) goto L2b
        L55:
            java.lang.String r0 = "offset"
            j$.util.Objects.requireNonNull(r8, r0)
            j$.time.chrono.ChronoZonedDateTimeImpl r0 = new j$.time.chrono.ChronoZonedDateTimeImpl
            r0.<init>(r6, r8, r7)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.time.chrono.ChronoZonedDateTimeImpl.ofBest(j$.time.chrono.ChronoLocalDateTimeImpl, j$.time.ZoneId, j$.time.ZoneOffset):j$.time.chrono.ChronoZonedDateTime");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ChronoZonedDateTimeImpl ofInstant(Chronology chronology, Instant instant, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        Objects.requireNonNull(offset, TypedValues.CycleType.S_WAVE_OFFSET);
        return new ChronoZonedDateTimeImpl((ChronoLocalDateTimeImpl) chronology.localDateTime(LocalDateTime.ofEpochSecond(instant.getEpochSecond(), instant.getNano(), offset)), offset, zoneId);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ChronoZonedDateTime readExternal(ObjectInput objectInput) {
        return ((ChronoLocalDateTime) objectInput.readObject()).atZone((ZoneOffset) objectInput.readObject()).withZoneSameLocal((ZoneId) objectInput.readObject());
    }

    private void readObject(ObjectInputStream objectInputStream) {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new Ser((byte) 3, this);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ int compareTo(ChronoZonedDateTime chronoZonedDateTime) {
        return ChronoZonedDateTime.CC.$default$compareTo((ChronoZonedDateTime) this, chronoZonedDateTime);
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        int compareTo;
        compareTo = compareTo((ChronoZonedDateTime) obj);
        return compareTo;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && compareTo((ChronoZonedDateTime) obj) == 0;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ int get(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$get(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ Chronology getChronology() {
        Chronology chronology;
        chronology = toLocalDate().getChronology();
        return chronology;
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ long getLong(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$getLong(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ZoneOffset getOffset() {
        return this.offset;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ZoneId getZone() {
        return this.zone;
    }

    public int hashCode() {
        return (toLocalDateTime().hashCode() ^ getOffset().hashCode()) ^ Integer.rotateLeft(getZone().hashCode(), 3);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public boolean isSupported(TemporalField temporalField) {
        return (temporalField instanceof ChronoField) || (temporalField != null && temporalField.isSupportedBy(this));
    }

    @Override // j$.time.chrono.ChronoZonedDateTime, j$.time.temporal.Temporal
    public /* synthetic */ ChronoZonedDateTime minus(long j, TemporalUnit temporalUnit) {
        ChronoZonedDateTime ensureValid;
        ensureValid = ensureValid(getChronology(), Temporal.CC.$default$minus(this, j, temporalUnit));
        return ensureValid;
    }

    @Override // j$.time.temporal.Temporal
    public /* bridge */ /* synthetic */ Temporal minus(long j, TemporalUnit temporalUnit) {
        Temporal minus;
        minus = minus(j, temporalUnit);
        return minus;
    }

    @Override // j$.time.temporal.Temporal
    public ChronoZonedDateTime plus(long j, TemporalUnit temporalUnit) {
        return temporalUnit instanceof ChronoUnit ? with((TemporalAdjuster) this.dateTime.plus(j, temporalUnit)) : ensureValid(getChronology(), temporalUnit.addTo(this, j));
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ Object query(TemporalQuery temporalQuery) {
        return ChronoZonedDateTime.CC.$default$query(this, temporalQuery);
    }

    @Override // j$.time.temporal.TemporalAccessor
    public /* synthetic */ ValueRange range(TemporalField temporalField) {
        return ChronoZonedDateTime.CC.$default$range(this, temporalField);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ long toEpochSecond() {
        return ChronoZonedDateTime.CC.$default$toEpochSecond(this);
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ ChronoLocalDate toLocalDate() {
        ChronoLocalDate localDate;
        localDate = toLocalDateTime().toLocalDate();
        return localDate;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ChronoLocalDateTime toLocalDateTime() {
        return this.dateTime;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public /* synthetic */ LocalTime toLocalTime() {
        LocalTime localTime;
        localTime = toLocalDateTime().toLocalTime();
        return localTime;
    }

    public String toString() {
        String str = toLocalDateTime().toString() + getOffset().toString();
        if (getOffset() != getZone()) {
            return str + "[" + getZone().toString() + "]";
        }
        return str;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime, j$.time.temporal.Temporal
    public /* synthetic */ ChronoZonedDateTime with(TemporalAdjuster temporalAdjuster) {
        return ChronoZonedDateTime.CC.$default$with((ChronoZonedDateTime) this, temporalAdjuster);
    }

    @Override // j$.time.temporal.Temporal
    public ChronoZonedDateTime with(TemporalField temporalField, long j) {
        if (temporalField instanceof ChronoField) {
            ChronoField chronoField = (ChronoField) temporalField;
            int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[chronoField.ordinal()];
            if (i != 1) {
                if (i != 2) {
                    return ofBest(this.dateTime.with(temporalField, j), this.zone, this.offset);
                }
                return create(this.dateTime.toInstant(ZoneOffset.ofTotalSeconds(chronoField.checkValidIntValue(j))), this.zone);
            }
            return plus(j - toEpochSecond(), (TemporalUnit) ChronoUnit.SECONDS);
        }
        return ensureValid(getChronology(), temporalField.adjustInto(this, j));
    }

    @Override // j$.time.temporal.Temporal
    public /* bridge */ /* synthetic */ Temporal with(TemporalAdjuster temporalAdjuster) {
        Temporal with;
        with = with(temporalAdjuster);
        return with;
    }

    @Override // j$.time.chrono.ChronoZonedDateTime
    public ChronoZonedDateTime withZoneSameLocal(ZoneId zoneId) {
        return ofBest(this.dateTime, zoneId, this.offset);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeObject(this.dateTime);
        objectOutput.writeObject(this.offset);
        objectOutput.writeObject(this.zone);
    }
}
