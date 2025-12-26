package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Booleans;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Iterator;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class DoubleMath {
    static final int MAX_FACTORIAL = 170;
    private static final double MAX_INT_AS_DOUBLE = 2.147483647E9d;
    private static final double MAX_LONG_AS_DOUBLE_PLUS_ONE = 9.223372036854776E18d;
    private static final double MIN_INT_AS_DOUBLE = -2.147483648E9d;
    private static final double MIN_LONG_AS_DOUBLE = -9.223372036854776E18d;
    private static final double LN_2 = Math.log(2.0d);
    static final double[] everySixteenthFactorial = {1.0d, 2.0922789888E13d, 2.631308369336935E35d, 1.2413915592536073E61d, 1.2688693218588417E89d, 7.156945704626381E118d, 9.916779348709496E149d, 1.974506857221074E182d, 3.856204823625804E215d, 5.5502938327393044E249d, 4.7147236359920616E284d};

    static double roundIntermediate(double x, RoundingMode mode) {
        if (!DoubleUtils.isFinite(x)) {
            throw new ArithmeticException("input is infinite or NaN");
        }
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isMathematicalInteger(x));
                return x;
            case 2:
                return (x >= LN_2 || isMathematicalInteger(x)) ? x : ((long) x) - 1;
            case 3:
                return (x <= LN_2 || isMathematicalInteger(x)) ? x : ((long) x) + 1;
            case 4:
                return x;
            case 5:
                if (isMathematicalInteger(x)) {
                    return x;
                }
                return ((long) x) + (x > LN_2 ? 1 : -1);
            case 6:
                return Math.rint(x);
            case 7:
                double rint = Math.rint(x);
                return Math.abs(x - rint) == 0.5d ? x + Math.copySign(0.5d, x) : rint;
            case 8:
                double rint2 = Math.rint(x);
                return Math.abs(x - rint2) == 0.5d ? x : rint2;
            default:
                throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.math.DoubleMath$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$java$math$RoundingMode;

        static {
            int[] iArr = new int[RoundingMode.values().length];
            $SwitchMap$java$math$RoundingMode = iArr;
            try {
                iArr[RoundingMode.UNNECESSARY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.FLOOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.CEILING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.UP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_EVEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_UP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$java$math$RoundingMode[RoundingMode.HALF_DOWN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public static int roundToInt(double x, RoundingMode mode) {
        double roundIntermediate = roundIntermediate(x, mode);
        MathPreconditions.checkInRangeForRoundingInputs((roundIntermediate > -2.147483649E9d) & (roundIntermediate < 2.147483648E9d), x, mode);
        return (int) roundIntermediate;
    }

    public static long roundToLong(double x, RoundingMode mode) {
        double roundIntermediate = roundIntermediate(x, mode);
        MathPreconditions.checkInRangeForRoundingInputs((MIN_LONG_AS_DOUBLE - roundIntermediate < 1.0d) & (roundIntermediate < MAX_LONG_AS_DOUBLE_PLUS_ONE), x, mode);
        return (long) roundIntermediate;
    }

    public static BigInteger roundToBigInteger(double x, RoundingMode mode) {
        double roundIntermediate = roundIntermediate(x, mode);
        if ((roundIntermediate < MAX_LONG_AS_DOUBLE_PLUS_ONE) & (MIN_LONG_AS_DOUBLE - roundIntermediate < 1.0d)) {
            return BigInteger.valueOf((long) roundIntermediate);
        }
        BigInteger shiftLeft = BigInteger.valueOf(DoubleUtils.getSignificand(roundIntermediate)).shiftLeft(Math.getExponent(roundIntermediate) - 52);
        return roundIntermediate < LN_2 ? shiftLeft.negate() : shiftLeft;
    }

    public static boolean isPowerOfTwo(double x) {
        if (x <= LN_2 || !DoubleUtils.isFinite(x)) {
            return false;
        }
        long significand = DoubleUtils.getSignificand(x);
        return (significand & (significand - 1)) == 0;
    }

    public static double log2(double x) {
        return Math.log(x) / LN_2;
    }

    public static int log2(double x, RoundingMode mode) {
        boolean isPowerOfTwo;
        Preconditions.checkArgument(x > LN_2 && DoubleUtils.isFinite(x), "x must be positive and finite");
        int exponent = Math.getExponent(x);
        if (!DoubleUtils.isNormal(x)) {
            return log2(x * 4.503599627370496E15d, mode) - 52;
        }
        switch (AnonymousClass1.$SwitchMap$java$math$RoundingMode[mode.ordinal()]) {
            case 1:
                MathPreconditions.checkRoundingUnnecessary(isPowerOfTwo(x));
                break;
            case 2:
                break;
            case 3:
                r2 = !isPowerOfTwo(x);
                break;
            case 4:
                r2 = exponent < 0;
                isPowerOfTwo = isPowerOfTwo(x);
                r2 &= !isPowerOfTwo;
                break;
            case 5:
                r2 = exponent >= 0;
                isPowerOfTwo = isPowerOfTwo(x);
                r2 &= !isPowerOfTwo;
                break;
            case 6:
            case 7:
            case 8:
                double scaleNormalize = DoubleUtils.scaleNormalize(x);
                if (scaleNormalize * scaleNormalize > 2.0d) {
                    r2 = true;
                    break;
                }
                break;
            default:
                throw new AssertionError();
        }
        return r2 ? exponent + 1 : exponent;
    }

    public static boolean isMathematicalInteger(double x) {
        return DoubleUtils.isFinite(x) && (x == LN_2 || 52 - Long.numberOfTrailingZeros(DoubleUtils.getSignificand(x)) <= Math.getExponent(x));
    }

    public static double factorial(int n) {
        MathPreconditions.checkNonNegative("n", n);
        if (n > MAX_FACTORIAL) {
            return Double.POSITIVE_INFINITY;
        }
        double d = 1.0d;
        for (int i = (n & (-16)) + 1; i <= n; i++) {
            d *= i;
        }
        return d * everySixteenthFactorial[n >> 4];
    }

    public static boolean fuzzyEquals(double a, double b, double tolerance) {
        MathPreconditions.checkNonNegative("tolerance", tolerance);
        return Math.copySign(a - b, 1.0d) <= tolerance || a == b || (Double.isNaN(a) && Double.isNaN(b));
    }

    public static int fuzzyCompare(double a, double b, double tolerance) {
        if (fuzzyEquals(a, b, tolerance)) {
            return 0;
        }
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        return Booleans.compare(Double.isNaN(a), Double.isNaN(b));
    }

    @Deprecated
    public static double mean(double... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        double checkFinite = checkFinite(values[0]);
        long j = 1;
        for (int i = 1; i < values.length; i++) {
            checkFinite(values[i]);
            j++;
            checkFinite += (values[i] - checkFinite) / j;
        }
        return checkFinite;
    }

    @Deprecated
    public static double mean(int... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        long j = 0;
        for (int i : values) {
            j += i;
        }
        return j / values.length;
    }

    @Deprecated
    public static double mean(long... values) {
        Preconditions.checkArgument(values.length > 0, "Cannot take mean of 0 values");
        double d = values[0];
        long j = 1;
        for (int i = 1; i < values.length; i++) {
            j++;
            d += (values[i] - d) / j;
        }
        return d;
    }

    @Deprecated
    public static double mean(Iterable<? extends Number> values) {
        return mean(values.iterator());
    }

    @Deprecated
    public static double mean(Iterator<? extends Number> values) {
        Preconditions.checkArgument(values.hasNext(), "Cannot take mean of 0 values");
        double checkFinite = checkFinite(values.next().doubleValue());
        long j = 1;
        while (values.hasNext()) {
            j++;
            checkFinite += (checkFinite(values.next().doubleValue()) - checkFinite) / j;
        }
        return checkFinite;
    }

    private static double checkFinite(double argument) {
        Preconditions.checkArgument(DoubleUtils.isFinite(argument));
        return argument;
    }

    private DoubleMath() {
    }
}
