package androidx.leanback.widget;
/* loaded from: classes.dex */
public class SectionRow extends Row {
    @Override // androidx.leanback.widget.Row
    public final boolean isRenderedAsRowView() {
        return false;
    }

    public SectionRow(HeaderItem headerItem) {
        super(headerItem);
    }

    public SectionRow(long j, String str) {
        super(new HeaderItem(j, str));
    }

    public SectionRow(String str) {
        super(new HeaderItem(str));
    }
}
