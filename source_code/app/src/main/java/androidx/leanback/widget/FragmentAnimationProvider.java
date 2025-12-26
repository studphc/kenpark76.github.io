package androidx.leanback.widget;

import android.animation.Animator;
import java.util.List;
/* loaded from: classes.dex */
public interface FragmentAnimationProvider {
    void onImeAppearing(List<Animator> list);

    void onImeDisappearing(List<Animator> list);
}
