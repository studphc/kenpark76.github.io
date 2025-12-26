package j$.util.function;

import j$.util.Objects;
import java.util.function.Function;
/* renamed from: j$.util.function.Function$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Function$CC {
    public static Function $default$andThen(final Function function, final Function function2) {
        Objects.requireNonNull(function2);
        return new Function() { // from class: j$.util.function.Function$$ExternalSyntheticLambda0
            public /* synthetic */ Function andThen(Function function3) {
                return Function$CC.$default$andThen(this, function3);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Object apply;
                apply = function2.apply(Function.this.apply(obj));
                return apply;
            }

            public /* synthetic */ Function compose(Function function3) {
                return Function$CC.$default$compose(this, function3);
            }
        };
    }

    public static Function $default$compose(final Function function, final Function function2) {
        Objects.requireNonNull(function2);
        return new Function() { // from class: j$.util.function.Function$$ExternalSyntheticLambda2
            public /* synthetic */ Function andThen(Function function3) {
                return Function$CC.$default$andThen(this, function3);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Object apply;
                apply = Function.this.apply(function2.apply(obj));
                return apply;
            }

            public /* synthetic */ Function compose(Function function3) {
                return Function$CC.$default$compose(this, function3);
            }
        };
    }
}
