package j$.util.stream;

import j$.util.Objects;
import j$.util.Optional;
import j$.util.OptionalDouble;
import j$.util.OptionalInt;
import j$.util.OptionalLong;
import j$.util.Spliterator;
import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import j$.util.stream.Collector;
import j$.util.stream.Sink;
import j$.util.stream.TerminalOp;
import java.util.concurrent.CountedCompleter;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
/* loaded from: classes2.dex */
abstract class ReduceOps {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$10ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C10ReducingSink extends Box implements AccumulatingSink, Sink.OfLong {
        final /* synthetic */ ObjLongConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        C10ReducingSink(Supplier supplier, ObjLongConsumer objLongConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objLongConsumer;
            this.val$combiner = binaryOperator;
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
        public void accept(long j) {
            this.val$accumulator.accept(this.state, j);
        }

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C10ReducingSink c10ReducingSink) {
            this.state = this.val$combiner.apply(this.state, c10ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$11ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C11ReducingSink implements AccumulatingSink, Sink.OfDouble {
        private double state;
        final /* synthetic */ double val$identity;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        C11ReducingSink(double d, DoubleBinaryOperator doubleBinaryOperator) {
            this.val$identity = d;
            this.val$operator = doubleBinaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public void accept(double d) {
            this.state = this.val$operator.applyAsDouble(this.state, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$identity;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C11ReducingSink c11ReducingSink) {
            accept(c11ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public Double get() {
            return Double.valueOf(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$12ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C12ReducingSink implements AccumulatingSink, Sink.OfDouble {
        private boolean empty;
        private double state;
        final /* synthetic */ DoubleBinaryOperator val$operator;

        C12ReducingSink(DoubleBinaryOperator doubleBinaryOperator) {
            this.val$operator = doubleBinaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public void accept(double d) {
            if (this.empty) {
                this.empty = false;
            } else {
                d = this.val$operator.applyAsDouble(this.state, d);
            }
            this.state = d;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.empty = true;
            this.state = 0.0d;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C12ReducingSink c12ReducingSink) {
            if (c12ReducingSink.empty) {
                return;
            }
            accept(c12ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public OptionalDouble get() {
            return this.empty ? OptionalDouble.empty() : OptionalDouble.of(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$13ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C13ReducingSink extends Box implements AccumulatingSink, Sink.OfDouble {
        final /* synthetic */ ObjDoubleConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        C13ReducingSink(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objDoubleConsumer;
            this.val$combiner = binaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public void accept(double d) {
            this.val$accumulator.accept(this.state, d);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(int i) {
            Sink.CC.$default$accept((Sink) this, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfDouble
        public /* synthetic */ void accept(Double d) {
            Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfDouble.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C13ReducingSink c13ReducingSink) {
            this.state = this.val$combiner.apply(this.state, c13ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$1ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C1ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ BiFunction val$reducer;
        final /* synthetic */ Object val$seed;

        C1ReducingSink(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
            this.val$seed = obj;
            this.val$reducer = biFunction;
            this.val$combiner = binaryOperator;
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
            this.state = this.val$reducer.apply(this.state, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$seed;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C1ReducingSink c1ReducingSink) {
            this.state = this.val$combiner.apply(this.state, c1ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$3ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C3ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        C3ReducingSink(Supplier supplier, BiConsumer biConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = biConsumer;
            this.val$combiner = binaryOperator;
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
            this.val$accumulator.accept(this.state, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C3ReducingSink c3ReducingSink) {
            this.state = this.val$combiner.apply(this.state, c3ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$4ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C4ReducingSink extends Box implements AccumulatingSink {
        final /* synthetic */ BiConsumer val$accumulator;
        final /* synthetic */ BiConsumer val$reducer;
        final /* synthetic */ Supplier val$seedFactory;

        C4ReducingSink(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
            this.val$seedFactory = supplier;
            this.val$accumulator = biConsumer;
            this.val$reducer = biConsumer2;
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
            this.val$accumulator.accept(this.state, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$seedFactory.get();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C4ReducingSink c4ReducingSink) {
            this.val$reducer.accept(this.state, c4ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$5ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C5ReducingSink implements AccumulatingSink, Sink.OfInt {
        private int state;
        final /* synthetic */ int val$identity;
        final /* synthetic */ IntBinaryOperator val$operator;

        C5ReducingSink(int i, IntBinaryOperator intBinaryOperator) {
            this.val$identity = i;
            this.val$operator = intBinaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public void accept(int i) {
            this.state = this.val$operator.applyAsInt(this.state, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$identity;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C5ReducingSink c5ReducingSink) {
            accept(c5ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public Integer get() {
            return Integer.valueOf(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$6ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C6ReducingSink implements AccumulatingSink, Sink.OfInt {
        private boolean empty;
        private int state;
        final /* synthetic */ IntBinaryOperator val$operator;

        C6ReducingSink(IntBinaryOperator intBinaryOperator) {
            this.val$operator = intBinaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public void accept(int i) {
            if (this.empty) {
                this.empty = false;
            } else {
                i = this.val$operator.applyAsInt(this.state, i);
            }
            this.state = i;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.empty = true;
            this.state = 0;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C6ReducingSink c6ReducingSink) {
            if (c6ReducingSink.empty) {
                return;
            }
            accept(c6ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public OptionalInt get() {
            return this.empty ? OptionalInt.empty() : OptionalInt.of(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$7ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C7ReducingSink extends Box implements AccumulatingSink, Sink.OfInt {
        final /* synthetic */ ObjIntConsumer val$accumulator;
        final /* synthetic */ BinaryOperator val$combiner;
        final /* synthetic */ Supplier val$supplier;

        C7ReducingSink(Supplier supplier, ObjIntConsumer objIntConsumer, BinaryOperator binaryOperator) {
            this.val$supplier = supplier;
            this.val$accumulator = objIntConsumer;
            this.val$combiner = binaryOperator;
        }

        @Override // j$.util.stream.Sink, java.util.function.DoubleConsumer
        public /* synthetic */ void accept(double d) {
            Sink.CC.$default$accept(this, d);
        }

        @Override // j$.util.stream.Sink
        public void accept(int i) {
            this.val$accumulator.accept(this.state, i);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void accept(long j) {
            Sink.CC.$default$accept((Sink) this, j);
        }

        @Override // j$.util.stream.Sink.OfInt
        public /* synthetic */ void accept(Integer num) {
            Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfInt.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$supplier.get();
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C7ReducingSink c7ReducingSink) {
            this.state = this.val$combiner.apply(this.state, c7ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$8ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C8ReducingSink implements AccumulatingSink, Sink.OfLong {
        private long state;
        final /* synthetic */ long val$identity;
        final /* synthetic */ LongBinaryOperator val$operator;

        C8ReducingSink(long j, LongBinaryOperator longBinaryOperator) {
            this.val$identity = j;
            this.val$operator = longBinaryOperator;
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
        public void accept(long j) {
            this.state = this.val$operator.applyAsLong(this.state, j);
        }

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.state = this.val$identity;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C8ReducingSink c8ReducingSink) {
            accept(c8ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public Long get() {
            return Long.valueOf(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: j$.util.stream.ReduceOps$9ReducingSink  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class C9ReducingSink implements AccumulatingSink, Sink.OfLong {
        private boolean empty;
        private long state;
        final /* synthetic */ LongBinaryOperator val$operator;

        C9ReducingSink(LongBinaryOperator longBinaryOperator) {
            this.val$operator = longBinaryOperator;
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
        public void accept(long j) {
            if (this.empty) {
                this.empty = false;
            } else {
                j = this.val$operator.applyAsLong(this.state, j);
            }
            this.state = j;
        }

        @Override // j$.util.stream.Sink.OfLong
        public /* synthetic */ void accept(Long l) {
            Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
        }

        @Override // java.util.function.Consumer
        public /* bridge */ /* synthetic */ void accept(Object obj) {
            Sink.OfLong.CC.$default$accept(this, obj);
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.empty = true;
            this.state = 0L;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        @Override // j$.util.stream.ReduceOps.AccumulatingSink
        public void combine(C9ReducingSink c9ReducingSink) {
            if (c9ReducingSink.empty) {
                return;
            }
            accept(c9ReducingSink.state);
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // java.util.function.Supplier
        public OptionalLong get() {
            return this.empty ? OptionalLong.empty() : OptionalLong.of(this.state);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public interface AccumulatingSink extends TerminalSink {
        void combine(AccumulatingSink accumulatingSink);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class Box {
        Object state;

        Box() {
        }

        public Object get() {
            return this.state;
        }
    }

    /* loaded from: classes2.dex */
    static abstract class CountingSink extends Box implements AccumulatingSink {
        long count;

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfDouble extends CountingSink implements Sink.OfDouble {
            OfDouble() {
            }

            @Override // j$.util.stream.ReduceOps.CountingSink, j$.util.stream.Sink, java.util.function.DoubleConsumer
            public void accept(double d) {
                this.count++;
            }

            @Override // j$.util.stream.Sink.OfDouble
            public /* synthetic */ void accept(Double d) {
                Sink.OfDouble.CC.$default$accept((Sink.OfDouble) this, d);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfDouble.CC.$default$accept(this, obj);
            }

            public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
                return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
            }

            @Override // j$.util.stream.ReduceOps.AccumulatingSink
            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            @Override // j$.util.stream.ReduceOps.Box, java.util.function.Supplier
            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfInt extends CountingSink implements Sink.OfInt {
            OfInt() {
            }

            @Override // j$.util.stream.ReduceOps.CountingSink, j$.util.stream.Sink
            public void accept(int i) {
                this.count++;
            }

            @Override // j$.util.stream.Sink.OfInt
            public /* synthetic */ void accept(Integer num) {
                Sink.OfInt.CC.$default$accept((Sink.OfInt) this, num);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfInt.CC.$default$accept(this, obj);
            }

            public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                return IntConsumer$CC.$default$andThen(this, intConsumer);
            }

            @Override // j$.util.stream.ReduceOps.AccumulatingSink
            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            @Override // j$.util.stream.ReduceOps.Box, java.util.function.Supplier
            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfLong extends CountingSink implements Sink.OfLong {
            OfLong() {
            }

            @Override // j$.util.stream.ReduceOps.CountingSink, j$.util.stream.Sink
            public void accept(long j) {
                this.count++;
            }

            @Override // j$.util.stream.Sink.OfLong
            public /* synthetic */ void accept(Long l) {
                Sink.OfLong.CC.$default$accept((Sink.OfLong) this, l);
            }

            @Override // java.util.function.Consumer
            public /* bridge */ /* synthetic */ void accept(Object obj) {
                Sink.OfLong.CC.$default$accept(this, obj);
            }

            public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
                return LongConsumer$CC.$default$andThen(this, longConsumer);
            }

            @Override // j$.util.stream.ReduceOps.AccumulatingSink
            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            @Override // j$.util.stream.ReduceOps.Box, java.util.function.Supplier
            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static final class OfRef extends CountingSink {
            OfRef() {
            }

            @Override // java.util.function.Consumer
            public void accept(Object obj) {
                this.count++;
            }

            @Override // j$.util.stream.ReduceOps.AccumulatingSink
            public /* bridge */ /* synthetic */ void combine(AccumulatingSink accumulatingSink) {
                super.combine((CountingSink) accumulatingSink);
            }

            @Override // j$.util.stream.ReduceOps.Box, java.util.function.Supplier
            public /* bridge */ /* synthetic */ Object get() {
                return super.get();
            }
        }

        CountingSink() {
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

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // j$.util.stream.Sink
        public void begin(long j) {
            this.count = 0L;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ boolean cancellationRequested() {
            return Sink.CC.$default$cancellationRequested(this);
        }

        public void combine(CountingSink countingSink) {
            this.count += countingSink.count;
        }

        @Override // j$.util.stream.Sink
        public /* synthetic */ void end() {
            Sink.CC.$default$end(this);
        }

        @Override // j$.util.stream.ReduceOps.Box, java.util.function.Supplier
        public Long get() {
            return Long.valueOf(this.count);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static abstract class ReduceOp implements TerminalOp {
        private final StreamShape inputShape;

        ReduceOp(StreamShape streamShape) {
            this.inputShape = streamShape;
        }

        @Override // j$.util.stream.TerminalOp
        public Object evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return ((AccumulatingSink) new ReduceTask(this, pipelineHelper, spliterator).invoke()).get();
        }

        @Override // j$.util.stream.TerminalOp
        public Object evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
            return ((AccumulatingSink) pipelineHelper.wrapAndCopyInto(makeSink(), spliterator)).get();
        }

        @Override // j$.util.stream.TerminalOp
        public /* synthetic */ int getOpFlags() {
            return TerminalOp.CC.$default$getOpFlags(this);
        }

        public abstract AccumulatingSink makeSink();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class ReduceTask extends AbstractTask {
        private final ReduceOp op;

        ReduceTask(ReduceOp reduceOp, PipelineHelper pipelineHelper, Spliterator spliterator) {
            super(pipelineHelper, spliterator);
            this.op = reduceOp;
        }

        ReduceTask(ReduceTask reduceTask, Spliterator spliterator) {
            super(reduceTask, spliterator);
            this.op = reduceTask.op;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public AccumulatingSink doLeaf() {
            return (AccumulatingSink) this.helper.wrapAndCopyInto(this.op.makeSink(), this.spliterator);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // j$.util.stream.AbstractTask
        public ReduceTask makeChild(Spliterator spliterator) {
            return new ReduceTask(this, spliterator);
        }

        @Override // j$.util.stream.AbstractTask, java.util.concurrent.CountedCompleter
        public void onCompletion(CountedCompleter countedCompleter) {
            if (!isLeaf()) {
                AccumulatingSink accumulatingSink = (AccumulatingSink) ((ReduceTask) this.leftChild).getLocalResult();
                accumulatingSink.combine((AccumulatingSink) ((ReduceTask) this.rightChild).getLocalResult());
                setLocalResult(accumulatingSink);
            }
            super.onCompletion(countedCompleter);
        }
    }

    public static TerminalOp makeDouble(final double d, final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new ReduceOp(StreamShape.DOUBLE_VALUE) { // from class: j$.util.stream.ReduceOps.14
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C11ReducingSink makeSink() {
                return new C11ReducingSink(d, doubleBinaryOperator);
            }
        };
    }

    public static TerminalOp makeDouble(final DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return new ReduceOp(StreamShape.DOUBLE_VALUE) { // from class: j$.util.stream.ReduceOps.15
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C12ReducingSink makeSink() {
                return new C12ReducingSink(doubleBinaryOperator);
            }
        };
    }

    public static TerminalOp makeDouble(final Supplier supplier, final ObjDoubleConsumer objDoubleConsumer, final BinaryOperator binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp(StreamShape.DOUBLE_VALUE) { // from class: j$.util.stream.ReduceOps.16
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C13ReducingSink makeSink() {
                return new C13ReducingSink(supplier, objDoubleConsumer, binaryOperator);
            }
        };
    }

    public static TerminalOp makeDoubleCounting() {
        return new ReduceOp(StreamShape.DOUBLE_VALUE) { // from class: j$.util.stream.ReduceOps.17
            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateParallel(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateSequential(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public int getOpFlags() {
                return StreamOpFlag.NOT_ORDERED;
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp
            public CountingSink makeSink() {
                return new CountingSink.OfDouble();
            }
        };
    }

    public static TerminalOp makeInt(final int i, final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new ReduceOp(StreamShape.INT_VALUE) { // from class: j$.util.stream.ReduceOps.6
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C5ReducingSink makeSink() {
                return new C5ReducingSink(i, intBinaryOperator);
            }
        };
    }

    public static TerminalOp makeInt(final IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return new ReduceOp(StreamShape.INT_VALUE) { // from class: j$.util.stream.ReduceOps.7
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C6ReducingSink makeSink() {
                return new C6ReducingSink(intBinaryOperator);
            }
        };
    }

    public static TerminalOp makeInt(final Supplier supplier, final ObjIntConsumer objIntConsumer, final BinaryOperator binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp(StreamShape.INT_VALUE) { // from class: j$.util.stream.ReduceOps.8
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C7ReducingSink makeSink() {
                return new C7ReducingSink(supplier, objIntConsumer, binaryOperator);
            }
        };
    }

    public static TerminalOp makeIntCounting() {
        return new ReduceOp(StreamShape.INT_VALUE) { // from class: j$.util.stream.ReduceOps.9
            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateParallel(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateSequential(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public int getOpFlags() {
                return StreamOpFlag.NOT_ORDERED;
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp
            public CountingSink makeSink() {
                return new CountingSink.OfInt();
            }
        };
    }

    public static TerminalOp makeLong(final long j, final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new ReduceOp(StreamShape.LONG_VALUE) { // from class: j$.util.stream.ReduceOps.10
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C8ReducingSink makeSink() {
                return new C8ReducingSink(j, longBinaryOperator);
            }
        };
    }

    public static TerminalOp makeLong(final LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return new ReduceOp(StreamShape.LONG_VALUE) { // from class: j$.util.stream.ReduceOps.11
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C9ReducingSink makeSink() {
                return new C9ReducingSink(longBinaryOperator);
            }
        };
    }

    public static TerminalOp makeLong(final Supplier supplier, final ObjLongConsumer objLongConsumer, final BinaryOperator binaryOperator) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp(StreamShape.LONG_VALUE) { // from class: j$.util.stream.ReduceOps.12
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C10ReducingSink makeSink() {
                return new C10ReducingSink(supplier, objLongConsumer, binaryOperator);
            }
        };
    }

    public static TerminalOp makeLongCounting() {
        return new ReduceOp(StreamShape.LONG_VALUE) { // from class: j$.util.stream.ReduceOps.13
            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateParallel(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateSequential(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public int getOpFlags() {
                return StreamOpFlag.NOT_ORDERED;
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp
            public CountingSink makeSink() {
                return new CountingSink.OfLong();
            }
        };
    }

    public static TerminalOp makeRef(final Collector collector) {
        final Supplier supplier = ((Collector) Objects.requireNonNull(collector)).supplier();
        final BiConsumer accumulator = collector.accumulator();
        final BinaryOperator combiner = collector.combiner();
        return new ReduceOp(StreamShape.REFERENCE) { // from class: j$.util.stream.ReduceOps.3
            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public int getOpFlags() {
                if (collector.characteristics().contains(Collector.Characteristics.UNORDERED)) {
                    return StreamOpFlag.NOT_ORDERED;
                }
                return 0;
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C3ReducingSink makeSink() {
                return new C3ReducingSink(supplier, accumulator, combiner);
            }
        };
    }

    public static TerminalOp makeRef(final Object obj, final BiFunction biFunction, final BinaryOperator binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp(StreamShape.REFERENCE) { // from class: j$.util.stream.ReduceOps.1
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C1ReducingSink makeSink() {
                return new C1ReducingSink(obj, biFunction, binaryOperator);
            }
        };
    }

    public static TerminalOp makeRef(final BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return new ReduceOp(StreamShape.REFERENCE) { // from class: j$.util.stream.ReduceOps.2
            /* JADX WARN: Type inference failed for: r0v0, types: [j$.util.stream.ReduceOps$2ReducingSink] */
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C2ReducingSink makeSink() {
                final BinaryOperator binaryOperator2 = binaryOperator;
                return new AccumulatingSink() { // from class: j$.util.stream.ReduceOps.2ReducingSink
                    private boolean empty;
                    private Object state;

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
                        if (this.empty) {
                            this.empty = false;
                        } else {
                            obj = BinaryOperator.this.apply(this.state, obj);
                        }
                        this.state = obj;
                    }

                    public /* synthetic */ Consumer andThen(Consumer consumer) {
                        return Consumer$CC.$default$andThen(this, consumer);
                    }

                    @Override // j$.util.stream.Sink
                    public void begin(long j) {
                        this.empty = true;
                        this.state = null;
                    }

                    @Override // j$.util.stream.Sink
                    public /* synthetic */ boolean cancellationRequested() {
                        return Sink.CC.$default$cancellationRequested(this);
                    }

                    @Override // j$.util.stream.ReduceOps.AccumulatingSink
                    public void combine(C2ReducingSink c2ReducingSink) {
                        if (c2ReducingSink.empty) {
                            return;
                        }
                        accept(c2ReducingSink.state);
                    }

                    @Override // j$.util.stream.Sink
                    public /* synthetic */ void end() {
                        Sink.CC.$default$end(this);
                    }

                    @Override // java.util.function.Supplier
                    public Optional get() {
                        return this.empty ? Optional.empty() : Optional.of(this.state);
                    }
                };
            }
        };
    }

    public static TerminalOp makeRef(final Supplier supplier, final BiConsumer biConsumer, final BiConsumer biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return new ReduceOp(StreamShape.REFERENCE) { // from class: j$.util.stream.ReduceOps.4
            @Override // j$.util.stream.ReduceOps.ReduceOp
            public C4ReducingSink makeSink() {
                return new C4ReducingSink(supplier, biConsumer, biConsumer2);
            }
        };
    }

    public static TerminalOp makeRefCounting() {
        return new ReduceOp(StreamShape.REFERENCE) { // from class: j$.util.stream.ReduceOps.5
            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateParallel(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateParallel(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public Long evaluateSequential(PipelineHelper pipelineHelper, Spliterator spliterator) {
                return StreamOpFlag.SIZED.isKnown(pipelineHelper.getStreamAndOpFlags()) ? Long.valueOf(spliterator.getExactSizeIfKnown()) : (Long) super.evaluateSequential(pipelineHelper, spliterator);
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp, j$.util.stream.TerminalOp
            public int getOpFlags() {
                return StreamOpFlag.NOT_ORDERED;
            }

            @Override // j$.util.stream.ReduceOps.ReduceOp
            public CountingSink makeSink() {
                return new CountingSink.OfRef();
            }
        };
    }
}
