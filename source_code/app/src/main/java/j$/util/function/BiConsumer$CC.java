package j$.util.function;

import j$.util.Objects;
import java.util.function.BiConsumer;
/* renamed from: j$.util.function.BiConsumer$-CC  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class BiConsumer$CC {
    public static BiConsumer $default$andThen(final BiConsumer biConsumer, final BiConsumer biConsumer2) {
        Objects.requireNonNull(biConsumer2);
        return new BiConsumer() { // from class: j$.util.function.BiConsumer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                BiConsumer$CC.$private$lambda$andThen$0(BiConsumer.this, biConsumer2, obj, obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer3) {
                return BiConsumer$CC.$default$andThen(this, biConsumer3);
            }
        };
    }

    public static /* synthetic */ void $private$lambda$andThen$0(BiConsumer biConsumer, BiConsumer biConsumer2, Object obj, Object obj2) {
        biConsumer.accept(obj, obj2);
        biConsumer2.accept(obj, obj2);
    }
}
