package androidx.leanback.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.core.view.ViewCompat;
import androidx.leanback.R;
import androidx.leanback.widget.MultiActionsProvider;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public abstract class AbstractMediaItemPresenter extends RowPresenter {
    public static final int PLAY_STATE_INITIAL = 0;
    public static final int PLAY_STATE_PAUSED = 1;
    public static final int PLAY_STATE_PLAYING = 2;
    static final Rect sTempRect = new Rect();
    private int mBackgroundColor;
    private boolean mBackgroundColorSet;
    private Presenter mMediaItemActionPresenter;
    private boolean mMediaRowSeparator;
    private int mThemeId;

    protected int getMediaPlayState(Object obj) {
        return 0;
    }

    @Override // androidx.leanback.widget.RowPresenter
    protected boolean isClippingChildren() {
        return true;
    }

    @Override // androidx.leanback.widget.RowPresenter
    public boolean isUsingDefaultSelectEffect() {
        return false;
    }

    protected abstract void onBindMediaDetails(ViewHolder viewHolder, Object obj);

    protected void onUnbindMediaDetails(ViewHolder viewHolder) {
    }

    public void onUnbindMediaPlayState(ViewHolder viewHolder) {
    }

    public AbstractMediaItemPresenter() {
        this(0);
    }

    public AbstractMediaItemPresenter(int i) {
        this.mBackgroundColor = 0;
        this.mMediaItemActionPresenter = new MediaItemActionPresenter();
        this.mThemeId = i;
        setHeaderPresenter(null);
    }

    public void setThemeId(int i) {
        this.mThemeId = i;
    }

    public int getThemeId() {
        return this.mThemeId;
    }

    public void setActionPresenter(Presenter presenter) {
        this.mMediaItemActionPresenter = presenter;
    }

    public Presenter getActionPresenter() {
        return this.mMediaItemActionPresenter;
    }

    /* loaded from: classes.dex */
    public static class ViewHolder extends RowPresenter.ViewHolder {
        private final List<Presenter.ViewHolder> mActionViewHolders;
        ValueAnimator mFocusViewAnimator;
        private final ViewGroup mMediaItemActionsContainer;
        private final View mMediaItemDetailsView;
        private final TextView mMediaItemDurationView;
        private final TextView mMediaItemNameView;
        final TextView mMediaItemNumberView;
        final ViewFlipper mMediaItemNumberViewFlipper;
        final View mMediaItemPausedView;
        final View mMediaItemPlayingView;
        MultiActionsProvider.MultiAction[] mMediaItemRowActions;
        private final View mMediaItemRowSeparator;
        final View mMediaRowView;
        AbstractMediaItemPresenter mRowPresenter;
        final View mSelectorView;

        public ViewHolder(View view) {
            super(view);
            this.mSelectorView = view.findViewById(R.id.mediaRowSelector);
            this.mMediaRowView = view.findViewById(R.id.mediaItemRow);
            this.mMediaItemDetailsView = view.findViewById(R.id.mediaItemDetails);
            this.mMediaItemNameView = (TextView) view.findViewById(R.id.mediaItemName);
            this.mMediaItemDurationView = (TextView) view.findViewById(R.id.mediaItemDuration);
            this.mMediaItemRowSeparator = view.findViewById(R.id.mediaRowSeparator);
            this.mMediaItemActionsContainer = (ViewGroup) view.findViewById(R.id.mediaItemActionsContainer);
            this.mActionViewHolders = new ArrayList();
            getMediaItemDetailsView().setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                        BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                        ViewHolder viewHolder = ViewHolder.this;
                        onItemViewClickedListener.onItemClicked(null, null, viewHolder, viewHolder.getRowObject());
                    }
                }
            });
            getMediaItemDetailsView().setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.2
                @Override // android.view.View.OnFocusChangeListener
                public void onFocusChange(View view2, boolean z) {
                    ViewHolder viewHolder = ViewHolder.this;
                    viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view2, ViewHolder.this.mFocusViewAnimator, true);
                }
            });
            ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.mediaItemNumberViewFlipper);
            this.mMediaItemNumberViewFlipper = viewFlipper;
            TypedValue typedValue = new TypedValue();
            View inflate = LayoutInflater.from(view.getContext()).inflate(view.getContext().getTheme().resolveAttribute(R.attr.playbackMediaItemNumberViewFlipperLayout, typedValue, true) ? typedValue.resourceId : R.layout.lb_media_item_number_view_flipper, (ViewGroup) viewFlipper, true);
            this.mMediaItemNumberView = (TextView) inflate.findViewById(R.id.initial);
            this.mMediaItemPausedView = inflate.findViewById(R.id.paused);
            this.mMediaItemPlayingView = inflate.findViewById(R.id.playing);
        }

        public void onBindRowActions() {
            int childCount = getMediaItemActionsContainer().getChildCount();
            while (true) {
                childCount--;
                if (childCount < this.mActionViewHolders.size()) {
                    break;
                }
                getMediaItemActionsContainer().removeViewAt(childCount);
                this.mActionViewHolders.remove(childCount);
            }
            this.mMediaItemRowActions = null;
            Object rowObject = getRowObject();
            if (rowObject instanceof MultiActionsProvider) {
                MultiActionsProvider.MultiAction[] actions = ((MultiActionsProvider) rowObject).getActions();
                Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
                if (actionPresenter == null) {
                    return;
                }
                this.mMediaItemRowActions = actions;
                for (final int size = this.mActionViewHolders.size(); size < actions.length; size++) {
                    final Presenter.ViewHolder onCreateViewHolder = actionPresenter.onCreateViewHolder(getMediaItemActionsContainer());
                    getMediaItemActionsContainer().addView(onCreateViewHolder.view);
                    this.mActionViewHolders.add(onCreateViewHolder);
                    onCreateViewHolder.view.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.3
                        @Override // android.view.View.OnFocusChangeListener
                        public void onFocusChange(View view, boolean z) {
                            ViewHolder viewHolder = ViewHolder.this;
                            viewHolder.mFocusViewAnimator = AbstractMediaItemPresenter.updateSelector(viewHolder.mSelectorView, view, ViewHolder.this.mFocusViewAnimator, false);
                        }
                    });
                    onCreateViewHolder.view.setOnClickListener(new View.OnClickListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.ViewHolder.4
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            if (ViewHolder.this.getOnItemViewClickedListener() != null) {
                                BaseOnItemViewClickedListener onItemViewClickedListener = ViewHolder.this.getOnItemViewClickedListener();
                                Presenter.ViewHolder viewHolder = onCreateViewHolder;
                                MultiActionsProvider.MultiAction multiAction = ViewHolder.this.mMediaItemRowActions[size];
                                ViewHolder viewHolder2 = ViewHolder.this;
                                onItemViewClickedListener.onItemClicked(viewHolder, multiAction, viewHolder2, viewHolder2.getRowObject());
                            }
                        }
                    });
                }
                if (this.mMediaItemActionsContainer != null) {
                    for (int i = 0; i < actions.length; i++) {
                        Presenter.ViewHolder viewHolder = this.mActionViewHolders.get(i);
                        actionPresenter.onUnbindViewHolder(viewHolder);
                        actionPresenter.onBindViewHolder(viewHolder, this.mMediaItemRowActions[i]);
                    }
                }
            }
        }

        int findActionIndex(MultiActionsProvider.MultiAction multiAction) {
            if (this.mMediaItemRowActions == null) {
                return -1;
            }
            int i = 0;
            while (true) {
                MultiActionsProvider.MultiAction[] multiActionArr = this.mMediaItemRowActions;
                if (i >= multiActionArr.length) {
                    return -1;
                }
                if (multiActionArr[i] == multiAction) {
                    return i;
                }
                i++;
            }
        }

        public void notifyActionChanged(MultiActionsProvider.MultiAction multiAction) {
            int findActionIndex;
            Presenter actionPresenter = this.mRowPresenter.getActionPresenter();
            if (actionPresenter != null && (findActionIndex = findActionIndex(multiAction)) >= 0) {
                Presenter.ViewHolder viewHolder = this.mActionViewHolders.get(findActionIndex);
                actionPresenter.onUnbindViewHolder(viewHolder);
                actionPresenter.onBindViewHolder(viewHolder, multiAction);
            }
        }

        public void notifyDetailsChanged() {
            this.mRowPresenter.onUnbindMediaDetails(this);
            this.mRowPresenter.onBindMediaDetails(this, getRowObject());
        }

        public void notifyPlayStateChanged() {
            this.mRowPresenter.onBindMediaPlayState(this);
        }

        public View getSelectorView() {
            return this.mSelectorView;
        }

        public ViewFlipper getMediaItemNumberViewFlipper() {
            return this.mMediaItemNumberViewFlipper;
        }

        public TextView getMediaItemNumberView() {
            return this.mMediaItemNumberView;
        }

        public View getMediaItemPausedView() {
            return this.mMediaItemPausedView;
        }

        public View getMediaItemPlayingView() {
            return this.mMediaItemPlayingView;
        }

        public void setSelectedMediaItemNumberView(int i) {
            if (i < 0 || i >= this.mMediaItemNumberViewFlipper.getChildCount()) {
                return;
            }
            this.mMediaItemNumberViewFlipper.setDisplayedChild(i);
        }

        public TextView getMediaItemNameView() {
            return this.mMediaItemNameView;
        }

        public TextView getMediaItemDurationView() {
            return this.mMediaItemDurationView;
        }

        public View getMediaItemDetailsView() {
            return this.mMediaItemDetailsView;
        }

        public View getMediaItemRowSeparator() {
            return this.mMediaItemRowSeparator;
        }

        public ViewGroup getMediaItemActionsContainer() {
            return this.mMediaItemActionsContainer;
        }

        public MultiActionsProvider.MultiAction[] getMediaItemRowActions() {
            return this.mMediaItemRowActions;
        }
    }

    @Override // androidx.leanback.widget.RowPresenter
    protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        if (this.mThemeId != 0) {
            context = new ContextThemeWrapper(context, this.mThemeId);
        }
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lb_row_media_item, viewGroup, false));
        viewHolder.mRowPresenter = this;
        if (this.mBackgroundColorSet) {
            viewHolder.mMediaRowView.setBackgroundColor(this.mBackgroundColor);
        }
        return viewHolder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.leanback.widget.RowPresenter
    public void onBindRowViewHolder(RowPresenter.ViewHolder viewHolder, Object obj) {
        super.onBindRowViewHolder(viewHolder, obj);
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        onBindRowActions(viewHolder2);
        viewHolder2.getMediaItemRowSeparator().setVisibility(hasMediaRowSeparator() ? 0 : 8);
        onBindMediaPlayState(viewHolder2);
        onBindMediaDetails(viewHolder2, obj);
    }

    protected void onBindRowActions(ViewHolder viewHolder) {
        viewHolder.onBindRowActions();
    }

    public void setBackgroundColor(int i) {
        this.mBackgroundColorSet = true;
        this.mBackgroundColor = i;
    }

    public void setHasMediaRowSeparator(boolean z) {
        this.mMediaRowSeparator = z;
    }

    public boolean hasMediaRowSeparator() {
        return this.mMediaRowSeparator;
    }

    public void onBindMediaPlayState(ViewHolder viewHolder) {
        int calculateMediaItemNumberFlipperIndex = calculateMediaItemNumberFlipperIndex(viewHolder);
        if (calculateMediaItemNumberFlipperIndex == -1 || viewHolder.mMediaItemNumberViewFlipper.getDisplayedChild() == calculateMediaItemNumberFlipperIndex) {
            return;
        }
        viewHolder.mMediaItemNumberViewFlipper.setDisplayedChild(calculateMediaItemNumberFlipperIndex);
    }

    static int calculateMediaItemNumberFlipperIndex(ViewHolder viewHolder) {
        int indexOfChild;
        int mediaPlayState = viewHolder.mRowPresenter.getMediaPlayState(viewHolder.getRowObject());
        if (mediaPlayState != 0) {
            if (mediaPlayState == 1) {
                if (viewHolder.mMediaItemPausedView == null) {
                    return -1;
                }
                indexOfChild = viewHolder.mMediaItemNumberViewFlipper.indexOfChild(viewHolder.mMediaItemPausedView);
            } else if (mediaPlayState != 2 || viewHolder.mMediaItemPlayingView == null) {
                return -1;
            } else {
                indexOfChild = viewHolder.mMediaItemNumberViewFlipper.indexOfChild(viewHolder.mMediaItemPlayingView);
            }
        } else if (viewHolder.mMediaItemNumberView == null) {
            return -1;
        } else {
            indexOfChild = viewHolder.mMediaItemNumberViewFlipper.indexOfChild(viewHolder.mMediaItemNumberView);
        }
        return indexOfChild;
    }

    static ValueAnimator updateSelector(final View view, View view2, ValueAnimator valueAnimator, boolean z) {
        ValueAnimator valueAnimator2;
        int integer = view2.getContext().getResources().getInteger(17694720);
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
        int layoutDirection = ViewCompat.getLayoutDirection(view);
        if (!view2.hasFocus()) {
            view.animate().cancel();
            view.animate().alpha(0.0f).setDuration(integer).setInterpolator(decelerateInterpolator).start();
            return valueAnimator;
        }
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator2 = null;
        } else {
            valueAnimator2 = valueAnimator;
        }
        float alpha = view.getAlpha();
        long j = integer;
        view.animate().alpha(1.0f).setDuration(j).setInterpolator(decelerateInterpolator).start();
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        Rect rect = sTempRect;
        rect.set(0, 0, view2.getWidth(), view2.getHeight());
        viewGroup.offsetDescendantRectToMyCoords(view2, rect);
        if (z) {
            if (layoutDirection == 1) {
                rect.right += viewGroup.getHeight();
                rect.left -= viewGroup.getHeight() / 2;
            } else {
                rect.left -= viewGroup.getHeight();
                rect.right += viewGroup.getHeight() / 2;
            }
        }
        final int i = rect.left;
        final int width = rect.width();
        final float f = marginLayoutParams.width - width;
        final float f2 = marginLayoutParams.leftMargin - i;
        if (f2 == 0.0f && f == 0.0f) {
            return valueAnimator2;
        }
        if (alpha == 0.0f) {
            marginLayoutParams.width = width;
            marginLayoutParams.leftMargin = i;
            view.requestLayout();
            return valueAnimator2;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(decelerateInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: androidx.leanback.widget.AbstractMediaItemPresenter.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator3) {
                float animatedFraction = 1.0f - valueAnimator3.getAnimatedFraction();
                marginLayoutParams.leftMargin = Math.round(i + (f2 * animatedFraction));
                marginLayoutParams.width = Math.round(width + (f * animatedFraction));
                view.requestLayout();
            }
        });
        ofFloat.start();
        return ofFloat;
    }
}
