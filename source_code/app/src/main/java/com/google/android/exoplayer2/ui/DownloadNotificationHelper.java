package com.google.android.exoplayer2.ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat;
/* loaded from: classes3.dex */
public final class DownloadNotificationHelper {
    private static final int NULL_STRING_ID = 0;
    private final NotificationCompat.Builder notificationBuilder;

    public DownloadNotificationHelper(Context context, String str) {
        this.notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext(), str);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.app.Notification buildProgressNotification(android.content.Context r21, int r22, android.app.PendingIntent r23, java.lang.String r24, java.util.List<com.google.android.exoplayer2.offline.Download> r25) {
        /*
            r20 = this;
            r0 = 0
            r1 = 0
            r2 = 1
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 1
            r8 = 0
        L9:
            int r9 = r25.size()
            if (r3 >= r9) goto L49
            r9 = r25
            java.lang.Object r10 = r9.get(r3)
            com.google.android.exoplayer2.offline.Download r10 = (com.google.android.exoplayer2.offline.Download) r10
            int r11 = r10.state
            r12 = 5
            if (r11 != r12) goto L1e
            r5 = 1
            goto L46
        L1e:
            int r11 = r10.state
            r12 = 7
            if (r11 == r12) goto L29
            int r11 = r10.state
            r12 = 2
            if (r11 == r12) goto L29
            goto L46
        L29:
            float r4 = r10.getPercentDownloaded()
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r11 = (r4 > r11 ? 1 : (r4 == r11 ? 0 : -1))
            if (r11 == 0) goto L35
            float r0 = r0 + r4
            r7 = 0
        L35:
            long r10 = r10.getBytesDownloaded()
            r12 = 0
            int r4 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r4 <= 0) goto L41
            r4 = 1
            goto L42
        L41:
            r4 = 0
        L42:
            r8 = r8 | r4
            int r6 = r6 + 1
            r4 = 1
        L46:
            int r3 = r3 + 1
            goto L9
        L49:
            if (r4 == 0) goto L4f
            int r3 = com.google.android.exoplayer2.ui.R.string.exo_download_downloading
        L4d:
            r14 = r3
            goto L55
        L4f:
            if (r5 == 0) goto L54
            int r3 = com.google.android.exoplayer2.ui.R.string.exo_download_removing
            goto L4d
        L54:
            r14 = 0
        L55:
            if (r4 == 0) goto L64
            float r3 = (float) r6
            float r0 = r0 / r3
            int r0 = (int) r0
            if (r7 == 0) goto L5f
            if (r8 == 0) goto L5f
            r1 = 1
        L5f:
            r16 = r0
            r17 = r1
            goto L68
        L64:
            r16 = 0
            r17 = 1
        L68:
            r15 = 100
            r18 = 1
            r19 = 0
            r9 = r20
            r10 = r21
            r11 = r22
            r12 = r23
            r13 = r24
            android.app.Notification r0 = r9.buildNotification(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.ui.DownloadNotificationHelper.buildProgressNotification(android.content.Context, int, android.app.PendingIntent, java.lang.String, java.util.List):android.app.Notification");
    }

    public Notification buildDownloadCompletedNotification(Context context, int i, PendingIntent pendingIntent, String str) {
        return buildEndStateNotification(context, i, pendingIntent, str, R.string.exo_download_completed);
    }

    public Notification buildDownloadFailedNotification(Context context, int i, PendingIntent pendingIntent, String str) {
        return buildEndStateNotification(context, i, pendingIntent, str, R.string.exo_download_failed);
    }

    private Notification buildEndStateNotification(Context context, int i, PendingIntent pendingIntent, String str, int i2) {
        return buildNotification(context, i, pendingIntent, str, i2, 0, 0, false, false, true);
    }

    private Notification buildNotification(Context context, int i, PendingIntent pendingIntent, String str, int i2, int i3, int i4, boolean z, boolean z2, boolean z3) {
        this.notificationBuilder.setSmallIcon(i);
        this.notificationBuilder.setContentTitle(i2 == 0 ? null : context.getResources().getString(i2));
        this.notificationBuilder.setContentIntent(pendingIntent);
        this.notificationBuilder.setStyle(str != null ? new NotificationCompat.BigTextStyle().bigText(str) : null);
        this.notificationBuilder.setProgress(i3, i4, z);
        this.notificationBuilder.setOngoing(z2);
        this.notificationBuilder.setShowWhen(z3);
        return this.notificationBuilder.build();
    }
}
