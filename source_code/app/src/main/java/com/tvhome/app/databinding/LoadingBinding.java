package com.tvhome.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tvhome.app.R;
/* loaded from: classes3.dex */
public final class LoadingBinding implements ViewBinding {
    public final ProgressBar bar;
    public final FrameLayout loading;
    public final LinearLayout main;
    private final FrameLayout rootView;

    private LoadingBinding(FrameLayout frameLayout, ProgressBar progressBar, FrameLayout frameLayout2, LinearLayout linearLayout) {
        this.rootView = frameLayout;
        this.bar = progressBar;
        this.loading = frameLayout2;
        this.main = linearLayout;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static LoadingBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static LoadingBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.loading, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static LoadingBinding bind(View view) {
        int i = R.id.bar;
        ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i);
        if (progressBar != null) {
            FrameLayout frameLayout = (FrameLayout) view;
            int i2 = R.id.main;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
            if (linearLayout != null) {
                return new LoadingBinding(frameLayout, progressBar, frameLayout, linearLayout);
            }
            i = i2;
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
