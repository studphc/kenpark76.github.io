package j$.time.temporal;

import j$.time.DateTimeException;
import j$.util.Objects;
/* loaded from: classes2.dex */
public interface TemporalAccessor {

    /* renamed from: j$.time.temporal.TemporalAccessor$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static int $default$get(TemporalAccessor temporalAccessor, TemporalField temporalField) {
            ValueRange range = temporalAccessor.range(temporalField);
            if (!range.isIntValue()) {
                throw new UnsupportedTemporalTypeException("Invalid field " + temporalField + " for get() method, use getLong() instead");
            }
            long j = temporalAccessor.getLong(temporalField);
            if (range.isValidValue(j)) {
                return (int) j;
            }
            throw new DateTimeException("Invalid value for " + temporalField + " (valid values " + range + "): " + j);
        }

        public static Object $default$query(TemporalAccessor temporalAccessor, TemporalQuery temporalQuery) {
            if (temporalQuery == TemporalQueries.zoneId() || temporalQuery == TemporalQueries.chronology() || temporalQuery == TemporalQueries.precision()) {
                return null;
            }
            return temporalQuery.queryFrom(temporalAccessor);
        }

        public static ValueRange $default$range(TemporalAccessor temporalAccessor, TemporalField temporalField) {
            if (!(temporalField instanceof ChronoField)) {
                Objects.requireNonNull(temporalField, "field");
                return temporalField.rangeRefinedBy(temporalAccessor);
            } else if (temporalAccessor.isSupported(temporalField)) {
                return temporalField.range();
            } else {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
        }
    }

    int get(TemporalField temporalField);

    long getLong(TemporalField temporalField);

    boolean isSupported(TemporalField temporalField);

    Object query(TemporalQuery temporalQuery);

    ValueRange range(TemporalField temporalField);
}
