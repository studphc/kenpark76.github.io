package com.google.android.exoplayer2.audio;
/* loaded from: classes3.dex */
public interface AudioListener {

    /* renamed from: com.google.android.exoplayer2.audio.AudioListener$-CC  reason: invalid class name */
    /* loaded from: classes3.dex */
    public final /* synthetic */ class CC {
        public static void $default$onAudioAttributesChanged(AudioListener _this, AudioAttributes audioAttributes) {
        }

        public static void $default$onAudioSessionIdChanged(AudioListener _this, int i) {
        }

        public static void $default$onSkipSilenceEnabledChanged(AudioListener _this, boolean z) {
        }

        public static void $default$onVolumeChanged(AudioListener _this, float f) {
        }
    }

    void onAudioAttributesChanged(AudioAttributes audioAttributes);

    void onAudioSessionIdChanged(int i);

    void onSkipSilenceEnabledChanged(boolean z);

    void onVolumeChanged(float f);
}
