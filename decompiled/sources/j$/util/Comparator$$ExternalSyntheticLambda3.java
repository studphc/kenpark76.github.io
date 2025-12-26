package j$.util;

import java.io.Serializable;
import java.util.function.ToIntFunction;
/* loaded from: classes2.dex */
public final /* synthetic */ class Comparator$$ExternalSyntheticLambda3 implements java.util.Comparator, Serializable {
    public final /* synthetic */ ToIntFunction f$0;

    public /* synthetic */ Comparator$$ExternalSyntheticLambda3(ToIntFunction toIntFunction) {
        this.f$0 = toIntFunction;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int compare;
        compare = Integer.compare(r0.applyAsInt(obj), this.f$0.applyAsInt(obj2));
        return compare;
    }
}
