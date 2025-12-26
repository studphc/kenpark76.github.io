package com.tvhome.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.media3.ui.PlayerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.tvhome.app.R;
/* loaded from: classes3.dex */
public final class PlayerBinding implements ViewBinding {
    public final FrameLayout playerFragment;
    public final PlayerView playerView;
    private final FrameLayout rootView;

    private PlayerBinding(FrameLayout frameLayout, FrameLayout frameLayout2, PlayerView playerView) {
        this.rootView = frameLayout;
        this.playerFragment = frameLayout2;
        this.playerView = playerView;
    }

    @Override // androidx.viewbinding.ViewBinding
    public FrameLayout getRoot() {
        return this.rootView;
    }

    public static PlayerBinding inflate(LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    public static PlayerBinding inflate(LayoutInflater layoutInflater, ViewGroup viewGroup, boolean z) {
        View inflate = layoutInflater.inflate(R.layout.player, viewGroup, false);
        if (z) {
            viewGroup.addView(inflate);
        }
        return bind(inflate);
    }

    public static PlayerBinding bind(View view) {
        FrameLayout frameLayout = (FrameLayout) view;
        int i = R.id.player_view;
        PlayerView playerView = (PlayerView) ViewBindings.findChildViewById(view, i);
        if (playerView != null) {
            return new PlayerBinding(frameLayout, frameLayout, playerView);
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i)));
    }
}
