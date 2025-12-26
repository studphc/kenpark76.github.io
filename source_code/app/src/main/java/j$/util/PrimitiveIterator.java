package j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
/* loaded from: classes2.dex */
public interface PrimitiveIterator extends java.util.Iterator {

    /* loaded from: classes2.dex */
    public interface OfDouble extends PrimitiveIterator {

        /* renamed from: j$.util.PrimitiveIterator$OfDouble$-CC */
        /* loaded from: classes2.dex */
        public abstract /* synthetic */ class CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfDouble ofDouble, Object obj) {
                ofDouble.forEachRemaining((DoubleConsumer) obj);
            }

            public static void $default$forEachRemaining(OfDouble ofDouble, Consumer consumer) {
                if (consumer instanceof DoubleConsumer) {
                    ofDouble.forEachRemaining((DoubleConsumer) consumer);
                    return;
                }
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofDouble.getClass(), "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
                }
                Objects.requireNonNull(consumer);
                ofDouble.forEachRemaining((DoubleConsumer) new PrimitiveIterator$OfDouble$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfDouble ofDouble, DoubleConsumer doubleConsumer) {
                Objects.requireNonNull(doubleConsumer);
                while (ofDouble.hasNext()) {
                    doubleConsumer.accept(ofDouble.nextDouble());
                }
            }

            public static Double $default$next(OfDouble ofDouble) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofDouble.getClass(), "{0} calling PrimitiveIterator.OfDouble.nextLong()");
                }
                return Double.valueOf(ofDouble.nextDouble());
            }

            /* renamed from: $default$next */
            public static /* bridge */ /* synthetic */ Object m504$default$next(OfDouble ofDouble) {
                return ofDouble.next();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class VivifiedWrapper implements OfDouble, Iterator {
            public final /* synthetic */ PrimitiveIterator.OfDouble wrappedValue;

            private /* synthetic */ VivifiedWrapper(PrimitiveIterator.OfDouble ofDouble) {
                this.wrappedValue = ofDouble;
            }

            public static /* synthetic */ OfDouble convert(PrimitiveIterator.OfDouble ofDouble) {
                if (ofDouble == null) {
                    return null;
                }
                return ofDouble instanceof Wrapper ? OfDouble.this : new VivifiedWrapper(ofDouble);
            }

            public /* synthetic */ boolean equals(Object obj) {
                PrimitiveIterator.OfDouble ofDouble = this.wrappedValue;
                if (obj instanceof VivifiedWrapper) {
                    obj = ((VivifiedWrapper) obj).wrappedValue;
                }
                return ofDouble.equals(obj);
            }

            @Override // j$.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(Object obj) {
                this.wrappedValue.forEachRemaining((PrimitiveIterator.OfDouble) obj);
            }

            @Override // j$.util.PrimitiveIterator.OfDouble, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                this.wrappedValue.forEachRemaining((Consumer<? super Double>) consumer);
            }

            @Override // j$.util.PrimitiveIterator.OfDouble
            public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                this.wrappedValue.forEachRemaining(doubleConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return this.wrappedValue.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return this.wrappedValue.hashCode();
            }

            @Override // j$.util.PrimitiveIterator.OfDouble, java.util.Iterator
            public /* synthetic */ Double next() {
                return this.wrappedValue.next();
            }

            @Override // java.util.Iterator
            public /* synthetic */ Object next() {
                return this.wrappedValue.next();
            }

            @Override // j$.util.PrimitiveIterator.OfDouble
            public /* synthetic */ double nextDouble() {
                return this.wrappedValue.nextDouble();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                this.wrappedValue.remove();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class Wrapper implements PrimitiveIterator.OfDouble {
            private /* synthetic */ Wrapper() {
                OfDouble.this = r1;
            }

            public static /* synthetic */ PrimitiveIterator.OfDouble convert(OfDouble ofDouble) {
                if (ofDouble == null) {
                    return null;
                }
                return ofDouble instanceof VivifiedWrapper ? ((VivifiedWrapper) ofDouble).wrappedValue : new Wrapper();
            }

            public /* synthetic */ boolean equals(Object obj) {
                OfDouble ofDouble = OfDouble.this;
                if (obj instanceof Wrapper) {
                    obj = OfDouble.this;
                }
                return ofDouble.equals(obj);
            }

            @Override // java.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
                OfDouble.this.forEachRemaining((Object) doubleConsumer);
            }

            @Override // java.util.PrimitiveIterator.OfDouble, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                OfDouble.this.forEachRemaining(consumer);
            }

            @Override // java.util.PrimitiveIterator.OfDouble
            /* renamed from: forEachRemaining */
            public /* synthetic */ void forEachRemaining2(DoubleConsumer doubleConsumer) {
                OfDouble.this.forEachRemaining(doubleConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return OfDouble.this.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return OfDouble.this.hashCode();
            }

            @Override // java.util.PrimitiveIterator.OfDouble, java.util.Iterator
            public /* synthetic */ Double next() {
                return OfDouble.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfDouble, java.util.Iterator
            public /* synthetic */ Object next() {
                return OfDouble.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfDouble
            public /* synthetic */ double nextDouble() {
                return OfDouble.this.nextDouble();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                OfDouble.this.remove();
            }
        }

        @Override // java.util.Iterator
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(DoubleConsumer doubleConsumer);

        @Override // java.util.Iterator
        Double next();

        double nextDouble();
    }

    /* loaded from: classes2.dex */
    public interface OfInt extends PrimitiveIterator {

        /* renamed from: j$.util.PrimitiveIterator$OfInt$-CC */
        /* loaded from: classes2.dex */
        public abstract /* synthetic */ class CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfInt ofInt, Object obj) {
                ofInt.forEachRemaining((IntConsumer) obj);
            }

            public static void $default$forEachRemaining(OfInt ofInt, Consumer consumer) {
                if (consumer instanceof IntConsumer) {
                    ofInt.forEachRemaining((IntConsumer) consumer);
                    return;
                }
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofInt.getClass(), "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
                }
                Objects.requireNonNull(consumer);
                ofInt.forEachRemaining((IntConsumer) new PrimitiveIterator$OfInt$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfInt ofInt, IntConsumer intConsumer) {
                Objects.requireNonNull(intConsumer);
                while (ofInt.hasNext()) {
                    intConsumer.accept(ofInt.nextInt());
                }
            }

            public static Integer $default$next(OfInt ofInt) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofInt.getClass(), "{0} calling PrimitiveIterator.OfInt.nextInt()");
                }
                return Integer.valueOf(ofInt.nextInt());
            }

            /* renamed from: $default$next */
            public static /* bridge */ /* synthetic */ Object m505$default$next(OfInt ofInt) {
                return ofInt.next();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class VivifiedWrapper implements OfInt, Iterator {
            public final /* synthetic */ PrimitiveIterator.OfInt wrappedValue;

            private /* synthetic */ VivifiedWrapper(PrimitiveIterator.OfInt ofInt) {
                this.wrappedValue = ofInt;
            }

            public static /* synthetic */ OfInt convert(PrimitiveIterator.OfInt ofInt) {
                if (ofInt == null) {
                    return null;
                }
                return ofInt instanceof Wrapper ? OfInt.this : new VivifiedWrapper(ofInt);
            }

            public /* synthetic */ boolean equals(Object obj) {
                PrimitiveIterator.OfInt ofInt = this.wrappedValue;
                if (obj instanceof VivifiedWrapper) {
                    obj = ((VivifiedWrapper) obj).wrappedValue;
                }
                return ofInt.equals(obj);
            }

            @Override // j$.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(Object obj) {
                this.wrappedValue.forEachRemaining((PrimitiveIterator.OfInt) obj);
            }

            @Override // j$.util.PrimitiveIterator.OfInt, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                this.wrappedValue.forEachRemaining((Consumer<? super Integer>) consumer);
            }

            @Override // j$.util.PrimitiveIterator.OfInt
            public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                this.wrappedValue.forEachRemaining(intConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return this.wrappedValue.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return this.wrappedValue.hashCode();
            }

            @Override // j$.util.PrimitiveIterator.OfInt, java.util.Iterator
            public /* synthetic */ Integer next() {
                return this.wrappedValue.next();
            }

            @Override // java.util.Iterator
            public /* synthetic */ Object next() {
                return this.wrappedValue.next();
            }

            @Override // j$.util.PrimitiveIterator.OfInt
            public /* synthetic */ int nextInt() {
                return this.wrappedValue.nextInt();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                this.wrappedValue.remove();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class Wrapper implements PrimitiveIterator.OfInt {
            private /* synthetic */ Wrapper() {
                OfInt.this = r1;
            }

            public static /* synthetic */ PrimitiveIterator.OfInt convert(OfInt ofInt) {
                if (ofInt == null) {
                    return null;
                }
                return ofInt instanceof VivifiedWrapper ? ((VivifiedWrapper) ofInt).wrappedValue : new Wrapper();
            }

            public /* synthetic */ boolean equals(Object obj) {
                OfInt ofInt = OfInt.this;
                if (obj instanceof Wrapper) {
                    obj = OfInt.this;
                }
                return ofInt.equals(obj);
            }

            @Override // java.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
                OfInt.this.forEachRemaining((Object) intConsumer);
            }

            @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                OfInt.this.forEachRemaining(consumer);
            }

            @Override // java.util.PrimitiveIterator.OfInt
            /* renamed from: forEachRemaining */
            public /* synthetic */ void forEachRemaining2(IntConsumer intConsumer) {
                OfInt.this.forEachRemaining(intConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return OfInt.this.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return OfInt.this.hashCode();
            }

            @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
            public /* synthetic */ Integer next() {
                return OfInt.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
            public /* synthetic */ Object next() {
                return OfInt.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfInt
            public /* synthetic */ int nextInt() {
                return OfInt.this.nextInt();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                OfInt.this.remove();
            }
        }

        @Override // java.util.Iterator
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(IntConsumer intConsumer);

        @Override // java.util.Iterator
        Integer next();

        int nextInt();
    }

    /* loaded from: classes2.dex */
    public interface OfLong extends PrimitiveIterator {

        /* renamed from: j$.util.PrimitiveIterator$OfLong$-CC */
        /* loaded from: classes2.dex */
        public abstract /* synthetic */ class CC {
            public static /* bridge */ /* synthetic */ void $default$forEachRemaining(OfLong ofLong, Object obj) {
                ofLong.forEachRemaining((LongConsumer) obj);
            }

            public static void $default$forEachRemaining(OfLong ofLong, Consumer consumer) {
                if (consumer instanceof LongConsumer) {
                    ofLong.forEachRemaining((LongConsumer) consumer);
                    return;
                }
                Objects.requireNonNull(consumer);
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofLong.getClass(), "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
                }
                Objects.requireNonNull(consumer);
                ofLong.forEachRemaining((LongConsumer) new PrimitiveIterator$OfLong$$ExternalSyntheticLambda0(consumer));
            }

            public static void $default$forEachRemaining(OfLong ofLong, LongConsumer longConsumer) {
                Objects.requireNonNull(longConsumer);
                while (ofLong.hasNext()) {
                    longConsumer.accept(ofLong.nextLong());
                }
            }

            public static Long $default$next(OfLong ofLong) {
                if (Tripwire.ENABLED) {
                    Tripwire.trip(ofLong.getClass(), "{0} calling PrimitiveIterator.OfLong.nextLong()");
                }
                return Long.valueOf(ofLong.nextLong());
            }

            /* renamed from: $default$next */
            public static /* bridge */ /* synthetic */ Object m506$default$next(OfLong ofLong) {
                return ofLong.next();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class VivifiedWrapper implements OfLong, Iterator {
            public final /* synthetic */ PrimitiveIterator.OfLong wrappedValue;

            private /* synthetic */ VivifiedWrapper(PrimitiveIterator.OfLong ofLong) {
                this.wrappedValue = ofLong;
            }

            public static /* synthetic */ OfLong convert(PrimitiveIterator.OfLong ofLong) {
                if (ofLong == null) {
                    return null;
                }
                return ofLong instanceof Wrapper ? OfLong.this : new VivifiedWrapper(ofLong);
            }

            public /* synthetic */ boolean equals(Object obj) {
                PrimitiveIterator.OfLong ofLong = this.wrappedValue;
                if (obj instanceof VivifiedWrapper) {
                    obj = ((VivifiedWrapper) obj).wrappedValue;
                }
                return ofLong.equals(obj);
            }

            @Override // j$.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(Object obj) {
                this.wrappedValue.forEachRemaining((PrimitiveIterator.OfLong) obj);
            }

            @Override // j$.util.PrimitiveIterator.OfLong, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                this.wrappedValue.forEachRemaining((Consumer<? super Long>) consumer);
            }

            @Override // j$.util.PrimitiveIterator.OfLong
            public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                this.wrappedValue.forEachRemaining(longConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return this.wrappedValue.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return this.wrappedValue.hashCode();
            }

            @Override // j$.util.PrimitiveIterator.OfLong, java.util.Iterator
            public /* synthetic */ Long next() {
                return this.wrappedValue.next();
            }

            @Override // java.util.Iterator
            public /* synthetic */ Object next() {
                return this.wrappedValue.next();
            }

            @Override // j$.util.PrimitiveIterator.OfLong
            public /* synthetic */ long nextLong() {
                return this.wrappedValue.nextLong();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                this.wrappedValue.remove();
            }
        }

        /* loaded from: classes2.dex */
        public final /* synthetic */ class Wrapper implements PrimitiveIterator.OfLong {
            private /* synthetic */ Wrapper() {
                OfLong.this = r1;
            }

            public static /* synthetic */ PrimitiveIterator.OfLong convert(OfLong ofLong) {
                if (ofLong == null) {
                    return null;
                }
                return ofLong instanceof VivifiedWrapper ? ((VivifiedWrapper) ofLong).wrappedValue : new Wrapper();
            }

            public /* synthetic */ boolean equals(Object obj) {
                OfLong ofLong = OfLong.this;
                if (obj instanceof Wrapper) {
                    obj = OfLong.this;
                }
                return ofLong.equals(obj);
            }

            @Override // java.util.PrimitiveIterator
            public /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
                OfLong.this.forEachRemaining((Object) longConsumer);
            }

            @Override // java.util.PrimitiveIterator.OfLong, java.util.Iterator
            public /* synthetic */ void forEachRemaining(Consumer consumer) {
                OfLong.this.forEachRemaining(consumer);
            }

            @Override // java.util.PrimitiveIterator.OfLong
            /* renamed from: forEachRemaining */
            public /* synthetic */ void forEachRemaining2(LongConsumer longConsumer) {
                OfLong.this.forEachRemaining(longConsumer);
            }

            @Override // java.util.Iterator
            public /* synthetic */ boolean hasNext() {
                return OfLong.this.hasNext();
            }

            public /* synthetic */ int hashCode() {
                return OfLong.this.hashCode();
            }

            @Override // java.util.PrimitiveIterator.OfLong, java.util.Iterator
            public /* synthetic */ Long next() {
                return OfLong.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfLong, java.util.Iterator
            public /* synthetic */ Object next() {
                return OfLong.this.next();
            }

            @Override // java.util.PrimitiveIterator.OfLong
            public /* synthetic */ long nextLong() {
                return OfLong.this.nextLong();
            }

            @Override // java.util.Iterator
            public /* synthetic */ void remove() {
                OfLong.this.remove();
            }
        }

        @Override // java.util.Iterator
        void forEachRemaining(Consumer consumer);

        void forEachRemaining(LongConsumer longConsumer);

        @Override // java.util.Iterator
        Long next();

        long nextLong();
    }

    void forEachRemaining(Object obj);
}
