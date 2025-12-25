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
public final class ErrorBinding implements ViewBinding {
    public final FrameLayout error;
    public final ImageView logo;
    public final LinearLayout main;
    public final TextView msg;
    private final FrameLayout rootView;

    private ErrorBinding(FrameLayout frameLayout, FrameLayout frameLayout2, ImageView imageView, LinearLayout linearLayout, TextView textView) {
        this.rootView = frameLayout;
        this.error = frameLayout2;
        this.logo = imageView;
        this.main = linearLayout;
        this.msg = textView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static ErrorBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ErrorBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.error, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ErrorBinding bind(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i = R.id.logo;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
        if (imageView != null) {
            i = R.id.main;
            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i);
            if (linearLayout != null) {
                i = R.id.msg;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
                if (textView != null) {
                    return new ErrorBinding(frameLayout, frameLayout, imageView, linearLayout, textView);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
