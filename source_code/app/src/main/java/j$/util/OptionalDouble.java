package j$.util;

import java.util.NoSuchElementException;
/* loaded from: classes2.dex */
public final class OptionalDouble {
    private static final OptionalDouble EMPTY = new OptionalDouble();
    private final boolean isPresent;
    private final double value;

    private OptionalDouble() {
        this.isPresent = false;
        this.value = Double.NaN;
    }

    private OptionalDouble(double d) {
        this.isPresent = true;
        this.value = d;
    }

    public static OptionalDouble empty() {
        return EMPTY;
    }

    public static OptionalDouble of(double d) {
        return new OptionalDouble(d);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OptionalDouble) {
            OptionalDouble optionalDouble = (OptionalDouble) obj;
            boolean z = this.isPresent;
            if (z && optionalDouble.isPresent) {
                if (Double.compare(this.value, optionalDouble.value) == 0) {
                    return true;
                }
            } else if (z == optionalDouble.isPresent) {
                return true;
            }
            return false;
        }
        return false;
    }

    public double getAsDouble() {
        if (this.isPresent) {
            return this.value;
        }
        throw new NoSuchElementException("No value present");
    }

    public int hashCode() {
        if (this.isPresent) {
            return OptionalDouble$$ExternalSyntheticBackport0.m(this.value);
        }
        return 0;
    }

    public boolean isPresent() {
        return this.isPresent;
    }

    public String toString() {
        return this.isPresent ? String.format("OptionalDouble[%s]", Double.valueOf(this.value)) : "OptionalDouble.empty";
    }
}
