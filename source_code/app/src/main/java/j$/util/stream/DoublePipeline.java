package j$.util.stream;

import j$.util.DoubleSummaryStatistics;
import j$.util.Objects;
import j$.util.OptionalDouble;
import j$.util.Spliterator;
import j$.util.Spliterators;
import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.stream.IntPipeline;
import j$.util.stream.LongPipeline;
import j$.util.stream.MatchOps;
import j$.util.stream.Node;
import j$.util.stream.ReferencePipeline;
import j$.util.stream.Sink;
import j$.util.stream.StreamSpliterators$DelegatingSpliterator;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
/* loaded from: classes2.dex */
abstract class DoublePipeline extends AbstractPipeline implements DoubleStream {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class Head extends DoublePipeline {
        /* JADX INFO: Access modifiers changed from: package-private */
        public Head(Spliterator spliterator, int i, boolean z) {
            super(spliterator, i, z);
        }

        @Override // j$.util.stream.DoublePipeline, j$.util.stream.DoubleStream
        public void forEach(DoubleConsumer doubleConsumer) {
            if (isParallel()) {
                super.forEach(doubleConsumer);
            } else {
                DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            }
        }

        @Override // j$.util.stream.DoublePipeline, j$.util.stream.DoubleStream
        public void forEachOrdered(DoubleConsumer doubleConsumer) {
            if (isParallel()) {
                super.forEachOrdered(doubleConsumer);
            } else {
                DoublePipeline.adapt(sourceStageSpliterator()).forEachRemaining(doubleConsumer);
            }
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return super.iterator();
        }

        @Override // j$.util.stream.AbstractPipeline
        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            throw new UnsupportedOperationException();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.AbstractPipeline
        public final Sink opWrapSink(int i, Sink sink) {
            throw new UnsupportedOperationException();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }
    }

    /* loaded from: classes2.dex */
    static abstract class StatefulOp extends DoublePipeline {
        /* JADX INFO: Access modifiers changed from: package-private */
        public StatefulOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return super.iterator();
        }

        @Override // j$.util.stream.AbstractPipeline
        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return true;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }
    }

    /* loaded from: classes2.dex */
    static abstract class StatelessOp extends DoublePipeline {
        /* JADX INFO: Access modifiers changed from: package-private */
        public StatelessOp(AbstractPipeline abstractPipeline, StreamShape streamShape, int i) {
            super(abstractPipeline, i);
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Iterator<Double> iterator() {
            return super.iterator();
        }

        @Override // j$.util.stream.AbstractPipeline
        /* bridge */ /* synthetic */ Spliterator lazySpliterator(Supplier supplier) {
            return super.lazySpliterator(supplier);
        }

        @Override // j$.util.stream.AbstractPipeline
        final boolean opIsStateful() {
            return false;
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream parallel() {
            return (DoubleStream) super.parallel();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ DoubleStream sequential() {
            return (DoubleStream) super.sequential();
        }

        @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ Spliterator spliterator() {
            return super.spliterator();
        }

        @Override // j$.util.stream.BaseStream
        public /* bridge */ /* synthetic */ BaseStream unordered() {
            return super.unordered();
        }
    }

    DoublePipeline(Spliterator spliterator, int i, boolean z) {
        super(spliterator, i, z);
    }

    DoublePipeline(AbstractPipeline abstractPipeline, int i) {
        super(abstractPipeline, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Spliterator.OfDouble adapt(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfDouble) {
            return (Spliterator.OfDouble) spliterator;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Spliterator<Double> s)");
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    private static DoubleConsumer adapt(Sink sink) {
        if (sink instanceof DoubleConsumer) {
            return (DoubleConsumer) sink;
        }
        if (Tripwire.ENABLED) {
            Tripwire.trip(AbstractPipeline.class, "using DoubleStream.adapt(Sink<Double> s)");
        }
        Objects.requireNonNull(sink);
        return new DoublePipeline$$ExternalSyntheticLambda5(sink);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ double[] lambda$average$4() {
        return new double[4];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$average$5(double[] dArr, double d) {
        dArr[2] = dArr[2] + 1.0d;
        Collectors.sumWithCompensation(dArr, d);
        dArr[3] = dArr[3] + d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$average$6(double[] dArr, double[] dArr2) {
        Collectors.sumWithCompensation(dArr, dArr2[0]);
        Collectors.sumWithCompensation(dArr, dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
        dArr[3] = dArr[3] + dArr2[3];
    }

    /*  JADX ERROR: NullPointerException in pass: MarkMethodsForInline
        java.lang.NullPointerException
        	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
        	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
        	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
        	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
        	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
        	at jadx.core.dex.visitors.MarkMethodsForInline.visit(MarkMethodsForInline.java:37)
        */
    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ java.lang.Object lambda$collect$7(java.util.function.BiConsumer r0, java.lang.Object r1, java.lang.Object r2) {
        /*
            r0.accept(r1, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.DoublePipeline.lambda$collect$7(java.util.function.BiConsumer, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ double[] lambda$sum$1() {
        return new double[3];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sum$2(double[] dArr, double d) {
        Collectors.sumWithCompensation(dArr, d);
        dArr[2] = dArr[2] + d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$sum$3(double[] dArr, double[] dArr2) {
        Collectors.sumWithCompensation(dArr, dArr2[0]);
        Collectors.sumWithCompensation(dArr, dArr2[1]);
        dArr[2] = dArr[2] + dArr2[2];
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Double[] lambda$toArray$8(int i) {
        return new Double[i];
    }

    private Stream mapToObj(final DoubleFunction doubleFunction, int i) {
        return new ReferencePipeline.StatelessOp(this, StreamShape.DOUBLE_VALUE, i) { // from class: j$.util.stream.DoublePipeline.1
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i2, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.1.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        this.downstream.accept((Sink) doubleFunction.apply(d));
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final boolean allMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ALL))).booleanValue();
    }

    @Override // j$.util.stream.DoubleStream
    public final boolean anyMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.ANY))).booleanValue();
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble average() {
        double[] dArr = (double[]) collect(new Supplier() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoublePipeline.lambda$average$4();
            }
        }, new ObjDoubleConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda13
            @Override // java.util.function.ObjDoubleConsumer
            public final void accept(Object obj, double d) {
                DoublePipeline.lambda$average$5((double[]) obj, d);
            }
        }, new BiConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda14
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                DoublePipeline.lambda$average$6((double[]) obj, (double[]) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        });
        return dArr[2] > 0.0d ? OptionalDouble.of(Collectors.computeFinalSum(dArr) / dArr[2]) : OptionalDouble.empty();
    }

    @Override // j$.util.stream.DoubleStream
    public final Stream boxed() {
        return mapToObj(new DoubleFunction() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda6
            @Override // java.util.function.DoubleFunction
            public final Object apply(double d) {
                return Double.valueOf(d);
            }
        }, 0);
    }

    @Override // j$.util.stream.DoubleStream
    public final Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, final BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        return evaluate(ReduceOps.makeDouble(supplier, objDoubleConsumer, new BinaryOperator() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda1
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
                jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: j$.util.stream.DoublePipeline.lambda$collect$7(java.util.function.BiConsumer, java.lang.Object, java.lang.Object):java.lang.Object
                	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
                	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
                Caused by: java.lang.NullPointerException
                */
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object r2, java.lang.Object r3) {
                /*
                    r1 = this;
                    java.util.function.BiConsumer r0 = java.util.function.BiConsumer.this
                    java.lang.Object r2 = j$.util.stream.DoublePipeline.lambda$collect$7(r0, r2, r3)
                    return r2
                */
                throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda1.apply(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }));
    }

    @Override // j$.util.stream.DoubleStream
    public final long count() {
        return ((Long) evaluate(ReduceOps.makeDoubleCounting())).longValue();
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream distinct() {
        return boxed().distinct().mapToDouble(new ToDoubleFunction() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda3
            @Override // java.util.function.ToDoubleFunction
            public final double applyAsDouble(Object obj) {
                double doubleValue;
                doubleValue = ((Double) obj).doubleValue();
                return doubleValue;
            }
        });
    }

    @Override // j$.util.stream.AbstractPipeline
    final Node evaluateToNode(PipelineHelper pipelineHelper, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return Nodes.collectDouble(pipelineHelper, spliterator, z);
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream filter(DoublePredicate doublePredicate) {
        Objects.requireNonNull(doublePredicate);
        return new StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SIZED, doublePredicate) { // from class: j$.util.stream.DoublePipeline.7
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.7.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        getClass();
                        throw null;
                    }

                    @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble findAny() {
        return (OptionalDouble) evaluate(FindOps.makeDouble(false));
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble findFirst() {
        return (OptionalDouble) evaluate(FindOps.makeDouble(true));
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream flatMap(final DoubleFunction doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT | StreamOpFlag.NOT_SIZED) { // from class: j$.util.stream.DoublePipeline.5
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.5.1
                    boolean cancellationRequestedCalled;
                    DoubleConsumer downstreamAsDouble;

                    {
                        Sink sink2 = this.downstream;
                        Objects.requireNonNull(sink2);
                        this.downstreamAsDouble = new DoublePipeline$$ExternalSyntheticLambda5(sink2);
                    }

                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        DoubleStream doubleStream = (DoubleStream) doubleFunction.apply(d);
                        if (doubleStream != null) {
                            try {
                                if (this.cancellationRequestedCalled) {
                                    Spliterator.OfDouble spliterator = doubleStream.sequential().spliterator();
                                    while (!this.downstream.cancellationRequested() && spliterator.tryAdvance(this.downstreamAsDouble)) {
                                    }
                                } else {
                                    doubleStream.sequential().forEach(this.downstreamAsDouble);
                                }
                            } catch (Throwable th) {
                                try {
                                    doubleStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                        if (doubleStream != null) {
                            doubleStream.close();
                        }
                    }

                    @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                    public void begin(long j) {
                        this.downstream.begin(-1L);
                    }

                    @Override // j$.util.stream.Sink.ChainedDouble, j$.util.stream.Sink
                    public boolean cancellationRequested() {
                        this.cancellationRequestedCalled = true;
                        return this.downstream.cancellationRequested();
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public void forEach(DoubleConsumer doubleConsumer) {
        evaluate(ForEachOps.makeDouble(doubleConsumer, false));
    }

    @Override // j$.util.stream.DoubleStream
    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        evaluate(ForEachOps.makeDouble(doubleConsumer, true));
    }

    @Override // j$.util.stream.AbstractPipeline
    final boolean forEachWithCancel(Spliterator spliterator, Sink sink) {
        boolean cancellationRequested;
        Spliterator.OfDouble adapt = adapt(spliterator);
        DoubleConsumer adapt2 = adapt(sink);
        do {
            cancellationRequested = sink.cancellationRequested();
            if (cancellationRequested) {
                break;
            }
        } while (adapt.tryAdvance(adapt2));
        return cancellationRequested;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.util.stream.AbstractPipeline
    public final StreamShape getOutputShape() {
        return StreamShape.DOUBLE_VALUE;
    }

    @Override // j$.util.stream.BaseStream
    public final Iterator<Double> iterator() {
        return Spliterators.iterator(spliterator());
    }

    @Override // j$.util.stream.AbstractPipeline
    final Spliterator.OfDouble lazySpliterator(Supplier supplier) {
        return new StreamSpliterators$DelegatingSpliterator.OfDouble(supplier);
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream limit(long j) {
        if (j >= 0) {
            return SliceOps.makeDouble(this, 0L, j);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.PipelineHelper
    public final Node.Builder makeNodeBuilder(long j, IntFunction intFunction) {
        return Nodes.doubleBuilder(j);
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream map(DoubleUnaryOperator doubleUnaryOperator) {
        Objects.requireNonNull(doubleUnaryOperator);
        return new StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleUnaryOperator) { // from class: j$.util.stream.DoublePipeline.2
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.2.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        getClass();
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final IntStream mapToInt(DoubleToIntFunction doubleToIntFunction) {
        Objects.requireNonNull(doubleToIntFunction);
        return new IntPipeline.StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleToIntFunction) { // from class: j$.util.stream.DoublePipeline.3
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.3.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        getClass();
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final LongStream mapToLong(DoubleToLongFunction doubleToLongFunction) {
        Objects.requireNonNull(doubleToLongFunction);
        return new LongPipeline.StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT, doubleToLongFunction) { // from class: j$.util.stream.DoublePipeline.4
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.4.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        getClass();
                        throw null;
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final Stream mapToObj(DoubleFunction doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return mapToObj(doubleFunction, StreamOpFlag.NOT_SORTED | StreamOpFlag.NOT_DISTINCT);
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble max() {
        return reduce(new DoubleBinaryOperator() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda4
            @Override // java.util.function.DoubleBinaryOperator
            public final double applyAsDouble(double d, double d2) {
                return Math.max(d, d2);
            }
        });
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble min() {
        return reduce(new DoubleBinaryOperator() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleBinaryOperator
            public final double applyAsDouble(double d, double d2) {
                return Math.min(d, d2);
            }
        });
    }

    @Override // j$.util.stream.DoubleStream
    public final boolean noneMatch(DoublePredicate doublePredicate) {
        return ((Boolean) evaluate(MatchOps.makeDouble(doublePredicate, MatchOps.MatchKind.NONE))).booleanValue();
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream peek(final DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new StatelessOp(this, StreamShape.DOUBLE_VALUE, 0) { // from class: j$.util.stream.DoublePipeline.8
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return new Sink.ChainedDouble(sink) { // from class: j$.util.stream.DoublePipeline.8.1
                    @Override // j$.util.stream.Sink.OfDouble, j$.util.stream.Sink, java.util.function.DoubleConsumer
                    public void accept(double d) {
                        doubleConsumer.accept(d);
                        this.downstream.accept(d);
                    }
                };
            }
        };
    }

    @Override // j$.util.stream.DoubleStream
    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return ((Double) evaluate(ReduceOps.makeDouble(d, doubleBinaryOperator))).doubleValue();
    }

    @Override // j$.util.stream.DoubleStream
    public final OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return (OptionalDouble) evaluate(ReduceOps.makeDouble(doubleBinaryOperator));
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream skip(long j) {
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i >= 0) {
            return i == 0 ? this : SliceOps.makeDouble(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleStream sorted() {
        return SortedOps.makeDouble(this);
    }

    @Override // j$.util.stream.AbstractPipeline, j$.util.stream.BaseStream
    public final Spliterator.OfDouble spliterator() {
        return adapt(super.spliterator());
    }

    @Override // j$.util.stream.DoubleStream
    public final double sum() {
        return Collectors.computeFinalSum((double[]) collect(new Supplier() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return DoublePipeline.lambda$sum$1();
            }
        }, new ObjDoubleConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda10
            @Override // java.util.function.ObjDoubleConsumer
            public final void accept(Object obj, double d) {
                DoublePipeline.lambda$sum$2((double[]) obj, d);
            }
        }, new BiConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                DoublePipeline.lambda$sum$3((double[]) obj, (double[]) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        }));
    }

    @Override // j$.util.stream.DoubleStream
    public final DoubleSummaryStatistics summaryStatistics() {
        return (DoubleSummaryStatistics) collect(new Supplier() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return new DoubleSummaryStatistics();
            }
        }, new ObjDoubleConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda7
            @Override // java.util.function.ObjDoubleConsumer
            public final void accept(Object obj, double d) {
                ((DoubleSummaryStatistics) obj).accept(d);
            }
        }, new BiConsumer() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((DoubleSummaryStatistics) obj).combine((DoubleSummaryStatistics) obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        });
    }

    @Override // j$.util.stream.DoubleStream
    public final double[] toArray() {
        return (double[]) Nodes.flattenDouble((Node.OfDouble) evaluateToArrayNode(new IntFunction() { // from class: j$.util.stream.DoublePipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return DoublePipeline.lambda$toArray$8(i);
            }
        })).asPrimitiveArray();
    }

    @Override // j$.util.stream.BaseStream
    public DoubleStream unordered() {
        return !isOrdered() ? this : new StatelessOp(this, StreamShape.DOUBLE_VALUE, StreamOpFlag.NOT_ORDERED) { // from class: j$.util.stream.DoublePipeline.6
            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // j$.util.stream.AbstractPipeline
            public Sink opWrapSink(int i, Sink sink) {
                return sink;
            }
        };
    }

    @Override // j$.util.stream.AbstractPipeline
    final Spliterator wrap(PipelineHelper pipelineHelper, Supplier supplier, boolean z) {
        return new StreamSpliterators$DoubleWrappingSpliterator(pipelineHelper, supplier, z);
    }
}
