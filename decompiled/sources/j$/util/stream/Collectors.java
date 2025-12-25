package j$.util.stream;

import j$.util.function.BiConsumer$CC;
import j$.util.function.BiFunction$CC;
import j$.util.function.Function$CC;
import j$.util.stream.Collector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
/* loaded from: classes2.dex */
public final class Collectors {
    static final Set CH_CONCURRENT_ID;
    static final Set CH_CONCURRENT_NOID;
    static final Set CH_ID;
    static final Set CH_NOID;
    static final Set CH_UNORDERED_ID;
    static final Set CH_UNORDERED_NOID;

    /* loaded from: classes2.dex */
    static class CollectorImpl implements Collector {
        private final BiConsumer accumulator;
        private final Set characteristics;
        private final BinaryOperator combiner;
        private final Function finisher;
        private final Supplier supplier;

        /* JADX INFO: Access modifiers changed from: package-private */
        public CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Set set) {
            this(supplier, biConsumer, binaryOperator, Collectors.m508$$Nest$smcastingIdentity(), set);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public CollectorImpl(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator, Function function, Set set) {
            this.supplier = supplier;
            this.accumulator = biConsumer;
            this.combiner = binaryOperator;
            this.finisher = function;
            this.characteristics = set;
        }

        @Override // j$.util.stream.Collector
        public BiConsumer accumulator() {
            return this.accumulator;
        }

        @Override // j$.util.stream.Collector
        public Set characteristics() {
            return this.characteristics;
        }

        @Override // j$.util.stream.Collector
        public BinaryOperator combiner() {
            return this.combiner;
        }

        @Override // j$.util.stream.Collector
        public Function finisher() {
            return this.finisher;
        }

        @Override // j$.util.stream.Collector
        public Supplier supplier() {
            return this.supplier;
        }
    }

    /* renamed from: -$$Nest$smcastingIdentity  reason: not valid java name */
    static /* bridge */ /* synthetic */ Function m508$$Nest$smcastingIdentity() {
        return castingIdentity();
    }

    static {
        Collector.Characteristics characteristics = Collector.Characteristics.CONCURRENT;
        Collector.Characteristics characteristics2 = Collector.Characteristics.UNORDERED;
        Collector.Characteristics characteristics3 = Collector.Characteristics.IDENTITY_FINISH;
        CH_CONCURRENT_ID = Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2, characteristics3));
        CH_CONCURRENT_NOID = Collections.unmodifiableSet(EnumSet.of(characteristics, characteristics2));
        CH_ID = Collections.unmodifiableSet(EnumSet.of(characteristics3));
        CH_UNORDERED_ID = Collections.unmodifiableSet(EnumSet.of(characteristics2, characteristics3));
        CH_NOID = Collections.emptySet();
        CH_UNORDERED_NOID = Collections.unmodifiableSet(EnumSet.of(characteristics2));
    }

    private static Function castingIdentity() {
        return new Function() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda15
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Collectors.lambda$castingIdentity$2(obj);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double computeFinalSum(double[] dArr) {
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (Double.isNaN(d) && Double.isInfinite(d2)) ? d2 : d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Object lambda$castingIdentity$2(Object obj) {
        return obj;
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
    public static /* synthetic */ java.util.List lambda$toList$4(java.util.List r0, java.util.List r1) {
        /*
            r0.addAll(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.Collectors.lambda$toList$4(java.util.List, java.util.List):java.util.List");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static double[] sumWithCompensation(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
        return dArr;
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new CollectorImpl(new Supplier() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda74
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ArrayList();
            }
        }, new BiConsumer() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda75
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                ((List) obj).add(obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        }, new BinaryOperator() { // from class: j$.util.stream.Collectors$$ExternalSyntheticLambda76
            public /* synthetic */ BiFunction andThen(Function function) {
                return BiFunction$CC.$default$andThen(this, function);
            }

            /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
                jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: j$.util.stream.Collectors.lambda$toList$4(java.util.List, java.util.List):java.util.List
                	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
                	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
                Caused by: java.lang.NullPointerException
                	at jadx.core.dex.instructions.args.RegisterArg.sameRegAndSVar(RegisterArg.java:173)
                	at jadx.core.dex.instructions.args.InsnArg.isSameVar(InsnArg.java:269)
                	at jadx.core.dex.visitors.MarkMethodsForInline.isSyntheticAccessPattern(MarkMethodsForInline.java:118)
                	at jadx.core.dex.visitors.MarkMethodsForInline.inlineMth(MarkMethodsForInline.java:86)
                	at jadx.core.dex.visitors.MarkMethodsForInline.process(MarkMethodsForInline.java:53)
                	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:65)
                	... 1 more
                */
            @Override // java.util.function.BiFunction
            public final java.lang.Object apply(java.lang.Object r1, java.lang.Object r2) {
                /*
                    r0 = this;
                    java.util.List r1 = (java.util.List) r1
                    java.util.List r2 = (java.util.List) r2
                    java.util.List r1 = j$.util.stream.Collectors.lambda$toList$4(r1, r2)
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: j$.util.stream.Collectors$$ExternalSyntheticLambda76.apply(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        }, CH_ID);
    }
}
