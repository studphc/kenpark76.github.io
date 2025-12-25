package j$.util.stream;

import j$.util.function.DoubleConsumer$CC;
import java.util.function.DoubleConsumer;
/* loaded from: classes2.dex */
public final /* synthetic */ class DoublePipeline$$ExternalSyntheticLambda5 implements DoubleConsumer {
    public final /* synthetic */ Sink f$0;

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f$0.accept(d);
    }

    public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
    }
}
