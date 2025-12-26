package com.google.android.exoplayer2.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.media3.common.util.Util$$ExternalSyntheticApiModelOutline0;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/* loaded from: classes3.dex */
public final class NotificationUtil {
    public static final int IMPORTANCE_DEFAULT = 3;
    public static final int IMPORTANCE_HIGH = 4;
    public static final int IMPORTANCE_LOW = 2;
    public static final int IMPORTANCE_MIN = 1;
    public static final int IMPORTANCE_NONE = 0;
    public static final int IMPORTANCE_UNSPECIFIED = -1000;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes3.dex */
    public @interface Importance {
    }

    @Deprecated
    public static void createNotificationChannel(Context context, String str, int i, int i2) {
        createNotificationChannel(context, str, i, 0, i2);
    }

    public static void createNotificationChannel(Context context, String str, int i, int i2, int i3) {
        if (Util.SDK_INT >= 26) {
            NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService("notification"));
            Util$$ExternalSyntheticApiModelOutline0.m168m();
            NotificationChannel m = Util$$ExternalSyntheticApiModelOutline0.m(str, context.getString(i), i3);
            if (i2 != 0) {
                m.setDescription(context.getString(i2));
            }
            notificationManager.createNotificationChannel(m);
        }
    }

    public static void setNotification(Context context, int i, Notification notification) {
        NotificationManager notificationManager = (NotificationManager) Assertions.checkNotNull((NotificationManager) context.getSystemService("notification"));
        if (notification != null) {
            notificationManager.notify(i, notification);
        } else {
            notificationManager.cancel(i);
        }
    }

    private NotificationUtil() {
    }
}
