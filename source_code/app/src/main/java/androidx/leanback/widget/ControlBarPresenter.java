package androidx.leanback.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.leanback.R;
import androidx.leanback.widget.ControlBar;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.Presenter;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class ControlBarPresenter extends Presenter {
    static final int MAX_CONTROLS = 7;
    private static int sChildMarginDefault;
    private static int sControlIconWidth;
    boolean mDefaultFocusToMiddle = true;
    private int mLayoutResourceId;
    OnControlClickedListener mOnControlClickedListener;
    OnControlSelectedListener mOnControlSelectedListener;

    /* loaded from: classes.dex */
    static class BoundData {
        ObjectAdapter adapter;
        Presenter presenter;
    }

    /* loaded from: classes.dex */
    interface OnControlClickedListener {
        void onControlClicked(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    /* loaded from: classes.dex */
    interface OnControlSelectedListener {
        void onControlSelected(Presenter.ViewHolder viewHolder, Object obj, BoundData boundData);
    }

    /* loaded from: classes.dex */
    class ViewHolder extends Presenter.ViewHolder {
        ObjectAdapter mAdapter;
        ControlBar mControlBar;
        View mControlsContainer;
        BoundData mData;
        ObjectAdapter.DataObserver mDataObserver;
        Presenter mPresenter;
        SparseArray<Presenter.ViewHolder> mViewHolders;

        /* JADX INFO: Access modifiers changed from: package-private */
        public ViewHolder(View view) {
            super(view);
            this.mViewHolders = new SparseArray<>();
            this.mControlsContainer = view.findViewById(R.id.controls_container);
            ControlBar controlBar = (ControlBar) view.findViewById(R.id.control_bar);
            this.mControlBar = controlBar;
            if (controlBar == null) {
                throw new IllegalStateException("Couldn't find control_bar");
            }
            controlBar.setDefaultFocusToMiddle(ControlBarPresenter.this.mDefaultFocusToMiddle);
            this.mControlBar.setOnChildFocusedListener(new ControlBar.OnChildFocusedListener() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.1
                @Override // androidx.leanback.widget.ControlBar.OnChildFocusedListener
                public void onChildFocusedListener(View view2, View view3) {
                    if (ControlBarPresenter.this.mOnControlSelectedListener == null) {
                        return;
                    }
                    for (int i = 0; i < ViewHolder.this.mViewHolders.size(); i++) {
                        if (ViewHolder.this.mViewHolders.get(i).view == view2) {
                            ControlBarPresenter.this.mOnControlSelectedListener.onControlSelected(ViewHolder.this.mViewHolders.get(i), ViewHolder.this.getDisplayedAdapter().get(i), ViewHolder.this.mData);
                            return;
                        }
                    }
                }
            });
            this.mDataObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.2
                @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
                public void onChanged() {
                    if (ViewHolder.this.mAdapter == ViewHolder.this.getDisplayedAdapter()) {
                        ViewHolder viewHolder = ViewHolder.this;
                        viewHolder.showControls(viewHolder.mPresenter);
                    }
                }

                @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
                public void onItemRangeChanged(int i, int i2) {
                    if (ViewHolder.this.mAdapter == ViewHolder.this.getDisplayedAdapter()) {
                        for (int i3 = 0; i3 < i2; i3++) {
                            ViewHolder viewHolder = ViewHolder.this;
                            viewHolder.bindControlToAction(i + i3, viewHolder.mPresenter);
                        }
                    }
                }
            };
        }

        int getChildMarginFromCenter(Context context, int i) {
            return ControlBarPresenter.this.getChildMarginDefault(context) + ControlBarPresenter.this.getControlIconWidth(context);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void showControls(Presenter presenter) {
            ObjectAdapter displayedAdapter = getDisplayedAdapter();
            int size = displayedAdapter == null ? 0 : displayedAdapter.size();
            View focusedChild = this.mControlBar.getFocusedChild();
            if (focusedChild != null && size > 0 && this.mControlBar.indexOfChild(focusedChild) >= size) {
                this.mControlBar.getChildAt(displayedAdapter.size() - 1).requestFocus();
            }
            for (int childCount = this.mControlBar.getChildCount() - 1; childCount >= size; childCount--) {
                this.mControlBar.removeViewAt(childCount);
            }
            for (int i = 0; i < size && i < 7; i++) {
                bindControlToAction(i, displayedAdapter, presenter);
            }
            ControlBar controlBar = this.mControlBar;
            controlBar.setChildMarginFromCenter(getChildMarginFromCenter(controlBar.getContext(), size));
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public void bindControlToAction(int i, Presenter presenter) {
            bindControlToAction(i, getDisplayedAdapter(), presenter);
        }

        private void bindControlToAction(final int i, ObjectAdapter objectAdapter, Presenter presenter) {
            final Presenter.ViewHolder viewHolder = this.mViewHolders.get(i);
            Object obj = objectAdapter.get(i);
            if (viewHolder == null) {
                viewHolder = presenter.onCreateViewHolder(this.mControlBar);
                this.mViewHolders.put(i, viewHolder);
                presenter.setOnClickListener(viewHolder, new View.OnClickListener() { // from class: androidx.leanback.widget.ControlBarPresenter.ViewHolder.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        Object obj2 = ViewHolder.this.getDisplayedAdapter().get(i);
                        if (ControlBarPresenter.this.mOnControlClickedListener != null) {
                            ControlBarPresenter.this.mOnControlClickedListener.onControlClicked(viewHolder, obj2, ViewHolder.this.mData);
                        }
                    }
                });
            }
            if (viewHolder.view.getParent() == null) {
                this.mControlBar.addView(viewHolder.view);
            }
            presenter.onBindViewHolder(viewHolder, obj);
        }

        ObjectAdapter getDisplayedAdapter() {
            return this.mAdapter;
        }
    }

    public ControlBarPresenter(int i) {
        this.mLayoutResourceId = i;
    }

    public int getLayoutResourceId() {
        return this.mLayoutResourceId;
    }

    public void setOnControlClickedListener(OnControlClickedListener onControlClickedListener) {
        this.mOnControlClickedListener = onControlClickedListener;
    }

    public OnControlClickedListener getOnItemViewClickedListener() {
        return this.mOnControlClickedListener;
    }

    public void setOnControlSelectedListener(OnControlSelectedListener onControlSelectedListener) {
        this.mOnControlSelectedListener = onControlSelectedListener;
    }

    public OnControlSelectedListener getOnItemControlListener() {
        return this.mOnControlSelectedListener;
    }

    public void setBackgroundColor(ViewHolder viewHolder, int i) {
        viewHolder.mControlsContainer.setBackgroundColor(i);
    }

    @Override // androidx.leanback.widget.Presenter
    public Presenter.ViewHolder onCreateViewHolder(ViewGroup viewGroup) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutResourceId(), viewGroup, false));
    }

    @Override // androidx.leanback.widget.Presenter
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object obj) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        BoundData boundData = (BoundData) obj;
        if (viewHolder2.mAdapter != boundData.adapter) {
            viewHolder2.mAdapter = boundData.adapter;
            if (viewHolder2.mAdapter != null) {
                viewHolder2.mAdapter.registerObserver(viewHolder2.mDataObserver);
            }
        }
        viewHolder2.mPresenter = boundData.presenter;
        viewHolder2.mData = boundData;
        viewHolder2.showControls(viewHolder2.mPresenter);
    }

    @Override // androidx.leanback.widget.Presenter
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        if (viewHolder2.mAdapter != null) {
            viewHolder2.mAdapter.unregisterObserver(viewHolder2.mDataObserver);
            viewHolder2.mAdapter = null;
        }
        viewHolder2.mData = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getChildMarginDefault(Context context) {
        if (sChildMarginDefault == 0) {
            sChildMarginDefault = context.getResources().getDimensionPixelSize(R.dimen.lb_playback_controls_child_margin_default);
        }
        return sChildMarginDefault;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getControlIconWidth(Context context) {
        if (sControlIconWidth == 0) {
            sControlIconWidth = context.getResources().getDimensionPixelSize(R.dimen.lb_control_icon_width);
        }
        return sControlIconWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setDefaultFocusToMiddle(boolean z) {
        this.mDefaultFocusToMiddle = z;
    }
}
