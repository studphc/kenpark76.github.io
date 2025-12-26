package com.pjy.koreatv.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TimeResponse.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u000fHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/pjy/koreatv/requests/TimeResponse;", "", "data", "Lcom/pjy/koreatv/requests/TimeResponse$Time;", "(Lcom/pjy/koreatv/requests/TimeResponse$Time;)V", "getData", "()Lcom/pjy/koreatv/requests/TimeResponse$Time;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "Time", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TimeResponse {
    private final Time data;

    public static /* synthetic */ TimeResponse copy$default(TimeResponse timeResponse, Time time, int i, Object obj) {
        if ((i & 1) != 0) {
            time = timeResponse.data;
        }
        return timeResponse.copy(time);
    }

    public final Time component1() {
        return this.data;
    }

    public final TimeResponse copy(Time data) {
        Intrinsics.checkNotNullParameter(data, "data");
        return new TimeResponse(data);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof TimeResponse) && Intrinsics.areEqual(this.data, ((TimeResponse) obj).data);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public String toString() {
        return "TimeResponse(data=" + this.data + ')';
    }

    public TimeResponse(Time data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.data = data;
    }

    public final Time getData() {
        return this.data;
    }

    /* compiled from: TimeResponse.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lcom/pjy/koreatv/requests/TimeResponse$Time;", "", "t", "", "(Ljava/lang/String;)V", "getT", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Time {
        private final String t;

        public static /* synthetic */ Time copy$default(Time time, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = time.t;
            }
            return time.copy(str);
        }

        public final String component1() {
            return this.t;
        }

        public final Time copy(String t) {
            Intrinsics.checkNotNullParameter(t, "t");
            return new Time(t);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Time) && Intrinsics.areEqual(this.t, ((Time) obj).t);
        }

        public int hashCode() {
            return this.t.hashCode();
        }

        public String toString() {
            return "Time(t=" + this.t + ')';
        }

        public Time(String t) {
            Intrinsics.checkNotNullParameter(t, "t");
            this.t = t;
        }

        public final String getT() {
            return this.t;
        }
    }
}
