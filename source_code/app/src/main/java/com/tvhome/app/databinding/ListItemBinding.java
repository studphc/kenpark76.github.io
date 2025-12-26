package com.tvhome.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tvhome.app.R;
/* loaded from: classes3.dex */
public final class ListItemBinding implements ViewBinding {
    public final TextView description;
    public final ImageView heart;
    public final ImageView icon;
    public final ConstraintLayout listItem;
    private final ConstraintLayout rootView;
    public final TextView title;

    private ListItemBinding(ConstraintLayout constraintLayout, TextView textView, ImageView imageView, ImageView imageView2, ConstraintLayout constraintLayout2, TextView textView2) {
        this.rootView = constraintLayout;
        this.description = textView;
        this.heart = imageView;
        this.icon = imageView2;
        this.listItem = constraintLayout2;
        this.title = textView2;
    }

    @Override // androidx.viewbinding.ViewBinding
    public ConstraintLayout getRoot() {
        return this.rootView;
    }

    public static ListItemBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static ListItemBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static ListItemBinding bind(View view) {
        int i = R.id.description;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i);
        if (textView != null) {
            i = R.id.heart;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i);
            if (imageView != null) {
                i = R.id.icon;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i);
                if (imageView2 != null) {
                    ConstraintLayout constraintLayout = (ConstraintLayout) view;
                    i = R.id.title;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i);
                    if (textView2 != null) {
                        return new ListItemBinding(constraintLayout, textView, imageView, imageView2, constraintLayout, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
