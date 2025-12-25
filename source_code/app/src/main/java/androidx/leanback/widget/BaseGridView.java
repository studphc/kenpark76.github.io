package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import androidx.leanback.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
/* loaded from: classes.dex */
public abstract class BaseGridView extends RecyclerView {
    public static final int FOCUS_SCROLL_ALIGNED = 0;
    public static final int FOCUS_SCROLL_ITEM = 1;
    public static final int FOCUS_SCROLL_PAGE = 2;
    public static final float ITEM_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    public static final int SAVE_ALL_CHILD = 3;
    public static final int SAVE_LIMITED_CHILD = 2;
    public static final int SAVE_NO_CHILD = 0;
    public static final int SAVE_ON_SCREEN_CHILD = 1;
    public static final int WINDOW_ALIGN_BOTH_EDGE = 3;
    public static final int WINDOW_ALIGN_HIGH_EDGE = 2;
    public static final int WINDOW_ALIGN_LOW_EDGE = 1;
    public static final int WINDOW_ALIGN_NO_EDGE = 0;
    public static final float WINDOW_ALIGN_OFFSET_PERCENT_DISABLED = -1.0f;
    private boolean mAnimateChildLayout;
    RecyclerView.RecyclerListener mChainedRecyclerListener;
    private boolean mHasOverlappingRendering;
    int mInitialPrefetchItemCount;
    final GridLayoutManager mLayoutManager;
    private OnKeyInterceptListener mOnKeyInterceptListener;
    private OnMotionInterceptListener mOnMotionInterceptListener;
    private OnTouchInterceptListener mOnTouchInterceptListener;
    private OnUnhandledKeyListener mOnUnhandledKeyListener;
    private RecyclerView.ItemAnimator mSavedItemAnimator;

    /* loaded from: classes.dex */
    public interface OnKeyInterceptListener {
        boolean onInterceptKeyEvent(KeyEvent keyEvent);
    }

    /* loaded from: classes.dex */
    public interface OnMotionInterceptListener {
        boolean onInterceptMotionEvent(MotionEvent motionEvent);
    }

    /* loaded from: classes.dex */
    public interface OnTouchInterceptListener {
        boolean onInterceptTouchEvent(MotionEvent motionEvent);
    }

    /* loaded from: classes.dex */
    public interface OnUnhandledKeyListener {
        boolean onUnhandledKey(KeyEvent keyEvent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAnimateChildLayout = true;
        this.mHasOverlappingRendering = true;
        this.mInitialPrefetchItemCount = 4;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this);
        this.mLayoutManager = gridLayoutManager;
        setLayoutManager(gridLayoutManager);
        setPreserveFocusAfterLayout(false);
        setDescendantFocusability(262144);
        setHasFixedSize(true);
        setChildrenDrawingOrderEnabled(true);
        setWillNotDraw(true);
        setOverScrollMode(2);
        ((SimpleItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
        super.setRecyclerListener(new RecyclerView.RecyclerListener() { // from class: androidx.leanback.widget.BaseGridView.1
            @Override // androidx.recyclerview.widget.RecyclerView.RecyclerListener
            public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
                BaseGridView.this.mLayoutManager.onChildRecycled(viewHolder);
                if (BaseGridView.this.mChainedRecyclerListener != null) {
                    BaseGridView.this.mChainedRecyclerListener.onViewRecycled(viewHolder);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void initBaseGridViewAttributes(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbBaseGridView);
        this.mLayoutManager.setFocusOutAllowed(obtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutFront, false), obtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutEnd, false));
        this.mLayoutManager.setFocusOutSideAllowed(obtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutSideStart, true), obtainStyledAttributes.getBoolean(R.styleable.lbBaseGridView_focusOutSideEnd, true));
        this.mLayoutManager.setVerticalSpacing(obtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_android_verticalSpacing, obtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_verticalMargin, 0)));
        this.mLayoutManager.setHorizontalSpacing(obtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_android_horizontalSpacing, obtainStyledAttributes.getDimensionPixelSize(R.styleable.lbBaseGridView_horizontalMargin, 0)));
        if (obtainStyledAttributes.hasValue(R.styleable.lbBaseGridView_android_gravity)) {
            setGravity(obtainStyledAttributes.getInt(R.styleable.lbBaseGridView_android_gravity, 0));
        }
        obtainStyledAttributes.recycle();
    }

    public void setFocusScrollStrategy(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException("Invalid scrollStrategy");
        }
        this.mLayoutManager.setFocusScrollStrategy(i);
        requestLayout();
    }

    public int getFocusScrollStrategy() {
        return this.mLayoutManager.getFocusScrollStrategy();
    }

    public void setWindowAlignment(int i) {
        this.mLayoutManager.setWindowAlignment(i);
        requestLayout();
    }

    public int getWindowAlignment() {
        return this.mLayoutManager.getWindowAlignment();
    }

    public void setWindowAlignmentPreferKeyLineOverLowEdge(boolean z) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverLowEdge(z);
        requestLayout();
    }

    public void setWindowAlignmentPreferKeyLineOverHighEdge(boolean z) {
        this.mLayoutManager.mWindowAlignment.mainAxis().setPreferKeylineOverHighEdge(z);
        requestLayout();
    }

    public boolean isWindowAlignmentPreferKeyLineOverLowEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverLowEdge();
    }

    public boolean isWindowAlignmentPreferKeyLineOverHighEdge() {
        return this.mLayoutManager.mWindowAlignment.mainAxis().isPreferKeylineOverHighEdge();
    }

    public void setWindowAlignmentOffset(int i) {
        this.mLayoutManager.setWindowAlignmentOffset(i);
        requestLayout();
    }

    public int getWindowAlignmentOffset() {
        return this.mLayoutManager.getWindowAlignmentOffset();
    }

    public void setWindowAlignmentOffsetPercent(float f) {
        this.mLayoutManager.setWindowAlignmentOffsetPercent(f);
        requestLayout();
    }

    public float getWindowAlignmentOffsetPercent() {
        return this.mLayoutManager.getWindowAlignmentOffsetPercent();
    }

    public void setItemAlignmentOffset(int i) {
        this.mLayoutManager.setItemAlignmentOffset(i);
        requestLayout();
    }

    public int getItemAlignmentOffset() {
        return this.mLayoutManager.getItemAlignmentOffset();
    }

    public void setItemAlignmentOffsetWithPadding(boolean z) {
        this.mLayoutManager.setItemAlignmentOffsetWithPadding(z);
        requestLayout();
    }

    public boolean isItemAlignmentOffsetWithPadding() {
        return this.mLayoutManager.isItemAlignmentOffsetWithPadding();
    }

    public void setItemAlignmentOffsetPercent(float f) {
        this.mLayoutManager.setItemAlignmentOffsetPercent(f);
        requestLayout();
    }

    public float getItemAlignmentOffsetPercent() {
        return this.mLayoutManager.getItemAlignmentOffsetPercent();
    }

    public void setItemAlignmentViewId(int i) {
        this.mLayoutManager.setItemAlignmentViewId(i);
    }

    public int getItemAlignmentViewId() {
        return this.mLayoutManager.getItemAlignmentViewId();
    }

    @Deprecated
    public void setItemMargin(int i) {
        setItemSpacing(i);
    }

    public void setItemSpacing(int i) {
        this.mLayoutManager.setItemSpacing(i);
        requestLayout();
    }

    @Deprecated
    public void setVerticalMargin(int i) {
        setVerticalSpacing(i);
    }

    @Deprecated
    public int getVerticalMargin() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    @Deprecated
    public void setHorizontalMargin(int i) {
        setHorizontalSpacing(i);
    }

    @Deprecated
    public int getHorizontalMargin() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public void setVerticalSpacing(int i) {
        this.mLayoutManager.setVerticalSpacing(i);
        requestLayout();
    }

    public int getVerticalSpacing() {
        return this.mLayoutManager.getVerticalSpacing();
    }

    public void setHorizontalSpacing(int i) {
        this.mLayoutManager.setHorizontalSpacing(i);
        requestLayout();
    }

    public int getHorizontalSpacing() {
        return this.mLayoutManager.getHorizontalSpacing();
    }

    public void setOnChildLaidOutListener(OnChildLaidOutListener onChildLaidOutListener) {
        this.mLayoutManager.setOnChildLaidOutListener(onChildLaidOutListener);
    }

    public void setOnChildSelectedListener(OnChildSelectedListener onChildSelectedListener) {
        this.mLayoutManager.setOnChildSelectedListener(onChildSelectedListener);
    }

    public void setOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.setOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    public void addOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.addOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    public void removeOnChildViewHolderSelectedListener(OnChildViewHolderSelectedListener onChildViewHolderSelectedListener) {
        this.mLayoutManager.removeOnChildViewHolderSelectedListener(onChildViewHolderSelectedListener);
    }

    public void setSelectedPosition(int i) {
        this.mLayoutManager.setSelection(i, 0);
    }

    public void setSelectedPositionWithSub(int i, int i2) {
        this.mLayoutManager.setSelectionWithSub(i, i2, 0);
    }

    public void setSelectedPosition(int i, int i2) {
        this.mLayoutManager.setSelection(i, i2);
    }

    public void setSelectedPositionWithSub(int i, int i2, int i3) {
        this.mLayoutManager.setSelectionWithSub(i, i2, i3);
    }

    public void setSelectedPositionSmooth(int i) {
        this.mLayoutManager.setSelectionSmooth(i);
    }

    public void setSelectedPositionSmoothWithSub(int i, int i2) {
        this.mLayoutManager.setSelectionSmoothWithSub(i, i2);
    }

    public void setSelectedPositionSmooth(final int i, final ViewHolderTask viewHolderTask) {
        if (viewHolderTask != null) {
            RecyclerView.ViewHolder findViewHolderForPosition = findViewHolderForPosition(i);
            if (findViewHolderForPosition == null || hasPendingAdapterUpdates()) {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.BaseGridView.2
                    @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                    public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                        if (i2 == i) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            viewHolderTask.run(viewHolder);
                        }
                    }
                });
            } else {
                viewHolderTask.run(findViewHolderForPosition);
            }
        }
        setSelectedPositionSmooth(i);
    }

    public void setSelectedPosition(final int i, final ViewHolderTask viewHolderTask) {
        if (viewHolderTask != null) {
            RecyclerView.ViewHolder findViewHolderForPosition = findViewHolderForPosition(i);
            if (findViewHolderForPosition == null || hasPendingAdapterUpdates()) {
                addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.BaseGridView.3
                    @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
                    public void onChildViewHolderSelectedAndPositioned(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                        if (i2 == i) {
                            BaseGridView.this.removeOnChildViewHolderSelectedListener(this);
                            viewHolderTask.run(viewHolder);
                        }
                    }
                });
            } else {
                viewHolderTask.run(findViewHolderForPosition);
            }
        }
        setSelectedPosition(i);
    }

    public int getSelectedPosition() {
        return this.mLayoutManager.getSelection();
    }

    public int getSelectedSubPosition() {
        return this.mLayoutManager.getSubSelection();
    }

    public void setAnimateChildLayout(boolean z) {
        if (this.mAnimateChildLayout != z) {
            this.mAnimateChildLayout = z;
            if (!z) {
                this.mSavedItemAnimator = getItemAnimator();
                super.setItemAnimator(null);
                return;
            }
            super.setItemAnimator(this.mSavedItemAnimator);
        }
    }

    public boolean isChildLayoutAnimated() {
        return this.mAnimateChildLayout;
    }

    public void setGravity(int i) {
        this.mLayoutManager.setGravity(i);
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mLayoutManager.gridOnRequestFocusInDescendants(this, i, rect);
    }

    public void getViewSelectedOffsets(View view, int[] iArr) {
        this.mLayoutManager.getViewSelectedOffsets(view, iArr);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        return this.mLayoutManager.getChildDrawingOrder(this, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean isChildrenDrawingOrderEnabledInternal() {
        return isChildrenDrawingOrderEnabled();
    }

    @Override // android.view.View
    public View focusSearch(int i) {
        if (isFocused()) {
            GridLayoutManager gridLayoutManager = this.mLayoutManager;
            View findViewByPosition = gridLayoutManager.findViewByPosition(gridLayoutManager.getSelection());
            if (findViewByPosition != null) {
                return focusSearch(findViewByPosition, i);
            }
        }
        return super.focusSearch(i);
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        this.mLayoutManager.onFocusChanged(z, i, rect);
    }

    public final void setFocusSearchDisabled(boolean z) {
        setDescendantFocusability(z ? 393216 : 262144);
        this.mLayoutManager.setFocusSearchDisabled(z);
    }

    public final boolean isFocusSearchDisabled() {
        return this.mLayoutManager.isFocusSearchDisabled();
    }

    public void setLayoutEnabled(boolean z) {
        this.mLayoutManager.setLayoutEnabled(z);
    }

    public void setChildrenVisibility(int i) {
        this.mLayoutManager.setChildrenVisibility(i);
    }

    public void setPruneChild(boolean z) {
        this.mLayoutManager.setPruneChild(z);
    }

    public void setScrollEnabled(boolean z) {
        this.mLayoutManager.setScrollEnabled(z);
    }

    public boolean isScrollEnabled() {
        return this.mLayoutManager.isScrollEnabled();
    }

    public boolean hasPreviousViewInSameRow(int i) {
        return this.mLayoutManager.hasPreviousViewInSameRow(i);
    }

    public void setFocusDrawingOrderEnabled(boolean z) {
        super.setChildrenDrawingOrderEnabled(z);
    }

    public boolean isFocusDrawingOrderEnabled() {
        return super.isChildrenDrawingOrderEnabled();
    }

    public void setOnTouchInterceptListener(OnTouchInterceptListener onTouchInterceptListener) {
        this.mOnTouchInterceptListener = onTouchInterceptListener;
    }

    public void setOnMotionInterceptListener(OnMotionInterceptListener onMotionInterceptListener) {
        this.mOnMotionInterceptListener = onMotionInterceptListener;
    }

    public void setOnKeyInterceptListener(OnKeyInterceptListener onKeyInterceptListener) {
        this.mOnKeyInterceptListener = onKeyInterceptListener;
    }

    public void setOnUnhandledKeyListener(OnUnhandledKeyListener onUnhandledKeyListener) {
        this.mOnUnhandledKeyListener = onUnhandledKeyListener;
    }

    public OnUnhandledKeyListener getOnUnhandledKeyListener() {
        return this.mOnUnhandledKeyListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        OnKeyInterceptListener onKeyInterceptListener = this.mOnKeyInterceptListener;
        if ((onKeyInterceptListener == null || !onKeyInterceptListener.onInterceptKeyEvent(keyEvent)) && !super.dispatchKeyEvent(keyEvent)) {
            OnUnhandledKeyListener onUnhandledKeyListener = this.mOnUnhandledKeyListener;
            return onUnhandledKeyListener != null && onUnhandledKeyListener.onUnhandledKey(keyEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        OnTouchInterceptListener onTouchInterceptListener = this.mOnTouchInterceptListener;
        if (onTouchInterceptListener == null || !onTouchInterceptListener.onInterceptTouchEvent(motionEvent)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        OnMotionInterceptListener onMotionInterceptListener = this.mOnMotionInterceptListener;
        if (onMotionInterceptListener == null || !onMotionInterceptListener.onInterceptMotionEvent(motionEvent)) {
            return super.dispatchGenericFocusedEvent(motionEvent);
        }
        return true;
    }

    public final int getSaveChildrenPolicy() {
        return this.mLayoutManager.mChildrenStates.getSavePolicy();
    }

    public final int getSaveChildrenLimitNumber() {
        return this.mLayoutManager.mChildrenStates.getLimitNumber();
    }

    public final void setSaveChildrenPolicy(int i) {
        this.mLayoutManager.mChildrenStates.setSavePolicy(i);
    }

    public final void setSaveChildrenLimitNumber(int i) {
        this.mLayoutManager.mChildrenStates.setLimitNumber(i);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    public void setHasOverlappingRendering(boolean z) {
        this.mHasOverlappingRendering = z;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        this.mLayoutManager.onRtlPropertiesChanged(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setRecyclerListener(RecyclerView.RecyclerListener recyclerListener) {
        this.mChainedRecyclerListener = recyclerListener;
    }

    public void setExtraLayoutSpace(int i) {
        this.mLayoutManager.setExtraLayoutSpace(i);
    }

    public int getExtraLayoutSpace() {
        return this.mLayoutManager.getExtraLayoutSpace();
    }

    public void animateOut() {
        this.mLayoutManager.slideOut();
    }

    public void animateIn() {
        this.mLayoutManager.slideIn();
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void scrollToPosition(int i) {
        if (this.mLayoutManager.isSlidingChildViews()) {
            this.mLayoutManager.setSelectionWithSub(i, 0, 0);
        } else {
            super.scrollToPosition(i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        if (this.mLayoutManager.isSlidingChildViews()) {
            this.mLayoutManager.setSelectionWithSub(i, 0, 0);
        } else {
            super.smoothScrollToPosition(i);
        }
    }

    public void setInitialPrefetchItemCount(int i) {
        this.mInitialPrefetchItemCount = i;
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }
}
