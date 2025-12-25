package com.google.common.util.concurrent;

import com.google.errorprone.annotations.DoNotMock;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
@DoNotMock("Create an AbstractIdleService")
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface Service {

    /* loaded from: classes3.dex */
    public static abstract class Listener {
        public void failed(State from, Throwable failure) {
        }

        public void running() {
        }

        public void starting() {
        }

        public void stopping(State from) {
        }

        public void terminated(State from) {
        }
    }

    /* loaded from: classes3.dex */
    public enum State {
        NEW,
        STARTING,
        RUNNING,
        STOPPING,
        TERMINATED,
        FAILED
    }

    void addListener(Listener listener, Executor executor);

    void awaitRunning();

    void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException;

    void awaitTerminated();

    void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException;

    Throwable failureCause();

    boolean isRunning();

    Service startAsync();

    State state();

    Service stopAsync();
}
