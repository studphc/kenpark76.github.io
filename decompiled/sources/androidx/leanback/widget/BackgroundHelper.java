package androidx.leanback.widget;

import android.graphics.drawable.Drawable;
import android.view.View;
/* loaded from: classes.dex */
public final class BackgroundHelper {
    public static void setBackgroundPreservingAlpha(View view, Drawable drawable) {
        if (view.getBackground() != null) {
            drawable.setAlpha(view.getBackground().getAlpha());
        }
        view.setBackground(drawable);
    }

    private BackgroundHelper() {
    }
}
