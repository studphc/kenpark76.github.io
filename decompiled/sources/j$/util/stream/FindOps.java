package j$.util.stream;

import j$.util.Optional;
import j$.util.OptionalDouble;
import j$.util.OptionalInt;
import j$.util.OptionalLong;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.function.Predicate$CC;
import j$.util.stream.FindOps;
import j$.util.stream.Sink;
import java.util.concurrent.CountedCompleter;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
/* loaded from: classes2.dex */
abstract class FindOps {

    /* loaded from: classes2.dex */
    private static final class FindOp implements TerminalOp {
        final Object emptyValue;
        final int opFlags;
        final Predicate presentPredicate;
        private final StreamShape shape;
        final Supplier sinkSupplier;

        FindOp(boolean z, StreamShape streamShape, Object obj, Predicate predicate, Supplier supplier) {
            this.opFlags = (z ? 0 : StreamOpFlag.NOT_ORDERED) | StreamOpFlag.IS_SHORT_CIRCUIT;
            this.shape = streamShape;
            this.emptyValue = obj;
            this.presentPredicate = predicate;
            this.sinkSupplier = supplier;
        }

        @Override // j$.util.stream.TerminalOp
        public Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return new FindTask(this, StreamOpFlag.ORDERED.isKnown(pipelineHelper.getStreamAndOpFlags()), pipelineHelper, spliterator).invoke();
        }

        @Override // j$.util.stream.TerminalOp
        public Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            Object obj = ((TerminalSink) pipelineHelper.wrapAndCopyInto((TerminalSink) this.sinkSupplier.get(), spliterator)).get();
            return obj != null ? obj : this.emptyValue;
        }

        @Override // j$.util.stream.TerminalOp
        public int getOpFlags() {
            return this.opFlags;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class FindSink implements TerminalSink {
        boolean hasValue;
        Object value;

        /* loaded from: classes2.dex */
        static final class OfDouble extends FindSink implements Sink.OfDouble {
            static final TerminalOp OP_FIND_ANY;
            static final TerminalOp OP_FIND_FIRST;

            static {
                StreamShape streamShape = StreamShape.DOUBLE_VALUE;
                OP_FIND_FIRST = new FindOp(true, streamShape, OptionalDouble.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfDouble$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalDouble) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfDouble$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfDouble();
                    }
                });
                OP_FIND_ANY = new FindOp(false, streamShape, OptionalDouble.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfDouble$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalDouble) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfDouble$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfDouble();
                    }
                });
            }

            @Override // j$.util.stream.FindOps.FindSink, j$.util.stream.Sink, java.util.function.DoubleConsumer
            public void accept(double d) {
                accept((Object) Double.valueOf(d));
            }

            @Override // j$.util.stream.Sink.OfDouble
            public /* bridge */ /* synthetic */ void accept(Double d) {
                super.accept((Object) d);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
            }

            @Override // java.util.function.Supplier
            public OptionalDouble get() {
                if (this.hasValue) {
                    return OptionalDouble.of(((Double) this.value).doubleValue());
                }
                return null;
            }
        }

        /* loaded from: classes2.dex */
        static final class OfInt extends FindSink implements Sink.OfInt {
            static final TerminalOp OP_FIND_ANY;
            static final TerminalOp OP_FIND_FIRST;

            static {
                StreamShape streamShape = StreamShape.INT_VALUE;
                OP_FIND_FIRST = new FindOp(true, streamShape, OptionalInt.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfInt$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalInt) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfInt$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfInt();
                    }
                });
                OP_FIND_ANY = new FindOp(false, streamShape, OptionalInt.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfInt$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalInt) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfInt$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfInt();
                    }
                });
            }

            @Override // j$.util.stream.FindOps.FindSink, j$.util.stream.Sink
            public void accept(int i) {
                accept((Object) Integer.valueOf(i));
            }

            @Override // j$.util.stream.Sink.OfInt
            public /* bridge */ /* synthetic */ void accept(Integer num) {
                super.accept((Object) num);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer$CC.$default$andThen(this, intConsumer);
            }

            @Override // java.util.function.Supplier
            public OptionalInt get() {
                if (this.hasValue) {
                    return OptionalInt.of(((Integer) this.value).intValue());
                }
                return null;
            }
        }

        /* loaded from: classes2.dex */
        static final class OfLong extends FindSink implements Sink.OfLong {
            static final TerminalOp OP_FIND_ANY;
            static final TerminalOp OP_FIND_FIRST;

            static {
                StreamShape streamShape = StreamShape.LONG_VALUE;
                OP_FIND_FIRST = new FindOp(true, streamShape, OptionalLong.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfLong$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalLong) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfLong$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfLong();
                    }
                });
                OP_FIND_ANY = new FindOp(false, streamShape, OptionalLong.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfLong$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((OptionalLong) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfLong$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfLong();
                    }
                });
            }

            @Override // j$.util.stream.FindOps.FindSink, j$.util.stream.Sink
            public void accept(long j) {
                accept((Object) Long.valueOf(j));
            }

            @Override // j$.util.stream.Sink.OfLong
            public /* bridge */ /* synthetic */ void accept(Long l) {
                super.accept((Object) l);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer$CC.$default$andThen(this, longConsumer);
            }

            @Override // java.util.function.Supplier
            public OptionalLong get() {
                if (this.hasValue) {
                    return OptionalLong.of(((Long) this.value).longValue());
                }
                return null;
            }
        }

        /* loaded from: classes2.dex */
        static final class OfRef extends FindSink {
            static final TerminalOp OP_FIND_ANY;
            static final TerminalOp OP_FIND_FIRST;

            static {
                StreamShape streamShape = StreamShape.REFERENCE;
                OP_FIND_FIRST = new FindOp(true, streamShape, Optional.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfRef$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Optional) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfRef$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfRef();
                    }
                });
                OP_FIND_ANY = new FindOp(false, streamShape, Optional.empty(), new Predicate() { // from class: j$.util.stream.FindOps$FindSink$OfRef$$ExternalSyntheticLambda0
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    public /* synthetic */ Predicate or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((Optional) obj).isPresent();
                    }
                }, new Supplier() { // from class: j$.util.stream.FindOps$FindSink$OfRef$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return new FindOps.FindSink.OfRef();
                    }
                });
            }

            @Override // java.util.function.Supplier
            public Optional get() {
                if (this.hasValue) {
                    return Optional.of(this.value);
                }
                return null;
            }
        }

        FindSink() {
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // java.util.function.Consumer
        public void accept(Object obj) {
            if (this.hasValue) {
                return;
            }
            this.hasValue = true;
            this.value = obj;
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void begin(long j) {
            Sink.CC.$default$begin(this, j);
        }

        @Override // j$.util.stream.Sink
        public boolean cancellationRequested() {
            return this.hasValue;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* loaded from: classes2.dex */
    private static final class FindTask extends AbstractShortCircuitTask {
        private final boolean mustFindFirst;
        private final FindOp op;

        FindTask(FindOp findOp, boolean z, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.mustFindFirst = z;
            this.op = findOp;
        }

        FindTask(FindTask findTask, Spliterator spliterator) {
            super(findTask, spliterator);
            this.mustFindFirst = findTask.mustFindFirst;
            this.op = findTask.op;
        }

        private void foundResult(Object obj) {
            if (isLeftmostNode()) {
                shortCircuit(obj);
            } else {
                cancelLaterNodes();
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public Object doLeaf() {
            Object obj = ((TerminalSink) this.helper.wrapAndCopyInto((TerminalSink) this.op.sinkSupplier.get(), this.spliterator)).get();
            if (!this.mustFindFirst) {
                if (obj != null) {
                    shortCircuit(obj);
                }
                return null;
            } else if (obj != null) {
                foundResult(obj);
                return obj;
            } else {
                return null;
            }
        }

        @Override // j$.util.stream.AbstractShortCircuitTask
        protected Object getEmptyResult() {
            return this.op.emptyValue;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public FindTask makeChild(Spliterator spliterator) {
            return new FindTask(this, spliterator);
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter countedCompleter) {
            if (this.mustFindFirst) {
                FindTask findTask = (FindTask) this.leftChild;
                FindTask findTask2 = null;
                while (true) {
                    if (findTask != findTask2) {
                        Object localResult = findTask.getLocalResult();
                        if (localResult != null && this.op.presentPredicate.test(localResult)) {
                            setLocalResult(localResult);
                            foundResult(localResult);
                            break;
                        }
                        findTask2 = findTask;
                        findTask = (FindTask) this.rightChild;
                    } else {
                        break;
                    }
                }
            }
            super.onCompletion(countedCompleter);
        }
    }

    public static TerminalOp makeDouble(boolean z) {
        return z ? FindSink.OfDouble.OP_FIND_FIRST : FindSink.OfDouble.OP_FIND_ANY;
    }

    public static TerminalOp makeInt(boolean z) {
        return z ? FindSink.OfInt.OP_FIND_FIRST : FindSink.OfInt.OP_FIND_ANY;
    }

    public static TerminalOp makeLong(boolean z) {
        return z ? FindSink.OfLong.OP_FIND_FIRST : FindSink.OfLong.OP_FIND_ANY;
    }

    public static TerminalOp makeRef(boolean z) {
        return z ? FindSink.OfRef.OP_FIND_FIRST : FindSink.OfRef.OP_FIND_ANY;
    }
}
