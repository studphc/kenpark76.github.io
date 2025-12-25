package androidx.leanback.widget;

import android.view.View;
/* loaded from: classes.dex */
final class ShadowHelper {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean supportsDynamicShadow() {
        return true;
    }

    private ShadowHelper() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object addDynamicShadow(View view, float f, float f2, int i) {
        return ShadowHelperApi21.addDynamicShadow(view, f, f2, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setShadowFocusLevel(Object obj, float f) {
        ShadowHelperApi21.setShadowFocusLevel(obj, f);
    }
}
