package androidx.leanback.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import androidx.leanback.transition.LeanbackTransitionHelper;
import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.widget.BrowseFrameLayout;
/* loaded from: classes.dex */
public class TitleHelper {
    private final BrowseFrameLayout.OnFocusSearchListener mOnFocusSearchListener = new BrowseFrameLayout.OnFocusSearchListener() { // from class: androidx.leanback.widget.TitleHelper.1
        @Override // androidx.leanback.widget.BrowseFrameLayout.OnFocusSearchListener
        public View onFocusSearch(View view, int i) {
            if (view != TitleHelper.this.mTitleView && i == 33) {
                return TitleHelper.this.mTitleView;
            }
            int i2 = ViewCompat.getLayoutDirection(view) == 1 ? 17 : 66;
            if (TitleHelper.this.mTitleView.hasFocus()) {
                if (i == 130 || i == i2) {
                    return TitleHelper.this.mSceneRoot;
                }
                return null;
            }
            return null;
        }
    };
    ViewGroup mSceneRoot;
    private Object mSceneWithTitle;
    private Object mSceneWithoutTitle;
    private Object mTitleDownTransition;
    private Object mTitleUpTransition;
    View mTitleView;

    public TitleHelper(ViewGroup viewGroup, View view) {
        if (viewGroup == null || view == null) {
            throw new IllegalArgumentException("Views may not be null");
        }
        this.mSceneRoot = viewGroup;
        this.mTitleView = view;
        createTransitions();
    }

    private void createTransitions() {
        this.mTitleUpTransition = LeanbackTransitionHelper.loadTitleOutTransition(this.mSceneRoot.getContext());
        this.mTitleDownTransition = LeanbackTransitionHelper.loadTitleInTransition(this.mSceneRoot.getContext());
        this.mSceneWithTitle = TransitionHelper.createScene(this.mSceneRoot, new Runnable() { // from class: androidx.leanback.widget.TitleHelper.2
            @Override // java.lang.Runnable
            public void run() {
                TitleHelper.this.mTitleView.setVisibility(0);
            }
        });
        this.mSceneWithoutTitle = TransitionHelper.createScene(this.mSceneRoot, new Runnable() { // from class: androidx.leanback.widget.TitleHelper.3
            @Override // java.lang.Runnable
            public void run() {
                TitleHelper.this.mTitleView.setVisibility(4);
            }
        });
    }

    public void showTitle(boolean z) {
        if (z) {
            TransitionHelper.runTransition(this.mSceneWithTitle, this.mTitleDownTransition);
        } else {
            TransitionHelper.runTransition(this.mSceneWithoutTitle, this.mTitleUpTransition);
        }
    }

    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public View getTitleView() {
        return this.mTitleView;
    }

    public BrowseFrameLayout.OnFocusSearchListener getOnFocusSearchListener() {
        return this.mOnFocusSearchListener;
    }
}
