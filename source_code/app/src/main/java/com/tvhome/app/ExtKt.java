package com.tvhome.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.media3.common.C;
import java.security.MessageDigest;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: Ext.kt */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0014\u0010\u0013\u001a\u00020\u0014*\u00020\u00012\b\b\u0002\u0010\u0015\u001a\u00020\u0016\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\u0005\"\u0018\u0010\f\u001a\u00020\r*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"TAG", "", "appSignature", "Landroid/content/Context;", "getAppSignature", "(Landroid/content/Context;)Ljava/lang/String;", "appVersionCode", "", "getAppVersionCode", "(Landroid/content/Context;)J", "appVersionName", "getAppVersionName", "packageInfo", "Landroid/content/pm/PackageInfo;", "getPackageInfo", "(Landroid/content/Context;)Landroid/content/pm/PackageInfo;", "hashSignature", "signature", "Landroid/content/pm/Signature;", "showToast", "", TypedValues.TransitionType.S_DURATION, "", "app_release"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class ExtKt {
    private static final String TAG = "Extensions";

    private static final PackageInfo getPackageInfo(Context context) {
        PackageManager.PackageInfoFlags of;
        PackageInfo packageInfo;
        int i = Build.VERSION.SDK_INT < 28 ? 64 : C.BUFFER_FLAG_FIRST_SAMPLE;
        if (Build.VERSION.SDK_INT < 33) {
            PackageInfo packageInfo2 = context.getPackageManager().getPackageInfo(context.getPackageName(), i);
            Intrinsics.checkNotNull(packageInfo2);
            return packageInfo2;
        }
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        of = PackageManager.PackageInfoFlags.of(134217728L);
        packageInfo = packageManager.getPackageInfo(packageName, of);
        Intrinsics.checkNotNull(packageInfo);
        return packageInfo;
    }

    public static final long getAppVersionCode(Context context) {
        long longVersionCode;
        Intrinsics.checkNotNullParameter(context, "<this>");
        PackageInfo packageInfo = getPackageInfo(context);
        if (Build.VERSION.SDK_INT >= 28) {
            longVersionCode = packageInfo.getLongVersionCode();
            return longVersionCode;
        }
        return packageInfo.versionCode;
    }

    public static final String getAppVersionName(Context context) {
        Intrinsics.checkNotNullParameter(context, "<this>");
        String versionName = getPackageInfo(context).versionName;
        Intrinsics.checkNotNullExpressionValue(versionName, "versionName");
        return versionName;
    }

    public static final String getAppSignature(Context context) {
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        Signature signature;
        Intrinsics.checkNotNullParameter(context, "<this>");
        PackageInfo packageInfo = getPackageInfo(context);
        if (Build.VERSION.SDK_INT < 28) {
            Signature[] signatureArr = packageInfo.signatures;
            if (signatureArr != null) {
                signature = signatureArr[0];
            }
            signature = null;
        } else {
            signingInfo = packageInfo.signingInfo;
            if (signingInfo != null) {
                apkContentsSigners = signingInfo.getApkContentsSigners();
                signature = apkContentsSigners[0];
            }
            signature = null;
        }
        return signature == null ? "" : hashSignature(signature);
    }

    private static final String hashSignature(Signature signature) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(signature.toByteArray());
            byte[] digest = messageDigest.digest();
            Intrinsics.checkNotNull(digest);
            return ArraysKt.joinToString$default(digest, (CharSequence) "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) new Function1<Byte, CharSequence>() { // from class: com.pjy.koreatv.ExtKt$hashSignature$1$1
                public final CharSequence invoke(byte b) {
                    String format = String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(b)}, 1));
                    Intrinsics.checkNotNullExpressionValue(format, "format(...)");
                    return format;
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Byte b) {
                    return invoke(b.byteValue());
                }
            }, 30, (Object) null);
        } catch (Exception e) {
            Log.e(TAG, "Error hashing signature", e);
            return "";
        }
    }

    public static /* synthetic */ void showToast$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        showToast(str, i);
    }

    public static final void showToast(String str, int i) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        MyTVApplication.Companion.getInstance().toast(str, i);
    }
}
