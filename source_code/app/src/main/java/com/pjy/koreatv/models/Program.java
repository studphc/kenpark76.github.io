package com.pjy.koreatv.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Program.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J?\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\b\u0010#\u001a\u00020\u0005H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\b\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\r¨\u0006$"}, d2 = {"Lcom/pjy/koreatv/models/Program;", "Ljava/io/Serializable;", "id", "", "title", "", "description", "logo", "image", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getId", "()I", "setId", "(I)V", "getImage", "setImage", "getLogo", "setLogo", "getTitle", "setTitle", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "", "hashCode", "toString", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class Program implements Serializable {
    private String description;
    private int id;
    private String image;
    private String logo;
    private String title;

    public Program() {
        this(0, null, null, null, null, 31, null);
    }

    public static /* synthetic */ Program copy$default(Program program, int i, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = program.id;
        }
        if ((i2 & 2) != 0) {
            str = program.title;
        }
        String str5 = str;
        if ((i2 & 4) != 0) {
            str2 = program.description;
        }
        String str6 = str2;
        if ((i2 & 8) != 0) {
            str3 = program.logo;
        }
        String str7 = str3;
        if ((i2 & 16) != 0) {
            str4 = program.image;
        }
        return program.copy(i, str5, str6, str7, str4);
    }

    public final int component1() {
        return this.id;
    }

    public final String component2() {
        return this.title;
    }

    public final String component3() {
        return this.description;
    }

    public final String component4() {
        return this.logo;
    }

    public final String component5() {
        return this.image;
    }

    public final Program copy(int i, String title, String str, String logo, String str2) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(logo, "logo");
        return new Program(i, title, str, logo, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Program) {
            Program program = (Program) obj;
            return this.id == program.id && Intrinsics.areEqual(this.title, program.title) && Intrinsics.areEqual(this.description, program.description) && Intrinsics.areEqual(this.logo, program.logo) && Intrinsics.areEqual(this.image, program.image);
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((this.id * 31) + this.title.hashCode()) * 31;
        String str = this.description;
        int hashCode2 = (((hashCode + (str == null ? 0 : str.hashCode())) * 31) + this.logo.hashCode()) * 31;
        String str2 = this.image;
        return hashCode2 + (str2 != null ? str2.hashCode() : 0);
    }

    public Program(int i, String title, String str, String logo, String str2) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(logo, "logo");
        this.id = i;
        this.title = title;
        this.description = str;
        this.logo = logo;
        this.image = str2;
    }

    public /* synthetic */ Program(int i, String str, String str2, String str3, String str4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? "" : str, (i2 & 4) != 0 ? null : str2, (i2 & 8) == 0 ? str3 : "", (i2 & 16) == 0 ? str4 : null);
    }

    public final int getId() {
        return this.id;
    }

    public final void setId(int i) {
        this.id = i;
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

    public String toString() {
        return "Program{id=" + this.id + ", title='" + this.title + "', description='" + this.description + "', logo='" + this.logo + "', image='" + this.image + "'}";
    }
}
