package androidx.leanback.widget;

import android.view.View;
import androidx.leanback.R;
/* loaded from: classes.dex */
final class RoundedRectHelper {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean supportsRoundedCorner() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setClipToRoundedOutline(View view, boolean z, int i) {
        RoundedRectHelperApi21.setClipToRoundedOutline(view, z, i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setClipToRoundedOutline(View view, boolean z) {
        RoundedRectHelperApi21.setClipToRoundedOutline(view, z, view.getResources().getDimensionPixelSize(R.dimen.lb_rounded_rect_corner_radius));
    }

    private RoundedRectHelper() {
    }
}
