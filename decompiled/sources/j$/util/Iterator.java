package j$.util;

import java.util.function.Consumer;
/* loaded from: classes2.dex */
public interface Iterator {

    /* renamed from: j$.util.Iterator$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static void $default$forEachRemaining(java.util.Iterator it, Consumer consumer) {
            Objects.requireNonNull(consumer);
            while (it.hasNext()) {
                consumer.accept(it.next());
            }
        }
    }

    /* renamed from: j$.util.Iterator$-EL  reason: invalid class name */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class EL {
        public static /* synthetic */ void forEachRemaining(java.util.Iterator it, Consumer consumer) {
            if (it instanceof Iterator) {
                ((Iterator) it).forEachRemaining(consumer);
            } else {
                CC.$default$forEachRemaining(it, consumer);
            }
        }
    }

    void forEachRemaining(Consumer consumer);
}
