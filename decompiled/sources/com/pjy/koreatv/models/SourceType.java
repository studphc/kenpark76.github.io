package com.pjy.koreatv.models;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: SourceType.kt */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lcom/pjy/koreatv/models/SourceType;", "", "(Ljava/lang/String;I)V", "UNKNOWN", "HLS", "DASH", "RTSP", "PROGRESSIVE", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class SourceType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ SourceType[] $VALUES;
    public static final SourceType UNKNOWN = new SourceType("UNKNOWN", 0);
    public static final SourceType HLS = new SourceType("HLS", 1);
    public static final SourceType DASH = new SourceType("DASH", 2);
    public static final SourceType RTSP = new SourceType("RTSP", 3);
    public static final SourceType PROGRESSIVE = new SourceType("PROGRESSIVE", 4);

    private static final /* synthetic */ SourceType[] $values() {
        return new SourceType[]{UNKNOWN, HLS, DASH, RTSP, PROGRESSIVE};
    }

    public static EnumEntries<SourceType> getEntries() {
        return $ENTRIES;
    }

    public static SourceType valueOf(String str) {
        return (SourceType) Enum.valueOf(SourceType.class, str);
    }

    public static SourceType[] values() {
        return (SourceType[]) $VALUES.clone();
    }

    private SourceType(String str, int i) {
    }

    static {
        SourceType[] $values = $values();
        $VALUES = $values;
        $ENTRIES = EnumEntriesKt.enumEntries($values);
    }
}
