package androidx.leanback.widget;

import androidx.collection.CircularIntArray;
import androidx.leanback.widget.Grid;
import androidx.recyclerview.widget.RecyclerView;
import java.io.PrintWriter;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class SingleRow extends Grid {
    private final Grid.Location mTmpLocation = new Grid.Location(0);

    /* JADX INFO: Access modifiers changed from: package-private */
    public SingleRow() {
        setNumRows(1);
    }

    @Override // androidx.leanback.widget.Grid
    public final Grid.Location getLocation(int i) {
        return this.mTmpLocation;
    }

    @Override // androidx.leanback.widget.Grid
    public final void debugPrint(PrintWriter printWriter) {
        printWriter.print("SingleRow<");
        printWriter.print(this.mFirstVisibleIndex);
        printWriter.print(",");
        printWriter.print(this.mLastVisibleIndex);
        printWriter.print(">");
        printWriter.println();
    }

    int getStartIndexForAppend() {
        if (this.mLastVisibleIndex >= 0) {
            return this.mLastVisibleIndex + 1;
        }
        if (this.mStartIndex != -1) {
            return Math.min(this.mStartIndex, this.mProvider.getCount() - 1);
        }
        return 0;
    }

    int getStartIndexForPrepend() {
        if (this.mFirstVisibleIndex >= 0) {
            return this.mFirstVisibleIndex - 1;
        }
        if (this.mStartIndex != -1) {
            return Math.min(this.mStartIndex, this.mProvider.getCount() - 1);
        }
        return this.mProvider.getCount() - 1;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean prependVisibleItems(int i, boolean z) {
        int i2;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (z || !checkPrependOverLimit(i)) {
            int minIndex = this.mProvider.getMinIndex();
            boolean z2 = false;
            for (int startIndexForPrepend = getStartIndexForPrepend(); startIndexForPrepend >= minIndex; startIndexForPrepend--) {
                int createItem = this.mProvider.createItem(startIndexForPrepend, false, this.mTmpItem, false);
                if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                    i2 = this.mReversedFlow ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    this.mFirstVisibleIndex = startIndexForPrepend;
                    this.mLastVisibleIndex = startIndexForPrepend;
                } else {
                    if (this.mReversedFlow) {
                        i2 = this.mProvider.getEdge(startIndexForPrepend + 1) + this.mSpacing + createItem;
                    } else {
                        i2 = (this.mProvider.getEdge(startIndexForPrepend + 1) - this.mSpacing) - createItem;
                    }
                    this.mFirstVisibleIndex = startIndexForPrepend;
                }
                this.mProvider.addItem(this.mTmpItem[0], startIndexForPrepend, createItem, 0, i2);
                z2 = true;
                if (z || checkPrependOverLimit(i)) {
                    break;
                }
            }
            return z2;
        }
        return false;
    }

    @Override // androidx.leanback.widget.Grid
    protected final boolean appendVisibleItems(int i, boolean z) {
        int i2;
        if (this.mProvider.getCount() == 0) {
            return false;
        }
        if (z || !checkAppendOverLimit(i)) {
            int startIndexForAppend = getStartIndexForAppend();
            boolean z2 = false;
            while (startIndexForAppend < this.mProvider.getCount()) {
                int createItem = this.mProvider.createItem(startIndexForAppend, true, this.mTmpItem, false);
                if (this.mFirstVisibleIndex < 0 || this.mLastVisibleIndex < 0) {
                    i2 = this.mReversedFlow ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                    this.mFirstVisibleIndex = startIndexForAppend;
                    this.mLastVisibleIndex = startIndexForAppend;
                } else {
                    if (this.mReversedFlow) {
                        int i3 = startIndexForAppend - 1;
                        i2 = (this.mProvider.getEdge(i3) - this.mProvider.getSize(i3)) - this.mSpacing;
                    } else {
                        int i4 = startIndexForAppend - 1;
                        i2 = this.mProvider.getEdge(i4) + this.mProvider.getSize(i4) + this.mSpacing;
                    }
                    this.mLastVisibleIndex = startIndexForAppend;
                }
                this.mProvider.addItem(this.mTmpItem[0], startIndexForAppend, createItem, 0, i2);
                if (z || checkAppendOverLimit(i)) {
                    return true;
                }
                startIndexForAppend++;
                z2 = true;
            }
            return z2;
        }
        return false;
    }

    @Override // androidx.leanback.widget.Grid
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int startIndexForPrepend;
        int edge;
        if (!this.mReversedFlow ? i2 < 0 : i2 > 0) {
            if (getFirstVisibleIndex() == 0) {
                return;
            }
            startIndexForPrepend = getStartIndexForPrepend();
            edge = this.mProvider.getEdge(this.mFirstVisibleIndex) + (this.mReversedFlow ? this.mSpacing : -this.mSpacing);
        } else if (getLastVisibleIndex() == this.mProvider.getCount() - 1) {
            return;
        } else {
            startIndexForPrepend = getStartIndexForAppend();
            int size = this.mProvider.getSize(this.mLastVisibleIndex) + this.mSpacing;
            int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
            if (this.mReversedFlow) {
                size = -size;
            }
            edge = size + edge2;
        }
        layoutPrefetchRegistry.addPosition(startIndexForPrepend, Math.abs(edge - i));
    }

    @Override // androidx.leanback.widget.Grid
    public final CircularIntArray[] getItemPositionsInRows(int i, int i2) {
        this.mTmpItemPositionsInRows[0].clear();
        this.mTmpItemPositionsInRows[0].addLast(i);
        this.mTmpItemPositionsInRows[0].addLast(i2);
        return this.mTmpItemPositionsInRows;
    }

    @Override // androidx.leanback.widget.Grid
    protected final int findRowMin(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? this.mProvider.getEdge(i) - this.mProvider.getSize(i) : this.mProvider.getEdge(i);
    }

    @Override // androidx.leanback.widget.Grid
    protected final int findRowMax(boolean z, int i, int[] iArr) {
        if (iArr != null) {
            iArr[0] = 0;
            iArr[1] = i;
        }
        return this.mReversedFlow ? this.mProvider.getEdge(i) : this.mProvider.getEdge(i) + this.mProvider.getSize(i);
    }
}
