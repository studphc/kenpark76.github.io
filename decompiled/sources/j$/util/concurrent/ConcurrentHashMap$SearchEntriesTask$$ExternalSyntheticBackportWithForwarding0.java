package j$.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
/* loaded from: classes2.dex */
public abstract /* synthetic */ class ConcurrentHashMap$SearchEntriesTask$$ExternalSyntheticBackportWithForwarding0 {
    public static /* synthetic */ boolean m(AtomicReference atomicReference, Object obj, Object obj2) {
        while (!atomicReference.compareAndSet(obj, obj2)) {
            if (atomicReference.get() != obj) {
                return false;
            }
        }
        return true;
    }
}
