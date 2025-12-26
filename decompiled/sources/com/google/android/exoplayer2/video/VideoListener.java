package com.google.android.exoplayer2.video;
/* loaded from: classes3.dex */
public interface VideoListener {

    /* renamed from: com.google.android.exoplayer2.video.VideoListener$-CC  reason: invalid class name */
    /* loaded from: classes3.dex */
    public final /* synthetic */ class CC {
        public static void $default$onRenderedFirstFrame(VideoListener _this) {
        }

        public static void $default$onSurfaceSizeChanged(VideoListener _this, int i, int i2) {
        }

        public static void $default$onVideoSizeChanged(VideoListener _this, int i, int i2, int i3, float f) {
        }
    }

    void onRenderedFirstFrame();

    void onSurfaceSizeChanged(int i, int i2);

    void onVideoSizeChanged(int i, int i2, int i3, float f);
}
