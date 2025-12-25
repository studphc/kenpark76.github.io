package androidx.leanback.widget;
/* loaded from: classes.dex */
public class PageRow extends Row {
    @Override // androidx.leanback.widget.Row
    public final boolean isRenderedAsRowView() {
        return false;
    }

    public PageRow(HeaderItem headerItem) {
        super(headerItem);
    }
}
