package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;
/* loaded from: classes3.dex */
final class ExpandedRow {
    private final List<ExpandedPair> pairs;
    private final int rowNumber;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExpandedRow(List<ExpandedPair> list, int i) {
        this.pairs = new ArrayList(list);
        this.rowNumber = i;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ExpandedPair> getPairs() {
        return this.pairs;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getRowNumber() {
        return this.rowNumber;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isEquivalent(List<ExpandedPair> list) {
        return this.pairs.equals(list);
    }

    public String toString() {
        return "{ " + this.pairs + " }";
    }

    public boolean equals(Object obj) {
        if (obj instanceof ExpandedRow) {
            return this.pairs.equals(((ExpandedRow) obj).pairs);
        }
        return false;
    }

    public int hashCode() {
        return this.pairs.hashCode();
    }
}
