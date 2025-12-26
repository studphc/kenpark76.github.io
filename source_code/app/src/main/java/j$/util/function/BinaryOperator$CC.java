package j$.util.function;

import j$.util.Objects;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
/* renamed from: j$.util.function.BinaryOperator$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class BinaryOperator$CC {
    public static /* synthetic */ Object lambda$maxBy$1(Comparator comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2) >= 0 ? obj : obj2;
    }

    public static /* synthetic */ Object lambda$minBy$0(Comparator comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2) <= 0 ? obj : obj2;
    }

    public static BinaryOperator maxBy(final Comparator comparator) {
        Objects.requireNonNull(comparator);
        return new BinaryOperator() { // from class: j$.util.function.BinaryOperator$$ExternalSyntheticLambda0
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BinaryOperator$CC.lambda$maxBy$1(comparator, obj, obj2);
            }
        };
    }

    public static BinaryOperator minBy(final Comparator comparator) {
        Objects.requireNonNull(comparator);
        return new BinaryOperator() { // from class: j$.util.function.BinaryOperator$$ExternalSyntheticLambda1
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return BinaryOperator$CC.lambda$minBy$0(comparator, obj, obj2);
            }
        };
    }
}
