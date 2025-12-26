package com.google.common.io;

import java.nio.Buffer;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
final class Java8Compatibility {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void clear(Buffer b) {
        b.clear();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void flip(Buffer b) {
        b.flip();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void limit(Buffer b, int limit) {
        b.limit(limit);
    }

    static void mark(Buffer b) {
        b.mark();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void position(Buffer b, int position) {
        b.position(position);
    }

    static void reset(Buffer b) {
        b.reset();
    }

    private Java8Compatibility() {
    }
}
