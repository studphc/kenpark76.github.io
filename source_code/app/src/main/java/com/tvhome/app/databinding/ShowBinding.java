package com.tvhome.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tvhome.app.R;
/* loaded from: classes3.dex */
public final class ShowBinding implements ViewBinding {
    public final FrameLayout categoryFragment;
    public final LinearLayout content;
    private final FrameLayout rootView;
    public final ScrollView scrollView;

    private ShowBinding(FrameLayout frameLayout, FrameLayout frameLayout2, LinearLayout linearLayout, ScrollView scrollView) {
        this.rootView = frameLayout;
        this.categoryFragment = frameLayout2;
        this.content = linearLayout;
        this.scrollView = scrollView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ShowBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ShowBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.show, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ShowBinding bind(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i = R.id.content;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
        if (linearLayout != null) {
            i = R.id.scroll_view;
            ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i);
            if (scrollView != null) {
                return new ShowBinding(frameLayout, frameLayout, linearLayout, scrollView);
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
