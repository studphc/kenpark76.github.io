package j$.util.stream;

import j$.util.LongSummaryStatistics;
import j$.util.LongSummaryStatisticsConversions;
import j$.util.OptionalConversions;
import j$.util.OptionalDouble;
import j$.util.OptionalLong;
import j$.util.PrimitiveIterator;
import j$.util.Spliterator;
import j$.util.stream.BaseStream;
import j$.util.stream.DoubleStream;
import j$.util.stream.IntStream;
import j$.util.stream.Stream;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
/* loaded from: classes2.dex */
public interface LongStream extends BaseStream<Long, LongStream> {

    /* loaded from: classes2.dex */
    public final /* synthetic */ class VivifiedWrapper implements LongStream {
        public final /* synthetic */ java.util.stream.LongStream wrappedValue;

        private /* synthetic */ VivifiedWrapper(java.util.stream.LongStream longStream) {
            this.wrappedValue = longStream;
        }

        public static /* synthetic */ LongStream convert(java.util.stream.LongStream longStream) {
            if (longStream == null) {
                return null;
            }
            return longStream instanceof Wrapper ? LongStream.this : new VivifiedWrapper(longStream);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ boolean allMatch(LongPredicate longPredicate) {
            return this.wrappedValue.allMatch(longPredicate);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ boolean anyMatch(LongPredicate longPredicate) {
            return this.wrappedValue.anyMatch(longPredicate);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ DoubleStream asDoubleStream() {
            return DoubleStream.VivifiedWrapper.convert(this.wrappedValue.asDoubleStream());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalDouble average() {
            return OptionalConversions.convert(this.wrappedValue.average());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ Stream boxed() {
            return Stream.VivifiedWrapper.convert(this.wrappedValue.boxed());
        }

        @Override // j$.util.stream.BaseStream, java.lang.AutoCloseable
        public /* synthetic */ void close() {
            this.wrappedValue.close();
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
            return this.wrappedValue.collect(supplier, objLongConsumer, biConsumer);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ long count() {
            return this.wrappedValue.count();
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream distinct() {
            return convert(this.wrappedValue.distinct());
        }

        public /* synthetic */ boolean equals(Object obj) {
            java.util.stream.LongStream longStream = this.wrappedValue;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).wrappedValue;
            }
            return longStream.equals(obj);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream filter(LongPredicate longPredicate) {
            return convert(this.wrappedValue.filter(longPredicate));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalLong findAny() {
            return OptionalConversions.convert(this.wrappedValue.findAny());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalLong findFirst() {
            return OptionalConversions.convert(this.wrappedValue.findFirst());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream flatMap(LongFunction longFunction) {
            return convert(this.wrappedValue.flatMap(FlatMapApiFlips.flipFunctionReturningStream(longFunction)));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ void forEach(LongConsumer longConsumer) {
            this.wrappedValue.forEach(longConsumer);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ void forEachOrdered(LongConsumer longConsumer) {
            this.wrappedValue.forEachOrdered(longConsumer);
        }

        public /* synthetic */ int hashCode() {
            return this.wrappedValue.hashCode();
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ boolean isParallel() {
            return this.wrappedValue.isParallel();
        }

        @Override // j$.util.stream.LongStream, j$.util.stream.BaseStream
        public /* synthetic */ Iterator<Long> iterator() {
            return PrimitiveIterator.OfLong.VivifiedWrapper.convert(this.wrappedValue.iterator());
        }

        @Override // j$.util.stream.BaseStream
        /* renamed from: iterator */
        public /* synthetic */ Iterator<Long> iterator2() {
            return this.wrappedValue.iterator();
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream limit(long j) {
            return convert(this.wrappedValue.limit(j));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream map(LongUnaryOperator longUnaryOperator) {
            return convert(this.wrappedValue.map(longUnaryOperator));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction) {
            return DoubleStream.VivifiedWrapper.convert(this.wrappedValue.mapToDouble(longToDoubleFunction));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ IntStream mapToInt(LongToIntFunction longToIntFunction) {
            return IntStream.VivifiedWrapper.convert(this.wrappedValue.mapToInt(longToIntFunction));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ Stream mapToObj(LongFunction longFunction) {
            return Stream.VivifiedWrapper.convert(this.wrappedValue.mapToObj(longFunction));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalLong max() {
            return OptionalConversions.convert(this.wrappedValue.max());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalLong min() {
            return OptionalConversions.convert(this.wrappedValue.min());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ boolean noneMatch(LongPredicate longPredicate) {
            return this.wrappedValue.noneMatch(longPredicate);
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream onClose(Runnable runnable) {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.onClose(runnable));
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream parallel() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.parallel());
        }

        @Override // j$.util.stream.LongStream, j$.util.stream.BaseStream
        public /* synthetic */ LongStream parallel() {
            return convert(this.wrappedValue.parallel());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream peek(LongConsumer longConsumer) {
            return convert(this.wrappedValue.peek(longConsumer));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ long reduce(long j, LongBinaryOperator longBinaryOperator) {
            return this.wrappedValue.reduce(j, longBinaryOperator);
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
            return OptionalConversions.convert(this.wrappedValue.reduce(longBinaryOperator));
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream sequential() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.sequential());
        }

        @Override // j$.util.stream.LongStream, j$.util.stream.BaseStream
        public /* synthetic */ LongStream sequential() {
            return convert(this.wrappedValue.sequential());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream skip(long j) {
            return convert(this.wrappedValue.skip(j));
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongStream sorted() {
            return convert(this.wrappedValue.sorted());
        }

        @Override // j$.util.stream.LongStream, j$.util.stream.BaseStream
        public /* synthetic */ Spliterator.OfLong spliterator() {
            return Spliterator.OfLong.VivifiedWrapper.convert(this.wrappedValue.spliterator());
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ Spliterator spliterator() {
            return Spliterator.VivifiedWrapper.convert(this.wrappedValue.spliterator());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ long sum() {
            return this.wrappedValue.sum();
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ LongSummaryStatistics summaryStatistics() {
            return LongSummaryStatisticsConversions.convert(this.wrappedValue.summaryStatistics());
        }

        @Override // j$.util.stream.LongStream
        public /* synthetic */ long[] toArray() {
            return this.wrappedValue.toArray();
        }

        @Override // j$.util.stream.BaseStream
        public /* synthetic */ BaseStream unordered() {
            return BaseStream.VivifiedWrapper.convert(this.wrappedValue.unordered());
        }
    }

    /* loaded from: classes2.dex */
    public final /* synthetic */ class Wrapper implements java.util.stream.LongStream {
        private /* synthetic */ Wrapper() {
            LongStream.this = r1;
        }

        public static /* synthetic */ java.util.stream.LongStream convert(LongStream longStream) {
            if (longStream == null) {
                return null;
            }
            return longStream instanceof VivifiedWrapper ? ((VivifiedWrapper) longStream).wrappedValue : new Wrapper();
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ boolean allMatch(LongPredicate longPredicate) {
            return LongStream.this.allMatch(longPredicate);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ boolean anyMatch(LongPredicate longPredicate) {
            return LongStream.this.anyMatch(longPredicate);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.DoubleStream asDoubleStream() {
            return DoubleStream.Wrapper.convert(LongStream.this.asDoubleStream());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalDouble average() {
            return OptionalConversions.convert(LongStream.this.average());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.Stream boxed() {
            return Stream.Wrapper.convert(LongStream.this.boxed());
        }

        @Override // java.util.stream.BaseStream, java.lang.AutoCloseable
        public /* synthetic */ void close() {
            LongStream.this.close();
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
            return LongStream.this.collect(supplier, objLongConsumer, biConsumer);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ long count() {
            return LongStream.this.count();
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream distinct() {
            return convert(LongStream.this.distinct());
        }

        public /* synthetic */ boolean equals(Object obj) {
            LongStream longStream = LongStream.this;
            if (obj instanceof Wrapper) {
                obj = LongStream.this;
            }
            return longStream.equals(obj);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream filter(LongPredicate longPredicate) {
            return convert(LongStream.this.filter(longPredicate));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalLong findAny() {
            return OptionalConversions.convert(LongStream.this.findAny());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalLong findFirst() {
            return OptionalConversions.convert(LongStream.this.findFirst());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream flatMap(LongFunction longFunction) {
            return convert(LongStream.this.flatMap(FlatMapApiFlips.flipFunctionReturningStream(longFunction)));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ void forEach(LongConsumer longConsumer) {
            LongStream.this.forEach(longConsumer);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ void forEachOrdered(LongConsumer longConsumer) {
            LongStream.this.forEachOrdered(longConsumer);
        }

        public /* synthetic */ int hashCode() {
            return LongStream.this.hashCode();
        }

        @Override // java.util.stream.BaseStream
        public /* synthetic */ boolean isParallel() {
            return LongStream.this.isParallel();
        }

        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        public /* synthetic */ Iterator<Long> iterator() {
            return LongStream.this.iterator();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [j$.util.PrimitiveIterator$OfLong] */
        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        /* renamed from: iterator */
        public /* synthetic */ Iterator<Long> iterator2() {
            return PrimitiveIterator.OfLong.Wrapper.convert(LongStream.this.iterator());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream limit(long j) {
            return convert(LongStream.this.limit(j));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream map(LongUnaryOperator longUnaryOperator) {
            return convert(LongStream.this.map(longUnaryOperator));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction) {
            return DoubleStream.Wrapper.convert(LongStream.this.mapToDouble(longToDoubleFunction));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.IntStream mapToInt(LongToIntFunction longToIntFunction) {
            return IntStream.Wrapper.convert(LongStream.this.mapToInt(longToIntFunction));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.Stream mapToObj(LongFunction longFunction) {
            return Stream.Wrapper.convert(LongStream.this.mapToObj(longFunction));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalLong max() {
            return OptionalConversions.convert(LongStream.this.max());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalLong min() {
            return OptionalConversions.convert(LongStream.this.min());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ boolean noneMatch(LongPredicate longPredicate) {
            return LongStream.this.noneMatch(longPredicate);
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [java.util.stream.LongStream, java.util.stream.BaseStream] */
        @Override // java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.LongStream onClose(Runnable runnable) {
            return BaseStream.Wrapper.convert(LongStream.this.onClose(runnable));
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.LongStream, java.util.stream.BaseStream] */
        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.LongStream parallel() {
            return BaseStream.Wrapper.convert(LongStream.this.parallel());
        }

        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        /* renamed from: parallel */
        public /* synthetic */ java.util.stream.LongStream parallel2() {
            return convert(LongStream.this.parallel());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream peek(LongConsumer longConsumer) {
            return convert(LongStream.this.peek(longConsumer));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ long reduce(long j, LongBinaryOperator longBinaryOperator) {
            return LongStream.this.reduce(j, longBinaryOperator);
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.OptionalLong reduce(LongBinaryOperator longBinaryOperator) {
            return OptionalConversions.convert(LongStream.this.reduce(longBinaryOperator));
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.LongStream, java.util.stream.BaseStream] */
        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.LongStream sequential() {
            return BaseStream.Wrapper.convert(LongStream.this.sequential());
        }

        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        /* renamed from: sequential */
        public /* synthetic */ java.util.stream.LongStream sequential2() {
            return convert(LongStream.this.sequential());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream skip(long j) {
            return convert(LongStream.this.skip(j));
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.stream.LongStream sorted() {
            return convert(LongStream.this.sorted());
        }

        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        public /* synthetic */ java.util.Spliterator<Long> spliterator() {
            return Spliterator.OfLong.Wrapper.convert(LongStream.this.spliterator());
        }

        @Override // java.util.stream.LongStream, java.util.stream.BaseStream
        /* renamed from: spliterator */
        public /* synthetic */ java.util.Spliterator<Long> spliterator2() {
            return Spliterator.Wrapper.convert(LongStream.this.spliterator());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ long sum() {
            return LongStream.this.sum();
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ java.util.LongSummaryStatistics summaryStatistics() {
            return LongSummaryStatisticsConversions.convert(LongStream.this.summaryStatistics());
        }

        @Override // java.util.stream.LongStream
        public /* synthetic */ long[] toArray() {
            return LongStream.this.toArray();
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.util.stream.LongStream, java.util.stream.BaseStream] */
        @Override // java.util.stream.BaseStream
        public /* synthetic */ java.util.stream.LongStream unordered() {
            return BaseStream.Wrapper.convert(LongStream.this.unordered());
        }
    }

    boolean allMatch(LongPredicate longPredicate);

    boolean anyMatch(LongPredicate longPredicate);

    DoubleStream asDoubleStream();

    OptionalDouble average();

    Stream boxed();

    Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer);

    long count();

    LongStream distinct();

    LongStream filter(LongPredicate longPredicate);

    OptionalLong findAny();

    OptionalLong findFirst();

    LongStream flatMap(LongFunction longFunction);

    void forEach(LongConsumer longConsumer);

    void forEachOrdered(LongConsumer longConsumer);

    @Override // j$.util.stream.BaseStream
    Iterator<Long> iterator();

    LongStream limit(long j);

    LongStream map(LongUnaryOperator longUnaryOperator);

    DoubleStream mapToDouble(LongToDoubleFunction longToDoubleFunction);

    IntStream mapToInt(LongToIntFunction longToIntFunction);

    Stream mapToObj(LongFunction longFunction);

    OptionalLong max();

    OptionalLong min();

    boolean noneMatch(LongPredicate longPredicate);

    @Override // j$.util.stream.BaseStream
    LongStream parallel();

    LongStream peek(LongConsumer longConsumer);

    long reduce(long j, LongBinaryOperator longBinaryOperator);

    OptionalLong reduce(LongBinaryOperator longBinaryOperator);

    @Override // j$.util.stream.BaseStream
    LongStream sequential();

    LongStream skip(long j);

    LongStream sorted();

    @Override // j$.util.stream.BaseStream
    Spliterator.OfLong spliterator();

    long sum();

    LongSummaryStatistics summaryStatistics();

    long[] toArray();
}
