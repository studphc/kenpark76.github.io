package androidx.leanback.transition;

import android.animation.Animator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
/* loaded from: classes.dex */
class CustomChangeBounds extends ChangeBounds {
    int mDefaultStartDelay;
    final HashMap<View, Integer> mViewStartDelays = new HashMap<>();
    final SparseIntArray mIdStartDelays = new SparseIntArray();
    final HashMap<String, Integer> mClassStartDelays = new HashMap<>();

    private int getDelay(View view) {
        Integer num = this.mViewStartDelays.get(view);
        if (num != null) {
            return num.intValue();
        }
        int i = this.mIdStartDelays.get(view.getId(), -1);
        if (i != -1) {
            return i;
        }
        Integer num2 = this.mClassStartDelays.get(view.getClass().getName());
        if (num2 != null) {
            return num2.intValue();
        }
        return this.mDefaultStartDelay;
    }

    @Override // android.transition.ChangeBounds, android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Animator createAnimator = super.createAnimator(viewGroup, transitionValues, transitionValues2);
        if (createAnimator != null && transitionValues2 != null && transitionValues2.view != null) {
            createAnimator.setStartDelay(getDelay(transitionValues2.view));
        }
        return createAnimator;
    }

    public void setStartDelay(View view, int i) {
        this.mViewStartDelays.put(view, Integer.valueOf(i));
    }

    public void setStartDelay(int i, int i2) {
        this.mIdStartDelays.put(i, i2);
    }

    public void setStartDelay(String str, int i) {
        this.mClassStartDelays.put(str, Integer.valueOf(i));
    }

    public void setDefaultStartDelay(int i) {
        this.mDefaultStartDelay = i;
    }
}
