package com.google.android.exoplayer2.device;
/* loaded from: classes3.dex */
public interface DeviceListener {

    /* renamed from: com.google.android.exoplayer2.device.DeviceListener$-CC  reason: invalid class name */
    /* loaded from: classes3.dex */
    public final /* synthetic */ class CC {
        public static void $default$onDeviceInfoChanged(DeviceListener _this, DeviceInfo deviceInfo) {
        }

        public static void $default$onDeviceVolumeChanged(DeviceListener _this, int i, boolean z) {
        }
    }

    void onDeviceInfoChanged(DeviceInfo deviceInfo);

    void onDeviceVolumeChanged(int i, boolean z);
}
