package j$.time.chrono;

import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.ZoneOffset;
import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.Temporal;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.TemporalUnit;
import j$.time.temporal.UnsupportedTemporalTypeException;
import j$.time.temporal.ValueRange;
/* loaded from: classes2.dex */
public interface ChronoZonedDateTime extends Temporal, Comparable {

    /* renamed from: j$.time.chrono.ChronoZonedDateTime$-CC */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static int $default$compareTo(ChronoZonedDateTime chronoZonedDateTime, ChronoZonedDateTime chronoZonedDateTime2) {
            int compare = Long.compare(chronoZonedDateTime.toEpochSecond(), chronoZonedDateTime2.toEpochSecond());
            if (compare == 0) {
                int nano = chronoZonedDateTime.toLocalTime().getNano() - chronoZonedDateTime2.toLocalTime().getNano();
                if (nano == 0) {
                    int compareTo = chronoZonedDateTime.toLocalDateTime().compareTo(chronoZonedDateTime2.toLocalDateTime());
                    if (compareTo == 0) {
                        int compareTo2 = chronoZonedDateTime.getZone().getId().compareTo(chronoZonedDateTime2.getZone().getId());
                        return compareTo2 == 0 ? chronoZonedDateTime.getChronology().compareTo(chronoZonedDateTime2.getChronology()) : compareTo2;
                    }
                    return compareTo;
                }
                return nano;
            }
            return compare;
        }

        public static /* bridge */ /* synthetic */ int $default$compareTo(ChronoZonedDateTime chronoZonedDateTime, Object obj) {
            return chronoZonedDateTime.compareTo((ChronoZonedDateTime) obj);
        }

        public static int $default$get(ChronoZonedDateTime chronoZonedDateTime, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
                if (i != 1) {
                    return i != 2 ? chronoZonedDateTime.toLocalDateTime().get(temporalField) : chronoZonedDateTime.getOffset().getTotalSeconds();
                }
                throw new UnsupportedTemporalTypeException("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
            }
            return TemporalAccessor.CC.$default$get(chronoZonedDateTime, temporalField);
        }

        public static Chronology $default$getChronology(ChronoZonedDateTime chronoZonedDateTime) {
            return chronoZonedDateTime.toLocalDate().getChronology();
        }

        public static long $default$getLong(ChronoZonedDateTime chronoZonedDateTime, TemporalField temporalField) {
            if (temporalField instanceof ChronoField) {
                int i = AnonymousClass1.$SwitchMap$java$time$temporal$ChronoField[((ChronoField) temporalField).ordinal()];
                return i != 1 ? i != 2 ? chronoZonedDateTime.toLocalDateTime().getLong(temporalField) : chronoZonedDateTime.getOffset().getTotalSeconds() : chronoZonedDateTime.toEpochSecond();
            }
            return temporalField.getFrom(chronoZonedDateTime);
        }

        public static Object $default$query(ChronoZonedDateTime chronoZonedDateTime, TemporalQuery temporalQuery) {
            return (temporalQuery == TemporalQueries.zone() || temporalQuery == TemporalQueries.zoneId()) ? chronoZonedDateTime.getZone() : temporalQuery == TemporalQueries.offset() ? chronoZonedDateTime.getOffset() : temporalQuery == TemporalQueries.localTime() ? chronoZonedDateTime.toLocalTime() : temporalQuery == TemporalQueries.chronology() ? chronoZonedDateTime.getChronology() : temporalQuery == TemporalQueries.precision() ? ChronoUnit.NANOS : temporalQuery.queryFrom(chronoZonedDateTime);
        }

        public static ValueRange $default$range(ChronoZonedDateTime chronoZonedDateTime, TemporalField temporalField) {
            return temporalField instanceof ChronoField ? (temporalField == ChronoField.INSTANT_SECONDS || temporalField == ChronoField.OFFSET_SECONDS) ? temporalField.range() : chronoZonedDateTime.toLocalDateTime().range(temporalField) : temporalField.rangeRefinedBy(chronoZonedDateTime);
        }

        public static long $default$toEpochSecond(ChronoZonedDateTime chronoZonedDateTime) {
            return ((chronoZonedDateTime.toLocalDate().toEpochDay() * 86400) + chronoZonedDateTime.toLocalTime().toSecondOfDay()) - chronoZonedDateTime.getOffset().getTotalSeconds();
        }

        public static ChronoZonedDateTime $default$with(ChronoZonedDateTime chronoZonedDateTime, TemporalAdjuster temporalAdjuster) {
            Temporal adjustInto;
            Chronology chronology = chronoZonedDateTime.getChronology();
            adjustInto = temporalAdjuster.adjustInto(chronoZonedDateTime);
            return ChronoZonedDateTimeImpl.ensureValid(chronology, adjustInto);
        }
    }

    /* renamed from: j$.time.chrono.ChronoZonedDateTime$1 */
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

    int compareTo(ChronoZonedDateTime chronoZonedDateTime);

    Chronology getChronology();

    ZoneOffset getOffset();

    ZoneId getZone();

    @Override // j$.time.temporal.Temporal
    ChronoZonedDateTime minus(long j, TemporalUnit temporalUnit);

    long toEpochSecond();

    ChronoLocalDate toLocalDate();

    ChronoLocalDateTime toLocalDateTime();

    LocalTime toLocalTime();

    @Override // j$.time.temporal.Temporal
    ChronoZonedDateTime with(TemporalAdjuster temporalAdjuster);

    ChronoZonedDateTime withZoneSameLocal(ZoneId zoneId);
}
