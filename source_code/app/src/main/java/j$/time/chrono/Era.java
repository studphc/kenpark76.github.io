package j$.time.chrono;

import j$.time.temporal.ChronoField;
import j$.time.temporal.ChronoUnit;
import j$.time.temporal.TemporalAccessor;
import j$.time.temporal.TemporalAdjuster;
import j$.time.temporal.TemporalField;
import j$.time.temporal.TemporalQueries;
import j$.time.temporal.TemporalQuery;
import j$.time.temporal.UnsupportedTemporalTypeException;
/* loaded from: classes2.dex */
public interface Era extends TemporalAccessor, TemporalAdjuster {

    /* renamed from: j$.time.chrono.Era$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static int $default$get(Era era, TemporalField temporalField) {
            return temporalField == ChronoField.ERA ? era.getValue() : TemporalAccessor.CC.$default$get(era, temporalField);
        }

        public static long $default$getLong(Era era, TemporalField temporalField) {
            if (temporalField == ChronoField.ERA) {
                return era.getValue();
            }
            if (temporalField instanceof ChronoField) {
                throw new UnsupportedTemporalTypeException("Unsupported field: " + temporalField);
            }
            return temporalField.getFrom(era);
        }

        public static boolean $default$isSupported(Era era, TemporalField temporalField) {
            return temporalField instanceof ChronoField ? temporalField == ChronoField.ERA : temporalField != null && temporalField.isSupportedBy(era);
        }

        public static Object $default$query(Era era, TemporalQuery temporalQuery) {
            return temporalQuery == TemporalQueries.precision() ? ChronoUnit.ERAS : TemporalAccessor.CC.$default$query(era, temporalQuery);
        }
    }

    int getValue();
}
