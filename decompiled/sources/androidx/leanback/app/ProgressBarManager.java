package androidx.leanback.app;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
/* loaded from: classes.dex */
public final class ProgressBarManager {
    private static final long DEFAULT_PROGRESS_BAR_DELAY = 1000;
    boolean mIsShowing;
    View mProgressBarView;
    boolean mUserProvidedProgressBar;
    ViewGroup rootView;
    private long mInitialDelay = 1000;
    private Handler mHandler = new Handler();
    boolean mEnableProgressBar = true;
    private Runnable runnable = new Runnable() { // from class: androidx.leanback.app.ProgressBarManager.1
        @Override // java.lang.Runnable
        public void run() {
            if (ProgressBarManager.this.mEnableProgressBar) {
                if ((ProgressBarManager.this.mUserProvidedProgressBar || ProgressBarManager.this.rootView != null) && ProgressBarManager.this.mIsShowing) {
                    if (ProgressBarManager.this.mProgressBarView == null) {
                        ProgressBarManager.this.mProgressBarView = new ProgressBar(ProgressBarManager.this.rootView.getContext(), null, 16842874);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
                        layoutParams.gravity = 17;
                        ProgressBarManager.this.rootView.addView(ProgressBarManager.this.mProgressBarView, layoutParams);
                    } else if (ProgressBarManager.this.mUserProvidedProgressBar) {
                        ProgressBarManager.this.mProgressBarView.setVisibility(0);
                    }
                }
            }
        }
    };

    public void setRootView(ViewGroup viewGroup) {
        this.rootView = viewGroup;
    }

    public void show() {
        if (this.mEnableProgressBar) {
            this.mIsShowing = true;
            this.mHandler.postDelayed(this.runnable, this.mInitialDelay);
        }
    }

    public void hide() {
        this.mIsShowing = false;
        if (this.mUserProvidedProgressBar) {
            this.mProgressBarView.setVisibility(4);
        } else {
            View view = this.mProgressBarView;
            if (view != null) {
                this.rootView.removeView(view);
                this.mProgressBarView = null;
            }
        }
        this.mHandler.removeCallbacks(this.runnable);
    }

    public void setProgressBarView(View view) {
        if (view.getParent() == null) {
            throw new IllegalArgumentException("Must have a parent");
        }
        this.mProgressBarView = view;
        view.setVisibility(4);
        this.mUserProvidedProgressBar = true;
    }

    public long getInitialDelay() {
        return this.mInitialDelay;
    }

    public void setInitialDelay(long j) {
        this.mInitialDelay = j;
    }

    public void disableProgressBar() {
        this.mEnableProgressBar = false;
    }

    public void enableProgressBar() {
        this.mEnableProgressBar = true;
    }
}
