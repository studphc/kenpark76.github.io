package com.google.common.base;

import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public interface Function<F, T> {
    @ParametricNullness
    T apply(@ParametricNullness F input);

    boolean equals(@CheckForNull Object object);
}
