package j$.time.chrono;

import j$.time.LocalTime;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.UnsupportedTemporalTypeException;
/* loaded from: classes2.dex */
public interface ChronoLocalDate extends Temporal, TemporalAdjuster, Comparable {

    /* renamed from: j$.time.chrono.ChronoLocalDate$-CC */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static int $default$compareTo(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            int compare = Long.compare(chronoLocalDate.toEpochDay(), chronoLocalDate2.toEpochDay());
            return compare == 0 ? chronoLocalDate.getChronology().compareTo(chronoLocalDate2.getChronology()) : compare;
        }

        public static boolean $default$isBefore(ChronoLocalDate chronoLocalDate, ChronoLocalDate chronoLocalDate2) {
            return chronoLocalDate.toEpochDay() < chronoLocalDate2.toEpochDay();
        }

        public static boolean $default$isSupported(ChronoLocalDate chronoLocalDate, TemporalField temporalField) {
            return temporalField instanceof ChronoField ? temporalField.isDateBased() : temporalField != null && temporalField.isSupportedBy(chronoLocalDate);
        }

        public static ChronoLocalDate $default$plus(ChronoLocalDate chronoLocalDate, long j, TemporalUnit temporalUnit) {
            if (temporalUnit instanceof ChronoUnit) {
                throw new UnsupportedTemporalTypeException("Unsupported unit: " + temporalUnit);
            }
            return ChronoLocalDateImpl.ensureValid(chronoLocalDate.getChronology(), temporalUnit.addTo(chronoLocalDate, j));
        }

        public static Object $default$query(ChronoLocalDate chronoLocalDate, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.offset() || temporalQuery == TemporalQueries.localTime()) {
                return null;
            }
            return temporalQuery == TemporalQueries.chronology() ? chronoLocalDate.getChronology() : temporalQuery == TemporalQueries.precision() ? ChronoUnit.DAYS : temporalQuery.queryFrom(chronoLocalDate);
        }

        public static ChronoLocalDate $default$with(ChronoLocalDate chronoLocalDate, TemporalAdjuster temporalAdjuster) {
            Temporal adjustInto;
            Chronology chronology = chronoLocalDate.getChronology();
            adjustInto = temporalAdjuster.adjustInto(chronoLocalDate);
            return ChronoLocalDateImpl.ensureValid(chronology, adjustInto);
        }

        public static ChronoLocalDate $default$with(ChronoLocalDate chronoLocalDate, TemporalField temporalField, long j) {
            if (temporalField instanceof ChronoField) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            return ChronoLocalDateImpl.ensureValid(chronoLocalDate.getChronology(), temporalField.adjustInto(chronoLocalDate, j));
        }
    }

    ChronoLocalDateTime atTime(LocalTime localTime);

    int compareTo(ChronoLocalDate chronoLocalDate);

    Chronology getChronology();

    int hashCode();

    @Override // j$.time.temporal.TemporalAccessor
    boolean isSupported(TemporalField temporalField);

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate plus(long j, TemporalUnit temporalUnit);

    long toEpochDay();

    String toString();

    @Override // j$.time.temporal.Temporal
    ChronoLocalDate with(TemporalField temporalField, long j);
}
