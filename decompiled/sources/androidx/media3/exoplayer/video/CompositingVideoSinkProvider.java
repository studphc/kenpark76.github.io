package androidx.media3.exoplayer.video;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.util.Pair;
import android.view.Surface;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.DebugViewProvider;
import androidx.media3.common.Effect;
import androidx.media3.common.Format;
import androidx.media3.common.FrameInfo;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.PreviewingVideoGraph;
import androidx.media3.common.SurfaceInfo;
import androidx.media3.common.VideoFrameProcessingException;
import androidx.media3.common.VideoFrameProcessor;
import androidx.media3.common.VideoGraph;
import androidx.media3.common.VideoSize;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Clock;
import androidx.media3.common.util.HandlerWrapper;
import androidx.media3.common.util.Size;
import androidx.media3.common.util.TimestampIterator;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.ExoPlaybackException;
import androidx.media3.exoplayer.video.CompositingVideoSinkProvider;
import androidx.media3.exoplayer.video.VideoFrameRenderControl;
import androidx.media3.exoplayer.video.VideoSink;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import j$.util.Objects;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
/* loaded from: classes.dex */
public final class CompositingVideoSinkProvider implements VideoSinkProvider, VideoGraph.Listener, VideoFrameRenderControl.FrameRenderer {
    private static final Executor NO_OP_EXECUTOR = new Executor() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda4
        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            CompositingVideoSinkProvider.lambda$static$0(runnable);
        }
    };
    private static final int STATE_CREATED = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_RELEASED = 2;
    private Clock clock;
    private final Context context;
    private Pair<Surface, Size> currentSurfaceAndSize;
    private HandlerWrapper handler;
    private VideoSink.Listener listener;
    private Executor listenerExecutor;
    private Format outputFormat;
    private int pendingFlushCount;
    private final PreviewingVideoGraph.Factory previewingVideoGraphFactory;
    private int state;
    private List<Effect> videoEffects;
    private VideoFrameMetadataListener videoFrameMetadataListener;
    private VideoFrameReleaseControl videoFrameReleaseControl;
    private VideoFrameRenderControl videoFrameRenderControl;
    private PreviewingVideoGraph videoGraph;
    private VideoSinkImpl videoSinkImpl;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$static$0(Runnable runnable) {
    }

    /* loaded from: classes.dex */
    public static final class Builder {
        private boolean built;
        private final Context context;
        private PreviewingVideoGraph.Factory previewingVideoGraphFactory;
        private VideoFrameProcessor.Factory videoFrameProcessorFactory;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setVideoFrameProcessorFactory(VideoFrameProcessor.Factory factory) {
            this.videoFrameProcessorFactory = factory;
            return this;
        }

        public Builder setPreviewingVideoGraphFactory(PreviewingVideoGraph.Factory factory) {
            this.previewingVideoGraphFactory = factory;
            return this;
        }

        public CompositingVideoSinkProvider build() {
            Assertions.checkState(!this.built);
            if (this.previewingVideoGraphFactory == null) {
                if (this.videoFrameProcessorFactory == null) {
                    this.videoFrameProcessorFactory = new ReflectiveDefaultVideoFrameProcessorFactory();
                }
                this.previewingVideoGraphFactory = new ReflectivePreviewingSingleInputVideoGraphFactory(this.videoFrameProcessorFactory);
            }
            CompositingVideoSinkProvider compositingVideoSinkProvider = new CompositingVideoSinkProvider(this);
            this.built = true;
            return compositingVideoSinkProvider;
        }
    }

    private CompositingVideoSinkProvider(Builder builder) {
        this.context = builder.context;
        this.previewingVideoGraphFactory = (PreviewingVideoGraph.Factory) Assertions.checkStateNotNull(builder.previewingVideoGraphFactory);
        this.clock = Clock.DEFAULT;
        this.listener = VideoSink.Listener.NO_OP;
        this.listenerExecutor = NO_OP_EXECUTOR;
        this.state = 0;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void initialize(Format format) throws VideoSink.VideoSinkException {
        boolean z = false;
        Assertions.checkState(this.state == 0);
        Assertions.checkStateNotNull(this.videoEffects);
        if (this.videoFrameRenderControl != null && this.videoFrameReleaseControl != null) {
            z = true;
        }
        Assertions.checkState(z);
        this.handler = this.clock.createHandler((Looper) Assertions.checkStateNotNull(Looper.myLooper()), null);
        ColorInfo adjustedInputColorInfo = getAdjustedInputColorInfo(format.colorInfo);
        ColorInfo build = adjustedInputColorInfo.colorTransfer == 7 ? adjustedInputColorInfo.buildUpon().setColorTransfer(6).build() : adjustedInputColorInfo;
        try {
            PreviewingVideoGraph.Factory factory = this.previewingVideoGraphFactory;
            Context context = this.context;
            DebugViewProvider debugViewProvider = DebugViewProvider.NONE;
            final HandlerWrapper handlerWrapper = this.handler;
            Objects.requireNonNull(handlerWrapper);
            this.videoGraph = factory.create(context, adjustedInputColorInfo, build, debugViewProvider, this, new Executor() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    HandlerWrapper.this.post(runnable);
                }
            }, ImmutableList.of(), 0L);
            Pair<Surface, Size> pair = this.currentSurfaceAndSize;
            if (pair != null) {
                Size size = (Size) this.currentSurfaceAndSize.second;
                maybeSetOutputSurfaceInfo((Surface) pair.first, size.getWidth(), size.getHeight());
            }
            VideoSinkImpl videoSinkImpl = new VideoSinkImpl(this.context, this, this.videoGraph);
            this.videoSinkImpl = videoSinkImpl;
            videoSinkImpl.setVideoEffects((List) Assertions.checkNotNull(this.videoEffects));
            this.state = 1;
        } catch (VideoFrameProcessingException e) {
            throw new VideoSink.VideoSinkException(e, format);
        }
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public boolean isInitialized() {
        return this.state == 1;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void release() {
        if (this.state == 2) {
            return;
        }
        HandlerWrapper handlerWrapper = this.handler;
        if (handlerWrapper != null) {
            handlerWrapper.removeCallbacksAndMessages(null);
        }
        PreviewingVideoGraph previewingVideoGraph = this.videoGraph;
        if (previewingVideoGraph != null) {
            previewingVideoGraph.release();
        }
        this.currentSurfaceAndSize = null;
        this.state = 2;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public VideoSink getSink() {
        return (VideoSink) Assertions.checkStateNotNull(this.videoSinkImpl);
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setVideoEffects(List<Effect> list) {
        this.videoEffects = list;
        if (isInitialized()) {
            ((VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl)).setVideoEffects(list);
        }
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setPendingVideoEffects(List<Effect> list) {
        this.videoEffects = list;
        if (isInitialized()) {
            ((VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl)).setPendingVideoEffects(list);
        }
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setStreamOffsetUs(long j) {
        ((VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl)).setStreamOffsetUs(j);
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setOutputSurfaceInfo(Surface surface, Size size) {
        Pair<Surface, Size> pair = this.currentSurfaceAndSize;
        if (pair != null && ((Surface) pair.first).equals(surface) && ((Size) this.currentSurfaceAndSize.second).equals(size)) {
            return;
        }
        this.currentSurfaceAndSize = Pair.create(surface, size);
        maybeSetOutputSurfaceInfo(surface, size.getWidth(), size.getHeight());
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setVideoFrameReleaseControl(VideoFrameReleaseControl videoFrameReleaseControl) {
        Assertions.checkState(!isInitialized());
        this.videoFrameReleaseControl = videoFrameReleaseControl;
        this.videoFrameRenderControl = new VideoFrameRenderControl(this, videoFrameReleaseControl);
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void clearOutputSurfaceInfo() {
        maybeSetOutputSurfaceInfo(null, Size.UNKNOWN.getWidth(), Size.UNKNOWN.getHeight());
        this.currentSurfaceAndSize = null;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setVideoFrameMetadataListener(VideoFrameMetadataListener videoFrameMetadataListener) {
        this.videoFrameMetadataListener = videoFrameMetadataListener;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public VideoFrameReleaseControl getVideoFrameReleaseControl() {
        return this.videoFrameReleaseControl;
    }

    @Override // androidx.media3.exoplayer.video.VideoSinkProvider
    public void setClock(Clock clock) {
        Assertions.checkState(!isInitialized());
        this.clock = clock;
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onOutputSizeChanged(int i, int i2) {
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).onOutputSizeChanged(i, i2);
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onOutputFrameAvailableForRendering(long j) {
        if (this.pendingFlushCount > 0) {
            return;
        }
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).onOutputFrameAvailableForRendering(j);
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onEnded(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // androidx.media3.common.VideoGraph.Listener
    public void onError(final VideoFrameProcessingException videoFrameProcessingException) {
        final VideoSink.Listener listener = this.listener;
        this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CompositingVideoSinkProvider.this.m292xe613aca2(listener, videoFrameProcessingException);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$onError$1$androidx-media3-exoplayer-video-CompositingVideoSinkProvider  reason: not valid java name */
    public /* synthetic */ void m292xe613aca2(VideoSink.Listener listener, VideoFrameProcessingException videoFrameProcessingException) {
        VideoSinkImpl videoSinkImpl = (VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl);
        listener.onError(videoSinkImpl, new VideoSink.VideoSinkException(videoFrameProcessingException, (Format) Assertions.checkStateNotNull(videoSinkImpl.inputFormat)));
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
    public void onVideoSizeChanged(final VideoSize videoSize) {
        this.outputFormat = new Format.Builder().setWidth(videoSize.width).setHeight(videoSize.height).setSampleMimeType(MimeTypes.VIDEO_RAW).build();
        final VideoSinkImpl videoSinkImpl = (VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl);
        final VideoSink.Listener listener = this.listener;
        this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                VideoSink.Listener.this.onVideoSizeChanged(videoSinkImpl, videoSize);
            }
        });
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
    public void renderFrame(long j, long j2, long j3, boolean z) {
        if (z && this.listenerExecutor != NO_OP_EXECUTOR) {
            final VideoSinkImpl videoSinkImpl = (VideoSinkImpl) Assertions.checkStateNotNull(this.videoSinkImpl);
            final VideoSink.Listener listener = this.listener;
            this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    VideoSink.Listener.this.onFirstFrameRendered(videoSinkImpl);
                }
            });
        }
        if (this.videoFrameMetadataListener != null) {
            Format format = this.outputFormat;
            if (format == null) {
                format = new Format.Builder().build();
            }
            this.videoFrameMetadataListener.onVideoFrameAboutToBeRendered(j2 - j3, this.clock.nanoTime(), format, null);
        }
        ((PreviewingVideoGraph) Assertions.checkStateNotNull(this.videoGraph)).renderOutputFrame(j);
    }

    @Override // androidx.media3.exoplayer.video.VideoFrameRenderControl.FrameRenderer
    public void dropFrame() {
        final VideoSink.Listener listener = this.listener;
        this.listenerExecutor.execute(new Runnable() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                CompositingVideoSinkProvider.this.m291xdf97794a(listener);
            }
        });
        ((PreviewingVideoGraph) Assertions.checkStateNotNull(this.videoGraph)).renderOutputFrame(-2L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: lambda$dropFrame$4$androidx-media3-exoplayer-video-CompositingVideoSinkProvider  reason: not valid java name */
    public /* synthetic */ void m291xdf97794a(VideoSink.Listener listener) {
        listener.onFrameDropped((VideoSink) Assertions.checkStateNotNull(this.videoSinkImpl));
    }

    public void render(long j, long j2) throws ExoPlaybackException {
        if (this.pendingFlushCount == 0) {
            ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).render(j, j2);
        }
    }

    public Surface getOutputSurface() {
        Pair<Surface, Size> pair = this.currentSurfaceAndSize;
        if (pair != null) {
            return (Surface) pair.first;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListener(VideoSink.Listener listener, Executor executor) {
        if (Objects.equals(listener, this.listener)) {
            Assertions.checkState(Objects.equals(executor, this.listenerExecutor));
            return;
        }
        this.listener = listener;
        this.listenerExecutor = executor;
    }

    private void maybeSetOutputSurfaceInfo(Surface surface, int i, int i2) {
        if (this.videoGraph != null) {
            this.videoGraph.setOutputSurfaceInfo(surface != null ? new SurfaceInfo(surface, i, i2) : null);
            ((VideoFrameReleaseControl) Assertions.checkNotNull(this.videoFrameReleaseControl)).setOutputSurface(surface);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isReady() {
        return this.pendingFlushCount == 0 && ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).isReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasReleasedFrame(long j) {
        return this.pendingFlushCount == 0 && ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).hasReleasedFrame(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flush() {
        this.pendingFlushCount++;
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).flush();
        ((HandlerWrapper) Assertions.checkStateNotNull(this.handler)).post(new Runnable() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CompositingVideoSinkProvider.this.flushInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void flushInternal() {
        int i = this.pendingFlushCount - 1;
        this.pendingFlushCount = i;
        if (i > 0) {
            return;
        }
        if (i < 0) {
            throw new IllegalStateException(String.valueOf(this.pendingFlushCount));
        }
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).flush();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPlaybackSpeed(float f) {
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).setPlaybackSpeed(f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStreamOffsetChange(long j, long j2) {
        ((VideoFrameRenderControl) Assertions.checkStateNotNull(this.videoFrameRenderControl)).onStreamOffsetChange(j, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ColorInfo getAdjustedInputColorInfo(ColorInfo colorInfo) {
        return (colorInfo == null || !ColorInfo.isTransferHdr(colorInfo)) ? ColorInfo.SDR_BT709_LIMITED : colorInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class VideoSinkImpl implements VideoSink {
        private final CompositingVideoSinkProvider compositingVideoSinkProvider;
        private final Context context;
        private boolean hasRegisteredFirstInputStream;
        private Format inputFormat;
        private long inputStreamOffsetUs;
        private int inputType;
        private long pendingInputStreamBufferPresentationTimeUs;
        private boolean pendingInputStreamOffsetChange;
        private Effect rotationEffect;
        private final VideoFrameProcessor videoFrameProcessor;
        private final int videoFrameProcessorMaxPendingFrameCount;
        private final ArrayList<Effect> videoEffects = new ArrayList<>();
        private long finalBufferPresentationTimeUs = -9223372036854775807L;
        private long lastBufferPresentationTimeUs = -9223372036854775807L;

        public VideoSinkImpl(Context context, CompositingVideoSinkProvider compositingVideoSinkProvider, PreviewingVideoGraph previewingVideoGraph) throws VideoFrameProcessingException {
            this.context = context;
            this.compositingVideoSinkProvider = compositingVideoSinkProvider;
            this.videoFrameProcessorMaxPendingFrameCount = Util.getMaxPendingFramesCountForMediaCodecDecoders(context);
            this.videoFrameProcessor = previewingVideoGraph.getProcessor(previewingVideoGraph.registerInput());
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void flush() {
            this.videoFrameProcessor.flush();
            this.hasRegisteredFirstInputStream = false;
            this.finalBufferPresentationTimeUs = -9223372036854775807L;
            this.lastBufferPresentationTimeUs = -9223372036854775807L;
            this.compositingVideoSinkProvider.flush();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isReady() {
            return this.compositingVideoSinkProvider.isReady();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isEnded() {
            long j = this.finalBufferPresentationTimeUs;
            return j != -9223372036854775807L && this.compositingVideoSinkProvider.hasReleasedFrame(j);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void registerInputStream(int i, Format format) {
            Format format2;
            if (i != 1 && i != 2) {
                throw new UnsupportedOperationException("Unsupported input type " + i);
            }
            if (i == 1 && Util.SDK_INT < 21 && format.rotationDegrees != -1 && format.rotationDegrees != 0) {
                if (this.rotationEffect == null || (format2 = this.inputFormat) == null || format2.rotationDegrees != format.rotationDegrees) {
                    this.rotationEffect = ScaleAndRotateAccessor.createRotationEffect(format.rotationDegrees);
                }
            } else {
                this.rotationEffect = null;
            }
            this.inputType = i;
            this.inputFormat = format;
            if (!this.hasRegisteredFirstInputStream) {
                maybeRegisterInputStream();
                this.hasRegisteredFirstInputStream = true;
                this.pendingInputStreamBufferPresentationTimeUs = -9223372036854775807L;
                return;
            }
            Assertions.checkState(this.lastBufferPresentationTimeUs != -9223372036854775807L);
            this.pendingInputStreamBufferPresentationTimeUs = this.lastBufferPresentationTimeUs;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setListener(VideoSink.Listener listener, Executor executor) {
            this.compositingVideoSinkProvider.setListener(listener, executor);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean isFrameDropAllowedOnInput() {
            return Util.isFrameDropAllowedOnSurfaceInput(this.context);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public Surface getInputSurface() {
            return this.videoFrameProcessor.getInputSurface();
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public long registerInputFrame(long j, boolean z) {
            Assertions.checkState(this.videoFrameProcessorMaxPendingFrameCount != -1);
            long j2 = this.pendingInputStreamBufferPresentationTimeUs;
            if (j2 != -9223372036854775807L) {
                if (!this.compositingVideoSinkProvider.hasReleasedFrame(j2)) {
                    return -9223372036854775807L;
                }
                maybeRegisterInputStream();
                this.pendingInputStreamBufferPresentationTimeUs = -9223372036854775807L;
            }
            if (this.videoFrameProcessor.getPendingInputFrameCount() < this.videoFrameProcessorMaxPendingFrameCount && this.videoFrameProcessor.registerInputFrame()) {
                long j3 = this.inputStreamOffsetUs;
                long j4 = j + j3;
                if (this.pendingInputStreamOffsetChange) {
                    this.compositingVideoSinkProvider.onStreamOffsetChange(j4, j3);
                    this.pendingInputStreamOffsetChange = false;
                }
                this.lastBufferPresentationTimeUs = j4;
                if (z) {
                    this.finalBufferPresentationTimeUs = j4;
                }
                return j4 * 1000;
            }
            return -9223372036854775807L;
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public boolean queueBitmap(Bitmap bitmap, TimestampIterator timestampIterator) {
            return ((VideoFrameProcessor) Assertions.checkStateNotNull(this.videoFrameProcessor)).queueInputBitmap(bitmap, timestampIterator);
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void render(long j, long j2) throws VideoSink.VideoSinkException {
            try {
                this.compositingVideoSinkProvider.render(j, j2);
            } catch (ExoPlaybackException e) {
                Format format = this.inputFormat;
                if (format == null) {
                    format = new Format.Builder().build();
                }
                throw new VideoSink.VideoSinkException(e, format);
            }
        }

        @Override // androidx.media3.exoplayer.video.VideoSink
        public void setPlaybackSpeed(float f) {
            this.compositingVideoSinkProvider.setPlaybackSpeed(f);
        }

        public void setVideoEffects(List<Effect> list) {
            setPendingVideoEffects(list);
            maybeRegisterInputStream();
        }

        public void setPendingVideoEffects(List<Effect> list) {
            this.videoEffects.clear();
            this.videoEffects.addAll(list);
        }

        public void setStreamOffsetUs(long j) {
            this.pendingInputStreamOffsetChange = this.inputStreamOffsetUs != j;
            this.inputStreamOffsetUs = j;
        }

        private void maybeRegisterInputStream() {
            if (this.inputFormat == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            Effect effect = this.rotationEffect;
            if (effect != null) {
                arrayList.add(effect);
            }
            arrayList.addAll(this.videoEffects);
            Format format = (Format) Assertions.checkNotNull(this.inputFormat);
            this.videoFrameProcessor.registerInputStream(this.inputType, arrayList, new FrameInfo.Builder(CompositingVideoSinkProvider.getAdjustedInputColorInfo(format.colorInfo), format.width, format.height).setPixelWidthHeightRatio(format.pixelWidthHeightRatio).build());
        }

        /* loaded from: classes.dex */
        private static final class ScaleAndRotateAccessor {
            private static Method buildScaleAndRotateTransformationMethod;
            private static Constructor<?> scaleAndRotateTransformationBuilderConstructor;
            private static Method setRotationMethod;

            private ScaleAndRotateAccessor() {
            }

            public static Effect createRotationEffect(float f) {
                try {
                    prepare();
                    Object newInstance = scaleAndRotateTransformationBuilderConstructor.newInstance(new Object[0]);
                    setRotationMethod.invoke(newInstance, Float.valueOf(f));
                    return (Effect) Assertions.checkNotNull(buildScaleAndRotateTransformationMethod.invoke(newInstance, new Object[0]));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }

            @EnsuresNonNull({"scaleAndRotateTransformationBuilderConstructor", "setRotationMethod", "buildScaleAndRotateTransformationMethod"})
            private static void prepare() throws NoSuchMethodException, ClassNotFoundException {
                if (scaleAndRotateTransformationBuilderConstructor == null || setRotationMethod == null || buildScaleAndRotateTransformationMethod == null) {
                    Class<?> cls = Class.forName("androidx.media3.effect.ScaleAndRotateTransformation$Builder");
                    scaleAndRotateTransformationBuilderConstructor = cls.getConstructor(new Class[0]);
                    setRotationMethod = cls.getMethod("setRotationDegrees", Float.TYPE);
                    buildScaleAndRotateTransformationMethod = cls.getMethod("build", new Class[0]);
                }
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class ReflectivePreviewingSingleInputVideoGraphFactory implements PreviewingVideoGraph.Factory {
        private final VideoFrameProcessor.Factory videoFrameProcessorFactory;

        public ReflectivePreviewingSingleInputVideoGraphFactory(VideoFrameProcessor.Factory factory) {
            this.videoFrameProcessorFactory = factory;
        }

        @Override // androidx.media3.common.PreviewingVideoGraph.Factory
        public PreviewingVideoGraph create(Context context, ColorInfo colorInfo, ColorInfo colorInfo2, DebugViewProvider debugViewProvider, VideoGraph.Listener listener, Executor executor, List<Effect> list, long j) throws VideoFrameProcessingException {
            Constructor<?> constructor;
            Object[] objArr;
            try {
                constructor = Class.forName("androidx.media3.effect.PreviewingSingleInputVideoGraph$Factory").getConstructor(VideoFrameProcessor.Factory.class);
                objArr = new Object[1];
            } catch (Exception e) {
                e = e;
            }
            try {
                objArr[0] = this.videoFrameProcessorFactory;
                return ((PreviewingVideoGraph.Factory) constructor.newInstance(objArr)).create(context, colorInfo, colorInfo2, debugViewProvider, listener, executor, list, j);
            } catch (Exception e2) {
                e = e2;
                throw VideoFrameProcessingException.from(e);
            }
        }
    }

    /* loaded from: classes.dex */
    private static final class ReflectiveDefaultVideoFrameProcessorFactory implements VideoFrameProcessor.Factory {
        private static final Supplier<VideoFrameProcessor.Factory> VIDEO_FRAME_PROCESSOR_FACTORY_SUPPLIER = Suppliers.memoize(new Supplier() { // from class: androidx.media3.exoplayer.video.CompositingVideoSinkProvider$ReflectiveDefaultVideoFrameProcessorFactory$$ExternalSyntheticLambda0
            @Override // com.google.common.base.Supplier
            public final Object get() {
                return CompositingVideoSinkProvider.ReflectiveDefaultVideoFrameProcessorFactory.lambda$static$0();
            }
        });

        private ReflectiveDefaultVideoFrameProcessorFactory() {
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ VideoFrameProcessor.Factory lambda$static$0() {
            try {
                Class<?> cls = Class.forName("androidx.media3.effect.DefaultVideoFrameProcessor$Factory$Builder");
                return (VideoFrameProcessor.Factory) Assertions.checkNotNull(cls.getMethod("build", new Class[0]).invoke(cls.getConstructor(new Class[0]).newInstance(new Object[0]), new Object[0]));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        @Override // androidx.media3.common.VideoFrameProcessor.Factory
        public VideoFrameProcessor create(Context context, DebugViewProvider debugViewProvider, ColorInfo colorInfo, boolean z, Executor executor, VideoFrameProcessor.Listener listener) throws VideoFrameProcessingException {
            return VIDEO_FRAME_PROCESSOR_FACTORY_SUPPLIER.get().create(context, debugViewProvider, colorInfo, z, executor, listener);
        }
    }
}
