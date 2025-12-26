package androidx.leanback.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.leanback.widget.Parallax;
import androidx.recyclerview.widget.RecyclerView;
/* loaded from: classes.dex */
public class RecyclerViewParallax extends Parallax<ChildPositionProperty> {
    boolean mIsVertical;
    RecyclerView mRecylerView;
    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.leanback.widget.RecyclerViewParallax.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            RecyclerViewParallax.this.updateValues();
        }
    };
    View.OnLayoutChangeListener mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: androidx.leanback.widget.RecyclerViewParallax.2
        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            RecyclerViewParallax.this.updateValues();
        }
    };

    /* loaded from: classes.dex */
    public static final class ChildPositionProperty extends Parallax.IntProperty {
        int mAdapterPosition;
        float mFraction;
        int mOffset;
        int mViewId;

        ChildPositionProperty(String str, int i) {
            super(str, i);
        }

        public ChildPositionProperty adapterPosition(int i) {
            this.mAdapterPosition = i;
            return this;
        }

        public ChildPositionProperty viewId(int i) {
            this.mViewId = i;
            return this;
        }

        public ChildPositionProperty offset(int i) {
            this.mOffset = i;
            return this;
        }

        public ChildPositionProperty fraction(float f) {
            this.mFraction = f;
            return this;
        }

        public int getAdapterPosition() {
            return this.mAdapterPosition;
        }

        public int getViewId() {
            return this.mViewId;
        }

        public int getOffset() {
            return this.mOffset;
        }

        public float getFraction() {
            return this.mFraction;
        }

        void updateValue(RecyclerViewParallax recyclerViewParallax) {
            RecyclerView recyclerView = recyclerViewParallax.mRecylerView;
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = recyclerView == null ? null : recyclerView.findViewHolderForAdapterPosition(this.mAdapterPosition);
            if (findViewHolderForAdapterPosition == null) {
                if (recyclerView == null || recyclerView.getLayoutManager().getChildCount() == 0) {
                    recyclerViewParallax.setIntPropertyValue(getIndex(), Integer.MAX_VALUE);
                    return;
                } else if (recyclerView.findContainingViewHolder(recyclerView.getLayoutManager().getChildAt(0)).getAdapterPosition() < this.mAdapterPosition) {
                    recyclerViewParallax.setIntPropertyValue(getIndex(), Integer.MAX_VALUE);
                    return;
                } else {
                    recyclerViewParallax.setIntPropertyValue(getIndex(), Integer.MIN_VALUE);
                    return;
                }
            }
            View findViewById = findViewHolderForAdapterPosition.itemView.findViewById(this.mViewId);
            if (findViewById == null) {
                return;
            }
            Rect rect = new Rect(0, 0, findViewById.getWidth(), findViewById.getHeight());
            recyclerView.offsetDescendantRectToMyCoords(findViewById, rect);
            float f = 0.0f;
            float f2 = 0.0f;
            while (findViewById != recyclerView && findViewById != null) {
                if (findViewById.getParent() != recyclerView || !recyclerView.isAnimating()) {
                    f += findViewById.getTranslationX();
                    f2 += findViewById.getTranslationY();
                }
                findViewById = (View) findViewById.getParent();
            }
            rect.offset((int) f, (int) f2);
            if (recyclerViewParallax.mIsVertical) {
                recyclerViewParallax.setIntPropertyValue(getIndex(), rect.top + this.mOffset + ((int) (this.mFraction * rect.height())));
            } else {
                recyclerViewParallax.setIntPropertyValue(getIndex(), rect.left + this.mOffset + ((int) (this.mFraction * rect.width())));
            }
        }
    }

    @Override // androidx.leanback.widget.Parallax
    public ChildPositionProperty createProperty(String str, int i) {
        return new ChildPositionProperty(str, i);
    }

    @Override // androidx.leanback.widget.Parallax
    public float getMaxValue() {
        RecyclerView recyclerView = this.mRecylerView;
        if (recyclerView == null) {
            return 0.0f;
        }
        return this.mIsVertical ? recyclerView.getHeight() : recyclerView.getWidth();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecylerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            recyclerView2.removeOnScrollListener(this.mOnScrollListener);
            this.mRecylerView.removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
        this.mRecylerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.getLayoutManager();
            this.mIsVertical = RecyclerView.LayoutManager.getProperties(this.mRecylerView.getContext(), null, 0, 0).orientation == 1;
            this.mRecylerView.addOnScrollListener(this.mOnScrollListener);
            this.mRecylerView.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        }
    }

    @Override // androidx.leanback.widget.Parallax
    public void updateValues() {
        for (ChildPositionProperty childPositionProperty : getProperties()) {
            childPositionProperty.updateValue(this);
        }
        super.updateValues();
    }

    public RecyclerView getRecyclerView() {
        return this.mRecylerView;
    }
}
