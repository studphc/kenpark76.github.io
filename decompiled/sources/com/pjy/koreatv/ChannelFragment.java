package com.pjy.koreatv;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.media3.common.C;
import com.pjy.koreatv.databinding.ChannelBinding;
import com.pjy.koreatv.models.TVModel;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ChannelFragment.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\b\u0010\u001c\u001a\u00020\u001aH\u0016J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020 R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/pjy/koreatv/ChannelFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/ChannelBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/ChannelBinding;", "channel", "", "delay", "", "handler", "Landroid/os/Handler;", "hideRunnable", "Ljava/lang/Runnable;", "playRunnable", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onPause", "onResume", "show", "tvViewModel", "Lcom/pjy/koreatv/models/TVModel;", "", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ChannelFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "ChannelFragment";
    private ChannelBinding _binding;
    private int channel;
    private final Handler handler = new Handler();
    private final long delay = C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS;
    private final Runnable hideRunnable = new Runnable() { // from class: com.pjy.koreatv.ChannelFragment$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ChannelFragment.hideRunnable$lambda$0(ChannelFragment.this);
        }
    };
    private final Runnable playRunnable = new Runnable() { // from class: com.pjy.koreatv.ChannelFragment$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            ChannelFragment.playRunnable$lambda$1(ChannelFragment.this);
        }
    };

    private final ChannelBinding getBinding() {
        ChannelBinding channelBinding = this._binding;
        Intrinsics.checkNotNull(channelBinding);
        return channelBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        ChannelBinding inflate = ChannelBinding.inflate(inflater, viewGroup, false);
        this._binding = inflate;
        Intrinsics.checkNotNull(inflate);
        inflate.getRoot().setVisibility(8);
        Context applicationContext = requireActivity().getApplicationContext();
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.pjy.koreatv.MyTVApplication");
        MyTVApplication myTVApplication = (MyTVApplication) applicationContext;
        getBinding().channel.getLayoutParams().width = myTVApplication.px2Px(getBinding().channel.getLayoutParams().width);
        getBinding().channel.getLayoutParams().height = myTVApplication.px2Px(getBinding().channel.getLayoutParams().height);
        ViewGroup.LayoutParams layoutParams = getBinding().channel.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        RelativeLayout channel = getBinding().channel;
        Intrinsics.checkNotNullExpressionValue(channel, "channel");
        ViewGroup.LayoutParams layoutParams2 = channel.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
        marginLayoutParams.topMargin = myTVApplication.px2Px(marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0);
        RelativeLayout channel2 = getBinding().channel;
        Intrinsics.checkNotNullExpressionValue(channel2, "channel");
        ViewGroup.LayoutParams layoutParams3 = channel2.getLayoutParams();
        marginLayoutParams.setMarginEnd(myTVApplication.px2Px(layoutParams3 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams3).getMarginEnd() : 0));
        getBinding().channel.setLayoutParams(marginLayoutParams);
        getBinding().content.setTextSize(myTVApplication.px2PxFont(getBinding().content.getTextSize()));
        getBinding().time.setTextSize(myTVApplication.px2PxFont(getBinding().time.getTextSize()));
        getBinding().main.getLayoutParams().width = myTVApplication.shouldWidthPx();
        getBinding().main.getLayoutParams().height = myTVApplication.shouldHeightPx();
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    public final void show(TVModel tvViewModel) {
        Intrinsics.checkNotNullParameter(tvViewModel, "tvViewModel");
        this.handler.removeCallbacks(this.hideRunnable);
        this.handler.removeCallbacks(this.playRunnable);
        getBinding().content.setText(String.valueOf(tvViewModel.getTv().getId() + 1));
        View view = getView();
        if (view != null) {
            view.setVisibility(0);
        }
        this.handler.postDelayed(this.hideRunnable, this.delay);
    }

    public final void show(String channel) {
        Intrinsics.checkNotNullParameter(channel, "channel");
        if (getBinding().content.getText().length() > 1) {
            return;
        }
        this.channel = Integer.parseInt(((Object) getBinding().content.getText()) + channel);
        this.handler.removeCallbacks(this.hideRunnable);
        this.handler.removeCallbacks(this.playRunnable);
        if (Intrinsics.areEqual(getBinding().content.getText(), "")) {
            getBinding().content.setText(channel);
            View view = getView();
            if (view != null) {
                view.setVisibility(0);
            }
            this.handler.postDelayed(this.playRunnable, this.delay);
            return;
        }
        TextView textView = getBinding().content;
        textView.setText(((Object) getBinding().content.getText()) + channel);
        this.handler.postDelayed(this.playRunnable, 0L);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        View view = getView();
        boolean z = false;
        if (view != null && view.getVisibility() == 0) {
            z = true;
        }
        if (z) {
            this.handler.postDelayed(this.hideRunnable, this.delay);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.handler.removeCallbacks(this.hideRunnable);
        this.handler.removeCallbacks(this.playRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideRunnable$lambda$0(ChannelFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getBinding().content.setText("");
        View view = this$0.getView();
        if (view == null) {
            return;
        }
        view.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void playRunnable$lambda$1(ChannelFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FragmentActivity activity = this$0.getActivity();
        Intrinsics.checkNotNull(activity, "null cannot be cast to non-null type com.pjy.koreatv.MainActivity");
        ((MainActivity) activity).play(this$0.channel - 1);
        this$0.handler.postDelayed(this$0.hideRunnable, this$0.delay);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* compiled from: ChannelFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/ChannelFragment$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
