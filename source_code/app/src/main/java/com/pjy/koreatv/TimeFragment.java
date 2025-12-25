package com.pjy.koreatv;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.pjy.koreatv.databinding.TimeBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TimeFragment.kt */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/pjy/koreatv/TimeFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/TimeBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/TimeBinding;", "delay", "", "handler", "Landroid/os/Handler;", "showRunnable", "Ljava/lang/Runnable;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onHiddenChanged", "hidden", "", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TimeFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "TimeFragment";
    private TimeBinding _binding;
    private final Handler handler = new Handler();
    private final long delay = 1000;
    private final Runnable showRunnable = new Runnable() { // from class: com.pjy.koreatv.TimeFragment$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            TimeFragment.showRunnable$lambda$1(TimeFragment.this);
        }
    };

    private final TimeBinding getBinding() {
        TimeBinding timeBinding = this._binding;
        Intrinsics.checkNotNull(timeBinding);
        return timeBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = TimeBinding.inflate(inflater, viewGroup, false);
        Context applicationContext = requireActivity().getApplicationContext();
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.pjy.koreatv.MyTVApplication");
        MyTVApplication myTVApplication = (MyTVApplication) applicationContext;
        getBinding().time.getLayoutParams().width = myTVApplication.px2Px(getBinding().time.getLayoutParams().width);
        getBinding().time.getLayoutParams().height = myTVApplication.px2Px(getBinding().time.getLayoutParams().height);
        ViewGroup.LayoutParams layoutParams = getBinding().time.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        RelativeLayout time = getBinding().time;
        Intrinsics.checkNotNullExpressionValue(time, "time");
        ViewGroup.LayoutParams layoutParams2 = time.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
        marginLayoutParams.topMargin = myTVApplication.px2Px(marginLayoutParams2 != null ? marginLayoutParams2.topMargin : 0);
        RelativeLayout time2 = getBinding().time;
        Intrinsics.checkNotNullExpressionValue(time2, "time");
        ViewGroup.LayoutParams layoutParams3 = time2.getLayoutParams();
        marginLayoutParams.setMarginEnd(myTVApplication.px2Px(layoutParams3 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams3).getMarginEnd() : 0));
        getBinding().time.setLayoutParams(marginLayoutParams);
        getBinding().content.setTextSize(myTVApplication.px2PxFont(getBinding().content.getTextSize()));
        getBinding().channel.setTextSize(myTVApplication.px2PxFont(getBinding().channel.getTextSize()));
        getBinding().main.getLayoutParams().width = myTVApplication.shouldWidthPx();
        getBinding().main.getLayoutParams().height = myTVApplication.shouldHeightPx();
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (!z) {
            this.handler.removeCallbacks(this.showRunnable);
            this.handler.postDelayed(this.showRunnable, 0L);
            return;
        }
        this.handler.removeCallbacks(this.showRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRunnable$lambda$1(TimeFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0._binding == null) {
            return;
        }
        this$0.getBinding().content.setText(Utils.INSTANCE.getDateFormat("HH:mm"));
        this$0.handler.postDelayed(this$0.showRunnable, this$0.delay);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    /* compiled from: TimeFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/TimeFragment$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
