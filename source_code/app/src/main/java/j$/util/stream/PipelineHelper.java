package j$.util.stream;

import j$.util.Spliterator;
import j$.util.stream.Node;
import java.util.function.IntFunction;
/* loaded from: classes2.dex */
abstract class PipelineHelper {
    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void copyInto(Sink sink, Spliterator spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean copyIntoWithCancel(Sink sink, Spliterator spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Node evaluate(Spliterator spliterator, boolean z, IntFunction intFunction);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract long exactOutputSizeIfKnown(Spliterator spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract StreamShape getSourceShape();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getStreamAndOpFlags();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Node.Builder makeNodeBuilder(long j, IntFunction intFunction);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Sink wrapAndCopyInto(Sink sink, Spliterator spliterator);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Sink wrapSink(Sink sink);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract Spliterator wrapSpliterator(Spliterator spliterator);
}
