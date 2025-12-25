package j$.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class Tripwire {
    static final boolean ENABLED = ((Boolean) AccessController.doPrivileged(new PrivilegedAction() { // from class: j$.util.Tripwire$$ExternalSyntheticLambda0
        @Override // java.security.PrivilegedAction
        public final Object run() {
            Boolean valueOf;
            valueOf = Boolean.valueOf(Boolean.getBoolean("org.openjdk.java.util.stream.tripwire"));
            return valueOf;
        }
    })).booleanValue();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void trip(Class cls, String str) {
        throw new UnsupportedOperationException(cls + " tripwire tripped but logging not supported: " + str);
    }
}
