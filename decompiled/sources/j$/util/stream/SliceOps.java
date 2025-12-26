package j$.util.stream;

import j$.util.Spliterator;
import j$.util.stream.DoublePipeline;
import j$.util.stream.IntPipeline;
import j$.util.stream.LongPipeline;
import j$.util.stream.Node;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import j$.util.stream.SliceOps;
import j$.util.stream.StreamSpliterators$SliceSpliterator;
import j$.util.stream.StreamSpliterators$UnorderedSliceSpliterator;
import java.util.function.IntFunction;
/* loaded from: classes2.dex */
abstract class SliceOps {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.SliceOps$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 extends IntPipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Integer[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Integer[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectInt(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Nodes.collectInt(this, unorderedSkipLimitSpliterator((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true) : (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                long j = this.val$skip;
                return new StreamSpliterators$SliceSpliterator.OfInt((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), j, SliceOps.calcSliceFence(j, this.val$limit));
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? unorderedSkipLimitSpliterator((Spliterator.OfInt) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown) : ((Node) new SliceTask(this, pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.SliceOps$2$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return SliceOps.AnonymousClass2.lambda$opEvaluateParallelLazy$0(i);
                }
            }, this.val$skip, this.val$limit).invoke()).spliterator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.AbstractPipeline
        public Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedInt(sink) { // from class: j$.util.stream.SliceOps.2.1
                long m;
                long n;

                {
                    this.n = AnonymousClass2.this.val$skip;
                    long j = AnonymousClass2.this.val$limit;
                    this.m = j < 0 ? Long.MAX_VALUE : j;
                }

                @Override // j$.util.stream.Sink.OfInt, j$.util.stream.Sink
                public void accept(int i2) {
                    long j = this.n;
                    if (j != 0) {
                        this.n = j - 1;
                        return;
                    }
                    long j2 = this.m;
                    if (j2 > 0) {
                        this.m = j2 - 1;
                        this.downstream.accept(i2);
                    }
                }

                @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(SliceOps.calcSize(j, AnonymousClass2.this.val$skip, this.m));
                }

                @Override // j$.util.stream.Sink.ChainedInt, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return this.m == 0 || this.downstream.cancellationRequested();
                }
            };
        }

        Spliterator.OfInt unorderedSkipLimitSpliterator(Spliterator.OfInt ofInt, long j, long j2, long j3) {
            long j4;
            long j5;
            if (j <= j3) {
                long j6 = j3 - j;
                j5 = j2 >= 0 ? Math.min(j2, j6) : j6;
                j4 = 0;
            } else {
                j4 = j;
                j5 = j2;
            }
            return new StreamSpliterators$UnorderedSliceSpliterator.OfInt(ofInt, j4, j5);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.SliceOps$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 extends LongPipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Long[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Long[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectLong(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Nodes.collectLong(this, unorderedSkipLimitSpliterator((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true) : (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                long j = this.val$skip;
                return new StreamSpliterators$SliceSpliterator.OfLong((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), j, SliceOps.calcSliceFence(j, this.val$limit));
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? unorderedSkipLimitSpliterator((Spliterator.OfLong) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown) : ((Node) new SliceTask(this, pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.SliceOps$3$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return SliceOps.AnonymousClass3.lambda$opEvaluateParallelLazy$0(i);
                }
            }, this.val$skip, this.val$limit).invoke()).spliterator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.AbstractPipeline
        public Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedLong(sink) { // from class: j$.util.stream.SliceOps.3.1
                long m;
                long n;

                {
                    this.n = AnonymousClass3.this.val$skip;
                    long j = AnonymousClass3.this.val$limit;
                    this.m = j < 0 ? Long.MAX_VALUE : j;
                }

                @Override // j$.util.stream.Sink.OfLong, j$.util.stream.Sink
                public void accept(long j) {
                    long j2 = this.n;
                    if (j2 != 0) {
                        this.n = j2 - 1;
                        return;
                    }
                    long j3 = this.m;
                    if (j3 > 0) {
                        this.m = j3 - 1;
                        this.downstream.accept(j);
                    }
                }

                @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(SliceOps.calcSize(j, AnonymousClass3.this.val$skip, this.m));
                }

                @Override // j$.util.stream.Sink.ChainedLong, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return this.m == 0 || this.downstream.cancellationRequested();
                }
            };
        }

        Spliterator.OfLong unorderedSkipLimitSpliterator(Spliterator.OfLong ofLong, long j, long j2, long j3) {
            long j4;
            long j5;
            if (j <= j3) {
                long j6 = j3 - j;
                j5 = j2 >= 0 ? Math.min(j2, j6) : j6;
                j4 = 0;
            } else {
                j4 = j;
                j5 = j2;
            }
            return new StreamSpliterators$UnorderedSliceSpliterator.OfLong(ofLong, j4, j5);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.SliceOps$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass4 extends DoublePipeline.StatefulOp {
        final /* synthetic */ long val$limit;
        final /* synthetic */ long val$skip;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass4(AbstractPipeline abstractPipeline, StreamShape streamShape, int i, long j, long j2) {
            super(abstractPipeline, streamShape, i);
            this.val$skip = j;
            this.val$limit = j2;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Double[] lambda$opEvaluateParallelLazy$0(int i) {
            return new Double[i];
        }

        @Override // j$.util.stream.AbstractPipeline
        Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                return Nodes.collectDouble(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, this.val$skip, this.val$limit), true);
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Nodes.collectDouble(this, unorderedSkipLimitSpliterator((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown), true) : (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, this.val$skip, this.val$limit).invoke();
        }

        @Override // j$.util.stream.AbstractPipeline
        Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
            long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
            if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                long j = this.val$skip;
                return new StreamSpliterators$SliceSpliterator.OfDouble((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), j, SliceOps.calcSliceFence(j, this.val$limit));
            }
            return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? unorderedSkipLimitSpliterator((Spliterator.OfDouble) pipelineHelper.wrapSpliterator(spliterator), this.val$skip, this.val$limit, exactOutputSizeIfKnown) : ((Node) new SliceTask(this, pipelineHelper, spliterator, new IntFunction() { // from class: j$.util.stream.SliceOps$4$$ExternalSyntheticLambda0
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return SliceOps.AnonymousClass4.lambda$opEvaluateParallelLazy$0(i);
                }
            }, this.val$skip, this.val$limit).invoke()).spliterator();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.AbstractPipeline
        public Sink opWrapSink(int i, Sink sink) {
            return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.SliceOps.4.1
                long m;
                long n;

                {
                    this.n = AnonymousClass4.this.val$skip;
                    long j = AnonymousClass4.this.val$limit;
                    this.m = j < 0 ? Long.MAX_VALUE : j;
                }

                @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                public void accept(double d) {
                    long j = this.n;
                    if (j != 0) {
                        this.n = j - 1;
                        return;
                    }
                    long j2 = this.m;
                    if (j2 > 0) {
                        this.m = j2 - 1;
                        this.downstream.accept(d);
                    }
                }

                @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                public void begin(long j) {
                    this.downstream.begin(SliceOps.calcSize(j, AnonymousClass4.this.val$skip, this.m));
                }

                @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                public boolean cancellationRequested() {
                    return this.m == 0 || this.downstream.cancellationRequested();
                }
            };
        }

        Spliterator.OfDouble unorderedSkipLimitSpliterator(Spliterator.OfDouble ofDouble, long j, long j2, long j3) {
            long j4;
            long j5;
            if (j <= j3) {
                long j6 = j3 - j;
                j5 = j2 >= 0 ? Math.min(j2, j6) : j6;
                j4 = 0;
            } else {
                j4 = j;
                j5 = j2;
            }
            return new StreamSpliterators$UnorderedSliceSpliterator.OfDouble(ofDouble, j4, j5);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.SliceOps$5  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$java$util$stream$StreamShape;

        static {
            int[] iArr = new int[StreamShape.values().length];
            $SwitchMap$java$util$stream$StreamShape = iArr;
            try {
                iArr[StreamShape.REFERENCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.INT_VALUE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.LONG_VALUE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$java$util$stream$StreamShape[StreamShape.DOUBLE_VALUE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* loaded from: classes2.dex */
    private static final class SliceTask extends AbstractShortCircuitTask {
        private volatile boolean completed;
        private final IntFunction generator;
        private final AbstractPipeline op;
        private final long targetOffset;
        private final long targetSize;
        private long thisNodeSize;

        SliceTask(AbstractPipeline abstractPipeline, PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction, long j, long j2) {
            super(pipelineHelper, spliterator);
            this.op = abstractPipeline;
            this.generator = intFunction;
            this.targetOffset = j;
            this.targetSize = j2;
        }

        SliceTask(SliceTask sliceTask, Spliterator spliterator) {
            super(sliceTask, spliterator);
            this.op = sliceTask.op;
            this.generator = sliceTask.generator;
            this.targetOffset = sliceTask.targetOffset;
            this.targetSize = sliceTask.targetSize;
        }

        private long completedSize(long j) {
            if (this.completed) {
                return this.thisNodeSize;
            }
            SliceTask sliceTask = (SliceTask) this.leftChild;
            SliceTask sliceTask2 = (SliceTask) this.rightChild;
            if (sliceTask == null || sliceTask2 == null) {
                return this.thisNodeSize;
            }
            long completedSize = sliceTask.completedSize(j);
            return completedSize >= j ? completedSize : completedSize + sliceTask2.completedSize(j);
        }

        private Node doTruncate(Node node) {
            return node.truncate(this.targetOffset, this.targetSize >= 0 ? Math.min(node.count(), this.targetOffset + this.targetSize) : this.thisNodeSize, this.generator);
        }

        private boolean isLeftCompleted(long j) {
            SliceTask sliceTask;
            long completedSize = this.completed ? this.thisNodeSize : completedSize(j);
            if (completedSize >= j) {
                return true;
            }
            SliceTask sliceTask2 = this;
            for (SliceTask sliceTask3 = (SliceTask) getParent(); sliceTask3 != null; sliceTask3 = (SliceTask) sliceTask3.getParent()) {
                if (sliceTask2 == sliceTask3.rightChild && (sliceTask = (SliceTask) sliceTask3.leftChild) != null) {
                    completedSize += sliceTask.completedSize(j);
                    if (completedSize >= j) {
                        return true;
                    }
                }
                sliceTask2 = sliceTask3;
            }
            return completedSize >= j;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public void cancel() {
            super.cancel();
            if (this.completed) {
                setLocalResult(getEmptyResult());
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public final Node doLeaf() {
            if (isRoot()) {
                Node.Builder makeNodeBuilder = this.op.makeNodeBuilder(StreamOpFlag.SIZED.isPreserved(this.op.sourceOrOpFlags) ? this.op.exactOutputSizeIfKnown(this.spliterator) : -1L, this.generator);
                Sink opWrapSink = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder);
                PipelineHelper pipelineHelper = this.helper;
                pipelineHelper.copyIntoWithCancel(pipelineHelper.wrapSink(opWrapSink), this.spliterator);
                return makeNodeBuilder.build();
            }
            Node.Builder makeNodeBuilder2 = this.op.makeNodeBuilder(-1L, this.generator);
            if (this.targetOffset == 0) {
                Sink opWrapSink2 = this.op.opWrapSink(this.helper.getStreamAndOpFlags(), makeNodeBuilder2);
                PipelineHelper pipelineHelper2 = this.helper;
                pipelineHelper2.copyIntoWithCancel(pipelineHelper2.wrapSink(opWrapSink2), this.spliterator);
            } else {
                this.helper.wrapAndCopyInto(makeNodeBuilder2, this.spliterator);
            }
            Node build = makeNodeBuilder2.build();
            this.thisNodeSize = build.count();
            this.completed = true;
            this.spliterator = null;
            return build;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractShortCircuitTask
        public final Node getEmptyResult() {
            return Nodes.emptyNode(this.op.getOutputShape());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public SliceTask makeChild(Spliterator spliterator) {
            return new SliceTask(this, spliterator);
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0062  */
        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final void onCompletion(java.util.concurrent.CountedCompleter r8) {
            /*
                r7 = this;
                boolean r0 = r7.isLeaf()
                r1 = 0
                if (r0 != 0) goto L6c
                j$.util.stream.AbstractTask r0 = r7.leftChild
                j$.util.stream.SliceOps$SliceTask r0 = (j$.util.stream.SliceOps.SliceTask) r0
                long r3 = r0.thisNodeSize
                j$.util.stream.AbstractTask r0 = r7.rightChild
                j$.util.stream.SliceOps$SliceTask r0 = (j$.util.stream.SliceOps.SliceTask) r0
                long r5 = r0.thisNodeSize
                long r3 = r3 + r5
                r7.thisNodeSize = r3
                boolean r0 = r7.canceled
                if (r0 == 0) goto L22
                r7.thisNodeSize = r1
            L1d:
                j$.util.stream.Node r0 = r7.getEmptyResult()
                goto L5c
            L22:
                long r3 = r7.thisNodeSize
                int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r0 != 0) goto L29
                goto L1d
            L29:
                j$.util.stream.AbstractTask r0 = r7.leftChild
                j$.util.stream.SliceOps$SliceTask r0 = (j$.util.stream.SliceOps.SliceTask) r0
                long r3 = r0.thisNodeSize
                int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r0 != 0) goto L3e
                j$.util.stream.AbstractTask r0 = r7.rightChild
                j$.util.stream.SliceOps$SliceTask r0 = (j$.util.stream.SliceOps.SliceTask) r0
                java.lang.Object r0 = r0.getLocalResult()
                j$.util.stream.Node r0 = (j$.util.stream.Node) r0
                goto L5c
            L3e:
                j$.util.stream.AbstractPipeline r0 = r7.op
                j$.util.stream.StreamShape r0 = r0.getOutputShape()
                j$.util.stream.AbstractTask r3 = r7.leftChild
                j$.util.stream.SliceOps$SliceTask r3 = (j$.util.stream.SliceOps.SliceTask) r3
                java.lang.Object r3 = r3.getLocalResult()
                j$.util.stream.Node r3 = (j$.util.stream.Node) r3
                j$.util.stream.AbstractTask r4 = r7.rightChild
                j$.util.stream.SliceOps$SliceTask r4 = (j$.util.stream.SliceOps.SliceTask) r4
                java.lang.Object r4 = r4.getLocalResult()
                j$.util.stream.Node r4 = (j$.util.stream.Node) r4
                j$.util.stream.Node r0 = j$.util.stream.Nodes.conc(r0, r3, r4)
            L5c:
                boolean r3 = r7.isRoot()
                if (r3 == 0) goto L66
                j$.util.stream.Node r0 = r7.doTruncate(r0)
            L66:
                r7.setLocalResult(r0)
                r0 = 1
                r7.completed = r0
            L6c:
                long r3 = r7.targetSize
                int r0 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
                if (r0 < 0) goto L86
                boolean r0 = r7.isRoot()
                if (r0 != 0) goto L86
                long r0 = r7.targetOffset
                long r2 = r7.targetSize
                long r0 = r0 + r2
                boolean r0 = r7.isLeftCompleted(r0)
                if (r0 == 0) goto L86
                r7.cancelLaterNodes()
            L86:
                super.onCompletion(r8)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.SliceOps.SliceTask.onCompletion(java.util.concurrent.CountedCompleter):void");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long calcSize(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long calcSliceFence(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    private static int flags(long j) {
        return (j != -1 ? StreamOpFlag.IS_SHORT_CIRCUIT : 0) | StreamOpFlag.NOT_SIZED;
    }

    public static DoubleStream makeDouble(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j >= 0) {
            return new AnonymousClass4(abstractPipeline, StreamShape.DOUBLE_VALUE, flags(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static IntStream makeInt(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j >= 0) {
            return new AnonymousClass2(abstractPipeline, StreamShape.INT_VALUE, flags(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static LongStream makeLong(AbstractPipeline abstractPipeline, long j, long j2) {
        if (j >= 0) {
            return new AnonymousClass3(abstractPipeline, StreamShape.LONG_VALUE, flags(j2), j, j2);
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    public static Stream makeRef(AbstractPipeline abstractPipeline, final long j, final long j2) {
        if (j >= 0) {
            return new ReferencePipeline.StatefulOp(abstractPipeline, StreamShape.REFERENCE, flags(j2)) { // from class: j$.util.stream.SliceOps.1
                @Override // j$.util.stream.AbstractPipeline
                Node opEvaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator, IntFunction intFunction) {
                    long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                        return Nodes.collect(pipelineHelper, SliceOps.sliceSpliterator(pipelineHelper.getSourceShape(), spliterator, j, j2), true, intFunction);
                    }
                    return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Nodes.collect(this, unorderedSkipLimitSpliterator(pipelineHelper.wrapSpliterator(spliterator), j, j2, exactOutputSizeIfKnown), true, intFunction) : (Node) new SliceTask(this, pipelineHelper, spliterator, intFunction, j, j2).invoke();
                }

                @Override // j$.util.stream.AbstractPipeline
                Spliterator opEvaluateParallelLazy(PipelineHelper pipelineHelper, Spliterator spliterator) {
                    long exactOutputSizeIfKnown = pipelineHelper.exactOutputSizeIfKnown(spliterator);
                    if (exactOutputSizeIfKnown > 0 && spliterator.hasCharacteristics(16384)) {
                        Spliterator wrapSpliterator = pipelineHelper.wrapSpliterator(spliterator);
                        long j3 = j;
                        return new StreamSpliterators$SliceSpliterator.OfRef(wrapSpliterator, j3, SliceOps.calcSliceFence(j3, j2));
                    }
                    return !StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? unorderedSkipLimitSpliterator(pipelineHelper.wrapSpliterator(spliterator), j, j2, exactOutputSizeIfKnown) : ((Node) new SliceTask(this, pipelineHelper, spliterator, Nodes.castingArray(), j, j2).invoke()).spliterator();
                }

                /* JADX INFO: Access modifiers changed from: package-private */
                @Override // j$.util.stream.AbstractPipeline
                public Sink opWrapSink(int i, Sink sink) {
                    return new Sink.ChainedReference(sink) { // from class: j$.util.stream.SliceOps.1.1
                        long m;
                        long n;

                        {
                            this.n = j;
                            long j3 = j2;
                            this.m = j3 < 0 ? Long.MAX_VALUE : j3;
                        }

                        @Override // java.util.function.Consumer
                        public void accept(Object obj) {
                            long j3 = this.n;
                            if (j3 != 0) {
                                this.n = j3 - 1;
                                return;
                            }
                            long j4 = this.m;
                            if (j4 > 0) {
                                this.m = j4 - 1;
                                this.downstream.accept((Sink) obj);
                            }
                        }

                        @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                        public void begin(long j3) {
                            this.downstream.begin(SliceOps.calcSize(j3, j, this.m));
                        }

                        @Override // j$.util.stream.Sink.ChainedReference, j$.util.stream.Sink
                        public boolean cancellationRequested() {
                            return this.m == 0 || this.downstream.cancellationRequested();
                        }
                    };
                }

                Spliterator unorderedSkipLimitSpliterator(Spliterator spliterator, long j3, long j4, long j5) {
                    long j6;
                    long j7;
                    if (j3 <= j5) {
                        long j8 = j5 - j3;
                        j7 = j4 >= 0 ? Math.min(j4, j8) : j8;
                        j6 = 0;
                    } else {
                        j6 = j3;
                        j7 = j4;
                    }
                    return new StreamSpliterators$UnorderedSliceSpliterator.OfRef(spliterator, j6, j7);
                }
            };
        }
        throw new IllegalArgumentException("Skip must be non-negative: " + j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Spliterator sliceSpliterator(StreamShape streamShape, Spliterator spliterator, long j, long j2) {
        long calcSliceFence = calcSliceFence(j, j2);
        int i = AnonymousClass5.$SwitchMap$java$util$stream$StreamShape[streamShape.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return new StreamSpliterators$SliceSpliterator.OfDouble((Spliterator.OfDouble) spliterator, j, calcSliceFence);
                    }
                    throw new IllegalStateException("Unknown shape " + streamShape);
                }
                return new StreamSpliterators$SliceSpliterator.OfLong((Spliterator.OfLong) spliterator, j, calcSliceFence);
            }
            return new StreamSpliterators$SliceSpliterator.OfInt((Spliterator.OfInt) spliterator, j, calcSliceFence);
        }
        return new StreamSpliterators$SliceSpliterator.OfRef(spliterator, j, calcSliceFence);
    }
}
