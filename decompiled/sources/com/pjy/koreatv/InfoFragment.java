package com.pjy.koreatv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.exoplayer.rtsp.RtspMediaSource;
import androidx.media3.exoplayer.upstream.CmcdData;
import com.bumptech.glide.Glide;
import com.pjy.koreatv.databinding.InfoBinding;
import com.pjy.koreatv.models.EPG;
import com.pjy.koreatv.models.TVModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
/* compiled from: InfoFragment.kt */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001bH\u0016J\b\u0010\u001d\u001a\u00020\u001bH\u0016J\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lcom/pjy/koreatv/InfoFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/InfoBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/InfoBinding;", "delay", "", "handler", "Landroid/os/Handler;", "removeRunnable", "Ljava/lang/Runnable;", "formatITime", "", CmcdData.Factory.STREAMING_FORMAT_SS, "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onPause", "onResume", "show", "tvViewModel", "Lcom/pjy/koreatv/models/TVModel;", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class InfoFragment extends Fragment {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "InfoFragment";
    private InfoBinding _binding;
    private final Handler handler = new Handler();
    private final long delay = RtspMediaSource.DEFAULT_TIMEOUT_MS;
    private final Runnable removeRunnable = new Runnable() { // from class: com.pjy.koreatv.InfoFragment$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            InfoFragment.removeRunnable$lambda$3(InfoFragment.this);
        }
    };

    private final InfoBinding getBinding() {
        InfoBinding infoBinding = this._binding;
        Intrinsics.checkNotNull(infoBinding);
        return infoBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = InfoBinding.inflate(inflater, viewGroup, false);
        Context applicationContext = requireActivity().getApplicationContext();
        Intrinsics.checkNotNull(applicationContext, "null cannot be cast to non-null type com.pjy.koreatv.MyTVApplication");
        MyTVApplication myTVApplication = (MyTVApplication) applicationContext;
        getBinding().info.getLayoutParams().width = myTVApplication.px2Px(getBinding().info.getLayoutParams().width);
        getBinding().info.getLayoutParams().height = myTVApplication.px2Px(getBinding().info.getLayoutParams().height);
        ViewGroup.LayoutParams layoutParams = getBinding().info.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        LinearLayout info = getBinding().info;
        Intrinsics.checkNotNullExpressionValue(info, "info");
        ViewGroup.LayoutParams layoutParams2 = info.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = layoutParams2 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams2 : null;
        marginLayoutParams.bottomMargin = myTVApplication.px2Px(marginLayoutParams2 != null ? marginLayoutParams2.bottomMargin : 0);
        getBinding().info.setLayoutParams(marginLayoutParams);
        getBinding().logo.getLayoutParams().width = myTVApplication.px2Px(getBinding().logo.getLayoutParams().width);
        int px2Px = myTVApplication.px2Px(getBinding().logo.getPaddingTop());
        getBinding().logo.setPadding(px2Px, px2Px, px2Px, px2Px);
        getBinding().main.getLayoutParams().width = myTVApplication.px2Px(getBinding().main.getLayoutParams().width);
        int px2Px2 = myTVApplication.px2Px(getBinding().main.getPaddingTop());
        getBinding().main.setPadding(px2Px2, px2Px2, px2Px2, px2Px2);
        ViewGroup.LayoutParams layoutParams3 = getBinding().main.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams3, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) layoutParams3;
        LinearLayout main = getBinding().main;
        Intrinsics.checkNotNullExpressionValue(main, "main");
        ViewGroup.LayoutParams layoutParams4 = main.getLayoutParams();
        marginLayoutParams3.setMarginStart(myTVApplication.px2Px(layoutParams4 instanceof ViewGroup.MarginLayoutParams ? ((ViewGroup.MarginLayoutParams) layoutParams4).getMarginStart() : 0));
        getBinding().main.setLayoutParams(marginLayoutParams3);
        ViewGroup.LayoutParams layoutParams5 = getBinding().desc.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams5, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) layoutParams5;
        ViewGroup.LayoutParams layoutParams6 = getBinding().descNext.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams6, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        ViewGroup.MarginLayoutParams marginLayoutParams5 = (ViewGroup.MarginLayoutParams) layoutParams6;
        TextView desc = getBinding().desc;
        Intrinsics.checkNotNullExpressionValue(desc, "desc");
        ViewGroup.LayoutParams layoutParams7 = desc.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams6 = layoutParams7 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams7 : null;
        marginLayoutParams4.topMargin = myTVApplication.px2Px(marginLayoutParams6 != null ? marginLayoutParams6.topMargin : 0);
        TextView descNext = getBinding().descNext;
        Intrinsics.checkNotNullExpressionValue(descNext, "descNext");
        ViewGroup.LayoutParams layoutParams8 = descNext.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams7 = layoutParams8 instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams8 : null;
        marginLayoutParams5.topMargin = myTVApplication.px2Px(marginLayoutParams7 != null ? marginLayoutParams7.topMargin : 0);
        getBinding().desc.setLayoutParams(marginLayoutParams4);
        getBinding().descNext.setLayoutParams(marginLayoutParams5);
        getBinding().title.setTextSize(myTVApplication.px2PxFont(getBinding().title.getTextSize()));
        getBinding().titleTime.setTextSize(myTVApplication.px2PxFont(getBinding().titleTime.getTextSize()));
        getBinding().desc.setTextSize(myTVApplication.px2PxFont(getBinding().desc.getTextSize()));
        getBinding().descNext.setTextSize(myTVApplication.px2PxFont(getBinding().descNext.getTextSize()));
        getBinding().container.getLayoutParams().width = myTVApplication.shouldWidthPx();
        getBinding().container.getLayoutParams().height = myTVApplication.shouldHeightPx();
        InfoBinding infoBinding = this._binding;
        Intrinsics.checkNotNull(infoBinding);
        infoBinding.getRoot().setVisibility(8);
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    public final void show(TVModel tvViewModel) {
        ArrayList arrayList;
        Intrinsics.checkNotNullParameter(tvViewModel, "tvViewModel");
        Context requireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(requireContext, "requireContext(...)");
        getBinding().title.setText(tvViewModel.getTv().getTitle());
        getBinding().titleTime.setText(Utils.INSTANCE.getDateFormat("HH:mm"));
        tvViewModel.getTv().getTitle();
        String logo = tvViewModel.getTv().getLogo();
        boolean z = true;
        if (logo == null || StringsKt.isBlank(logo)) {
            int dpToPx = Utils.INSTANCE.dpToPx(100);
            int dpToPx2 = Utils.INSTANCE.dpToPx(60);
            Bitmap createBitmap = Bitmap.createBitmap(dpToPx, dpToPx2, Bitmap.Config.ARGB_8888);
            Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(...)");
            Canvas canvas = new Canvas(createBitmap);
            String valueOf = String.valueOf(tvViewModel.getTv().getId() + 1);
            float f = tvViewModel.getTv().getId() > 999 ? 70.0f : 80.0f;
            Paint paint = new Paint();
            paint.setColor(ContextCompat.getColor(requireContext, R.color.blur));
            paint.setTextSize(f);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(valueOf, dpToPx / 2.0f, (dpToPx2 / 2.0f) - ((paint.descent() + paint.ascent()) / 2), paint);
            Glide.with(this).load((Drawable) new BitmapDrawable(requireContext.getResources(), createBitmap)).into(getBinding().logo);
        } else {
            Glide.with(this).load(tvViewModel.getTv().getLogo()).fitCenter().into(getBinding().logo);
        }
        List<EPG> value = tvViewModel.getEpg().getValue();
        ArrayList arrayList2 = null;
        if (value != null) {
            ArrayList arrayList3 = new ArrayList();
            for (Object obj : value) {
                EPG epg = (EPG) obj;
                if (((long) epg.getBeginTime()) < Utils.INSTANCE.getDateTimestamp() && ((long) epg.getEndTime()) > Utils.INSTANCE.getDateTimestamp()) {
                    arrayList3.add(obj);
                }
            }
            arrayList = arrayList3;
        } else {
            arrayList = null;
        }
        List<EPG> value2 = tvViewModel.getEpg().getValue();
        if (value2 != null) {
            ArrayList arrayList4 = new ArrayList();
            for (Object obj2 : value2) {
                if (((long) ((EPG) obj2).getBeginTime()) > Utils.INSTANCE.getDateTimestamp()) {
                    arrayList4.add(obj2);
                }
            }
            arrayList2 = arrayList4;
        }
        ArrayList arrayList5 = arrayList;
        if (!(arrayList5 == null || arrayList5.isEmpty())) {
            getBinding().desc.setText("[" + formatITime(((EPG) CollectionsKt.last((List<? extends Object>) arrayList)).getBeginTime()) + "] " + ((EPG) CollectionsKt.last((List<? extends Object>) arrayList)).getTitle());
        } else {
            getBinding().desc.setText(getString(R.string.now_no_epg_data));
        }
        ArrayList arrayList6 = arrayList2;
        if (arrayList6 != null && !arrayList6.isEmpty()) {
            z = false;
        }
        if (!z) {
            getBinding().descNext.setText("[" + formatITime(((EPG) CollectionsKt.first((List<? extends Object>) arrayList2)).getBeginTime()) + "] " + ((EPG) CollectionsKt.first((List<? extends Object>) arrayList2)).getTitle());
        } else {
            getBinding().descNext.setText(getString(R.string.next_no_epg_data));
        }
        this.handler.removeCallbacks(this.removeRunnable);
        View view = getView();
        if (view != null) {
            view.setVisibility(0);
        }
        this.handler.postDelayed(this.removeRunnable, this.delay);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.handler.postDelayed(this.removeRunnable, this.delay);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.handler.removeCallbacks(this.removeRunnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeRunnable$lambda$3(InfoFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        View view = this$0.getView();
        if (view == null) {
            return;
        }
        view.setVisibility(8);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    private final String formatITime(int i) {
        String format = new SimpleDateFormat("HH:mm").format(new Date(i * 1000));
        return format != null ? format.toString() : "Unknown";
    }

    /* compiled from: InfoFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/InfoFragment$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
