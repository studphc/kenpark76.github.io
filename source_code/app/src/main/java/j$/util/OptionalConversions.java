package j$.util;
/* loaded from: classes2.dex */
public abstract class OptionalConversions {
    public static Optional convert(java.util.Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? Optional.of(optional.get()) : Optional.empty();
    }

    public static OptionalDouble convert(java.util.OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        return optionalDouble.isPresent() ? OptionalDouble.of(optionalDouble.getAsDouble()) : OptionalDouble.empty();
    }

    public static OptionalInt convert(java.util.OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        return optionalInt.isPresent() ? OptionalInt.of(optionalInt.getAsInt()) : OptionalInt.empty();
    }

    public static OptionalLong convert(java.util.OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        return optionalLong.isPresent() ? OptionalLong.of(optionalLong.getAsLong()) : OptionalLong.empty();
    }

    public static java.util.Optional convert(Optional optional) {
        if (optional == null) {
            return null;
        }
        return optional.isPresent() ? java.util.Optional.of(optional.get()) : java.util.Optional.empty();
    }

    public static java.util.OptionalDouble convert(OptionalDouble optionalDouble) {
        if (optionalDouble == null) {
            return null;
        }
        return optionalDouble.isPresent() ? java.util.OptionalDouble.of(optionalDouble.getAsDouble()) : java.util.OptionalDouble.empty();
    }

    public static java.util.OptionalInt convert(OptionalInt optionalInt) {
        if (optionalInt == null) {
            return null;
        }
        return optionalInt.isPresent() ? java.util.OptionalInt.of(optionalInt.getAsInt()) : java.util.OptionalInt.empty();
    }

    public static java.util.OptionalLong convert(OptionalLong optionalLong) {
        if (optionalLong == null) {
            return null;
        }
        return optionalLong.isPresent() ? java.util.OptionalLong.of(optionalLong.getAsLong()) : java.util.OptionalLong.empty();
    }
}
