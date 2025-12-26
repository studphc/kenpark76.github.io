package com.tvhome.app.requests;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: ReleaseResponse.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\bJ\u000b\u0010\r\u001a\u0004\u0018\u00010\u0005HÆ\u0003J&\u0010\u000e\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0005HÖ\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0015"}, d2 = {"Lcom/pjy/koreatv/requests/ReleaseResponse;", "", "version_code", "", "version_name", "", "(Ljava/lang/Integer;Ljava/lang/String;)V", "getVersion_code", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getVersion_name", "()Ljava/lang/String;", "component1", "component2", "copy", "(Ljava/lang/Integer;Ljava/lang/String;)Lcom/pjy/koreatv/requests/ReleaseResponse;", "equals", "", "other", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ReleaseResponse {
    private final Integer version_code;
    private final String version_name;

    public static /* synthetic */ ReleaseResponse copy$default(ReleaseResponse releaseResponse, Integer num, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            num = releaseResponse.version_code;
        }
        if ((i & 2) != 0) {
            str = releaseResponse.version_name;
        }
        return releaseResponse.copy(num, str);
    }

    public final Integer component1() {
        return this.version_code;
    }

    public final String component2() {
        return this.version_name;
    }

    public final ReleaseResponse copy(Integer num, String str) {
        return new ReleaseResponse(num, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ReleaseResponse) {
            ReleaseResponse releaseResponse = (ReleaseResponse) obj;
            return Intrinsics.areEqual(this.version_code, releaseResponse.version_code) && Intrinsics.areEqual(this.version_name, releaseResponse.version_name);
        }
        return false;
    }

    public int hashCode() {
        Integer num = this.version_code;
        int hashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.version_name;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    public String toString() {
        return "ReleaseResponse(version_code=" + this.version_code + ", version_name=" + this.version_name + ')';
    }

    public ReleaseResponse(Integer num, String str) {
        this.version_code = num;
        this.version_name = str;
    }

    public final Integer getVersion_code() {
        return this.version_code;
    }

    public final String getVersion_name() {
        return this.version_name;
    }
}
