package com.google.common.collect;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public enum BoundType {
    OPEN(false),
    CLOSED(true);
    
    final boolean inclusive;

    BoundType(boolean inclusive) {
        this.inclusive = inclusive;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BoundType forBoolean(boolean inclusive) {
        return inclusive ? CLOSED : OPEN;
    }
}
