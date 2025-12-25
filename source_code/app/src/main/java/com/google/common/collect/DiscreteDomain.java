package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.lang.Comparable;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class DiscreteDomain<C extends Comparable> {
    final boolean supportsFastOffset;

    public abstract long distance(C start, C end);

    @CheckForNull
    public abstract C next(C value);

    @CheckForNull
    public abstract C previous(C value);

    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.INSTANCE;
    }

    /* loaded from: classes3.dex */
    private static final class IntegerDomain extends DiscreteDomain<Integer> implements Serializable {
        private static final IntegerDomain INSTANCE = new IntegerDomain();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.integers()";
        }

        IntegerDomain() {
            super(true);
        }

        @Override // com.google.common.collect.DiscreteDomain
        @CheckForNull
        public Integer next(Integer value) {
            int intValue = value.intValue();
            if (intValue == Integer.MAX_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue + 1);
        }

        @Override // com.google.common.collect.DiscreteDomain
        @CheckForNull
        public Integer previous(Integer value) {
            int intValue = value.intValue();
            if (intValue == Integer.MIN_VALUE) {
                return null;
            }
            return Integer.valueOf(intValue - 1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.DiscreteDomain
        public Integer offset(Integer origin, long distance) {
            CollectPreconditions.checkNonnegative(distance, "distance");
            return Integer.valueOf(Ints.checkedCast(origin.longValue() + distance));
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(Integer start, Integer end) {
            return end.intValue() - start.intValue();
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.INSTANCE;
    }

    /* loaded from: classes3.dex */
    private static final class LongDomain extends DiscreteDomain<Long> implements Serializable {
        private static final LongDomain INSTANCE = new LongDomain();
        private static final long serialVersionUID = 0;

        public String toString() {
            return "DiscreteDomain.longs()";
        }

        LongDomain() {
            super(true);
        }

        @Override // com.google.common.collect.DiscreteDomain
        @CheckForNull
        public Long next(Long value) {
            long longValue = value.longValue();
            if (longValue == Long.MAX_VALUE) {
                return null;
            }
            return Long.valueOf(longValue + 1);
        }

        @Override // com.google.common.collect.DiscreteDomain
        @CheckForNull
        public Long previous(Long value) {
            long longValue = value.longValue();
            if (longValue == Long.MIN_VALUE) {
                return null;
            }
            return Long.valueOf(longValue - 1);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.DiscreteDomain
        public Long offset(Long origin, long distance) {
            CollectPreconditions.checkNonnegative(distance, "distance");
            long longValue = origin.longValue() + distance;
            if (longValue < 0) {
                Preconditions.checkArgument(origin.longValue() < 0, "overflow");
            }
            return Long.valueOf(longValue);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(Long start, Long end) {
            long longValue = end.longValue() - start.longValue();
            if (end.longValue() <= start.longValue() || longValue >= 0) {
                if (end.longValue() >= start.longValue() || longValue <= 0) {
                    return longValue;
                }
                return Long.MIN_VALUE;
            }
            return Long.MAX_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        @Override // com.google.common.collect.DiscreteDomain
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.INSTANCE;
    }

    /* loaded from: classes3.dex */
    private static final class BigIntegerDomain extends DiscreteDomain<BigInteger> implements Serializable {
        private static final long serialVersionUID = 0;
        private static final BigIntegerDomain INSTANCE = new BigIntegerDomain();
        private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
        private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }

        BigIntegerDomain() {
            super(true);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger next(BigInteger value) {
            return value.add(BigInteger.ONE);
        }

        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger previous(BigInteger value) {
            return value.subtract(BigInteger.ONE);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.google.common.collect.DiscreteDomain
        public BigInteger offset(BigInteger origin, long distance) {
            CollectPreconditions.checkNonnegative(distance, "distance");
            return origin.add(BigInteger.valueOf(distance));
        }

        @Override // com.google.common.collect.DiscreteDomain
        public long distance(BigInteger start, BigInteger end) {
            return end.subtract(start).max(MIN_LONG).min(MAX_LONG).longValue();
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    protected DiscreteDomain() {
        this(false);
    }

    private DiscreteDomain(boolean supportsFastOffset) {
        this.supportsFastOffset = supportsFastOffset;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public C offset(C origin, long distance) {
        CollectPreconditions.checkNonnegative(distance, "distance");
        C c = origin;
        for (long j = 0; j < distance; j++) {
            c = next(c);
            if (c == null) {
                throw new IllegalArgumentException("overflowed computing offset(" + origin + ", " + distance + ")");
            }
        }
        return c;
    }

    public C minValue() {
        throw new NoSuchElementException();
    }

    public C maxValue() {
        throw new NoSuchElementException();
    }
}
