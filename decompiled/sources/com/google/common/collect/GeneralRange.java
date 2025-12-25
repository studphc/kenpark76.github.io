package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Comparator;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class GeneralRange<T> implements Serializable {
    private final Comparator<? super T> comparator;
    private final boolean hasLowerBound;
    private final boolean hasUpperBound;
    private final BoundType lowerBoundType;
    @CheckForNull
    private final T lowerEndpoint;
    @CheckForNull
    @LazyInit
    private transient GeneralRange<T> reverse;
    private final BoundType upperBoundType;
    @CheckForNull
    private final T upperEndpoint;

    static <T extends Comparable> GeneralRange<T> from(Range<T> range) {
        return new GeneralRange<>(Ordering.natural(), range.hasLowerBound(), range.hasLowerBound() ? range.lowerEndpoint() : null, range.hasLowerBound() ? range.lowerBoundType() : BoundType.OPEN, range.hasUpperBound(), range.hasUpperBound() ? range.upperEndpoint() : null, range.hasUpperBound() ? range.upperBoundType() : BoundType.OPEN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> GeneralRange<T> all(Comparator<? super T> comparator) {
        return new GeneralRange<>(comparator, false, null, BoundType.OPEN, false, null, BoundType.OPEN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> GeneralRange<T> downTo(Comparator<? super T> comparator, @ParametricNullness T endpoint, BoundType boundType) {
        return new GeneralRange<>(comparator, true, endpoint, boundType, false, null, BoundType.OPEN);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static <T> GeneralRange<T> upTo(Comparator<? super T> comparator, @ParametricNullness T endpoint, BoundType boundType) {
        return new GeneralRange<>(comparator, false, null, BoundType.OPEN, true, endpoint, boundType);
    }

    static <T> GeneralRange<T> range(Comparator<? super T> comparator, @ParametricNullness T lower, BoundType lowerType, @ParametricNullness T upper, BoundType upperType) {
        return new GeneralRange<>(comparator, true, lower, lowerType, true, upper, upperType);
    }

    private GeneralRange(Comparator<? super T> comparator, boolean hasLowerBound, @CheckForNull T lowerEndpoint, BoundType lowerBoundType, boolean hasUpperBound, @CheckForNull T upperEndpoint, BoundType upperBoundType) {
        this.comparator = (Comparator) Preconditions.checkNotNull(comparator);
        this.hasLowerBound = hasLowerBound;
        this.hasUpperBound = hasUpperBound;
        this.lowerEndpoint = lowerEndpoint;
        this.lowerBoundType = (BoundType) Preconditions.checkNotNull(lowerBoundType);
        this.upperEndpoint = upperEndpoint;
        this.upperBoundType = (BoundType) Preconditions.checkNotNull(upperBoundType);
        if (hasLowerBound) {
            comparator.compare((Object) NullnessCasts.uncheckedCastNullableTToT(lowerEndpoint), (Object) NullnessCasts.uncheckedCastNullableTToT(lowerEndpoint));
        }
        if (hasUpperBound) {
            comparator.compare((Object) NullnessCasts.uncheckedCastNullableTToT(upperEndpoint), (Object) NullnessCasts.uncheckedCastNullableTToT(upperEndpoint));
        }
        if (hasLowerBound && hasUpperBound) {
            int compare = comparator.compare((Object) NullnessCasts.uncheckedCastNullableTToT(lowerEndpoint), (Object) NullnessCasts.uncheckedCastNullableTToT(upperEndpoint));
            boolean z = true;
            Preconditions.checkArgument(compare <= 0, "lowerEndpoint (%s) > upperEndpoint (%s)", lowerEndpoint, upperEndpoint);
            if (compare == 0) {
                if (lowerBoundType == BoundType.OPEN && upperBoundType == BoundType.OPEN) {
                    z = false;
                }
                Preconditions.checkArgument(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Comparator<? super T> comparator() {
        return this.comparator;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasLowerBound() {
        return this.hasLowerBound;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean hasUpperBound() {
        return this.hasUpperBound;
    }

    /* JADX WARN: Multi-variable type inference failed */
    boolean isEmpty() {
        return (hasUpperBound() && tooLow(NullnessCasts.uncheckedCastNullableTToT(getUpperEndpoint()))) || (hasLowerBound() && tooHigh(NullnessCasts.uncheckedCastNullableTToT(getLowerEndpoint())));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean tooLow(@ParametricNullness T t) {
        if (hasLowerBound()) {
            int compare = this.comparator.compare(t, NullnessCasts.uncheckedCastNullableTToT(getLowerEndpoint()));
            return ((compare == 0) & (getLowerBoundType() == BoundType.OPEN)) | (compare < 0);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean tooHigh(@ParametricNullness T t) {
        if (hasUpperBound()) {
            int compare = this.comparator.compare(t, NullnessCasts.uncheckedCastNullableTToT(getUpperEndpoint()));
            return ((compare == 0) & (getUpperBoundType() == BoundType.OPEN)) | (compare > 0);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean contains(@ParametricNullness T t) {
        return (tooLow(t) || tooHigh(t)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public GeneralRange<T> intersect(GeneralRange<T> other) {
        int compare;
        int compare2;
        T t;
        BoundType boundType;
        BoundType boundType2;
        int compare3;
        Preconditions.checkNotNull(other);
        Preconditions.checkArgument(this.comparator.equals(other.comparator));
        boolean z = this.hasLowerBound;
        T lowerEndpoint = getLowerEndpoint();
        BoundType lowerBoundType = getLowerBoundType();
        if (!hasLowerBound()) {
            z = other.hasLowerBound;
            lowerEndpoint = other.getLowerEndpoint();
            lowerBoundType = other.getLowerBoundType();
        } else if (other.hasLowerBound() && ((compare = this.comparator.compare(getLowerEndpoint(), other.getLowerEndpoint())) < 0 || (compare == 0 && other.getLowerBoundType() == BoundType.OPEN))) {
            lowerEndpoint = other.getLowerEndpoint();
            lowerBoundType = other.getLowerBoundType();
        }
        boolean z2 = z;
        boolean z3 = this.hasUpperBound;
        T upperEndpoint = getUpperEndpoint();
        BoundType upperBoundType = getUpperBoundType();
        if (!hasUpperBound()) {
            z3 = other.hasUpperBound;
            upperEndpoint = other.getUpperEndpoint();
            upperBoundType = other.getUpperBoundType();
        } else if (other.hasUpperBound() && ((compare2 = this.comparator.compare(getUpperEndpoint(), other.getUpperEndpoint())) > 0 || (compare2 == 0 && other.getUpperBoundType() == BoundType.OPEN))) {
            upperEndpoint = other.getUpperEndpoint();
            upperBoundType = other.getUpperBoundType();
        }
        boolean z4 = z3;
        T t2 = upperEndpoint;
        if (z2 && z4 && ((compare3 = this.comparator.compare(lowerEndpoint, t2)) > 0 || (compare3 == 0 && lowerBoundType == BoundType.OPEN && upperBoundType == BoundType.OPEN))) {
            boundType = BoundType.OPEN;
            boundType2 = BoundType.CLOSED;
            t = t2;
        } else {
            t = lowerEndpoint;
            boundType = lowerBoundType;
            boundType2 = upperBoundType;
        }
        return new GeneralRange<>(this.comparator, z2, t, boundType, z4, t2, boundType2);
    }

    public boolean equals(@CheckForNull Object obj) {
        if (obj instanceof GeneralRange) {
            GeneralRange generalRange = (GeneralRange) obj;
            return this.comparator.equals(generalRange.comparator) && this.hasLowerBound == generalRange.hasLowerBound && this.hasUpperBound == generalRange.hasUpperBound && getLowerBoundType().equals(generalRange.getLowerBoundType()) && getUpperBoundType().equals(generalRange.getUpperBoundType()) && Objects.equal(getLowerEndpoint(), generalRange.getLowerEndpoint()) && Objects.equal(getUpperEndpoint(), generalRange.getUpperEndpoint());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.comparator, getLowerEndpoint(), getLowerBoundType(), getUpperEndpoint(), getUpperBoundType());
    }

    GeneralRange<T> reverse() {
        GeneralRange<T> generalRange = this.reverse;
        if (generalRange == null) {
            GeneralRange<T> generalRange2 = new GeneralRange<>(Ordering.from(this.comparator).reverse(), this.hasUpperBound, getUpperEndpoint(), getUpperBoundType(), this.hasLowerBound, getLowerEndpoint(), getLowerBoundType());
            generalRange2.reverse = this;
            this.reverse = generalRange2;
            return generalRange2;
        }
        return generalRange;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.comparator);
        sb.append(":");
        sb.append(this.lowerBoundType == BoundType.CLOSED ? '[' : '(');
        sb.append(this.hasLowerBound ? this.lowerEndpoint : "-∞");
        sb.append(',');
        sb.append(this.hasUpperBound ? this.upperEndpoint : "∞");
        sb.append(this.upperBoundType == BoundType.CLOSED ? ']' : ')');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public T getLowerEndpoint() {
        return this.lowerEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BoundType getLowerBoundType() {
        return this.lowerBoundType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @CheckForNull
    public T getUpperEndpoint() {
        return this.upperEndpoint;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BoundType getUpperBoundType() {
        return this.upperBoundType;
    }
}
