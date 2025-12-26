package androidx.leanback.app;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.leanback.R;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.DividerPresenter;
import androidx.leanback.widget.DividerRow;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowHeaderPresenter;
import androidx.leanback.widget.SectionRow;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
@Deprecated
/* loaded from: classes.dex */
public class HeadersFragment extends BaseRowFragment {
    private static final PresenterSelector sHeaderPresenter = new ClassPresenterSelector().addClassPresenter(DividerRow.class, new DividerPresenter()).addClassPresenter(SectionRow.class, new RowHeaderPresenter(R.layout.lb_section_header, false)).addClassPresenter(Row.class, new RowHeaderPresenter(R.layout.lb_header));
    static View.OnLayoutChangeListener sLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.leanback.app.HeadersFragment.2
        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            view.setPivotX(view.getLayoutDirection() == 1 ? view.getWidth() : 0.0f);
            view.setPivotY(view.getMeasuredHeight() / 2);
        }
    };
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    OnHeaderClickedListener mOnHeaderClickedListener;
    private OnHeaderViewSelectedListener mOnHeaderViewSelectedListener;
    private boolean mHeadersEnabled = true;
    private boolean mHeadersGone = false;
    private final ItemBridgeAdapter.AdapterListener mAdapterListener = new ItemBridgeAdapter.AdapterListener() { // from class: androidx.leanback.app.HeadersFragment.1
        @Override // androidx.leanback.widget.ItemBridgeAdapter.AdapterListener
        public void onCreate(final ItemBridgeAdapter.ViewHolder viewHolder) {
            View view = viewHolder.getViewHolder().view;
            view.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.app.HeadersFragment.1.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (HeadersFragment.this.mOnHeaderClickedListener != null) {
                        HeadersFragment.this.mOnHeaderClickedListener.onHeaderClicked((RowHeaderPresenter.ViewHolder) viewHolder.getViewHolder(), (Row) viewHolder.getItem());
                    }
                }
            });
            if (HeadersFragment.this.mWrapper != null) {
                viewHolder.itemView.addOnLayoutChangeListener(HeadersFragment.sLayoutChangeListener);
            } else {
                view.addOnLayoutChangeListener(HeadersFragment.sLayoutChangeListener);
            }
        }
    };
    final ItemBridgeAdapter.Wrapper mWrapper = new ItemBridgeAdapter.Wrapper() { // from class: androidx.leanback.app.HeadersFragment.3
        @Override // androidx.leanback.widget.ItemBridgeAdapter.Wrapper
        public void wrap(View view, View view2) {
            ((FrameLayout) view).addView(view2);
        }

        @Override // androidx.leanback.widget.ItemBridgeAdapter.Wrapper
        public View createWrapper(View view) {
            return new NoOverlappingFrameLayout(view.getContext());
        }
    };

    @Deprecated
    /* loaded from: classes.dex */
    public interface OnHeaderClickedListener {
        void onHeaderClicked(RowHeaderPresenter.ViewHolder viewHolder, Row row);
    }

    @Deprecated
    /* loaded from: classes.dex */
    public interface OnHeaderViewSelectedListener {
        void onHeaderSelected(RowHeaderPresenter.ViewHolder viewHolder, Row row);
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public /* bridge */ /* synthetic */ int getSelectedPosition() {
        return super.getSelectedPosition();
    }

    @Override // androidx.leanback.app.BaseRowFragment, android.app.Fragment
    public /* bridge */ /* synthetic */ View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.leanback.app.BaseRowFragment, android.app.Fragment
    public /* bridge */ /* synthetic */ void onDestroyView() {
        super.onDestroyView();
    }

    @Override // androidx.leanback.app.BaseRowFragment, android.app.Fragment
    public /* bridge */ /* synthetic */ void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public /* bridge */ /* synthetic */ boolean onTransitionPrepare() {
        return super.onTransitionPrepare();
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public /* bridge */ /* synthetic */ void setAlignment(int i) {
        super.setAlignment(i);
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public /* bridge */ /* synthetic */ void setSelectedPosition(int i) {
        super.setSelectedPosition(i);
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public /* bridge */ /* synthetic */ void setSelectedPosition(int i, boolean z) {
        super.setSelectedPosition(i, z);
    }

    public HeadersFragment() {
        setPresenterSelector(sHeaderPresenter);
        FocusHighlightHelper.setupHeaderItemFocusHighlight(getBridgeAdapter());
    }

    public void setOnHeaderClickedListener(OnHeaderClickedListener onHeaderClickedListener) {
        this.mOnHeaderClickedListener = onHeaderClickedListener;
    }

    public void setOnHeaderViewSelectedListener(OnHeaderViewSelectedListener onHeaderViewSelectedListener) {
        this.mOnHeaderViewSelectedListener = onHeaderViewSelectedListener;
    }

    @Override // androidx.leanback.app.BaseRowFragment
    VerticalGridView findGridViewFromRoot(View view) {
        return (VerticalGridView) view.findViewById(R.id.browse_headers);
    }

    @Override // androidx.leanback.app.BaseRowFragment
    void onRowSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
        OnHeaderViewSelectedListener onHeaderViewSelectedListener = this.mOnHeaderViewSelectedListener;
        if (onHeaderViewSelectedListener != null) {
            if (viewHolder != null && i >= 0) {
                ItemBridgeAdapter.ViewHolder viewHolder2 = (ItemBridgeAdapter.ViewHolder) viewHolder;
                onHeaderViewSelectedListener.onHeaderSelected((RowHeaderPresenter.ViewHolder) viewHolder2.getViewHolder(), (Row) viewHolder2.getItem());
                return;
            }
            onHeaderViewSelectedListener.onHeaderSelected(null, null);
        }
    }

    @Override // androidx.leanback.app.BaseRowFragment
    int getLayoutResourceId() {
        return R.layout.lb_headers_fragment;
    }

    @Override // androidx.leanback.app.BaseRowFragment, android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView == null) {
            return;
        }
        if (this.mBackgroundColorSet) {
            verticalGridView.setBackgroundColor(this.mBackgroundColor);
            updateFadingEdgeToBrandColor(this.mBackgroundColor);
        } else {
            Drawable background = verticalGridView.getBackground();
            if (background instanceof ColorDrawable) {
                updateFadingEdgeToBrandColor(((ColorDrawable) background).getColor());
            }
        }
        updateListViewVisibility();
    }

    private void updateListViewVisibility() {
        VerticalGridView verticalGridView = getVerticalGridView();
        if (verticalGridView != null) {
            getView().setVisibility(this.mHeadersGone ? 8 : 0);
            if (this.mHeadersGone) {
                return;
            }
            if (this.mHeadersEnabled) {
                verticalGridView.setChildrenVisibility(0);
            } else {
                verticalGridView.setChildrenVisibility(4);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setHeadersEnabled(boolean z) {
        this.mHeadersEnabled = z;
        updateListViewVisibility();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setHeadersGone(boolean z) {
        this.mHeadersGone = z;
        updateListViewVisibility();
    }

    /* loaded from: classes.dex */
    static class NoOverlappingFrameLayout extends FrameLayout {
        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }

        public NoOverlappingFrameLayout(Context context) {
            super(context);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // androidx.leanback.app.BaseRowFragment
    public void updateAdapter() {
        super.updateAdapter();
        ItemBridgeAdapter bridgeAdapter = getBridgeAdapter();
        bridgeAdapter.setAdapterListener(this.mAdapterListener);
        bridgeAdapter.setWrapper(this.mWrapper);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        this.mBackgroundColorSet = true;
        if (getVerticalGridView() != null) {
            getVerticalGridView().setBackgroundColor(this.mBackgroundColor);
            updateFadingEdgeToBrandColor(this.mBackgroundColor);
        }
    }

    private void updateFadingEdgeToBrandColor(int i) {
        Drawable background = getView().findViewById(R.id.fade_out_edge).getBackground();
        if (background instanceof GradientDrawable) {
            background.mutate();
            ((GradientDrawable) background).setColors(new int[]{0, i});
        }
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public void onTransitionStart() {
        VerticalGridView verticalGridView;
        super.onTransitionStart();
        if (this.mHeadersEnabled || (verticalGridView = getVerticalGridView()) == null) {
            return;
        }
        verticalGridView.setDescendantFocusability(131072);
        if (verticalGridView.hasFocus()) {
            verticalGridView.requestFocus();
        }
    }

    @Override // androidx.leanback.app.BaseRowFragment
    public void onTransitionEnd() {
        VerticalGridView verticalGridView;
        if (this.mHeadersEnabled && (verticalGridView = getVerticalGridView()) != null) {
            verticalGridView.setDescendantFocusability(262144);
            if (verticalGridView.hasFocus()) {
                verticalGridView.requestFocus();
            }
        }
        super.onTransitionEnd();
    }

    public boolean isScrolling() {
        return getVerticalGridView().getScrollState() != 0;
    }
}
