package androidx.media3.exoplayer.audio;

import android.media.AudioDeviceInfo;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.AuxEffectInfo;
import androidx.media3.common.Format;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.common.util.Clock;
import androidx.media3.exoplayer.analytics.PlayerId;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
/* loaded from: classes.dex */
public interface AudioSink {
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
    public static final int OFFLOAD_MODE_DISABLED = 0;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_NOT_REQUIRED = 2;
    public static final int OFFLOAD_MODE_ENABLED_GAPLESS_REQUIRED = 1;
    public static final int SINK_FORMAT_SUPPORTED_DIRECTLY = 2;
    public static final int SINK_FORMAT_SUPPORTED_WITH_TRANSCODING = 1;
    public static final int SINK_FORMAT_UNSUPPORTED = 0;

    /* loaded from: classes.dex */
    public interface Listener {

        /* renamed from: androidx.media3.exoplayer.audio.AudioSink$Listener$-CC  reason: invalid class name */
        /* loaded from: classes.dex */
        public final /* synthetic */ class CC {
            public static void $default$onAudioCapabilitiesChanged(Listener _this) {
            }

            public static void $default$onAudioSinkError(Listener _this, Exception exc) {
            }

            public static void $default$onAudioTrackInitialized(Listener _this, AudioTrackConfig audioTrackConfig) {
            }

            public static void $default$onAudioTrackReleased(Listener _this, AudioTrackConfig audioTrackConfig) {
            }

            public static void $default$onOffloadBufferEmptying(Listener _this) {
            }

            public static void $default$onOffloadBufferFull(Listener _this) {
            }

            public static void $default$onPositionAdvancing(Listener _this, long j) {
            }

            public static void $default$onSilenceSkipped(Listener _this) {
            }
        }

        void onAudioCapabilitiesChanged();

        void onAudioSinkError(Exception exc);

        void onAudioTrackInitialized(AudioTrackConfig audioTrackConfig);

        void onAudioTrackReleased(AudioTrackConfig audioTrackConfig);

        void onOffloadBufferEmptying();

        void onOffloadBufferFull();

        void onPositionAdvancing(long j);

        void onPositionDiscontinuity();

        void onSilenceSkipped();

        void onSkipSilenceEnabledChanged(boolean z);

        void onUnderrun(int i, long j, long j2);
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface OffloadMode {
    }

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface SinkFormatSupport {
    }

    void configure(Format format, int i, int[] iArr) throws ConfigurationException;

    void disableTunneling();

    void enableTunnelingV21();

    void flush();

    AudioAttributes getAudioAttributes();

    long getCurrentPositionUs(boolean z);

    AudioOffloadSupport getFormatOffloadSupport(Format format);

    int getFormatSupport(Format format);

    PlaybackParameters getPlaybackParameters();

    boolean getSkipSilenceEnabled();

    boolean handleBuffer(ByteBuffer byteBuffer, long j, int i) throws InitializationException, WriteException;

    void handleDiscontinuity();

    boolean hasPendingData();

    boolean isEnded();

    void pause();

    void play();

    void playToEndOfStream() throws WriteException;

    void release();

    void reset();

    void setAudioAttributes(AudioAttributes audioAttributes);

    void setAudioSessionId(int i);

    void setAuxEffectInfo(AuxEffectInfo auxEffectInfo);

    void setClock(Clock clock);

    void setListener(Listener listener);

    void setOffloadDelayPadding(int i, int i2);

    void setOffloadMode(int i);

    void setOutputStreamOffsetUs(long j);

    void setPlaybackParameters(PlaybackParameters playbackParameters);

    void setPlayerId(PlayerId playerId);

    void setPreferredDevice(AudioDeviceInfo audioDeviceInfo);

    void setSkipSilenceEnabled(boolean z);

    void setVolume(float f);

    boolean supportsFormat(Format format);

    /* loaded from: classes.dex */
    public static final class AudioTrackConfig {
        public final int bufferSize;
        public final int channelConfig;
        public final int encoding;
        public final boolean offload;
        public final int sampleRate;
        public final boolean tunneling;

        public AudioTrackConfig(int i, int i2, int i3, boolean z, boolean z2, int i4) {
            this.encoding = i;
            this.sampleRate = i2;
            this.channelConfig = i3;
            this.tunneling = z;
            this.offload = z2;
            this.bufferSize = i4;
        }
    }

    /* loaded from: classes.dex */
    public static final class ConfigurationException extends Exception {
        public final Format format;

        public ConfigurationException(Throwable th, Format format) {
            super(th);
            this.format = format;
        }

        public ConfigurationException(String str, Format format) {
            super(str);
            this.format = format;
        }
    }

    /* loaded from: classes.dex */
    public static final class InitializationException extends Exception {
        public final int audioTrackState;
        public final Format format;
        public final boolean isRecoverable;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public InitializationException(int r3, int r4, int r5, int r6, androidx.media3.common.Format r7, boolean r8, java.lang.Exception r9) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.String r1 = "AudioTrack init failed "
                r0.<init>(r1)
                r0.append(r3)
                java.lang.String r1 = " Config("
                r0.append(r1)
                r0.append(r4)
                java.lang.String r4 = ", "
                r0.append(r4)
                r0.append(r5)
                r0.append(r4)
                r0.append(r6)
                java.lang.String r4 = ") "
                r0.append(r4)
                r0.append(r7)
                if (r8 == 0) goto L2d
                java.lang.String r4 = " (recoverable)"
                goto L2f
            L2d:
                java.lang.String r4 = ""
            L2f:
                r0.append(r4)
                java.lang.String r4 = r0.toString()
                r2.<init>(r4, r9)
                r2.audioTrackState = r3
                r2.isRecoverable = r8
                r2.format = r7
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.media3.exoplayer.audio.AudioSink.InitializationException.<init>(int, int, int, int, androidx.media3.common.Format, boolean, java.lang.Exception):void");
        }
    }

    /* loaded from: classes.dex */
    public static final class WriteException extends Exception {
        public final int errorCode;
        public final Format format;
        public final boolean isRecoverable;

        public WriteException(int i, Format format, boolean z) {
            super("AudioTrack write failed: " + i);
            this.isRecoverable = z;
            this.errorCode = i;
            this.format = format;
        }
    }

    /* loaded from: classes.dex */
    public static final class UnexpectedDiscontinuityException extends Exception {
        public final long actualPresentationTimeUs;
        public final long expectedPresentationTimeUs;

        public UnexpectedDiscontinuityException(long j, long j2) {
            super("Unexpected audio track timestamp discontinuity: expected " + j2 + ", got " + j);
            this.actualPresentationTimeUs = j;
            this.expectedPresentationTimeUs = j2;
        }
    }

    /* renamed from: androidx.media3.exoplayer.audio.AudioSink$-CC  reason: invalid class name */
    /* loaded from: classes.dex */
    public final /* synthetic */ class CC {
        public static void $default$release(AudioSink _this) {
        }

        public static void $default$setClock(AudioSink _this, Clock clock) {
        }

        public static void $default$setOffloadDelayPadding(AudioSink _this, int i, int i2) {
        }

        public static void $default$setOffloadMode(AudioSink _this, int i) {
        }

        public static void $default$setOutputStreamOffsetUs(AudioSink _this, long j) {
        }

        public static void $default$setPlayerId(AudioSink _this, PlayerId playerId) {
        }

        public static void $default$setPreferredDevice(AudioSink _this, AudioDeviceInfo audioDeviceInfo) {
        }
    }
}
