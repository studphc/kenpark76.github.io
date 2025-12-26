package androidx.leanback.widget;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
/* loaded from: classes.dex */
class ShadowHelperApi21 {
    static final ViewOutlineProvider sOutlineProvider = new ViewOutlineProvider() { // from class: androidx.leanback.widget.ShadowHelperApi21.1
        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRect(0, 0, view.getWidth(), view.getHeight());
            outline.setAlpha(1.0f);
        }
    };

    /* loaded from: classes.dex */
    static class ShadowImpl {
        float mFocusedZ;
        float mNormalZ;
        View mShadowContainer;

        ShadowImpl() {
        }
    }

    public static Object addDynamicShadow(View view, float f, float f2, int i) {
        if (i > 0) {
            RoundedRectHelperApi21.setClipToRoundedOutline(view, true, i);
        } else {
            view.setOutlineProvider(sOutlineProvider);
        }
        ShadowImpl shadowImpl = new ShadowImpl();
        shadowImpl.mShadowContainer = view;
        shadowImpl.mNormalZ = f;
        shadowImpl.mFocusedZ = f2;
        view.setZ(shadowImpl.mNormalZ);
        return shadowImpl;
    }

    public static void setShadowFocusLevel(Object obj, float f) {
        ShadowImpl shadowImpl = (ShadowImpl) obj;
        shadowImpl.mShadowContainer.setZ(shadowImpl.mNormalZ + (f * (shadowImpl.mFocusedZ - shadowImpl.mNormalZ)));
    }

    private ShadowHelperApi21() {
    }
}
