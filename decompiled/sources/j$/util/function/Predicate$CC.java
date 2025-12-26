package j$.util.function;

import j$.util.Objects;
import java.util.function.Predicate;
/* renamed from: j$.util.function.Predicate$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Predicate$CC {
    public static Predicate $default$and(final Predicate predicate, final Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new Predicate() { // from class: j$.util.function.Predicate$$ExternalSyntheticLambda2
            public /* synthetic */ Predicate and(Predicate predicate3) {
                return Predicate$CC.$default$and(this, predicate3);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            public /* synthetic */ Predicate or(Predicate predicate3) {
                return Predicate$CC.$default$or(this, predicate3);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Predicate$CC.$private$lambda$and$0(Predicate.this, predicate2, obj);
            }
        };
    }

    public static Predicate $default$negate(final Predicate predicate) {
        return new Predicate() { // from class: j$.util.function.Predicate$$ExternalSyntheticLambda3
            public /* synthetic */ Predicate and(Predicate predicate2) {
                return Predicate$CC.$default$and(this, predicate2);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            public /* synthetic */ Predicate or(Predicate predicate2) {
                return Predicate$CC.$default$or(this, predicate2);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Predicate$CC.$private$lambda$negate$1(Predicate.this, obj);
            }
        };
    }

    public static Predicate $default$or(final Predicate predicate, final Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new Predicate() { // from class: j$.util.function.Predicate$$ExternalSyntheticLambda4
            public /* synthetic */ Predicate and(Predicate predicate3) {
                return Predicate$CC.$default$and(this, predicate3);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            public /* synthetic */ Predicate or(Predicate predicate3) {
                return Predicate$CC.$default$or(this, predicate3);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Predicate$CC.$private$lambda$or$2(Predicate.this, predicate2, obj);
            }
        };
    }

    public static /* synthetic */ boolean $private$lambda$and$0(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) && predicate2.test(obj);
    }

    public static /* synthetic */ boolean $private$lambda$negate$1(Predicate predicate, Object obj) {
        return !predicate.test(obj);
    }

    public static /* synthetic */ boolean $private$lambda$or$2(Predicate predicate, Predicate predicate2, Object obj) {
        return predicate.test(obj) || predicate2.test(obj);
    }
}
