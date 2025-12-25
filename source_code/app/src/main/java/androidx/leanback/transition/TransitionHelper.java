package androidx.leanback.transition;

import android.animation.TimeInterpolator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Rect;
import android.transition.AutoTransition;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import androidx.vectordrawable.graphics.drawable.AndroidResources;
import java.util.ArrayList;
/* loaded from: classes.dex */
public final class TransitionHelper {
    public static final int FADE_IN = 1;
    public static final int FADE_OUT = 2;

    public static boolean systemSupportsEntranceTransitions() {
        return true;
    }

    /* loaded from: classes.dex */
    private static class TransitionStub {
        ArrayList<TransitionListener> mTransitionListeners;

        TransitionStub() {
        }
    }

    public static Object getSharedElementEnterTransition(Window window) {
        return window.getSharedElementEnterTransition();
    }

    public static void setSharedElementEnterTransition(Window window, Object obj) {
        window.setSharedElementEnterTransition((Transition) obj);
    }

    public static Object getSharedElementReturnTransition(Window window) {
        return window.getSharedElementReturnTransition();
    }

    public static void setSharedElementReturnTransition(Window window, Object obj) {
        window.setSharedElementReturnTransition((Transition) obj);
    }

    public static Object getSharedElementExitTransition(Window window) {
        return window.getSharedElementExitTransition();
    }

    public static Object getSharedElementReenterTransition(Window window) {
        return window.getSharedElementReenterTransition();
    }

    public static Object getEnterTransition(Window window) {
        return window.getEnterTransition();
    }

    public static void setEnterTransition(Window window, Object obj) {
        window.setEnterTransition((Transition) obj);
    }

    public static Object getReturnTransition(Window window) {
        return window.getReturnTransition();
    }

    public static void setReturnTransition(Window window, Object obj) {
        window.setReturnTransition((Transition) obj);
    }

    public static Object getExitTransition(Window window) {
        return window.getExitTransition();
    }

    public static Object getReenterTransition(Window window) {
        return window.getReenterTransition();
    }

    public static Object createScene(ViewGroup viewGroup, Runnable runnable) {
        Scene scene = new Scene(viewGroup);
        scene.setEnterAction(runnable);
        return scene;
    }

    public static Object createChangeBounds(boolean z) {
        CustomChangeBounds customChangeBounds = new CustomChangeBounds();
        customChangeBounds.setReparent(z);
        return customChangeBounds;
    }

    public static Object createChangeTransform() {
        return new ChangeTransform();
    }

    public static void setChangeBoundsStartDelay(Object obj, View view, int i) {
        ((CustomChangeBounds) obj).setStartDelay(view, i);
    }

    public static void setChangeBoundsStartDelay(Object obj, int i, int i2) {
        ((CustomChangeBounds) obj).setStartDelay(i, i2);
    }

    public static void setChangeBoundsStartDelay(Object obj, String str, int i) {
        ((CustomChangeBounds) obj).setStartDelay(str, i);
    }

    public static void setChangeBoundsDefaultStartDelay(Object obj, int i) {
        ((CustomChangeBounds) obj).setDefaultStartDelay(i);
    }

    public static Object createTransitionSet(boolean z) {
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(z ? 1 : 0);
        return transitionSet;
    }

    public static Object createSlide(int i) {
        SlideKitkat slideKitkat = new SlideKitkat();
        slideKitkat.setSlideEdge(i);
        return slideKitkat;
    }

    public static Object createScale() {
        return new ChangeTransform();
    }

    public static void addTransition(Object obj, Object obj2) {
        ((TransitionSet) obj).addTransition((Transition) obj2);
    }

    public static void exclude(Object obj, int i, boolean z) {
        ((Transition) obj).excludeTarget(i, z);
    }

    public static void exclude(Object obj, View view, boolean z) {
        ((Transition) obj).excludeTarget(view, z);
    }

    public static void excludeChildren(Object obj, int i, boolean z) {
        ((Transition) obj).excludeChildren(i, z);
    }

    public static void excludeChildren(Object obj, View view, boolean z) {
        ((Transition) obj).excludeChildren(view, z);
    }

    public static void include(Object obj, int i) {
        ((Transition) obj).addTarget(i);
    }

    public static void include(Object obj, View view) {
        ((Transition) obj).addTarget(view);
    }

    public static void setStartDelay(Object obj, long j) {
        ((Transition) obj).setStartDelay(j);
    }

    public static void setDuration(Object obj, long j) {
        ((Transition) obj).setDuration(j);
    }

    public static Object createAutoTransition() {
        return new AutoTransition();
    }

    public static Object createFadeTransition(int i) {
        return new Fade(i);
    }

    public static void addTransitionListener(Object obj, final TransitionListener transitionListener) {
        if (transitionListener == null) {
            return;
        }
        transitionListener.mImpl = new Transition.TransitionListener() { // from class: androidx.leanback.transition.TransitionHelper.1
            @Override // android.transition.Transition.TransitionListener
            public void onTransitionStart(Transition transition) {
                TransitionListener.this.onTransitionStart(transition);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionResume(Transition transition) {
                TransitionListener.this.onTransitionResume(transition);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionPause(Transition transition) {
                TransitionListener.this.onTransitionPause(transition);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionEnd(Transition transition) {
                TransitionListener.this.onTransitionEnd(transition);
            }

            @Override // android.transition.Transition.TransitionListener
            public void onTransitionCancel(Transition transition) {
                TransitionListener.this.onTransitionCancel(transition);
            }
        };
        ((Transition) obj).addListener((Transition.TransitionListener) transitionListener.mImpl);
    }

    public static void removeTransitionListener(Object obj, TransitionListener transitionListener) {
        if (transitionListener == null || transitionListener.mImpl == null) {
            return;
        }
        ((Transition) obj).removeListener((Transition.TransitionListener) transitionListener.mImpl);
        transitionListener.mImpl = null;
    }

    public static void runTransition(Object obj, Object obj2) {
        TransitionManager.go((Scene) obj, (Transition) obj2);
    }

    public static void setInterpolator(Object obj, Object obj2) {
        ((Transition) obj).setInterpolator((TimeInterpolator) obj2);
    }

    public static void addTarget(Object obj, View view) {
        ((Transition) obj).addTarget(view);
    }

    public static Object createDefaultInterpolator(Context context) {
        return AnimationUtils.loadInterpolator(context, AndroidResources.FAST_OUT_LINEAR_IN);
    }

    public static Object loadTransition(Context context, int i) {
        return TransitionInflater.from(context).inflateTransition(i);
    }

    public static void setEnterTransition(Fragment fragment, Object obj) {
        fragment.setEnterTransition((Transition) obj);
    }

    public static void setExitTransition(Fragment fragment, Object obj) {
        fragment.setExitTransition((Transition) obj);
    }

    public static void setSharedElementEnterTransition(Fragment fragment, Object obj) {
        fragment.setSharedElementEnterTransition((Transition) obj);
    }

    public static void addSharedElement(FragmentTransaction fragmentTransaction, View view, String str) {
        fragmentTransaction.addSharedElement(view, str);
    }

    public static Object createFadeAndShortSlide(int i) {
        return new FadeAndShortSlide(i);
    }

    public static Object createFadeAndShortSlide(int i, float f) {
        FadeAndShortSlide fadeAndShortSlide = new FadeAndShortSlide(i);
        fadeAndShortSlide.setDistance(f);
        return fadeAndShortSlide;
    }

    public static void beginDelayedTransition(ViewGroup viewGroup, Object obj) {
        TransitionManager.beginDelayedTransition(viewGroup, (Transition) obj);
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean z) {
        viewGroup.setTransitionGroup(z);
    }

    public static void setEpicenterCallback(Object obj, final TransitionEpicenterCallback transitionEpicenterCallback) {
        if (transitionEpicenterCallback == null) {
            ((Transition) obj).setEpicenterCallback(null);
        } else {
            ((Transition) obj).setEpicenterCallback(new Transition.EpicenterCallback() { // from class: androidx.leanback.transition.TransitionHelper.2
                @Override // android.transition.Transition.EpicenterCallback
                public Rect onGetEpicenter(Transition transition) {
                    return TransitionEpicenterCallback.this.onGetEpicenter(transition);
                }
            });
        }
    }

    private TransitionHelper() {
    }
}
