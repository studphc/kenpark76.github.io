package androidx.leanback.widget;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.leanback.widget.GuidedActionAutofillSupport;
import androidx.leanback.widget.GuidedActionsStylist;
import androidx.leanback.widget.ImeKeyMonitor;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class GuidedActionAdapter extends RecyclerView.Adapter {
    static final boolean DEBUG = false;
    static final boolean DEBUG_EDIT = false;
    static final String TAG = "GuidedActionAdapter";
    static final String TAG_EDIT = "EditableAction";
    private final ActionAutofillListener mActionAutofillListener;
    private final ActionEditListener mActionEditListener;
    private final ActionOnFocusListener mActionOnFocusListener;
    private final ActionOnKeyListener mActionOnKeyListener;
    final List<GuidedAction> mActions;
    private ClickListener mClickListener;
    DiffCallback<GuidedAction> mDiffCallback;
    GuidedActionAdapterGroup mGroup;
    private final boolean mIsSubAdapter;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() { // from class: androidx.leanback.widget.GuidedActionAdapter.1
        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view == null || view.getWindowToken() == null || GuidedActionAdapter.this.getRecyclerView() == null) {
                return;
            }
            GuidedActionsStylist.ViewHolder viewHolder = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(view);
            GuidedAction action = viewHolder.getAction();
            if (action.hasTextEditable()) {
                GuidedActionAdapter.this.mGroup.openIme(GuidedActionAdapter.this, viewHolder);
            } else if (action.hasEditableActivatorView()) {
                GuidedActionAdapter.this.performOnActionClick(viewHolder);
            } else {
                GuidedActionAdapter.this.handleCheckedActions(viewHolder);
                if (!action.isEnabled() || action.infoOnly()) {
                    return;
                }
                GuidedActionAdapter.this.performOnActionClick(viewHolder);
            }
        }
    };
    final GuidedActionsStylist mStylist;

    /* loaded from: classes.dex */
    public interface ClickListener {
        void onGuidedActionClicked(GuidedAction guidedAction);
    }

    /* loaded from: classes.dex */
    public interface EditListener {
        void onGuidedActionEditCanceled(GuidedAction guidedAction);

        long onGuidedActionEditedAndProceed(GuidedAction guidedAction);

        void onImeClose();

        void onImeOpen();
    }

    /* loaded from: classes.dex */
    public interface FocusListener {
        void onGuidedActionFocused(GuidedAction guidedAction);
    }

    public GuidedActionAdapter(List<GuidedAction> list, ClickListener clickListener, FocusListener focusListener, GuidedActionsStylist guidedActionsStylist, boolean z) {
        this.mActions = list == null ? new ArrayList() : new ArrayList(list);
        this.mClickListener = clickListener;
        this.mStylist = guidedActionsStylist;
        this.mActionOnKeyListener = new ActionOnKeyListener();
        this.mActionOnFocusListener = new ActionOnFocusListener(focusListener);
        this.mActionEditListener = new ActionEditListener();
        this.mActionAutofillListener = new ActionAutofillListener();
        this.mIsSubAdapter = z;
        if (z) {
            return;
        }
        this.mDiffCallback = GuidedActionDiffCallback.getInstance();
    }

    public void setDiffCallback(DiffCallback<GuidedAction> diffCallback) {
        this.mDiffCallback = diffCallback;
    }

    public void setActions(List<GuidedAction> list) {
        if (!this.mIsSubAdapter) {
            this.mStylist.collapseAction(false);
        }
        this.mActionOnFocusListener.unFocus();
        if (this.mDiffCallback != null) {
            final ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mActions);
            this.mActions.clear();
            this.mActions.addAll(list);
            DiffUtil.calculateDiff(new DiffUtil.Callback() { // from class: androidx.leanback.widget.GuidedActionAdapter.2
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getOldListSize() {
                    return arrayList.size();
                }

                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public int getNewListSize() {
                    return GuidedActionAdapter.this.mActions.size();
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areItemsTheSame(int i, int i2) {
                    return GuidedActionAdapter.this.mDiffCallback.areItemsTheSame(arrayList.get(i), GuidedActionAdapter.this.mActions.get(i2));
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public boolean areContentsTheSame(int i, int i2) {
                    return GuidedActionAdapter.this.mDiffCallback.areContentsTheSame(arrayList.get(i), GuidedActionAdapter.this.mActions.get(i2));
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // androidx.recyclerview.widget.DiffUtil.Callback
                public Object getChangePayload(int i, int i2) {
                    return GuidedActionAdapter.this.mDiffCallback.getChangePayload(arrayList.get(i), GuidedActionAdapter.this.mActions.get(i2));
                }
            }).dispatchUpdatesTo(this);
            return;
        }
        this.mActions.clear();
        this.mActions.addAll(list);
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.mActions.size();
    }

    public GuidedAction getItem(int i) {
        return this.mActions.get(i);
    }

    public int indexOf(GuidedAction guidedAction) {
        return this.mActions.indexOf(guidedAction);
    }

    public GuidedActionsStylist getGuidedActionsStylist() {
        return this.mStylist;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setFocusListener(FocusListener focusListener) {
        this.mActionOnFocusListener.setFocusListener(focusListener);
    }

    public List<GuidedAction> getActions() {
        return new ArrayList(this.mActions);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.mStylist.getItemViewType(this.mActions.get(i));
    }

    RecyclerView getRecyclerView() {
        return this.mIsSubAdapter ? this.mStylist.getSubActionsGridView() : this.mStylist.getActionsGridView();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        GuidedActionsStylist.ViewHolder onCreateViewHolder = this.mStylist.onCreateViewHolder(viewGroup, i);
        View view = onCreateViewHolder.itemView;
        view.setOnKeyListener(this.mActionOnKeyListener);
        view.setOnClickListener(this.mOnClickListener);
        view.setOnFocusChangeListener(this.mActionOnFocusListener);
        setupListeners(onCreateViewHolder.getEditableTitleView());
        setupListeners(onCreateViewHolder.getEditableDescriptionView());
        return onCreateViewHolder;
    }

    private void setupListeners(EditText editText) {
        if (editText != null) {
            editText.setPrivateImeOptions("escapeNorth");
            editText.setOnEditorActionListener(this.mActionEditListener);
            if (editText instanceof ImeKeyMonitor) {
                ((ImeKeyMonitor) editText).setImeKeyListener(this.mActionEditListener);
            }
            if (editText instanceof GuidedActionAutofillSupport) {
                ((GuidedActionAutofillSupport) editText).setOnAutofillListener(this.mActionAutofillListener);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (i >= this.mActions.size()) {
            return;
        }
        this.mStylist.onBindViewHolder((GuidedActionsStylist.ViewHolder) viewHolder, this.mActions.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mActions.size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ActionOnFocusListener implements View.OnFocusChangeListener {
        private FocusListener mFocusListener;
        private View mSelectedView;

        ActionOnFocusListener(FocusListener focusListener) {
            this.mFocusListener = focusListener;
        }

        public void setFocusListener(FocusListener focusListener) {
            this.mFocusListener = focusListener;
        }

        public void unFocus() {
            if (this.mSelectedView == null || GuidedActionAdapter.this.getRecyclerView() == null) {
                return;
            }
            RecyclerView.ViewHolder childViewHolder = GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(this.mSelectedView);
            if (childViewHolder != null) {
                GuidedActionAdapter.this.mStylist.onAnimateItemFocused((GuidedActionsStylist.ViewHolder) childViewHolder, false);
                return;
            }
            Log.w(GuidedActionAdapter.TAG, "RecyclerView returned null view holder", new Throwable());
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (GuidedActionAdapter.this.getRecyclerView() == null) {
                return;
            }
            GuidedActionsStylist.ViewHolder viewHolder = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(view);
            if (z) {
                this.mSelectedView = view;
                FocusListener focusListener = this.mFocusListener;
                if (focusListener != null) {
                    focusListener.onGuidedActionFocused(viewHolder.getAction());
                }
            } else if (this.mSelectedView == view) {
                GuidedActionAdapter.this.mStylist.onAnimateItemPressedCancelled(viewHolder);
                this.mSelectedView = null;
            }
            GuidedActionAdapter.this.mStylist.onAnimateItemFocused(viewHolder, z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
        if (r4 == null) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x002d, code lost:
        return (androidx.leanback.widget.GuidedActionsStylist.ViewHolder) getRecyclerView().getChildViewHolder(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:?, code lost:
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public androidx.leanback.widget.GuidedActionsStylist.ViewHolder findSubChildViewHolder(android.view.View r4) {
        /*
            r3 = this;
            androidx.recyclerview.widget.RecyclerView r0 = r3.getRecyclerView()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            android.view.ViewParent r0 = r4.getParent()
        Lc:
            androidx.recyclerview.widget.RecyclerView r2 = r3.getRecyclerView()
            if (r0 == r2) goto L1e
            if (r0 == 0) goto L1e
            if (r4 == 0) goto L1e
            r4 = r0
            android.view.View r4 = (android.view.View) r4
            android.view.ViewParent r0 = r0.getParent()
            goto Lc
        L1e:
            if (r0 == 0) goto L2d
            if (r4 == 0) goto L2d
            androidx.recyclerview.widget.RecyclerView r0 = r3.getRecyclerView()
            androidx.recyclerview.widget.RecyclerView$ViewHolder r4 = r0.getChildViewHolder(r4)
            r1 = r4
            androidx.leanback.widget.GuidedActionsStylist$ViewHolder r1 = (androidx.leanback.widget.GuidedActionsStylist.ViewHolder) r1
        L2d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.GuidedActionAdapter.findSubChildViewHolder(android.view.View):androidx.leanback.widget.GuidedActionsStylist$ViewHolder");
    }

    public void handleCheckedActions(GuidedActionsStylist.ViewHolder viewHolder) {
        GuidedAction action = viewHolder.getAction();
        int checkSetId = action.getCheckSetId();
        if (getRecyclerView() == null || checkSetId == 0) {
            return;
        }
        if (checkSetId != -1) {
            int size = this.mActions.size();
            for (int i = 0; i < size; i++) {
                GuidedAction guidedAction = this.mActions.get(i);
                if (guidedAction != action && guidedAction.getCheckSetId() == checkSetId && guidedAction.isChecked()) {
                    guidedAction.setChecked(false);
                    GuidedActionsStylist.ViewHolder viewHolder2 = (GuidedActionsStylist.ViewHolder) getRecyclerView().findViewHolderForPosition(i);
                    if (viewHolder2 != null) {
                        this.mStylist.onAnimateItemChecked(viewHolder2, false);
                    }
                }
            }
        }
        if (!action.isChecked()) {
            action.setChecked(true);
            this.mStylist.onAnimateItemChecked(viewHolder, true);
        } else if (checkSetId == -1) {
            action.setChecked(false);
            this.mStylist.onAnimateItemChecked(viewHolder, false);
        }
    }

    public void performOnActionClick(GuidedActionsStylist.ViewHolder viewHolder) {
        ClickListener clickListener = this.mClickListener;
        if (clickListener != null) {
            clickListener.onGuidedActionClicked(viewHolder.getAction());
        }
    }

    /* loaded from: classes.dex */
    private class ActionOnKeyListener implements View.OnKeyListener {
        private boolean mKeyPressed = false;

        ActionOnKeyListener() {
        }

        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (view == null || keyEvent == null || GuidedActionAdapter.this.getRecyclerView() == null) {
                return false;
            }
            if (i == 23 || i == 66 || i == 160 || i == 99 || i == 100) {
                GuidedActionsStylist.ViewHolder viewHolder = (GuidedActionsStylist.ViewHolder) GuidedActionAdapter.this.getRecyclerView().getChildViewHolder(view);
                GuidedAction action = viewHolder.getAction();
                if (!action.isEnabled() || action.infoOnly()) {
                    keyEvent.getAction();
                    return true;
                }
                int action2 = keyEvent.getAction();
                if (action2 == 0) {
                    if (!this.mKeyPressed) {
                        this.mKeyPressed = true;
                        GuidedActionAdapter.this.mStylist.onAnimateItemPressed(viewHolder, this.mKeyPressed);
                    }
                } else if (action2 == 1 && this.mKeyPressed) {
                    this.mKeyPressed = false;
                    GuidedActionAdapter.this.mStylist.onAnimateItemPressed(viewHolder, this.mKeyPressed);
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ActionEditListener implements TextView.OnEditorActionListener, ImeKeyMonitor.ImeKeyListener {
        ActionEditListener() {
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == 5 || i == 6) {
                GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, textView);
                return true;
            } else if (i == 1) {
                GuidedActionAdapter.this.mGroup.fillAndStay(GuidedActionAdapter.this, textView);
                return true;
            } else {
                return false;
            }
        }

        @Override // androidx.leanback.widget.ImeKeyMonitor.ImeKeyListener
        public boolean onKeyPreIme(EditText editText, int i, KeyEvent keyEvent) {
            if (i == 4 && keyEvent.getAction() == 1) {
                GuidedActionAdapter.this.mGroup.fillAndStay(GuidedActionAdapter.this, editText);
                return true;
            } else if (i == 66 && keyEvent.getAction() == 1) {
                GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, editText);
                return true;
            } else {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ActionAutofillListener implements GuidedActionAutofillSupport.OnAutofillListener {
        ActionAutofillListener() {
        }

        @Override // androidx.leanback.widget.GuidedActionAutofillSupport.OnAutofillListener
        public void onAutofill(View view) {
            GuidedActionAdapter.this.mGroup.fillAndGoNext(GuidedActionAdapter.this, (EditText) view);
        }
    }
}
