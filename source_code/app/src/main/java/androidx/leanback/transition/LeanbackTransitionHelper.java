package androidx.leanback.transition;

import android.content.Context;
import androidx.leanback.R;
/* loaded from: classes.dex */
public class LeanbackTransitionHelper {
    public static Object loadTitleInTransition(Context context) {
        return TransitionHelper.loadTransition(context, R.transition.lb_title_in);
    }

    public static Object loadTitleOutTransition(Context context) {
        return TransitionHelper.loadTransition(context, R.transition.lb_title_out);
    }

    private LeanbackTransitionHelper() {
    }
}
