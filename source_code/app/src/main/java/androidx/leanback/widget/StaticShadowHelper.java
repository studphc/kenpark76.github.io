package androidx.leanback.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;
/* loaded from: classes.dex */
final class StaticShadowHelper {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean supportsShadow() {
        return true;
    }

    private StaticShadowHelper() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void prepareParent(ViewGroup viewGroup) {
        viewGroup.setLayoutMode(1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Object addStaticShadow(ViewGroup viewGroup) {
        viewGroup.setLayoutMode(1);
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lb_shadow, viewGroup, true);
        ShadowImpl shadowImpl = new ShadowImpl();
        shadowImpl.mNormalShadow = viewGroup.findViewById(R.id.lb_shadow_normal);
        shadowImpl.mFocusShadow = viewGroup.findViewById(R.id.lb_shadow_focused);
        return shadowImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setShadowFocusLevel(Object obj, float f) {
        ShadowImpl shadowImpl = (ShadowImpl) obj;
        shadowImpl.mNormalShadow.setAlpha(1.0f - f);
        shadowImpl.mFocusShadow.setAlpha(f);
    }

    /* loaded from: classes.dex */
    static class ShadowImpl {
        View mFocusShadow;
        View mNormalShadow;

        ShadowImpl() {
        }
    }
}
