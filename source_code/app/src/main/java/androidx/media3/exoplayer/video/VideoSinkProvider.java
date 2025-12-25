package androidx.media3.exoplayer.video;

import android.view.Surface;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.Size;
import androidx.media3.exoplayer.video.VideoSink;
import java.util.List;
/* loaded from: classes.dex */
public interface VideoSinkProvider {
    void clearOutputSurfaceInfo();

    VideoSink getSink();

    VideoFrameReleaseControl getVideoFrameReleaseControl();

    void initialize(Format format) throws VideoSink.VideoSinkException;

    boolean isInitialized();

    void release();

    void setClock(Clock clock);

    void setOutputSurfaceInfo(Surface surface, Size size);

    void setPendingVideoEffects(List<Effect> list);

    void setStreamOffsetUs(long j);

    void setVideoEffects(List<Effect> list);

    void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener);

    void setVideoFrameReleaseControl(VideoFrameReleaseControl videoFrameReleaseControl);
}
