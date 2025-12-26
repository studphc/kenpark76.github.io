package com.pjy.koreatv.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: EPG.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lcom/pjy/koreatv/models/EPG;", "", "title", "", "beginTime", "", "endTime", "(Ljava/lang/String;II)V", "getBeginTime", "()I", "getEndTime", "getTitle", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class EPG {
    private final int beginTime;
    private final int endTime;
    private final String title;

    public static /* synthetic */ EPG copy$default(EPG epg, String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = epg.title;
        }
        if ((i3 & 2) != 0) {
            i = epg.beginTime;
        }
        if ((i3 & 4) != 0) {
            i2 = epg.endTime;
        }
        return epg.copy(str, i, i2);
    }

    public final String component1() {
        return this.title;
    }

    public final int component2() {
        return this.beginTime;
    }

    public final int component3() {
        return this.endTime;
    }

    public final EPG copy(String title, int i, int i2) {
        Intrinsics.checkNotNullParameter(title, "title");
        return new EPG(title, i, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof EPG) {
            EPG epg = (EPG) obj;
            return Intrinsics.areEqual(this.title, epg.title) && this.beginTime == epg.beginTime && this.endTime == epg.endTime;
        }
        return false;
    }

    public int hashCode() {
        return (((this.title.hashCode() * 31) + this.beginTime) * 31) + this.endTime;
    }

    public String toString() {
        return "EPG(title=" + this.title + ", beginTime=" + this.beginTime + ", endTime=" + this.endTime + ')';
    }

    public EPG(String title, int i, int i2) {
        Intrinsics.checkNotNullParameter(title, "title");
        this.title = title;
        this.beginTime = i;
        this.endTime = i2;
    }

    public final String getTitle() {
        return this.title;
    }

    public final int getBeginTime() {
        return this.beginTime;
    }

    public final int getEndTime() {
        return this.endTime;
    }
}
