package androidx.leanback.widget;

import android.graphics.Outline;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewOutlineProvider;
/* loaded from: classes.dex */
class RoundedRectHelperApi21 {
    private static final int MAX_CACHED_PROVIDER = 32;
    private static SparseArray<ViewOutlineProvider> sRoundedRectProvider;

    /* loaded from: classes.dex */
    static final class RoundedRectOutlineProvider extends ViewOutlineProvider {
        private int mRadius;

        RoundedRectOutlineProvider(int i) {
            this.mRadius = i;
        }

        @Override // android.view.ViewOutlineProvider
        public void getOutline(View view, Outline outline) {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), this.mRadius);
            outline.setAlpha(1.0f);
        }
    }

    public static void setClipToRoundedOutline(View view, boolean z, int i) {
        if (z) {
            if (sRoundedRectProvider == null) {
                sRoundedRectProvider = new SparseArray<>();
            }
            ViewOutlineProvider viewOutlineProvider = sRoundedRectProvider.get(i);
            if (viewOutlineProvider == null) {
                viewOutlineProvider = new RoundedRectOutlineProvider(i);
                if (sRoundedRectProvider.size() < 32) {
                    sRoundedRectProvider.put(i, viewOutlineProvider);
                }
            }
            view.setOutlineProvider(viewOutlineProvider);
        } else {
            view.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }
        view.setClipToOutline(z);
    }

    private RoundedRectHelperApi21() {
    }
}
