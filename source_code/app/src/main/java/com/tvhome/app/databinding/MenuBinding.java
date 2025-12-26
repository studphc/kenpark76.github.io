package com.tvhome.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tvhome.app.R;
/* loaded from: classes3.dex */
public final class MenuBinding implements ViewBinding {
    public final RecyclerView group;
    public final RecyclerView list;
    public final LinearLayout menu;
    private final LinearLayout rootView;
    public final RecyclerView temp;

    private MenuBinding(LinearLayout linearLayout, RecyclerView recyclerView, RecyclerView recyclerView2, LinearLayout linearLayout2, RecyclerView recyclerView3) {
        this.rootView = linearLayout;
        this.group = recyclerView;
        this.list = recyclerView2;
        this.menu = linearLayout2;
        this.temp = recyclerView3;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static MenuBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static MenuBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.menu, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static MenuBinding bind(View view) {
        int i = R.id.group;
        RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i);
        if (recyclerView != null) {
            i = R.id.list;
            RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i);
            if (recyclerView2 != null) {
                LinearLayout linearLayout = (LinearLayout) view;
                i = R.id.temp;
                RecyclerView recyclerView3 = (RecyclerView) ViewBindings.findChildViewById(view, i);
                if (recyclerView3 != null) {
                    return new MenuBinding(linearLayout, recyclerView, recyclerView2, linearLayout, recyclerView3);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
