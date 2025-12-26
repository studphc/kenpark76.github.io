package com.tvhome.app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.tvhome.app.models.TVList;
import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: InitializerProvider.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001a\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016JM\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0013\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\u0015J\u0014\u0010\u0016\u001a\u00020\u00042\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\bH\u0002J;\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u0019¨\u0006\u001a"}, d2 = {"Lcom/pjy/koreatv/InitializerProvider;", "Landroid/content/ContentProvider;", "()V", "delete", "", "uri", "Landroid/net/Uri;", "selection", "", "selectionArgs", "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Void;", "getType", "insert", "values", "Landroid/content/ContentValues;", "onCreate", "", "query", "projection", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Void;", "unsupported", "errorMessage", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/Void;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class InitializerProvider extends ContentProvider {
    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ int delete(Uri uri, String str, String[] strArr) {
        return ((Number) delete(uri, str, strArr)).intValue();
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ String getType(Uri uri) {
        return (String) getType(uri);
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ Uri insert(Uri uri, ContentValues contentValues) {
        return (Uri) insert(uri, contentValues);
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return (Cursor) query(uri, strArr, str, strArr2, str2);
    }

    @Override // android.content.ContentProvider
    public /* bridge */ /* synthetic */ int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return ((Number) update(uri, contentValues, str, strArr)).intValue();
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        SP sp = SP.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNull(context);
        sp.init(context);
        TVList tVList = TVList.INSTANCE;
        Context context2 = getContext();
        Intrinsics.checkNotNull(context2);
        tVList.init(context2);
        return true;
    }

    @Override // android.content.ContentProvider
    public Void query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        unsupported$default(this, null, 1, null);
        throw new KotlinNothingValueException();
    }

    @Override // android.content.ContentProvider
    public Void getType(Uri uri) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        unsupported$default(this, null, 1, null);
        throw new KotlinNothingValueException();
    }

    @Override // android.content.ContentProvider
    public Void insert(Uri uri, ContentValues contentValues) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        unsupported$default(this, null, 1, null);
        throw new KotlinNothingValueException();
    }

    @Override // android.content.ContentProvider
    public Void delete(Uri uri, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        unsupported$default(this, null, 1, null);
        throw new KotlinNothingValueException();
    }

    @Override // android.content.ContentProvider
    public Void update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        Intrinsics.checkNotNullParameter(uri, "uri");
        unsupported$default(this, null, 1, null);
        throw new KotlinNothingValueException();
    }

    static /* synthetic */ Void unsupported$default(InitializerProvider initializerProvider, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return initializerProvider.unsupported(str);
    }

    private final Void unsupported(String str) {
        throw new UnsupportedOperationException(str);
    }
}
