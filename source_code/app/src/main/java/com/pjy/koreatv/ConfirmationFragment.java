package com.pjy.koreatv;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ConfirmationFragment.kt */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/pjy/koreatv/ConfirmationFragment;", "Landroidx/fragment/app/DialogFragment;", "listener", "Lcom/pjy/koreatv/ConfirmationFragment$ConfirmationListener;", "message", "", "update", "", "(Lcom/pjy/koreatv/ConfirmationFragment$ConfirmationListener;Ljava/lang/String;Z)V", "onCreateDialog", "Landroid/app/Dialog;", "savedInstanceState", "Landroid/os/Bundle;", "ConfirmationListener", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ConfirmationFragment extends DialogFragment {
    private final ConfirmationListener listener;
    private final String message;
    private final boolean update;

    /* compiled from: ConfirmationFragment.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/ConfirmationFragment$ConfirmationListener;", "", "onCancel", "", "onConfirm", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public interface ConfirmationListener {
        void onCancel();

        void onConfirm();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$3$lambda$2(DialogInterface dialogInterface, int i) {
    }

    public ConfirmationFragment(ConfirmationListener listener, String message, boolean z) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        Intrinsics.checkNotNullParameter(message, "message");
        this.listener = listener;
        this.message = message;
        this.update = z;
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog alertDialog;
        FragmentActivity activity = getActivity();
        if (activity != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(this.message);
            if (this.update) {
                builder.setMessage("确定更新吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.pjy.koreatv.ConfirmationFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConfirmationFragment.onCreateDialog$lambda$3$lambda$0(ConfirmationFragment.this, dialogInterface, i);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.pjy.koreatv.ConfirmationFragment$$ExternalSyntheticLambda1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConfirmationFragment.onCreateDialog$lambda$3$lambda$1(ConfirmationFragment.this, dialogInterface, i);
                    }
                });
            } else {
                builder.setMessage("").setNegativeButton("确定", new DialogInterface.OnClickListener() { // from class: com.pjy.koreatv.ConfirmationFragment$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ConfirmationFragment.onCreateDialog$lambda$3$lambda$2(dialogInterface, i);
                    }
                });
            }
            alertDialog = builder.create();
        } else {
            alertDialog = null;
        }
        if (alertDialog != null) {
            return alertDialog;
        }
        throw new IllegalStateException("Activity cannot be null");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$3$lambda$0(ConfirmationFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.listener.onConfirm();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreateDialog$lambda$3$lambda$1(ConfirmationFragment this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.listener.onCancel();
    }
}
