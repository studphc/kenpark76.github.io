package j$.util.function;

import j$.util.Objects;
import java.util.function.Consumer;
/* renamed from: j$.util.function.Consumer$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Consumer$CC {
    public static Consumer $default$andThen(final Consumer consumer, final Consumer consumer2) {
        Objects.requireNonNull(consumer2);
        return new Consumer() { // from class: j$.util.function.Consumer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Consumer$CC.$private$lambda$andThen$0(Consumer.this, consumer2, obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer3) {
                return Consumer$CC.$default$andThen(this, consumer3);
            }
        };
    }

    public static /* synthetic */ void $private$lambda$andThen$0(Consumer consumer, Consumer consumer2, Object obj) {
        consumer.accept(obj);
        consumer2.accept(obj);
    }
}
