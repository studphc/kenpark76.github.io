package androidx.media3.common;
/* loaded from: classes.dex */
public interface VideoGraph {

    /* loaded from: classes.dex */
    public interface Listener {
        void onEnded(long j);

        void onError(VideoFrameProcessingException videoFrameProcessingException);

        void onOutputFrameAvailableForRendering(long j);

        void onOutputSizeChanged(int i, int i2);
    }

    VideoFrameProcessor getProcessor(int i);

    boolean hasProducedFrameWithTimestampZero();

    void initialize() throws VideoFrameProcessingException;

    int registerInput() throws VideoFrameProcessingException;

    void release();

    void setOutputSurfaceInfo(SurfaceInfo surfaceInfo);
}
