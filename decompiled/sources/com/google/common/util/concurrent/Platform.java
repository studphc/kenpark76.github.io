package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import javax.annotation.CheckForNull;
/* JADX INFO: Access modifiers changed from: package-private */
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public final class Platform {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isInstanceOfThrowableClass(@CheckForNull Throwable t, Class<? extends Throwable> expectedClass) {
        return expectedClass.isInstance(t);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void restoreInterruptIfIsInterruptedException(Throwable t) {
        Preconditions.checkNotNull(t);
        if (t instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private Platform() {
    }
}
