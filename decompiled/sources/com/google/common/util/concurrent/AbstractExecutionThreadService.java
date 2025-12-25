package com.google.common.util.concurrent;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.google.common.util.concurrent.Service;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public abstract class AbstractExecutionThreadService implements Service {
    private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AnonymousClass1();

    protected abstract void run() throws Exception;

    protected void shutDown() throws Exception {
    }

    protected void startUp() throws Exception {
    }

    protected void triggerShutdown() {
    }

    static /* synthetic */ Logger access$000() {
        return logger;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.google.common.util.concurrent.AbstractExecutionThreadService$1  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass1 extends AbstractService {
        AnonymousClass1() {
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected final void doStart() {
            MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService$1$$ExternalSyntheticLambda0
                @Override // com.google.common.base.Supplier
                public final Object get() {
                    return AbstractExecutionThreadService.AnonymousClass1.this.m462xa0f821c5();
                }
            }).execute(new Runnable() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractExecutionThreadService.AnonymousClass1.this.m463x3d661e24();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$doStart$0$com-google-common-util-concurrent-AbstractExecutionThreadService$1  reason: not valid java name */
        public /* synthetic */ String m462xa0f821c5() {
            return AbstractExecutionThreadService.this.serviceName();
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* renamed from: lambda$doStart$1$com-google-common-util-concurrent-AbstractExecutionThreadService$1  reason: not valid java name */
        public /* synthetic */ void m463x3d661e24() {
            try {
                AbstractExecutionThreadService.this.startUp();
                notifyStarted();
                if (isRunning()) {
                    AbstractExecutionThreadService.this.run();
                }
                AbstractExecutionThreadService.this.shutDown();
                notifyStopped();
            } catch (Throwable th) {
                Platform.restoreInterruptIfIsInterruptedException(th);
                notifyFailed(th);
            }
        }

        @Override // com.google.common.util.concurrent.AbstractService
        protected void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        @Override // com.google.common.util.concurrent.AbstractService
        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    }

    protected AbstractExecutionThreadService() {
    }

    protected Executor executor() {
        return new Executor() { // from class: com.google.common.util.concurrent.AbstractExecutionThreadService$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                AbstractExecutionThreadService.this.m461xafeb5522(runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$executor$0$com-google-common-util-concurrent-AbstractExecutionThreadService  reason: not valid java name */
    public /* synthetic */ void m461xafeb5522(Runnable runnable) {
        MoreExecutors.newThread(serviceName(), runnable).start();
    }

    public String toString() {
        return serviceName() + " [" + state() + "]";
    }

    @Override // com.google.common.util.concurrent.Service
    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service.State state() {
        return this.delegate.state();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    @Override // com.google.common.util.concurrent.Service
    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitRunning(timeout, unit);
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    @Override // com.google.common.util.concurrent.Service
    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitTerminated(timeout, unit);
    }

    protected String serviceName() {
        return getClass().getSimpleName();
    }
}
