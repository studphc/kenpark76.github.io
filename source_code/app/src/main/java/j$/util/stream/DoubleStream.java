package j$.util.stream;

import j$.util.DoubleSummaryStatistics;
import j$.util.DoubleSummaryStatisticsConversions;
import j$.util.OptionalConversions;
import j$.util.OptionalDouble;
import j$.util.PrimitiveIterator;
import j$.util.Spliterator;
import j$.util.stream.BaseStream;
import j$.util.stream.IntStream;
import j$.util.stream.LongStream;
import j$.util.stream.Stream;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
/* loaded from: classes2.dex */
public interface DoubleStream extends BaseStream<Double, DoubleStream> {

    /* loaded from: classes2.dex */
    public final /* synthetic */ class VivifiedWrapper implements DoubleStream {
        public final /* synthetic */ java.util.stream.DoubleStream wrappedValue;

        private /* synthetic */ VivifiedWrapper(java.util.stream.DoubleStream doubleStream) {
            this.wrappedValue = doubleStream;
        }

        public static /* synthetic */ DoubleStream convert(java.util.stream.DoubleStream doubleStream) {
            if (doubleStream == null) {
                return null;
            }
            return doubleStream instanceof Wrapper ? DoubleStream.this : new VivifiedWrapper(doubleStream);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ boolean allMatch(DoublePredicate doublePredicate) {
            return this.wrappedValue.allMatch(doublePredicate);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ boolean anyMatch(DoublePredicate doublePredicate) {
            return this.wrappedValue.anyMatch(doublePredicate);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble average() {
            return OptionalConversions.convert(this.wrappedValue.average());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ Stream boxed() {
            return Stream.VivifiedWrapper.convert(this.wrappedValue.boxed());
        }

        @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
        public /* synthetic */ void close() {
            this.wrappedValue.close();
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
            return this.wrappedValue.collect(supplier, objDoubleConsumer, biConsumer);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ long count() {
            return this.wrappedValue.count();
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream distinct() {
            return convert(this.wrappedValue.distinct());
        }

        public /* synthetic */ boolean equals(Object obj) {
            java.util.stream.DoubleStream doubleStream = this.wrappedValue;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).wrappedValue;
            }
            return doubleStream.equals(obj);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream filter(DoublePredicate doublePredicate) {
            return convert(this.wrappedValue.filter(doublePredicate));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble findAny() {
            return OptionalConversions.convert(this.wrappedValue.findAny());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble findFirst() {
            return OptionalConversions.convert(this.wrappedValue.findFirst());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream flatMap(DoubleFunction doubleFunction) {
            return convert(this.wrappedValue.flatMap(FlatMapApiFlips.flipFunctionReturningStream(doubleFunction)));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ void forEach(DoubleConsumer doubleConsumer) {
            this.wrappedValue.forEach(doubleConsumer);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ void forEachOrdered(DoubleConsumer doubleConsumer) {
            this.wrappedValue.forEachOrdered(doubleConsumer);
        }

        public /* synthetic */ int hashCode() {
            return this.wrappedValue.hashCode();
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ boolean isParallel() {
            return this.wrappedValue.isParallel();
        }

        @Override // j$.util.stream.DoubleStream, j$.util.stream.BaseStream
        public /* synthetic */ Iterator<Double> iterator() {
            return PrimitiveIterator.OfDouble.VivifiedWrapper.convert(this.wrappedValue.iterator());
        }

        @Override // j$.util.stream.BaseStream
        /* renamed from: iterator */
        public /* synthetic */ Iterator<Double> iterator2() {
            return this.wrappedValue.iterator();
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream limit(long j) {
            return convert(this.wrappedValue.limit(j));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream map(DoubleUnaryOperator doubleUnaryOperator) {
            return convert(this.wrappedValue.map(doubleUnaryOperator));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ IntStream mapToInt(DoubleToIntFunction doubleToIntFunction) {
            return IntStream.VivifiedWrapper.convert(this.wrappedValue.mapToInt(doubleToIntFunction));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ LongStream mapToLong(DoubleToLongFunction doubleToLongFunction) {
            return LongStream.VivifiedWrapper.convert(this.wrappedValue.mapToLong(doubleToLongFunction));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ Stream mapToObj(DoubleFunction doubleFunction) {
            return Stream.VivifiedWrapper.convert(this.wrappedValue.mapToObj(doubleFunction));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble max() {
            return OptionalConversions.convert(this.wrappedValue.max());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble min() {
            return OptionalConversions.convert(this.wrappedValue.min());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ boolean noneMatch(DoublePredicate doublePredicate) {
            return this.wrappedValue.noneMatch(doublePredicate);
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream onClose(Runnable runnable) {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.onClose(runnable));
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream parallel() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.parallel());
        }

        @Override // j$.util.stream.DoubleStream, j$.util.stream.BaseStream
        public /* synthetic */ DoubleStream parallel() {
            return convert(this.wrappedValue.parallel());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream peek(DoubleConsumer doubleConsumer) {
            return convert(this.wrappedValue.peek(doubleConsumer));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
            return this.wrappedValue.reduce(d, doubleBinaryOperator);
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
            return OptionalConversions.convert(this.wrappedValue.reduce(doubleBinaryOperator));
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream sequential() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.sequential());
        }

        @Override // j$.util.stream.DoubleStream, j$.util.stream.BaseStream
        public /* synthetic */ DoubleStream sequential() {
            return convert(this.wrappedValue.sequential());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream skip(long j) {
            return convert(this.wrappedValue.skip(j));
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleStream sorted() {
            return convert(this.wrappedValue.sorted());
        }

        @Override // j$.util.stream.DoubleStream, j$.util.stream.BaseStream
        public /* synthetic */ Spliterator.OfDouble spliterator() {
            return Spliterator.OfDouble.VivifiedWrapper.convert(this.wrappedValue.spliterator());
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ Spliterator spliterator() {
            return Spliterator.VivifiedWrapper.convert(this.wrappedValue.spliterator());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ double sum() {
            return this.wrappedValue.sum();
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ DoubleSummaryStatistics summaryStatistics() {
            return DoubleSummaryStatisticsConversions.convert(this.wrappedValue.summaryStatistics());
        }

        @Override // j$.util.stream.DoubleStream
        public /* synthetic */ double[] toArray() {
            return this.wrappedValue.toArray();
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream unordered() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.unordered());
        }
    }

    /* loaded from: classes2.dex */
    public final /* synthetic */ class Wrapper implements java.util.stream.DoubleStream {
        private /* synthetic */ Wrapper() {
            DoubleStream.this = r1;
        }

        public static /* synthetic */ java.util.stream.DoubleStream convert(DoubleStream doubleStream) {
            if (doubleStream == null) {
                return null;
            }
            return doubleStream instanceof VivifiedWrapper ? ((VivifiedWrapper) doubleStream).wrappedValue : new Wrapper();
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ boolean allMatch(DoublePredicate doublePredicate) {
            return DoubleStream.this.allMatch(doublePredicate);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ boolean anyMatch(DoublePredicate doublePredicate) {
            return DoubleStream.this.anyMatch(doublePredicate);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble average() {
            return OptionalConversions.convert(DoubleStream.this.average());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.Stream boxed() {
            return Stream.Wrapper.convert(DoubleStream.this.boxed());
        }

        @Override // java.util.stream.BaseStream, java.lang.AutoCloseable
        public /* synthetic */ void close() {
            DoubleStream.this.close();
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
            return DoubleStream.this.collect(supplier, objDoubleConsumer, biConsumer);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ long count() {
            return DoubleStream.this.count();
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream distinct() {
            return convert(DoubleStream.this.distinct());
        }

        public /* synthetic */ boolean equals(Object obj) {
            DoubleStream doubleStream = DoubleStream.this;
            if (obj instanceof Wrapper) {
                obj = DoubleStream.this;
            }
            return doubleStream.equals(obj);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream filter(DoublePredicate doublePredicate) {
            return convert(DoubleStream.this.filter(doublePredicate));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble findAny() {
            return OptionalConversions.convert(DoubleStream.this.findAny());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble findFirst() {
            return OptionalConversions.convert(DoubleStream.this.findFirst());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream flatMap(DoubleFunction doubleFunction) {
            return convert(DoubleStream.this.flatMap(FlatMapApiFlips.flipFunctionReturningStream(doubleFunction)));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ void forEach(DoubleConsumer doubleConsumer) {
            DoubleStream.this.forEach(doubleConsumer);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ void forEachOrdered(DoubleConsumer doubleConsumer) {
            DoubleStream.this.forEachOrdered(doubleConsumer);
        }

        public /* synthetic */ int hashCode() {
            return DoubleStream.this.hashCode();
        }

        @Override // java.util.stream.BaseStream
        public /* synthetic */ boolean isParallel() {
            return DoubleStream.this.isParallel();
        }

        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        public /* synthetic */ Iterator<Double> iterator() {
            return DoubleStream.this.iterator();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [j$.util.PrimitiveIterator$OfDouble] */
        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        /* renamed from: iterator */
        public /* synthetic */ Iterator<Double> iterator2() {
            return PrimitiveIterator.OfDouble.Wrapper.convert(DoubleStream.this.iterator());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream limit(long j) {
            return convert(DoubleStream.this.limit(j));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream map(DoubleUnaryOperator doubleUnaryOperator) {
            return convert(DoubleStream.this.map(doubleUnaryOperator));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.IntStream mapToInt(DoubleToIntFunction doubleToIntFunction) {
            return IntStream.Wrapper.convert(DoubleStream.this.mapToInt(doubleToIntFunction));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.LongStream mapToLong(DoubleToLongFunction doubleToLongFunction) {
            return LongStream.Wrapper.convert(DoubleStream.this.mapToLong(doubleToLongFunction));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.Stream mapToObj(DoubleFunction doubleFunction) {
            return Stream.Wrapper.convert(DoubleStream.this.mapToObj(doubleFunction));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble max() {
            return OptionalConversions.convert(DoubleStream.this.max());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble min() {
            return OptionalConversions.convert(DoubleStream.this.min());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ boolean noneMatch(DoublePredicate doublePredicate) {
            return DoubleStream.this.noneMatch(doublePredicate);
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [java.util.stream.BaseStream, java.util.stream.DoubleStream] */
        @Override // java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.DoubleStream onClose(Runnable runnable) {
            return BaseStream.Wrapper.convert(DoubleStream.this.onClose(runnable));
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.BaseStream, java.util.stream.DoubleStream] */
        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.DoubleStream parallel() {
            return BaseStream.Wrapper.convert(DoubleStream.this.parallel());
        }

        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        /* renamed from: parallel */
        public /* synthetic */ java.util.stream.DoubleStream parallel2() {
            return convert(DoubleStream.this.parallel());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream peek(DoubleConsumer doubleConsumer) {
            return convert(DoubleStream.this.peek(doubleConsumer));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
            return DoubleStream.this.reduce(d, doubleBinaryOperator);
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator) {
            return OptionalConversions.convert(DoubleStream.this.reduce(doubleBinaryOperator));
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.BaseStream, java.util.stream.DoubleStream] */
        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.DoubleStream sequential() {
            return BaseStream.Wrapper.convert(DoubleStream.this.sequential());
        }

        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        /* renamed from: sequential */
        public /* synthetic */ java.util.stream.DoubleStream sequential2() {
            return convert(DoubleStream.this.sequential());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream skip(long j) {
            return convert(DoubleStream.this.skip(j));
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.stream.DoubleStream sorted() {
            return convert(DoubleStream.this.sorted());
        }

        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.Spliterator<Double> spliterator() {
            return Spliterator.OfDouble.Wrapper.convert(DoubleStream.this.spliterator());
        }

        @Override // java.util.stream.DoubleStream, java.util.stream.BaseStream
        /* renamed from: spliterator */
        public /* synthetic */ java.util.Spliterator<Double> spliterator2() {
            return Spliterator.Wrapper.convert(DoubleStream.this.spliterator());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ double sum() {
            return DoubleStream.this.sum();
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ java.util.DoubleSummaryStatistics summaryStatistics() {
            return DoubleSummaryStatisticsConversions.convert(DoubleStream.this.summaryStatistics());
        }

        @Override // java.util.stream.DoubleStream
        public /* synthetic */ double[] toArray() {
            return DoubleStream.this.toArray();
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.BaseStream, java.util.stream.DoubleStream] */
        @Override // java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.DoubleStream unordered() {
            return BaseStream.Wrapper.convert(DoubleStream.this.unordered());
        }
    }

    boolean allMatch(DoublePredicate doublePredicate);

    boolean anyMatch(DoublePredicate doublePredicate);

    OptionalDouble average();

    Stream boxed();

    Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer);

    long count();

    DoubleStream distinct();

    DoubleStream filter(DoublePredicate doublePredicate);

    OptionalDouble findAny();

    OptionalDouble findFirst();

    DoubleStream flatMap(DoubleFunction doubleFunction);

    void forEach(DoubleConsumer doubleConsumer);

    void forEachOrdered(DoubleConsumer doubleConsumer);

    @Override // j$.util.stream.BaseStream
    Iterator<Double> iterator();

    DoubleStream limit(long j);

    DoubleStream map(DoubleUnaryOperator doubleUnaryOperator);

    IntStream mapToInt(DoubleToIntFunction doubleToIntFunction);

    LongStream mapToLong(DoubleToLongFunction doubleToLongFunction);

    Stream mapToObj(DoubleFunction doubleFunction);

    OptionalDouble max();

    OptionalDouble min();

    boolean noneMatch(DoublePredicate doublePredicate);

    @Override // j$.util.stream.BaseStream
    DoubleStream parallel();

    DoubleStream peek(DoubleConsumer doubleConsumer);

    double reduce(double d, DoubleBinaryOperator doubleBinaryOperator);

    OptionalDouble reduce(DoubleBinaryOperator doubleBinaryOperator);

    @Override // j$.util.stream.BaseStream
    DoubleStream sequential();

    DoubleStream skip(long j);

    DoubleStream sorted();

    @Override // j$.util.stream.BaseStream
    Spliterator.OfDouble spliterator();

    double sum();

    DoubleSummaryStatistics summaryStatistics();

    double[] toArray();
}
