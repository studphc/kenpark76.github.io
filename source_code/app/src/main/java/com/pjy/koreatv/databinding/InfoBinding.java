package com.pjy.koreatv.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.pjy.koreatv.R;
/* loaded from: classes3.dex */
public final class InfoBinding implements ViewBinding {
    public final FrameLayout container;
    public final TextView desc;
    public final TextView descNext;
    public final LinearLayout info;
    public final ImageView logo;
    public final LinearLayout main;
    private final LinearLayout rootView;
    public final TextView title;
    public final TextView titleTime;

    private InfoBinding(LinearLayout linearLayout, FrameLayout frameLayout, TextView textView, TextView textView2, LinearLayout linearLayout2, ImageView imageView, LinearLayout linearLayout3, TextView textView3, TextView textView4) {
        this.rootView = linearLayout;
        this.container = frameLayout;
        this.desc = textView;
        this.descNext = textView2;
        this.info = linearLayout2;
        this.logo = imageView;
        this.main = linearLayout3;
        this.title = textView3;
        this.titleTime = textView4;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static InfoBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static InfoBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.info, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static InfoBinding bind(View view) {
        int i = R.id.container;
        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i);
        if (frameLayout != null) {
            i = R.id.desc;
            TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
            if (textView != null) {
                i = R.id.descNext;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                if (textView2 != null) {
                    i = R.id.info;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
                    if (linearLayout != null) {
                        i = R.id.logo;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
                        if (imageView != null) {
                            i = R.id.main;
                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i);
                            if (linearLayout2 != null) {
                                i = R.id.title;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i);
                                if (textView3 != null) {
                                    i = R.id.titleTime;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i);
                                    if (textView4 != null) {
                                        return new InfoBinding((LinearLayout) view, frameLayout, textView, textView2, linearLayout, imageView, linearLayout2, textView3, textView4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
