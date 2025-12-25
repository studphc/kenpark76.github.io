package j$.util.function;

import j$.util.Objects;
import java.util.function.DoubleConsumer;
/* renamed from: j$.util.function.DoubleConsumer$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class DoubleConsumer$CC {
    public static DoubleConsumer $default$andThen(final DoubleConsumer doubleConsumer, final DoubleConsumer doubleConsumer2) {
        Objects.requireNonNull(doubleConsumer2);
        return new DoubleConsumer() { // from class: j$.util.function.DoubleConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleConsumer
            public final void accept(double d) {
                DoubleConsumer$CC.$private$lambda$andThen$0(DoubleConsumer.this, doubleConsumer2, d);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer3) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer3);
            }
        };
    }

    public static /* synthetic */ void $private$lambda$andThen$0(DoubleConsumer doubleConsumer, DoubleConsumer doubleConsumer2, double d) {
        doubleConsumer.accept(d);
        doubleConsumer2.accept(d);
    }
}
