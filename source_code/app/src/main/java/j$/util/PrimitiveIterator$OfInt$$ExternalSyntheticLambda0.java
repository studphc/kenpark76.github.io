package j$.util;

import j$.util.function.IntConsumer$CC;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
/* loaded from: classes2.dex */
public final /* synthetic */ class PrimitiveIterator$OfInt$$ExternalSyntheticLambda0 implements IntConsumer {
    public final /* synthetic */ Consumer f$0;

    public /* synthetic */ PrimitiveIterator$OfInt$$ExternalSyntheticLambda0(Consumer consumer) {
        this.f$0 = consumer;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f$0.accept(Integer.valueOf(i));
    }

    public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }
}
