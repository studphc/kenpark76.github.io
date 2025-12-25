package androidx.media3.common.util;

import android.app.NotificationChannel;
import android.media.AudioFocusRequest;
import android.media.AudioProfile;
import android.media.MediaCodec;
import android.media.MediaDrmResetException;
import android.media.metrics.MediaMetricsManager;
import android.media.metrics.NetworkEvent;
import android.media.metrics.PlaybackErrorEvent;
import android.media.metrics.PlaybackMetrics;
import android.media.metrics.PlaybackStateEvent;
import android.media.metrics.TrackChangeEvent;
/* compiled from: D8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class Util$$ExternalSyntheticApiModelOutline0 {
    public static /* synthetic */ NotificationChannel m(String str, CharSequence charSequence, int i) {
        return new NotificationChannel(str, charSequence, i);
    }

    public static /* synthetic */ AudioFocusRequest.Builder m(int i) {
        return new AudioFocusRequest.Builder(i);
    }

    public static /* synthetic */ AudioFocusRequest.Builder m(AudioFocusRequest audioFocusRequest) {
        return new AudioFocusRequest.Builder(audioFocusRequest);
    }

    public static /* bridge */ /* synthetic */ AudioProfile m(Object obj) {
        return (AudioProfile) obj;
    }

    public static /* synthetic */ MediaCodec.CryptoInfo.Pattern m(int i, int i2) {
        return new MediaCodec.CryptoInfo.Pattern(i, i2);
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ MediaMetricsManager m159m(Object obj) {
        return (MediaMetricsManager) obj;
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ NetworkEvent.Builder m160m() {
        return new NetworkEvent.Builder();
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ PlaybackErrorEvent.Builder m161m() {
        return new PlaybackErrorEvent.Builder();
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ PlaybackMetrics.Builder m162m() {
        return new PlaybackMetrics.Builder();
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ PlaybackMetrics.Builder m163m(Object obj) {
        return (PlaybackMetrics.Builder) obj;
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ PlaybackStateEvent.Builder m164m() {
        return new PlaybackStateEvent.Builder();
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ TrackChangeEvent.Builder m165m(int i) {
        return new TrackChangeEvent.Builder(i);
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* synthetic */ void m168m() {
    }

    /* renamed from: m  reason: collision with other method in class */
    public static /* bridge */ /* synthetic */ boolean m170m(Object obj) {
        return obj instanceof MediaDrmResetException;
    }

    public static /* synthetic */ void m$1() {
    }
}
