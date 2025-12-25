package j$.util;

import j$.util.stream.Stream;
import j$.util.stream.StreamSupport;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.function.Predicate;
/* loaded from: classes2.dex */
public interface Collection {

    /* renamed from: j$.util.Collection$-CC */
    /* loaded from: classes2.dex */
    public abstract /* synthetic */ class CC {
        public static void $default$forEach(java.util.Collection collection, Consumer consumer) {
            Objects.requireNonNull(consumer);
            for (Object obj : collection) {
                consumer.accept(obj);
            }
        }

        public static boolean $default$removeIf(java.util.Collection collection, Predicate predicate) {
            if (DesugarCollections.SYNCHRONIZED_COLLECTION.isInstance(collection)) {
                return DesugarCollections.removeIf(collection, predicate);
            }
            Objects.requireNonNull(predicate);
            java.util.Iterator it = collection.iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (predicate.test(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }

        public static Stream $default$stream(java.util.Collection collection) {
            return StreamSupport.stream(EL.spliterator(collection), false);
        }
    }

    /* renamed from: j$.util.Collection$-EL */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class EL {
        public static /* synthetic */ void forEach(java.util.Collection collection, Consumer consumer) {
            if (collection instanceof Collection) {
                ((Collection) collection).forEach(consumer);
            } else {
                CC.$default$forEach(collection, consumer);
            }
        }

        public static /* synthetic */ boolean removeIf(java.util.Collection collection, Predicate predicate) {
            return collection instanceof Collection ? ((Collection) collection).removeIf(predicate) : CC.$default$removeIf(collection, predicate);
        }

        public static /* synthetic */ Spliterator spliterator(java.util.Collection collection) {
            Spliterator spliterator;
            if (collection instanceof Collection) {
                return ((Collection) collection).spliterator();
            }
            if (collection instanceof LinkedHashSet) {
                return DesugarLinkedHashSet.spliterator((LinkedHashSet) collection);
            }
            if (collection instanceof SortedSet) {
                return SortedSet$CC.$default$spliterator((SortedSet) collection);
            }
            if (!(collection instanceof Set)) {
                return collection instanceof List ? List$CC.$default$spliterator((List) collection) : Spliterators.spliterator(collection, 0);
            }
            spliterator = Spliterators.spliterator((Set) collection, 1);
            return spliterator;
        }

        public static /* synthetic */ Stream stream(java.util.Collection collection) {
            return collection instanceof Collection ? ((Collection) collection).stream() : CC.$default$stream(collection);
        }
    }

    void forEach(Consumer consumer);

    boolean removeIf(Predicate predicate);

    Spliterator spliterator();

    Stream stream();
}
