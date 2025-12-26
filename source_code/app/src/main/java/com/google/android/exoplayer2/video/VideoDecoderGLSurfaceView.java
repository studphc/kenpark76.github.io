package com.google.android.exoplayer2.video;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
/* loaded from: classes3.dex */
public class VideoDecoderGLSurfaceView extends GLSurfaceView {
    private final VideoDecoderGLFrameRenderer renderer;

    public VideoDecoderGLSurfaceView(Context context) {
        this(context, null);
    }

    public VideoDecoderGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        VideoDecoderGLFrameRenderer videoDecoderGLFrameRenderer = new VideoDecoderGLFrameRenderer(this);
        this.renderer = videoDecoderGLFrameRenderer;
        setPreserveEGLContextOnPause(true);
        setEGLContextClientVersion(2);
        setRenderer(videoDecoderGLFrameRenderer);
        setRenderMode(0);
    }

    public VideoDecoderOutputBufferRenderer getVideoDecoderOutputBufferRenderer() {
        return this.renderer;
    }
}
