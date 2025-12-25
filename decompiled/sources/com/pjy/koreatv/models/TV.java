package com.pjy.koreatv.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: TV.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b/\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u008d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u0012\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00000\u000b¢\u0006\u0002\u0010\u0012J\t\u00103\u001a\u00020\u0003HÆ\u0003J\t\u00104\u001a\u00020\u0010HÆ\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bHÆ\u0003J\t\u00106\u001a\u00020\u0005HÆ\u0003J\t\u00107\u001a\u00020\u0005HÆ\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u00109\u001a\u00020\u0005HÆ\u0003J\u000b\u0010:\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bHÆ\u0003J\u0017\u0010<\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\rHÆ\u0003J\t\u0010=\u001a\u00020\u0005HÆ\u0003J\u0095\u0001\u0010>\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bHÆ\u0001J\u0013\u0010?\u001a\u00020@2\b\u0010A\u001a\u0004\u0018\u00010BHÖ\u0003J\t\u0010C\u001a\u00020\u0003HÖ\u0001J\b\u0010D\u001a\u00020\u0005H\u0016R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00000\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0018\"\u0004\b\u001c\u0010\u001aR(\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001c\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0018\"\u0004\b&\u0010\u001aR\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0018\"\u0004\b(\u0010\u001aR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u0018\"\u0004\b*\u0010\u001aR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0018\"\u0004\b0\u0010\u001aR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0014\"\u0004\b2\u0010\u0016¨\u0006E"}, d2 = {"Lcom/pjy/koreatv/models/TV;", "Ljava/io/Serializable;", "id", "", "name", "", "title", "description", "logo", "image", "uris", "", "headers", "", "group", "sourceType", "Lcom/pjy/koreatv/models/SourceType;", "child", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/Map;Ljava/lang/String;Lcom/pjy/koreatv/models/SourceType;Ljava/util/List;)V", "getChild", "()Ljava/util/List;", "setChild", "(Ljava/util/List;)V", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getGroup", "setGroup", "getHeaders", "()Ljava/util/Map;", "setHeaders", "(Ljava/util/Map;)V", "getId", "()I", "setId", "(I)V", "getImage", "setImage", "getLogo", "setLogo", "getName", "setName", "getSourceType", "()Lcom/pjy/koreatv/models/SourceType;", "setSourceType", "(Lcom/pjy/koreatv/models/SourceType;)V", "getTitle", "setTitle", "getUris", "setUris", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TV implements Serializable {
    private List<TV> child;
    private String description;
    private String group;
    private Map<String, String> headers;
    private int id;
    private String image;
    private String logo;
    private String name;
    private SourceType sourceType;
    private String title;
    private List<String> uris;

    public final int component1() {
        return this.id;
    }

    public final SourceType component10() {
        return this.sourceType;
    }

    public final List<TV> component11() {
        return this.child;
    }

    public final String component2() {
        return this.name;
    }

    public final String component3() {
        return this.title;
    }

    public final String component4() {
        return this.description;
    }

    public final String component5() {
        return this.logo;
    }

    public final String component6() {
        return this.image;
    }

    public final List<String> component7() {
        return this.uris;
    }

    public final Map<String, String> component8() {
        return this.headers;
    }

    public final String component9() {
        return this.group;
    }

    public final TV copy(int i, String name, String title, String str, String logo, String str2, List<String> uris, Map<String, String> map, String group, SourceType sourceType, List<TV> child) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(logo, "logo");
        Intrinsics.checkNotNullParameter(uris, "uris");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        Intrinsics.checkNotNullParameter(child, "child");
        return new TV(i, name, title, str, logo, str2, uris, map, group, sourceType, child);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TV) {
            TV tv = (TV) obj;
            return this.id == tv.id && Intrinsics.areEqual(this.name, tv.name) && Intrinsics.areEqual(this.title, tv.title) && Intrinsics.areEqual(this.description, tv.description) && Intrinsics.areEqual(this.logo, tv.logo) && Intrinsics.areEqual(this.image, tv.image) && Intrinsics.areEqual(this.uris, tv.uris) && Intrinsics.areEqual(this.headers, tv.headers) && Intrinsics.areEqual(this.group, tv.group) && this.sourceType == tv.sourceType && Intrinsics.areEqual(this.child, tv.child);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((((this.id * 31) + this.name.hashCode()) * 31) + this.title.hashCode()) * 31;
        String str = this.description;
        int hashCode2 = (((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.logo.hashCode()) * 31;
        String str2 = this.image;
        int hashCode3 = (((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + this.uris.hashCode()) * 31;
        Map<String, String> map = this.headers;
        return ((((((hashCode3 + (map != null ? map.hashCode() : 0)) * 31) + this.group.hashCode()) * 31) + this.sourceType.hashCode()) * 31) + this.child.hashCode();
    }

    public TV(int i, String name, String title, String str, String logo, String str2, List<String> uris, Map<String, String> map, String group, SourceType sourceType, List<TV> child) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(logo, "logo");
        Intrinsics.checkNotNullParameter(uris, "uris");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(sourceType, "sourceType");
        Intrinsics.checkNotNullParameter(child, "child");
        this.id = i;
        this.name = name;
        this.title = title;
        this.description = str;
        this.logo = logo;
        this.image = str2;
        this.uris = uris;
        this.headers = map;
        this.group = group;
        this.sourceType = sourceType;
        this.child = child;
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final String getTitle() {
        return this.title;
    }

    public final void setTitle(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setDescription(String str) {
        this.description = str;
    }

    public final String getLogo() {
        return this.logo;
    }

    public final void setLogo(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.logo = str;
    }

    public final String getImage() {
        return this.image;
    }

    public final void setImage(String str) {
        this.image = str;
    }

    public final List<String> getUris() {
        return this.uris;
    }

    public final void setUris(List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.uris = list;
    }

    public final Map<String, String> getHeaders() {
        return this.headers;
    }

    public final void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public final String getGroup() {
        return this.group;
    }

    public final void setGroup(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.group = str;
    }

    public /* synthetic */ TV(int i, String str, String str2, String str3, String str4, String str5, List list, Map map, String str6, SourceType sourceType, List list2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? "" : str2, (i2 & 8) != 0 ? null : str3, (i2 & 16) != 0 ? "" : str4, (i2 & 32) != 0 ? null : str5, list, (i2 & 128) != 0 ? null : map, (i2 & 256) != 0 ? "" : str6, (i2 & 512) != 0 ? SourceType.UNKNOWN : sourceType, list2);
    }

    public final SourceType getSourceType() {
        return this.sourceType;
    }

    public final void setSourceType(SourceType sourceType) {
        Intrinsics.checkNotNullParameter(sourceType, "<set-?>");
        this.sourceType = sourceType;
    }

    public final List<TV> getChild() {
        return this.child;
    }

    public final void setChild(List<TV> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.child = list;
    }

    public String toString() {
        return "TV{id=" + this.id + ", name='" + this.name + "', title='" + this.title + "', description='" + this.description + "', logo='" + this.logo + "', image='" + this.image + "', uris='" + this.uris + "', headers='" + this.headers + "', group='" + this.group + "', sourceType='" + this.sourceType + "'}";
    }
}
