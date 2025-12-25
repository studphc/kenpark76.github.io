package j$.util;

import j$.time.Instant;
import java.util.Date;
/* loaded from: classes2.dex */
public abstract /* synthetic */ class DesugarDate {
    public static Instant toInstant(Date date) {
        return Instant.ofEpochMilli(date.getTime());
    }
}
