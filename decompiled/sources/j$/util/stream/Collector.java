package j$.util.stream;

import j$.util.Objects;
import j$.util.stream.Collectors;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
/* loaded from: classes2.dex */
public interface Collector<T, A, R> {

    /* renamed from: j$.util.stream.Collector$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static <T, A, R> Collector<T, A, R> of(Supplier<A> supplier, BiConsumer<A, T> biConsumer, BinaryOperator<A> binaryOperator, Function<A, R> function, Characteristics... characteristicsArr) {
            Objects.requireNonNull(supplier);
            Objects.requireNonNull(biConsumer);
            Objects.requireNonNull(binaryOperator);
            Objects.requireNonNull(function);
            Objects.requireNonNull(characteristicsArr);
            Set set = Collectors.CH_NOID;
            if (characteristicsArr.length > 0) {
                EnumSet noneOf = EnumSet.noneOf(Characteristics.class);
                Collections.addAll(noneOf, characteristicsArr);
                set = Collections.unmodifiableSet(noneOf);
            }
            return new Collectors.CollectorImpl(supplier, biConsumer, binaryOperator, function, set);
        }

        public static <T, R> Collector<T, R, R> of(Supplier<R> supplier, BiConsumer<R, T> biConsumer, BinaryOperator<R> binaryOperator, Characteristics... characteristicsArr) {
            Objects.requireNonNull(supplier);
            Objects.requireNonNull(biConsumer);
            Objects.requireNonNull(binaryOperator);
            Objects.requireNonNull(characteristicsArr);
            return new Collectors.CollectorImpl(supplier, biConsumer, binaryOperator, characteristicsArr.length == 0 ? Collectors.CH_ID : Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH, characteristicsArr)));
        }
    }

    /* loaded from: classes2.dex */
    public enum Characteristics {
        CONCURRENT,
        UNORDERED,
        IDENTITY_FINISH;

        /* loaded from: classes2.dex */
        public abstract /* synthetic */ class EnumConversion {
            public static /* synthetic */ Characteristics convert(Collector.Characteristics characteristics) {
                if (characteristics == null) {
                    return null;
                }
                return characteristics == Collector.Characteristics.CONCURRENT ? Characteristics.CONCURRENT : characteristics == Collector.Characteristics.UNORDERED ? Characteristics.UNORDERED : Characteristics.IDENTITY_FINISH;
            }

            public static /* synthetic */ Collector.Characteristics convert(Characteristics characteristics) {
                if (characteristics == null) {
                    return null;
                }
                return characteristics == Characteristics.CONCURRENT ? Collector.Characteristics.CONCURRENT : characteristics == Characteristics.UNORDERED ? Collector.Characteristics.UNORDERED : Collector.Characteristics.IDENTITY_FINISH;
            }
        }
    }

    /* loaded from: classes2.dex */
    public final /* synthetic */ class VivifiedWrapper implements Collector {
        public final /* synthetic */ java.util.stream.Collector wrappedValue;

        private /* synthetic */ VivifiedWrapper(java.util.stream.Collector collector) {
            this.wrappedValue = collector;
        }

        public static /* synthetic */ Collector convert(java.util.stream.Collector collector) {
            if (collector == null) {
                return null;
            }
            return collector instanceof Wrapper ? Collector.this : new VivifiedWrapper(collector);
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ BiConsumer accumulator() {
            return this.wrappedValue.accumulator();
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Set characteristics() {
            return StreamApiFlips.flipCharacteristicSet(this.wrappedValue.characteristics());
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ BinaryOperator combiner() {
            return this.wrappedValue.combiner();
        }

        public /* synthetic */ boolean equals(Object obj) {
            java.util.stream.Collector collector = this.wrappedValue;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).wrappedValue;
            }
            return collector.equals(obj);
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Function finisher() {
            return this.wrappedValue.finisher();
        }

        public /* synthetic */ int hashCode() {
            return this.wrappedValue.hashCode();
        }

        @Override // j$.util.stream.Collector
        public /* synthetic */ Supplier supplier() {
            return this.wrappedValue.supplier();
        }
    }

    /* loaded from: classes2.dex */
    public final /* synthetic */ class Wrapper implements java.util.stream.Collector {
        private /* synthetic */ Wrapper() {
        }

        public static /* synthetic */ java.util.stream.Collector convert(Collector collector) {
            if (collector == null) {
                return null;
            }
            return collector instanceof VivifiedWrapper ? ((VivifiedWrapper) collector).wrappedValue : new Wrapper();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ BiConsumer accumulator() {
            return Collector.this.accumulator();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Set characteristics() {
            return StreamApiFlips.flipCharacteristicSet(Collector.this.characteristics());
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ BinaryOperator combiner() {
            return Collector.this.combiner();
        }

        public /* synthetic */ boolean equals(Object obj) {
            Collector collector = Collector.this;
            if (obj instanceof Wrapper) {
                obj = Collector.this;
            }
            return collector.equals(obj);
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Function finisher() {
            return Collector.this.finisher();
        }

        public /* synthetic */ int hashCode() {
            return Collector.this.hashCode();
        }

        @Override // java.util.stream.Collector
        public /* synthetic */ Supplier supplier() {
            return Collector.this.supplier();
        }
    }

    BiConsumer accumulator();

    Set characteristics();

    BinaryOperator combiner();

    Function finisher();

    Supplier supplier();
}
