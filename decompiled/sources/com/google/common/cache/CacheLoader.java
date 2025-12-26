package com.google.common.cache;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class CacheLoader<K, V> {
    public abstract V load(K key) throws Exception;

    public ListenableFuture<V> reload(K key, V oldValue) throws Exception {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(oldValue);
        return Futures.immediateFuture(load(key));
    }

    public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
        throw new UnsupportedLoadingOperationException();
    }

    public static <K, V> CacheLoader<K, V> from(Function<K, V> function) {
        return new FunctionToCacheLoader(function);
    }

    public static <V> CacheLoader<Object, V> from(Supplier<V> supplier) {
        return new SupplierToCacheLoader(supplier);
    }

    /* loaded from: classes3.dex */
    private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Function<K, V> computingFunction;

        public FunctionToCacheLoader(Function<K, V> computingFunction) {
            this.computingFunction = (Function) Preconditions.checkNotNull(computingFunction);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.google.common.cache.CacheLoader
        public V load(K key) {
            return (V) this.computingFunction.apply(Preconditions.checkNotNull(key));
        }
    }

    public static <K, V> CacheLoader<K, V> asyncReloading(final CacheLoader<K, V> loader, final Executor executor) {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(executor);
        return new AnonymousClass1(executor);
    }

    /* renamed from: com.google.common.cache.CacheLoader$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends CacheLoader<K, V> {
        final /* synthetic */ Executor val$executor;

        AnonymousClass1(final Executor val$executor) {
            this.val$executor = val$executor;
        }

        @Override // com.google.common.cache.CacheLoader
        public V load(K key) throws Exception {
            return (V) CacheLoader.this.load(key);
        }

        @Override // com.google.common.cache.CacheLoader
        public ListenableFuture<V> reload(final K key, final V oldValue) {
            final CacheLoader cacheLoader = CacheLoader.this;
            ListenableFutureTask create = ListenableFutureTask.create(new Callable() { // from class: com.google.common.cache.CacheLoader$1$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    Object obj;
                    obj = CacheLoader.this.reload(key, oldValue).get();
                    return obj;
                }
            });
            this.val$executor.execute(create);
            return create;
        }

        @Override // com.google.common.cache.CacheLoader
        public Map<K, V> loadAll(Iterable<? extends K> keys) throws Exception {
            return CacheLoader.this.loadAll(keys);
        }
    }

    /* loaded from: classes3.dex */
    private static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Supplier<V> computingSupplier;

        public SupplierToCacheLoader(Supplier<V> computingSupplier) {
            this.computingSupplier = (Supplier) Preconditions.checkNotNull(computingSupplier);
        }

        @Override // com.google.common.cache.CacheLoader
        public V load(Object key) {
            Preconditions.checkNotNull(key);
            return this.computingSupplier.get();
        }
    }

    /* loaded from: classes3.dex */
    public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
        UnsupportedLoadingOperationException() {
        }
    }

    /* loaded from: classes3.dex */
    public static final class InvalidCacheLoadException extends RuntimeException {
        public InvalidCacheLoadException(String message) {
            super(message);
        }
    }
}
