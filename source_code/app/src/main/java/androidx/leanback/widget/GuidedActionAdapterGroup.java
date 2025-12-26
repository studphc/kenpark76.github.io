package androidx.leanback.widget;

import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import androidx.leanback.widget.GuidedActionAdapter;
import androidx.leanback.widget.GuidedActionsStylist;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class GuidedActionAdapterGroup {
    private static final boolean DEBUG_EDIT = false;
    private static final String TAG_EDIT = "EditableAction";
    ArrayList<Pair<GuidedActionAdapter, GuidedActionAdapter>> mAdapters = new ArrayList<>();
    private GuidedActionAdapter.EditListener mEditListener;
    private boolean mImeOpened;

    public void addAdpter(GuidedActionAdapter guidedActionAdapter, GuidedActionAdapter guidedActionAdapter2) {
        this.mAdapters.add(new Pair<>(guidedActionAdapter, guidedActionAdapter2));
        if (guidedActionAdapter != null) {
            guidedActionAdapter.mGroup = this;
        }
        if (guidedActionAdapter2 != null) {
            guidedActionAdapter2.mGroup = this;
        }
    }

    public GuidedActionAdapter getNextAdapter(GuidedActionAdapter guidedActionAdapter) {
        for (int i = 0; i < this.mAdapters.size(); i++) {
            Pair<GuidedActionAdapter, GuidedActionAdapter> pair = this.mAdapters.get(i);
            if (pair.first == guidedActionAdapter) {
                return (GuidedActionAdapter) pair.second;
            }
        }
        return null;
    }

    public void setEditListener(GuidedActionAdapter.EditListener editListener) {
        this.mEditListener = editListener;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018 A[LOOP:0: B:11:0x0018->B:14:0x0024, LOOP_START, PHI: r9 
      PHI: (r9v8 int) = (r9v2 int), (r9v9 int) binds: [B:10:0x0016, B:14:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0027 A[LOOP:1: B:15:0x0027->B:18:0x0035, LOOP_START, PHI: r9 
      PHI: (r9v3 int) = (r9v2 int), (r9v4 int) binds: [B:10:0x0016, B:18:0x0035] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0064  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:8:0x0011 -> B:9:0x0012). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean focusToNextAction(androidx.leanback.widget.GuidedActionAdapter r8, androidx.leanback.widget.GuidedAction r9, long r10) {
        /*
            r7 = this;
            r0 = -2
            r2 = 1
            r3 = 0
            int r4 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r4 != 0) goto L11
            int r9 = r8.indexOf(r9)
            if (r9 >= 0) goto Lf
            return r3
        Lf:
            int r9 = r9 + r2
            goto L12
        L11:
            r9 = 0
        L12:
            int r0 = r8.getCount()
            if (r4 != 0) goto L27
        L18:
            if (r9 >= r0) goto L38
            androidx.leanback.widget.GuidedAction r1 = r8.getItem(r9)
            boolean r1 = r1.isFocusable()
            if (r1 != 0) goto L38
            int r9 = r9 + 1
            goto L18
        L27:
            if (r9 >= r0) goto L38
            androidx.leanback.widget.GuidedAction r1 = r8.getItem(r9)
            long r5 = r1.getId()
            int r1 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r1 == 0) goto L38
            int r9 = r9 + 1
            goto L27
        L38:
            if (r9 >= r0) goto L64
            androidx.leanback.widget.GuidedActionsStylist r10 = r8.getGuidedActionsStylist()
            androidx.leanback.widget.VerticalGridView r10 = r10.getActionsGridView()
            androidx.recyclerview.widget.RecyclerView$ViewHolder r9 = r10.findViewHolderForPosition(r9)
            androidx.leanback.widget.GuidedActionsStylist$ViewHolder r9 = (androidx.leanback.widget.GuidedActionsStylist.ViewHolder) r9
            if (r9 == 0) goto L63
            androidx.leanback.widget.GuidedAction r10 = r9.getAction()
            boolean r10 = r10.hasTextEditable()
            if (r10 == 0) goto L58
            r7.openIme(r8, r9)
            goto L62
        L58:
            android.view.View r8 = r9.itemView
            r7.closeIme(r8)
            android.view.View r8 = r9.itemView
            r8.requestFocus()
        L62:
            return r2
        L63:
            return r3
        L64:
            androidx.leanback.widget.GuidedActionAdapter r8 = r7.getNextAdapter(r8)
            if (r8 != 0) goto L11
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GuidedActionAdapterGroup.focusToNextAction(androidx.leanback.widget.GuidedActionAdapter, androidx.leanback.widget.GuidedAction, long):boolean");
    }

    public void openIme(GuidedActionAdapter guidedActionAdapter, GuidedActionsStylist.ViewHolder viewHolder) {
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(viewHolder, true);
        View editingView = viewHolder.getEditingView();
        if (editingView == null || !viewHolder.isInEditingText()) {
            return;
        }
        editingView.setFocusable(true);
        editingView.requestFocus();
        ((InputMethodManager) editingView.getContext().getSystemService("input_method")).showSoftInput(editingView, 0);
        if (this.mImeOpened) {
            return;
        }
        this.mImeOpened = true;
        this.mEditListener.onImeOpen();
    }

    public void closeIme(View view) {
        if (this.mImeOpened) {
            this.mImeOpened = false;
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
            this.mEditListener.onImeClose();
        }
    }

    public void fillAndStay(GuidedActionAdapter guidedActionAdapter, TextView textView) {
        GuidedActionsStylist.ViewHolder findSubChildViewHolder = guidedActionAdapter.findSubChildViewHolder(textView);
        updateTextIntoAction(findSubChildViewHolder, textView);
        this.mEditListener.onGuidedActionEditCanceled(findSubChildViewHolder.getAction());
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(findSubChildViewHolder, false);
        closeIme(textView);
        findSubChildViewHolder.itemView.requestFocus();
    }

    public void fillAndGoNext(GuidedActionAdapter guidedActionAdapter, TextView textView) {
        GuidedActionsStylist.ViewHolder findSubChildViewHolder = guidedActionAdapter.findSubChildViewHolder(textView);
        updateTextIntoAction(findSubChildViewHolder, textView);
        guidedActionAdapter.performOnActionClick(findSubChildViewHolder);
        long onGuidedActionEditedAndProceed = this.mEditListener.onGuidedActionEditedAndProceed(findSubChildViewHolder.getAction());
        boolean z = false;
        guidedActionAdapter.getGuidedActionsStylist().setEditingMode(findSubChildViewHolder, false);
        if (onGuidedActionEditedAndProceed != -3 && onGuidedActionEditedAndProceed != findSubChildViewHolder.getAction().getId()) {
            z = focusToNextAction(guidedActionAdapter, findSubChildViewHolder.getAction(), onGuidedActionEditedAndProceed);
        }
        if (z) {
            return;
        }
        closeIme(textView);
        findSubChildViewHolder.itemView.requestFocus();
    }

    private void updateTextIntoAction(GuidedActionsStylist.ViewHolder viewHolder, TextView textView) {
        GuidedAction action = viewHolder.getAction();
        if (textView == viewHolder.getDescriptionView()) {
            if (action.getEditDescription() != null) {
                action.setEditDescription(textView.getText());
            } else {
                action.setDescription(textView.getText());
            }
        } else if (textView == viewHolder.getTitleView()) {
            if (action.getEditTitle() != null) {
                action.setEditTitle(textView.getText());
            } else {
                action.setTitle(textView.getText());
            }
        }
    }
}
