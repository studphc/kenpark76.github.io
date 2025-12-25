package j$.time.temporal;

import j$.time.Clock$OffsetClock$$ExternalSyntheticBackport0;
import j$.time.DayOfWeek;
import j$.time.Duration;
import j$.time.Instant$$ExternalSyntheticBackport0;
import j$.time.LocalDate;
import j$.time.chrono.Chronology;
import j$.time.chrono.IsoChronology;
/* loaded from: classes2.dex */
public abstract class IsoFields {
    public static final TemporalField DAY_OF_QUARTER = Field.DAY_OF_QUARTER;
    public static final TemporalField QUARTER_OF_YEAR = Field.QUARTER_OF_YEAR;
    public static final TemporalField WEEK_OF_WEEK_BASED_YEAR = Field.WEEK_OF_WEEK_BASED_YEAR;
    public static final TemporalField WEEK_BASED_YEAR = Field.WEEK_BASED_YEAR;
    public static final TemporalUnit WEEK_BASED_YEARS = Unit.WEEK_BASED_YEARS;
    public static final TemporalUnit QUARTER_YEARS = Unit.QUARTER_YEARS;

    /* renamed from: j$.time.temporal.IsoFields$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$time$temporal$IsoFields$Unit;

        static {
            int[] iArr = new int[Unit.values().length];
            $SwitchMap$java$time$temporal$IsoFields$Unit = iArr;
            try {
                iArr[Unit.WEEK_BASED_YEARS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$time$temporal$IsoFields$Unit[Unit.QUARTER_YEARS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* loaded from: classes2.dex */
    public static abstract class Field implements TemporalField {
        private static final /* synthetic */ Field[] $VALUES;
        public static final Field DAY_OF_QUARTER;
        private static final int[] QUARTER_DAYS;
        public static final Field QUARTER_OF_YEAR;
        public static final Field WEEK_BASED_YEAR;
        public static final Field WEEK_OF_WEEK_BASED_YEAR;

        /* renamed from: j$.time.temporal.IsoFields$Field$1  reason: invalid class name */
        /* loaded from: classes2.dex */
        enum AnonymousClass1 extends Field {
            private AnonymousClass1(String str, int i) {
                super(str, i);
            }

            @Override // j$.time.temporal.TemporalField
            public Temporal adjustInto(Temporal temporal, long j) {
                long from = getFrom(temporal);
                range().checkValidValue(j, this);
                ChronoField chronoField = ChronoField.DAY_OF_YEAR;
                return temporal.with(chronoField, temporal.getLong(chronoField) + (j - from));
            }

            @Override // j$.time.temporal.TemporalField
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return temporalAccessor.get(ChronoField.DAY_OF_YEAR) - Field.QUARTER_DAYS[((temporalAccessor.get(ChronoField.MONTH_OF_YEAR) - 1) / 3) + (IsoChronology.INSTANCE.isLeapYear(temporalAccessor.getLong(ChronoField.YEAR)) ? 4 : 0)];
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
            }

            @Override // j$.time.temporal.TemporalField
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.DAY_OF_YEAR) && temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && temporalAccessor.isSupported(ChronoField.YEAR) && IsoFields.isIso(temporalAccessor);
            }

            @Override // j$.time.temporal.TemporalField
            public ValueRange range() {
                return ValueRange.of(1L, 90L, 92L);
            }

            @Override // j$.time.temporal.IsoFields.Field, j$.time.temporal.TemporalField
            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    long j = temporalAccessor.getLong(Field.QUARTER_OF_YEAR);
                    if (j == 1) {
                        return IsoChronology.INSTANCE.isLeapYear(temporalAccessor.getLong(ChronoField.YEAR)) ? ValueRange.of(1L, 91L) : ValueRange.of(1L, 90L);
                    }
                    return j == 2 ? ValueRange.of(1L, 91L) : (j == 3 || j == 4) ? ValueRange.of(1L, 92L) : range();
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: DayOfQuarter");
            }

            @Override // java.lang.Enum
            public String toString() {
                return "DayOfQuarter";
            }
        }

        /* renamed from: j$.time.temporal.IsoFields$Field$2  reason: invalid class name */
        /* loaded from: classes2.dex */
        enum AnonymousClass2 extends Field {
            private AnonymousClass2(String str, int i) {
                super(str, i);
            }

            @Override // j$.time.temporal.TemporalField
            public Temporal adjustInto(Temporal temporal, long j) {
                long from = getFrom(temporal);
                range().checkValidValue(j, this);
                ChronoField chronoField = ChronoField.MONTH_OF_YEAR;
                return temporal.with(chronoField, temporal.getLong(chronoField) + ((j - from) * 3));
            }

            @Override // j$.time.temporal.TemporalField
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return (temporalAccessor.getLong(ChronoField.MONTH_OF_YEAR) + 2) / 3;
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
            }

            @Override // j$.time.temporal.TemporalField
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.MONTH_OF_YEAR) && IsoFields.isIso(temporalAccessor);
            }

            @Override // j$.time.temporal.TemporalField
            public ValueRange range() {
                return ValueRange.of(1L, 4L);
            }

            @Override // j$.time.temporal.IsoFields.Field, j$.time.temporal.TemporalField
            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return super.rangeRefinedBy(temporalAccessor);
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: QuarterOfYear");
            }

            @Override // java.lang.Enum
            public String toString() {
                return "QuarterOfYear";
            }
        }

        /* renamed from: j$.time.temporal.IsoFields$Field$3  reason: invalid class name */
        /* loaded from: classes2.dex */
        enum AnonymousClass3 extends Field {
            private AnonymousClass3(String str, int i) {
                super(str, i);
            }

            @Override // j$.time.temporal.TemporalField
            public Temporal adjustInto(Temporal temporal, long j) {
                range().checkValidValue(j, this);
                return temporal.plus(Instant$$ExternalSyntheticBackport0.m(j, getFrom(temporal)), ChronoUnit.WEEKS);
            }

            @Override // j$.time.temporal.TemporalField
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return Field.getWeek(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // j$.time.temporal.TemporalField
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && IsoFields.isIso(temporalAccessor);
            }

            @Override // j$.time.temporal.TemporalField
            public ValueRange range() {
                return ValueRange.of(1L, 52L, 53L);
            }

            @Override // j$.time.temporal.IsoFields.Field, j$.time.temporal.TemporalField
            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return Field.getWeekRange(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // java.lang.Enum
            public String toString() {
                return "WeekOfWeekBasedYear";
            }
        }

        /* renamed from: j$.time.temporal.IsoFields$Field$4  reason: invalid class name */
        /* loaded from: classes2.dex */
        enum AnonymousClass4 extends Field {
            private AnonymousClass4(String str, int i) {
                super(str, i);
            }

            @Override // j$.time.temporal.TemporalField
            public Temporal adjustInto(Temporal temporal, long j) {
                if (isSupportedBy(temporal)) {
                    int checkValidIntValue = range().checkValidIntValue(j, Field.WEEK_BASED_YEAR);
                    LocalDate from = LocalDate.from(temporal);
                    ChronoField chronoField = ChronoField.DAY_OF_WEEK;
                    int i = from.get(chronoField);
                    int week = Field.getWeek(from);
                    if (week == 53 && Field.getWeekRange(checkValidIntValue) == 52) {
                        week = 52;
                    }
                    LocalDate of = LocalDate.of(checkValidIntValue, 1, 4);
                    return temporal.with(of.plusDays((i - of.get(chronoField)) + ((week - 1) * 7)));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
            }

            @Override // j$.time.temporal.TemporalField
            public long getFrom(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return Field.getWeekBasedYear(LocalDate.from(temporalAccessor));
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
            }

            @Override // j$.time.temporal.TemporalField
            public boolean isSupportedBy(TemporalAccessor temporalAccessor) {
                return temporalAccessor.isSupported(ChronoField.EPOCH_DAY) && IsoFields.isIso(temporalAccessor);
            }

            @Override // j$.time.temporal.TemporalField
            public ValueRange range() {
                return ChronoField.YEAR.range();
            }

            @Override // j$.time.temporal.IsoFields.Field, j$.time.temporal.TemporalField
            public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
                if (isSupportedBy(temporalAccessor)) {
                    return super.rangeRefinedBy(temporalAccessor);
                }
                throw new UnsupportedTemporalTypeException("Unsupported field: WeekBasedYear");
            }

            @Override // java.lang.Enum
            public String toString() {
                return "WeekBasedYear";
            }
        }

        static {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1("DAY_OF_QUARTER", 0);
            DAY_OF_QUARTER = anonymousClass1;
            AnonymousClass2 anonymousClass2 = new AnonymousClass2("QUARTER_OF_YEAR", 1);
            QUARTER_OF_YEAR = anonymousClass2;
            AnonymousClass3 anonymousClass3 = new AnonymousClass3("WEEK_OF_WEEK_BASED_YEAR", 2);
            WEEK_OF_WEEK_BASED_YEAR = anonymousClass3;
            AnonymousClass4 anonymousClass4 = new AnonymousClass4("WEEK_BASED_YEAR", 3);
            WEEK_BASED_YEAR = anonymousClass4;
            $VALUES = new Field[]{anonymousClass1, anonymousClass2, anonymousClass3, anonymousClass4};
            QUARTER_DAYS = new int[]{0, 90, 181, 273, 0, 91, 182, 274};
        }

        private Field(String str, int i) {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getWeek(LocalDate localDate) {
            int ordinal = localDate.getDayOfWeek().ordinal();
            int dayOfYear = localDate.getDayOfYear() - 1;
            int i = (3 - ordinal) + dayOfYear;
            int i2 = (i - ((i / 7) * 7)) - 3;
            if (i2 < -3) {
                i2 += 7;
            }
            if (dayOfYear < i2) {
                return (int) getWeekRange(localDate.withDayOfYear(180).minusYears(1L)).getMaximum();
            }
            int i3 = ((dayOfYear - i2) / 7) + 1;
            if (i3 == 53) {
                if (!(i2 == -3 || (i2 == -2 && localDate.isLeapYear()))) {
                    return 1;
                }
            }
            return i3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getWeekBasedYear(LocalDate localDate) {
            int year = localDate.getYear();
            int dayOfYear = localDate.getDayOfYear();
            if (dayOfYear <= 3) {
                return dayOfYear - localDate.getDayOfWeek().ordinal() < -2 ? year - 1 : year;
            } else if (dayOfYear >= 363) {
                return ((dayOfYear - 363) - (localDate.isLeapYear() ? 1 : 0)) - localDate.getDayOfWeek().ordinal() >= 0 ? year + 1 : year;
            } else {
                return year;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int getWeekRange(int i) {
            LocalDate of = LocalDate.of(i, 1, 1);
            if (of.getDayOfWeek() != DayOfWeek.THURSDAY) {
                return (of.getDayOfWeek() == DayOfWeek.WEDNESDAY && of.isLeapYear()) ? 53 : 52;
            }
            return 53;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ValueRange getWeekRange(LocalDate localDate) {
            return ValueRange.of(1L, getWeekRange(getWeekBasedYear(localDate)));
        }

        public static Field valueOf(String str) {
            return (Field) Enum.valueOf(Field.class, str);
        }

        public static Field[] values() {
            return (Field[]) $VALUES.clone();
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isDateBased() {
            return true;
        }

        @Override // j$.time.temporal.TemporalField
        public boolean isTimeBased() {
            return false;
        }

        @Override // j$.time.temporal.TemporalField
        public ValueRange rangeRefinedBy(TemporalAccessor temporalAccessor) {
            return range();
        }
    }

    /* loaded from: classes2.dex */
    private enum Unit implements TemporalUnit {
        WEEK_BASED_YEARS("WeekBasedYears", Duration.ofSeconds(31556952)),
        QUARTER_YEARS("QuarterYears", Duration.ofSeconds(7889238));
        
        private final Duration duration;
        private final String name;

        Unit(String str, Duration duration) {
            this.name = str;
            this.duration = duration;
        }

        @Override // j$.time.temporal.TemporalUnit
        public Temporal addTo(Temporal temporal, long j) {
            int i = AnonymousClass1.$SwitchMap$java$time$temporal$IsoFields$Unit[ordinal()];
            if (i == 1) {
                TemporalField temporalField = IsoFields.WEEK_BASED_YEAR;
                return temporal.with(temporalField, Clock$OffsetClock$$ExternalSyntheticBackport0.m(temporal.get(temporalField), j));
            } else if (i == 2) {
                return temporal.plus(j / 4, ChronoUnit.YEARS).plus((j % 4) * 3, ChronoUnit.MONTHS);
            } else {
                throw new IllegalStateException("Unreachable");
            }
        }

        @Override // j$.time.temporal.TemporalUnit
        public boolean isDateBased() {
            return true;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.name;
        }
    }

    static boolean isIso(TemporalAccessor temporalAccessor) {
        return Chronology.CC.from(temporalAccessor).equals(IsoChronology.INSTANCE);
    }
}
