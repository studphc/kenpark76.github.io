package androidx.leanback.app;

import android.app.Fragment;
import android.os.Build;
/* loaded from: classes.dex */
public class PermissionHelper {
    public static void requestPermissions(Fragment fragment, String[] strArr, int i) {
        if (Build.VERSION.SDK_INT >= 23) {
            fragment.requestPermissions(strArr, i);
        }
    }

    private PermissionHelper() {
    }
}
