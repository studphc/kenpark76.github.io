package j$.util.stream;

import j$.util.Objects;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.stream.Node;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import java.util.Comparator;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
/* loaded from: classes2.dex */
abstract class WhileOps {
    static final int DROP_FLAGS;
    static final int TAKE_FLAGS;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.WhileOps$1Op  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C1Op extends ReferencePipeline.StatefulOp implements DropWhileOp {
        final /* synthetic */ Predicate val$predicate;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: j$.util.stream.WhileOps$1Op$1OpSink  reason: invalid class name */
        /* loaded from: classes2.dex */
        public class C1OpSink extends Sink.ChainedReference implements DropWhileSink {
            long dropCount;
            boolean take;
            final /* synthetic */ boolean val$retainAndCountDroppedElements;
            final /* synthetic */ Sink val$sink;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C1OpSink(Sink sink, boolean z) {
                super(sink);
                this.val$sink = sink;
                this.val$retainAndCountDroppedElements = z;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                boolean z = true;
                if (!this.take) {
                    boolean z2 = !C1Op.this.val$predicate.test(obj);
                    this.take = z2;
                    if (!z2) {
                        z = false;
                    }
                }
                boolean z3 = this.val$retainAndCountDroppedElements;
                if (z3 && !z) {
                    this.dropCount++;
                }
                if (z3 || z) {
                    this.downstream.accept((Sink) obj);
                }
            }

            @Override // j$.util.stream.WhileOps.DropWhileSink
            public long getDropCount() {
                return this.dropCount;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C1Op(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, Predicate predicate) {
            super(abstractPipeline, streamShape, i);
            this.val$predicate = predicate;
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            return (Node) new DropWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator() : new UnorderedWhileSpliterator.OfRef.Dropping(pipelineHelper.wrapSpliterator(spliterator), false, this.val$predicate);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.AbstractPipeline
        public Sink opWrapSink(int i, Sink sink) {
            return opWrapSink(sink, false);
        }

        @Override // j$.util.stream.WhileOps.DropWhileOp
        public DropWhileSink opWrapSink(Sink sink, boolean z) {
            return new C1OpSink(sink, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface DropWhileOp {
        DropWhileSink opWrapSink(Sink sink, boolean z);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface DropWhileSink extends Sink {
        long getDropCount();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class DropWhileTask extends AbstractTask {
        private final IntFunction generator;
        private long index;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private long thisNodeSize;

        DropWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        DropWhileTask(DropWhileTask dropWhileTask, Spliterator spliterator) {
            super(dropWhileTask, spliterator);
            this.op = dropWhileTask.op;
            this.generator = dropWhileTask.generator;
            this.isOrdered = dropWhileTask.isOrdered;
        }

        private Node doTruncate(Node node) {
            return this.isOrdered ? node.truncate(this.index, node.count(), this.generator) : node;
        }

        private Node merge() {
            AbstractTask abstractTask = this.leftChild;
            return ((DropWhileTask) abstractTask).thisNodeSize == 0 ? (Node) ((DropWhileTask) this.rightChild).getLocalResult() : ((DropWhileTask) this.rightChild).thisNodeSize == 0 ? (Node) ((DropWhileTask) abstractTask).getLocalResult() : Nodes.conc(this.op.getOutputShape(), (Node) ((DropWhileTask) this.leftChild).getLocalResult(), (Node) ((DropWhileTask) this.rightChild).getLocalResult());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public final Node doLeaf() {
            boolean z = true;
            boolean z2 = !isRoot();
            Node.Builder makeNodeBuilder = this.helper.makeNodeBuilder((z2 && this.isOrdered && StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags)) ? this.op.exactOutputSizeIfKnown(this.spliterator) : -1L, this.generator);
            DropWhileSink opWrapSink = ((DropWhileOp) this.op).opWrapSink(makeNodeBuilder, (this.isOrdered && z2) ? false : false);
            this.helper.wrapAndCopyInto(opWrapSink, this.spliterator);
            Node build = makeNodeBuilder.build();
            this.thisNodeSize = build.count();
            this.index = opWrapSink.getDropCount();
            return build;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public DropWhileTask makeChild(Spliterator spliterator) {
            return new DropWhileTask(this, spliterator);
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                if (this.isOrdered) {
                    AbstractTask abstractTask = this.leftChild;
                    long j = ((DropWhileTask) abstractTask).index;
                    this.index = j;
                    if (j == ((DropWhileTask) abstractTask).thisNodeSize) {
                        this.index = j + ((DropWhileTask) this.rightChild).index;
                    }
                }
                this.thisNodeSize = ((DropWhileTask) this.leftChild).thisNodeSize + ((DropWhileTask) this.rightChild).thisNodeSize;
                Node merge = merge();
                if (isRoot()) {
                    merge = doTruncate(merge);
                }
                setLocalResult(merge);
            }
            super.onCompletion(countedCompleter);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class TakeWhileTask extends AbstractShortCircuitTask {
        private volatile boolean completed;
        private final IntFunction generator;
        private final boolean isOrdered;
        private final AbstractPipeline op;
        private boolean shortCircuited;
        private long thisNodeSize;

        TakeWhileTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.isOrdered = StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags());
        }

        TakeWhileTask(TakeWhileTask takeWhileTask, Spliterator spliterator) {
            super(takeWhileTask, spliterator);
            this.op = takeWhileTask.op;
            this.generator = takeWhileTask.generator;
            this.isOrdered = takeWhileTask.isOrdered;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public void cancel() {
            super.cancel();
            if (this.isOrdered && this.completed) {
                setLocalResult(getEmptyResult());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public final Node doLeaf() {
            Node.Builder makeNodeBuilder = this.helper.makeNodeBuilder(-1L, this.generator);
            Sink opWrapSink = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder);
            PipelineHelper pipelineHelper = this.helper;
            boolean copyIntoWithCancel = pipelineHelper.copyIntoWithCancel(pipelineHelper.wrapSink(opWrapSink), this.spliterator);
            this.shortCircuited = copyIntoWithCancel;
            if (copyIntoWithCancel) {
                cancelLaterNodes();
            }
            Node build = makeNodeBuilder.build();
            this.thisNodeSize = build.count();
            return build;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public final Node getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public TakeWhileTask makeChild(Spliterator spliterator) {
            return new TakeWhileTask(this, spliterator);
        }

        Node merge() {
            AbstractTask abstractTask = this.leftChild;
            return ((TakeWhileTask) abstractTask).thisNodeSize == 0 ? (Node) ((TakeWhileTask) this.rightChild).getLocalResult() : ((TakeWhileTask) this.rightChild).thisNodeSize == 0 ? (Node) ((TakeWhileTask) abstractTask).getLocalResult() : Nodes.conc(this.op.getOutputShape(), (Node) ((TakeWhileTask) this.leftChild).getLocalResult(), (Node) ((TakeWhileTask) this.rightChild).getLocalResult());
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public final void onCompletion(CountedCompleter countedCompleter) {
            Node merge;
            if (!isLeaf()) {
                this.shortCircuited = ((TakeWhileTask) this.leftChild).shortCircuited | ((TakeWhileTask) this.rightChild).shortCircuited;
                if (this.isOrdered && this.canceled) {
                    this.thisNodeSize = 0L;
                    merge = getEmptyResult();
                } else {
                    if (this.isOrdered) {
                        AbstractTask abstractTask = this.leftChild;
                        if (((TakeWhileTask) abstractTask).shortCircuited) {
                            this.thisNodeSize = ((TakeWhileTask) abstractTask).thisNodeSize;
                            merge = (Node) ((TakeWhileTask) abstractTask).getLocalResult();
                        }
                    }
                    this.thisNodeSize = ((TakeWhileTask) this.leftChild).thisNodeSize + ((TakeWhileTask) this.rightChild).thisNodeSize;
                    merge = merge();
                }
                setLocalResult(merge);
            }
            this.completed = true;
            super.onCompletion(countedCompleter);
        }
    }

    /* loaded from: classes2.dex */
    static abstract class UnorderedWhileSpliterator implements Spliterator {
        final AtomicBoolean cancel;
        int count;
        final boolean noSplitting;
        final Spliterator s;
        boolean takeOrDrop;

        /* loaded from: classes2.dex */
        static abstract class OfRef extends UnorderedWhileSpliterator implements Consumer {
            final Predicate p;
            Object t;

            /* loaded from: classes2.dex */
            static final class Dropping extends OfRef {
                Dropping(Spliterator spliterator, Dropping dropping) {
                    super(spliterator, dropping);
                }

                Dropping(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Dropping(spliterator, this);
                }

                /* JADX WARN: Code restructure failed: missing block: B:13:0x0024, code lost:
                    if (r0 == false) goto L15;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:14:0x0026, code lost:
                    r5.cancel.set(true);
                 */
                /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
                    r6.accept(r5.t);
                 */
                @Override // j$.util.Spliterator
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public boolean tryAdvance(java.util.function.Consumer r6) {
                    /*
                        r5 = this;
                        boolean r0 = r5.takeOrDrop
                        if (r0 == 0) goto L31
                        r0 = 0
                        r5.takeOrDrop = r0
                    L7:
                        j$.util.Spliterator r1 = r5.s
                        boolean r1 = r1.tryAdvance(r5)
                        r2 = 1
                        if (r1 == 0) goto L22
                        boolean r3 = r5.checkCancelOnCount()
                        if (r3 == 0) goto L22
                        java.util.function.Predicate r3 = r5.p
                        java.lang.Object r4 = r5.t
                        boolean r3 = r3.test(r4)
                        if (r3 == 0) goto L22
                        r0 = 1
                        goto L7
                    L22:
                        if (r1 == 0) goto L30
                        if (r0 == 0) goto L2b
                        java.util.concurrent.atomic.AtomicBoolean r0 = r5.cancel
                        r0.set(r2)
                    L2b:
                        java.lang.Object r0 = r5.t
                        r6.accept(r0)
                    L30:
                        return r1
                    L31:
                        j$.util.Spliterator r0 = r5.s
                        boolean r6 = r0.tryAdvance(r6)
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.WhileOps.UnorderedWhileSpliterator.OfRef.Dropping.tryAdvance(java.util.function.Consumer):boolean");
                }
            }

            /* loaded from: classes2.dex */
            static final class Taking extends OfRef {
                Taking(Spliterator spliterator, Taking taking) {
                    super(spliterator, taking);
                }

                Taking(Spliterator spliterator, boolean z, Predicate predicate) {
                    super(spliterator, z, predicate);
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator
                Spliterator makeSpliterator(Spliterator spliterator) {
                    return new Taking(spliterator, this);
                }

                @Override // j$.util.Spliterator
                public boolean tryAdvance(Consumer consumer) {
                    boolean z;
                    if (this.takeOrDrop && checkCancelOnCount() && this.s.tryAdvance(this)) {
                        z = this.p.test(this.t);
                        if (z) {
                            consumer.accept(this.t);
                            return true;
                        }
                    } else {
                        z = true;
                    }
                    this.takeOrDrop = false;
                    if (!z) {
                        this.cancel.set(true);
                    }
                    return false;
                }

                @Override // j$.util.stream.WhileOps.UnorderedWhileSpliterator, j$.util.Spliterator
                public Spliterator trySplit() {
                    if (this.cancel.get()) {
                        return null;
                    }
                    return super.trySplit();
                }
            }

            OfRef(Spliterator spliterator, OfRef ofRef) {
                super(spliterator, ofRef);
                this.p = ofRef.p;
            }

            OfRef(Spliterator spliterator, boolean z, Predicate predicate) {
                super(spliterator, z);
                this.p = predicate;
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                this.count = (this.count + 1) & 63;
                this.t = obj;
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }
        }

        UnorderedWhileSpliterator(Spliterator spliterator, UnorderedWhileSpliterator unorderedWhileSpliterator) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = unorderedWhileSpliterator.noSplitting;
            this.cancel = unorderedWhileSpliterator.cancel;
        }

        UnorderedWhileSpliterator(Spliterator spliterator, boolean z) {
            this.takeOrDrop = true;
            this.s = spliterator;
            this.noSplitting = z;
            this.cancel = new AtomicBoolean();
        }

        @Override // j$.util.Spliterator
        public int characteristics() {
            return this.s.characteristics() & (-16449);
        }

        boolean checkCancelOnCount() {
            return (this.count == 0 && this.cancel.get()) ? false : true;
        }

        @Override // j$.util.Spliterator
        public long estimateSize() {
            return this.s.estimateSize();
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.CC.$default$forEachRemaining(this, consumer);
        }

        @Override // j$.util.Spliterator
        public Comparator getComparator() {
            return this.s.getComparator();
        }

        @Override // j$.util.Spliterator
        public long getExactSizeIfKnown() {
            return -1L;
        }

        @Override // j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        abstract Spliterator makeSpliterator(Spliterator spliterator);

        @Override // j$.util.Spliterator
        public Spliterator trySplit() {
            Spliterator trySplit = this.noSplitting ? null : this.s.trySplit();
            if (trySplit != null) {
                return makeSpliterator(trySplit);
            }
            return null;
        }
    }

    static {
        int i = StreamOpFlag.NOT_SIZED;
        TAKE_FLAGS = StreamOpFlag.IS_SHORT_CIRCUIT | i;
        DROP_FLAGS = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Stream makeDropWhileRef(AbstractPipeline abstractPipeline, Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new C1Op(abstractPipeline, StreamShape.REFERENCE, DROP_FLAGS, predicate);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Stream makeTakeWhileRef(AbstractPipeline abstractPipeline, final Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new ReferencePipeline.StatefulOp(abstractPipeline, StreamShape.REFERENCE, TAKE_FLAGS) { // from class: j$.util.stream.WhileOps.1
            @Override // j$.util.stream.AbstractPipeline
            Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
                return (Node) new TakeWhileTask(this, pipelineHelper, spliterator, intFunction).invoke();
            }

            @Override // j$.util.stream.AbstractPipeline
            Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? opEvaluateParallel(pipelineHelper, spliterator, Nodes.castingArray()).spliterator() : new UnorderedWhileSpliterator.OfRef.Taking(pipelineHelper.wrapSpliterator(spliterator), false, predicate);
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedReference(sink) { // from class: j$.util.stream.WhileOps.1.1
                    boolean take = true;

                    @Override // java.util.function.Consumer
                    public void accept(Object obj) {
                        if (this.take) {
                            boolean test = predicate.test(obj);
                            this.take = test;
                            if (test) {
                                this.downstream.accept((Sink) obj);
                            }
                        }
                    }

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                    public boolean cancellationRequested() {
                        return !this.take || this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }
}
