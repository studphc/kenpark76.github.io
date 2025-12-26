package androidx.leanback.widget;

import androidx.leanback.widget.StaggeredGrid;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class StaggeredGridDefault extends StaggeredGrid {
    int getRowMax(int i) {
        int i2;
        StaggeredGrid.Location location;
        if (this.mFirstVisibleIndex < 0) {
            return Integer.MIN_VALUE;
        }
        if (this.mReversedFlow) {
            int edge = this.mProvider.getEdge(this.mFirstVisibleIndex);
            if (getLocation(this.mFirstVisibleIndex).row == i) {
                return edge;
            }
            int i3 = this.mFirstVisibleIndex;
            do {
                i3++;
                if (i3 <= getLastIndex()) {
                    location = getLocation(i3);
                    edge += location.offset;
                }
            } while (location.row != i);
            return edge;
        }
        int edge2 = this.mProvider.getEdge(this.mLastVisibleIndex);
        StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
        if (location2.row == i) {
            i2 = location2.size;
        } else {
            int i4 = this.mLastVisibleIndex;
            while (true) {
                i4--;
                if (i4 < getFirstIndex()) {
                    break;
                }
                edge2 -= location2.offset;
                location2 = getLocation(i4);
                if (location2.row == i) {
                    i2 = location2.size;
                    break;
                }
            }
        }
        return edge2 + i2;
        return Integer.MIN_VALUE;
    }

    int getRowMin(int i) {
        StaggeredGrid.Location location;
        int i2;
        if (this.mFirstVisibleIndex < 0) {
            return Integer.MAX_VALUE;
        }
        if (this.mReversedFlow) {
            int edge = this.mProvider.getEdge(this.mLastVisibleIndex);
            StaggeredGrid.Location location2 = getLocation(this.mLastVisibleIndex);
            if (location2.row == i) {
                i2 = location2.size;
            } else {
                int i3 = this.mLastVisibleIndex;
                while (true) {
                    i3--;
                    if (i3 < getFirstIndex()) {
                        break;
                    }
                    edge -= location2.offset;
                    location2 = getLocation(i3);
                    if (location2.row == i) {
                        i2 = location2.size;
                        break;
                    }
                }
            }
            return edge - i2;
        }
        int edge2 = this.mProvider.getEdge(this.mFirstVisibleIndex);
        if (getLocation(this.mFirstVisibleIndex).row == i) {
            return edge2;
        }
        int i4 = this.mFirstVisibleIndex;
        do {
            i4++;
            if (i4 <= getLastIndex()) {
                location = getLocation(i4);
                edge2 += location.offset;
            }
        } while (location.row != i);
        return edge2;
        return Integer.MAX_VALUE;
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMax(boolean z, int i, int[] iArr) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.row;
        if (this.mReversedFlow) {
            i2 = i3;
            int i4 = i2;
            int i5 = 1;
            int i6 = edge;
            for (int i7 = i + 1; i5 < this.mNumRows && i7 <= this.mLastVisibleIndex; i7++) {
                StaggeredGrid.Location location2 = getLocation(i7);
                i6 += location2.offset;
                if (location2.row != i4) {
                    i4 = location2.row;
                    i5++;
                    if (z) {
                        if (i6 <= edge) {
                        }
                        edge = i6;
                        i = i7;
                        i2 = i4;
                    } else {
                        if (i6 >= edge) {
                        }
                        edge = i6;
                        i = i7;
                        i2 = i4;
                    }
                }
            }
        } else {
            int i8 = 1;
            int i9 = i3;
            StaggeredGrid.Location location3 = location;
            int i10 = edge;
            edge = this.mProvider.getSize(i) + edge;
            i2 = i9;
            for (int i11 = i - 1; i8 < this.mNumRows && i11 >= this.mFirstVisibleIndex; i11--) {
                i10 -= location3.offset;
                location3 = getLocation(i11);
                if (location3.row != i9) {
                    i9 = location3.row;
                    i8++;
                    int size = this.mProvider.getSize(i11) + i10;
                    if (z) {
                        if (size <= edge) {
                        }
                        i2 = i9;
                        i = i11;
                        edge = size;
                    } else {
                        if (size >= edge) {
                        }
                        i2 = i9;
                        i = i11;
                        edge = size;
                    }
                }
            }
        }
        if (iArr != null) {
            iArr[0] = i2;
            iArr[1] = i;
        }
        return edge;
    }

    @Override // androidx.leanback.widget.Grid
    public int findRowMin(boolean z, int i, int[] iArr) {
        int i2;
        int edge = this.mProvider.getEdge(i);
        StaggeredGrid.Location location = getLocation(i);
        int i3 = location.row;
        if (this.mReversedFlow) {
            int i4 = 1;
            i2 = edge - this.mProvider.getSize(i);
            int i5 = i3;
            for (int i6 = i - 1; i4 < this.mNumRows && i6 >= this.mFirstVisibleIndex; i6--) {
                edge -= location.offset;
                location = getLocation(i6);
                if (location.row != i5) {
                    i5 = location.row;
                    i4++;
                    int size = edge - this.mProvider.getSize(i6);
                    if (z) {
                        if (size <= i2) {
                        }
                        i3 = i5;
                        i = i6;
                        i2 = size;
                    } else {
                        if (size >= i2) {
                        }
                        i3 = i5;
                        i = i6;
                        i2 = size;
                    }
                }
            }
        } else {
            int i7 = i3;
            int i8 = i7;
            int i9 = 1;
            int i10 = edge;
            for (int i11 = i + 1; i9 < this.mNumRows && i11 <= this.mLastVisibleIndex; i11++) {
                StaggeredGrid.Location location2 = getLocation(i11);
                i10 += location2.offset;
                if (location2.row != i8) {
                    i8 = location2.row;
                    i9++;
                    if (z) {
                        if (i10 <= edge) {
                        }
                        edge = i10;
                        i = i11;
                        i7 = i8;
                    } else {
                        if (i10 >= edge) {
                        }
                        edge = i10;
                        i = i11;
                        i7 = i8;
                    }
                }
            }
            i2 = edge;
            i3 = i7;
        }
        if (iArr != null) {
            iArr[0] = i3;
            iArr[1] = i;
        }
        return i2;
    }

    private int findRowEdgeLimitSearchIndex(boolean z) {
        boolean z2 = false;
        if (z) {
            for (int i = this.mLastVisibleIndex; i >= this.mFirstVisibleIndex; i--) {
                int i2 = getLocation(i).row;
                if (i2 == 0) {
                    z2 = true;
                } else if (z2 && i2 == this.mNumRows - 1) {
                    return i;
                }
            }
            return -1;
        }
        for (int i3 = this.mFirstVisibleIndex; i3 <= this.mLastVisibleIndex; i3++) {
            int i4 = getLocation(i3).row;
            if (i4 == this.mNumRows - 1) {
                z2 = true;
            } else if (z2 && i4 == 0) {
                return i3;
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x0139, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x013a, code lost:
        r1 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0151, code lost:
        return r9;
     */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean appendVisibleItemsWithoutCache(int r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 358
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.appendVisibleItemsWithoutCache(int, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0134, code lost:
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0135, code lost:
        r0 = r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x014c, code lost:
        return r8;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0105 A[LOOP:2: B:86:0x0105->B:102:0x0129, LOOP_START, PHI: r0 r8 r9 
      PHI: (r0v14 int) = (r0v7 int), (r0v20 int) binds: [B:85:0x0103, B:102:0x0129] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r8v19 int) = (r8v17 int), (r8v20 int) binds: [B:85:0x0103, B:102:0x0129] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r9v7 int) = (r9v6 int), (r9v9 int) binds: [B:85:0x0103, B:102:0x0129] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // androidx.leanback.widget.StaggeredGrid
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean prependVisibleItemsWithoutCache(int r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.leanback.widget.StaggeredGridDefault.prependVisibleItemsWithoutCache(int, boolean):boolean");
    }
}
