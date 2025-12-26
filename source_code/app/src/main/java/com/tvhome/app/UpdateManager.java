package com.tvhome.app;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import com.tvhome.app.ConfirmationFragment;
import com.tvhome.app.requests.HttpClient;
import com.tvhome.app.requests.ReleaseRequest;
import com.tvhome.app.requests.ReleaseResponse;
import java.io.File;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
/* compiled from: UpdateManager.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\u000eJ,\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00052\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000e0\u0013H\u0002J\b\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/pjy/koreatv/UpdateManager;", "Lcom/pjy/koreatv/ConfirmationFragment$ConfirmationListener;", "context", "Landroid/content/Context;", "versionCode", "", "(Landroid/content/Context;J)V", "downloadReceiver", "Lcom/pjy/koreatv/UpdateManager$DownloadReceiver;", "release", "Lcom/pjy/koreatv/requests/ReleaseResponse;", "releaseRequest", "Lcom/pjy/koreatv/requests/ReleaseRequest;", "checkAndUpdate", "", "destroy", "getDownloadProgress", "downloadId", "progressListener", "Lkotlin/Function1;", "", "onCancel", "onConfirm", "startDownload", "updateUI", "text", "", "update", "", "Companion", "DownloadReceiver", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class UpdateManager implements ConfirmationFragment.ConfirmationListener {
    public static final Companion Companion = new Companion(null);
    private static final String TAG = "UpdateManager";
    private Context context;
    private DownloadReceiver downloadReceiver;
    private ReleaseResponse release;
    private ReleaseRequest releaseRequest;
    private long versionCode;

    @Override // com.pjy.koreatv.ConfirmationFragment.ConfirmationListener
    public void onCancel() {
    }

    public UpdateManager(Context context, long j) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.versionCode = j;
        this.releaseRequest = new ReleaseRequest();
    }

    public final void checkAndUpdate() {
        Log.i(TAG, "checkAndUpdate");
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getMain()), null, null, new UpdateManager$checkAndUpdate$1(this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUI(String str, boolean z) {
        ConfirmationFragment confirmationFragment = new ConfirmationFragment(this, str, z);
        Context context = this.context;
        Intrinsics.checkNotNull(context, "null cannot be cast to non-null type androidx.fragment.app.FragmentActivity");
        confirmationFragment.show(((FragmentActivity) context).getSupportFragmentManager(), TAG);
    }

    private final void startDownload(ReleaseResponse releaseResponse) {
        String str = "my-tv-0-" + releaseResponse.getVersion_name() + ".apk";
        Object systemService = this.context.getSystemService("download");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
        DownloadManager downloadManager = (DownloadManager) systemService;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(HttpClient.DOWNLOAD_HOST + releaseResponse.getVersion_name() + "/my-tv-0-" + releaseResponse.getVersion_name() + ".apk"));
        StringBuilder sb = new StringBuilder("url ");
        sb.append(Uri.parse(HttpClient.DOWNLOAD_HOST + releaseResponse.getVersion_name() + "/my-tv-0-" + releaseResponse.getVersion_name() + ".apk"));
        Log.i(TAG, sb.toString());
        File externalFilesDir = this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (externalFilesDir != null) {
            externalFilesDir.mkdirs();
        }
        Log.i(TAG, "save dir " + Environment.DIRECTORY_DOWNLOADS);
        request.setDestinationInExternalFilesDir(this.context, Environment.DIRECTORY_DOWNLOADS, str);
        request.setTitle(this.context.getResources().getString(R.string.app_name) + ' ' + releaseResponse.getVersion_name());
        request.setNotificationVisibility(1);
        request.setAllowedOverRoaming(false);
        request.setMimeType("application/vnd.android.package-archive");
        long enqueue = downloadManager.enqueue(request);
        this.downloadReceiver = new DownloadReceiver(this.context, str, enqueue);
        if (Build.VERSION.SDK_INT >= 26) {
            this.context.registerReceiver(this.downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"), 4);
        } else {
            this.context.registerReceiver(this.downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        }
        getDownloadProgress(this.context, enqueue, new Function1<Integer, Unit>() { // from class: com.pjy.koreatv.UpdateManager$startDownload$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Integer num) {
                invoke(num.intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(int i) {
                System.out.println((Object) ("Download progress: " + i + '%'));
            }
        });
    }

    private final void getDownloadProgress(Context context, final long j, final Function1<? super Integer, Unit> function1) {
        Object systemService = context.getSystemService("download");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
        final DownloadManager downloadManager = (DownloadManager) systemService;
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.pjy.koreatv.UpdateManager$getDownloadProgress$1
            @Override // java.lang.Runnable
            public void run() {
                Log.i("UpdateManager", "search");
                Cursor query = downloadManager.query(new DownloadManager.Query().setFilterById(j));
                Intrinsics.checkNotNullExpressionValue(query, "query(...)");
                Cursor cursor = query;
                Function1<Integer, Unit> function12 = function1;
                try {
                    Cursor cursor2 = cursor;
                    if (cursor2.moveToFirst()) {
                        int columnIndex = cursor2.getColumnIndex("bytes_so_far");
                        int columnIndex2 = cursor2.getColumnIndex("total_size");
                        if (columnIndex != -1 && columnIndex2 != -1) {
                            int i = cursor2.getInt(columnIndex);
                            int i2 = cursor2.getInt(columnIndex2);
                            if (i2 != -1) {
                                int i3 = (int) ((i * 100) / i2);
                                function12.invoke(Integer.valueOf(i3));
                                if (i3 == 100) {
                                    CloseableKt.closeFinally(cursor, null);
                                    return;
                                }
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor, null);
                } finally {
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/pjy/koreatv/UpdateManager$DownloadReceiver;", "Landroid/content/BroadcastReceiver;", "context", "Landroid/content/Context;", "apkFileName", "", "downloadReference", "", "(Landroid/content/Context;Ljava/lang/String;J)V", "installNewVersion", "", "onReceive", "intent", "Landroid/content/Intent;", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class DownloadReceiver extends BroadcastReceiver {
        private final String apkFileName;
        private final Context context;
        private final long downloadReference;

        public DownloadReceiver(Context context, String apkFileName, long j) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(apkFileName, "apkFileName");
            this.context = context;
            this.apkFileName = apkFileName;
            this.downloadReference = j;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            long longExtra = intent.getLongExtra("extra_download_id", -1L);
            Log.i(UpdateManager.TAG, "reference " + longExtra);
            if (longExtra == this.downloadReference) {
                Object systemService = context.getSystemService("download");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
                Cursor query = ((DownloadManager) systemService).query(new DownloadManager.Query().setFilterById(this.downloadReference));
                if (query == null || !query.moveToFirst()) {
                    return;
                }
                int columnIndex = query.getColumnIndex(NotificationCompat.CATEGORY_STATUS);
                if (columnIndex < 0) {
                    Log.i(UpdateManager.TAG, "Download failure");
                    return;
                }
                int i = query.getInt(columnIndex);
                int columnIndex2 = query.getColumnIndex("bytes_so_far");
                if (columnIndex2 < 0) {
                    Log.i(UpdateManager.TAG, "Download failure");
                    return;
                }
                int i2 = query.getInt(columnIndex2);
                int i3 = query.getInt(query.getColumnIndex("total_size"));
                query.close();
                if (i == 8) {
                    installNewVersion();
                } else if (i == 16) {
                    Log.i(UpdateManager.TAG, "Download failure");
                } else {
                    Log.i(UpdateManager.TAG, "Download progress: " + ((i2 * 100) / i3) + '%');
                }
            }
        }

        private final void installNewVersion() {
            File file = new File(this.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), this.apkFileName);
            Log.i(UpdateManager.TAG, "apkFile " + file);
            if (file.exists()) {
                Uri parse = Uri.parse("file://" + file);
                Log.i(UpdateManager.TAG, "apkUri " + parse);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(parse, "application/vnd.android.package-archive");
                intent.addFlags(268435457);
                this.context.startActivity(intent);
                return;
            }
            Log.e(UpdateManager.TAG, "APK file does not exist!");
        }
    }

    /* compiled from: UpdateManager.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lcom/pjy/koreatv/UpdateManager$Companion;", "", "()V", "TAG", "", "app_release"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // com.pjy.koreatv.ConfirmationFragment.ConfirmationListener
    public void onConfirm() {
        Log.i(TAG, "onConfirm " + this.release);
        ReleaseResponse releaseResponse = this.release;
        if (releaseResponse != null) {
            startDownload(releaseResponse);
        }
    }

    public final void destroy() {
        DownloadReceiver downloadReceiver = this.downloadReceiver;
        if (downloadReceiver != null) {
            this.context.unregisterReceiver(downloadReceiver);
            Log.i(TAG, "destroy downloadReceiver");
        }
    }
}
