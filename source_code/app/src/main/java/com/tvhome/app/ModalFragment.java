package com.tvhome.app;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tvhome.app.databinding.ModalBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ModalFragment.kt */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0017H\u0016J\u001a\u0010\u0019\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/pjy/koreatv/ModalFragment;", "Landroidx/fragment/app/DialogFragment;", "()V", "_binding", "Lcom/pjy/koreatv/databinding/ModalBinding;", "binding", "getBinding", "()Lcom/pjy/koreatv/databinding/ModalBinding;", "delayHideAppreciateModal", "", "handler", "Landroid/os/Handler;", "hideAppreciateModal", "Ljava/lang/Runnable;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onStart", "onViewCreated", "view", "Companion", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ModalFragment extends DialogFragment {
    public static final Companion Companion = new Companion(null);
    public static final String KEY_BITMAP = "bitmap";
    public static final String KEY_DRAWABLE_ID = "drawable_id";
    public static final String TAG = "ModalFragment";
    private ModalBinding _binding;
    private final long delayHideAppreciateModal;
    private final Handler handler;
    private final Runnable hideAppreciateModal;

    public ModalFragment() {
        Looper myLooper = Looper.myLooper();
        Intrinsics.checkNotNull(myLooper);
        this.handler = new Handler(myLooper);
        this.delayHideAppreciateModal = 10000L;
        this.hideAppreciateModal = new Runnable() { // from class: com.pjy.koreatv.ModalFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ModalFragment.hideAppreciateModal$lambda$1(ModalFragment.this);
            }
        };
    }

    private final ModalBinding getBinding() {
        ModalBinding modalBinding = this._binding;
        Intrinsics.checkNotNull(modalBinding);
        return modalBinding;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        Window window;
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        window.addFlags(1024);
        window.getDecorView().setSystemUiVisibility(2);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle bundle) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = ModalBinding.inflate(inflater, viewGroup, false);
        ConstraintLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, bundle);
        Bundle arguments = getArguments();
        Bitmap bitmap = arguments != null ? (Bitmap) arguments.getParcelable(KEY_BITMAP) : null;
        if (bitmap != null) {
            Glide.with(requireContext()).load(bitmap).into(getBinding().modalImage);
        } else {
            RequestManager with = Glide.with(requireContext());
            Bundle arguments2 = getArguments();
            with.load(arguments2 != null ? Integer.valueOf(arguments2.getInt(KEY_DRAWABLE_ID)) : null).into(getBinding().modalImage);
        }
        this.handler.postDelayed(this.hideAppreciateModal, this.delayHideAppreciateModal);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideAppreciateModal$lambda$1(ModalFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isHidden()) {
            return;
        }
        this$0.dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
        this.handler.removeCallbacksAndMessages(null);
    }

    /* compiled from: ModalFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/pjy/koreatv/ModalFragment$Companion;", "", "()V", "KEY_BITMAP", "", "KEY_DRAWABLE_ID", "TAG", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
