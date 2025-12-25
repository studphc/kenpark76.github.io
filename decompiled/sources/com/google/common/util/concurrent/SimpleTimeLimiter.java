package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.SimpleTimeLimiter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class SimpleTimeLimiter implements TimeLimiter {
    private final ExecutorService executor;

    private SimpleTimeLimiter(ExecutorService executor) {
        this.executor = (ExecutorService) Preconditions.checkNotNull(executor);
    }

    public static SimpleTimeLimiter create(ExecutorService executor) {
        return new SimpleTimeLimiter(executor);
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public <T> T newProxy(T target, Class<T> interfaceType, long timeoutDuration, TimeUnit timeoutUnit) {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Preconditions.checkArgument(interfaceType.isInterface(), "interfaceType must be an interface type");
        return (T) newProxy(interfaceType, new AnonymousClass1(target, timeoutDuration, timeoutUnit, findInterruptibleMethods(interfaceType)));
    }

    /* renamed from: com.google.common.util.concurrent.SimpleTimeLimiter$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    class AnonymousClass1 implements InvocationHandler {
        final /* synthetic */ Set val$interruptibleMethods;
        final /* synthetic */ Object val$target;
        final /* synthetic */ long val$timeoutDuration;
        final /* synthetic */ TimeUnit val$timeoutUnit;

        AnonymousClass1(final Object val$target, final long val$timeoutDuration, final TimeUnit val$timeoutUnit, final Set val$interruptibleMethods) {
            this.val$target = val$target;
            this.val$timeoutDuration = val$timeoutDuration;
            this.val$timeoutUnit = val$timeoutUnit;
            this.val$interruptibleMethods = val$interruptibleMethods;
        }

        @Override // java.lang.reflect.InvocationHandler
        @CheckForNull
        public Object invoke(Object obj, final Method method, @CheckForNull final Object[] args) throws Throwable {
            final Object obj2 = this.val$target;
            return SimpleTimeLimiter.this.callWithTimeout(new Callable() { // from class: com.google.common.util.concurrent.SimpleTimeLimiter$1$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return SimpleTimeLimiter.AnonymousClass1.lambda$invoke$0(method, obj2, args);
                }
            }, this.val$timeoutDuration, this.val$timeoutUnit, this.val$interruptibleMethods.contains(method));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ Object lambda$invoke$0(Method method, Object obj, Object[] objArr) throws Exception {
            try {
                return method.invoke(obj, objArr);
            } catch (InvocationTargetException e) {
                throw SimpleTimeLimiter.throwCause(e, false);
            }
        }
    }

    private static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        return interfaceType.cast(Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @ParametricNullness
    public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> submit = this.executor.submit(callable);
        try {
            if (amInterruptible) {
                return submit.get(timeoutDuration, timeoutUnit);
            }
            return (T) Uninterruptibles.getUninterruptibly(submit, timeoutDuration, timeoutUnit);
        } catch (InterruptedException e) {
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            throw throwCause(e2, true);
        } catch (TimeoutException e3) {
            submit.cancel(true);
            throw new UncheckedTimeoutException(e3);
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    @ParametricNullness
    public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> submit = this.executor.submit(callable);
        try {
            return submit.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException e) {
            e = e;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        } catch (TimeoutException e3) {
            e = e3;
            submit.cancel(true);
            throw e;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    @ParametricNullness
    public <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> submit = this.executor.submit(callable);
        try {
            return (T) Uninterruptibles.getUninterruptibly(submit, timeoutDuration, timeoutUnit);
        } catch (ExecutionException e) {
            wrapAndThrowExecutionExceptionOrError(e.getCause());
            throw new AssertionError();
        } catch (TimeoutException e2) {
            submit.cancel(true);
            throw e2;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public void runWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<?> submit = this.executor.submit(runnable);
        try {
            submit.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException e) {
            e = e;
            submit.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        } catch (TimeoutException e3) {
            e = e3;
            submit.cancel(true);
            throw e;
        }
    }

    @Override // com.google.common.util.concurrent.TimeLimiter
    public void runUninterruptiblyWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<?> submit = this.executor.submit(runnable);
        try {
            Uninterruptibles.getUninterruptibly(submit, timeoutDuration, timeoutUnit);
        } catch (ExecutionException e) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e.getCause());
            throw new AssertionError();
        } catch (TimeoutException e2) {
            submit.cancel(true);
            throw e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Exception throwCause(Exception e, boolean combineStackTraces) throws Exception {
        Throwable cause = e.getCause();
        if (cause == null) {
            throw e;
        }
        if (combineStackTraces) {
            cause.setStackTrace((StackTraceElement[]) ObjectArrays.concat(cause.getStackTrace(), e.getStackTrace(), StackTraceElement.class));
        }
        if (cause instanceof Exception) {
            throw ((Exception) cause);
        }
        if (cause instanceof Error) {
            throw ((Error) cause);
        }
        throw e;
    }

    private static Set<Method> findInterruptibleMethods(Class<?> interfaceType) {
        Method[] methods;
        HashSet newHashSet = Sets.newHashSet();
        for (Method method : interfaceType.getMethods()) {
            if (declaresInterruptedEx(method)) {
                newHashSet.add(method);
            }
        }
        return newHashSet;
    }

    private static boolean declaresInterruptedEx(Method method) {
        for (Class<?> cls : method.getExceptionTypes()) {
            if (cls == InterruptedException.class) {
                return true;
            }
        }
        return false;
    }

    private void wrapAndThrowExecutionExceptionOrError(Throwable cause) throws ExecutionException {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        }
        throw new ExecutionException(cause);
    }

    private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }

    private static void checkPositiveTimeout(long timeoutDuration) {
        Preconditions.checkArgument(timeoutDuration > 0, "timeout must be positive: %s", timeoutDuration);
    }
}
