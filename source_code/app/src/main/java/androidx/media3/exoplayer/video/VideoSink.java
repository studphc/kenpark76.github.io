package androidx.media3.exoplayer.video;

import android.graphics.Bitmap;
import android.view.Surface;
import androidx.media3.common.Format;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.TimestampIterator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.Executor;
/* loaded from: classes.dex */
public interface VideoSink {
    public static final int INPUT_TYPE_BITMAP = 2;
    public static final int INPUT_TYPE_SURFACE = 1;

    @Target({ElementType.TYPE_USE})
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes.dex */
    public @interface InputType {
    }

    /* loaded from: classes.dex */
    public interface Listener {
        public static final Listener NO_OP = new Listener() { // from class: androidx.media3.exoplayer.video.VideoSink.Listener.1
            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onError(VideoSink videoSink, VideoSinkException videoSinkException) {
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onFirstFrameRendered(VideoSink videoSink) {
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onFrameDropped(VideoSink videoSink) {
            }

            @Override // androidx.media3.exoplayer.video.VideoSink.Listener
            public void onVideoSizeChanged(VideoSink videoSink, VideoSize videoSize) {
            }
        };

        void onError(VideoSink videoSink, VideoSinkException videoSinkException);

        void onFirstFrameRendered(VideoSink videoSink);

        void onFrameDropped(VideoSink videoSink);

        void onVideoSizeChanged(VideoSink videoSink, VideoSize videoSize);
    }

    void flush();

    Surface getInputSurface();

    boolean isEnded();

    boolean isFrameDropAllowedOnInput();

    boolean isReady();

    boolean queueBitmap(Bitmap bitmap, TimestampIterator timestampIterator);

    long registerInputFrame(long j, boolean z);

    void registerInputStream(int i, Format format);

    void render(long j, long j2) throws VideoSinkException;

    void setListener(Listener listener, Executor executor);

    void setPlaybackSpeed(float f);

    /* loaded from: classes.dex */
    public static final class VideoSinkException extends Exception {
        public final Format format;

        public VideoSinkException(Throwable th, Format format) {
            super(th);
            this.format = format;
        }
    }
}
