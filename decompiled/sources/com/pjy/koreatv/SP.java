package com.pjy.koreatv;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
/* compiled from: SP.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b)\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010H\u001a\u00020IJ\u000e\u0010J\u001a\u00020\u00142\u0006\u0010K\u001a\u00020\u0004J\u000e\u0010L\u001a\u00020I2\u0006\u0010M\u001a\u00020NJ\u0016\u0010O\u001a\u00020I2\u0006\u0010K\u001a\u00020\u00042\u0006\u0010P\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R$\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R$\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001a8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR$\u0010 \u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R$\u0010#\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b$\u0010\u0017\"\u0004\b%\u0010\u0019R(\u0010&\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R$\u0010+\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b,\u0010\u0017\"\u0004\b-\u0010\u0019R$\u0010.\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b/\u0010\u0017\"\u0004\b0\u0010\u0019R(\u00101\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b2\u0010(\"\u0004\b3\u0010*R$\u00104\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001a8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b5\u0010\u001d\"\u0004\b6\u0010\u001fR$\u00107\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001a8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b8\u0010\u001d\"\u0004\b9\u0010\u001fR$\u0010:\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001a8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b;\u0010\u001d\"\u0004\b<\u0010\u001fR(\u0010=\u001a\u0004\u0018\u00010\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b>\u0010(\"\u0004\b?\u0010*R$\u0010@\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bA\u0010\u0017\"\u0004\bB\u0010\u0019R\u000e\u0010C\u001a\u00020DX\u0082.¢\u0006\u0002\n\u0000R$\u0010E\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u00148F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bF\u0010\u0017\"\u0004\bG\u0010\u0019¨\u0006Q"}, d2 = {"Lcom/pjy/koreatv/SP;", "", "()V", "KEY_BOOT_STARTUP", "", "KEY_CHANNEL", "KEY_CHANNEL_NUM", "KEY_CHANNEL_REVERSAL", "KEY_CONFIG", "KEY_CONFIG_AUTO_LOAD", "KEY_DEFAULT_LIKE", "KEY_EPG", "KEY_LIKE", "KEY_POSITION", "KEY_POSITION_GROUP", "KEY_POSITION_SUB", "KEY_PROXY", "KEY_REPEAT_INFO", "KEY_TIME", "value", "", "bootStartup", "getBootStartup", "()Z", "setBootStartup", "(Z)V", "", SP.KEY_CHANNEL, "getChannel", "()I", "setChannel", "(I)V", "channelNum", "getChannelNum", "setChannelNum", "channelReversal", "getChannelReversal", "setChannelReversal", SP.KEY_CONFIG, "getConfig", "()Ljava/lang/String;", "setConfig", "(Ljava/lang/String;)V", "configAutoLoad", "getConfigAutoLoad", "setConfigAutoLoad", "defaultLike", "getDefaultLike", "setDefaultLike", SP.KEY_EPG, "getEpg", "setEpg", SP.KEY_POSITION, "getPosition", "setPosition", "positionGroup", "getPositionGroup", "setPositionGroup", "positionSub", "getPositionSub", "setPositionSub", SP.KEY_PROXY, "getProxy", "setProxy", "repeatInfo", "getRepeatInfo", "setRepeatInfo", "sp", "Landroid/content/SharedPreferences;", SP.KEY_TIME, "getTime", "setTime", "deleteLike", "", "getLike", "name", "init", "context", "Landroid/content/Context;", "setLike", "liked", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class SP {
    public static final SP INSTANCE = new SP();
    private static final String KEY_BOOT_STARTUP = "boot_startup";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_CHANNEL_NUM = "channel_num";
    private static final String KEY_CHANNEL_REVERSAL = "channel_reversal";
    private static final String KEY_CONFIG = "config";
    private static final String KEY_CONFIG_AUTO_LOAD = "config_auto_load";
    private static final String KEY_DEFAULT_LIKE = "default_like";
    private static final String KEY_EPG = "epg";
    private static final String KEY_LIKE = "like";
    private static final String KEY_POSITION = "position";
    private static final String KEY_POSITION_GROUP = "position_group";
    private static final String KEY_POSITION_SUB = "position_sub";
    private static final String KEY_PROXY = "proxy";
    private static final String KEY_REPEAT_INFO = "repeat_info";
    private static final String KEY_TIME = "time";
    private static SharedPreferences sp;

    private SP() {
    }

    public final void init(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.app_name), 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        sp = sharedPreferences;
    }

    public final boolean getChannelReversal() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_CHANNEL_REVERSAL, false);
    }

    public final void setChannelReversal(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_CHANNEL_REVERSAL, z).apply();
    }

    public final boolean getChannelNum() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_CHANNEL_NUM, true);
    }

    public final void setChannelNum(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_CHANNEL_NUM, z).apply();
    }

    public final boolean getTime() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_TIME, false);
    }

    public final void setTime(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_TIME, z).apply();
    }

    public final boolean getBootStartup() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_BOOT_STARTUP, false);
    }

    public final void setBootStartup(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_BOOT_STARTUP, z).apply();
    }

    public final int getPosition() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getInt(KEY_POSITION, 0);
    }

    public final void setPosition(int i) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_POSITION, i).apply();
    }

    public final int getPositionGroup() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getInt(KEY_POSITION_GROUP, 0);
    }

    public final void setPositionGroup(int i) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_POSITION_GROUP, i).apply();
    }

    public final int getPositionSub() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getInt(KEY_POSITION_SUB, 0);
    }

    public final void setPositionSub(int i) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_POSITION_SUB, i).apply();
    }

    public final boolean getRepeatInfo() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_REPEAT_INFO, true);
    }

    public final void setRepeatInfo(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_REPEAT_INFO, z).apply();
    }

    public final String getConfig() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getString(KEY_CONFIG, "");
    }

    public final void setConfig(String str) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putString(KEY_CONFIG, str).apply();
    }

    public final boolean getConfigAutoLoad() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_CONFIG_AUTO_LOAD, true);
    }

    public final void setConfigAutoLoad(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_CONFIG_AUTO_LOAD, z).apply();
    }

    public final int getChannel() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getInt(KEY_CHANNEL, 0);
    }

    public final void setChannel(int i) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putInt(KEY_CHANNEL, i).apply();
    }

    public final boolean getDefaultLike() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getBoolean(KEY_DEFAULT_LIKE, false);
    }

    public final void setDefaultLike(boolean z) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putBoolean(KEY_DEFAULT_LIKE, z).apply();
    }

    public final boolean getLike(String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        Set<String> stringSet = sharedPreferences.getStringSet(KEY_LIKE, SetsKt.emptySet());
        if (stringSet != null) {
            return stringSet.contains(name);
        }
        return false;
    }

    public final void setLike(String name, boolean z) {
        LinkedHashSet linkedHashSet;
        Intrinsics.checkNotNullParameter(name, "name");
        SharedPreferences sharedPreferences = sp;
        SharedPreferences sharedPreferences2 = null;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        Set<String> stringSet = sharedPreferences.getStringSet(KEY_LIKE, SetsKt.emptySet());
        if (stringSet == null || (linkedHashSet = CollectionsKt.toMutableSet(stringSet)) == null) {
            linkedHashSet = new LinkedHashSet();
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        linkedHashSet2.addAll(linkedHashSet);
        if (z) {
            linkedHashSet2.add(name);
        } else {
            linkedHashSet2.remove(name);
        }
        SharedPreferences sharedPreferences3 = sp;
        if (sharedPreferences3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
        } else {
            sharedPreferences2 = sharedPreferences3;
        }
        sharedPreferences2.edit().putStringSet(KEY_LIKE, linkedHashSet2).apply();
    }

    public final void deleteLike() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().remove(KEY_LIKE).apply();
    }

    public final String getProxy() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getString(KEY_PROXY, "");
    }

    public final void setProxy(String str) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putString(KEY_PROXY, str).apply();
    }

    public final String getEpg() {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        return sharedPreferences.getString(KEY_EPG, "https://kenpark76.github.io/koreatvEPG.xml");
    }

    public final void setEpg(String str) {
        SharedPreferences sharedPreferences = sp;
        if (sharedPreferences == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sp");
            sharedPreferences = null;
        }
        sharedPreferences.edit().putString(KEY_EPG, str).apply();
    }
}
