package j$.util.function;

import j$.util.Objects;
import java.util.function.LongConsumer;
/* renamed from: j$.util.function.LongConsumer$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class LongConsumer$CC {
    public static LongConsumer $default$andThen(final LongConsumer longConsumer, final LongConsumer longConsumer2) {
        Objects.requireNonNull(longConsumer2);
        return new LongConsumer() { // from class: j$.util.function.LongConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.LongConsumer
            public final void accept(long j) {
                LongConsumer$CC.$private$lambda$andThen$0(LongConsumer.this, longConsumer2, j);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer3) {
                return LongConsumer$CC.$default$andThen(this, longConsumer3);
            }
        };
    }

    public static /* synthetic */ void $private$lambda$andThen$0(LongConsumer longConsumer, LongConsumer longConsumer2, long j) {
        longConsumer.accept(j);
        longConsumer2.accept(j);
    }
}
