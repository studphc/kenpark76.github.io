package j$.util.stream;

import j$.util.function.Consumer$CC;
import j$.util.function.DoubleConsumer$CC;
import j$.util.function.IntConsumer$CC;
import j$.util.function.LongConsumer$CC;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
/* loaded from: classes2.dex */
abstract class StreamSpliterators$ArrayBuffer {
    int index;

    /* loaded from: classes2.dex */
    static final class OfDouble extends OfPrimitive implements DoubleConsumer {
        final double[] array;

        /* JADX INFO: Access modifiers changed from: package-private */
        public OfDouble(int i) {
            this.array = new double[i];
        }

        @Override // java.util.function.DoubleConsumer
        public void accept(double d) {
            double[] dArr = this.array;
            int i = ((OfPrimitive) this).index;
            ((OfPrimitive) this).index = i + 1;
            dArr[i] = d;
        }

        public /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
            return DoubleConsumer$CC.$default$andThen(this, doubleConsumer);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.StreamSpliterators$ArrayBuffer.OfPrimitive
        public void forEach(DoubleConsumer doubleConsumer, long j) {
            for (int i = 0; i < j; i++) {
                doubleConsumer.accept(this.array[i]);
            }
        }
    }

    /* loaded from: classes2.dex */
    static final class OfInt extends OfPrimitive implements IntConsumer {
        final int[] array;

        /* JADX INFO: Access modifiers changed from: package-private */
        public OfInt(int i) {
            this.array = new int[i];
        }

        @Override // java.util.function.IntConsumer
        public void accept(int i) {
            int[] iArr = this.array;
            int i2 = ((OfPrimitive) this).index;
            ((OfPrimitive) this).index = i2 + 1;
            iArr[i2] = i;
        }

        public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
            return IntConsumer$CC.$default$andThen(this, intConsumer);
        }

        @Override // j$.util.stream.StreamSpliterators$ArrayBuffer.OfPrimitive
        public void forEach(IntConsumer intConsumer, long j) {
            for (int i = 0; i < j; i++) {
                intConsumer.accept(this.array[i]);
            }
        }
    }

    /* loaded from: classes2.dex */
    static final class OfLong extends OfPrimitive implements LongConsumer {
        final long[] array;

        /* JADX INFO: Access modifiers changed from: package-private */
        public OfLong(int i) {
            this.array = new long[i];
        }

        @Override // java.util.function.LongConsumer
        public void accept(long j) {
            long[] jArr = this.array;
            int i = ((OfPrimitive) this).index;
            ((OfPrimitive) this).index = i + 1;
            jArr[i] = j;
        }

        public /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
            return LongConsumer$CC.$default$andThen(this, longConsumer);
        }

        @Override // j$.util.stream.StreamSpliterators$ArrayBuffer.OfPrimitive
        public void forEach(LongConsumer longConsumer, long j) {
            for (int i = 0; i < j; i++) {
                longConsumer.accept(this.array[i]);
            }
        }
    }

    /* loaded from: classes2.dex */
    static abstract class OfPrimitive extends StreamSpliterators$ArrayBuffer {
        int index;

        OfPrimitive() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public abstract void forEach(Object obj, long j);

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // j$.util.stream.StreamSpliterators$ArrayBuffer
        public void reset() {
            this.index = 0;
        }
    }

    /* loaded from: classes2.dex */
    static final class OfRef extends StreamSpliterators$ArrayBuffer implements Consumer {
        final Object[] array;

        /* JADX INFO: Access modifiers changed from: package-private */
        public OfRef(int i) {
            this.array = new Object[i];
        }

        @Override // java.util.function.Consumer
        public void accept(Object obj) {
            Object[] objArr = this.array;
            int i = this.index;
            this.index = i + 1;
            objArr[i] = obj;
        }

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        public void forEach(Consumer consumer, long j) {
            for (int i = 0; i < j; i++) {
                consumer.accept(this.array[i]);
            }
        }
    }

    StreamSpliterators$ArrayBuffer() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void reset() {
        this.index = 0;
    }
}
