package j$.time.chrono;

import j$.time.DateTimeException;
import j$.time.Instant;
import j$.time.LocalTime;
import j$.time.ZoneId;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalQueries;
import j$.util.Objects;
/* loaded from: classes2.dex */
public interface Chronology extends Comparable {

    /* renamed from: j$.time.chrono.Chronology$-CC */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static ChronoLocalDateTime $default$localDateTime(Chronology chronology, TemporalAccessor temporalAccessor) {
            try {
                return chronology.date(temporalAccessor).atTime(LocalTime.from(temporalAccessor));
            } catch (DateTimeException e) {
                Class<?> cls = temporalAccessor.getClass();
                throw new DateTimeException("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + cls, e);
            }
        }

        public static ChronoZonedDateTime $default$zonedDateTime(Chronology chronology, Instant instant, ZoneId zoneId) {
            return ChronoZonedDateTimeImpl.ofInstant(chronology, instant, zoneId);
        }

        public static Chronology from(TemporalAccessor temporalAccessor) {
            Objects.requireNonNull(temporalAccessor, "temporal");
            return (Chronology) Objects.requireNonNullElse((Chronology) temporalAccessor.query(TemporalQueries.chronology()), IsoChronology.INSTANCE);
        }

        public static Chronology of(String str) {
            return AbstractChronology.of(str);
        }
    }

    int compareTo(Chronology chronology);

    ChronoLocalDate date(TemporalAccessor temporalAccessor);

    boolean equals(Object obj);

    Era eraOf(int i);

    String getCalendarType();

    String getId();

    int hashCode();

    ChronoLocalDateTime localDateTime(TemporalAccessor temporalAccessor);

    String toString();

    ChronoZonedDateTime zonedDateTime(Instant instant, ZoneId zoneId);
}
