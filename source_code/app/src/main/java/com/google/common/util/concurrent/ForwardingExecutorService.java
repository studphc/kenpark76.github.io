package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CheckReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class ForwardingExecutorService extends ForwardingObject implements ExecutorService {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.common.collect.ForwardingObject
    public abstract ExecutorService delegate();

    @Override // java.util.concurrent.ExecutorService
    @CheckReturnValue
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return delegate().awaitTermination(timeout, unit);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return delegate().invokeAll(tasks);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return delegate().invokeAll(tasks, timeout, unit);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return (T) delegate().invokeAny(tasks);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return (T) delegate().invokeAny(tasks, timeout, unit);
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isShutdown() {
        return delegate().isShutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public boolean isTerminated() {
        return delegate().isTerminated();
    }

    @Override // java.util.concurrent.ExecutorService
    public void shutdown() {
        delegate().shutdown();
    }

    @Override // java.util.concurrent.ExecutorService
    public List<Runnable> shutdownNow() {
        return delegate().shutdownNow();
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
        delegate().execute(command);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Callable<T> task) {
        return delegate().submit(task);
    }

    @Override // java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable task) {
        return delegate().submit(task);
    }

    @Override // java.util.concurrent.ExecutorService
    public <T> Future<T> submit(Runnable task, @ParametricNullness T result) {
        return delegate().submit(task, result);
    }
}
