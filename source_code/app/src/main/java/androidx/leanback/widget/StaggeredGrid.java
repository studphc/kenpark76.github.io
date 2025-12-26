package androidx.leanback.widget;

import androidx.collection.CircularArray;
import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import java.io.PrintWriter;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public abstract class StaggeredGrid extends Grid {
    protected Object mPendingItem;
    protected int mPendingItemSize;
    protected CircularArray<Location> mLocations = new CircularArray<>(64);
    protected int mFirstIndex = -1;

    protected abstract boolean appendVisibleItemsWithoutCache(int i, boolean z);

    protected abstract boolean prependVisibleItemsWithoutCache(int i, boolean z);

    /* loaded from: classes.dex */
    public static class Location extends Grid.Location {
        public int offset;
        public int size;

        public Location(int i, int i2, int i3) {
            super(i);
            this.offset = i2;
            this.size = i3;
        }
    }

    public final int getFirstIndex() {
        return this.mFirstIndex;
    }

    public final int getLastIndex() {
        return (this.mFirstIndex + this.mLocations.size()) - 1;
    }

    public final int getSize() {
        return this.mLocations.size();
    }

    @Override // androidx.leanback.widget.Grid
    public final Location getLocation(int i) {
        int i2 = i - this.mFirstIndex;
        if (i2 < 0 || i2 >= this.mLocations.size()) {
            return null;
        }
        return this.mLocations.get(i2);
    }

    @Override // androidx.leanback.widget.Grid
    public final void debugPrint(PrintWriter printWriter) {
        int size = this.mLocations.size();
        for (int i = 0; i < size; i++) {
            printWriter.print("<" + (this.mFirstIndex + i) + "," + this.mLocations.get(i).row + ">");
            printWriter.print(" ");
            printWriter.println();
        }
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean prependVisibleItems(int i, boolean z) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (z || !checkPrependOverLimit(i)) {
            try {
                if (!prependVisbleItemsWithCache(i, z)) {
                    return prependVisibleItemsWithoutCache(i, z);
                }
                this.mTmpItem[0] = null;
                this.mPendingItem = null;
                return true;
            } finally {
                this.mTmpItem[0] = null;
                this.mPendingItem = null;
            }
        }
        return false;
    }

    protected final boolean prependVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        if (this.mLocations.size() == 0) {
            return false;
        }
        if (this.mFirstVisibleIndex >= 0) {
            i3 = this.mProvider.getEdge(this.mFirstVisibleIndex);
            i4 = getLocation(this.mFirstVisibleIndex).offset;
            i2 = this.mFirstVisibleIndex - 1;
        } else {
            i2 = this.mStartIndex != -1 ? this.mStartIndex : 0;
            if (i2 > getLastIndex() || i2 < getFirstIndex() - 1) {
                this.mLocations.clear();
                return false;
            } else if (i2 < getFirstIndex()) {
                return false;
            } else {
                i3 = Integer.MAX_VALUE;
                i4 = 0;
            }
        }
        int max = Math.max(this.mProvider.getMinIndex(), this.mFirstIndex);
        while (i2 >= max) {
            Location location = getLocation(i2);
            int i5 = location.row;
            int createItem = this.mProvider.createItem(i2, false, this.mTmpItem, false);
            if (createItem != location.size) {
                this.mLocations.removeFromStart((i2 + 1) - this.mFirstIndex);
                this.mFirstIndex = this.mFirstVisibleIndex;
                this.mPendingItem = this.mTmpItem[0];
                this.mPendingItemSize = createItem;
                return false;
            }
            this.mFirstVisibleIndex = i2;
            if (this.mLastVisibleIndex < 0) {
                this.mLastVisibleIndex = i2;
            }
            this.mProvider.addItem(this.mTmpItem[0], i2, createItem, i5, i3 - i4);
            if (!z && checkPrependOverLimit(i)) {
                return true;
            }
            i3 = this.mProvider.getEdge(i2);
            i4 = location.offset;
            if (i5 == 0 && z) {
                return true;
            }
            i2--;
        }
        return false;
    }

    private int calculateOffsetAfterLastItem(int i) {
        boolean z;
        int lastIndex = getLastIndex();
        while (true) {
            if (lastIndex < this.mFirstIndex) {
                z = false;
                break;
            } else if (getLocation(lastIndex).row == i) {
                z = true;
                break;
            } else {
                lastIndex--;
            }
        }
        if (!z) {
            lastIndex = getLastIndex();
        }
        int i2 = isReversedFlow() ? (-getLocation(lastIndex).size) - this.mSpacing : getLocation(lastIndex).size + this.mSpacing;
        for (int i3 = lastIndex + 1; i3 <= getLastIndex(); i3++) {
            i2 -= getLocation(i3).offset;
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int prependVisibleItemToRow(int i, int i2, int i3) {
        Object obj;
        if (this.mFirstVisibleIndex >= 0 && (this.mFirstVisibleIndex != getFirstIndex() || this.mFirstVisibleIndex != i + 1)) {
            throw new IllegalStateException();
        }
        int i4 = this.mFirstIndex;
        Location location = i4 >= 0 ? getLocation(i4) : null;
        int edge = this.mProvider.getEdge(this.mFirstIndex);
        Location location2 = new Location(i2, 0, 0);
        this.mLocations.addFirst(location2);
        if (this.mPendingItem != null) {
            location2.size = this.mPendingItemSize;
            obj = this.mPendingItem;
            this.mPendingItem = null;
        } else {
            location2.size = this.mProvider.createItem(i, false, this.mTmpItem, false);
            obj = this.mTmpItem[0];
        }
        Object obj2 = obj;
        this.mFirstVisibleIndex = i;
        this.mFirstIndex = i;
        if (this.mLastVisibleIndex < 0) {
            this.mLastVisibleIndex = i;
        }
        int i5 = !this.mReversedFlow ? i3 - location2.size : i3 + location2.size;
        if (location != null) {
            location.offset = edge - i5;
        }
        this.mProvider.addItem(obj2, i, location2.size, i2, i5);
        return location2.size;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean appendVisibleItems(int i, boolean z) {
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (z || !checkAppendOverLimit(i)) {
            try {
                if (!appendVisbleItemsWithCache(i, z)) {
                    return appendVisibleItemsWithoutCache(i, z);
                }
                this.mTmpItem[0] = null;
                this.mPendingItem = null;
                return true;
            } finally {
                this.mTmpItem[0] = null;
                this.mPendingItem = null;
            }
        }
        return false;
    }

    protected final boolean appendVisbleItemsWithCache(int i, boolean z) {
        int i2;
        int i3;
        if (this.mLocations.size() == 0) {
            return false;
        }
        int count = this.mProvider.getCount();
        if (this.mLastVisibleIndex >= 0) {
            i2 = this.mLastVisibleIndex + 1;
            i3 = this.mProvider.getEdge(this.mLastVisibleIndex);
        } else {
            i2 = this.mStartIndex != -1 ? this.mStartIndex : 0;
            if (i2 > getLastIndex() + 1 || i2 < getFirstIndex()) {
                this.mLocations.clear();
                return false;
            } else if (i2 > getLastIndex()) {
                return false;
            } else {
                i3 = Integer.MAX_VALUE;
            }
        }
        int lastIndex = getLastIndex();
        while (i2 < count && i2 <= lastIndex) {
            Location location = getLocation(i2);
            if (i3 != Integer.MAX_VALUE) {
                i3 += location.offset;
            }
            int i4 = location.row;
            int createItem = this.mProvider.createItem(i2, true, this.mTmpItem, false);
            if (createItem != location.size) {
                location.size = createItem;
                this.mLocations.removeFromEnd(lastIndex - i2);
                lastIndex = i2;
            }
            this.mLastVisibleIndex = i2;
            if (this.mFirstVisibleIndex < 0) {
                this.mFirstVisibleIndex = i2;
            }
            this.mProvider.addItem(this.mTmpItem[0], i2, createItem, i4, i3);
            if (!z && checkAppendOverLimit(i)) {
                return true;
            }
            if (i3 == Integer.MAX_VALUE) {
                i3 = this.mProvider.getEdge(i2);
            }
            if (i4 == this.mNumRows - 1 && z) {
                return true;
            }
            i2++;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int appendVisibleItemToRow(int i, int i2, int i3) {
        int edge;
        Object obj;
        if (this.mLastVisibleIndex >= 0 && (this.mLastVisibleIndex != getLastIndex() || this.mLastVisibleIndex != i - 1)) {
            throw new IllegalStateException();
        }
        if (this.mLastVisibleIndex < 0) {
            edge = (this.mLocations.size() <= 0 || i != getLastIndex() + 1) ? 0 : calculateOffsetAfterLastItem(i2);
        } else {
            edge = i3 - this.mProvider.getEdge(this.mLastVisibleIndex);
        }
        Location location = new Location(i2, edge, 0);
        this.mLocations.addLast(location);
        if (this.mPendingItem != null) {
            location.size = this.mPendingItemSize;
            obj = this.mPendingItem;
            this.mPendingItem = null;
        } else {
            location.size = this.mProvider.createItem(i, true, this.mTmpItem, false);
            obj = this.mTmpItem[0];
        }
        Object obj2 = obj;
        if (this.mLocations.size() == 1) {
            this.mLastVisibleIndex = i;
            this.mFirstVisibleIndex = i;
            this.mFirstIndex = i;
        } else if (this.mLastVisibleIndex < 0) {
            this.mLastVisibleIndex = i;
            this.mFirstVisibleIndex = i;
        } else {
            this.mLastVisibleIndex++;
        }
        this.mProvider.addItem(obj2, i, location.size, i2, i3);
        return location.size;
    }

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        for (int i3 = 0; i3 < this.mNumRows; i3++) {
            this.mTmpItemPositionsInRows[i3].clear();
        }
        if (i >= 0) {
            while (i <= i2) {
                CircularIntArray circularIntArray = this.mTmpItemPositionsInRows[getLocation(i).row];
                if (circularIntArray.size() > 0 && circularIntArray.getLast() == i - 1) {
                    circularIntArray.popLast();
                    circularIntArray.addLast(i);
                } else {
                    circularIntArray.addLast(i);
                    circularIntArray.addLast(i);
                }
                i++;
            }
        }
        return this.mTmpItemPositionsInRows;
    }

    @Override // androidx.leanback.widget.Grid
    public void invalidateItemsAfter(int i) {
        super.invalidateItemsAfter(i);
        this.mLocations.removeFromEnd((getLastIndex() - i) + 1);
        if (this.mLocations.size() == 0) {
            this.mFirstIndex = -1;
        }
    }
}
