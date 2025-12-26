package androidx.media3.decoder.ffmpeg;

import android.os.Handler;
import android.view.Surface;
import androidx.media3.common.Format;
import androidx.media3.common.util.TraceUtil;
import androidx.media3.common.util.Util;
import androidx.media3.decoder.CryptoConfig;
import androidx.media3.decoder.Decoder;
import androidx.media3.decoder.DecoderInputBuffer;
import androidx.media3.decoder.VideoDecoderOutputBuffer;
import androidx.media3.exoplayer.DecoderReuseEvaluation;
import androidx.media3.exoplayer.RendererCapabilities;
import androidx.media3.exoplayer.video.DecoderVideoRenderer;
import androidx.media3.exoplayer.video.VideoRendererEventListener;
/* loaded from: classes.dex */
public final class ExperimentalFfmpegVideoRenderer extends DecoderVideoRenderer {
    private static final String TAG = "ExperimentalFfmpegVideoRenderer";

    @Override // androidx.media3.exoplayer.Renderer, androidx.media3.exoplayer.RendererCapabilities
    public String getName() {
        return TAG;
    }

    @Override // androidx.media3.exoplayer.video.DecoderVideoRenderer
    protected void renderOutputBufferToSurface(VideoDecoderOutputBuffer videoDecoderOutputBuffer, Surface surface) throws FfmpegDecoderException {
    }

    @Override // androidx.media3.exoplayer.video.DecoderVideoRenderer
    protected void setDecoderOutputMode(int i) {
    }

    public ExperimentalFfmpegVideoRenderer(long j, Handler handler, VideoRendererEventListener videoRendererEventListener, int i) {
        super(j, handler, videoRendererEventListener, i);
    }

    @Override // androidx.media3.exoplayer.RendererCapabilities
    public final int supportsFormat(Format format) {
        return RendererCapabilities.CC.create(0);
    }

    @Override // androidx.media3.exoplayer.video.DecoderVideoRenderer
    protected Decoder<DecoderInputBuffer, VideoDecoderOutputBuffer, FfmpegDecoderException> createDecoder(Format format, CryptoConfig cryptoConfig) throws FfmpegDecoderException {
        TraceUtil.beginSection("createFfmpegVideoDecoder");
        TraceUtil.endSection();
        return null;
    }

    @Override // androidx.media3.exoplayer.video.DecoderVideoRenderer
    protected DecoderReuseEvaluation canReuseDecoder(String str, Format format, Format format2) {
        boolean areEqual = Util.areEqual(format.sampleMimeType, format2.sampleMimeType);
        return new DecoderReuseEvaluation(str, format, format2, areEqual ? 3 : 0, areEqual ? 0 : 8);
    }
}
