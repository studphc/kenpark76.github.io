package androidx.leanback.system;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import androidx.leanback.widget.ShadowOverlayContainer;
import java.util.Iterator;
/* loaded from: classes.dex */
public class Settings {
    private static final String ACTION_PARTNER_CUSTOMIZATION = "android.support.v17.leanback.action.PARTNER_CUSTOMIZATION";
    private static final boolean DEBUG = false;
    public static final String OUTLINE_CLIPPING_DISABLED = "OUTLINE_CLIPPING_DISABLED";
    public static final String PREFER_STATIC_SHADOWS = "PREFER_STATIC_SHADOWS";
    private static final String TAG = "Settings";
    private static Settings sInstance;
    private boolean mOutlineClippingDisabled;
    private boolean mPreferStaticShadows;

    public static Settings getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new Settings(context);
        }
        return sInstance;
    }

    private Settings(Context context) {
        generateSetting(getCustomizations(context));
    }

    public boolean preferStaticShadows() {
        return this.mPreferStaticShadows;
    }

    public boolean isOutlineClippingDisabled() {
        return this.mOutlineClippingDisabled;
    }

    public boolean getBoolean(String str) {
        return getOrSetBoolean(str, false, false);
    }

    public void setBoolean(String str, boolean z) {
        getOrSetBoolean(str, true, z);
    }

    boolean getOrSetBoolean(String str, boolean z, boolean z2) {
        if (str.compareTo(PREFER_STATIC_SHADOWS) == 0) {
            if (z) {
                this.mPreferStaticShadows = z2;
                return z2;
            }
            return this.mPreferStaticShadows;
        } else if (str.compareTo(OUTLINE_CLIPPING_DISABLED) == 0) {
            if (z) {
                this.mOutlineClippingDisabled = z2;
                return z2;
            }
            return this.mOutlineClippingDisabled;
        } else {
            throw new IllegalArgumentException("Invalid key");
        }
    }

    private void generateSetting(Customizations customizations) {
        if (ShadowOverlayContainer.supportsDynamicShadow()) {
            this.mPreferStaticShadows = false;
            if (customizations != null) {
                this.mPreferStaticShadows = customizations.getBoolean("leanback_prefer_static_shadows", false);
            }
        } else {
            this.mPreferStaticShadows = true;
        }
        this.mOutlineClippingDisabled = false;
        if (customizations != null) {
            this.mOutlineClippingDisabled = customizations.getBoolean("leanback_outline_clipping_disabled", false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Customizations {
        String mPackageName;
        Resources mResources;

        public Customizations(Resources resources, String str) {
            this.mResources = resources;
            this.mPackageName = str;
        }

        public boolean getBoolean(String str, boolean z) {
            int identifier = this.mResources.getIdentifier(str, "bool", this.mPackageName);
            return identifier > 0 ? this.mResources.getBoolean(identifier) : z;
        }
    }

    private Customizations getCustomizations(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Iterator<ResolveInfo> it = packageManager.queryBroadcastReceivers(new Intent(ACTION_PARTNER_CUSTOMIZATION), 0).iterator();
        Resources resources = null;
        String str = null;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ResolveInfo next = it.next();
            String str2 = next.activityInfo.packageName;
            if (str2 != null && isSystemApp(next)) {
                try {
                    resources = packageManager.getResourcesForApplication(str2);
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            if (resources != null) {
                str = str2;
                break;
            }
            str = str2;
        }
        if (resources == null) {
            return null;
        }
        return new Customizations(resources, str);
    }

    private static boolean isSystemApp(ResolveInfo resolveInfo) {
        return (resolveInfo.activityInfo == null || (resolveInfo.activityInfo.applicationInfo.flags & 1) == 0) ? false : true;
    }
}
