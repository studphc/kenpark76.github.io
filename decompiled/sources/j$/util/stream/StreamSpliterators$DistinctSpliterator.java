package j$.util.stream;

import j$.util.Spliterator;
import j$.util.concurrent.ConcurrentHashMap;
import j$.util.function.Consumer$CC;
import java.util.Comparator;
import java.util.function.Consumer;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class StreamSpliterators$DistinctSpliterator implements Spliterator, Consumer {
    private static final Object NULL_VALUE = new Object();
    private final Spliterator s;
    private final ConcurrentHashMap seen;
    private Object tmpSlot;

    /* JADX INFO: Access modifiers changed from: package-private */
    public StreamSpliterators$DistinctSpliterator(Spliterator spliterator) {
        this(spliterator, new ConcurrentHashMap());
    }

    private StreamSpliterators$DistinctSpliterator(Spliterator spliterator, ConcurrentHashMap concurrentHashMap) {
        this.s = spliterator;
        this.seen = concurrentHashMap;
    }

    private Object mapNull(Object obj) {
        return obj != null ? obj : NULL_VALUE;
    }

    @Override // java.util.function.Consumer
    public void accept(Object obj) {
        this.tmpSlot = obj;
    }

    public /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // j$.util.Spliterator
    public int characteristics() {
        return (this.s.characteristics() & (-16469)) | 1;
    }

    @Override // j$.util.Spliterator
    public long estimateSize() {
        return this.s.estimateSize();
    }

    @Override // j$.util.Spliterator
    public void forEachRemaining(final Consumer consumer) {
        this.s.forEachRemaining(new Consumer() { // from class: j$.util.stream.StreamSpliterators$DistinctSpliterator$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StreamSpliterators$DistinctSpliterator.this.m527xb9bff3f1(consumer, obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer2) {
                return Consumer$CC.$default$andThen(this, consumer2);
            }
        });
    }

    @Override // j$.util.Spliterator
    public Comparator getComparator() {
        return this.s.getComparator();
    }

    @Override // j$.util.Spliterator
    public /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // j$.util.Spliterator
    public /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$forEachRemaining$0$java-util-stream-StreamSpliterators$DistinctSpliterator  reason: not valid java name */
    public /* synthetic */ void m527xb9bff3f1(Consumer consumer, Object obj) {
        if (this.seen.putIfAbsent(mapNull(obj), Boolean.TRUE) == null) {
            consumer.accept(obj);
        }
    }

    @Override // j$.util.Spliterator
    public boolean tryAdvance(Consumer consumer) {
        while (this.s.tryAdvance(this)) {
            if (this.seen.putIfAbsent(mapNull(this.tmpSlot), Boolean.TRUE) == null) {
                consumer.accept(this.tmpSlot);
                this.tmpSlot = null;
                return true;
            }
        }
        return false;
    }

    @Override // j$.util.Spliterator
    public Spliterator trySplit() {
        Spliterator trySplit = this.s.trySplit();
        if (trySplit != null) {
            return new StreamSpliterators$DistinctSpliterator(trySplit, this.seen);
        }
        return null;
    }
}
