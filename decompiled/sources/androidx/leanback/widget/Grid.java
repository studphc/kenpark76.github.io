package androidx.leanback.widget;

import android.util.SparseIntArray;
import androidx.collection.CircularIntArray;
import androidx.recyclerview.widget.RecyclerView;
import java.io.PrintWriter;
import java.util.Arrays;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class Grid {
    public static final int START_DEFAULT = -1;
    protected int mNumRows;
    protected Provider mProvider;
    protected boolean mReversedFlow;
    protected int mSpacing;
    protected CircularIntArray[] mTmpItemPositionsInRows;
    Object[] mTmpItem = new Object[1];
    protected int mFirstVisibleIndex = -1;
    protected int mLastVisibleIndex = -1;
    protected int mStartIndex = -1;

    /* loaded from: classes.dex */
    public interface Provider {
        void addItem(Object obj, int i, int i2, int i3, int i4);

        int createItem(int i, boolean z, Object[] objArr, boolean z2);

        int getCount();

        int getEdge(int i);

        int getMinIndex();

        int getSize(int i);

        void removeItem(int i);
    }

    protected abstract boolean appendVisibleItems(int i, boolean z);

    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
    }

    public abstract void debugPrint(PrintWriter printWriter);

    protected abstract int findRowMax(boolean z, int i, int[] iArr);

    protected abstract int findRowMin(boolean z, int i, int[] iArr);

    public abstract CircularIntArray[] getItemPositionsInRows(int i, int i2);

    public abstract Location getLocation(int i);

    protected abstract boolean prependVisibleItems(int i, boolean z);

    /* loaded from: classes.dex */
    public static class Location {
        public int row;

        public Location(int i) {
            this.row = i;
        }
    }

    public static Grid createGrid(int i) {
        if (i == 1) {
            return new SingleRow();
        }
        StaggeredGridDefault staggeredGridDefault = new StaggeredGridDefault();
        staggeredGridDefault.setNumRows(i);
        return staggeredGridDefault;
    }

    public final void setSpacing(int i) {
        this.mSpacing = i;
    }

    public final void setReversedFlow(boolean z) {
        this.mReversedFlow = z;
    }

    public boolean isReversedFlow() {
        return this.mReversedFlow;
    }

    public void setProvider(Provider provider) {
        this.mProvider = provider;
    }

    public void setStart(int i) {
        this.mStartIndex = i;
    }

    public int getNumRows() {
        return this.mNumRows;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setNumRows(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.mNumRows == i) {
            return;
        }
        this.mNumRows = i;
        this.mTmpItemPositionsInRows = new CircularIntArray[i];
        for (int i2 = 0; i2 < this.mNumRows; i2++) {
            this.mTmpItemPositionsInRows[i2] = new CircularIntArray();
        }
    }

    public final int getFirstVisibleIndex() {
        return this.mFirstVisibleIndex;
    }

    public final int getLastVisibleIndex() {
        return this.mLastVisibleIndex;
    }

    public void resetVisibleIndex() {
        this.mLastVisibleIndex = -1;
        this.mFirstVisibleIndex = -1;
    }

    public void invalidateItemsAfter(int i) {
        int i2;
        if (i >= 0 && (i2 = this.mLastVisibleIndex) >= 0) {
            if (i2 >= i) {
                this.mLastVisibleIndex = i - 1;
            }
            resetVisibleIndexIfEmpty();
            if (getFirstVisibleIndex() < 0) {
                setStart(i);
            }
        }
    }

    public final int getRowIndex(int i) {
        Location location = getLocation(i);
        if (location == null) {
            return -1;
        }
        return location.row;
    }

    public final int findRowMin(boolean z, int[] iArr) {
        return findRowMin(z, this.mReversedFlow ? this.mLastVisibleIndex : this.mFirstVisibleIndex, iArr);
    }

    public final int findRowMax(boolean z, int[] iArr) {
        return findRowMax(z, this.mReversedFlow ? this.mFirstVisibleIndex : this.mLastVisibleIndex, iArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean checkAppendOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMin(true, null) > i + this.mSpacing) {
                return false;
            }
        } else if (findRowMax(false, null) < i - this.mSpacing) {
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final boolean checkPrependOverLimit(int i) {
        if (this.mLastVisibleIndex < 0) {
            return false;
        }
        if (this.mReversedFlow) {
            if (findRowMax(false, null) < i - this.mSpacing) {
                return false;
            }
        } else if (findRowMin(true, null) > i + this.mSpacing) {
            return false;
        }
        return true;
    }

    public final CircularIntArray[] getItemPositionsInRows() {
        return getItemPositionsInRows(getFirstVisibleIndex(), getLastVisibleIndex());
    }

    public final boolean prependOneColumnVisibleItems() {
        return prependVisibleItems(this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE, true);
    }

    public final void prependVisibleItems(int i) {
        prependVisibleItems(i, false);
    }

    public boolean appendOneColumnVisibleItems() {
        return appendVisibleItems(this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE, true);
    }

    public final void appendVisibleItems(int i) {
        appendVisibleItems(i, false);
    }

    public void removeInvisibleItemsAtEnd(int i, int i2) {
        while (true) {
            int i3 = this.mLastVisibleIndex;
            if (i3 < this.mFirstVisibleIndex || i3 <= i) {
                break;
            }
            boolean z = false;
            if (this.mReversedFlow ? this.mProvider.getEdge(i3) <= i2 : this.mProvider.getEdge(i3) >= i2) {
                z = true;
            }
            if (!z) {
                break;
            }
            this.mProvider.removeItem(this.mLastVisibleIndex);
            this.mLastVisibleIndex--;
        }
        resetVisibleIndexIfEmpty();
    }

    public void removeInvisibleItemsAtFront(int i, int i2) {
        while (true) {
            int i3 = this.mLastVisibleIndex;
            int i4 = this.mFirstVisibleIndex;
            if (i3 < i4 || i4 >= i) {
                break;
            }
            int size = this.mProvider.getSize(i4);
            boolean z = false;
            if (this.mReversedFlow ? this.mProvider.getEdge(this.mFirstVisibleIndex) - size >= i2 : this.mProvider.getEdge(this.mFirstVisibleIndex) + size <= i2) {
                z = true;
            }
            if (!z) {
                break;
            }
            this.mProvider.removeItem(this.mFirstVisibleIndex);
            this.mFirstVisibleIndex++;
        }
        resetVisibleIndexIfEmpty();
    }

    private void resetVisibleIndexIfEmpty() {
        if (this.mLastVisibleIndex < this.mFirstVisibleIndex) {
            resetVisibleIndex();
        }
    }

    public void fillDisappearingItems(int[] iArr, int i, SparseIntArray sparseIntArray) {
        int edge;
        int edge2;
        int lastVisibleIndex = getLastVisibleIndex();
        int binarySearch = lastVisibleIndex >= 0 ? Arrays.binarySearch(iArr, 0, i, lastVisibleIndex) : 0;
        if (binarySearch < 0) {
            if (this.mReversedFlow) {
                edge2 = (this.mProvider.getEdge(lastVisibleIndex) - this.mProvider.getSize(lastVisibleIndex)) - this.mSpacing;
            } else {
                edge2 = this.mProvider.getEdge(lastVisibleIndex) + this.mProvider.getSize(lastVisibleIndex) + this.mSpacing;
            }
            int i2 = edge2;
            for (int i3 = (-binarySearch) - 1; i3 < i; i3++) {
                int i4 = iArr[i3];
                int i5 = sparseIntArray.get(i4);
                int i6 = i5 < 0 ? 0 : i5;
                int createItem = this.mProvider.createItem(i4, true, this.mTmpItem, true);
                this.mProvider.addItem(this.mTmpItem[0], i4, createItem, i6, i2);
                if (this.mReversedFlow) {
                    i2 = (i2 - createItem) - this.mSpacing;
                } else {
                    i2 = i2 + createItem + this.mSpacing;
                }
            }
        }
        int firstVisibleIndex = getFirstVisibleIndex();
        int binarySearch2 = firstVisibleIndex >= 0 ? Arrays.binarySearch(iArr, 0, i, firstVisibleIndex) : 0;
        if (binarySearch2 < 0) {
            if (this.mReversedFlow) {
                edge = this.mProvider.getEdge(firstVisibleIndex);
            } else {
                edge = this.mProvider.getEdge(firstVisibleIndex);
            }
            for (int i7 = (-binarySearch2) - 2; i7 >= 0; i7--) {
                int i8 = iArr[i7];
                int i9 = sparseIntArray.get(i8);
                int i10 = i9 < 0 ? 0 : i9;
                int createItem2 = this.mProvider.createItem(i8, false, this.mTmpItem, true);
                if (this.mReversedFlow) {
                    edge = edge + this.mSpacing + createItem2;
                } else {
                    edge = (edge - this.mSpacing) - createItem2;
                }
                this.mProvider.addItem(this.mTmpItem[0], i8, createItem2, i10, edge);
            }
        }
    }
}
